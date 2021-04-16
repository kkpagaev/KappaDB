package DB;

import DB.Exceptions.*;
import DB.Query.Query;
import DB.Query.QueryParser;
import DB.Query.Where;
import Json.Json;
import Json.JsonArray;
import Json.JsonObject;
import Json.Parser.SyntaxError;
import util.FileHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class DB {
    public LinkedList<Table> tables;

    public DB(String config) {
        String json = FileHelper.getStringFromFile(config);

        JsonObject confJson = JsonObject.parse(json);

        assert confJson != null;
        JsonArray tablesConfig = (JsonArray) confJson.get("tables");
        initTables(tablesConfig);
    }

    private void initTables(JsonArray tablesConfig) {
        this.tables = new LinkedList<>();

        for (Json son : tablesConfig.sons) {
            if (son.getClass() == JsonObject.class) {
                JsonObject tableConf = (JsonObject) son;
                this.tables.add(new Table(
                        (String) tableConf.get("name").toArray(),
                        ((JsonObject) tableConf.get("columns")).toHashMapWithLinkedLists()
                ));
            }
        }
    }

    public LinkedList<HashMap<String, String>> fetch(Query query) {
        Table table = getTable(query.table);
        return table.select(query.wheres);
    }

    public boolean execute(Query query) throws FieldValidationException {
        Table table = getTable(query.table);
        switch (query.method) {
            case "DELETE":
                table.delete(query.wheres);
                break;

            case "UPDATE":
                table.update(query.params, query.wheres);
                break;
            case "INSERT":
                table.store(query.params);
                break;
        }
        return true;
    }

    public Table getTable(String tableName) {
        for (Table table1 : tables) {
            if (table1.name.equals(tableName)) {
                return table1;
            }
        }
        return null;
    }

    public Query query(String string) throws SyntaxError, TableNotFoundException, FieldNotFound {
        Query query = (new QueryParser()).parse(string);
        Table table = getTable(query.table);
        if (table == null) {
            throw new TableNotFoundException();
        }
        Set<String> columns = table.columns.keySet();
        if (query.wheres != null) {
            for (Where where : query.wheres) {
                if (!columns.contains(where.key) && !where.key.equals("id")) {
                    throw new FieldNotFound(where.key);
                }
            }
        }
        if (query.params != null) {
            for (String param : query.params.keySet()) {
                if (!columns.contains(param) && !param.equals("id")) {
                    throw new FieldNotFound(param);
                }
            }
        }

        return query;
    }
}
