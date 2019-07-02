package enums;


import lombok.Getter;

@Getter
public enum GroupType {
    /**
     * 默认级别
     */
    GROUP_DEFAULT,
    /**
     * 白名单，回复一切信息
     */
    GROUP_WHITELIST,
    /**
     * 只回复启用的模式
     */
    GROUP_MODE_ONLY,
    /**
     * 只回复@我的信息
     */
    GROUP_AT_ME_ONLY,
    /**
     * 黑名单，不回复任何信息
     */
    GROUP_BLACKLIST,
    /**
     * 群不存在
     */
    GROUP_NOT_EXISTS;

    private int status;

}
