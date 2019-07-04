package cache.redis.entity;

import cache.redis.provider.RCacheEntity;
import enums.RubbishType;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/4 16:07
 */
public class RubbishCacheEntity extends RCacheEntity {

    public RubbishCacheEntity(KeyBuilder keyBuilder) {
        super(keyBuilder);
    }

    public RubbishCacheEntity(KeyBuilder keyBuilder, int expireSeconds) {
        super(keyBuilder, expireSeconds);
    }

    public RubbishType getRubbishType() {

        String stringType = get();
        if (stringType == null) {
            return null;
        }
        return RubbishType.findByValue(Integer.valueOf(stringType));
    }

    public RubbishCacheEntity setValue(RubbishType rubbishType) {
        this.setValue(String.valueOf(rubbishType.getValue()));
        return this;
    }

}
