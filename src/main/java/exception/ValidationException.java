package exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends IllegalArgumentException {
    private final Map<String,String> errorDetails;
    public ValidationException(String message,Map<String,String> errorDetails) {
        super(message);this.errorDetails = errorDetails;
    }
}
