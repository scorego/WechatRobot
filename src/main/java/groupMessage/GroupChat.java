package groupMessage;

import api.ChatApi;
import api.WeatherApi;
import enums.GroupType;
import robot.QingyunkeRobot.QingyunkeRobot;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;

import java.util.LinkedList;
import java.util.List;

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


    private static List<String> WHITE_LIST;

    private static List<String> WEATHER_ONLY;

    private static List<String> BLACK_KEYWORD;

    static {
        BLACK_KEYWORD = new LinkedList<>();
        BLACK_KEYWORD.add("博");
        BLACK_KEYWORD.add("邮");

        WHITE_LIST = new LinkedList<>();
//        WHITE_LIST.add("<span class=\"emoji emoji1f680\"></span> 明修栈道\uD83D\uDD29");

        WEATHER_ONLY = new LinkedList<>();
    }


    public String dealGroupMsg(WeChatMessage message) {
        String groupType = message.getName();
        switch (checkGroupType(groupType)) {
            case WHITELIST:
                return dealAllMsg(message);
            case DEFAULT:
            case WEATHER_ONLY:
                return dealWeatherQueryMsg(message);
            case NOT_EXISTS:
            case BLACKLIST:
            default:
                return null;
        }
    }

    private GroupType checkGroupType(String from) {
        if (StringUtils.isEmpty(from)) {
            return GroupType.NOT_EXISTS;
        }

        for (String s : BLACK_KEYWORD) {
            if (from.contains(s))
                return GroupType.BLACKLIST;
        }
        for (String s : WHITE_LIST) {
            if (from.equals(s))
                return GroupType.WHITELIST;
        }
        for (String s : WEATHER_ONLY) {
            if (from.equals(s))
                return GroupType.WEATHER_ONLY;
        }

        return GroupType.DEFAULT;
    }


    private String dealWeatherQueryMsg(WeChatMessage message) {
        String keyword = message.getText();
        if ("天气预报".equals(keyword)) {
            return WeatherApi.dealWeatherMsg(message);
        }
        if (keyword != null && (keyword.startsWith("天气") || keyword.endsWith("天气"))) {
            return WeatherApi.getWeatherByKeyword(keyword);
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
            response = WeatherApi.dealWeatherMsg(message);
        } else {
            // 不是查询天气就调用对话api
            response = ChatApi.chat(keyword);
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
