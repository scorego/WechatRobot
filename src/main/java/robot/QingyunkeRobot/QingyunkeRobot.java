package robot.QingyunkeRobot;

import config.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import robot.QingyunkeRobot.entity.QingyunkeResponseEntity;
import com.alibaba.fastjson.JSON;
import utils.HttpRequestUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QingyunkeRobot {

    private static final String ChatRobot = GlobalConfig.getValue("QingyunkeRobot.chat", "");

    private static final String RECEIVE_CHAT_MSG_LINE = "{br}";

    public static String getResponse(String keyWord) {
        if (StringUtils.isBlank(keyWord)) {
            return null;
        }

        String link = ChatRobot;
        try {
            link += URLEncoder.encode(keyWord, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            link += keyWord;
        }
        String response = HttpRequestUtil.doGet(link);
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
