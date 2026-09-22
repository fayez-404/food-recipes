package fayes.food.recipes.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

    private int status ;
    private String message ;
    private LocalDateTime localDateTime;
    private String path ;

    public ErrorDetails(int status, String message, LocalDateTime localDateTime, String path) {
        this.status = status;
        this.message = message;
        this.localDateTime = localDateTime;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", localDateTime=" + localDateTime +
                ", path='" + path + '\'' +
                '}';
    }
}
