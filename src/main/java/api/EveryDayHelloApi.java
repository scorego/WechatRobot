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

    private static final String EVERYDAY_HELLO = GlobalConfig.getValue("everydayHelloApi", "");

    private static final String DEFAULT_EVERYDAY_HELLO = GlobalConfig.getValue("everydayHello.default", "");

    public static String getGroupHelloMsg() {
        String msg = getMsg();
        if (StringUtils.isBlank(msg)) {
            msg = DEFAULT_EVERYDAY_HELLO;
        }
        String weatherMsg = WeatherApi.getWeatherByCityName("北京");

        return  weatherMsg
                + WxMsg.LINE
                + "【每日一句】" + msg
                + "【详情】输入？？了解更多。" + WxMsg.LINE;
    }

    public static String getFriendHelloMsg(){
        // 暂未启用
        return "";
    }


    private static String getMsg() {
        switch (EVERYDAY_HELLO) {
            case "Ciba":
                return CibaEveryDayHello.getCibaEveryday();
            default:
                return null;
        }
    }

}
