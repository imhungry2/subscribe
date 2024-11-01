package com.fastcampus.netplix.advice;

import com.fastcampus.netplix.controller.NetplixApiResponse;
import com.fastcampus.netplix.exception.ErrorCode;
import com.fastcampus.netplix.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(UserException.class)
    protected NetplixApiResponse<?> handleUserException(UserException e) {
        log.error("error={}", e.getMessage(), e);
        return NetplixApiResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    protected NetplixApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("error={}", e.getMessage(), e);
        return NetplixApiResponse.fail(ErrorCode.DEFAULT_ERROR, e.getMessage());
    }
}
