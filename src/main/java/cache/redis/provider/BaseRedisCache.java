package cache.redis.provider;

import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/2 20:30
 */
public class BaseRedisCache {

    private static volatile BaseRedisCache INSTANCE;

    public static BaseRedisCache getInstance() {
        if (INSTANCE == null) {
            synchronized (BaseRedisCache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaseRedisCache();
                }
            }
        }
        return INSTANCE;
    }

    private BaseRedisCache() {
    }

    private final static String REDIS_OK = "OK";


    public boolean setex(String key, String value, int seconds) {
        Jedis jedis = JedisPoolUtil.getJedis();
        String result = jedis.setex(key, seconds, value);
        jedis.close();
        return REDIS_OK.equals(result);
    }

    public boolean set(String key, String value) {
        Jedis jedis = JedisPoolUtil.getJedis();
        String result = jedis.set(key, value);
        jedis.close();
        return REDIS_OK.equals(result);
    }

    public String get(String key) {
        Jedis jedis = JedisPoolUtil.getJedis();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    public boolean del(String key) {
        Jedis jedis = JedisPoolUtil.getJedis();
        Long result = jedis.del(key);
        jedis.close();
        return result != null && result > 0;
    }

}
