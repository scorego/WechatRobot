package main.service.groupMsg;

import api.ChatApi;
import enums.GroupType;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AtMeMsg;


public class GroupChat {

    private static final Logger log = LoggerFactory.getLogger(GroupChat.class);

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
        GroupType groupType = CheckGroupType.checkGroupType(groupName);
        log.info("GroupChat::dealGroupChatMsg, 群:{}, GroupType:{}", groupName, groupType);
        switch (groupType) {
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
