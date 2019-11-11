package com.zhitu.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 对线程池进行 配置(config)
 */
@Configuration   //等同于spring的XML配置文件；使用Java代码可以检查类型安全
@EnableAsync     //异步任务多线程使用
public class ThreadConfig implements AsyncConfigurer {

    @Override   //覆盖了父类的方法

    public Executor getAsyncExecutor() {
        //新建一个线程池任务执行器类的executor
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心池大小
        executor.setCorePoolSize(10);
        //设置最大池大小
        executor.setMaxPoolSize(20);
        //设置队列容量
        executor.setQueueCapacity(200);
        //设置拒绝执行处理程序
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置线程名称前缀newbeeSmartAlbum-
        executor.setThreadNamePrefix("newbeeSmartAlbum-");
        return null;
    }

    @Override
    /**
     * 获取异步未捕获异常处理程序
     */
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
