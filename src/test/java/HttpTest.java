import robot.QingyunkeRobot.QingWeather;
import utils.CityIdUtil;

import java.net.URL;

public class HttpTest {


    public static void main(String[] args) {
        String[] key = {"北京市", "北京", "深圳市", "深圳", "朝阳区"};
        for (String cityName : key) {
            System.out.print(cityName + ":  ");

            if (cityName.endsWith("市")) {
                cityName = cityName.substring(0, cityName.length() - 1);
            }
            String cityId = CityIdUtil.getInstance().getCityId(cityName);
            String response = QingWeather.getWeatherByCityId(cityId);
            System.out.println(cityId + " -- " + response);
        }

//        String classPath = System.getProperty("user.dir");
//        URL classPath = HttpTest.class.getResource("");
//        String path = classPath.getPath();
//        System.out.println(path);

    }


}
