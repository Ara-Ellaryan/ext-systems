package edu.javacourse.city.exception;

public class PersonCheckException extends Exception{

    public PersonCheckException() {
        super();
    }

    public PersonCheckException(String message) {
        super(message);
    }

    public PersonCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonCheckException(Throwable cause) {
        super(cause);
    }

    protected PersonCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
