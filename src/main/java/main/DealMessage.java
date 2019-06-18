package main;

import friendMessage.FriendChat;
import groupMessage.GroupChat;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;

public class DealMessage {


    public static String dealGroupMsg(WXMessage message) {
        return GroupChat.getInstance().dealGroupMsg(message);
    }

    public static String dealFriendMsg(WXMessage message) {
        return FriendChat.dealFriendMsg(message);
    }


}
