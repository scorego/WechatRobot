package cache.redis.entity;

import cache.redis.provider.RCacheEntity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/5 13:38
 */
public class RubbishLinkListEntity extends RCacheEntity {

    public RubbishLinkListEntity(KeyBuilder keyBuilder) {
        super(keyBuilder);
    }

    public RubbishLinkListEntity(KeyBuilder keyBuilder, int expireSeconds) {
        super(keyBuilder, expireSeconds);
    }



}
