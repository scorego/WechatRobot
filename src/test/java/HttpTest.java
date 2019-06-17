import api.WeatherApi;

public class HttpTest {


    public static void main(String[] args) {
        String[] key = {"北京市", "北京", "深圳市", "深圳", "朝阳区"};
        for (String cityName : key) {
            System.out.print(cityName + ":  ");
            String response = WeatherApi.getWeatherByKeyword(cityName + "天气");
            System.out.println(response);
        }
    }
}
