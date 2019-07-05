package cache.redis;

import cache.redis.entity.RubbishCacheEntity;
import cache.redis.entity.RubbishLinkListEntity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/5 13:39
 */
public class RubbishLinkListCache {

    private static int RUBBISH_LINK_CACHE_DURATION_SECONDS = 0;

    public static RubbishLinkListEntity getRubbishLinkListCache(String rubbish) {
        RubbishLinkListEntity.KeyBuilder KeyBuilder = new RubbishLinkListEntity.KeyBuilder("rubbishLink").addParam("item", rubbish);
        return new RubbishLinkListEntity(KeyBuilder, RUBBISH_LINK_CACHE_DURATION_SECONDS);
    }

}
