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
    private Date date = format.parse("13 March 2019");
    private String city = "Saratov";
    private WeatherFilter nullFilter = null;
    private WeatherFilter filter = new WeatherFilter(date, city);
    private WeatherEntity entity = new WeatherEntity(date, city, "Wed", "35", "27",
            "Partly cloudy");

    private WeatherView view = new WeatherView(date, city, "Wed", "35", "27",
            "Partly cloudy");

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

    /**
     * Проверяем сгенерируется ли исключении при полученнии методом WeatherServiceImplements.getWeatherByFilter()
     * аргумента равного null
     *
     */
    @Test
    public void testGetForecastByNullFilter() throws RuntimeException {
        service.getWeatherByFilter(nullFilter);
    }
}