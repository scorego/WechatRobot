package cache.redis;


import cache.redis.entity.WeatherCacheEntity;
import cache.redis.provider.RCacheEntity;
import utils.DateUtil;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/4 16:29
 */

public class WeatherCacheFactory {

    /**
     * 天气缓存默认3小时。因为天气接口数据3小时更新一次
     */
    private static final int WEATHER_CACHE_DURATION_SECONDS = 60 * 60 * 3;

    public static WeatherCacheEntity getWeatherCacheEntity(String cityName, String date) {

        WeatherCacheEntity.KeyBuilder keyBuilder = new WeatherCacheEntity.KeyBuilder("weather")
                .addParam("city", cityName)
                .addParam("date", date);

        return new WeatherCacheEntity(keyBuilder, WEATHER_CACHE_DURATION_SECONDS);
    }

    public static WeatherCacheEntity getWeatherCacheEntity(String cityName) {
        String today = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
        WeatherCacheEntity.KeyBuilder keyBuilder = new WeatherCacheEntity.KeyBuilder("weather")
                .addParam("city", cityName)
                .addParam("date", today);

        return new WeatherCacheEntity(keyBuilder, WEATHER_CACHE_DURATION_SECONDS);

    }

}
