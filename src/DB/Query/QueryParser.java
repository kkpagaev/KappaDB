package DB.Query;

import Json.Json;
import Json.JsonObject;
import Json.JsonString;
import Json.Parser.Parser;
import Json.Parser.SyntaxError;

import java.util.HashMap;
import java.util.LinkedList;

public class QueryParser extends Parser {
    public Query parse(String str) throws SyntaxError {
        setInput(str);
        String tableName;
        LinkedList<Where> wheres;
        String method = string(" ");
        switch (method) {
            case "SELECT":
                spaces();
                match('*');
                spaces();
                matchWord("FROM");
                spaces();
                tableName = string(" $");
                spaces();
                return new Query(method, tableName, wheres());
            case "DELETE":
                spaces();
                matchWord("FROM");
                spaces();
                tableName = string(" $");
                spaces();
                return new Query(method, tableName, wheres());
            case "UPDATE": {
                spaces();
                tableName = string(" $");
                spaces();
                matchWord("SET");
                spaces();
                JsonObject jsonObject = params();
                spaces();
                return new Query(method, tableName, wheres(), jsonObject.toStringHashArray());
            }
            case "INSERT": {
                spaces();
                matchWord("INTO");
                spaces();
                tableName = string(" $");
                spaces();
                matchWord("SET");
                spaces();
                JsonObject jsonObject = params();
                spaces();
                return new Query(method, tableName, jsonObject.toStringHashArray());
            }
        }
        throw new SyntaxError("invalid query method");

    }

    private JsonObject params() throws SyntaxError {
        HashMap<String, Json> obj = new HashMap<>();
        do {
            spaces();
            String key = string("=");
            match('=');
            match('"');
            JsonString value = new JsonString(string("\""));
            match('"');
            spaces();
            obj.put(key, value);
        } while (next != 'W' && next != '$');
        return new JsonObject(obj);
    }

    private LinkedList<Where> wheres() throws SyntaxError {
        LinkedList<Where> wheres = new LinkedList<>();
        spaces();
        if (next == 'W') {
            matchWord("WHERE");
            wheres.add(where());
            while (next == 'A') {
                matchWord("AND");
                wheres.add(where());
            }
        }
        return wheres;
    }

    private Where where() throws SyntaxError {
        spaces();
        String key = string("=");
        match('=');
        match('"');
        String value = string("\"");
        match('"');
        spaces();
        return new Where(key, value);
    }


}
