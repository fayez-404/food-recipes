package fayes.food.recipes.exception;

public class InvalidRefreshTokenException extends RuntimeException {
  public InvalidRefreshTokenException(String message) {
    super(message);
  }
}
