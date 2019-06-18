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


public class HelloBot extends WeChatBot {

    private static final Logger log = LoggerFactory.getLogger(HelloBot.class);

    private static final String PREFIX = GlobalConfig.getValue("reply-prefix","");

    private HelloBot(Config config) {
        super(config);
    }

    @Bind(msgType = MsgType.TEXT , accountType = AccountType.TYPE_FRIEND)
    public void friendTextMsg(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
            log.info("接收消息，好友：{}, 内容：{}, 全部消息：{}" , message.getName() ,message.getText(), message.toString());
            String response = DealMessage.dealFriendMsg(message);
            if (StringUtils.isNotEmpty(response)){
                response = PREFIX + response;
                log.info("回复消息, 好友：{}, 消息：{}", message.getFromUserName(), response);
                this.sendMsg(message.getFromUserName(),  response);
            }
        }
    }

    @Bind(msgType = MsgType.TEXT, accountType = AccountType.TYPE_GROUP)
    public void groupTextMsg(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
            log.info("接收消息，群：{}, 内容：{}, 全部消息：{}" , message.getName() ,message.getText(), message.toString());
            String response = DealMessage.dealGroupMsg(message);
            if (StringUtils.isNotEmpty(response)){
                response = PREFIX + response;
                log.info("回复消息, 群：{}, 消息：{}", message.getFromUserName(), response);
                this.sendMsg(message.getFromUserName(),  response);
            }
        }
    }

    public static void main(String[] args) {
        new HelloBot(Config.me().autoLogin(true).showTerminal(true)).start();
    }


}
