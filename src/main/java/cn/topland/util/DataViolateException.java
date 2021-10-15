package cn.topland.util;

public class DataViolateException extends RuntimeException {

    @Override
    public String getMessage() {

        return "Field value is not unique or should not be null";
    }
}