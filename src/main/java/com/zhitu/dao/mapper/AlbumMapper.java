package com.zhitu.dao.mapper;

import com.zhitu.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 相册映射器，都与数据库有关，连接的，就和上次做的Demo一样
 */
public interface AlbumMapper {

    void insert(Album album);  //相册插入

    //在方法参数的前面写上@Param("参数名"),表示给参数命名,名称就是括号中的内容
    Integer selectDefaultAlbumIdByUserId(@Param("userId") int userId); //按用户ID选择默认相册ID

    Integer selectDefaultAlbumIdByAlbumId(@Param("albumId") int albumId);  //按相册id选择默认id

    void updatePhotoAmountByAlbumId(@Param("albumId") int albumId, @Param("amount") int amount);  //按相册ID列出的最新照片数量

    void updateLastEditTimeByAlbumId(@Param("albumId") int albumId, @Param("time") Timestamp time);  //按相册ID更新上次编辑时间

    void editAlbumByAlbumId(@Param("albumId") int albumId, @Param("name") String name, @Param("cover") int cover,
                            @Param("description") String description);  //按相册id编辑相册信息


    void deleteByAlbumId(@Param("albumId") int albumId);  //删除相册

    Integer selectUserIdByAlbumId(@Param("albumId") int albumId);  //按相册id选择用户id

    Integer selectIsDefaultAlbumByAlbumId(@Param("albumId") int albumId); //按相册id选择是否是默认相册

    List<Album> selectAllAlbumByUserId(@Param("userId") int userId);  //按用户id选择所有相册

    Album selectAllByAlbumId(@Param("albumId") int albumId);  //按相册id全选

    void updateCoverByAlbumId(@Param("albumId") int albumId, @Param("photoId") int photoId);  //按相册列出最新封面
}
