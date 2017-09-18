package com.dreampany.frame.data.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by nuc on 1/18/2017.
 */

public final class JsonUtil {
    private JsonUtil() {}

    public static JsonObject getJsonObject(String json) {
        JsonParser parser = new JsonParser();
        return parser.parse(json).getAsJsonObject();
    }
}
