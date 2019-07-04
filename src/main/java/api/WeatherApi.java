package api;

import cache.redis.WeatherCache;
import cache.redis.entity.WeatherCacheEntity;
import config.GlobalConfig;
import config.RedisConfig;
import cons.WxMsg;
import lombok.extern.slf4j.Slf4j;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import robot.QingyunkeRobot.QingyunkeWeather.QingWeather;
import robot.RollToolsApi.RollWeather;

/**
 * 查询天气API
 */

@Slf4j
public class WeatherApi {

    private static final String WEATHER_ROBOT = GlobalConfig.getValue("weatherApi", "");

    private static final boolean REDIS_ENABLE = RedisConfig.isRedisEnable();

    /**
     * 天气缓存默认3小时。因为天气接口数据3小时更新一次
     */
    private static final int WEATHER_CACHE_DURATION_SECONDS = 60 * 60 * 3;


    /**
     * 总入口
     *
     * @param message
     * @return
     */
    public static String dealWeatherMsg(WXMessage message) {
        String keyword = message.content.trim();
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        if ("天气".equals(keyword)) {
            return getFastWeatherCommand(message);
        }
        if (keyword.endsWith("天气")) {
            return getWeatherByKeyword(keyword);
        }
        return null;
    }

    /**
     * 快速查询天气，根据发消息的用户微信名片上的地址发送天气预报
     *
     * @param message
     * @return
     */
    private static String getFastWeatherCommand(WXMessage message) {
        String fromUserCity = message.fromUser.city;
        String response = getWeatherByCityName(fromUserCity);
        return StringUtils.isNotBlank(response) ? response :
                "抱歉，未获取到您所在城市。输入指定市/区/县查天气，如\"北京天气\"。" + WxMsg.LINE;
    }

    /**
     * 根据关键字查询天气接口
     *
     * @param keyword 关键字指以“天气”开头的词，举例：北京天气
     * @return 查询到天气返回天气；未查询到返回抱歉语句。
     */
    public static String getWeatherByKeyword(String keyword) {
        if (StringUtils.isBlank(WEATHER_ROBOT)) {
            return null;
        }
        String cityName = keyword.substring(0, keyword.length() - 2).trim();
        String response = getWeatherByCityName(cityName);
        if (StringUtils.isBlank(response)) {
            response = "抱歉，未查询到\"" + keyword + "\"。" + "只支持查询国内(部分)市/区/县天气。" + WxMsg.LINE;
        }
        log.info("WeatherApi::getWeatherByKeyword, keyword:{}, cityName:{},response:{}", keyword, cityName, response);
        return response;
    }

    /**
     * 根据城市名查询今日天气。未查询到返回null
     *
     * @param cityName
     * @return
     */
    public static String getWeatherByCityName(String cityName) {
        if (!REDIS_ENABLE) {
            return getWeatherFromApi(cityName);
        }

        WeatherCacheEntity weatherCacheEntity = WeatherCache.getWeatherCacheEntity(cityName);

        String result = weatherCacheEntity.get();
        if (result != null) {
            log.info("WeatherApi::getWeatherByCityName, from cache >> cityName: {}, result: {}", cityName, result);
            return result;
        }

        result = getWeatherFromApi(cityName);
        if (weatherCacheEntity.setValue(result).save()) {
            log.info("WeatherApi::getWeatherByCityName, update cache >> cityName: {}, result: {}", cityName, result);
        }
        return result;
    }

    private static String getWeatherFromApi(String cityName) {
        String result;
        switch (WEATHER_ROBOT) {
            case "QingyunkeRobot":
                result = QingWeather.getWeatherByCityName(cityName);
                break;
            case "RollToolsApi":
                result = RollWeather.getWeatherByCityName(cityName);
                break;
            default:
                result = null;
        }
        log.info("WeatherApi:getWeatherFromApi, WEATHER_ROBOT:{}, cityName:{}, result: {}", WEATHER_ROBOT, cityName, result);
        return result;
    }

}
