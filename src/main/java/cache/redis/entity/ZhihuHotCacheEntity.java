package cache.redis.entity;

import cache.redis.provider.RCacheEntity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/7 19:06
 */
public class ZhihuHotCacheEntity extends RCacheEntity {
    public ZhihuHotCacheEntity(KeyBuilder keyBuilder) {
        super(keyBuilder);
    }

    public ZhihuHotCacheEntity(KeyBuilder keyBuilder, int expireSeconds) {
        super(keyBuilder, expireSeconds);
    }
}
