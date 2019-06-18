package utils;

import config.GlobalConfig;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 20:34
 */
public class AtMeMsg {

    private static final String USER_NICKNAME = GlobalConfig.getValue("userNickName", "");

    private static final String AT_ME_STRING;

    static {
        AT_ME_STRING = "@" + USER_NICKNAME + " ";
    }

    /**
     * 判定群文字消息是否是at自己的
     *
     * @param message
     * @return
     */
    public static boolean isAtMe(WXMessage message) {
        return StringUtils.isNotBlank(USER_NICKNAME) && message.content.contains(AT_ME_STRING);
    }

    public static String removeAtFix(WXMessage message) {
        String content = message.content;
        if (StringUtils.isBlank(content)) {
            return "";
        }
        while (content.contains("@")) {
            int i = firstIndex(content,'@');
            int j = firstIndex(content,' ');
            if (i > 0 && j > 0) {
                content = content.substring(0, i + 1) + content.substring(j + 1);
            } else if (i > 0) {
                content = content.substring(0, i + 1);
            }else{
                break;
            }
        }
        return content;
    }

    private static int firstIndex(String s, char c) {
        for (int i = 0; i < s.toCharArray().length; i++) {
            if (s.toCharArray()[i] == c) {
                return i;
            }
        }
        return -1;
    }

}
