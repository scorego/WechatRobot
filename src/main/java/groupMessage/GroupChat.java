package groupMessage;

import api.ChatApi;
import api.WeatherApi;
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


    public String dealGroupMsg(WXMessage message) {
        String groupName = message.fromGroup.name;
        GroupType groupType = CheckGroupType.checkGroupType(groupName);
        log.info("GroupChat::dealGroupMsg, 群:{}, GroupType:{}",groupName, groupType);
        switch (groupType) {
            case GROUP_WHITELIST:
                return dealAllMsg(message);
            case GROUP_DEFAULT:
            case GROUP_WEATHER_ONLY:
                return dealWeatherQueryMsg(message);
            case GROUP_NOT_EXISTS:
            case GROUP_BLACKLIST:
            default:
                return null;
        }
    }


    private String dealWeatherQueryMsg(WXMessage message) {
        String keyword = AtMeMsg.removeAtFix(message);
        if (keyword != null && (keyword.startsWith("天气") || keyword.endsWith("天气"))) {
            return WeatherApi.dealWeatherMsg(message);
        }
        return null;
    }

    private String dealAllMsg(WXMessage message) {
        String keyword = AtMeMsg.removeAtFix(message);
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        String response;
        if (keyword.startsWith("天气") || keyword.endsWith("天气")) {
            // 查询天气
            response = dealWeatherQueryMsg(message);
        } else {
            // 不是查询天气就调用对话api
            response = ChatApi.chat(message);
        }

        if (StringUtils.isEmpty(response)) {
            return response;
        }
        String atMePreFix = AtMeMsg.isAtMe(message) ?  " @" + message.fromUser.name + " " : "";

        return atMePreFix + response;
    }
}
