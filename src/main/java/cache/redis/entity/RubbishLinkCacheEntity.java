package cache.redis.entity;

import cache.redis.provider.RCacheEntity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/5 13:38
 */
public class RubbishLinkCacheEntity extends RCacheEntity {

    public RubbishLinkCacheEntity(KeyBuilder keyBuilder) {
        super(keyBuilder);
    }

    public RubbishLinkCacheEntity(KeyBuilder keyBuilder, int expireSeconds) {
        super(keyBuilder, expireSeconds);
    }



}
