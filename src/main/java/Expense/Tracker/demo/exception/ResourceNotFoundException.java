package Expense.Tracker.demo.exception;

// Create new file: src/main/java/Expense/Tracker/demo/exception/ResourceNotFoundException.java

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
