package robot.QingyunkeRobot;

import config.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import robot.QingyunkeRobot.entity.QingWeatherEntity;
import com.alibaba.fastjson.JSON;
import utils.CityIdUtil;
import utils.HttpRequestUtil;

public class QingWeather {

    private static final String QING_WEATHER = GlobalConfig.getValue("QingyunkeRobot.weather","");

    public static String getWeatherByCityId(String cityId) {
        if (StringUtils.isBlank(cityId)) {
            return null;
        }
        String response = HttpRequestUtil.doGet(QING_WEATHER + cityId);
        if (StringUtils.isBlank(response)){
            return null;
        }
        QingWeatherEntity weather = JSON.parseObject(response, QingWeatherEntity.class);

        return weather.isValid() ? weather.getWeather() : null;
    }

    public static String getWeatherByKeyword(String keyWord) {
        if (StringUtils.isBlank(keyWord)) {
            return null;
        }
        String cityName = keyWord.startsWith("天气") ?
                keyWord.substring(2) : keyWord.substring(0, keyWord.length() - 2);
        return QingWeather.getWeatherByCityName(cityName);
    }
    public static String getWeatherByCityName(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            return null;
        }

        // 部分地区有问题，已知的有：北京朝阳区和辽宁朝阳市；福田区；浦东新区
        switch (cityName){
            case "朝阳区":
            case "朝阳":
                return QingWeather.getWeatherByCityId("101010300");
            case "福田区":
                return null;
            case "浦东新区":
            case "浦东":
                return QingWeather.getWeatherByCityId("101021300");
        }

        if (cityName.endsWith("市") || cityName.endsWith("县")) {
            cityName = cityName.substring(0, cityName.length() - 1);
        }
        String cityId = CityIdUtil.getInstance().getCityId(cityName);
        return QingWeather.getWeatherByCityId(cityId);
    }
}
