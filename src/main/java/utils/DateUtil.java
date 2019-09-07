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

    private static final int MILLE_SECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    private static final String YYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static Date addOneDay(Date oldDate) {
        return addDate(oldDate, 1);
    }

    public static Date addDate(Date oldDate, int addDays) {
        long time = oldDate.getTime();
        long addTime = MILLE_SECONDS_PER_DAY * (long) addDays;
        time += addTime;
        return new Date(time);
    }

    public static String getYYMMDDHHMMSSDate(Date date) {
        try {
            SimpleDateFormat targetformator = new SimpleDateFormat(YYMMDDHHMMSS);
            return targetformator.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getViewDate(Date date) {
        try {
            SimpleDateFormat targetformator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return targetformator.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCacheFormatDate(Date date) {
        try {
            SimpleDateFormat targetformator = new SimpleDateFormat("yyyyMMdd");
            return targetformator.format(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static String getFormatDate(Date date, String pattern) {
        try {
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
