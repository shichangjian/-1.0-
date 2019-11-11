package com.zhitu.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 照片类，包含属性
 */
public class Photo implements Serializable {

    private int photoId;  //照片ID，数据库中主键，自增

    private String name;  //照片名

    private String suffix;  //文件后缀名

    private String path;  //路径(ip:port/newbee_smart_album/user_id/photos)

    private String thumbnailPath;  //缩略图路径

    private String description;  //照片简介

    private String information;  //照片EXIF信息

    private int userId;  //从属者（属于那个用户）

    private int albumId;  //从属相册

    private int likes;  //点赞次数

    private int isPublic;  //是否公开

    private long size;  //占用空间Byte

    private int width;  //图片长

    private int height;  //图片宽

    private Timestamp originalTime;  //照片生成时间

    private Timestamp uploadTime;  //上传时间

    private int inRecycleBin;  //在回收站里面的数量

    private Timestamp deleteTime;  //删除时间

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getInRecycleBin() {
        return inRecycleBin;
    }

    public void setInRecycleBin(int inRecycleBin) {
        this.inRecycleBin = inRecycleBin;
    }

    public Timestamp getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(Timestamp originalTime) {
        this.originalTime = originalTime;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }
}
