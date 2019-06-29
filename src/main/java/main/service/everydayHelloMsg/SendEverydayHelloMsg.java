package main.service.everydayHelloMsg;

import api.EveryDayHelloApi;
import main.WechatBot;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXGroup;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXUser;
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
        String msg = EveryDayHelloApi.getEverydayHello();
        log.info("Start SendGroupEverydayHelloMsg, msg: {}", msg);
        EveryDayHelloWhiteList.getInstance().getGroupSet().forEach(group -> {
            WechatBot.getWeChatClient().sendText(group, msg);
            log.info("SendGroupEverydayHelloMsg, group: {}", group.name);
        });
        log.info("Finish SendGroupEverydayHelloMsg.");
    }

    public static void SendFriendEverydayHelloMsg(){
        // todo
    }

}
