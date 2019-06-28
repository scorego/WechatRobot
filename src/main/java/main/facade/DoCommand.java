package main.facade;

import IdentifyCommand.PreProcessMessage;
import cons.WxMsg;
import main.service.groupMsg.GroupTextCommand;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.GroupMsgUtil;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 15:43
 */
public class DoCommand {

    private static final Logger log = LoggerFactory.getLogger(DoCommand.class);

    public static String doGroupTextCommand(WXMessage message) {
        boolean isCommand = PreProcessMessage.isCommand(message);
        String display = GroupMsgUtil.getUserDisplayOrName(message);
        log.info("群：{}, 用户: {}, 群名片: {}, isCommand: {}, 内容: {}", message.fromGroup.name, message.fromUser.name, display, isCommand, message.content);
        if (isCommand) {
            PreProcessMessage.removeCommandFix(message);
            String response = GroupTextCommand.getInstance().doGroupCommand(message);
            if (StringUtils.isNotBlank(response)) {
                String atMePrefix = " @" + display + WxMsg.AT_ME_SPACE + WxMsg.LINE;
                return atMePrefix + response;
            }
        }
        return null;
    }

}
