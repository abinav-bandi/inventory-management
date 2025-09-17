package com.inventory.exception;

// For invalid input from user
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

// For product not found case
public class NoProductFoundException extends Exception {
    public NoProductFoundException(String message) {
        super(message);
    }
}

// For invalid quantity or price
public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}
