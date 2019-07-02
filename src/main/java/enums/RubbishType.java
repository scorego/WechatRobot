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

    DEFAULT_TYPE(5, "不存在");

    @Getter
    private int value;

    @Getter
    private String name;

    private RubbishType(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
