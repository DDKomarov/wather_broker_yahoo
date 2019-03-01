package service;

import view.WeatherFilter;
import view.WeatherView;

public interface WeatherService {
    WeatherView getWeatherByFilter(WeatherFilter filter);
}
