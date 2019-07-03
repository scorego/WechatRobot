package main.service.friendMsg;

import api.ChatApi;
import config.GlobalConfig;
import enums.FriendType;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 14:41
 */
public class FriendChat {

    private static final Logger log = LoggerFactory.getLogger(FriendChat.class);

    private static final boolean autoReplyFriend = GlobalConfig.getValue("autoReplyFriend", "false").equalsIgnoreCase("true");

    private static final String autoReplyFriendMsg = GlobalConfig.getValue("autoReplyFriendMsg", "");

    public static String dealFriendMsg(WXMessage message) {
        FriendType friendType = CheckFriendType.checkFriendType(message.fromUser.name);
        log.info("FriendChat::dealFriendMsg, friend: {}, friendType: {}", message.fromUser.name, friendType);
        switch (friendType) {
            case FRIEND_WHITE:
                return autoReplyFriend ? autoReplyFriend(message) : ChatApi.chat(message);
            case FRIEND_DEFAULT:
                return autoReplyFriend ? autoReplyFriend(message) : null;
            case FRIEND_BLACK:
            default:
                return null;
        }
    }

    private static String autoReplyFriend(WXMessage message) {
        return autoReplyFriendMsg;
    }
}
