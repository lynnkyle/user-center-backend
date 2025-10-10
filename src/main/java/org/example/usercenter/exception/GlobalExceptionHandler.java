package org.example.usercenter.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.usercenter.common.BaseResponse;
import org.example.usercenter.common.ErrorCode;
import org.example.usercenter.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author LinZeyuan
 * @description 全局异常处理器类
 * @createDate 2025/10/10 16:23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessExceptionHandler", e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "");
    }
}
