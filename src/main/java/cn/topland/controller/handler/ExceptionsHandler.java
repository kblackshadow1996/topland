package cn.topland.controller.handler;

import cn.topland.util.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理器
 */
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = AccessException.class)
    public Response handleAccessException(AccessException e) {

        return Responses.fail(Response.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(value = InternalException.class)
    public Response handleInternalException(InternalException e) {

        return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = DataViolateException.class)
    public Response handleUniqueException(DataViolateException e) {

        return Responses.fail(Response.FAILED_VALIDATION, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Response handleInternalException(Exception e) {

        return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}