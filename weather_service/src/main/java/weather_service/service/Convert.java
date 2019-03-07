package weather_service.service;

import db.entity.WeatherEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weather_service.view.WeatherView;

class Convert {
    private final Logger log = LoggerFactory.getLogger(WeatherServiceImplements.class);


    /**
     * Преобразование сущности, связанной с таблицей в базе данных, в объект содержащий информацию о погоде
     *
     * @param entity Сущность, связанная с таблицей в базе данных
     * @return Объект содержащий информацию о погоде
     */

    WeatherView transformFromEntityToView(WeatherEntity entity) {
        WeatherView view = new WeatherView();
        view.city = entity.getCity();
        view.date = entity.getDate();
        view.day = entity.getDay();
        view.highTemp = entity.getHighTemp();
        view.lowTemp = entity.getLowTemp();
        view.description = entity.getDescription();
        log.debug("view after transform from entity: {}", view);
        return view;
    }

}
