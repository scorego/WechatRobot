package main.Service.groupMsg;

import IdentifyCommand.CheckCommandType;
import IdentifyCommand.PreProcessMessage;
import api.HelpMsg;
import api.WeatherApi;
import cons.WxMsg;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:47
 */
public class GroupCommand {

    private static volatile GroupCommand INSTANCE;

    private GroupCommand() {
    }

    public static GroupCommand getInstance() {
        if (INSTANCE == null) {
            synchronized (GroupCommand.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GroupCommand();
                }
            }
        }
        return INSTANCE;
    }

    public String doGroupCommand(WXMessage message) {
        if (isFastCommand(message.content)) {
            message.content = "天气";
            String response = doGroupWeather(message);
            if (StringUtils.isBlank(response)) {
                return null;
            }
            return response + "【极速天气模式】更多模式输入" + PreProcessMessage.getCommandPrefix() + "了解。" + WxMsg.LINE;
        }
        switch (CheckCommandType.getInstance().checkCommandType(message.content)) {
            case COMMAND_HELP:
                return doGroupHelp(message);
            case COMMAND_WEATHER:
                return doGroupWeather(message);
            case COMMAND_DIDI:
                return "";
            case COMMAND_DEFAULT:
            case COMMAND_NOT_EXIST:
            default:
                return null;
        }
    }


    private boolean isFastCommand(String content) {
        return "?".equals(content) || "？".equals(content);
    }

    private String doGroupHelp(WXMessage message) {
        switch (CheckGroupType.checkGroupType(message.fromGroup.name)) {
            case GROUP_WEATHER_ONLY:
            case GROUP_WHITELIST:
                return HelpMsg.getHelpMsg();
            default:
                return null;
        }
    }

    private String doGroupWeather(WXMessage message) {
        switch (CheckGroupType.checkGroupType(message.fromGroup.name)) {
            case GROUP_WEATHER_ONLY:
            case GROUP_WHITELIST:
                return WeatherApi.dealWeatherMsg(message);
            default:
                return null;
        }
    }


}
