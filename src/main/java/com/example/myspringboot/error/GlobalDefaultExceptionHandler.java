package com.example.myspringboot.error;

import com.example.myspringboot.exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一业务处理类
 * @author ：dejavu111
 * @date ：Created in 2019/8/15 13:07
 */
// @ControllerAdvice定义统一的异常处理类
@ControllerAdvice(basePackages = {"com.example.myspringboot"})
public class GlobalDefaultExceptionHandler {
    // @ExceptionHandler用来定义函数指针的异常类型，可以传入多个需要被捕获的异常类
    @ExceptionHandler({BusinessException.class})
    // @ControllerAdvice和@ExceptionHandler注解组合使用，实现全局异常，省去使用try..catch
    @ResponseBody
    public ErrorInfo defaultErrorHandler(HttpServletRequest request,Exception e) throws Exception{
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setUrl(request.getRequestURI());
        errorInfo.setCode(ErrorInfo.SUCCESS);
        return errorInfo;
    }
}
