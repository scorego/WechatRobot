package api;

import config.GlobalConfig;
import cons.WxMsg;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robot.QingyunkeRobot.QingyunkeWeather.QingWeather;
import robot.RollToolsApi.RollWeather;

/**
 * 查询天气API
 */
public class WeatherApi {

    private static final Logger log = LoggerFactory.getLogger(WeatherApi.class);

    private static final String WEATHER_ROBOT = GlobalConfig.getValue("weatherApi", "");

    public static String dealWeatherMsg(WXMessage message) {
        String keyword = message.content.trim();
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        if ("天气预报".equals(keyword) || "天气".equals(keyword)) {
            String fromUserCity = message.fromUser.city;
            String response = getWeatherByCityName(fromUserCity);
            return StringUtils.isBlank(response) ?
                    "抱歉，未获取到您所在城市(" + fromUserCity + ")天气。输入指定市/区/县查天气，如\"北京天气\"。" + WxMsg.LINE : response;
        }
        if (keyword.startsWith("天气") || keyword.endsWith("天气")) {
            return getWeatherByKeyword(keyword);
        }
        return null;
    }


    /**
     * 根据关键字查询天气接口
     *
     * @param keyword 关键字指以“天气”开头和结尾的词，举例：北京天气、天气北京
     * @return 查询到天气返回天气；未查询到返回抱歉语句。
     */
    public static String getWeatherByKeyword(String keyword) {
        if (WEATHER_ROBOT.equals("")) {
            return null;
        }
        String cityName = keyword.startsWith("天气") ? keyword.substring(2).trim() : keyword.substring(0, keyword.length() - 2).trim();
        String response = getWeatherByCityName(cityName);
        if (StringUtils.isBlank(response)) {
            response = "抱歉，未查询到\"" + keyword + "\"。" + "只支持查询国内(部分)市、区、县天气。" + WxMsg.LINE;
        }
        log.info("WeatherApi::getWeatherByKeyword, keyword:{}, cityName:{},response:{}", keyword, cityName, response);
        return response;
    }

    public static String getWeatherByCityName(String cityName) {
        log.info("WeatherApi:getWeatherByCityName, WEATHER_ROBOT:{}, cityName:{}", WEATHER_ROBOT, cityName);
        switch (WEATHER_ROBOT) {
            case "QingyunkeRobot":
                return QingWeather.getWeatherByCityName(cityName);
            case "RollToolsApi":
                return RollWeather.getWeatherByCityName(cityName);
            default:
                return null;
        }
    }



}
