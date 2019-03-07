package weather_service.dao;

import db.entity.WeatherEntity;
import weather_service.view.WeatherFilter;

public interface WeatherDao {
    WeatherEntity getByCityAndDate(WeatherFilter filter);

}
