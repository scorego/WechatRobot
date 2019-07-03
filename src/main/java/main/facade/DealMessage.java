package main.facade;

import IdentifyCommand.PreProcessMessage;
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
        boolean isCommand = PreProcessMessage.isCommand(message);
        String display = GroupMsgUtil.getUserDisplayOrName(message);
        log.info("群：{}, 用户: {}, 群名片: {}, isCommand: {}, 内容: {}", message.fromGroup.name, message.fromUser.name, display, isCommand, message.content);
        String response;
        if (isCommand) {
            // 是指令的话执行指令
            response = DoCommand.doGroupTextCommand(message);
        } else {
            // 否则白名单的群陪聊
            response = GroupChat.getInstance().dealGroupChatMsg(message);
        }

        if (StringUtils.isNotBlank(response)) {
            String atMePrefix = " @" + display + WxMsg.AT_ME_SPACE + WxMsg.LINE;
            return atMePrefix + response;
        }
        return null;
    }

    public static String dealFriendTextMsg(WXMessage message) {
        log.info("好友：{}, 好友备注: {}，内容: {}", message.fromUser.name, message.fromUser.remark, message.content);
        return FriendChat.dealFriendMsg(message);
    }


}
