import robot.AToolBox.ToolBoxRubbish;

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
//        System.out.println(everydayHello);
//        System.out.println(GlobalConfig.getValue("autoReplyFriendMsg",""));

//        System.out.println(RubbishApp.getRubbishType("电池").getName());

//        RCacheEntity.KeyBuilder keyBuilder = new RCacheEntity.KeyBuilder("test").addParam("aa", 1);
//        RCacheEntity rCacheEntity = new RCacheEntity(keyBuilder);
////        System.out.println(rCacheEntity.setValue("a").setex());
//        System.out.println(rCacheEntity.get() == null);
        Arrays.stream(new String[]{"牛奶", "milk","塑料袋"}).forEach(a -> {
                    System.out.println(ToolBoxRubbish.getRubbishType(a));
                }
        );

    }
}
