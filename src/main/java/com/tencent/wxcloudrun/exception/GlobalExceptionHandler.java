package com.tencent.wxcloudrun.exception;

import com.tencent.wxcloudrun.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 运行时的异常处理
     */

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> filerErrors = e.getBindingResult().getFieldErrors();
        String defaultMessage = filerErrors.get(0).getDefaultMessage();
        log.error("错误的原因为："+defaultMessage);
        return Response.builder().status(500).message(defaultMessage).build();
    }
}
