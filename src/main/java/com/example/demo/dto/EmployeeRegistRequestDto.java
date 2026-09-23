package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 問①登録リクエスト用Dtoクラス
 * 各フィールド変数に足りないバリデーションのアノテーションやメッセージを記述しなさい。
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistRequestDto {
    
    @NotNull(message = "IDの入力は必須です。")
    @Size(min = 5, max = 5, message = "IDの入力値が不正です。")
    private String empId;
    
    @NotBlank(message = "パスワードの入力は必須です。")
    @Size(min = 4, max = 4, message = "パスワードは4文字で設定してください。")
    
    private String password;
    
    @NotBlank(message = "名前の入力は必須です。")
    @Size(min = 2, max = 50, message = "名前の入力値が不正です。")
    private String name;
    
    @Email(message = "正しいメールアドレスの形式で入力してください。")
    @Size(max = 255, message = "メールアドレスが長すぎます。")
    private String mail;
    
    
    private String programingLanguage;
    
    
    private String comment;

}
