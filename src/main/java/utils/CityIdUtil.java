package utils;

import config.QingWeatherCityConfig;
import entity.QingWeatherCityEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CityIdUtil {

    private static volatile CityIdUtil INSTANCE;

    private CityIdUtil() {
        cityList = QingWeatherCityConfig.getInstance().getCityList();
    }

    public static CityIdUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (QingWeatherCityConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CityIdUtil();
                }
            }
        }
        return INSTANCE;
    }


    private List<QingWeatherCityEntity> cityList;

    private Map<String, String> cityIdMap = new ConcurrentHashMap<>();


    public String getCityId(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            return null;
        }
        if (cityIdMap.containsKey(cityName)) {
            return cityIdMap.get(cityName);
        }

        for (QingWeatherCityEntity cityEntity : cityList) {
            if (cityEntity.getCity_name().equals(cityName) && StringUtils.isNotBlank(cityEntity.getCity_code())) {
                cityIdMap.put(cityName, cityEntity.getCity_code());
                return cityEntity.getCity_code();
            }
        }
        cityIdMap.put(cityName, null);
        return null;
    }
}
