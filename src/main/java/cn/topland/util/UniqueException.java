package cn.topland.util;

public class UniqueException extends RuntimeException {

    @Override
    public String getMessage() {

        return "Field has to be unique.";
    }
}