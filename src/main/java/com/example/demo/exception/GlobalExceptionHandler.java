package com.example.demo.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

//例外ハンドラークラス

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /*
     * 問①検索時エラー
     * EmployeeNotFoundExceptionが発生した際にこのハンドラーが処理されるようにしなさい。
     * レスポンスにはHTTPステータス「404 NOTFOUND」がレスポンスされるようにしなさい。
     */
    
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(
            EmployeeNotFoundException ex, WebRequest request) {
        
        ErrorResponse employeeNotFoundResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), 
                "検索エラーが発生しました。", List.of(ex.getMessage()));
        
        return new ResponseEntity<>(employeeNotFoundResponse, HttpStatus.NOT_FOUND);
    }
    
    //バリデーションエラー
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        
        ErrorResponse validateErrorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "入力エラーが発生しました。", errors);
        
        return new ResponseEntity<>(validateErrorResponse, HttpStatus.BAD_REQUEST);
    }
    
    //IDが登録済み
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(
            IllegalStateException ex, WebRequest request) {
        
        ErrorResponse illegalStateErrorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(), "登録エラーが発生しました。", List.of(ex.getMessage()));
        
        return new ResponseEntity<>(illegalStateErrorResponse, HttpStatus.CONFLICT);
        
    }
    
    //URLが見つからないときの例外
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, WebRequest request) {
        
        ErrorResponse noHandlerFoundErrorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), "不明なパスです。", List.of(ex.getRequestURL()));
        
        return new ResponseEntity<>(noHandlerFoundErrorResponse, HttpStatus.NOT_FOUND);
    }

}
