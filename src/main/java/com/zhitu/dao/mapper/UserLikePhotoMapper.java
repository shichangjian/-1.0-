package com.zhitu.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户喜欢的相片映射器
 */
public interface UserLikePhotoMapper {

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    Long selectUserLikePhotoIdByUserIdAndPhotoId(@Param("userId") int userId, @Param("photoId") int photoId);

    void insert(@Param("userId") int userId, @Param("photoId") int photoId);

    void deleteByUserLikePhotoId(@Param("id") long id);

    List<Long> selectAllUserLikePhotoIdByPhotoId(@Param("photoId") int photoId);
}
