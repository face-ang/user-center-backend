package com.xjtu.usercenter.exception;

import com.xjtu.usercenter.common.BaseResponse;
import com.xjtu.usercenter.common.ErrorCode;
import com.xjtu.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobleExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse bussinessExceptionHandler(BusinessException e) {
        log.error("bussinessException:" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException:", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
