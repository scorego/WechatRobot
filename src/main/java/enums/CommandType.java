package enums;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:38
 */

@Getter
public enum CommandType {
    COMMAND_HELP,
    /**
     * 查天气
     */
    COMMAND_WEATHER,
    /**
     * 查垃圾分类
     */
    COMMAND_RUBBISH,
    COMMAND_DIDI,
    COMMAND_CHAT,
    COMMAND_DEFAULT,
    COMMAND_NOT_EXIST;

    private int value;
}
