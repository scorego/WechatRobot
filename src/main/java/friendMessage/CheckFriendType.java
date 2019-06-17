package friendMessage;

import config.GlobalConfig;
import enums.FriendType;
import io.github.biezhi.wechat.utils.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 16:52
 */
public class CheckFriendType {

    private static final String FRIEND_BLACK = GlobalConfig.getValue("friend.blacklist","");

    private static final String FRIEND_WHITE = GlobalConfig.getValue("friend.whitelist","");

    private static List<String> FRIEND_BLACK_LIST = new LinkedList<>();

    private static List<String> FRIEND_WHITE_LIST = new LinkedList<>();

    static {
        FRIEND_BLACK_LIST.addAll(Arrays.asList(FRIEND_BLACK.split("#")));

        FRIEND_WHITE_LIST.addAll(Arrays.asList(FRIEND_WHITE.split("#")));
    }

    public static FriendType checkFriendType(String friendName){
        if (StringUtils.isEmpty(friendName)){
            return FriendType.FRIEND_NOT_EXISTS;
        }
        for (String s : FRIEND_BLACK_LIST) {
            if (friendName.equals(s)){
                return FriendType.FRIEND_BLACK;
            }
        }
        for (String s : FRIEND_WHITE_LIST) {
            if (friendName.equals(s)){
                return FriendType.FRIEND_WHITE;
            }
        }
        return FriendType.FRIEND_DEFAULT;
    }

}
