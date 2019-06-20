package api;

import IdentifyCommand.PreProcessMessage;
import cons.WxMsg;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 16:10
 */
public class HelpMsg {


    public static String getHelpMsg() {
        String commandPrefix = PreProcessMessage.getCommandPrefix();
        String helpMsg = "WeChatBot指令详情：" + WxMsg.LINE
                + "指令前缀：" + commandPrefix + WxMsg.LINE
                + "指令前缀+具体指令开启相应模式。" + WxMsg.LINE
                + "天气模式：" + "输入>城市+天气<查询天气，如北京天气。" + WxMsg.LINE
                + "更多模式：" + "开发中" + WxMsg.LINE;

        return helpMsg;
    }
}
