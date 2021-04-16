package DB.Exceptions;

public class FieldMustBeAnInteger extends FieldValidationException{
    public FieldMustBeAnInteger(String field) {
        super(field + " field must be an integer");
    }
}
