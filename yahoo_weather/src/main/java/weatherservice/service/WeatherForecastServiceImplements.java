package weatherservice.service;

import model.City;
import model.Weather;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import weatherservice.view.YahooChannel;
import weatherservice.view.YahooWeatherResponse;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@RequestScoped
public class WeatherForecastServiceImplements implements WeatherForecastService {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastServiceImplements.class);
    private static final String WEATHER_QUEUE = "java:jboss/weatherQueue";
    private static final String CONNECTION = "java:comp/DefaultJMSConnectionFactory";

    @Resource(name = WEATHER_QUEUE)
    private Queue queue;

    @Resource(name = CONNECTION)
    private ConnectionFactory connection;

    private JsonService jsonService;
    private RestTemplate restTemplate = new RestTemplate();

    @Inject
    public WeatherForecastServiceImplements(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public WeatherForecastServiceImplements() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAndSendMessage(String xml) {
        if (StringUtils.isBlank(xml)) {
            throw new RuntimeException("Please enter the name of the city");
        }
        City city = jsonService.readJsonMessage(xml, City.class);
        String cityName = city.getName();
        YahooWeatherResponse response = getResponseFromYahooWeather(cityName);
        try (JMSContext context = connection.createContext()) {
            JMSProducer producer = context.createProducer().setDeliveryMode(DeliveryMode.PERSISTENT);
            Set<Weather> weathers = getForecastSetFromYahooResponse(response);
            for (Weather wf : weathers) {
                String message = jsonService.createJsonMessage(wf);
                producer.send(queue, message);
                log.info(String.format("Message to send: %s", message));
            }
        }
    }

    /**
     * Получить ответ от сервиса Yahoo.weather
     *
     * @param city Название города
     * @return Ответ от Yahoo Weather API
     * //     * @throws WeatherBrokerServiceException Ошибка сгенерированная при попытке запроса с сервесу Yahoo Weather API
     */
    private YahooWeatherResponse getResponseFromYahooWeather(String city) {
        YahooWeatherResponse response;
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20location%2C%20item." +
                "forecast%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo." +
                "places(1)%20where%20text%3D%22" + city + "%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables." +
                "org%2Falltableswithkeys";
        try {
            response = restTemplate.getForObject(new URI(url), YahooWeatherResponse.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(
                    String.format("Failed to get a response from the Yahoo Weather API service in the city: %s", city),
                    e
            );
        }
        return response;
    }

    /**
     * Получить список погоды за 10 дней
     *
     * @param response Объект содержащий всю информацию по погоде в интересующем нас городе,
     *                 сформированный из ответа сервиса Yahoo Weather API
     * @return Множество объектов с конкретно интересующей нас информацией о погоде. Полученные объекты могут
     * использоваься при создания xml для jms сообщения
     */
    private Set<Weather> getForecastSetFromYahooResponse(YahooWeatherResponse response) {
        Set<Weather> forecastSet = new HashSet<>();
        YahooChannel[] channel = response
                .getQuery()
                .getResults()
                .getChannel();
        for (YahooChannel c : channel) {
            Weather weather = new Weather();
            weather.setDate(c.getItem().getForecast().getDate());
            weather.setCity(c.getLocation().getCity());
            weather.setDay(c.getItem().getForecast().getDay());
            weather.setHighTemp(c.getItem().getForecast().getHigh());
            weather.setLowTemp(c.getItem().getForecast().getLow());
            weather.setDescription(c.getItem().getForecast().getText());
            forecastSet.add(weather);
        }
        return forecastSet;
    }
}
