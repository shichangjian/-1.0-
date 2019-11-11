package com.zhitu.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface PhotoAlbumRelationMapper {
    void insert(@Param("photoId") int photoId, @Param("albumId") int albumId);
    Integer selectAlbumIdByPhotoId(@Param("photoId") int photoId);
    void editAlbumIdByPhotoId(@Param("photoId") int photoId);
}
