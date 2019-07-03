package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.GlobalConfig;
import cons.WxMsg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import main.facade.DealMessage;
import me.xuxiaoxiao.chatapi.wechat.WeChatClient;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXContact;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXLocation;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXText;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXVerify;
import org.apache.commons.lang3.StringUtils;
import schedule.EverydayHelloSchedule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 20:06
 */
@Slf4j
public class WechatBot {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private static String REPLY_PREFIX = GlobalConfig.getValue("replyPrefix", "");

    static {
        if (StringUtils.isNotBlank(REPLY_PREFIX)) {
            REPLY_PREFIX += WxMsg.LINE;
        }
    }


    @Getter
    private static WeChatClient weChatClient;

    /**
     * 新建一个微信监听器
     */
    private static final WeChatClient.WeChatListener LISTENER = new WeChatClient.WeChatListener() {
        @Override
        public void onQRCode(@Nonnull WeChatClient client, @Nonnull String qrCode) {
            log.info("onQRCode：{}", qrCode);
//            log.info(QRCodeUtil.printQRCode(qrCode));
        }

        @Override
        public void onLogin(@Nonnull WeChatClient client) {
            log.info("onLogin：您有{}名好友，活跃微信群{}个", client.userFriends().size(), client.userGroups().size());
        }

        @Override
        public void onMessage(@Nonnull WeChatClient client, @Nonnull WXMessage message) {

            EverydayHelloSchedule.startEverydaySchedule();

            log.info("获取到消息：{}", GSON.toJson(message));

            if (message instanceof WXVerify) {
                log.info("收到好友请求消息。来自:{}", message.fromUser.name);
//                client.passVerify((WXVerify) message);
            } else if (message instanceof WXLocation && message.fromUser != null && !message.fromUser.id.equals(client.userMe().id)) {
                if (message.fromGroup != null) {
                    log.info("收到位置消息。来自群: {}，用户: {}", message.fromGroup.name, message.fromUser.name);
//                    // client.sendLocation(message.fromGroup, "120.14556", "30.23856", "我在这里", "西湖");
                } else {
                    log.info("收到位置消息。来自好友: {}", message.fromUser.name);
//                    client.sendLocation(message.fromUser, "120.14556", "30.23856", "我在这里", "西湖");
                }
            } else if (message instanceof WXText && message.fromUser != null && !message.fromUser.id.equals(client.userMe().id)) {
                if (message.fromGroup != null) {
                    log.info("收到文字消息。来自群: {}，用户: {}，内容: {}", message.fromGroup.name, message.fromUser.name, message.content);
                    String response = DealMessage.dealGroupTextMsg(message);
                    if (StringUtils.isNotBlank(response)) {
                        response = REPLY_PREFIX + response;
                        log.info("回复消息，to:{}, content: {}", message.fromGroup.name, response);
                        client.sendText(message.fromGroup, response);
                    }
                } else {
                    log.info("收到文字消息。来自好友: {}", message.fromUser.name);
                    String response = DealMessage.dealFriendTextMsg(message);
                    if (StringUtils.isNotBlank(response)) {
                        response = REPLY_PREFIX + response;
                        log.info("回复消息，to:{}, content: {}", message.fromUser.name, response);
                        client.sendText(message.fromUser, response);
                    }
                }
            }
        }

        @Override
        public void onContact(@Nonnull WeChatClient client, @Nullable WXContact oldContact, @Nullable WXContact newContact) {
            if (oldContact != null && newContact != null && !oldContact.name.equals(newContact.name)) {
                log.info("检测到联系人变更: 旧联系人名称：{}, 新联系人名称：{}", oldContact.name, newContact.name);
            }
        }
    };

    public static void main(String[] args) {
        weChatClient = new WeChatClient();
        weChatClient.setListener(LISTENER);
        weChatClient.startup();
    }
}
