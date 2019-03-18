package admin.service;

import message_service.model.City;
import message_service.service.JsonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;

@RequestScoped
public class SendServiceImplements implements SendService {
    private final Logger log = LoggerFactory.getLogger(SendServiceImplements.class);


    private static final String WEATHER_TOPIC = "java:jboss/queue/cityQueue";
    private static final String CONNECTION = "java:comp/DefaultJMSConnectionFactory";

    @Resource(name = WEATHER_TOPIC)
    private Queue queue;

    @Resource(name = CONNECTION)
    private ConnectionFactory connection;

    private JsonService jsonService;

    @Inject
    public SendServiceImplements(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public SendServiceImplements() {
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
            producer.send(queue, message);
        } catch (JMSException e) {
            throw new RuntimeException("Not connection" , e );
        }
        log.info("Message to send: {}" , city);
    }
}
