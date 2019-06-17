package config;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import entity.QingWeatherCityEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class QingWeatherCityConfig {

    private static final String CITY_JSON_FILE = GlobalConfig.getValue("cityJsonFile", "");

    @Getter
    private List<QingWeatherCityEntity> cityList;

    private static volatile QingWeatherCityConfig INSTANCE;

    private QingWeatherCityConfig(){
        String filePath = CITY_JSON_FILE;
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new FileInputStream(filePath);
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            is.close();
            cityList = JSON.parseArray(sb.toString(), QingWeatherCityEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (cityList == null) {
                cityList = new ArrayList<>(0);
            }
        }
    }

    public static QingWeatherCityConfig getInstance(){
        if (INSTANCE == null){
            synchronized (QingWeatherCityConfig.class){
                if (INSTANCE == null){
                    INSTANCE = new QingWeatherCityConfig();
                }
            }
        }
        return INSTANCE;
    }
}
