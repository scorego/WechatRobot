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
        // 极速模式为垃圾分类
        if (isFastCommand(message.content)) {
            String response = doRubbishCheck(message);
            if (StringUtils.isBlank(response)) {
                return null;
            }
            return response + "【了解详情】更多模式输入" + PreProcessMessage.getCommandPrefix() + "了解。" + WxMsg.LINE;
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
        return content.startsWith("?") || content.startsWith("？");
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
                return RubbishClassificationApi.dealRubbishMsg(message);
            default:
                return null;
        }
    }
    private String doRubbishCheck(WXMessage message) {
        switch (CheckGroupType.checkGroupType(message.fromGroup.name)) {
            case GROUP_MODE_ONLY:
            case GROUP_WHITELIST:
                return RubbishClassificationApi.classifyRubbish(message.content.substring(1));
            default:
                return null;
        }
    }


}
