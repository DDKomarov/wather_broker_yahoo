package weatherservice.dao;

import entity.WeatherEntity;
import org.springframework.stereotype.Repository;
import weatherservice.view.WeatherFilter;

public interface WeatherDao {
    WeatherEntity getByCityAndDate(WeatherFilter filter);

}
