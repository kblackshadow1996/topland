package cn.topland.controller.handler;

import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.*;
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

    @ExceptionHandler(value = UniqueException.class)
    public Response handleUniqueException(UniqueException e) {

        return Responses.fail(Response.INVALID_PARAMS, e.getMessage());
    }

    @ExceptionHandler(value = QueryException.class)
    public Response handleQueryException(QueryException e) {

        return Responses.fail(Response.INVALID_PARAMS, e.getMessage());
    }

    @ExceptionHandler(value = InvalidException.class)
    public Response handleInvalidException(InvalidException e) {

        return Responses.fail(Response.INVALID, e.getMessage());
    }

    @ExceptionHandler(value = InternalException.class)
    public Response handleInternalException(InternalException e) {

        return Responses.fail(Response.INTERNAL_SERVICE_UNAVAILABLE, e.getMessage());
    }

    @ExceptionHandler(value = ExternalException.class)
    public Response handleExternalException(ExternalException e) {

        return Responses.fail(Response.EXTERNAL_SERVICE_UNAVAILABLE, e.getMessage());
    }

//    @ExceptionHandler(value = Exception.class)
//    public Response handleException(Exception e) {
//
//        return Responses.fail(Response.INTERNAL_SERVICE_UNAVAILABLE, "内部未知错误");
//    }
}