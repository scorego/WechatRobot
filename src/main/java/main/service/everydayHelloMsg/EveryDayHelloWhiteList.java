package main.service.everydayHelloMsg;

import com.alibaba.fastjson.JSONObject;
import config.GlobalConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import main.WechatBot;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXGroup;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXUser;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 12:42
 */
@Slf4j
public class EveryDayHelloWhiteList {

    private static volatile EveryDayHelloWhiteList INSTANCE;

    private static final String EVERYDAY_HELLO_GROUP_WHITE_LIST = GlobalConfig.getValue("everydayHello.group.whitelist", "");

    private static final String EVERYDAY_HELLO_FRIEND_WHITE_LIST = GlobalConfig.getValue("everydayHello.friend.whitelist", "");


    private EveryDayHelloWhiteList() {
        group = new LinkedList<>();
        group.addAll(Arrays.stream(EVERYDAY_HELLO_GROUP_WHITE_LIST.split("#")).filter(StringUtils::isNotBlank).collect(Collectors.toList()));
        friend = new LinkedList<>();
        friend.addAll(Arrays.stream(EVERYDAY_HELLO_FRIEND_WHITE_LIST.split("#")).filter(StringUtils::isNotBlank).collect(Collectors.toList()));

        groupSet = new HashSet<>(group.size());
        HashMap<String, WXGroup> groupMap = WechatBot.getWeChatClient().userGroups();
        group.forEach(g ->
                groupMap.forEach((key, value) -> {
                    if (value.name.equals(g)) {
                        groupSet.add(value);
                    }
                })
        );
        StringBuilder groupSetList = new StringBuilder();
        for (WXGroup wxGroup : groupSet) {
            groupSetList.append(wxGroup.name).append("  \t");
        }
        log.info("每日一句群列表：{}", groupSetList.toString());

        friendSet = new HashSet<>(friend.size());
        HashMap<String, WXUser> friendMap = WechatBot.getWeChatClient().userFriends();
        friend.forEach(f ->
                friendMap.forEach((key, value) -> {
                    if (value.name.equals(f)) {
                        friendSet.add(value);
                    }
                })
        );
        StringBuilder friendSetList = new StringBuilder();
        for (WXUser wxUser : friendSet) {
            friendSetList.append(wxUser.name).append("  \t");
        }
        log.info("每日一句好友列表：{}", friendSetList.toString());
    }

    public static EveryDayHelloWhiteList getInstance() {
        if (INSTANCE == null) {
            synchronized (EveryDayHelloWhiteList.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EveryDayHelloWhiteList();
                }
            }
        }
        return INSTANCE;
    }

    @Getter
    private List<String> group;

    @Getter
    private List<String> friend;

    /**
     * 要发送每日一句的群名单
     */
    @Getter
    private Set<WXGroup> groupSet;

    /**
     * 要发送每日一句的好友名单
     */
    @Getter
    private Set<WXUser> friendSet;

}
