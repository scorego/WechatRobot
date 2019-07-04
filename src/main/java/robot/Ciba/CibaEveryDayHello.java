package robot.Ciba;

import com.alibaba.fastjson.JSON;
import config.GlobalConfig;
import robot.Ciba.entity.CibaEveryDayHelloEntity;
import utils.HttpRequestUtil;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 11:35
 */
public class CibaEveryDayHello {

    private static final String CIBA_EVERYDAY = GlobalConfig.getValue("ciba.everydayhello", "");

    public static String getCibaEveryday() {
        String response = HttpRequestUtil.doGet(CIBA_EVERYDAY);
        CibaEveryDayHelloEntity everyDayEntity = JSON.parseObject(response, CibaEveryDayHelloEntity.class);
        return everyDayEntity == null ? null : everyDayEntity.getSentence();
    }
}
