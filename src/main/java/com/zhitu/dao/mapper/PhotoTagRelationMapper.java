package com.zhitu.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 照片标签关系映射器
 */
public interface PhotoTagRelationMapper {

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    void insert(@Param("photoId") int photoId, @Param("tagId") int tagId, @Param("score") double score);

    List<Integer> selectPhotoIdByTagIdOrderByScoreDesc(List<Integer> list);

    List<Long> selectAllRelationIdByPhotoId(@Param("photoId") int photoId);

    void deleteByRelationId(@Param("relationId") long relationId);

    List<Integer> selectTagIdByPhotoId(@Param("photoId") int photoId);

    Integer selectExistByPhotoIdAndTagId(@Param("photoId") int photoId, @Param("tagId") int tagId);

    Long selectRelationIdByPhotoIdAndTagId(@Param("photoId") int photoId, @Param("tagId") int tagId);

}
