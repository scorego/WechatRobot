package robot.RollToolsApi.entity;

import lombok.Data;

@Data
public class RollWeatherEntity {

    private static final int SUCCESS = 1;

    private int code;

    private String msg;

    private RollWeatherData data;

    public boolean isValid(){
        return code == SUCCESS;
    }

    public String getWeather(){
        return data.toString();
    }
    public String getWeatherDefaultNull(){
        return isValid() ? data.toString() : null;
    }
}

@Data
class RollWeatherData{
    private String address;
    private String cityCode;
    private String temp;
    private String weather;
    private String windDirection;
    private String windPower;
    private String humidity;
    private String reportTime;

    @Override
    public String toString() {
        return address + "今日天气: " +
                ", 温度" + temp  +
                ", " + weather +
                ", " + windDirection + '风' +
                ", 风力" + windPower  +
                ", 湿度" + humidity  +
                ", 更新时间" + reportTime  + "。";
    }
}
