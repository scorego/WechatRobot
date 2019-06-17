package utils;

import config.QingWeatherCityConfig;
import entity.QingWeatherCityEntity;
import io.github.biezhi.wechat.utils.StringUtils;

import java.sql.SQLOutput;
import java.util.List;

public class CityIdUtil {

    private static volatile  CityIdUtil INSTANCE;

    private  CityIdUtil(){
        cityList = QingWeatherCityConfig.getInstance().getCityList();
        cityList.forEach(System.out::println);
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
        if (StringUtils.isEmpty(cityName)) {
            return null;
        }
        for (QingWeatherCityEntity cityEntity : cityList) {
            if (cityEntity.getCity_name().equals(cityName) && StringUtils.isNotEmpty(cityEntity.getCity_code())) {
                return cityEntity.getCity_code();
            }
        }
        return null;
    }
}
