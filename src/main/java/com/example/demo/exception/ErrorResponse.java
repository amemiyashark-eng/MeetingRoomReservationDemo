package com.example.demo.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.DateUtil;

//エラーレスポンス用クラス

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    //ステータスコード
    private int code;
    
    //エラー発生時刻のタイムスタンプ
    private String errorTimeStamp;
    
    //エラー発生のメッセージ
    private String message;
    
    //エラー内容のメッセージ
    private List<String> errors;
    
    //バリデーションエラー発生時のコンストラクタ
    public ErrorResponse(int code, String message, List<String> errors) {
        this.code = code;
        this.errorTimeStamp = DateUtil.getCurrentTimeStampISO8601();
        this.message = message;
        this.errors = errors;
    }

}
