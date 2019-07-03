package api;

import cache.RCacheEntity;
import config.GlobalConfig;
import config.RedisConfig;
import cons.WxMsg;
import lombok.extern.slf4j.Slf4j;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import robot.QingyunkeRobot.QingyunkeWeather.QingWeather;
import robot.RollToolsApi.RollWeather;
import utils.DateUtil;

import java.util.Date;

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

    public static String dealWeatherMsg(WXMessage message) {
        String keyword = message.content.trim();
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        if ("天气预报".equals(keyword) || "天气".equals(keyword)) {
            String fromUserCity = message.fromUser.city;
            String response = getWeatherByCityName(fromUserCity);
            return StringUtils.isBlank(response) ?
                    "抱歉，未获取到您所在城市。输入指定市/区/县查天气，如\"北京天气\"。" + WxMsg.LINE : response;
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
        if (!REDIS_ENABLE) {
            return getWeatherWithoutCache(cityName);
        }

        // 1. 构造缓存key
        String curDate = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
        RCacheEntity.KeyBuilder rubbishTypeKeyBuilder = new RCacheEntity.KeyBuilder("weather")
                .addParam("city", cityName)
                .addParam("date", curDate);
        RCacheEntity rCacheEntity = new RCacheEntity(rubbishTypeKeyBuilder, WEATHER_CACHE_DURATION_SECONDS);

        // 2. 有缓存，更新缓存过期时间，返回缓存结果
        String result;
        if ((result = rCacheEntity.get()) != null) {
            rCacheEntity.save();
            log.info("WeatherApi::getWeatherByCityName, cache >> cityName: {}, date: {}, result: {}", cityName, curDate, result);
            return result;
        }

        // 3. 无缓存，查询结果，存入缓存
        result = getWeatherWithoutCache(cityName);
        rCacheEntity.setValue(result).save();

        return result;


    }

    private static String getWeatherWithoutCache(String cityName) {
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
        log.info("WeatherApi:getWeatherWithoutCache, WEATHER_ROBOT:{}, cityName:{}, result: {}", WEATHER_ROBOT, cityName, result);
        return result;
    }

}
