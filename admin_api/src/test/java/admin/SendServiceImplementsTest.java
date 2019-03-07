package admin;

import admin.service.SendServiceImplements;
import message_service.model.City;
import message_service.model.JsonModel;
import message_service.service.JsonServiceImplements;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionForContext;
import org.apache.activemq.artemis.jms.client.ActiveMQXAJMSContext;
import org.apache.activemq.artemis.jms.client.ThreadAwareContext;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import javax.jms.JMSProducer;
import javax.jms.Queue;

import static org.easymock.EasyMock.*;

public class SendServiceImplementsTest {
    private String city = "Saratov";
    private String emptyCity = "";
    private String nullCity = null;
    private String TEST_STRING = "{City: {_name: Saratov }}";
    private JsonModel cityModel = new City(city);

    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock
    private JsonServiceImplements service;

    @Mock
    private ActiveMQConnectionFactory connectionFactory;

    @Mock
    private ActiveMQConnectionForContext connectionForContext;

    @Mock
    private ThreadAwareContext threadAwareContext;

    @Mock(MockType.NICE)
    private ActiveMQXAJMSContext context;

    @Mock
    private JMSProducer producer;

    @Mock
    private Queue queue;

    @TestSubject
    private SendServiceImplements sendService = new SendServiceImplements(service);

    /**
     * Проверяем вызов методов: JsonServiceImplements.createJsonMessage(JsonModel json),
     * ActiveMQConnectionForContext.createContext(), ActiveMQXAJMSContext.createProducer(),
     * JMSProducer.send(Destination destination, String body)
     *
     */
    @Test
    public void testSend() throws RuntimeException {
        expect(service.createJsonMessage(cityModel)).andStubReturn(TEST_STRING);
        expect(connectionFactory.createContext()).andStubReturn(context);
        expect(context.createProducer()).andStubReturn(producer);
        expect(producer.send(queue, TEST_STRING)).andStubReturn(producer);
        replay(context);
        replay(queue);
        replay(connectionFactory);
        replay(service);
        replay(producer);
        sendService.send(city);
        verify(service);
        verify(connectionFactory);
        verify(context);
        verify(producer);
    }
}