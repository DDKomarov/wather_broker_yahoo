package db.service;

import db.dao.WeatherDaoImplements;
import message_service.model.Weather;
import message_service.service.JsonServiceImplements;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;

public class DataServiceImplementsTest {
    private Weather weatherForecast = new Weather("21 May 2018", "Penza", "Mon", "74",
            "54",
            "Scattered Thunderstorms");
    private Weather weatherForecastWithWrongDate = new Weather("thisisjust", "Penza", "Mon",
            "74", "54",
            "Scattered Thunderstorms");
    private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
    private Date date = format.parse(weatherForecast.getDate());
    private String city = "Saratov";
    private String json = "test";
    private String nullJson = null;
    private String emptyJson= "";




    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock
    private Convert convert;

    @Mock
    private JsonServiceImplements service;

    @Mock(MockType.NICE)
    private WeatherDaoImplements dao;

    @TestSubject
    private DataServiceImplements dataService = new DataServiceImplements(dao,convert);



    public DataServiceImplementsTest() throws ParseException {
    }

    /**
     * Проверяем вызов методов: JsonServiceImplements.readJsonMessage(String json, Class<T> modelClass),
     * ForecastDaoImpl.isForecastDuplicate(Date date, String city)
     *
     *                                       строки
     */
    @Test
    public void testSave() throws RuntimeException {
        expect(service.readJsonMessage(json, Weather.class)).andStubReturn(weatherForecast);
        expect(dao.isForecastDuplicate(date, city)).andStubReturn(true);
        replay(service);
        replay(dao);
        dataService.save(json);
        verify(service);
        verify(dao);
    }
}