import config.GlobalConfig;
import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.AccountType;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WechatBot extends WeChatBot {

    private static final Logger log = LoggerFactory.getLogger(WechatBot.class);

    private static final String PREFIX = GlobalConfig.getValue("reply-prefix","Auto: ");

    public WechatBot(Config config) {
        super(config);
    }

//    @Bind(msgType = MsgType.TEXT , accountType = AccountType.TYPE_FRIEND)
//    public void friendTextMsg(WeChatMessage message) {
//        if (StringUtils.isNotEmpty(message.getName())) {
//            System.out.println("接收到朋友 [{" + message.getName() + "}] 的消息:  " +  message.getText());
//            this.sendMsg(message.getFromUserName(), "自动回复: " + message.getText());
//        }
//    }

    @Bind(msgType = MsgType.TEXT, accountType = AccountType.TYPE_GROUP)
    public void groupTextMessage(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
            log.info("接收消息，群：{}, 内容：{}, 全部消息：{}" , message.getName() ,message.getText(), message.toString());
            String response = DealMessage.getInstance().dealGroupMsg(message);
            if (response != null){
                response = PREFIX + response;
                log.info("回复消息, 群：{}, 消息：{}", message.getFromUserName(), response);
                this.sendMsg(message.getFromUserName(),  response);
            }
        }
    }

    public static void main(String[] args) {
        new WechatBot(Config.me().autoLogin(true).showTerminal(true)).start();
    }


}
