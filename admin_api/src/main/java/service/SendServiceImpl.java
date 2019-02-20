package service;

import model.City;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;

public class SendServiceImpl implements SendService {
    private final Logger log = Logger.getLogger(SendServiceImpl.class);
    private static final String WEATHER_TOPIC = "java:jboss/weatherTopic";
    private static final String CONNECTION = "java:comp/DefaultJMSConnectionFactory";

    @Resource(name = WEATHER_TOPIC)
    private Topic topic;

    @Resource(name = CONNECTION)
    private ConnectionFactory connection;

    private JsonService jsonService;

    @Inject
    public SendServiceImpl(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public SendServiceImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(String city){
        if (StringUtils.isBlank(city)) {
            throw new RuntimeException("Please enter the name of the city");
        }
        City writeCity = new City(city);
        String message = jsonService.createJsonMessage(writeCity);
        try (JMSContext context = (JMSContext) connection.createConnection()) {
            JMSProducer producer = context.createProducer();
            producer.send(topic, message);
        } catch (JMSException e) {
            throw new RuntimeException("Not connection" , e );
        }
        log.info("Message to send: {}" + city);
    }
}
