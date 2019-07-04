package cache.redis.entity;

import cache.redis.provider.RCacheEntity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/4 16:33
 */
public class WeatherCacheEntity extends RCacheEntity {
    public WeatherCacheEntity(KeyBuilder keyBuilder) {
        super(keyBuilder);
    }

    public WeatherCacheEntity(KeyBuilder keyBuilder, int expireSeconds) {
        super(keyBuilder, expireSeconds);
    }

}
