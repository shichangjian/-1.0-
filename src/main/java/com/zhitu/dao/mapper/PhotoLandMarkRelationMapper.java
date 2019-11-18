package com.zhitu.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 照片标签关系映射器
 */
public interface PhotoLandMarkRelationMapper {

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    void insert(@Param("photoId") int photoId, @Param("landmarkId") int landmarkId);
}

