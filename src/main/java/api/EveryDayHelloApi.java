package api;

import config.GlobalConfig;
import cons.WxMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import robot.Ciba.CibaEveryDayHello;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 12:27
 */
@Slf4j
public class EveryDayHelloApi {

    private static final String EVERYDAY_HELLO_API = GlobalConfig.getValue("everydayHelloApi", "");

    private static final String EVERYDAY_HELLO_WEATHER_CITY = GlobalConfig.getValue("everydayHello.weather.city", "北京");

    private static String DEFAULT_EVERYDAY_HELLO = GlobalConfig.getValue("everydayHello.default", "");

    static {
        if (StringUtils.isNotBlank(DEFAULT_EVERYDAY_HELLO)) {
            DEFAULT_EVERYDAY_HELLO += WxMsg.LINE;
        }
    }

    public static String getGroupHelloMsg() {
        String msg = getMsg();

        String weatherMsg = WeatherApi.getWeatherByCityName(EVERYDAY_HELLO_WEATHER_CITY);
        if (StringUtils.isBlank(weatherMsg)) {
            weatherMsg = WeatherApi.getWeatherByCityName("北京");
        }

        return msg
                + "-------" + WxMsg.LINE
                + weatherMsg + "【详情】发送？？了解更多" + WxMsg.LINE;
    }

    public static String getFriendHelloMsg() {
        // 暂未启用
        return "";
    }


    private static String getMsg() {
        String result;
        switch (EVERYDAY_HELLO_API) {
            case "Ciba":
                result = CibaEveryDayHello.getCibaEveryday();
                break;
            default:
                result = null;
        }
        if (StringUtils.isBlank(result)) {
            result = DEFAULT_EVERYDAY_HELLO;
        }
        return result;
    }

}
