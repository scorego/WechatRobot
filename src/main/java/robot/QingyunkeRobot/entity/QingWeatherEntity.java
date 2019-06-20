package robot.QingyunkeRobot.entity;

import cons.WxMsg;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class QingWeatherEntity {
    private static final int SUCCESS = 200;
    private String time;
    private CityInfo cityInfo;
    private String date;
    private String message;
    private int status;
    private WeatherData data;

    public boolean isValid() {
        return status == SUCCESS;
    }

    public String getWeather() {
        String today = date.substring(date.length() - 2);
        for (OtherDay thisDay : data.getForecast()) {
            if (thisDay.getDate().equals(today)) {
                return cityInfo.getCityName() + " " + thisDay.getWeather();
            }
        }
        return cityInfo.getCityName() + "今日天气:"
                + data.toString()
                + "更新时间" + time.substring(11) + WxMsg.LINE;
    }
}

@Data
class CityInfo {
    private String city;
    private String cityID;
    private String parent;
    private String updateTime;

    String getCityName() {
        switch (city) {
            case "北京市":
            case "天津市":
            case "重庆市":
            case "上海市":
                return city;
            default:
                return parent + city;
        }

    }

}

@Data
class WeatherData {
    private String shidu;
    private int pm25;
    private int pm10;
    private String quality;
    private String wendu;
    private String ganmao;
    private OtherDay yesterday;
    private List<OtherDay> forecast;


    @Override
    public String toString() {
        return "温度：" + wendu + "℃" +
                ", 湿度：" + shidu +
                ", pm25：" + pm25 +
                ", pm10：" + pm10 +
                ", 空气质量：" + quality +
                ", " + ganmao + "。";
    }
}

@Data
class OtherDay {
    private String date;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private int aqi;
    private String ymd;
    private String week;
    private String fx;
    private String fl;
    private String type;
    private String notice;

    String getWeather() {
        return ymd + " " + week + WxMsg.LINE
                + "【天气】 " + type + WxMsg.LINE
                + "【温度】 " + high + " ," + low + WxMsg.LINE
                + "【风力】 " + fx + " " + fl + WxMsg.LINE
                + "【提示】 " + notice + WxMsg.LINE;
    }
}