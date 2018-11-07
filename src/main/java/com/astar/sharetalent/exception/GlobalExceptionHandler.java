package com.astar.sharetalent.exception;

import com.astar.sharetalent.common.R;
import com.astar.sharetalent.util.IpUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author zhangyu
 * @description 统一异常处理
 * @date 2018/6/19 18:21
 */
@RestControllerAdvice()
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object>{

    /**
     * 这里定义的是需要返回详细错误的异常
     */
    private Map<String, SystemException> exceptionMappings = new HashMap<>();

    @PostConstruct
    private void initExceptionMappings() {
        exceptionMappings.put(HttpRequestMethodNotSupportedException.class.getName(),
                SystemException.SYSTEM_HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION);
        exceptionMappings.put(HttpMediaTypeNotSupportedException.class.getName(),
                SystemException.SYSTEM_HTTP_MEDIATYPE_NOT_SUPPORTED_EXCEPTION);
        exceptionMappings.put(HttpMediaTypeNotAcceptableException.class.getName(),
                SystemException.SYSTEM_HTTP_MEDIATYPE_NOT_ACCEPTABLE_EXCEPTION);
        exceptionMappings.put(MissingPathVariableException.class.getName(),
                SystemException.SYSTEM_MISSING_PATHVARIABLE_EXCEPTION);
        exceptionMappings.put(MissingServletRequestParameterException.class.getName(),
                SystemException.SYSTEM_MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
        exceptionMappings.put(ServletRequestBindingException.class.getName(),
                SystemException.SYSTEM_SERVLET_REQUEST_BINDING_EXCEPTION);
        exceptionMappings.put(ConversionNotSupportedException.class.getName(),
                SystemException.SYSTEM_CONVERSION_NOT_SUPPORTED_EXCEPTION);
        exceptionMappings.put(TypeMismatchException.class.getName(),
                SystemException.SYSTEM_TYPE_MISMATCH_EXCEPTION);
        exceptionMappings.put(HttpMessageNotReadableException.class.getName(),
                SystemException.SYSTEM_HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
        exceptionMappings.put(HttpMessageNotWritableException.class.getName(),
                SystemException.SYSTEM_HTTP_MESSAGE_NOT_WRITABLE_EXCEPTION);
        exceptionMappings.put(MissingServletRequestPartException.class.getName(),
                SystemException.SYSTEM_MISSING_SERVLET_REQUEST_PART_EXCEPTION);
        exceptionMappings.put(NoHandlerFoundException.class.getName(),
                SystemException.SYSTEM_NO_HANDLER_FOUND_EXCEPTION);
        exceptionMappings.put(SQLException.class.getName(),
                SystemException.SYSTEM_SQL_EXCEPTION);
        exceptionMappings.put(UndeclaredThrowableException.class.getName(),
                SystemException.SYSTEM_REMOTE_SERVICE_NOT_FOUND);
    }


    /**
     * 验证异常
     * @param ex
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class,BindException.class,MethodArgumentNotValidException.class,AuthException.class})
    public R validException(HttpServletRequest request, Exception ex){
        SystemException systemException = SystemException.SYSTEM_OTHER_EXCEPTION;
        String code = systemException.getCode();
        String message = systemException.getMsg();
        log.error("IP={}, URL={}, QueryString={}", IpUtil.getIpAddr(request), request.getRequestURI(), request.getQueryString());
        log.error(ex.getMessage(), ex);

        if (ex instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> constraintViolations =  ((ConstraintViolationException) ex).getConstraintViolations();
            StringBuilder sb = new StringBuilder();
            constraintViolations.forEach(constraintViolation -> sb.append(constraintViolation.getMessage() + ";"));
            message = sb.substring(0,sb.length()-1);
        }else if (ex instanceof BindException){
            List<FieldError> fieldErrors = ((BindException)ex).getBindingResult().getFieldErrors();
            StringBuilder sb = new StringBuilder();
            fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage() + ";"));
            message = sb.substring(0,sb.length()-1);
        }else if (ex instanceof MethodArgumentNotValidException){
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
            StringBuilder sb = new StringBuilder();
            fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage() + ";"));
            message = sb.substring(0,sb.length()-1);
        }else if (ex instanceof AuthException){
            //微信登录权限错误
            message = ex.getMessage();
        }
        return R.error(code,message);
    }



    @ExceptionHandler(Exception.class)
    public R remoteServiceException(HttpServletRequest request, HttpServletResponse response, Exception ex){
        SystemException systemException = SystemException.SYSTEM_OTHER_EXCEPTION;
        String code = systemException.getCode();
        String message = systemException.getMsg();
        log.error("IP={}, URL={}, QueryString={}",IpUtil.getIpAddr(request), request.getRequestURI(), request.getQueryString());
        log.error(ex.getMessage(), ex);

        if (ex instanceof RuntimeException && StrUtil.containsIgnoreCase(ex.getMessage(),BusinessException.class.getName())) {
            message = ex.getMessage().substring(BusinessException.class.getName().length() + 1, ex.getMessage().indexOf("\n")).trim();
        }else if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;
            message = businessException.getMessage();
            code = businessException.getCode();
        }else {
            if (exceptionMappings.get(ex.getClass().getName()) != null) {
                systemException = exceptionMappings.get(ex.getClass().getName());
            }
            if (StrUtil.containsIgnoreCase(ex.getMessage(), "TimeoutException")) {
                systemException = SystemException.SYSTEM_CALL_REMOTE_SERVICE_TIME_OUT;
            }
            if (ex instanceof IllegalStateException && StrUtil.isNotEmpty(ex.getMessage()) && StrUtil.endWith(ex.getMessage(),
                    "Consider declaring it as object wrapper for the corresponding primitive type.")) {
                systemException = SystemException.SYSTEM_MISSING_REQUEST_PARAMETERS;
            }
            code = systemException.getCode();
            message = systemException.getMsg();
        }

        return R.error(code,message);
    }


    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return !(returnType.getDeclaringClass().getName().contains("swagger"));
    }


    /**
     * 将接口返回的对象统一包装
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof R){
            return body;
        }else {
            return R.ok(body);
        }
    }


}