package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//日時作成のためのUtilクラス

public class DateUtil {
    
    //DB登録用日時のフォーマット設定
    private static final DateTimeFormatter FORMATTER_STANDARD = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    
    //エラーレスポンス用の日時フォーマット設定
    private static final DateTimeFormatter FORMATTER_ISO8601 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    //DB登録用日時の取得
    public static String getCurrentTimeStampStandard() {
        return LocalDateTime.now().format(FORMATTER_STANDARD);
    }
    
    //エラーレスポンス用の日時の取得
    public static String getCurrentTimeStampISO8601() {
        return LocalDateTime.now().format(FORMATTER_ISO8601);
    }

}
