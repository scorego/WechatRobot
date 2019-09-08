package schedule.task;


import org.apache.commons.lang3.StringUtils;

import api.EveryDayHelloApi;
import lombok.extern.slf4j.Slf4j;
import main.WechatBot;
import main.service.everydayHelloMsg.EveryDayHelloWhiteList;
import schedule.base.BaseScheduleTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/9/8 10:52
 */
@Slf4j
public class GroupEverydayTask extends BaseScheduleTask {
    public GroupEverydayTask() {
        super("GroupEverydayTask");
    }

    public void process() {
        String msg = EveryDayHelloApi.getGroupHelloMsg();
        log.info("GroupEverydayHelloMsg msg: {}", msg);
        if (StringUtils.isNotBlank(msg)) {
            EveryDayHelloWhiteList.getInstance().getGroupSet().forEach(group -> {
                log.info("SendGroupEverydayHelloMsg, group: {}", group.name);
                WechatBot.getWeChatClient().sendText(group, msg);
            });
        }
        log.info("Finish SendGroupEverydayHelloMsg.");
    }
}
