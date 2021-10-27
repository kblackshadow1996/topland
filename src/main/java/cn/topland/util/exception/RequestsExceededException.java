package cn.topland.util.exception;

public class RequestsExceededException extends RuntimeException {

    public RequestsExceededException(String message) {
        super(message);
    }
}