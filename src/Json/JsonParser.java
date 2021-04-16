package Json;

import Json.Parser.Parser;
import Json.Parser.SyntaxError;

import java.util.HashMap;
import java.util.LinkedList;

public class JsonParser extends Parser {

    public Json parse(String json) throws SyntaxError {
        Json jsonObj;
        setInput(json);
        spaces();
        jsonObj = json();

        return jsonObj;
    }

    public Json json() throws SyntaxError {
        Json json = null;
        spaces();
        if (next == '[') {
            json = jsonArray();
        } else if (next == '"') {
            json = jsonString();
        } else if (next == '{') {
            json = jsonObject();
        }
        return json;

    }

    public JsonArray jsonArray() throws SyntaxError {
        spaces();
        match('[');
        spaces();
        JsonArray array = new JsonArray(jsonArrayElements());
        spaces();
        match(']');
        spaces();
        return array;
    }

    public LinkedList<Json> jsonArrayElements() throws SyntaxError {
        LinkedList<Json> elements = new LinkedList<>();
        spaces();
        while (next != ']') {
            spaces();
            elements.add(json());
            spaces();
            if (next == ',') {
                match(',');
            } else break;
            spaces();
        }
        spaces();
        return elements;
    }

    public JsonObject jsonObject() throws SyntaxError {
        spaces();
        match('{');
        HashMap<String, Json> object = new HashMap<>();
        while (next != '}') {
            spaces();
            match('"');
            String key = string("\"");
            match('"');
            spaces();
            match(':');
            spaces();
            Json value = json();
            spaces();
            if (next == ',') match(',');
            spaces();
            object.put(key, value);
        }
        spaces();
        match('}');
        spaces();
        return new JsonObject(object);
    }

    public JsonString jsonString() throws SyntaxError {
        spaces();
        match('"');
        String string = string("\"");
        match('"');
        spaces();
        return new JsonString(string);
    }

}