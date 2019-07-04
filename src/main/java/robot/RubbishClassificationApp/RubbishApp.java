package robot.RubbishClassificationApp;


import config.GlobalConfig;
import enums.RubbishType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import utils.HttpRequestUtil;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/7/1 21:51
 */
@Slf4j
public class RubbishApp {

    private static final String LA_JI_FEN_LEI_APP = GlobalConfig.getValue("rubbish.LaJjFenLeiAPP", "");

    public static RubbishType getRubbishType(String rubbish) {
        if (StringUtils.isBlank(rubbish)) {
            return null;
        }
        String link = LA_JI_FEN_LEI_APP + rubbish;
        String response = HttpRequestUtil.doGet(link);
        log.info("RubbishApp::getRubbishType, httpRequest >> rubbish: {}, response: {}", rubbish, response);

        RubbishType rubbishType;
        if (StringUtils.isBlank(response)) {
            rubbishType = RubbishType.NOT_EXISTS;
        } else if (response.contains("干垃圾是指")) {
            rubbishType = RubbishType.RESIDUAL_WASTE;
        } else if (response.contains("湿垃圾是指")) {
            rubbishType = RubbishType.HOUSEHOLD_FOOD_WASTE;
        } else if (response.contains("有害垃圾是指")) {
            rubbishType = RubbishType.HAZARDOUS_WASTE;
        } else if (response.contains("可回收物是指")) {
            return RubbishType.RECYCLABLE_WASTE;
        } else {
            rubbishType = RubbishType.NOT_EXISTS;
        }
        log.info("RubbishApp::getRubbishType, httpRequest >> rubbish: {}, result: {}", rubbish, rubbishType);
        return rubbishType;
    }


}
