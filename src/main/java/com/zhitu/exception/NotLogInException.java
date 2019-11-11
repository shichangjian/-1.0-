package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 未登录异常
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "未登录")
public class NotLogInException extends RuntimeException {
}
