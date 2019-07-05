package robot.AToolBox;

import api.entity.RubbishToolBoxResponseEntity;
import cache.redis.RubbishLinkCacheFactory;
import cache.redis.RubbishTypeCacheFactory;
import cache.redis.entity.RubbishCacheEntity;
import cache.redis.entity.RubbishLinkCacheEntity;
import com.alibaba.fastjson.JSONObject;
import config.GlobalConfig;
import config.RedisConfig;
import cons.WxMsg;
import enums.RubbishType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import utils.HttpRequestUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/4 15:09
 */
@Slf4j
public class ToolBoxRubbish {

    private static final String TOOL_BOX_RUBBISH = GlobalConfig.getValue("rubbish.aToolBox", "");

    private static final boolean ENABLE_REDIS = RedisConfig.isRedisEnable();

    public static final String RUBBISH_LINK_NOT_EXIST = "RUBBISH_LINK_NOT_EXIST";

    public static final String RUBBISH_LINK_NO_RESPONSE = "RUBBISH_LINK_NO_RESPONSE";

    private static String getRubbishResult(@NonNull String rubbish) {

        String link = TOOL_BOX_RUBBISH;
        try {
            link += URLEncoder.encode(rubbish, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            link += rubbish;
        }
        String response = HttpRequestUtil.doGet(link);
        log.info("ToolBoxRubbish::getRubbishType, httpRequest >> rubbish: {}, response: {}", rubbish, response);
        return response;
    }


    public static RubbishType getRubbishType(String rubbish, RubbishToolBoxResponseEntity entity) {
        rubbish = rubbish.trim();
        if (StringUtils.isBlank(rubbish)) {
            cacheLinkRubbish(rubbish, RUBBISH_LINK_NOT_EXIST);
            return RubbishType.NOT_EXISTS;
        }
        String rubbishResult = getRubbishResult(rubbish);
        if (StringUtils.isBlank(rubbishResult)) {
            // 接口无响应
            cacheLinkRubbish(rubbish, RUBBISH_LINK_NO_RESPONSE);
            return RubbishType.NO_RESPONSE;
        } else if (!rubbishResult.startsWith("{")) {
            // 接口没返回有效数据
            cacheLinkRubbish(rubbish, RUBBISH_LINK_NOT_EXIST);
            return RubbishType.NOT_EXISTS;
        }

        // 接口返回了有效数据
        Map<String, Map<String, String>> result = JSONObject.parseObject(rubbishResult, Map.class);

        if (result == null || result.isEmpty()) {
            cacheLinkRubbish(rubbish, RUBBISH_LINK_NOT_EXIST);
            return RubbishType.NOT_EXISTS;
        }

        String linkRubbishList = getLinkRubbishList(rubbish, result);
        entity.setLinkRubbishString(linkRubbishList);

        if (ENABLE_REDIS) {
            cacheLinkRubbish(rubbish, linkRubbishList);
            cacheAllResult(result);
        }
        String resultType = null;
        for (Map.Entry<String, Map<String, String>> mapEntry : result.entrySet()) {
            if (rubbish.equals(mapEntry.getValue().getOrDefault("name", ""))) {
                resultType = mapEntry.getValue().getOrDefault("type", "");
                break;
            }
        }
        return getType(resultType);
    }

    private static RubbishType getType(String stringType) {
        if (stringType == null) {
            return RubbishType.NOT_EXISTS;
        }
        switch (stringType) {
            case "干垃圾":
                return RubbishType.RESIDUAL_WASTE;
            case "可回收物":
                return RubbishType.RECYCLABLE_WASTE;
            case "湿垃圾":
                return RubbishType.HOUSEHOLD_FOOD_WASTE;
            case "有害垃圾":
                return RubbishType.HAZARDOUS_WASTE;
            default:
                return RubbishType.NOT_EXISTS;
        }
    }

    private static void cacheAllResult(Map<String, Map<String, String>> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Map<String, String>> mapEntry : map.entrySet()) {
            RubbishCacheEntity rubbishCacheEntity = RubbishTypeCacheFactory.getRubbishCacheEntity(mapEntry.getValue().getOrDefault("name", ""));
            rubbishCacheEntity.setValue(getType(mapEntry.getValue().getOrDefault("type", ""))).save();
        }
    }

    private static void cacheLinkRubbish(String rubbish, String linkRubbishList) {
        RubbishLinkCacheEntity rubbishLinkListCache = RubbishLinkCacheFactory.getRubbishLinkCache(rubbish);
        rubbishLinkListCache.setValue(linkRubbishList).save();
    }

    public static String getLinkRubbish(String rubbish) {
        rubbish = rubbish.trim();
        if (StringUtils.isBlank(rubbish)) {
            return RUBBISH_LINK_NOT_EXIST;
        }
        String rubbishResult = getRubbishResult(rubbish);
        if (StringUtils.isBlank(rubbishResult)) {
            // 接口无响应
            return RUBBISH_LINK_NO_RESPONSE;
        } else if (!rubbishResult.startsWith("{")) {
            // 接口没返回有效数据
            return RUBBISH_LINK_NOT_EXIST;
        }

        // 接口返回了有效数据
        Map<String, Map<String, String>> resultMap = JSONObject.parseObject(rubbishResult, Map.class);

        if (resultMap == null || resultMap.isEmpty()) {
            return RUBBISH_LINK_NOT_EXIST;
        }

        return getLinkRubbishList(rubbish, resultMap);
    }

    private static String getLinkRubbishList(String rubbish, Map<String, Map<String, String>> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder(" ");
        for (Map.Entry<String, Map<String, String>> mapEntry : map.entrySet()) {
            if (!rubbish.equals(mapEntry.getValue().getOrDefault("name", rubbish))) {
                result.append(mapEntry.getValue().getOrDefault("name", "")).append(" ");
            }
        }
        String trim = result.toString().trim();
        return StringUtils.isEmpty(trim) ? "" : trim + WxMsg.LINE;
    }

}
