package api;

import IdentifyCommand.PreProcessMessage;
import config.GlobalConfig;
import cons.WxMsg;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/20 16:10
 */
public class HelpMsg {

    private static final String README_LINK = GlobalConfig.getValue("helpMsg.detail","https://github.com/scorego/WechatRobot");

    public static String getHelpMsg() {
        String commandPrefix = PreProcessMessage.getCommandPrefix();
        String helpMsg = "WeChat指令聊天机器人" + WxMsg.LINE
                + "【指令前缀】" + commandPrefix + WxMsg.LINE
                + "指令前缀+具体指令开启相应模式。" + WxMsg.LINE
                + "【天气模式】" + "输入城市查询天气，如北京天气。" + WxMsg.LINE
                + "【极速模式】" + "查天气快捷指令：" +  "？" + WxMsg.LINE
                + "【更多模式】" + "开发中" + WxMsg.LINE
                + "试着发一下“" + PreProcessMessage.getCommandPrefix() +"天气”。☺" + WxMsg.LINE
                + "详情见" + README_LINK + WxMsg.LINE;

        return helpMsg;
    }
}
