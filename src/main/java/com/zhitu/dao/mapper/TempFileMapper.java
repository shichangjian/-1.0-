package com.zhitu.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 临时文件映射器
 */
public interface TempFileMapper {

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    void insert(@Param("path") String path, @Param("time") Timestamp time);

    List<Integer> selectTempFileIdWhereExpired();

    String selectPathByTempFileId(@Param("tempFileId") int tempFileId);

    void deleteByTempFileId(@Param("tempFileId") int tempFileId);
}
