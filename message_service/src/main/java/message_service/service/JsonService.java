package message_service.service;

import message_service.model.JsonModel;

public interface JsonService {

    String createJsonMessage(JsonModel jm);

    <T extends JsonModel> T readJsonMessage(String json, Class<T> modelClass);
}
