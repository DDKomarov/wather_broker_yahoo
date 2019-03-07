package yahoo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Сервис принимает JMS сообщения с названием города и делает запрос погоды через Yahoo Weather Api
 */
@MessageDriven(name = "YahooReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/queue/weatherQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class YahooReceiver implements MessageListener {

    private final Logger log = LoggerFactory.getLogger(YahooReceiver.class);

    private WeatherForecastService service;

    @Inject
    public YahooReceiver(WeatherForecastService service) {
        this.service = service;
    }

    public YahooReceiver() {
    }

    @Override
    public void onMessage(Message message) {

        String city = "";
        try {
            city = ((TextMessage) message).getText();
            service.createAndSendMessage(city);
            log.info("Received message: {}", city);
        } catch (JMSException  e) {
            throw new RuntimeException(
                    String.format("An error occurred while reading jms message containing city: %s", city),
                    e
            );
        }
    }
}
