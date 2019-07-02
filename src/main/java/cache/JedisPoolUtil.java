package cache;

import config.RedisConfig;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/2 21:36
 */
public class JedisPoolUtil {

    private final static String HOST = RedisConfig.getValue("redis.host", "");

    private final static int PORT = Integer.valueOf(RedisConfig.getValue("redis.port", "6379"));

    private final static String PASSWORD = RedisConfig.getValue("redis.password", "");


    private static JedisPool pool;

    private static void createJedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();

        // 设置空间连接
        config.setMaxTotal(10);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(1000 * 10);

        // 创建连接池
        pool = new JedisPool(config, HOST, PORT);

    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool();
    }

    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getJedis() {

        if (pool == null)
            poolInit();
        Jedis resource = pool.getResource();
        if (StringUtils.isNotBlank(PASSWORD)){
            resource.auth(PASSWORD);
        }
        return resource;
    }

    /**
     * 归还一个连接
     *
     * @param jedis
     */
    public static void returnRes(Jedis jedis) {
        JedisPoolUtil.returnRes(jedis);
    }

}
