package DB.Exceptions;

public class FieldMustBeAnDouble extends FieldValidationException{
    public FieldMustBeAnDouble(String field) {
        super(field + " field  must be double");
    }
}
