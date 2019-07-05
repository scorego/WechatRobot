package cache.redis;

import cache.redis.entity.RubbishCacheEntity;
import lombok.NonNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/4 16:04
 */

public class RubbishTypeCacheFactory {


    /**
     * 缓存key过期时间。<= 0为不过期
     */
    private static final int RUBBISH_CACHE_DURATION_SECONDS = 0;

    public static RubbishCacheEntity getRubbishCacheEntity(@NonNull String rubbish) {
        RubbishCacheEntity.KeyBuilder KeyBuilder = new RubbishCacheEntity.KeyBuilder("rubbishType").addParam("item", rubbish);
        return new RubbishCacheEntity(KeyBuilder, RUBBISH_CACHE_DURATION_SECONDS);
    }

}
