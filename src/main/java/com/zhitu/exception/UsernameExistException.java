package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 用户名存在，异常
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY,reason = "用户名已注册")
public class UsernameExistException extends RuntimeException {
}
