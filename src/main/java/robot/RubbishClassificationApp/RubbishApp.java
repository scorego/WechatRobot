package robot.RubbishClassificationApp;

import cache.RCacheEntity;
import config.GlobalConfig;
import enums.RubbishType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtil;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/1 21:51
 */
public class RubbishApp {

    private static final Logger log = LoggerFactory.getLogger(RubbishApp.class);

    private static final String LA_JI_FEN_LEI_APP = GlobalConfig.getValue("rubbish.classification", "");

    public static RubbishType getRubbishType(String rubbish) {
        rubbish = rubbish.trim();
        if (StringUtils.isBlank(rubbish)) {
            return null;
        }
        RCacheEntity.KeyBuilder rubbishTypeKeyBuilder = new RCacheEntity.KeyBuilder("rubbishType").addParam("item", rubbish);
        RCacheEntity rCacheEntity = new RCacheEntity(rubbishTypeKeyBuilder);
        // 1.有缓存
        String stringType;
        RubbishType rubbishType;
        if ((stringType = rCacheEntity.get()) != null) {
            rubbishType = RubbishType.findByValue(Integer.valueOf(stringType));
            log.info("RubbishApp::getRubbishType, cache >> rubbish: {}, result: {}", rubbish, rubbishType);
            return rubbishType;
        }
        // 2. 无缓存
        String link = LA_JI_FEN_LEI_APP + rubbish;
        String result = HttpRequestUtil.doGet(link);
        log.info("RubbishApp::getRubbishType, httpRequest >> rubbish: {}, result: {}", rubbish, result);

        if (StringUtils.isBlank(result)) {
            rubbishType = RubbishType.DEFAULT_TYPE;
        } else if (result.contains("干垃圾是指")) {
            rubbishType = RubbishType.RESIDUAL_WASTE;
        } else if (result.contains("湿垃圾是指")) {
            rubbishType = RubbishType.HOUSEHOLD_FOOD_WASTE;
        } else if (result.contains("有害垃圾是指")) {
            rubbishType = RubbishType.HAZARDOUS_WASTE;
        } else if (result.contains("可回收物是指")) {
            return RubbishType.RECYCLABLE_WASTE;
        } else {
            rubbishType = RubbishType.DEFAULT_TYPE;
        }

        // 缓存结果
        stringType = String.valueOf(rubbishType.getValue());
        rCacheEntity.setValue(stringType).save();

        return rubbishType;
    }


}
