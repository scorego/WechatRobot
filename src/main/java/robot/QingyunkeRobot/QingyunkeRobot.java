package robot.QingyunkeRobot;

import config.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import robot.QingyunkeRobot.entity.QingyunkeResponseEntity;
import com.alibaba.fastjson.JSON;
import utils.HttpRequestUtil;

public class QingyunkeRobot {

    private static final String ChatRobot = GlobalConfig.getValue("QingyunkeRobot.chat", "");

    private static final String RECEIVE_CHAT_MSG_LINE = "{br}";

    public static String getResponse(String keyWord) {
        if (StringUtils.isBlank(keyWord)) {
            return null;
        }
        keyWord = keyWord.replace(" ", "%20");
        String response = HttpRequestUtil.doGet(ChatRobot + keyWord);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        QingyunkeResponseEntity qingResponse = JSON.parseObject(response, QingyunkeResponseEntity.class);

        return replaceBr(qingResponse.getContent());
    }

    private static String replaceBr(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        return content.replace(RECEIVE_CHAT_MSG_LINE, "\n");
    }
}
