package com.astar.sharetalent.exception;


/**
 * @author: zhangyu
 * @description: 系统异常定义
 * @date: 2018/6/19
 */
public enum SystemException {

    UNAUTHORIZED("1999", "没有权限"),

    //系统相关异常
    SYSTEM_OTHER_EXCEPTION("500", "其它系统异常"),
    SYSTEM_MISSING_REQUEST_PARAMETERS("9919", "缺少必填参数"),
    SYSTEM_SQL_EXCEPTION("9920", "数据库异常"),
    SYSTEM_REMOTE_SERVICE_NOT_FOUND("9922", "远程服务未找到"),
    SYSTEM_CALL_REMOTE_SERVICE_TIME_OUT("9923", "远程服务调用超时"),
    SYSTEM_HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION("9924", "request handler does not support the specific request method"),
    SYSTEM_HTTP_MEDIATYPE_NOT_SUPPORTED_EXCEPTION("9925", "client POSTs, PUTs, or PATCHes content of a type not supported by request handler"),
    SYSTEM_HTTP_MEDIATYPE_NOT_ACCEPTABLE_EXCEPTION("9926", "the request handler cannot generate a response that is acceptable by the client"),
    SYSTEM_MISSING_PATHVARIABLE_EXCEPTION("9927", "the URI template does not match the path variable name declared on the method parameter"),
    SYSTEM_MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION("9928", "missing servlet request parameter"),
    SYSTEM_SERVLET_REQUEST_BINDING_EXCEPTION("9929", "fatal binding exception, thrown when we want to treat binding exceptions as unrecoverable"),
    SYSTEM_CONVERSION_NOT_SUPPORTED_EXCEPTION("9930", "no suitable editor or converter can be found for a bean property"),
    SYSTEM_TYPE_MISMATCH_EXCEPTION("9931", "type mismatch when trying to set a bean property"),
    SYSTEM_HTTP_MESSAGE_NOT_READABLE_EXCEPTION("9932", "thrown by {@link HttpMessageConverter} implementations when the {@link HttpMessageConverter#read} method fails"),
    SYSTEM_HTTP_MESSAGE_NOT_WRITABLE_EXCEPTION("9933", "thrown by {@link HttpMessageConverter} implementations when the {@link HttpMessageConverter#write} method fails"),
    SYSTEM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION("9934", "validation on an argument annotated with {@code @Valid} fails"),
    SYSTEM_MISSING_SERVLET_REQUEST_PART_EXCEPTION("9935", "raised when the part of a \"multipart/form-data\" request identified by its name cannot be found"),
    SYSTEM_BIND_EXCEPTION("9936", "thrown when binding errors are considered fatal"),
    SYSTEM_NO_HANDLER_FOUND_EXCEPTION("9937", "by default when the DispatcherServlet can't find a handler for a request it sends a 404 response. However if its property throwExceptionIfNoHandlerFound is set to {@code true} this exception is raised and may be handled with a configured HandlerExceptionResolver"),
    SYSTEM_BAD_GATEWAY("9938","502 Bad Gateway");

    private String code;

    private String msg;

    SystemException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
