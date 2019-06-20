package main;

import IdentifyCommand.PreProcessMessage;
import api.EveryDayHelloApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.GlobalConfig;
import cons.WxMsg;
import main.facade.DealMessage;
import main.facade.DoCommand;
import me.xuxiaoxiao.chatapi.wechat.WeChatClient;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXContact;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXGroup;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXLocation;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXText;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXVerify;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.GroupMsgUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 20:06
 */
public class WechatBot {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private static final String REPLY_PREFIX = GlobalConfig.getValue("replyPrefix", "");

    private static final Logger log = LoggerFactory.getLogger(WechatBot.class);

    /**
     * 新建一个微信监听器
     */
    private static final WeChatClient.WeChatListener LISTENER = new WeChatClient.WeChatListener() {
        @Override
        public void onQRCode(@Nonnull WeChatClient client, @Nonnull String qrCode) {
            log.info("onQRCode：{}", qrCode);
        }

        @Override
        public void onLogin(@Nonnull WeChatClient client) {
            log.info("onLogin：您有{}名好友，活跃微信群{}个", client.userFriends().size(), client.userGroups().size());
            WXGroup group = client.userGroups().getOrDefault("EverydayWechat 交流群", null);
            if (group != null) {
                client.sendText(group, EveryDayHelloApi.getEverydayHello());
            }
        }

        @Override
        public void onMessage(@Nonnull WeChatClient client, @Nonnull WXMessage message) {
            log.info("获取到消息：{}", GSON.toJson(message));

            if (message instanceof WXVerify) {
                //是好友请求消息
                log.info("判定为好友请求消息。来自:{}", message.fromUser.name);
//                同意好友请求
//                client.passVerify((WXVerify) message);
            } else if (message instanceof WXLocation && message.fromUser != null && !message.fromUser.id.equals(client.userMe().id)) {
                // 位置消息
                if (message.fromGroup != null) {
                    // 群消息
                    log.info("判定为位置消息。来自群: {}，用户: {}", message.fromGroup.name, message.fromUser.name);
//                    // client.sendLocation(message.fromGroup, "120.14556", "30.23856", "我在这里", "西湖");
                } else {
                    // 用户消息
                    log.info("判定为位置消息。来自用户: {}", message.fromUser.name);
                    client.sendLocation(message.fromUser, "120.14556", "30.23856", "我在这里", "西湖");
                }
            } else if (message instanceof WXText && message.fromUser != null && !message.fromUser.id.equals(client.userMe().id)) {
                //是文字消息，并且发送消息的人不是自己

                if (message.fromGroup != null) {
                    boolean isCommand = PreProcessMessage.isCommand(message);
                    String display = GroupMsgUtil.getUserDisplayOrName(message);
                    log.info("判定为文字消息。来自于群：{}, 用户: {}, 群名片: {}, isCommand: {}, 内容: {}", message.fromGroup.name, message.fromUser.name, display, isCommand, message.content);
                    if (isCommand) {
                        PreProcessMessage.removeCommandFix(message);
                        String response = DoCommand.doGroupCommand(message);
                        if (StringUtils.isNotBlank(response)) {
                            String atMePrefix = " @" + display + WxMsg.AT_ME_SPACE + WxMsg.LINE;
                            response = REPLY_PREFIX + atMePrefix + response;
                            log.info("回复消息，to:{}, content: {}", message.fromGroup.name, response);
                            client.sendText(message.fromGroup, response);
                        }
                    }
                } else {
                    log.info("判定为文字消息。来自于好友：{}, 好友备注: {}，内容: {}", message.fromUser.name, message.fromUser.remark, message.content);
                    String response = DealMessage.dealFriendMsg(message);
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
            log.info("检测到联系人变更: 旧联系人名称：{}, 新联系人名称：{}", (oldContact == null ? "null" : oldContact.name), (newContact == null ? "null" : newContact.name));
        }
    };

    public static void main(String[] args) {
        //新建一个模拟微信客户端
        WeChatClient wechatClient = new WeChatClient();
        //为模拟微信客户端设置监听器
        wechatClient.setListener(LISTENER);
        //启动模拟微信客户端
        wechatClient.startup();
    }
}
