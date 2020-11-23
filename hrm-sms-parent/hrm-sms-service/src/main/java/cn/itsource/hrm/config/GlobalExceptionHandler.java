package cn.itsource.hrm.config;

import cn.itsource.basic.util.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理
@RestControllerAdvice //可以在controller执行前，后做一些事情
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)   //处理异常
    public AjaxResult exceptionHandler(Exception e){
        e.printStackTrace();
        return AjaxResult.me().setSuccess(false).setMessage("操作失败!"+e.getMessage());
    }
}