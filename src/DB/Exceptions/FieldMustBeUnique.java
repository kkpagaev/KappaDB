package DB.Exceptions;

public class FieldMustBeUnique extends FieldValidationException{
    public FieldMustBeUnique(String field) {
        super(field + " field must be unique");
    }
}
