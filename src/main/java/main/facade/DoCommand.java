package main.facade;

import main.service.groupMsg.GroupCommand;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:43
 */
public class DoCommand {

    public static String doGroupCommand(WXMessage message) {
        return GroupCommand.getInstance().doGroupCommand(message);
    }


}
