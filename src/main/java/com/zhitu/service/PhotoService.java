package com.zhitu.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 照片服务
 */
public interface PhotoService {

    Map<String, Object> getFace(int photold);

    Map<String, Object> getFacePhotos(int userId, int page);

    Map<String, Object> getAllLandmarkPhoto(int userId, int begin);

    Map<String, Object> getLandmarkPhoto(int userId, int landmarkId, int begin);

    public Map<String, Object> getLandmarks(int userId);

    void upload(int userId, MultipartFile file, String name, String description, int albumId, int isPublic, String[] tags) throws IOException;

    Map<String,Object> uploads(int userId, int albumId, MultipartFile[] files) throws IOException;

    void download(int photoId, HttpServletResponse response);

    void downloads(List<Integer> photos, HttpServletResponse response);

    void moveToRecycleBin(int userId, List<Integer> photos);

    void edit(int userId, int photoId, String name, String description, int isPublic, List<String> tags);

    void show(Object userIdObject, int photoId, HttpServletResponse response);

    void showThumbnail(Object userIdObject, int photoId, HttpServletResponse response);

    List<Map<String, Object>> getRecycleBinPhotos(int userId);

    void move(int userId, int photoId, int albumId, int tagId);

    void moveOutRecycleBin(int userId, List<Integer> photos);

    void completelyDelete(int userId, List<Integer> photos);

    Map<String,Object> getPhotos(int userId, int tagId, int page);

    Map<String,Object> globalSearch(Object userIdObject, String keyword, int page);

    String like(int userId, int photoId);

    Map<String,Object> personalSearch(int userId, String keyword, int page);

    List<Map<String ,Object>> timeline(int userId);

    List<Map<String,Object>> recommend(Object userIdObject);
}
