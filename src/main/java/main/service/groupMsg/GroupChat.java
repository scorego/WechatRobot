package main.service.groupMsg;

import api.ChatApi;
import lombok.extern.slf4j.Slf4j;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class GroupChat {

    private static volatile GroupChat INSTANCE;

    private GroupChat() {
    }

    public static GroupChat getInstance() {
        if (INSTANCE == null) {
            synchronized (GroupChat.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GroupChat();
                }
            }
        }
        return INSTANCE;
    }


    public String dealGroupChatMsg(WXMessage message) {
        String groupName = message.fromGroup.name;
        switch (CheckGroupType.checkGroupType(groupName)) {
            case GROUP_WHITELIST:
                return dealChatMsg(message);
            case GROUP_DEFAULT:
            case GROUP_MODE_ONLY:
            case GROUP_NOT_EXISTS:
            case GROUP_BLACKLIST:
            default:
                return null;
        }
    }


    private String dealChatMsg(WXMessage message) {
        String keyword = message.content;
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return ChatApi.chat(message);
    }
}
