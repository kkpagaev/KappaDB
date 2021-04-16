package DB;

import DB.Exceptions.*;
import DB.Query.Where;
import Json.JsonArray;
import Json.JsonString;
import util.FileHelper;
import util.StringHelper;

import java.util.*;

public class Table {
    private final LinkedList<HashMap<String, String>> data;

    public String name;

    public HashMap<String, LinkedList<String>> columns;

    private int lastId;

    public Table(String name, HashMap<String, LinkedList<String>> columns) {
        this.name = name;
        this.columns = columns;
        this.lastId = retrieveId();
        this.data = Objects.requireNonNull(JsonArray.parse(FileHelper.getStringFromFile(name + ".json"))).toOneLevelLinkedListWithHashSons();
    }

    private int retrieveId() {
        return Integer.parseInt(JsonString.parse(FileHelper.getStringFromFile(name + ".vars.json")).toArray());
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", lastId=" + lastId +
                '}';
    }

    public int incriminateId() {
        lastId++;
        FileHelper.setFileContent(name + ".vars.json", (new JsonString(String.valueOf(lastId))).toString());
        return lastId;
    }

    public void store(HashMap<String, String> storeData) throws FieldValidationException {
        HashMap<String, String> model = newModel(storeData);
        data.add(model);
        //System.out.println(data.toString());
        save();
    }

    private HashMap<String, String> newModel(HashMap<String, String> storeData) throws FieldValidationException {
        HashMap<String, String> model = validatedModel(storeData);
        model.put("id", String.valueOf(incriminateId()));
        return model;
    }

    private HashMap<String, String> updatedModel(HashMap<String, String> model, HashMap<String, String> newData) throws FieldValidationException {
        model.putAll(newData);
        return validatedModel(model);
    }

    private HashMap<String, String> validatedModel(HashMap<String, String> model) throws FieldValidationException {
        Set<String> cols = columns.keySet();
        // validates
        for (String field : cols) {
            if (field.equals("id")) continue;
            LinkedList<String> rules = columns.get(field);
            String value = model.get(field);
            if (value == null || value.equals("")) {
                if (rules.contains("required")) {
                    throw new FieldIsRequired(field);
                } else {
                    model.put(field, "");
                    continue;
                }
            }
            if (rules.contains("integer") && !StringHelper.isInteger(value)) {
                throw new FieldMustBeAnInteger(field);
            }
            if (rules.contains("double") && !StringHelper.isDouble(value)) {
                throw new FieldMustBeAnDouble(field);
            }

            if (rules.contains("unique")) {
                if (model.containsKey("id")) {
                    if(!isUnique(field, value, model.get("id"))) {
                        throw new FieldMustBeUnique(field);
                    }
                } else {
                    if(!isUnique(field, value)) {
                        throw new FieldMustBeUnique(field);
                    }
                }
            }


            model.put(field, value);
        }
        return model;
    }

    private boolean isUnique(String field, String value) {
        return matchedModels(new LinkedList<Where>(Collections.singletonList(new Where(field, value)))).size() == 0;
    }

    private boolean isUnique(String field, String value, String id) {
        LinkedList<HashMap<String, String>> matched = matchedModels(new LinkedList<Where>(Collections.singletonList(new Where(field, value))));
        if (matched.size() > 1) {
            return false;
        }
        if (matched.size() == 1) {
            return matched.getFirst().get("id").equals(id);
        }
        return true;
    }

    public LinkedList<HashMap<String, String>> select(LinkedList<Where> wheres) {
        return matchedModels(wheres);
    }

    private LinkedList<HashMap<String, String>> matchedModels(LinkedList<Where> wheres) {
        LinkedList<HashMap<String, String>> selected = new LinkedList<>();
        for (HashMap<String, String> son : data) {
            boolean isValid = true;
            for (Where where : wheres) {
                if(columns.get(where.key) == null) {
                    continue;
                }
                if (!son.get(where.key).equals(where.value)) {
                    isValid = false;
                }
            }
            if (isValid) selected.add(son);
        }
        return selected;
    }

    private void save() {
        FileHelper.setFileContent(name + ".json", JsonArray.parseOneLevelLinkedListWithHashSons(data).toString());
    }

    public void update(HashMap<String, String> updateData, LinkedList<Where> wheres) throws FieldValidationException {
        LinkedList<HashMap<String, String>> matched = matchedModels(wheres);
        for (HashMap<String, String> match : matched) {
            HashMap<String, String> model = updatedModel(match, updateData);
            data.set(data.indexOf(match), model);
        }
        save();
    }

    public void delete(LinkedList<Where> wheres) {
        LinkedList<HashMap<String, String>> matched = matchedModels(wheres);
        for (HashMap<String, String> model : matched) {
            data.remove(model);
        }
        save();
    }

}
