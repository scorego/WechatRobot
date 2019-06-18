package utils;

import config.QingWeatherCityConfig;
import entity.QingWeatherCityEntity;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

public class CityIdUtil {

    private static volatile  CityIdUtil INSTANCE;

    private  CityIdUtil(){
        cityList = QingWeatherCityConfig.getInstance().getCityList();
    }

    public static CityIdUtil getInstance(){
        if (INSTANCE == null){
            synchronized (QingWeatherCityConfig.class){
                if (INSTANCE == null){
                    INSTANCE = new CityIdUtil();
                }
            }
        }
        return INSTANCE;
    }


    private  List<QingWeatherCityEntity> cityList ;


    public  String getCityId(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            return null;
        }
        for (QingWeatherCityEntity cityEntity : cityList) {
            if (cityEntity.getCity_name().equals(cityName) && StringUtils.isNotBlank(cityEntity.getCity_code())) {
                return cityEntity.getCity_code();
            }
        }
        return null;
    }
}
