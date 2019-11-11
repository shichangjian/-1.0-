package com.zhitu;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 智能相册应用程序启动类
 */
@SpringBootApplication
@MapperScan("com.zhitu.dao.mapper") //用来扫描mybatis接口
@EnableScheduling
@ServletComponentScan
public class SmartAlbumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAlbumApplication.class, args);
    }

}
