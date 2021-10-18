package cn.topland.controller.handler;

import cn.topland.util.AccessException;
import cn.topland.util.InternalException;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Response handleUniqueException(DataIntegrityViolationException e) {

        return Responses.fail(Response.FAILED_VALIDATION, e.getMessage());
    }

//    @ExceptionHandler(value = Exception.class)
//    public Response handleInternalException(Exception e) {
//
//        return Responses.fail(Response.INTERNAL_SERVER_ERROR, e.getMessage());
//    }
}