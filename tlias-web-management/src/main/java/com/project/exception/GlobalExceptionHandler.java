package com.project.exception;

import com.project.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 这就等于重写了异常, 这样所以异常都会被这个方法处理
     */
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("程序出错了", e);
        return Result.error("出错了, 请联系管理员");
    }

    /**
     * 这个在正式项目中也比较常见
     */
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
         log.error("程序出错", e);
         String message = e.getMessage();
         int i = message.indexOf("Duplicate entry");
         String errMsg = message.substring(i);
         String[] arr = errMsg.split(" ");
         return Result.error(arr[2] + "已存在");
    }
}
