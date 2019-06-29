import api.EveryDayHelloApi;
import api.WeatherApi;
import config.GlobalConfig;

public class HttpTest {


    public static void main(String[] args) {
        String[] key = {"北京市", "北京", "深圳市", "深圳", "朝阳"};
        for (String cityName : key) {
            System.out.print(cityName + ":  ");
            String response = WeatherApi.getWeatherByKeyword(cityName + "天气");
            System.out.println(response);
        }

        String everydayHello = EveryDayHelloApi.getGroupHelloMsg();
        System.out.println(everydayHello);
        System.out.println(GlobalConfig.getValue("autoReplyFriendMsg",""));

    }
}
