package api;

import config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robot.QingyunkeRobot.QingyunkeRobot;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 14:04
 */
public class ChatApi {
    private static final Logger log = LoggerFactory.getLogger(ChatApi.class);

    private static final String CHAT_ROBOT = GlobalConfig.getValue("chatApi", "");

    public static String chat(String keyword) {
        log.info("ChatApi::chat, keyword: {}",keyword);

        switch (CHAT_ROBOT) {
            case "QingyunkeRobot":
                return QingyunkeRobot.getResponse(keyword);
            default:
                return null;
        }

    }
}
