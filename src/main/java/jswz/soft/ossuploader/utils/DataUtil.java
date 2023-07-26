package jswz.soft.ossuploader.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * @author wazh
 * @since 2023-06-17-12:52
 */
public class DataUtil {
    private static final Calendar calendar = Calendar.getInstance();
    public static String getYear() {
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
    public static String getMonth() {
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }
    public static String getDay() {
        return String.valueOf(calendar.get(Calendar.DATE));
    }

    /**
     * @return 年-月-日 时:分:秒
     */
    public String getNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(dateTimeFormatter);
    }
}

