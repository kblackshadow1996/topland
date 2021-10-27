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
    public Response handleAccessException(AccessException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.FORBIDDEN, "无操作权限");
    }

    @ExceptionHandler(value = CredentialException.class)
    public Response handleCredentialException(CredentialException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.FORBIDDEN, "口令过期, 请刷新后重试");
    }

    @ExceptionHandler(value = ExternalException.class)
    public Response handleExternalException(ExternalException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.EXTERNAL_SERVICE_UNAVAILABLE, "外部服务不可用");
    }

    @ExceptionHandler(value = InvalidException.class)
    public Response handleInvalidException(InvalidException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_CREDENTIALS, "访问令牌过期, 请刷新后重试");
    }

    @ExceptionHandler(value = InvalidIpException.class)
    public Response handleInvalidIpException(InvalidIpException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_IP, "用户ip不可用");
    }

    @ExceptionHandler(value = InvalidOtpException.class)
    public Response handleInvalidOtpException(InvalidOtpException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_OTP, "用户ip不可用");
    }

    @ExceptionHandler(value = InvalidPayloadException.class)
    public Response handleInvalidPayloadException(InvalidPayloadException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_PAYLOAD, "负载无效");
    }

    @ExceptionHandler(value = InvalidQueryException.class)
    public Response handleInvalidQueryException(InvalidQueryException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INVALID_QUERY, "请求参数无效");
    }

    @ExceptionHandler(value = RequestsExceededException.class)
    public Response handleRequestsExceededException(RequestsExceededException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.REQUESTS_EXCEEDED, "请求频繁");
    }

    @ExceptionHandler(value = RouteNotFoundException.class)
    public Response handleRouteNotFoundException(RouteNotFoundException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.ROUTE_NOT_FOUND, "请求地址不存在");
    }

    @ExceptionHandler(value = UniqueException.class)
    public Response handleUniqueException(UniqueException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.FAILED_VALIDATION, "字段重复, 请更改后重试");
    }

    @ExceptionHandler(value = UnprocessableException.class)
    public Response handleUnprocessableException(UnprocessableException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.UNPROCESSABLE_ENTITY, "非法操作");
    }

    @ExceptionHandler(value = ValidationException.class)
    public Response handleValidationException(ValidationException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.FAILED_VALIDATION, "字段检验失败, 请更改后重试");
    }

    @ExceptionHandler(value = InternalException.class)
    public Response handleInternalException(InternalException e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INTERNAL_SERVICE_UNAVAILABLE, "内部服务异常, 请联系管理员");
    }

    @ExceptionHandler(value = Exception.class)
    public Response handleException(Exception e) {

        log.error(e.getMessage());
        return Responses.fail(Response.INTERNAL_SERVICE_UNAVAILABLE, "内部未知错误");
    }
}