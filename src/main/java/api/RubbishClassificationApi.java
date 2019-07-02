package api;

import cons.WxMsg;
import enums.RubbishType;
import org.apache.commons.lang3.StringUtils;
import robot.RubbishClassificationApp.RubbishApp;

import static enums.RubbishType.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/1 21:41
 */
public class RubbishClassificationApi {

    public static String classfyRubbish(String rubbish) {
        if(StringUtils.isBlank(rubbish)){
            return "生活垃圾主要包括有害垃圾、可回收物、湿垃圾、干垃圾。垃圾分类，从我做起。";
        }
        RubbishType rubbishType = checkRubbishType(rubbish);
        String tip = getRubbishTypeTip(rubbishType) + WxMsg.LINE;
        switch (rubbishType) {
            case HAZARDOUS_WASTE:
            case RECYCLABLE_WASTE:
            case HOUSEHOLD_FOOD_WASTE:
            case RESIDUAL_WASTE:
                return rubbish + "属于" + rubbishType.getName() + "。" + WxMsg.LINE + tip;
            case DEFAULT_TYPE:
            default:
                return tip;
        }
    }

    private static RubbishType checkRubbishType(String rubbish) {
        return RubbishApp.getRubbishType(rubbish);
    }

    private static String getRubbishTypeTip(RubbishType type) {
        switch (type) {
            case HAZARDOUS_WASTE:
                return "有害垃圾是指废电池、废灯管、废药品、废油漆及其容器等对人体健康或者自然环境造成直接或者潜在危害的生活废弃物。";
            case RECYCLABLE_WASTE:
                return "可回收物是指废纸张、废塑料、废玻璃制品、废金属、废织物等适宜回收、可循环利用的生活废弃物。";
            case HOUSEHOLD_FOOD_WASTE:
                return "湿垃圾是指日常生活垃圾产生的容易腐烂的生物质废弃物。";
            case RESIDUAL_WASTE:
                return "干垃圾即其他垃圾，指除可回收物、有害垃圾、湿垃圾以外的其它生活废弃物。";
            case DEFAULT_TYPE:
            default:
                return "抱歉，未找到该类型垃圾信息。";
        }
    }
}
