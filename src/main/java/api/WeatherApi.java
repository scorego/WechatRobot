package api;

import config.GlobalConfig;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robot.QingyunkeRobot.QingWeather;
import io.github.biezhi.wechat.utils.StringUtils;
import robot.RollToolsApi.RollWeather;

/**
 * 查询天气API
 */
public class WeatherApi {

    private static final Logger log = LoggerFactory.getLogger(WeatherApi.class);

    private static final String WEATHER_ROBOT = GlobalConfig.getValue("chatRobot", "");

    /**
     * 根据关键字查询天气接口
     *
     * @param keyword 关键字指以“天气”开头和结尾的词，举例：北京天气、天气北京
     * @return 查询到天气返回天气；未查询到返回抱歉语句。
     */
    public static String getWeatherByKeyword(String keyword) {
        if (WEATHER_ROBOT.equals("") || StringUtils.isEmpty(keyword)) {
            return null;
        }
        String cityName = keyword.startsWith("天气") ? keyword.substring(2) : keyword.substring(0, keyword.length() - 2);
        String response =getWeatherByCityName(cityName);
        if (StringUtils.isEmpty(response)) {
            response = "抱歉，未查询到\"" + keyword + "\"。" + "只支持查询国内(部分)市、区、县天气。";
        }
        log.info("WeatherApi::getWeatherByKeyword, keyword:{}, cityName:{},response:{}", keyword, cityName, response);
        return response;
    }

    private static String getWeatherByCityName(String cityName) {
        log.info("WeatherApi:getWeatherByCityName, WEATHER_ROBOT:{}, cityName:{}", WEATHER_ROBOT, cityName);
        switch (WEATHER_ROBOT) {
            case "QingWeather":
                return QingWeather.getWeatherByCityName(cityName);
            case "RollWeather":
                return RollWeather.getWeatherByCityName(cityName);
            default:
                return null;
        }
    }


    public static String dealWeatherMsg(WeChatMessage message){
        String keyword = message.getText();
        if (StringUtils.isEmpty(keyword)){
            return null;
        }
        if ("天气预报".equals(keyword)){
            return getWeatherByCityName("北京") + "输入指定市/区/县查天气。";
        }
        if (keyword.startsWith("天气") || keyword.endsWith("天气")) {
            return getWeatherByKeyword(keyword);
        }
        return null;
    }
}
