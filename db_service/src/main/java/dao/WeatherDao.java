package dao;

import entity.Weather;

import java.util.Date;

public interface WeatherDao {
    void save(Weather entity);

    boolean isForecastDuplicate(Date date, String city);
}
