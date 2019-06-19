package utils;

import com.alibaba.fastjson.asm.MethodWriter;
import config.GlobalConfig;
import cons.WxMsg;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXGroup;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 20:34
 */
public class AtMeMsg {

    private static final Logger log = LoggerFactory.getLogger(AtMeMsg.class);

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
        return message.content.contains(atMeFix(message));
    }
    private static String atMeFix(WXMessage message){
        String myName = message.toContact.name;
        HashMap<String, WXGroup.Member> members = message.fromGroup.members;
        for(HashMap.Entry<String, WXGroup.Member> entry : members.entrySet()){
            if (myName.equals(entry.getValue().name)){
                if (StringUtils.isNotBlank( entry.getValue().display)){
                    myName = entry.getValue().display;
                }
                break;
            }
        }
        return "@" + myName+ WxMsg.AT_ME_SPACE;
    }

    public static void removeAtFix(WXMessage message) {
        String content = message.content;
        if (StringUtils.isBlank(content)) {
            return ;
        }
        String prefix = atMeFix(message);

        if (!content.contains(prefix)) {
             return;
        }
        message.content = content.replace(prefix, " ").trim();
        log.info("AtMeMsg::removeAtFix, content:{}, newContent:{}", content, message.content);
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
