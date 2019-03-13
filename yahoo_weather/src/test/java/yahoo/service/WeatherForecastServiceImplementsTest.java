package yahoo.service;

import message_service.model.City;
import message_service.model.Weather;
import message_service.service.JsonServiceImplements;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQXAJMSContext;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import yahoo.view.*;

import javax.jms.DeliveryMode;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import java.net.URI;
import java.net.URISyntaxException;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;

public class WeatherForecastServiceImplementsTest {
    private String message = "{" +
            "weather: {" +
            "date: 26 May 2018," +
            "city: Saratov," +
            "day: Sat," +
            "highTemp: 73," +
            "lowTemp: 59," +
            "description: Mostly Cloudy" +
            "}" +
            "}";
    private String json = "{" +
            "City: " +
            "{_name: Saratov " +
            "}" +
            "}";
    private String cityName = "Saratov";
    private String nullCityName = null;
    private String emptyCityName = "";
    private City city = new City(cityName);
    private String weatherJson = "{" +
            "weather: {" +
            "date: 26 May 2018," +
            "city: Saratov," +
            "day: Sat," +
            "highTemp: 73," +
            "lowTemp: 59," +
            "description: Mostly Cloudy" +
            "}" +
            "}";
    private Weather forecast = new Weather("13 March 2019", "Saratov", "Wed", "35",
            "27",
            "Partly cloudy");

    private String url = "https://query.yahooapis.com/v1/public/yql?q=select%20location%2C%20item." +
            "forecast%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo." +
            "places(1)%20where%20text%3D%22" + cityName + "%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables." +
            "org%2Falltableswithkeys";
    private YahooWeather yahooWeather = new YahooWeather("18 May 2018", "Fri", "78",
            "57", "Scattered Thunderstorms");
    private YahooItem item = new YahooItem(yahooWeather);
    private YahooLocation location = new YahooLocation("Saratov", "Russia", " Saratov Oblast");
    private YahooChannel[] channel = {new YahooChannel(location, item)};
    private YahooResult result = new YahooResult(channel);
    private Query query = new Query(10, "2019-03-11T14:22:57Z", "ru-RU", result);
    private YahooWeatherResponse response = new YahooWeatherResponse(query);

    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock
    private JsonServiceImplements service;

    @Mock
    private RestTemplate template;

    @Mock
    private Queue queue;

    @Mock
    private ActiveMQConnectionFactory connectionFactory;

    @Mock(MockType.NICE)
    private ActiveMQXAJMSContext context;

    @Mock
    private JMSProducer producer;

    @TestSubject
    private WeatherForecastServiceImplements forecastService = new WeatherForecastServiceImplements(service);

    /**
     * Проверяем вызовы методов: JsonServiceImplements.readJsonMessage(String json, Class<T> modelClass),
     * JsonServiceImplements.createJsonMessage(JsonModel json, ActiveMQConnectionFactory.createContext(),
     * ActiveMQXAJMSContext.createProducer(), JMSProducer.setDeliveryMode(int var1),
     * JMSProducer.send(Destination var1, String var2), RestTemplate.getForObject(URI url, Class<T> responseType)
     *
     */
    @Test
    public void testCreateAndSendMessage() throws URISyntaxException {
        expect(service.readJsonMessage(json, City.class)).andStubReturn(city);
        expect(service.createJsonMessage(forecast)).andStubReturn(weatherJson);
        expect(connectionFactory.createContext()).andStubReturn(context);
        expect(context.createProducer()).andStubReturn(producer);
        expect(producer.setDeliveryMode(DeliveryMode.PERSISTENT)).andStubReturn(producer);
        expect(producer.send(queue, message)).andStubReturn(producer);
        expect(template.getForObject(new URI(url), YahooWeatherResponse.class)).andStubReturn(response);
        replay(service);
        replay(queue);
        replay(template);
        replay(connectionFactory);
        replay(context);
        replay(producer);
        forecastService.createAndSendMessage(json);
        verify(service);
        verify(template);
        verify(producer);
        verify(context);
        verify(connectionFactory);
    }

    /**
     * Проверяем, сгенерируется ли исключение при получении методом строки равной null
     *
     */
    @Test
    public void testCreateAndSendMessageWithNullCityName() throws RuntimeException {
        forecastService.createAndSendMessage(nullCityName);
    }

    /**
     * Проверяем, сгенерируется ли исключение при получении методом пустой строки
     *
     */

    @Test
    public void testCreateAndSendMessageWithEmptyCityName() throws RuntimeException {
        forecastService.createAndSendMessage(emptyCityName);
    }
}