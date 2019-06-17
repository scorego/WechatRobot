package robot.QingyunkeRobot;

import robot.QingyunkeRobot.entity.QingyunkeResponseEntity;
import com.alibaba.fastjson.JSON;
import io.github.biezhi.wechat.utils.StringUtils;
import utils.HttpRequestUtil;

public class QingyunkeRobot {
    private static final String LINK = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";

    public static String getResponse(String keyWord){
        if (StringUtils.isEmpty(keyWord)){
            return null;
        }
        String response =  HttpRequestUtil.doGet(LINK + keyWord);

        QingyunkeResponseEntity qingResponse = JSON.parseObject(response, QingyunkeResponseEntity.class);

        return replaceBr(qingResponse.getContent());
    }

    private static String replaceBr(String content){
        if (StringUtils.isEmpty(content)){
            return null;
        }
        return content.replace("{br}","\n");
    }
}
