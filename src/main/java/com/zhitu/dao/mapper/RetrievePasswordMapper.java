package com.zhitu.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 检索密码映射器
 */
public interface RetrievePasswordMapper {

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    void insert(@Param("userId") int userId, @Param("sid") String sid, @Param("time") Timestamp time);

    Integer selectUserIdBySid(@Param("sid") String sid);

    List<Integer> selectRetrievePasswordIdWhereExpired();

    void deleteByRetrievePasswordId(@Param("id") int id);

    void deleteBySid(@Param("sid") String sid);

}
