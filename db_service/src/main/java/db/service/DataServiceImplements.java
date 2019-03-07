package db.service;

import db.dao.WeatherDao;
import db.entity.WeatherEntity;
import message_service.model.Weather;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataServiceImplements implements DataService {
    private final Logger log = LoggerFactory.getLogger(DataServiceImplements.class);
    private WeatherDao dao;
    private Convert convert;
    private static final String DATE_PATTERN = "dd MMM yyyy";


    @Inject
    public DataServiceImplements(WeatherDao dao, Convert convert) {
        this.dao = dao;
        this.convert = convert;
    }

    @Override
    @Transactional
    public void save(String json) {
        if (StringUtils.isNoneBlank(json)) {
            throw new RuntimeException("message is null");
        }
        Weather weather = convert.transformJsonMessageToModel(json);
        log.debug("Transformed object from json: {}", weather);
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        try {
            Date date = format.parse(weather.getDate());
            String city = weather.getCity();
            if (dao.isForecastDuplicate(date, city)) {
                WeatherEntity entity = convert.transformFromModelToEntity(weather);
                dao.save(entity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
