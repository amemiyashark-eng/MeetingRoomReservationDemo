package com.example.demo.dto;

import com.example.demo.model.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 問①レスポンス用Dtoクラス
 * 各フィールド変数に値を格納できるようコンストラクタを記述しなさい。
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {
    
    private String empId;
    
    private String password;
    
    private String name;
    
    private String mail;
    
    private String programingLanguage;
    
    private String comment;
    
    private String createDate;
    
    private String updateDate;
    
    private String deleteFlg;
public EmployeeResponseDto(EmployeeEntity employee) {
        
        this.name = employee.getName();
        
        this.empId = employee.getEmpId();
        this.password = employee.getPassword();
        this.mail = employee.getMail();
        this.programingLanguage = employee.getProgramingLanguage();
        this.comment = employee.getComment();
        this.createDate = employee.getCreateDate();
        this.updateDate = employee.getUpdateDate();
        this.deleteFlg = employee.getDeleteFlg();
    }

}
