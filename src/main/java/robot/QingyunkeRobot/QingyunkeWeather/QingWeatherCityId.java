package robot.QingyunkeRobot.QingyunkeWeather;

import config.QingWeatherCityConfig;
import robot.QingyunkeRobot.QingyunkeWeather.entity.QingWeatherCityEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QingWeatherCityId {

    private static volatile QingWeatherCityId INSTANCE;

    private QingWeatherCityId() {
        cityList = QingWeatherCityConfig.getInstance().getCityList();
    }

    public static QingWeatherCityId getInstance() {
        if (INSTANCE == null) {
            synchronized (QingWeatherCityConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QingWeatherCityId();
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
        return null;
    }
}
