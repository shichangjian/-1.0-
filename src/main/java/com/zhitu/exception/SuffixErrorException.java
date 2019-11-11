package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 后缀错误，异常
 */
@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE,reason = "后缀错误")
public class SuffixErrorException extends RuntimeException {
}
