package cn.itsource.hrm.config;

import cn.itsource.basic.util.AjaxResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理
@RestControllerAdvice //可以在controller执行前，后做一些事情
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)   //处理异常 - 参数验证异常
    public AjaxResult globalExceptionHandler(MethodArgumentNotValidException e){
        e.printStackTrace();
        //获取异常的字段和异常信息
        FieldError fieldError = e.getBindingResult().getFieldError();
        return AjaxResult.me().setSuccess(false).setMessage(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)   //处理异常
    public AjaxResult exceptionHandler(Exception e){
        e.printStackTrace();
        return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
    }
}