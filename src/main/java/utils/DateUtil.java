package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/19 18:21
 */
public class DateUtil {

    private static final int MILLSECONDSPERDAY = 24 * 60 * 60 * 1000;


    public static Date addDate(Date oldDate, int addDays) {
        long time = oldDate.getTime();
        long addTime = MILLSECONDSPERDAY * (long) addDays;
        time += addTime;
        return new Date(time);
    }

    public static String getFormatDate(String source, String pattern) {
        SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formator.parse(source);
            SimpleDateFormat targetformator = new SimpleDateFormat(pattern);
            return targetformator.format(date);
        } catch (Exception e) {
            return null;
        }
    }
    public static Date parseDate(String source, String pattern) {
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        try {
            return formater.parse(source);
        } catch (Exception e) {
            return null;
        }
    }




}
