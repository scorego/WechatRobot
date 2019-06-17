package enums;


import lombok.Getter;

@Getter
public enum GroupType {
    /**
     * 默认级别
     */
    DEFAULT,
    /**
     * 白名单，回复一切信息
     */
    WHITELIST,
    /**
     * 只回复查询天气
     */
    WEATHER_ONLY,
    /**
     * 只回复@我的信息
     */
    AT_ME_ONLY,
    /**
     * 黑名单，不回复任何信息
     */
    BLACKLIST,
    /**
     * 群不存在
     */
    NOT_EXISTS;

    private int status;
}
