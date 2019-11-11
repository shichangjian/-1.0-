package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 上传失败，异常
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason = "上传失败")
public class UploadFailedException extends RuntimeException {
}
