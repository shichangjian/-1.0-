package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 非图像，异常
 */
@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE,reason = "文件不是图像")
public class NotImageException extends RuntimeException {
}
