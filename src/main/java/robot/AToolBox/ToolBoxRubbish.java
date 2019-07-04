package robot.AToolBox;

import cache.redis.RubbishTypeCache;
import cache.redis.entity.RubbishCacheEntity;
import com.alibaba.fastjson.JSONObject;
import config.GlobalConfig;
import config.RedisConfig;
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

    private static String getRubbishResult(@NonNull String rubbish) {

        String link = TOOL_BOX_RUBBISH;
        try {
            link += URLEncoder.encode(rubbish, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            link += rubbish;
        }
        String response = HttpRequestUtil.doGet(link);
        log.info("ToolBoxRubbish::getRubbishType, httpRequest >> rubbish: {}, response: {}", rubbish, response);
        if ("[]".equals(response)) {
            response = null;
        }
        return response;
    }

    public static RubbishType getRubbishType(String rubbish) {
        rubbish = rubbish.trim();
        if (StringUtils.isBlank(rubbish)) {
            return RubbishType.DEFAULT_TYPE;
        }
        String rubbishResult = getRubbishResult(rubbish);
        Map<String, Map<String, String>> result = JSONObject.parseObject(rubbishResult, Map.class);

        if (ENABLE_REDIS) {
            cacheAllResult(result);
        }

        if (result == null || result.isEmpty()) {
            return RubbishType.DEFAULT_TYPE;
        }

        String resultType = null;
        for (Map.Entry<String, Map<String, String>> mapEntry : result.entrySet()) {
            if (rubbish.equals(mapEntry.getValue().get("name"))) {
                resultType = mapEntry.getValue().get("type");
            }
        }
        return (resultType == null) ? RubbishType.DEFAULT_TYPE : getType(resultType);
    }

    private static RubbishType getType(String stringType) {
        if (stringType == null) {
            return RubbishType.DEFAULT_TYPE;
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
                return RubbishType.DEFAULT_TYPE;
        }
    }

    private static void cacheAllResult(Map<String, Map<String, String>> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Map<String, String>> mapEntry : map.entrySet()) {
            RubbishCacheEntity rubbishCacheEntity = RubbishTypeCache.getRubbishCacheEntity(mapEntry.getValue().get("name"));
            rubbishCacheEntity.setValue(getType(mapEntry.getValue().get("type"))).save();
        }
    }

}
