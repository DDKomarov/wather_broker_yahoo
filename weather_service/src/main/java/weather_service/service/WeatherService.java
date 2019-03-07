package weather_service.service;

import weather_service.view.WeatherFilter;
import weather_service.view.WeatherView;

public interface WeatherService {
    WeatherView getWeatherByFilter(WeatherFilter filter);
}
