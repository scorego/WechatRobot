package robot.RubbishClassificationApp;

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
        String link = LA_JI_FEN_LEI_APP + rubbish;
        String result = HttpRequestUtil.doGet(link);
        log.info("RubbishApp::getRubbishType, rubbish: {}, result: {}", rubbish, result);
        if (StringUtils.isBlank(result)) {
            return RubbishType.DEFAULT_TYPE;
        }
        if (result.contains("干垃圾是指")) {
            return RubbishType.RESIDUAL_WASTE;
        } else if (result.contains("湿垃圾是指")) {
            return RubbishType.HOUSEHOLD_FOOD_WASTE;
        } else if (result.contains("有害垃圾是指")) {
            return RubbishType.HAZARDOUS_WASTE;
        } else if (result.contains("可回收物是指")) {
            return RubbishType.RECYCLABLE_WASTE;
        } else {
            return RubbishType.DEFAULT_TYPE;
        }
    }


}
