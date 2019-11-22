package com.zhitu.dao.mapper;

import com.zhitu.entity.Face;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FaceMapper {

    void insert(Face face);

    List<Face> selectFaces(@Param("photoId") int photoId);
}
