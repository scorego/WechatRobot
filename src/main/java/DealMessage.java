import enums.GroupType;
import groupMessage.GroupChat;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;

import java.util.LinkedList;
import java.util.List;


public class DealMessage {

    private static volatile DealMessage INSTANCE;

    private DealMessage() {
    }

    public static DealMessage getInstance() {
        if (INSTANCE == null) {
            synchronized (DealMessage.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DealMessage();
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
        String from = message.getName();
        switch (checkFrom(from)) {
            case WHITELIST:
                return GroupChat.dealAllMsg(message);
            case DEFAULT:
            case WEATHER_ONLY:
                return GroupChat.dealWeatherQueryMsg(message);
            case NOT_EXISTS:
            case BLACKLIST:
            default:
                return null;
        }
    }

    private GroupType checkFrom(String from) {
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


}
