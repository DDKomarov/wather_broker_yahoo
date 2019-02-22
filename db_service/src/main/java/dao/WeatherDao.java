package dao;

import entity.WeatherEntity;

import java.util.Date;

public interface WeatherDao {
    void save(WeatherEntity entity);

    boolean isForecastDuplicate(Date date, String city);
}
