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

    /**
     * 获取详情
     */
    COMMAND_HELP,

    /**
     * 查天气
     */
    COMMAND_WEATHER,

    /**
     * 查垃圾分类
     */
    COMMAND_RUBBISH,

    /**
     * 拼车模式
     */
    COMMAND_DIDI,

    /**
     * 投票模式
     */
    COMMAND_VOTE,

    /**
     * 聊天
     */
    COMMAND_CHAT,

    /**
     * 默认级别
     */
    COMMAND_DEFAULT,

    COMMAND_NOT_EXIST;

    private int value;

}
