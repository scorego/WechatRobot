package utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/9/7 13:03
 */
@Slf4j
public class ThreadPoolUtil {

    private static volatile ThreadPoolExecutor cachedPool;

    private static volatile ScheduledThreadPoolExecutor schedulePool;

    public static ThreadPoolExecutor getCachedPool() {
        if (cachedPool == null) {
            synchronized (ThreadPoolUtil.class) {
                if (cachedPool == null) {
                    cachedPool = new ThreadPoolExecutor(0,
                            200,
                            60,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(),
                            Executors.defaultThreadFactory(),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
        return cachedPool;
    }

    public static ScheduledThreadPoolExecutor getSchedulePool() {
        if (schedulePool == null) {
            synchronized (ThreadPoolUtil.class) {
                if (schedulePool == null) {
                    schedulePool = new ScheduledThreadPoolExecutor(100, new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
        return schedulePool;
    }

}
