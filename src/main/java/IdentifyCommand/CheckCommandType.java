package IdentifyCommand;

import enums.CommandType;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:38
 */
public class CheckCommandType {

    private static volatile CheckCommandType Instance;

    private CheckCommandType() {
    }

    public static CheckCommandType getInstance() {
        if (Instance == null) {
            synchronized (CheckCommandType.class) {
                if (Instance == null) {
                    Instance = new CheckCommandType();
                }
            }
        }
        return Instance;
    }


    public CommandType checkCommandType(String content) {
        if (content == null) {
            return CommandType.COMMAND_NOT_EXIST;
        }
        if (StringUtils.isBlank(content)) {
            return CommandType.COMMAND_HELP;
        }
        if (isRubbishCommand(content)) {
            return CommandType.COMMAND_RUBBISH;
        }
        if (isWeatherCommand(content)) {
            return CommandType.COMMAND_WEATHER;
        }
        if (isNewsCommand(content)) {
            return CommandType.COMMAND_NEWS;
        }
        if (isZhihuHotCommand(content)) {
            return CommandType.COMMAND_ZHIHU_HOT;
        }
        if (isDiDiCommand(content)) {
            return CommandType.COMMAND_DIDI;
        }
        if (isVoteCommand(content)) {
            return CommandType.COMMAND_VOTE;
        }
        return CommandType.COMMAND_DEFAULT;
    }

    private boolean isWeatherCommand(String content) {
        return content.endsWith("天气");
    }

    private boolean isNewsCommand(String content) {
        return content.startsWith("新闻");
    }

    private boolean isRubbishCommand(String content) {
        return (content.startsWith("？") || content.startsWith("?"));
    }

    private boolean isZhihuHotCommand(String content) {
        return (content.startsWith("知乎"));
    }

    private boolean isDiDiCommand(String content) {
        return false;
    }

    private boolean isVoteCommand(String content) {
        return false;
    }


}
