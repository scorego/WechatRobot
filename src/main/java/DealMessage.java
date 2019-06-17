
import friendMessage.FriendChat;
import groupMessage.GroupChat;
import io.github.biezhi.wechat.api.model.WeChatMessage;

public class DealMessage {


    public static String dealGroupMsg(WeChatMessage message) {
        return GroupChat.getInstance().dealGroupMsg(message);
    }

    public static String dealFriendMsg(WeChatMessage message) {
        return FriendChat.dealFriendMsg(message);
    }


}
