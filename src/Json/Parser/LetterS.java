package Json.Parser;

import Json.Json;

public class LetterS {
    // $ - end of string
    String input;
    int p;
    char c;

    public LetterS(String input) {
        this.input = input;
        p = 0;
        c = ' ';
    }

    public char nextChar() {
        if (p < input.length()) c = input.charAt(p++);
        else c = '$';
        return c;
    }

    public char seeChar() {
        if (p < input.length()) c = input.charAt(p);
        else c = '$';
        return c;
    }
}
