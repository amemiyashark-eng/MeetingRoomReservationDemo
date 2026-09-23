package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * employeeテーブルのEntityクラス
 */

@Entity
@Table(name = "employee")
@Data
public class EmployeeEntity {
    
    @Id
    @Column(name = "empid")
    private String empId;
    
    @Column
    private String password;
    
    @Column
    private String name;
    
    @Column
    private String mail;
    
    @Column(name = "programinglanguage")
    private String programingLanguage;
    
    @Column
    private String comment;
    
    @Column(name = "createdate")
    private String createDate;
    
    @Column(name = "updatedate")
    private String updateDate;
    
    @Column(name = "deleteflg")
    private String deleteFlg;

}
