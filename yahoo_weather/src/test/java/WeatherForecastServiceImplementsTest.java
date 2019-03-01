import model.City;
import model.Weather;
import service.WeatherForecastServiceImplements;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQXAJMSContext;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.springframework.web.client.RestTemplate;
import service.JsonServiceImplements;
import view.*;

import javax.jms.DeliveryMode;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import java.net.URI;
import java.net.URISyntaxException;

public class WeatherForecastServiceImplementsTest {

    private String json = "{" +
            "weather: " +
            "{" +
            "date: 5 March 2019," +
            "city: Saratov," +
            "day: Tue," +
            "highTemp: 31," +
            "lowTemp: 27," +
            "description: Partly Cloudy" +
            "}" +
            "}";
//    private String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><City name=\"Penza\"/>";
    private String cityName = "Saratov";
    private String nullCityName = null;
    private String emptyCityName = "";
    private City city = new City(cityName);
    private String forecastXml = "{" +
            "weather: " +
            "{" +
            "date: 5 March 2019," +
            "city: Saratov," +
            "day: Tue," +
            "highTemp: 31," +
            "lowTemp: 27," +
            "description: Partly Cloudy" +
            "}" +
            "}";
    private Weather weather = new Weather("18 May 2018", "Penza", "Fri", "78",
            "57", "Scattered Thunderstorms");

    private String url = "https://query.yahooapis.com/v1/public/yql?q=select%20location%2C%20item." +
            "weather%20from%20weather.weather%20where%20woeid%20in%20(select%20woeid%20from%20geo." +
            "places(1)%20where%20text%3D%22" + cityName + "%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables." +
            "org%2Falltableswithkeys";
    private YahooWeather yahooWeather = new YahooWeather("2123272", "18 May 2018", "Fri", "78",
            "57", "Scattered Thunderstorms");
    private YahooItem item = new YahooItem(yahooWeather);
    private YahooLocation location = new YahooLocation("Saratov", "Russia", " Saratov Oblast");
    private YahooChannel[] channel = {new YahooChannel(location, item)};
    private YahooResult result = new YahooResult(channel);
    private Query query = new Query(10, "2018-05-18T07:06:57Z", "ru-RU", result);
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
     * Проверяем вызовы методов: XmlServiceImp.readXmlMessage(String xml, Class<T> modelClass),
     * XmlServiceImp.createXmlMessage(XmlModel xml, ActiveMQConnectionFactory.createContext(),
     * ActiveMQXAJMSContext.createProducer(), JMSProducer.setDeliveryMode(int var1),
     * JMSProducer.send(Destination var1, String var2), RestTemplate.getForObject(URI url, Class<T> responseType)
     *
//     * @throws WeatherBrokerServiceException Исключение, сгенерированная при получении меодом пустой или равной null строки
     * @throws URISyntaxException            Исключение, указывающая на то, что строка не может быть принята как URI
     *                                       reference
     */
    @Test
    public void testCreateAndSendMessage() throws URISyntaxException {
        expect(service.readJsonMessage(json, City.class)).andStubReturn(city);
        expect(service.createJsonMessage(weather)).andStubReturn(forecastXml);
        expect(connectionFactory.createContext()).andStubReturn(context);
        expect(context.createProducer()).andStubReturn(producer);
        expect(producer.setDeliveryMode(DeliveryMode.PERSISTENT)).andStubReturn(producer);
        expect(producer.send(queue, json)).andStubReturn(producer);
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
//     * @throws WeatherBrokerServiceException Исключение, сгенерированная при получении меодом пустой или равной null
     *                                       строки
     */
//    @Test(expected = WeatherBrokerServiceException.class)
//    public void testCreateAndSendMessageWithNullCityName() throws WeatherBrokerServiceException {
//        forecastService.createAndSendMessage(nullCityName);
//    }

    /**
     * Проверяем, сгенерируется ли исключение при получении методом пустой строки
     *
//     * @throws WeatherBrokerServiceException Исключение, сгенерированная при получении меодом пустой или равной null
     *                                       строки
     */
//    @Test(expected = WeatherBrokerServiceException.class)
//    public void testCreateAndSendMessageWithEmptyCityName() throws WeatherBrokerServiceException {
//        forecastService.createAndSendMessage(emptyCityName);
//    }
}