package schedule.task;

import org.apache.commons.lang3.StringUtils;

import api.EveryDayHelloApi;
import lombok.extern.slf4j.Slf4j;
import main.WechatBotClient;
import main.service.everydayHelloMsg.EveryDayHelloWhiteList;
import schedule.base.BaseScheduleTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/9/18 11:17
 */
@Slf4j
public class FriendEverydayTask extends BaseScheduleTask {
    public FriendEverydayTask() {
        super("friendEverydayTask");
    }

    public void process() {
        String msg = EveryDayHelloApi.getFriendHelloMsg();
        log.info("friendEverydayHelloMsg, msg: {}", msg);
        if (StringUtils.isNotBlank(msg)) {
            EveryDayHelloWhiteList.getInstance().getFriendSet().forEach(friend -> {
                WechatBotClient.getWeChatClient().sendText(friend, msg);
                log.info("SendFriendEverydayHelloMsg, friend: {}", friend.name);
            });
        }
        log.info("Finish SendFriendEverydayHelloMsg.");
    }
}
