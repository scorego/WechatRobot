package cache.redis;

import cache.redis.entity.WeatherCacheEntity;
import cache.redis.entity.ZhihuHotCacheEntity;
import utils.DateUtil;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 16:21
 */
public class ZhihuHotCacheFactory {

    /**
     * 新闻缓存默认1小时。
     */
    private static final int ZHI_HU_HOT_CACHE_DURATION_SECONDS = 60 * 60;
//    private static final int ZHI_HU_HOT_CACHE_DURATION_SECONDS = 60;

    /**
     * 单条新闻缓存时间为30天
     */
    private static final int SINGLE_ZHI_HU_HOT_CACHE_DURATION_SECONDS = 60 * 60 * 24 * 30;

    public static ZhihuHotCacheEntity getZhihuHotCacheEntity(String date, int limit) {

        WeatherCacheEntity.KeyBuilder keyBuilder = new ZhihuHotCacheEntity.KeyBuilder("zhihu")
                .addParam("limit", limit)
                .addParam("date", date);

        return new ZhihuHotCacheEntity(keyBuilder, ZHI_HU_HOT_CACHE_DURATION_SECONDS);
    }

    public static ZhihuHotCacheEntity getZhihuHotCacheEntity(int limit) {
        String today = DateUtil.getCacheFormatDate(new Date());
        WeatherCacheEntity.KeyBuilder keyBuilder = new ZhihuHotCacheEntity.KeyBuilder("zhihu")
                .addParam("limit", limit)
                .addParam("date", today);

        return new ZhihuHotCacheEntity(keyBuilder, ZHI_HU_HOT_CACHE_DURATION_SECONDS);
    }

    public static ZhihuHotCacheEntity getSingleZhihuHotCacheEntity(long newsId) {
        String today = DateUtil.getCacheFormatDate(new Date());
        WeatherCacheEntity.KeyBuilder keyBuilder = new ZhihuHotCacheEntity.KeyBuilder("zhihu")
                .addParam("newsId", newsId);

        return new ZhihuHotCacheEntity(keyBuilder, SINGLE_ZHI_HU_HOT_CACHE_DURATION_SECONDS);
    }
}
