package com.zhitu.dao.mapper;

import com.zhitu.entity.Photo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 照片映射器
 */
public interface PhotoMapper {

    void insert(Photo photo);

    List<Photo> selectAllLandmarkPhoto(@Param("userId") int userId, @Param("begin") int begin);

    List<Photo> selectLandmarkPhoto(@Param("userId") int userId, @Param("landmarkId") int landmarkId, @Param("begin") int begin);

    List<Map<String, Object>> getLandmarks(@Param("userId") int userId);

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    Photo selectAllByPhotoId(@Param("photoId") int photoId);

    Photo selectAllByPhotoIdWhereIsPublic(@Param("photoId") int photoId);

    List<Integer> selectPhotoIdByAlbumId(@Param("albumId") int albumId);

    void updateAlbumIdByPhotoId(@Param("photoId") int photoId, @Param("albumId") int albumId);

    void moveToRecycleBinByPhotoId(@Param("photoId") int photoId, @Param("time") Timestamp time);

    void updateByPhotoId(@Param("photoId") int photoId, @Param("name") String name, @Param("description") String description,
                         @Param("isPublic") int isPublic);

    void updateIsPublicByPhotoId(@Param("photoId") int photoId, @Param("isPublic") int isPublic);

    List<Photo> selectAllPhotoNotInRecycleBinByAlbumIdOrderByUploadTimeDesc(@Param("albumId") int albumId);

    List<Photo> selectAllPhotoNotInRecycleBinByAlbumIdOrderByUploadTimeDescLimitPage(@Param("albumId") int albumId, @Param("begin") int begin);

    Integer selectInRecycleBinByPhotoId(@Param("photoId") int photoId);

    List<Photo> selectPhotoInRecycleBinByUserIdOrderByDeleteTimeDesc(@Param("userId") int userId);

    void moveOutRecycleBinByPhotoId(@Param("photoId") int photoId);

    List<Integer> selectPhotoIdWhereExpired();

    void deleteByPhotoId(@Param("photoId") int photoId);

    List<Photo> selectAllPhotoNotInRecycleBinByUserIdOrderByUploadTimeDescLimitPage(@Param("userId") int userId, @Param("begin") int begin);

    Integer selectPhotoIdByPath(@Param("path") String path);

    void updateLikesByPhotoId(@Param("photoId") int photoId, @Param("amount") int amount);

    List<Integer> selectPhotoIdLikeName(List<String> keyword);

    List<Photo> selectAllPhotoNotInRecycleBinByUserIdOrderByOriginalTimeAndUploadTimeDesc(@Param("userId") int userId);

    List<Photo> selectOrderByLikesLimit(@Param("limit") int limit);
}
