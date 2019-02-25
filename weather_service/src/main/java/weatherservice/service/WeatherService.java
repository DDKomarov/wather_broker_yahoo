package weatherservice.service;

import org.springframework.stereotype.Service;
import weatherservice.view.WeatherFilter;
import weatherservice.view.WeatherView;

public interface WeatherService {
    WeatherView getWeatherByFilter(WeatherFilter filter);
}
