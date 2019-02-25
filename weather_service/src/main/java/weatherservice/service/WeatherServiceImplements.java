package weatherservice.service;

import entity.WeatherEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weatherservice.dao.WeatherDao;
import weatherservice.view.WeatherFilter;
import weatherservice.view.WeatherView;

@Service
public class WeatherServiceImplements implements WeatherService {
    private final Logger log = LoggerFactory.getLogger(WeatherServiceImplements.class);
    private WeatherDao dao;

    public WeatherServiceImplements(WeatherDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public WeatherView getWeatherByFilter(WeatherFilter filter) {
        if (filter == null) {
            throw new RuntimeException("Please enter the name of the city and date");
        }

        WeatherEntity entity = dao.getByCityAndDate(filter);
        log.debug("received weather forecast: {}", entity);
        return new Convert().transformFromEntityToView(entity);
    }
}
