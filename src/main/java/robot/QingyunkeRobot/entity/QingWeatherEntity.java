package robot.QingyunkeRobot.entity;

import lombok.Data;

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
        return cityInfo.getCity()
                + "今日天气:" + data.toString()
                + "更新时间:" + time.substring(11);
    }
}

@Data
class CityInfo {
    private String city;
    private String cityID;
    private String parent;
    private String updateTime;
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
}