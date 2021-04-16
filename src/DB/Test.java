package DB;

import DB.Exceptions.*;
import Json.JsonObject;
import Json.Parser.SyntaxError;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws FieldValidationException, TableNotFoundException, SyntaxError {
        DB db = new DB("db.conf.json");
        HashMap<String, String> jobj = JsonObject.parse("{\"123\": \"123\"}").toStringHashArray();
//        db.query("SELECT * FROM users WHERE id=\"1\"");

//
//        db.query("DELETE FROM users WHERE id=\"1\" ");
       db.execute(db.query("UPDATE users SET name=\"Vlad\" WHERE name=\"Stas\" "));
//        db.store(db.getTable("users"), jobj);
        //  System.out.println(db.execute(db.query("DELETE FROM users WHERE 123=\"123\"")));
//        System.out.println(db.execute(db.query("INSERT INTO users SET name=\"Vladd\"")));

        System.out.println(db.fetch(db.query("SELECT * FROM users ")));
        //System.out.println(db.tables);
    }
}
