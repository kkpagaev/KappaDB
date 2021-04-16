package Json;

public class Test {
    public static void main(String[] args) {
        JsonParser parser = new JsonParser();

        System.out.println(JsonArray.parse("[ {\"123\": \"123\",}, { \"123\" : \"123\" },{ \"123\": \"123\"}]"));
        System.out.println(JsonObject.parse("{\"ff\":\"foo\",\"fdf\": \"123\"}"));
        System.out.println(JsonString.parse("\"123\""));
//        System.out.println(array);
    }

}
