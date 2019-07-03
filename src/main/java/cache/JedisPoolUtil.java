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


    /**
     * 获取一个Jedis 对象
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

    private static void createJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(10);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(1000 * 10);

        // 创建连接池
        pool = new JedisPool(config, HOST, PORT);
    }


    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool();
    }


}
