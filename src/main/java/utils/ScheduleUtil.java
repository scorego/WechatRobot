package utils;

import java.util.concurrent.TimeUnit;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import schedule.base.BaseScheduleTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/9/8 11:02
 */
@Slf4j
public class ScheduleUtil {

    public static void scheduleAtFixedRate(@NonNull BaseScheduleTask task,
            long initialDelay, long period, TimeUnit timeUnit) {
        if (initialDelay < 0) {
            throw new IllegalArgumentException("initialDelay should not be smaller than zero!");
        }
        if (period <= 0) {
            throw new IllegalArgumentException("period should be bigger than zero!");
        }
        try {
            log.info(
                    "ScheduleUtil.scheduleAtFixedRate, now is [{}], "
                            + "taskName: [{}], initialDelay: [{}], period: [{}], timeUnit: [{}]",
                    DateUtil.getCurViewDate(), task.getTaskName(), initialDelay, period, timeUnit);
            ThreadPoolUtil.getSchedulePool().scheduleAtFixedRate(task, initialDelay, period, timeUnit);
        } catch (Exception e) {
            log.error("ScheduleUtil.scheduleAtFixedRate error, taskName: [{}], ", task.getTaskName(), e);
        }
    }

}
