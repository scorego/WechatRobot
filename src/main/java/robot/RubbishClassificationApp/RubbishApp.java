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
        if (StringUtils.isBlank(rubbish)) {
            return null;
        }

        String link = LA_JI_FEN_LEI_APP + rubbish;
        String response = HttpRequestUtil.doGet(link);
        log.info("RubbishApp::getRubbishType, httpRequest >> rubbish: {}, response: {}", rubbish, response);

        RubbishType rubbishType;
        if (StringUtils.isBlank(response)) {
            rubbishType = RubbishType.DEFAULT_TYPE;
        } else if (response.contains("干垃圾是指")) {
            rubbishType = RubbishType.RESIDUAL_WASTE;
        } else if (response.contains("湿垃圾是指")) {
            rubbishType = RubbishType.HOUSEHOLD_FOOD_WASTE;
        } else if (response.contains("有害垃圾是指")) {
            rubbishType = RubbishType.HAZARDOUS_WASTE;
        } else if (response.contains("可回收物是指")) {
            return RubbishType.RECYCLABLE_WASTE;
        } else {
            rubbishType = RubbishType.DEFAULT_TYPE;
        }
        log.info("RubbishApp::getRubbishType, httpRequest >> rubbish: {}, result: {}", rubbish, rubbishType);
        return rubbishType;
    }


}
