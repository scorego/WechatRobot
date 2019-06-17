package enums;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/17 16:49
 */

@Getter
public enum FriendType {
    FRIEND_BLACK,
    FRIEND_WHITE,
    FRIEND_NOT_EXISTS,
    FRIEND_DEFAULT;

    private int value;
}
