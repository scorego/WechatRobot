package enums;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/1 21:42
 */
public enum RubbishType {

    HAZARDOUS_WASTE(1, "有害垃圾"),

    RECYCLABLE_WASTE(2, "可回收物"),

    HOUSEHOLD_FOOD_WASTE(3, "湿垃圾/厨余垃圾"),

    RESIDUAL_WASTE(4, "干垃圾/其它垃圾"),

    NOT_EXISTS(5, "数据库未查到"),

    NO_RESPONSE(6, "API无响应");

    @Getter
    private int value;

    @Getter
    private String name;

    public static RubbishType findByValue(int value) {
        switch (value) {
            case 1:
                return HAZARDOUS_WASTE;
            case 2:
                return RECYCLABLE_WASTE;
            case 3:
                return HOUSEHOLD_FOOD_WASTE;
            case 4:
                return RESIDUAL_WASTE;
            case 5:
                return NOT_EXISTS;
            case 6:
                return NO_RESPONSE;
            default:
                return null;
        }
    }


    RubbishType(int value, String name) {
        this.value = value;
        this.name = name;
    }

}
