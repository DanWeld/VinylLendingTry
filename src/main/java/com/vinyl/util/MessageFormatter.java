package com.vinyl.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class MessageFormatter {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String createJsonMessage(String type, String content) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("content", content);
        return gson.toJson(jsonObject);
    }

    public static String createErrorJsonMessage(String errorMessage) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "error");
        jsonObject.addProperty("message", errorMessage);
        return gson.toJson(jsonObject);
    }

    public static JsonObject parseJsonMessage(String jsonString) {
        return gson.fromJson(jsonString, JsonObject.class);
    }
} 