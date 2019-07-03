package main.service.friendMsg;

import config.GlobalConfig;
import enums.FriendType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 16:52
 */
@Slf4j
public class CheckFriendType {

    private static final String FRIEND_BLACK = GlobalConfig.getValue("friend.blacklist", "");

    private static final String FRIEND_WHITE = GlobalConfig.getValue("friend.whitelist", "");

    private static List<String> FRIEND_BLACK_LIST = new LinkedList<>();

    private static List<String> FRIEND_WHITE_LIST = new LinkedList<>();

    private static Map<String, FriendType> cache = new ConcurrentHashMap<>();

    static {
        FRIEND_BLACK_LIST.addAll(Arrays.stream(FRIEND_BLACK.split("#")).filter(StringUtils::isNotBlank).collect(Collectors.toList()));

        FRIEND_WHITE_LIST.addAll(Arrays.stream(FRIEND_WHITE.split("#")).filter(StringUtils::isNotBlank).collect(Collectors.toList()));
    }

    public static FriendType checkFriendType(String friendName) {
        if (StringUtils.isEmpty(friendName)) {
            log.info("checkFriendType, friendName: {}, FriendType: {}", friendName, FriendType.FRIEND_NOT_EXISTS);
            return FriendType.FRIEND_NOT_EXISTS;
        }

        FriendType type = FriendType.FRIEND_DEFAULT;

        if (cache.containsKey(friendName)) {
            type = cache.getOrDefault(friendName, FriendType.FRIEND_NOT_EXISTS);
            log.info("checkFriendType, friendName: {}, FriendType: {}, fromCache: true", friendName, type);
            return type;
        }

        for (String s : FRIEND_BLACK_LIST) {
            if (friendName.equals(s)) {
                type = FriendType.FRIEND_BLACK;
            }
        }
        for (String s : FRIEND_WHITE_LIST) {
            if (friendName.equals(s)) {
                type = FriendType.FRIEND_WHITE;
            }
        }
        cache.put(friendName, type);
        log.info("checkFriendType, friendName: {}, FriendType: {}, fromCache: false", friendName, FriendType.FRIEND_NOT_EXISTS);
        return type;
    }

}
