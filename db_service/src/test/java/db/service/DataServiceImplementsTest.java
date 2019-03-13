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
    private Weather weatherForecast = new Weather("13 March 2019", "Saratov", "Wed", "35",
            "27",
            "Partly cloudy");
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


    /**
     * Проверяем, сгенерируется ли исключение при получении методом строки равной null
     * */
    @Test
    public void testSaveNullJson() throws RuntimeException{
        dataService.save(nullJson);
    }

    /**
     * Проверяем сгенерируется ли исключение при получении методом пустой строки
     */
    @Test
    public void testSaveEmptyJson() throws RuntimeException{
        dataService.save(emptyJson);
    }












































}