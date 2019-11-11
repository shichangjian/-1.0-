package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 用户名或邮件不存在，异常
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY,reason = "用户名或电子邮件不存在")
public class UsernameOrEmailNotExistException extends RuntimeException {
}
