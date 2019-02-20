package service;

import model.JsonModel;

public interface JsonService {

    String createJsonMessage(JsonModel jm);

    <T extends JsonModel> T readXmlMessage(String json, Class<T> modelClass);
}
