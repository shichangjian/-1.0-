package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 电子邮件不存在，异常
 */
//@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY,reason = "email does not exist")
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY,reason = "电子邮件不存在")
public class EmailNotExistException extends RuntimeException {
}
