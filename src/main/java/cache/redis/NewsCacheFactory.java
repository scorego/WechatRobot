package cache.redis;

import cache.redis.entity.NewsCacheEntity;
import cache.redis.entity.WeatherCacheEntity;
import utils.DateUtil;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 16:21
 */
public class NewsCacheFactory {

    /**
     * 新闻缓存默认1小时。
     */
    private static final int NEWS_CACHE_DURATION_SECONDS = 60 * 60;

    /**
     * 单条新闻缓存时间为24小时
     */
    private static final int SINGLE_NEWS_CACHE_DURATION_SECONDS = 60 * 60 * 24;

    public static NewsCacheEntity getNewsCacheEntity(String typeId, String date, int limit) {

        WeatherCacheEntity.KeyBuilder keyBuilder = new NewsCacheEntity.KeyBuilder("news")
                .addParam("typeId", typeId)
                .addParam("limit", limit)
                .addParam("date", date);

        return new NewsCacheEntity(keyBuilder, NEWS_CACHE_DURATION_SECONDS);
    }

    public static NewsCacheEntity getNewsCacheEntity(String typeId, int limit) {
        String today = DateUtil.getCacheFormatDate(new Date());
        WeatherCacheEntity.KeyBuilder keyBuilder = new NewsCacheEntity.KeyBuilder("news")
                .addParam("typeId", typeId)
                .addParam("limit", limit)
                .addParam("date", today);

        return new NewsCacheEntity(keyBuilder, NEWS_CACHE_DURATION_SECONDS);
    }

    public static NewsCacheEntity getSingleNewsCacheEntity(String newsId) {
        String today = DateUtil.getCacheFormatDate(new Date());
        WeatherCacheEntity.KeyBuilder keyBuilder = new NewsCacheEntity.KeyBuilder("news")
                .addParam("newsId", newsId);

        return new NewsCacheEntity(keyBuilder, SINGLE_NEWS_CACHE_DURATION_SECONDS);
    }
}
