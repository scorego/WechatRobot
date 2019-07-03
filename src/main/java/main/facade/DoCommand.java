package main.facade;

import IdentifyCommand.PreProcessMessage;
import main.service.groupMsg.GroupTextCommand;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:43
 */
public class DoCommand {

    private static final Logger log = LoggerFactory.getLogger(DoCommand.class);

    public static String doGroupTextCommand(WXMessage message) {
        PreProcessMessage.removeCommandFix(message);
        log.info("DoCommand::doGroupTextCommand, command: {}", message.content);
        return GroupTextCommand.getInstance().doGroupCommand(message);
    }

}
