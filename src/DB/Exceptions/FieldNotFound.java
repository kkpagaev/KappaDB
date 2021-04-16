package DB.Exceptions;

public class FieldNotFound extends FieldValidationException{
    public FieldNotFound(String field) {
        super(field + " not found");
    }
}
