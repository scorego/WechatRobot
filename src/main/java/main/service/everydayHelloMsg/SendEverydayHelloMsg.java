package main.service.everydayHelloMsg;

import api.EveryDayHelloApi;
import main.WechatBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 13:01
 */
public class SendEverydayHelloMsg {

    private static final Logger log = LoggerFactory.getLogger(SendEverydayHelloMsg.class);


    public static void SendGroupEverydayHelloMsg() {
        String msg = EveryDayHelloApi.getGroupHelloMsg();
        log.info("Start SendGroupEverydayHelloMsg, msg: {}", msg);
        EveryDayHelloWhiteList.getInstance().getGroupSet().forEach(group -> {
            WechatBot.getWeChatClient().sendText(group, msg);
            log.info("SendGroupEverydayHelloMsg, group: {}", group.name);
        });
        log.info("Finish SendGroupEverydayHelloMsg.");
    }

    public static void SendFriendEverydayHelloMsg(){
        String msg = EveryDayHelloApi.getFriendHelloMsg();
        log.info("Start SendFriendEverydayHelloMsg, msg: {}", msg);
        EveryDayHelloWhiteList.getInstance().getFriendSet().forEach(friend -> {
            WechatBot.getWeChatClient().sendText(friend, msg);
            log.info("SendFriendEverydayHelloMsg, friend: {}", friend.name);
        });
        log.info("Finish SendFriendEverydayHelloMsg.");
    }

}
