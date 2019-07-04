package api;

import cache.RCacheEntity;
import config.RedisConfig;
import cons.WxMsg;
import enums.RubbishType;
import lombok.extern.slf4j.Slf4j;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import org.apache.commons.lang3.StringUtils;
import robot.RubbishClassificationApp.RubbishApp;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/1 21:41
 */
@Slf4j
public class RubbishClassificationApi {

    private static final boolean REDIS_ENABLE = RedisConfig.isRedisEnable();

    /**
     * 垃圾分类缓存默认30天
     */
    private static final int RUBBISH_CACHE_DURATION_SECONDS = 60 * 60 * 24 * 30;

    public static String dealRubbishMsg(WXMessage message) {
        String content = message.content.substring(1).trim();
        return classifyRubbish(content);
    }

    public static String classifyRubbish(String rubbish) {
        if (StringUtils.isBlank(rubbish)) {
            return "生活垃圾主要包括有害垃圾、可回收物、湿垃圾/厨余垃圾、干垃圾/其他垃圾。" + WxMsg.LINE
                    + "7月1日，《上海市生活垃圾管理条例》正式施行。个人混合投放垃圾，最高可罚款200元；单位混合投放或混合运输垃圾，最高可罚5万元。" + WxMsg.LINE
                    + "垃圾分类，从我做起。" + WxMsg.LINE;
        }
        RubbishType rubbishType = checkRubbishType(rubbish.trim());
        String tip = getRubbishTypeTip(rubbishType);
        switch (rubbishType) {
            case HAZARDOUS_WASTE:
            case RECYCLABLE_WASTE:
            case HOUSEHOLD_FOOD_WASTE:
            case RESIDUAL_WASTE:
                return "【分类结果】" + rubbish + "属于" + rubbishType.getName() + "。" + WxMsg.LINE + tip;
            case DEFAULT_TYPE:
            default:
                return "抱歉，未找到\"" + rubbish + "\"分类信息。" + WxMsg.LINE;
        }
    }

    private static RubbishType checkRubbishType(String rubbish) {
        if (!REDIS_ENABLE) {
            return RubbishApp.getRubbishType(rubbish);
        }

        // 1. 构造缓存key
        RCacheEntity.KeyBuilder rubbishTypeKeyBuilder = new RCacheEntity.KeyBuilder("rubbishType").addParam("item", rubbish);
        RCacheEntity rCacheEntity = new RCacheEntity(rubbishTypeKeyBuilder, RUBBISH_CACHE_DURATION_SECONDS);

        // 2. 有缓存，更新缓存过期时间，返回缓存结果
        String stringType;
        RubbishType rubbishType;
        if ((stringType = rCacheEntity.get()) != null) {
            rubbishType = RubbishType.findByValue(Integer.valueOf(stringType));
            if (rubbishType != RubbishType.DEFAULT_TYPE) {
                // 查询不到结果的记录不更新缓存过期时间
                rCacheEntity.save();
            }
            log.info("RubbishClassificationApi::checkRubbishType, cache >> rubbish: {}, result: {}", rubbish, rubbishType);
            return rubbishType;
        }

        // 3. 无缓存，查询结果，存入缓存
        rubbishType = RubbishApp.getRubbishType(rubbish);
        if (rubbishType == null) {
            rubbishType = RubbishType.DEFAULT_TYPE;
        }
        stringType = String.valueOf(rubbishType.getValue());
        rCacheEntity.setValue(stringType).save();
        log.info("RubbishClassificationApi::checkRubbishType, cache insert >> rubbish: {}, result: {}", rubbish, rubbishType);

        return rubbishType;
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
                return "【分类介绍】湿垃圾/厨余垃圾，指日常生活垃圾产生的容易腐烂的生物质废弃物。" + WxMsg.LINE
                        + "【投放要求】纯流质的食物垃圾（如牛奶）应直接倒进下水口；"
                        + "有包装物的湿垃圾应将包装物去除后分类投放；包装物请投放到对应的可回收物或干垃圾容器。" + WxMsg.LINE;
            case RESIDUAL_WASTE:
                return "【分类介绍】干垃圾/其他垃圾，指除可回收物、有害垃圾、湿垃圾以外的其它生活废弃物。" + WxMsg.LINE
                        + "【投放要求】" + "尽量沥干水分；" + "难以辨识类别的生活垃圾投入干垃圾容器内。" + WxMsg.LINE;
            case DEFAULT_TYPE:
            default:
                return "";
        }
    }
}
