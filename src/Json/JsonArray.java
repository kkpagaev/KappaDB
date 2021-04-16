package Json;

import Json.Parser.SyntaxError;

import java.util.HashMap;
import java.util.LinkedList;

public class JsonArray extends Json {
    public LinkedList<Json> sons;

    public JsonArray(LinkedList<Json> sons) {
        this.sons = sons;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < sons.size() - 1; i++) {
            result.append(sons.get(i)).append(",");
        }
        if (sons.size() > 0) {
            result.append(sons.getLast());
        }
        return result + "]";
    }

    @Override
    public LinkedList<Object> toArray() {
        LinkedList<Object> array = new LinkedList<>();
        for (Json son : sons) {
            array.add(son.toArray());
        }
        return array;
    }

    public LinkedList<String> toStringArray() {
        LinkedList<String> array = new LinkedList<>();
        for (Json son : sons) {
            array.add(((JsonString) son).string);
        }
        return array;
    }

    public LinkedList<HashMap<String, String>> toOneLevelLinkedListWithHashSons() {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        for (Json son : sons) {
            result.add(((JsonObject) son).toStringHashArray());
        }
        return result;
    }

    public static JsonArray parseOneLevelLinkedListWithHashSons(LinkedList<HashMap<String, String>> array) {
        LinkedList<Json> sons = new LinkedList<Json>();
        for (HashMap<String, String> son : array) {
            sons.add(JsonObject.parseStringStringMap(son));
        }
        return new JsonArray(sons);
    }

    public static JsonArray parse(String word) {
        JsonParser parser = new JsonParser();
        parser.setInput(word);
        JsonArray array;
        try {
            array = parser.jsonArray();
        } catch (SyntaxError syntaxError) {
            syntaxError.printStackTrace();
            return null;
        }

        return array;
    }

    public void add(Json json) {
        sons.add(json);
    }
}
