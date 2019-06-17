package robot.QingyunkeRobot;

import config.GlobalConfig;
import robot.QingyunkeRobot.entity.QingWeatherEntity;
import com.alibaba.fastjson.JSON;
import io.github.biezhi.wechat.utils.StringUtils;
import utils.CityIdUtil;
import utils.HttpRequestUtil;

public class QingWeather {

    private static final String QING_WEATHER = GlobalConfig.getValue("QingyunkeRobot.weather","");

    public static String getWeatherByCityId(String cityId) {
        if (StringUtils.isEmpty(cityId)) {
            return null;
        }
        String response = HttpRequestUtil.doGet(QING_WEATHER + cityId);
        if (StringUtils.isEmpty(response)){
            return null;
        }
        QingWeatherEntity weather = JSON.parseObject(response, QingWeatherEntity.class);

        return weather.isValid() ? weather.getWeather() : null;
    }

    public static String getWeatherByKeyword(String keyWord) {
        if (StringUtils.isEmpty(keyWord)) {
            return null;
        }
        String cityName = keyWord.startsWith("天气") ?
                keyWord.substring(2) : keyWord.substring(0, keyWord.length() - 2);
        return QingWeather.getWeatherByCityName(cityName);
    }
    public static String getWeatherByCityName(String cityName) {
        if (StringUtils.isEmpty(cityName)) {
            return null;
        }
        if (cityName.endsWith("市")) {
            cityName = cityName.substring(0, cityName.length() - 1);
        }
        if (cityName.endsWith("区")) {
            cityName = cityName.substring(0, cityName.length() - 1);
        }
        if (cityName.endsWith("县")) {
            cityName = cityName.substring(0, cityName.length() - 1);
        }
        String cityId = CityIdUtil.getInstance().getCityId(cityName);
        return QingWeather.getWeatherByCityId(cityId);
    }
}
