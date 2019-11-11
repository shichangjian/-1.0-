package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 页面不存在，异常
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "页面不存在")
public class PageNotExistException extends RuntimeException {
}
