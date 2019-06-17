package robot.RollToolsApi;

import config.GlobalConfig;
import robot.RollToolsApi.entity.RollWeatherEntity;
import com.alibaba.fastjson.JSON;
import io.github.biezhi.wechat.utils.StringUtils;
import utils.HttpRequestUtil;

public class RollWeather {

    private static final String ROLL_WEATHER = GlobalConfig.getValue("rollWeather","");


    public static String getWeatherByKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        String cityName = keyword.startsWith("天气") ?
                keyword.substring(2) : keyword.substring(0, keyword.length() - 2);
        return RollWeather.getWeatherByCityName(cityName);
    }

    public static String getWeatherByCityName(String cityName) {
        if (StringUtils.isEmpty(cityName)) {
            return null;
        }
        String response = HttpRequestUtil.doGet(ROLL_WEATHER + cityName);
        RollWeatherEntity weather = JSON.parseObject(response, RollWeatherEntity.class);
        return weather.getWeatherDefaultNull();
    }


}
