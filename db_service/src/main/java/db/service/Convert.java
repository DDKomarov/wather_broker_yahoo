package db.service;

import db.entity.WeatherEntity;
import message_service.model.Weather;
import message_service.service.JsonService;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Convert {
    private static final String DATE_PATTERN = "dd MMM yyyy";

    @Inject
    private JsonService service;

    Weather transformJsonMessageToModel(String json)  {
        return service.readJsonMessage(json, Weather.class);
    }

    /**
     * Преобразование объекта, содержащего информацию о погоде, в сущность, сопоставленную с таблицей в базе данных
     *
     * @param weather Объект, содержащий информацию о погоде
     * @return Сущность, сопоставленная с таблицей в базе данных
     */
    WeatherEntity transformFromModelToEntity(Weather weather){
        WeatherEntity entity = new WeatherEntity();
        Date date = transformFromStringToDate(weather.getDate());
        entity.setDate(date);
        entity.setCity(weather.getCity());
        entity.setDay(weather.getDay());
        entity.setHighTemp(weather.getHighTemp());
        entity.setLowTemp(weather.getLowTemp());
        entity.setDescription(weather.getDescription());
        return entity;
    }

    /**
     * Преобразование строки, содержащей дату, в объект
     *
     * @param date Строка содержащая дату
     * @return Дата
     */
    Date transformFromStringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Internal service.service error", e);
        }
    }
}
