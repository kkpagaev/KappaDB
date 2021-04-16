package Json.Parser;

public class Parser {

    private String word;
    protected LetterS input;

    public char next;

    protected void match(char c) throws SyntaxError {
        if (next == c) next = input.nextChar();
        else throw new SyntaxError("Expecting " + c + ", found " + next);
    }

    public void setInput(String word) {
        input = new LetterS(word);
        next = input.nextChar();
    }
    protected boolean isSpace(char c) {
        return c == ' ' || c == '\n' || c == '\t';
    }

    protected boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    protected boolean isText(char c) {
        return c != '<' && c != '>';
    }

    protected boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    protected String string(String wordIn) {
        StringBuilder value = new StringBuilder();
        while (wordIn.indexOf(next) == -1) {
            value.append(next);
            next = input.nextChar();
        }
        return value.toString();
    }

    protected void spaces() throws SyntaxError {
        while (isSpace(next)) {
            if (next == '\n') {
                match('\n');
            } else if (next == '\t') {
                match('\t');
            } else {
                match(' ');
            }
        }
    }

    protected void matchWord(String word) throws SyntaxError {
        for (int i = 0; i < word.length(); i++) {
            match(word.charAt(i));
        }
    }
}
