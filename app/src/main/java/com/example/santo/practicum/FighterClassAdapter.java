package com.example.santo.practicum;

import com.example.santo.practicum.GameObjects.Fighter;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Santo on 11/9/2017.
 */


//code from https://stackoverflow.com/questions/16872492/gson-and-abstract-superclasses-deserialization-issue
public class FighterClassAdapter implements JsonSerializer<Fighter>, JsonDeserializer<Fighter> {
    @Override
    public JsonElement serialize(Fighter src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        //result.add("properties", context.serialize(src, src.getClass()));
        result.add("properties", new Gson().toJsonTree(src));
        return result;
    }


    @Override
    public Fighter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            String thepackage = "com.example.santo.practicum.GameObjects.";
            return context.deserialize(element, Class.forName(thepackage + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
