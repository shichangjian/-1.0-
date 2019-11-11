package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Sid错误，异常
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "SID已过期或不存在")
public class SidErrorException extends RuntimeException {
}
