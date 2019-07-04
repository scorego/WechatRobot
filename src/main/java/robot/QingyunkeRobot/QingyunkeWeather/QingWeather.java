package robot.QingyunkeRobot.QingyunkeWeather;

import config.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import robot.QingyunkeRobot.QingyunkeWeather.entity.QingWeatherEntity;
import com.alibaba.fastjson.JSON;
import utils.HttpRequestUtil;

public class QingWeather {

    private static final String QING_WEATHER = GlobalConfig.getValue("QingyunkeRobot.weather", "");

    public static String getWeatherByCityId(String cityId) {
        if (StringUtils.isBlank(cityId)) {
            return null;
        }
        String response = HttpRequestUtil.doGet(QING_WEATHER + cityId);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        QingWeatherEntity weather = JSON.parseObject(response, QingWeatherEntity.class);

        return weather.isValid() ? weather.getWeather() : null;
    }

    public static String getWeatherByKeyword(String keyWord) {
        if (StringUtils.isBlank(keyWord)) {
            return null;
        }
        String cityName = keyWord.substring(0, keyWord.length() - 2);
        return QingWeather.getWeatherByCityName(cityName);
    }

    public static String getWeatherByCityName(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            return null;
        }

        // 部分地区有问题，已知的有：北京朝阳区和辽宁朝阳市；福田区；浦东新区
        switch (cityName) {
            case "朝阳市":
                // 查天气时，朝阳默认为北京朝阳区；朝阳市为辽宁朝阳市
                return QingWeather.getWeatherByCityId("101071201");
            case "福田区":
            case "福田":
                return null;
            case "浦东新区":
            case "浦东":
                return QingWeather.getWeatherByCityId("101021300");
            case "帝都":
                // 帝都默认为北京
                return QingWeather.getWeatherByCityId("101010100");
            case "魔都":
                // 魔都默认为上海
                return QingWeather.getWeatherByCityId("101020100");
            case "长安":
                // 长安默认为西安
                return QingWeather.getWeatherByCityId("101110101");
        }

        if (cityName.endsWith("市") || cityName.endsWith("县") || cityName.endsWith("区")) {
            cityName = cityName.substring(0, cityName.length() - 1);
        }
        String cityId = QingWeatherCityId.getInstance().getCityId(cityName);
        return QingWeather.getWeatherByCityId(cityId);
    }
}
