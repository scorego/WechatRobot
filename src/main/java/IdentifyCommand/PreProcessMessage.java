package IdentifyCommand;

import config.GlobalConfig;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:20
 */
public class PreProcessMessage {

    private static final Logger log = LoggerFactory.getLogger(PreProcessMessage.class);

    /**
     * 配置文件中配置的指令前缀；为空时不生效
     */

    private static final String CONFIG_COMMAND_PREFIX = GlobalConfig.getValue("commandPrefix", "");

    /**
     * 如果配置文件中没配置指令前缀，默认是两个问号
     */
    private static final boolean IS_DEFAULT_COMMAND_PREFIX;

    /**
     * 指令前缀的长度
     */
    private static final int COMMAND_PREFIX_LENGTH;

    static {
        IS_DEFAULT_COMMAND_PREFIX = StringUtils.isBlank(CONFIG_COMMAND_PREFIX);
        COMMAND_PREFIX_LENGTH = IS_DEFAULT_COMMAND_PREFIX ? 2 : CONFIG_COMMAND_PREFIX.length();
    }

    public static boolean isCommand(WXMessage message) {
        String content = message.content;
        if (StringUtils.isBlank(content) || content.length() < COMMAND_PREFIX_LENGTH) {
            return false;
        }
        return startWithCommandPrefix(content);
    }

    private static boolean startWithCommandPrefix(String content) {
        if (IS_DEFAULT_COMMAND_PREFIX) {
            char[] arr = content.toCharArray();
            // 中文或者英文问号均可
            return (arr[0] == '?' || arr[0] == '？') && (arr[1] == '?' || arr[1] == '？');
        } else {
            return CONFIG_COMMAND_PREFIX.equals(content.substring(0, 2));
        }
    }

    public static String getCommandPrefix() {
        return IS_DEFAULT_COMMAND_PREFIX ? "??" : CONFIG_COMMAND_PREFIX;
    }

    public static void removeCommandFix(WXMessage message) {
        String content = message.content;
        if (StringUtils.isBlank(content) || content.length() < COMMAND_PREFIX_LENGTH) {
            return;
        }
        message.content = content.substring(2).trim();
        log.info("AtMeMsg::removeCommandFix, content:{}, newContent:{}", content, message.content);
    }
}
