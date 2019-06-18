package api;

import config.GlobalConfig;
import robot.Ciba.CibaEveryDayHello;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Javior
 * @date 2019/6/18 12:27
 */
public class EveryDayHelloApi {

    private static final String EVERYDAY_HELLO = GlobalConfig.getValue("everydayHelloApi", "");


    public static String getEverydayHello() {
        switch (EVERYDAY_HELLO) {
            case "Ciba":
                return CibaEveryDayHello.getCibaEveryday();
            default:
                return null;
        }
    }

}
