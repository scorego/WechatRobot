package schedule.base;


import org.apache.commons.lang3.time.StopWatch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/9/8 10:43
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseScheduleTask implements Runnable {

    @Getter
    private String taskName;

    @Override
    public void run() {
        log.info("scheduleTask, taskName: [{}], now is: [{}]", taskName, DateUtil.getCurViewDate());
        StopWatch stopWatch = new StopWatch();
        try {
            process();
        } catch (Exception e) {
            log.error("scheduleTask error, taskName: [{}], ", taskName, e);
        } finally {
            stopWatch.stop();
            log.info("scheduleTask finished, taskName: [{}], cost: [{}] mille seconds.", taskName, stopWatch.getTime());
        }
    }

    protected abstract void process();

}
