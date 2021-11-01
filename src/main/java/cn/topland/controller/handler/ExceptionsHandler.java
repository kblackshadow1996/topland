package cn.topland.controller.handler;

import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理器
 */
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = AccessException.class)
    public Response<String> handleAccessException(AccessException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.FORBIDDEN, "无操作权限");
    }

    @ExceptionHandler(value = CredentialException.class)
    public Response<String> handleCredentialException(CredentialException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_CREDENTIALS, "无效身份口令, 请刷新后重试");
    }

    @ExceptionHandler(value = ExternalException.class)
    public Response<String> handleExternalException(ExternalException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.EXTERNAL_SERVICE_UNAVAILABLE, "外部服务不可用");
    }

    @ExceptionHandler(value = InvalidException.class)
    public Response<String> handleInvalidException(InvalidException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_CREDENTIALS, "访问令牌过期, 请刷新后重试");
    }

    @ExceptionHandler(value = InvalidIpException.class)
    public Response<String> handleInvalidIpException(InvalidIpException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_IP, "用户ip不可用");
    }

    @ExceptionHandler(value = InvalidOtpException.class)
    public Response<String> handleInvalidOtpException(InvalidOtpException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_OTP, "用户token不可用");
    }

    @ExceptionHandler(value = InvalidPayloadException.class)
    public Response<String> handleInvalidPayloadException(InvalidPayloadException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_PAYLOAD, "负载无效");
    }

    @ExceptionHandler(value = InvalidQueryException.class)
    public Response<String> handleInvalidQueryException(InvalidQueryException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_QUERY, "请求参数无效");
    }

    @ExceptionHandler(value = QueryException.class)
    public Response<String> handleQueryException(QueryException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_QUERY, e.getMessage());
    }

    @ExceptionHandler(value = RequestsExceededException.class)
    public Response<String> handleRequestsExceededException(RequestsExceededException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.REQUESTS_EXCEEDED, "请求频繁");
    }

    @ExceptionHandler(value = RouteNotFoundException.class)
    public Response<String> handleRouteNotFoundException(RouteNotFoundException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.ROUTE_NOT_FOUND, "请求地址不存在");
    }

    @ExceptionHandler(value = UniqueException.class)
    public Response<String> handleUniqueException(UniqueException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.FAILED_VALIDATION, "字段重复, 请更改后重试");
    }

    @ExceptionHandler(value = UnprocessableException.class)
    public Response<String> handleUnprocessableException(UnprocessableException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.UNPROCESSABLE_ENTITY, "非法操作");
    }

    @ExceptionHandler(value = ValidationException.class)
    public Response<String> handleValidationException(ValidationException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.FAILED_VALIDATION, "字段检验失败, 请更改后重试");
    }

    @ExceptionHandler(value = InternalException.class)
    public Response<String> handleInternalException(InternalException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INTERNAL_SERVICE_UNAVAILABLE, "内部服务异常, 请联系管理员");
    }

    @ExceptionHandler(value = InternalAccessException.class)
    public Response<String> handleInternalAccessException(InternalAccessException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INTERNAL_SERVICE_UNAVAILABLE, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Response<String> handleException(Exception e) {

        log.error("内部异常:", e);
        return Responses.fail(Response.INTERNAL_SERVICE_UNAVAILABLE, "内部未知错误");
    }
}