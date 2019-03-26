package db.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "DataReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/queue/weatherQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class DataReceiver implements MessageListener {
    private final Logger log = LoggerFactory.getLogger(DataReceiver.class);
    private DataService service;


    @Inject
    public void setService(DataService service) {
        this.service = service;
    }

    public DataReceiver() {
    }

    @Override
    public void onMessage(Message message) {
        if (message == null) {
            throw new RuntimeException("message is null");
        }
        try {
            String json = ((TextMessage) message).getText();
            service.save(json);
            log.debug("Received message: {}", json);
        } catch (JMSException e) {
            throw new RuntimeException("An error occurred while reading jms message containing json",e);
        }
    }
}
