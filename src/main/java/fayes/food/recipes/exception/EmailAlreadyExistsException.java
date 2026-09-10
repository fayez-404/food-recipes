package fayes.food.recipes.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String s) {
        super(s);
    }

    public String getName() {
        return "EmailAlreadyExistsException";
    }
}
