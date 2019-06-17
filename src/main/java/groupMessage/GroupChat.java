package groupMessage;

import api.ChatApi;
import api.WeatherApi;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;

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


    public String dealGroupMsg(WeChatMessage message) {
        String groupType = message.getName();
        switch (CheckGroupType.checkGroupType(groupType)) {
            case GROUP_WHITELIST:
                return dealAllMsg(message);
            case GROUP_WEATHER_ONLY:
                return dealWeatherQueryMsg(message);
            case GROUP_DEFAULT:
            case GROUP_NOT_EXISTS:
            case GROUP_BLACKLIST:
            default:
                return null;
        }
    }


    private String dealWeatherQueryMsg(WeChatMessage message) {
        String keyword = message.getText();
        if (keyword != null && (keyword.startsWith("天气") || keyword.endsWith("天气"))) {
            return WeatherApi.dealWeatherMsg(message);
        }
        return null;
    }

    private String dealAllMsg(WeChatMessage message) {
        String keyword = message.getText();
        if (StringUtils.isEmpty(keyword)) {
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
        String atMePreFix = message.isAtMe() ?
                "@" + message.getFromNickName() + " "
                : "";
        return atMePreFix + response;
    }
}
