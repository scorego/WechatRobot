package api;

import cons.WxMsg;
import enums.RubbishType;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import robot.RubbishClassificationApp.RubbishApp;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/1 21:41
 */
public class RubbishClassificationApi {

    public static String dealRubbishMsg(WXMessage message) {
        String content = message.content.substring(1).trim();
        return classifyRubbish(content);
    }

    public static String classifyRubbish(String rubbish) {
        if (StringUtils.isBlank(rubbish)) {
            return "生活垃圾主要包括有害垃圾、可回收物、湿垃圾/厨余垃圾、干垃圾/其他垃圾。垃圾分类，从我做起。";
        }
        RubbishType rubbishType = checkRubbishType(rubbish);
        String tip = getRubbishTypeTip(rubbishType);
        switch (rubbishType) {
            case HAZARDOUS_WASTE:
            case RECYCLABLE_WASTE:
            case HOUSEHOLD_FOOD_WASTE:
            case RESIDUAL_WASTE:
                return "【分类结果】" + rubbish + "属于" + rubbishType.getName() + "。" + WxMsg.LINE + tip;
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
                return "【分类介绍】有害垃圾是指废电池、废灯管、废药品、废油漆及其容器等对人体健康或者自然环境造成直接或者潜在危害的生活废弃物。" + WxMsg.LINE
                        + "【投放要求】投放时请注意轻放；易破损的请连带包装或包裹后轻放；如易挥发，请密封后投放。" + WxMsg.LINE;
            case RECYCLABLE_WASTE:
                return "【分类介绍】可回收物是指废纸张、废塑料、废玻璃制品、废金属、废织物等适宜回收、可循环利用的生活废弃物。" + WxMsg.LINE
                        + "【投放要求】轻投轻放；清洁干燥，避免污染，费纸尽量平整；立体包装物请清空内容物，清洁后压扁投放；"
                        + "有尖锐边角的、应包裹后投放。" + WxMsg.LINE;
            case HOUSEHOLD_FOOD_WASTE:
                return "【分类介绍】湿垃圾即厨余垃圾，是指日常生活垃圾产生的容易腐烂的生物质废弃物。" + WxMsg.LINE
                        + "【投放要求】纯流质的食物垃圾，如牛奶等，应直接倒进下水口；"
                        + "有包装物的湿垃圾应将包装物去除后分类投放、包装物请投放到对应的可回收物或干垃圾容器。" + WxMsg.LINE;
            case RESIDUAL_WASTE:
                return "【分类介绍】干垃圾即其他垃圾，指除可回收物、有害垃圾、湿垃圾以外的其它生活废弃物。" + WxMsg.LINE
                        + "【投放要求】" + "尽量沥干水分；" + "难以辨识类别的生活垃圾投入干垃圾容器内。" + WxMsg.LINE;
            case DEFAULT_TYPE:
            default:
                return "抱歉，未找到该类型垃圾信息。" + WxMsg.LINE;
        }
    }
}
