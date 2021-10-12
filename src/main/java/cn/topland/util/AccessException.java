package cn.topland.util;

public class AccessException extends Exception {

    @Override
    public String getMessage() {

        return "You don't have permission to access this.";
    }
}