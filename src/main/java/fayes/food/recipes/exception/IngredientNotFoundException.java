package fayes.food.recipes.exception;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(String s) {
        super(s);
    }
}
