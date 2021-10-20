package cn.topland.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * REST接口数据统一格式
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {

    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    public static final String FORBIDDEN = "FORBIDDEN";

    public static final String RECORD_NOT_UNIQUE = "RECORD_NOT_UNIQUE";

    public static final String FAILED_VALIDATION = "FAILED_VALIDATION";

    private String code;

    private Object data;

    private String message;

    private List<Errors> errors;

    public Response(Object data) {

        this.data = data;
    }

    public Response(String code, String message) {

        errors = new ArrayList<>();
        Errors error = new Errors(message, new Errors.Extensions(code));
        errors.add(error);
    }

    public Response(Errors error) {

        errors = new ArrayList<>();
        errors.add(error);
    }
}