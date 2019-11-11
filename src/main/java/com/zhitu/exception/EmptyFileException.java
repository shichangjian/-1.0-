package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 空文件，异常
 */
@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE,reason = "空文件错误")
public class EmptyFileException extends RuntimeException {
}
