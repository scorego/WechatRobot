package cache.redis;

import cache.redis.entity.RubbishLinkCacheEntity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/5 13:39
 */
public class RubbishLinkCacheFactory {

    private static final int RUBBISH_LINK_CACHE_DURATION_SECONDS = 0;

    public static RubbishLinkCacheEntity getRubbishLinkCache(String rubbish) {
        RubbishLinkCacheEntity.KeyBuilder KeyBuilder = new RubbishLinkCacheEntity.KeyBuilder("rubbishLink").addParam("item", rubbish);
        return new RubbishLinkCacheEntity(KeyBuilder, RUBBISH_LINK_CACHE_DURATION_SECONDS);
    }

}
