package com.zhitu.service;

import com.zhitu.entity.Photo;

import java.io.File;

/**
 * 异步任务服务
 */
public interface AsyncTaskService {

    void photoUploadTask(int userId, int albumId, String prefix, String suffix, String uploadPath, File uploadFile);

    void photoUploadTask(int userId, int albumId, String prefix, String suffix, String uploadPath, File uploadFile, Photo photo);
}
