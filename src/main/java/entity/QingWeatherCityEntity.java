package entity;

import lombok.Data;

@Data
public class QingWeatherCityEntity {
    private int _id;
    private int id;
    private int pid;
    private String city_code;
    private String city_name;
}
