package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.EmployeeEntity;

/*
 *リポジトリクラス 
 */

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    
    //削除フラグが0のデータを検索
    List<EmployeeEntity> findByDeleteFlg(String deleteFlg);
    
    //IDを元に削除フラグが0のものを検索
    EmployeeEntity findByEmpIdAndDeleteFlg(String empId, String deleteFlg);
    
    //IDを前後方一致で削除フラグが0のものを検索
    List<EmployeeEntity> findByEmpIdContainingAndDeleteFlg(String empId, String deleteFlg);

}
