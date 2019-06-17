package groupMessage;

import api.ChatApi;
import api.WeatherApi;
import robot.QingyunkeRobot.QingyunkeRobot;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;

public class GroupChat {

    public static String dealWeatherQueryMsg(WeChatMessage message) {
        String keyword = message.getText();
        if ("天气预报".equals(keyword)){
            return WeatherApi.dealWeatherMsg(message);
        }
        if (keyword != null && (keyword.startsWith("天气") || keyword.endsWith("天气"))) {
            return WeatherApi.getWeatherByKeyword(keyword);
        }
        return null;
    }

    public static String dealAllMsg(WeChatMessage message) {
        String keyword = message.getText();
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        String response;
        if (keyword.startsWith("天气") || keyword.endsWith("天气")) {
            // 查询天气
            response = WeatherApi.dealWeatherMsg(message);
        }else{
            // 不是查询天气就调用对话api
            response = ChatApi.chat(keyword);
        }

        if (StringUtils.isEmpty(response)){
            return response;
        }
        String atMePreFix = message.isAtMe() ?
                "@" + message.getFromNickName() + " "
                : "";
        return atMePreFix + response;
    }
}
