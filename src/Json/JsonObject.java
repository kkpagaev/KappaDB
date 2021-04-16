package Json;

import Json.Parser.SyntaxError;

import java.util.HashMap;
import java.util.LinkedList;

public class JsonObject extends Json {
    public HashMap<String, Json> object;

    public JsonObject(HashMap<String, Json> map) {
        this.object = map;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        object.forEach((jsonString, json) -> result.append('"').append(jsonString).append('"').append(": ").append(json).append(','));
        result.delete(result.length()-1, result.length());
        return result + "}";
    }

    public HashMap<String, Object> toArray() {
        HashMap<String, Object> newArray = new HashMap<>();

        object.forEach((string, json) -> newArray.put(string, json.toArray()));

        return newArray;
    }

    public HashMap<String, String> toStringHashArray() {
        HashMap<String, String> newArray = new HashMap<>();

        object.forEach((string, json) -> newArray.put(string, ((JsonString) json ).string));

        return newArray;
    }
    public HashMap<String, LinkedList<String>> toHashMapWithLinkedLists() {
        HashMap<String, LinkedList<String>> newArray = new HashMap<>();

        object.forEach((string, json) -> newArray.put(string, ((JsonArray) json).toStringArray()));

        return newArray;
    }

    public static JsonObject parse(String word) {
        JsonParser parser = new JsonParser();
        parser.setInput(word);
        JsonObject object;

        try {
            object = parser.jsonObject();
        } catch (SyntaxError syntaxError) {
            syntaxError.printStackTrace();
            return null;
        }
        return object;
    }

    public static JsonObject parseStringStringMap(HashMap<String, String> map) {
        HashMap<String, Json> object = new HashMap<>();
        map.forEach((s, s2) -> {
            object.put(s, new JsonString(s2));
        });

        return new JsonObject(object);
    }

    public void put(String key, Json json) {
        this.object.put(key, json);
    }

    public Json get(String key) {
        return this.object.get(key);
    }

}
