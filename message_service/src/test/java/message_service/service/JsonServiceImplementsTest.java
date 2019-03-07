package message_service.service;

import message_service.model.City;
import message_service.model.Weather;
import org.junit.Assert;
import org.junit.Test;

public class JsonServiceImplementsTest {
    private String city = "Saratov";
    private City cityModel = new City(city);
    private String cityJson = "{City: {_name: Saratov }}";
    private String weatherJson = "{" +
            "weather: {" +
            "date: 26 May 2018," +
            "city: Penza," +
            "day: Sat," +
            "highTemp: 73," +
            "lowTemp: 59," +
            "description: Mostly Cloudy" +
            "}" +
            "}";
    private Weather weather = new Weather("26 May 2018", "Saratov", "Sat", "73",
            "59", "Mostly Cloudy");
    private JsonServiceImplements service = new JsonServiceImplements();

    /**
     * Проверяем результат работы метода JsonServiceImplements.createJsonMessage(JsonModel json) с эталонным
     */
    @Test
    public void testCreateXmlMessage() throws RuntimeException {
        Assert.assertEquals(service.createJsonMessage(cityModel), cityJson);
        Assert.assertEquals(service.createJsonMessage(weather), weatherJson);
    }

    /**
     * Проверяем результа работы метода JsonServiceImplements.readJsonMessage(String json, Class<T> modelClass) с эталонным
     */
    @Test
    public void testReadXmlMessage() throws RuntimeException {
        Assert.assertEquals(service.readJsonMessage(cityJson, City.class), cityModel);
        Assert.assertEquals(service.readJsonMessage(weatherJson, Weather.class), weather);
    }

}