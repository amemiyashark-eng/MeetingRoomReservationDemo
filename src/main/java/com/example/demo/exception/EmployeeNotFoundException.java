package com.example.demo.exception;

//社員情報が見つからなかった時用のカスタム例外

public class EmployeeNotFoundException extends RuntimeException {
    
    public EmployeeNotFoundException(String message) {
        super(message);
    }
    

}
