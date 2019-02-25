package weatherservice.dao;

import entity.WeatherEntity;
import org.springframework.stereotype.Repository;
import weatherservice.view.WeatherFilter;

@Repository
public class WeatherDaoImplements implements WeatherDao{
    @Override
    public WeatherEntity getByCityAndDate(WeatherFilter filter) {
        return null;
    }
}
