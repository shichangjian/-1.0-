package com.zhitu.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhitu.dao.mapper.*;
import com.zhitu.dao.mapper.*;
import com.zhitu.entity.Photo;
import com.zhitu.dao.mapper.*;
import com.zhitu.externalAPI.Baidu;
import com.zhitu.service.AsyncTaskService;
import com.zhitu.tools.PhotoTool;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Semaphore;

/**
 * 异步任务服务实现类
 */
@Service //告诉spring创建一个实现类的实例表示这是一个bean
@Async  //异步调用
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private static Semaphore semaphore = new Semaphore(1);

    @Resource
    private PhotoLandMarkRelationMapper photoLandMarkRelationMapper;

    @Resource
    private LandMarkMapper landMarkMapper;

    @Resource  //指定这个类型或名字的bean
    private PhotoMapper photoMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AlbumMapper albumMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private PhotoTagRelationMapper photoTagRelationMapper;

    @Autowired
    private PhotoTool photoTool;

    @Autowired
    private Baidu baidu;

    private void doUpload(int userId, int albumId, String suffix, String uploadPath, File uploadFile, Photo photo, String[] cus_tags) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(uploadFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //新建一个Photo对象用来保存照片信息并写入数据库
        photo.setSuffix(suffix);
        //压缩并保存
        String thumbnailPath = photoTool.THUMBNAIL_DIR + userId + "/" + UUID.randomUUID() + "." + suffix;
        File thumbnailFile = new File(photoTool.LOCAL_DIR + thumbnailPath);
        if (!thumbnailFile.getParentFile().exists()) {
            if (!thumbnailFile.getParentFile().mkdirs())
                return;
        }
        try {
            Thumbnails.of(uploadFile).scale(0.5).outputQuality(0.5).toFile(thumbnailFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        photo.setThumbnailPath(thumbnailPath);
        //计算文件大小，保存在数据库中
        long fileSizeB = uploadFile.length();
        photo.setSize(fileSizeB);
        //如果是jpeg格式的图片，处理EXIF信息
        if (photoTool.isJpeg(suffix)) {
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(uploadFile);
                Map<String, String> map = new HashMap<>();
                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {
                        map.put(tag.getTagName(), tag.getDescription());
                        if (tag.getTagName().equals("Date/Time Original")) {
                            photo.setOriginalTime(photoTool.exifTimeToTimestamp(tag.getDescription()));
                        }
                    }
                }
                //MAP转JSON,并写入photo对象
                ObjectMapper objectMapper = new ObjectMapper();
                photo.setInformation(objectMapper.writeValueAsString(map));
            } catch (ImageProcessingException | IOException e) {
                e.printStackTrace();
            }
        }
        photo.setWidth(image.getWidth());
        photo.setHeight(image.getHeight());
        photo.setUserId(userId);
        photo.setLikes(0);
        photo.setAlbumId(albumId);
        photo.setInRecycleBin(0);
        photo.setPath(uploadPath);
        photo.setDescription("");
        photo.setUploadTime(new Timestamp(System.currentTimeMillis()));
        //将photo对象写入数据库
        photoMapper.insert(photo);
        //更新已用空间
        userMapper.updateUsedSpaceByUserId(userId, fileSizeB);
        //更新照片数量
        userMapper.updatePhotoAmountByUserId(userId, 1);
        //更新相册信息
        albumMapper.updatePhotoAmountByAlbumId(albumId, 1);
        albumMapper.updateLastEditTimeByAlbumId(albumId, new Timestamp(System.currentTimeMillis()));
        //图片AI智能识别标签
        String tagJsonString = baidu.imageClassify(thumbnailFile, suffix, Baidu.ADVANCED_GENERAL);
        List<Map<String, Object>> tagList = null;
        try {
            tagList = baidu.photoTag(tagJsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tagList != null && tagList.size() > 0) {
            for (Map<String, Object> tag : tagList) {
                if (tagMapper.selectExistByName(tag.get("keyword").toString()) == null)
                    tagMapper.insert(tag.get("keyword").toString());
                int photoId = photoMapper.selectPhotoIdByPath(uploadPath);
                int tagId = tagMapper.selectTagIdByName(tag.get("keyword").toString());
                photoTagRelationMapper.insert(photoId, tagId, Double.parseDouble(tag.get("score").toString()));
            }
        }
        //添加用户自定义的标签
        if (cus_tags != null && cus_tags.length > 0) {
            for (String tag : cus_tags) {
                if (tagMapper.selectExistByName(tag) == null)
                    tagMapper.insert(tag);
                int tagId = tagMapper.selectTagIdByName(tag);
                photoTagRelationMapper.insert(photo.getPhotoId(), tagId, 1);
            }
        }
        //地标识别
        String marklandJsonString = baidu.imageClassify(thumbnailFile, suffix, Baidu.LANDMARK);
        try {
            String marklandStr = baidu.landmarkJson(marklandJsonString);
            if (null != marklandStr && marklandStr.trim().length() > 0) {
                if (landMarkMapper.selectExistByName(marklandStr) == null)
                    landMarkMapper.insert(marklandStr);
                int marklandId = landMarkMapper.selectLandmarkIdByName(marklandStr);
                photoLandMarkRelationMapper.insert(photo.getPhotoId(), marklandId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 照片批量上传
     * @param userId
     * @param albumId
     * @param prefix
     * @param suffix
     * @param uploadPath
     * @param uploadFile
     */
    @Override
    public void photoUploadTask(int userId, int albumId, String prefix, String suffix, String uploadPath, File uploadFile) {
        //新建一个Photo对象用来保存照片信息并写入数据库
        Photo photo = new Photo();
        photo.setName(prefix);

        doUpload(userId, albumId, suffix, uploadPath, uploadFile, photo, null);
    }

    /**
     * 单独上传
     * @param userId
     * @param albumId
     * @param suffix
     * @param uploadPath
     * @param uploadFile
     * @param photo
     * @param cus_tags
     */
    @Override
    public void photoUploadTask(int userId, int albumId, String suffix, String uploadPath, File uploadFile, Photo photo, String[] cus_tags) {
        doUpload(userId, albumId, suffix, uploadPath, uploadFile, photo, cus_tags);
    }
}
