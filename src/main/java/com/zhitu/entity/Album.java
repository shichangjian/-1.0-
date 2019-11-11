package com.zhitu.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 相册实体，包含相册属性
 */
public class Album implements Serializable {

    private Integer albumId; //相册ID，数据库中主键，自增

    private String name; //相册名

    private Integer userId;  //使用这个相册的用户ID

    private int cover;

    private Timestamp createTime; //相册创建时间

    private Timestamp lastEditTime; //相册最后一次编辑时间

    private String description;  //相册描述

    private int isDefaultAlbum;  //是否是默认相册，1是0不是

    private int photoAmount; //照片数量

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Timestamp lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsDefaultAlbum() {
        return isDefaultAlbum;
    }

    public void setIsDefaultAlbum(int isDefaultAlbum) {
        this.isDefaultAlbum = isDefaultAlbum;
    }

    public int getPhotoAmount() {
        return photoAmount;
    }

    public void setPhotoAmount(int photoAmount) {
        this.photoAmount = photoAmount;
    }
}
