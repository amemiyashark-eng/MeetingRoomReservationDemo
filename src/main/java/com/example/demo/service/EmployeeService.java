package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRegistRequestDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.dto.EmployeeUpdateRequestDto;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.repository.EmployeeRepository;

import util.DateUtil;

/*
 * サービスクラス
 */

@Service
public class EmployeeService {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    EmployeeRepository empRepository;
    
    /*
     * 問①全件検索
     * 削除フラグが0のデータを取得できるようにしなさい。
     * また、データが見つからない場合はEmployeeNotFoundExceptionが発生するようにしなさい。
     */
    public List<EmployeeResponseDto> findAllActiveEmployees() {
        
        List<EmployeeEntity> employees = empRepository.findByDeleteFlg("0");
        if(employees  == null || employees.isEmpty()) {
            throw new EmployeeNotFoundException("データが見つかりません。");
            
        }
        
        List<EmployeeResponseDto> responseEmployees = new ArrayList<>();
        for(EmployeeEntity employee : employees) {
            responseEmployees.add(new EmployeeResponseDto(employee));
        }
        
        return responseEmployees;
    }
    
    /*
     * 問②完全一致検索
     * IDを元に削除フラグが0のデータを取得しなさい。
     * 見つからない場合はEmployeeNotFoundExceptionが発生するようにしなさい。
     */
    public EmployeeResponseDto searchActiveEmployees(String empId) {
        
        EmployeeEntity employee = empRepository.findByEmpIdAndDeleteFlg( empId, "0");
        if(employee == null) {
            throw new EmployeeNotFoundException("データが見つかりません。");
        }
        
        return new EmployeeResponseDto(employee);
    }
    
    //削除フラグ0のデータをIDで前後方一致検索
    public List<EmployeeResponseDto> searchActiveEmployeesLike(String empId) {
        
        List<EmployeeEntity> employees = empRepository.findByEmpIdContainingAndDeleteFlg(empId, "0");
        
        if(employees == null || employees.isEmpty()) {
            throw new EmployeeNotFoundException("データが見つかりません。");
        }
        
        List<EmployeeResponseDto> responseEmployees = new ArrayList<>();
        for(EmployeeEntity employee : employees) {
            responseEmployees.add(new EmployeeResponseDto(employee));
        }
        
        return responseEmployees;
    }
    
    /*
     * 問③データ登録処理
     * IDが既に存在する場合はIllegalStateExceptionが発生するようにしなさい。
     * 作成日時と更新日時をDateUtilクラスから適切なメソッドを呼び出し取得し、DBに登録できるようにしなさい。
     * またレスポンスボディとして返却する返り値をResponseDtoに格納し、返しなさい。
     */
    @Transactional
    public EmployeeResponseDto registEmployee(EmployeeRegistRequestDto requestDto) {
        if(empRepository.existsById(requestDto.getEmpId())) {
            throw new IllegalStateException("IDが既に存在します。");
        }
        
        
        //作成日時の作成
        String createDate = DateUtil.getCurrentTimeStampStandard();
        
        //更新日時の作成
        String updateDate = DateUtil.getCurrentTimeStampStandard();
        
        //パスワードエンコード
        String reqPassword = passwordEncoder.encode(requestDto.getPassword());
        
        EmployeeEntity employee = new EmployeeEntity();
        employee.setEmpId(requestDto.getEmpId());
        employee.setPassword(reqPassword);
        employee.setName(requestDto.getName());
        employee.setMail(requestDto.getMail());
        employee.setComment(requestDto.getComment());
        employee.setProgramingLanguage(requestDto.getProgramingLanguage());
        
        employee.setDeleteFlg("0");
        
        employee.setUpdateDate(updateDate);
       

        employee.setCreateDate(createDate);
        

        employee = empRepository.save(employee);
        
        return new EmployeeResponseDto(employee);
    }
    
    /*
     * 問④データ更新
     * 更新日時をDateUtilクラスから適切なメソッドを呼び出して取得しなさい
     * Entityクラスのフィールドに更新するリクエストの値をセットしなさい。
     */
    @Transactional
    public EmployeeResponseDto updateEmployee(String empid, EmployeeUpdateRequestDto requestDto) {
        
        EmployeeEntity employee = new EmployeeEntity();
        employee = empRepository.findById(empid).orElse(null);
        
        String updateDate = DateUtil.getCurrentTimeStampStandard();
        

        employee.setName(requestDto.getName());
        employee.setMail(requestDto.getMail());
        employee.setProgramingLanguage(requestDto.getProgramingLanguage());
        employee.setComment(requestDto.getComment());
        employee.setUpdateDate(updateDate);
       
        employee = empRepository.save(employee);
        
        return new EmployeeResponseDto(employee);
    }
    
    /*
     * 問⑤データ削除
     * 削除フラグと更新日時を変更しDBに登録しなさい。
     */
    @Transactional
    public void updateDeleteFlg(String empid) {
        
        EmployeeEntity employee = new EmployeeEntity();
        employee = empRepository.findById(empid).orElse(null);
        String updateDate = DateUtil.getCurrentTimeStampStandard();
        

        employee.setUpdateDate(updateDate);

        employee.setDeleteFlg("1");

    }

}
