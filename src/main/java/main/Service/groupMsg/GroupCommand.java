package main.Service.groupMsg;

import IdentifyCommand.CheckCommandType;
import api.HelpMsg;
import api.WeatherApi;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;

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
