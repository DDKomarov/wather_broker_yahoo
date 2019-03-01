package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.JsonModel;

import javax.enterprise.context.RequestScoped;
import java.io.*;

@RequestScoped
public class JsonServiceImplements implements JsonService {

    @Override
    public String createJsonMessage(JsonModel jm) {
        try (OutputStream os = new ByteArrayOutputStream()) {
//            JAXBContext context = JAXBContext.newInstance(jm.getClass());
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.marshal(jm, os);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(os,jm);
            return os.toString();
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Error trying to create json from objects: %s", jm),
                    e
            );
        }
    }

    @Override
    public <T extends JsonModel> T readJsonMessage(String json, Class<T> modelClass) {

        byte[] array = json.getBytes();
        try (InputStream inputStream = new ByteArrayInputStream(array)) {
            ObjectMapper mapper = new ObjectMapper();

//            mapper.readValue(json,modelClass);
//            JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            Object result = unmarshaller.unmarshal(inputStream);

            Object result = mapper.readValue(json, modelClass);
            if (result == null || !result.getClass().isAssignableFrom(modelClass)) {
                throw new RuntimeException(
                        String.format("Failed to generate object from xml string: %s", json)
                );
            }
            return (T) result;
        } catch (IOException | ClassCastException e) {
            throw new RuntimeException(
                    String.format("An error occurred while trying to restore an object from an xml string: %s", json)
            );
        }
    }
}

