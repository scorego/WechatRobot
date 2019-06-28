package main.facade;

import cons.WxMsg;
import main.service.friendMsg.FriendChat;
import main.service.groupMsg.GroupChat;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.GroupMsgUtil;

public class DealMessage {

    private static final Logger log = LoggerFactory.getLogger(DealMessage.class);

    public static String dealGroupTextMsg(WXMessage message) {
        String display = GroupMsgUtil.getUserDisplayOrName(message);
        log.info("群：{}, 用户: {}, 群名片: {}, 内容: {}", message.fromGroup.name, message.fromUser.name, display, message.content);
        String response = GroupChat.getInstance().dealGroupMsg(message);
        if (StringUtils.isNotBlank(response)) {
            String atMePrefix = " @" + display + WxMsg.AT_ME_SPACE + WxMsg.LINE;
            return atMePrefix + response;
        }
        return null;
    }

    public static String dealFriendTextMsg(WXMessage message) {
        log.info("好友：{}, 好友备注: {}，内容: {}", message.fromUser.name, message.fromUser.remark, message.content);
        String response = FriendChat.dealFriendMsg(message);
        if (StringUtils.isNotBlank(response)) {
            // 回复好友不用@对方。这么写是为了以后扩展
            return response;
        }
        return null;
    }


}
