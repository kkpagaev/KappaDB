package DB.Exceptions;

public class FieldIsRequired extends FieldValidationException{

    public FieldIsRequired(String field) {
        super(field + " field is require");
    }
}
