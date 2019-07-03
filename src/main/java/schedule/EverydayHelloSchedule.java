package schedule;

import config.GlobalConfig;
import main.service.everydayHelloMsg.SendEverydayHelloMsg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DateUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/29 15:01
 */
public class EverydayHelloSchedule {

    private static final Logger log = LoggerFactory.getLogger(EverydayHelloSchedule.class);

    private static final int MILLE_SECONDS_PER_MINUTE = 60 * 1000;

    private static final int MILLE_SECONDS_PER_HOUR = 60 * MILLE_SECONDS_PER_MINUTE;

    private static final int MILLE_SECONDS_PER_DAY = 24 * MILLE_SECONDS_PER_HOUR;

    private static final boolean ENABLE = GlobalConfig.getValue("everydayHello.enable", "false").equalsIgnoreCase("true");

    private static String GROUP_TIME = GlobalConfig.getValue("everydayHello.group.time", "").trim();

    private static long GROUP_PERIOD = MILLE_SECONDS_PER_DAY;

    private static volatile boolean isScheduled = false;

    static {
        if (StringUtils.isBlank(GROUP_TIME) || GROUP_TIME.length() != 6) {
            log.info("每日一句定时调度，配置everydayHello.group.time失效，设为默认值083000.");
            GROUP_TIME = "083000";
        }

        String GROUP_PERIOD_CONFIG = GlobalConfig.getValue("everydayHello.group.period.hour", "").trim();
        if (StringUtils.isNotBlank(GROUP_PERIOD_CONFIG)) {
            try {
                GROUP_PERIOD = Long.parseLong(GROUP_PERIOD_CONFIG) * MILLE_SECONDS_PER_HOUR;
                // 调试用，以分钟为单位
//                GROUP_PERIOD = Long.parseLong(GROUP_PERIOD_CONFIG) * 60 * 1000;
            } catch (NumberFormatException e) {
                log.error("startEverydaySchedule解析period失败。GROUP_PERIOD_CONFIG: {}", GROUP_PERIOD_CONFIG);
            }
        }

    }

    public static void startEverydaySchedule() {
        if (!ENABLE) {
            return;
        }
        if (isScheduled) {
            return;
        }

        Timer timer = new Timer(true);
        Date curDate = new Date();
        String curTime = DateUtil.getFormatDate(curDate, "HHmmss");
        Date scheduleDate = DateUtil.parseDate(DateUtil.getFormatDate(curDate, "yyyyMMdd") + GROUP_TIME, "yyyyMMddHHmmss");
        if (scheduleDate == null) {
            log.error("解析scheduleDate失败。");
            return;
        }
        if (curTime != null && GROUP_TIME.compareTo(curTime) <= 0) {
            scheduleDate = DateUtil.addOneDay(scheduleDate);
        }

        log.info("EverydayHelloSchedule::startEverydaySchedule, schedule >> scheduleDate: {}, period: {}分钟", scheduleDate, GROUP_PERIOD / MILLE_SECONDS_PER_MINUTE);
        timer.schedule(new EverydayHelloTask(), scheduleDate, GROUP_PERIOD);
        isScheduled = true;
    }

    private static class EverydayHelloTask extends TimerTask {
        @Override
        public void run() {
            log.info("EverydayHelloSchedule::EverydayHelloTask.run(), now is {}", DateUtil.getYYMMDDHHMMSSDate(new Date()));
            SendEverydayHelloMsg.SendGroupEverydayHelloMsg();
        }
    }


}
