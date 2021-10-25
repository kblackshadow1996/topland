package cn.topland.util.exception;

public class AccessException extends Exception {

    public AccessException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {

        return "You don't have permission to access this.";
    }
}