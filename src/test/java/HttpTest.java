import api.NewsApi;
import api.RubbishApi;
import robot.RollToolsApi.RollNews;
import robot.Zhihu.ZhihuHot;

import java.util.Arrays;

public class HttpTest {


    public static void main(String[] args) {
//        String[] key = {"北京市", "北京", "深圳市", "深圳", "朝阳"};
//        for (String cityName : key) {
//            System.out.print(cityName + ":  ");
//            String response = WeatherApi.getWeatherByKeyword(cityName + "天气");
//            System.out.println(response);
//        }
//
//        String everydayHello = EveryDayHelloApi.getGroupHelloMsg();

//        System.out.println(RubbishApp.getRubbishType("电池").getName());

//        String arr = "玉米塑料袋#龙虾壳#米饭#牛奶#矿泉水瓶#玉米#电池";
//        Arrays.stream(arr.split("#")).forEach(a -> System.out.println(RubbishApi.classifyRubbish(a)));

//        String s = NewsApi.getTodayNews("新闻");
//        System.out.println(s);
        String zhihuHot = ZhihuHot.getZhihuHot();
        System.out.println(zhihuHot);
    }
}
