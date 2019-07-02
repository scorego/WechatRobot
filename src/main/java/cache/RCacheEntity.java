package cache;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/2 20:20
 */
public class RCacheEntity {

    private String value;

    @Setter
    @Getter
    private KeyBuilder keyBuilder;

    /**
     * 默认缓存时间是24小时
     */
    @Getter
    @Setter
    private int expire = 60 * 60 * 24;

    private BaseRedisCache baseRedisCache = BaseRedisCache.getInstance();

    public boolean save() {
        if (value == null) {
            return false;
        }
        return baseRedisCache.save(getKey(), String.valueOf(value), expire);
    }

    public String get() {
        return baseRedisCache.get(getKey());
    }

    public boolean del() {
        return baseRedisCache.del(getKey());
    }


    public RCacheEntity(KeyBuilder keyBuilder) {
        this.keyBuilder = keyBuilder;
    }

    public RCacheEntity(KeyBuilder keyBuilder, int expireSeconds) {
        this.keyBuilder = keyBuilder;
        this.expire = expireSeconds;
    }


    public RCacheEntity setValue(String value) {
        this.value = value;
        return this;
    }

    private String getKey() {
        return keyBuilder.build();
    }


    public static class KeyBuilder {
        Map<String, Object> keyMap = new TreeMap<>();
        @Setter
        String name;
        @Setter
        String key;

        public KeyBuilder(String name) {
            this.name = name;
        }

        public KeyBuilder addParam(String name, Object object) {
            keyMap.put(name, object);
            return this;
        }

        public String build() {
            StringBuilder result = new StringBuilder();
            if (StringUtils.isEmpty(key)) {
                for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
                    result.append(":").append(entry.getKey()).append(":").append(entry.getValue());
                }
                result.insert(0, this.name);
            } else {
                result = new StringBuilder(key);
            }
            return result.toString();
        }
    }


}
