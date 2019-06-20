package main.facade;

import main.Service.friendMsg.FriendChat;
import main.Service.groupMsg.GroupChat;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;

public class DealMessage {


    public static String dealGroupMsg(WXMessage message) {

        return GroupChat.getInstance().dealGroupMsg(message);
    }

    public static String dealFriendMsg(WXMessage message) {
        return FriendChat.dealFriendMsg(message);
    }


}
