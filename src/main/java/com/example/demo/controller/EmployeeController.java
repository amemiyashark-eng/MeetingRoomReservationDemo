package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeRegistRequestDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.dto.EmployeeUpdateRequestDto;
import com.example.demo.service.EmployeeService;

/**
 * コントローラークラス
 */

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    
    @Autowired
    EmployeeService empService;
    
    /*
     * 問①検索機能
     * 検索機能を以下の条件に沿って実装しなさい。
     * 条件①クエリパラメータが空白または空文字の場合は全件検索
     * 条件②クエリパラメータが5文字未満の場合は前後方一致検索
     * 条件③クエリパラメータが5文字の場合は完全一致検索
     * 条件④1つのメソッドで検索機能を実装すること
     * 条件⑤レスポンスの内容はDTOクラスを使い、ResponseEntityでHTTPステータス「200 OK」をレスポンスすること
     */
     @GetMapping("/search")
     public ResponseEntity <List<EmployeeResponseDto>> searchEmployees(@RequestParam("empid") String empId) {
        
         List<EmployeeResponseDto> list = new ArrayList<>();

         if( empId == null || empId.trim().isEmpty()) {
             list = empService.findAllActiveEmployees();
         }else if(empId.trim().length() == 5) {
             EmployeeResponseDto dto = empService.searchActiveEmployees(empId);
             list.add(dto);
         }else if(!empId.trim().isEmpty() && empId.trim().length() < 5) {
             list = empService.searchActiveEmployeesLike(empId);
         }
         return  ResponseEntity.ok(list);
         
     }

        
     /*
      * 問②データ登録機能
      * バリデーションが機能するようにアノテーションを付与し、
      * EmployeeRegistRequestDtoでリクエストボディをバインドしなさい。
      * レスポンスにはResponseEntityを使ってresponseDtoと
      * HTTPステータス「201 CREATED」をレスポンスできるようにしなさい。
      */
    @PostMapping("/regist")
    public ResponseEntity<EmployeeResponseDto> registEmployeeData(@Validated @RequestBody EmployeeRegistRequestDto requestDto) {
        
        EmployeeResponseDto responseDto = empService.registEmployee(requestDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
            
    }
    
    /*
     * 問③データ更新
     * パスパラメータでリクエストされたempidを受け取れるようにしなさい。
     * 適切にパスパラメータで送信されたempidをバインドし、
     * バリデーションが機能するようにアノテーションを付与して
     * リクエストボディをEmployeeUpdateRequestDtoでバインドしなさい。
     */
    @PutMapping("/update/{empId}")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeData( @PathVariable String empId, @Validated @RequestBody EmployeeUpdateRequestDto requestDto) {
       
        EmployeeResponseDto responseDto = empService.updateEmployee(empId, requestDto);
        
        return ResponseEntity.ok(responseDto);
    }
    
    /*
     * データ削除
     * データのレスポンスはなし。
     * 削除したメッセージを返す
     */
    @PutMapping("/delete/{empId}")
    public ResponseEntity<String> updateDeleteFlg(@PathVariable("empid") String empId) {
        
        empService.updateDeleteFlg(empId);
        
        return ResponseEntity.ok("削除しました。");
    }

}
