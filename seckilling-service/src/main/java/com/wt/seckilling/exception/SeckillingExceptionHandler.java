package com.wt.seckilling.exception;

import ai.ii.common.dto.RestResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: qiyu
 * @date: 2019/12/10 11:49
 * @description:
 */
@ControllerAdvice(basePackages = {"com.wt.seckilling"})
@Slf4j
public class SeckillingExceptionHandler extends RequestBodyAdviceAdapter {

    @Autowired
    private SeckillingRequestContext seckillingRequestContext;

    /**
     * RequestBody校验异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RestResult<?> handleValidationException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder("输入错误:");
        for (ObjectError objectError : objectErrorList) {
            sb.append(objectError.getDefaultMessage()).append("!");
        }
        String message = sb.toString();
        log.warn("Invalid inbound requestBody,URI:{},body:{},message:{}", request.getRequestURI(), JSON.toJSON(seckillingRequestContext.getRequestBody()), message, e);
        return new RestResult<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    /**
     * RequestParam校验异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public RestResult<?> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder("输入错误:");
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            sb.append(constraintViolation.getMessage()).append("!");
        }
        String message = sb.toString();
        log.warn("Invalid inbound requestParam,URI:{},parameters:{},message:{}", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), message, e);
        return new RestResult<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = SeckillingException.class)
    public RestResult<?> handleSeckillingException(HttpServletRequest request, SeckillingException e) {
        log.error("SeckillingException Error,URI:{},body:{},parameters:{}", request.getRequestURI(), JSON.toJSONString(seckillingRequestContext.getRequestBody()), JSON.toJSONString(request.getParameterMap()), e);
        return new RestResult<>(e.getCode(), e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = SeckillingRuntimeException.class)
    public RestResult<?> handleSeckillingRuntimeException(HttpServletRequest request, SeckillingRuntimeException e) {
        log.error("SeckillingRuntimeException Error,URI:{},body:{},parameters:{}", request.getRequestURI(), JSON.toJSONString(seckillingRequestContext.getRequestBody()), JSON.toJSONString(request.getParameterMap()), e);
        return new RestResult<>(e.getCode(), e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RestResult<?> handleException(HttpServletRequest request, Exception e) {
        log.error("System Error,URI:{},body:{},parameters:{}", request.getRequestURI(), JSON.toJSONString(seckillingRequestContext.getRequestBody()), JSON.toJSONString(request.getParameterMap()), e);
        return new RestResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常", null);
    }


    /**
     * The default implementation returns the body that was passed in.
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        seckillingRequestContext.setRequestBody(body);
        return body;
    }

    /**
     * Invoked first to determine if this interceptor applies.
     *
     * @param methodParameter the method parameter
     * @param targetType      the target type, not necessarily the same as the method
     *                        parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType   the selected converter type
     * @return whether this interceptor should be invoked or not
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}
