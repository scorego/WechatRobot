package utils;

import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXGroup;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 17:36
 */
public class GroupMsgUtil {

    public static String getUserDisplayOrName(WXMessage message) {
        String display = message.fromGroup.members.containsKey(message.fromUser.id)
                ? message.fromGroup.members.get(message.fromUser.id).display : null;
        return StringUtils.isBlank(display) ? message.fromUser.name : display;

//        String name = message.fromUser.name;
//        for (HashMap.Entry<String, WXGroup.Member> entry : message.fromGroup.members.entrySet()){
//            if (name.equals(entry.getValue().name)){
//                if (StringUtils.isNotBlank(entry.getValue().display)){
//                    name = entry.getValue().display;
//                }
//                break;
//            }
//        }
//        return name;
    }
}
