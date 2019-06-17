package friendMessage;

import api.ChatApi;
import config.GlobalConfig;
import io.github.biezhi.wechat.api.model.WeChatMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 14:41
 */
public class FriendChat {

    private static final boolean autoReplyFriend = GlobalConfig.getValue("autoReplyFriend", "false").equalsIgnoreCase("true");

    private static final String autoReplyFriendMsg = GlobalConfig.getValue("autoReplyFriendMsg", "");

    public static String dealFriendMsg(WeChatMessage message) {
        if (autoReplyFriend) {
            return autoReplyFriend(message);
        }
        switch (CheckFriendType.checkFriendType(message.getFromNickName())) {
            case FRIEND_WHITE:
                return ChatApi.chat(message);
            case FRIEND_DEFAULT:
            case FRIEND_BLACK:
            default:
                return null;
        }
    }

    private static String autoReplyFriend(WeChatMessage message) {
        return autoReplyFriendMsg;
    }
}
