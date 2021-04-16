package Json;

import Json.Parser.SyntaxError;

public class JsonString extends Json {
    public String string;
    public JsonString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "\"" + string + "\"";
    }

    public String toArray() {
        return string;
    }

    public static JsonString parse(String word) {
        JsonParser parser = new JsonParser();
        parser.setInput(word);
        JsonString string;
        try {
            string = parser.jsonString();
        } catch (SyntaxError syntaxError) {
            syntaxError.printStackTrace();
            return null;
        }
        return string;
    }
}
