package weather_service.service;

import db.entity.WeatherEntity;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import weather_service.dao.WeatherDaoImplements;
import weather_service.view.WeatherFilter;
import weather_service.view.WeatherView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.easymock.EasyMock.*;

public class WeatherServiceImplementsTest{
    private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
    private Date date = format.parse("21 May 2018");
    private String city = "Penza";
    private WeatherFilter nullFilter = null;
    private WeatherFilter filterWithNullDate = new WeatherFilter(null, city);
    private WeatherFilter filter = new WeatherFilter(date, city);
    private WeatherEntity entity = new WeatherEntity(date, city, "Mon", "74", "54",
            "Scattered Thunderstorms");
    private WeatherView view = new WeatherView(date, city, "Mon", "74", "54",
            "Scattered Thunderstorms");

    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock
    private WeatherDaoImplements dao;

    @TestSubject
    private WeatherServiceImplements service = new WeatherServiceImplements(dao);

    public WeatherServiceImplementsTest() throws ParseException {
    }


    /**
     * Проверяем вызов метода WeatherDaoImplements.getByCityAndDate. Сравниваем результат работы метода
     * WeatherServiceImplements.getForecastByFilter c эталоном
     *
     */
    @Test
    public void testGetForecastByFilter() throws RuntimeException {
        expect(dao.getByCityAndDate(filter)).andStubReturn(entity);
        replay(dao);
        Assert.assertEquals(service.getWeatherByFilter(filter), view);
        verify(dao);
    }

//    /**
//     * Проверяем сгенерируется ли исключении при полученнии методом WeatherServiceImpl.getForecastByFilter()
//     * аргумента равного null
//     *
//     */
//    @Test(expected = WeatherBrokerServiceException.class)
//    public void testGetForecastByNullFilter() throws WeatherBrokerServiceException {
//        service.getForecastByFilter(nullFilter);
//    }
}