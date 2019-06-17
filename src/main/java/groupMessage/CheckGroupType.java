package groupMessage;

import config.GlobalConfig;
import enums.GroupType;
import io.github.biezhi.wechat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 16:07
 */
public class CheckGroupType {

    private static final Logger log = LoggerFactory.getLogger(CheckGroupType.class);

    private static final String GROUP_WHITELIST = GlobalConfig.getValue("group.whitelist", "");

    private static final String GROUP_WHITE_KEYWORD = GlobalConfig.getValue("group.whiteKeyword", "");

    private static final String GROUP_BLACKLIST = GlobalConfig.getValue("group.blacklist", "");

    private static final String GROUP_BLACK_KEYWORD = GlobalConfig.getValue("group.blackKeyword", "");

    private static final String GROUP_WEATHER_ONLY = GlobalConfig.getValue("group.weatherOnly", "");

    private static final String GROUP_WEATHER_KEYWORD = GlobalConfig.getValue("group.weatherKeyword", "");

    private static List<String> WHITE_LIST = new LinkedList<>();

    private static List<String> WHITE_KEYWORD_LIST = new LinkedList<>();

    private static List<String> WEATHER_KEYWORD_LIST = new LinkedList<>();

    private static List<String> WEATHER_ONLY_LIST = new LinkedList<>();

    private static List<String> BLACK_KEYWORD_LIST = new LinkedList<>();

    private static List<String> BLACK_LIST = new LinkedList<>();

    static {
        WHITE_LIST.addAll(Arrays.stream(GROUP_WHITELIST.split("#")).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));

        WHITE_KEYWORD_LIST.addAll(Arrays.stream(GROUP_WHITE_KEYWORD.split("#")).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));

        BLACK_KEYWORD_LIST.addAll(Arrays.stream(GROUP_BLACK_KEYWORD.split("#")).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));

        BLACK_LIST.addAll(Arrays.stream(GROUP_BLACKLIST.split("#")).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));

        WEATHER_ONLY_LIST.addAll(Arrays.stream(GROUP_WEATHER_ONLY.split("#")).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));

        WEATHER_KEYWORD_LIST.addAll(Arrays.stream(GROUP_WEATHER_KEYWORD.split("#")).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));
    }


    public static GroupType checkGroupType(String from) {
        if (StringUtils.isEmpty(from)) {
            return GroupType.GROUP_NOT_EXISTS;
        }

        for (String s : BLACK_KEYWORD_LIST) {
            if (from.contains(s))
                return GroupType.GROUP_BLACKLIST;
        }
        for (String s : BLACK_LIST) {
            if (from.equals(s))
                return GroupType.GROUP_BLACKLIST;
        }

        for (String s : WHITE_KEYWORD_LIST) {
            if (from.contains(s))
                return GroupType.GROUP_WHITELIST;
        }
        for (String s : WHITE_LIST) {
            if (from.equals(s))
                return GroupType.GROUP_WHITELIST;
        }

        for (String s : WEATHER_KEYWORD_LIST){
            if (from.contains(s)){
                return GroupType.GROUP_WEATHER_ONLY;
            }
        }
        for (String s : WEATHER_ONLY_LIST) {
            if (from.equals(s))
                return GroupType.GROUP_WEATHER_ONLY;
        }

        return GroupType.GROUP_DEFAULT;
    }
}
