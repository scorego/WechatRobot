package robot.RollToolsApi;

import config.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import robot.RollToolsApi.entity.RollWeatherEntity;
import com.alibaba.fastjson.JSON;
import utils.HttpRequestUtil;

public class RollWeather {

    private static final String ROLL_WEATHER = GlobalConfig.getValue("RollToolsApi.weather","");


    public static String getWeatherByKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        String cityName = keyword.startsWith("天气") ?
                keyword.substring(2) : keyword.substring(0, keyword.length() - 2);
        return RollWeather.getWeatherByCityName(cityName);
    }

    public static String getWeatherByCityName(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            return null;
        }
        String response = HttpRequestUtil.doGet(ROLL_WEATHER + cityName);
        if (StringUtils.isBlank(response)){
            return null;
        }
        RollWeatherEntity weather = JSON.parseObject(response, RollWeatherEntity.class);
        return weather.getWeatherDefaultNull();
    }


}
