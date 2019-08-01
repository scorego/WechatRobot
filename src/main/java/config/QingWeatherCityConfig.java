package config;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import robot.QingyunkeRobot.QingyunkeWeather.entity.QingWeatherCityEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QingWeatherCityConfig {

    private static final String CITY_JSON_FILE = GlobalConfig.getValue("cityJsonFile", "");

    @Getter
    private List<QingWeatherCityEntity> cityList;

    private static volatile QingWeatherCityConfig INSTANCE;

    private QingWeatherCityConfig() {
        String filePath = QingWeatherCityConfig.class.getClassLoader().getResource("").getPath() + CITY_JSON_FILE;
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new FileInputStream(filePath)) {
            String line;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            cityList = JSON.parseArray(sb.toString(), QingWeatherCityEntity.class);
        } catch (IOException e) {
            log.error("QingWeather::读取city数据出错!", e);
        } finally {
            if (cityList == null) {
                cityList = new ArrayList<>(0);
            }
        }
    }

    public static QingWeatherCityConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (QingWeatherCityConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QingWeatherCityConfig();
                }
            }
        }
        return INSTANCE;
    }
}
