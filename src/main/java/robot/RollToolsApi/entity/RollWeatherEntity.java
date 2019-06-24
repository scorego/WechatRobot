package robot.RollToolsApi.entity;

import cons.WxMsg;
import lombok.Data;

@Data
public class RollWeatherEntity {

    private static final int SUCCESS = 1;

    private int code;

    private String msg;

    private RollWeatherData data;

    public boolean isValid() {
        return code == SUCCESS;
    }

    public String getWeather() {
        return data.toString();
    }

    public String getWeatherDefaultNull() {
        return isValid() ? data.toString() : null;
    }
}

@Data
class RollWeatherData {
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
        return address + WxMsg.LINE
                + "【天气】 " + weather + WxMsg.LINE
                + "【温度】" + temp + WxMsg.LINE
                + "【湿度】" + humidity + WxMsg.LINE
                + "【风力】" + windDirection + "风  " + windPower + WxMsg.LINE
                + "【更新时间】" + reportTime + WxMsg.LINE;
    }
}
