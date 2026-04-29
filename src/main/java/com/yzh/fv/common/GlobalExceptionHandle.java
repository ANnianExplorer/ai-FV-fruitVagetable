package com.yzh.fv.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * 全局异常处理
 *
 * @author 杨振华
 * @since 2023/1/12
 */
// @ControllerAdvice 注解来对annotation含有的注解执行异常处理
@ControllerAdvice(annotations = {RestController.class,Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandle {

    /**
     * 异常处理方法
     *
     * @return {@link R}<{@link String}>
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandle(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandle(CustomException ex){
        log.error("业务异常：{}", ex.getMessage());
        return R.error(ex.getMessage());
    }
    
    /**
     * 处理参数校验异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> exceptionHandler(MethodArgumentNotValidException ex) {
        // 获取校验失败的字段和错误信息
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("参数校验失败");
        log.warn("参数校验异常：{}", errorMessage);
        return R.error(errorMessage);
    }

    /**
     * 处理其他异常
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception ex) {
        log.error("未知错误：{}", ex.getMessage(), ex);
        return R.error("未知错误");
    }
}