package DB.Query;

import Json.JsonObject;

import java.util.HashMap;
import java.util.LinkedList;

public class Query {
    public Query(String method, String table, LinkedList<Where> wheres, HashMap<String, String> params) {
        this.wheres = wheres;
        this.method = method;
        this.params = params;
        this.table = table;
    }

    public LinkedList<Where> wheres;
    public String method;
    public HashMap<String, String> params;
    public String table;

    public Query(String method, String table, LinkedList<Where> wheres) {
        this.wheres = wheres;
        this.method = method;
        this.table = table;
    }

    public Query(String method, String table, HashMap<String, String> params) {
        this.params = params;
        this.method = method;
        this.table = table;
    }

    @Override
    public String toString() {
        return "Query{" +
                "wheres=" + wheres +
                ", method='" + method + '\'' +
                ", params=" + params +
                '}';
    }
}
