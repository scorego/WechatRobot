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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 20:34
 */
public class AtMeMsg {

    private static final Logger log = LoggerFactory.getLogger(AtMeMsg.class);

    private static final Map<String, String> MY_DISPLAY_OR_NICKNAME = new ConcurrentHashMap<>();

    /**
     * 判定群文字消息是否是at自己的
     *
     * @param message
     * @return
     */
    public static boolean isAtMe(WXMessage message) {
        return message.content.contains(atMeFix(message));
    }

    private static String atMeFix(WXMessage message) {
        if (MY_DISPLAY_OR_NICKNAME.containsKey(message.fromGroup.name)) {
            return MY_DISPLAY_OR_NICKNAME.get(message.fromGroup.name);
        }
        String myName = message.toContact.name;
        HashMap<String, WXGroup.Member> members = message.fromGroup.members;
        for (HashMap.Entry<String, WXGroup.Member> entry : members.entrySet()) {
            if (myName.equals(entry.getValue().name)) {
                if (StringUtils.isNotBlank(entry.getValue().display)) {
                    myName = entry.getValue().display;
                }
                break;
            }
        }
        String atMeFix = "@" + myName + WxMsg.AT_ME_SPACE;
        MY_DISPLAY_OR_NICKNAME.put(message.fromGroup.name, atMeFix);
        return atMeFix;
    }

}
