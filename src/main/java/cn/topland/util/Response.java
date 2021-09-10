package cn.topland.util;

import java.io.Serializable;

/**
 * REST接口数据统一格式
 */
public class Response implements Serializable {

    public static final int OK = 200;
    public static final int ACCESS_FORBIDDEN = 403;
    public static final int INTERNAL_ERROR = 500;

    private int code;

    private Object data;

    private String message;

    public Response(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}