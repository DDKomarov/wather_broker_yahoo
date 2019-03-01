package dao;

import entity.WeatherEntity;
import view.WeatherFilter;

public interface WeatherDao {
    WeatherEntity getByCityAndDate(WeatherFilter filter);

}
