package api;

import config.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import robot.QingyunkeRobot.QingyunkeRobot;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 14:04
 */
@Slf4j
public class ChatApi {

    private static final String CHAT_ROBOT = GlobalConfig.getValue("chatApi", "");

    public static String chat(WXMessage message) {
        String keyword = message.content.trim();
        log.info("ChatApi::chat, keyword: {}", keyword);

        switch (CHAT_ROBOT) {
            case "QingyunkeRobot":
                return QingyunkeRobot.getResponse(keyword);
            default:
                return null;
        }

    }
}
