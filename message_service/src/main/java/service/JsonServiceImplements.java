package service;

import model.JsonModel;

import javax.enterprise.context.RequestScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@RequestScoped
public class JsonServiceImplements implements JsonService {
    @Override
    public String createJsonMessage(JsonModel jm) {
        try (OutputStream os = new ByteArrayOutputStream()) {
            JAXBContext context = JAXBContext.newInstance(jm.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(jm, os);
            return os.toString();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(String.format("Error trying to create xml from objects: %s", jm), e);
        }
    }

    @Override
    public <T extends JsonModel> T readXmlMessage(String json, Class<T> modelClass) {

        byte[] array = json.getBytes();
        try (InputStream inputStream = new ByteArrayInputStream(array)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object result = unmarshaller.unmarshal(inputStream);
            if (result == null || !result.getClass().isAssignableFrom(modelClass)) {
                throw new RuntimeException(
                        String.format("Failed to generate object from xml string: %s", json)
                );
            }
            return (T)result;
        } catch (JAXBException | IOException | ClassCastException e) {
            throw new RuntimeException(
                    String.format("An error occurred while trying to restore an object from an xml string: %s", json)
            );
        }
    }
}

