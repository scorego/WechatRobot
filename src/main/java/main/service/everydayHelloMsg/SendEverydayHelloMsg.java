package main.service.everydayHelloMsg;

import api.EveryDayHelloApi;
import lombok.extern.slf4j.Slf4j;
import main.WechatBot;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 13:01
 */
@Slf4j
public class SendEverydayHelloMsg {

    public static void SendGroupEverydayHelloMsg() {
        String msg = EveryDayHelloApi.getGroupHelloMsg();
        log.info("Start SendGroupEverydayHelloMsg, msg: {}", msg);
        if (StringUtils.isNotBlank(msg)) {
            EveryDayHelloWhiteList.getInstance().getGroupSet().forEach(group -> {
                WechatBot.getWeChatClient().sendText(group, msg);
                log.info("SendGroupEverydayHelloMsg, group: {}", group.name);
            });
        }
        log.info("Finish SendGroupEverydayHelloMsg.");
    }

    public static void SendFriendEverydayHelloMsg() {
        String msg = EveryDayHelloApi.getFriendHelloMsg();
        log.info("Start SendFriendEverydayHelloMsg, msg: {}", msg);
        if (StringUtils.isNotBlank(msg)) {
            EveryDayHelloWhiteList.getInstance().getFriendSet().forEach(friend -> {
                WechatBot.getWeChatClient().sendText(friend, msg);
                log.info("SendFriendEverydayHelloMsg, friend: {}", friend.name);
            });
        }
        log.info("Finish SendFriendEverydayHelloMsg.");
    }

}
