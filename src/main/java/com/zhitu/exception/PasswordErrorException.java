package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 密码错误，异常
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY,reason = "密码错误")
public class PasswordErrorException extends RuntimeException {
}
