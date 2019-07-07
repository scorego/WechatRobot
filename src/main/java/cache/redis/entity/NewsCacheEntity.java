package cache.redis.entity;

import cache.redis.provider.RCacheEntity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 16:20
 */
public class NewsCacheEntity  extends RCacheEntity {
    public NewsCacheEntity(KeyBuilder keyBuilder) {
        super(keyBuilder);
    }

    public NewsCacheEntity(KeyBuilder keyBuilder, int expireSeconds) {
        super(keyBuilder, expireSeconds);
    }
}
