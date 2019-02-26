package weatherservice.dao;

import entity.WeatherEntity;
import weatherservice.view.WeatherFilter;

public interface WeatherDao {
    WeatherEntity getByCityAndDate(WeatherFilter filter);

}
