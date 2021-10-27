package cn.topland.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * REST接口数据统一格式
 */
@Setter
@Getter
public class Response implements Serializable {

    public static final int OK = 200;

    public static final int FAILED_VALIDATION = 400;

    public static final int INVALID_PAYLOAD = 400;

    public static final int INVALID_QUERY = 400;

    public static final int INVALID_CREDENTIALS = 401;

    public static final int INVALID_IP = 401;

    public static final int INVALID_OTP = 401;

    public static final int FORBIDDEN = 403;

    public static final int ROUTE_NOT_FOUND = 404;

    public static final int UNPROCESSABLE_ENTITY = 422;

    public static final int REQUESTS_EXCEEDED = 429;

    public static final int INTERNAL_SERVICE_UNAVAILABLE = 500;

    public static final int EXTERNAL_SERVICE_UNAVAILABLE = 503;

    private int code;

    private Object data;

    private String message;

    private boolean success;

    public Response(Object data) {

        this.code = OK;
        this.data = data;
    }

    public Response(int code, String message) {

        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {

        return OK == this.code;
    }
}