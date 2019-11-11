package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 禁止进入，异常
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "禁止进入")
public class ForbiddenAccessException extends RuntimeException{
}
