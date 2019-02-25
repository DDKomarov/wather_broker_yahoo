package weatherservice.service;

import model.JsonModel;

public interface JsonService {

    String createJsonMessage(JsonModel jm);

    <T extends JsonModel> T readJsonMessage(String json, Class<T> modelClass);
}
