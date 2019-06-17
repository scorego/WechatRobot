package friendMessage;

import config.GlobalConfig;
import io.github.biezhi.wechat.api.model.WeChatMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 14:41
 */
public class FriendChat {

   private static final boolean autoReplyFriend = GlobalConfig.getValue("autoReplyFriend", "false").equalsIgnoreCase("true");

   private static final String autoReplyFriendMsg = GlobalConfig.getValue("autoReplyFriendMsg", "");

   public static String friendChat(WeChatMessage message){
         if (autoReplyFriend){
            return autoReplyFriend(message);
         }
         return null;
   }

   private static String autoReplyFriend(WeChatMessage message){
         return autoReplyFriendMsg;
   }
}
