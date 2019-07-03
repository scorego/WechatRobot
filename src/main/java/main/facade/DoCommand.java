package main.facade;

import IdentifyCommand.PreProcessMessage;
import lombok.extern.slf4j.Slf4j;
import main.service.groupMsg.GroupTextCommand;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:43
 */
@Slf4j
public class DoCommand {

    public static String doGroupTextCommand(WXMessage message) {
        PreProcessMessage.removeCommandFix(message);
        log.info("DoCommand::doGroupTextCommand, command: {}", message.content);
        return GroupTextCommand.getInstance().doGroupCommand(message);
    }

}
