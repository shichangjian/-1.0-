package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 禁止编辑，异常
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "禁止编辑")
public class ForbiddenEditException extends RuntimeException {

}
