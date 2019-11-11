package com.zhitu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 已经登录，异常
 */
// @ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析。
// 可以实现自定义的一些异常,同时在页面上进行显示，
// 注解中有两个参数，value属性设置异常的状态码，reaseon是异常的描述
// 将@ResponseStatus注解加在目标方法上，一定会抛出异常。但是如果没有发生异常的话方法会正常执行完毕。

//@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "already logged in")
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "已登录")
public class AlreadyLogInException extends RuntimeException {
}
