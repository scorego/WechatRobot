package main.service.groupMsg;

import IdentifyCommand.CheckCommandType;
import IdentifyCommand.PreProcessMessage;
import api.HelpMsg;
import api.RubbishClassificationApi;
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
public class GroupTextCommand {

    private static volatile GroupTextCommand INSTANCE;

    private GroupTextCommand() {
    }

    public static GroupTextCommand getInstance() {
        if (INSTANCE == null) {
            synchronized (GroupTextCommand.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GroupTextCommand();
                }
            }
        }
        return INSTANCE;
    }

    public String doGroupCommand(WXMessage message) {
        // 极速模式为查询天气
        if (isFastCommand(message.content)) {
            message.content = "天气";
            return doGroupWeather(message);
        }
        switch (CheckCommandType.getInstance().checkCommandType(message.content)) {
            case COMMAND_HELP:
                return doGroupHelp(message);
            case COMMAND_WEATHER:
                return doGroupWeather(message);
            case COMMAND_RUBBISH:
                return doGroupRubbish(message);
            case COMMAND_DIDI:
                return "";
            case COMMAND_DEFAULT:
            case COMMAND_NOT_EXIST:
            default:
                return null;
        }
    }


    private boolean isFastCommand(String content) {
        content = content.trim();
        return "?".equals(content) || "？".equals(content);
    }

    private String doGroupHelp(WXMessage message) {
        switch (CheckGroupType.checkGroupType(message.fromGroup.name)) {
            case GROUP_MODE_ONLY:
            case GROUP_WHITELIST:
                return HelpMsg.getHelpMsg();
            default:
                return null;
        }
    }

    private String doGroupWeather(WXMessage message) {
        switch (CheckGroupType.checkGroupType(message.fromGroup.name)) {
            case GROUP_MODE_ONLY:
            case GROUP_WHITELIST:
                return WeatherApi.dealWeatherMsg(message);
            default:
                return null;
        }
    }

    private String doGroupRubbish(WXMessage message) {
        switch (CheckGroupType.checkGroupType(message.fromGroup.name)) {
            case GROUP_MODE_ONLY:
            case GROUP_WHITELIST:
                return RubbishClassificationApi.dealRubbishMsg(message)
                        + "【更多功能】更多功能发送" + PreProcessMessage.getCommandPrefix() + "了解。" + WxMsg.LINE;
            default:
                return null;
        }
    }

}
