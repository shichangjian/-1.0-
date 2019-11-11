package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 空间已满，异常
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "可用空间已满")
public class SpaceAlreadyFullException extends RuntimeException {
}
