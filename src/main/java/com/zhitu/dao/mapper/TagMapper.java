package com.zhitu.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签映射器
 */
public interface TagMapper {

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    void insert(@Param("name") String name);

    Integer selectExistByName(@Param("name") String name);

    Integer selectTagIdByName(@Param("name") String name);

    List<Integer> selectTagIdLikeName(List<String> keywordList);

    String selectNameByTagId(@Param("tagId") int tagId);
}
