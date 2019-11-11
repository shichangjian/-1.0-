package com.zhitu.service.impl;

import com.zhitu.dao.mapper.*;
import com.zhitu.dao.mapper.*;
import com.zhitu.entity.Album;
import com.zhitu.entity.Photo;
import com.zhitu.dao.mapper.*;
import com.zhitu.exception.ForbiddenAccessException;
import com.zhitu.exception.ForbiddenEditException;
import com.zhitu.exception.PageNotExistException;
import com.zhitu.service.AlbumService;
import com.zhitu.tools.PhotoTool;
import com.zhitu.tools.ZipTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 相册服务实现类
 */
@Service  //告诉spring创建一个实现类的实例表示这是一个bean
public class AlbumServiceImpl implements AlbumService {

    /**
     * @Resource装配顺序
     * 　　1. 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常
     * 　　2. 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常
     * 　　3. 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常
     * 　　4. 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；
     *          如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；
     *
     *   @Resource（这个注解属于J2EE的），
     *   默认按照名称进行装配，名称可以通过name属性进行指定，
     *   如果没有指定name属性，当注解写在字段上时，
     *   默认取字段名进行安装名称查找，如果注解写在setter方法上默认取属性名进行装配。
     *   当找不到与名称匹配的bean时才按照类型进行装配。
     *   但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。
     */
    @Resource
    private AlbumMapper albumMapper;

    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private PhotoTagRelationMapper photoTagRelationMapper;

    /**
     * @Autowired默认按类型装配（这个注解是属于spring的），
     * 默认情况下必须要求依赖对象必须存在，如果要允许null值，
     * 可以设置它的required属性为false，
     * 如：@Autowired(required=false) ，
     * 如果我们想使用名称装配可以结合@Qualifier注解进行使用
     */
    @Autowired
    private PhotoTool photoTool;

    @Autowired
    private ZipTool zipTool;

    /**
     * 新建一个相册
     * @param userId
     * @param name
     * @param description
     */
    @Override
    public void create(int userId, String name, String description) {
        Album album = new Album();
        album.setName(name);
        album.setUserId(userId);
        album.setCover(0);
        album.setDescription(description);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        album.setCreateTime(timestamp);
        album.setLastEditTime(timestamp);
        album.setIsDefaultAlbum(0);
        album.setPhotoAmount(0);
        albumMapper.insert(album);
        userMapper.updateAlbumAmountByUserId(userId,1);
    }

    /**
     * 编辑相册功能
     * @param userId
     * @param albumId
     * @param name
     * @param photoId
     * @param description
     */
    @Override
    public void edit(int userId,int albumId, String name, int photoId, String description) {
        //校验user_id和album_id
        if(albumMapper.selectUserIdByAlbumId(albumId) != userId)
            throw new ForbiddenEditException();
        //如果是默认相册，禁止编辑
        if(albumMapper.selectIsDefaultAlbumByAlbumId(albumId) != null)
            throw new ForbiddenEditException();
        if(photoId != 0)
        {
            //相册封面不能选此相册之外的照片
            if(photoMapper.selectAllByPhotoId(photoId).getAlbumId() != albumId)
                throw new ForbiddenAccessException();
            //相册封面不能选在回收站里的照片
            if(photoMapper.selectInRecycleBinByPhotoId(photoId) != null)
                throw new ForbiddenAccessException();
            if(photoId == -1)
            {
                photoId = albumMapper.selectAllByAlbumId(albumId).getCover();
            }
            albumMapper.editAlbumByAlbumId(albumId,name,photoId,description);
        }
        else
            albumMapper.editAlbumByAlbumId(albumId,name,0,description);
        albumMapper.updateLastEditTimeByAlbumId(albumId,new Timestamp(System.currentTimeMillis()));
    }

    /**
     * 删除功能
     * @param userId
     * @param albumId
     */
    @Override
    public void delete(int userId,int albumId) {
        //校验user_id和album_id
        if(albumMapper.selectUserIdByAlbumId(albumId) != userId)
            throw new ForbiddenEditException();
        //如果是默认相册，禁止编辑
        if(albumMapper.selectIsDefaultAlbumByAlbumId(albumId) != null)
            throw new ForbiddenEditException();
        List<Integer> list = photoMapper.selectPhotoIdByAlbumId(albumId);
        int defaultAlbumId = albumMapper.selectDefaultAlbumIdByAlbumId(albumId);
        for(int photoId : list)
        {
            photoMapper.updateAlbumIdByPhotoId(photoId,defaultAlbumId);
        }
        albumMapper.deleteByAlbumId(albumId);
        userMapper.updateAlbumAmountByUserId(userId,-1);
    }

    /**
     * 下载功能
     * @param albumId
     * @param response  //响应
     */
    @Override
    public void download(int albumId, HttpServletResponse response) {
        List<String> fileFullName = new ArrayList<>();
        List<String> filePath = new ArrayList<>();
        List<Photo> photos = photoMapper.selectAllPhotoNotInRecycleBinByAlbumIdOrderByUploadTimeDesc(albumId);
        for(Photo photo : photos)
        {
            if(!fileFullName.contains(photo.getName() + "." + photo.getSuffix()))
                fileFullName.add(photo.getName() + "." + photo.getSuffix());
            else {
                int count = 2;
                while(fileFullName.contains(photo.getName() + "_" + count + "." + photo.getSuffix()))
                    count++;
                fileFullName.add(photo.getName() + "_" + count + "." + photo.getSuffix());
            }
            filePath.add(photo.getPath());
        }
        //创建ZIP文件并返回文件路径
        String zipPath = zipTool.createZip(fileFullName,filePath);
        File file = new File(photoTool.LOCAL_DIR + zipPath);
        response.reset();
        response.setHeader("content-type","application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(albumMapper.selectAllByAlbumId(albumId).getName() + ".zip", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置输入输出流和缓冲区
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            inputStream = new FileInputStream(file.getPath());
            outputStream = response.getOutputStream();
            while((len = inputStream.read(buffer)) > 0)
            {
                outputStream.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取相册照片
     * @param userId
     * @param albumId
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> getAlbumPhotos(int userId, int albumId,int page) {
        //校验user_id和album_id
        if(albumMapper.selectUserIdByAlbumId(albumId) != userId)
            throw new ForbiddenAccessException();
        Map<String,Object> mapReturn = new HashMap<>();
        int photoAmount = albumMapper.selectAllByAlbumId(albumId).getPhotoAmount();
        int pages;
        if(photoAmount % 50 > 0)
            pages = photoAmount / 50 + 1;
        else
            pages = photoAmount / 50;
        mapReturn.put("pages",pages);
        List<Photo> photos;
        if(page != -10086)
        {
            if(page > pages || page <= 0)
                throw new PageNotExistException();
            photos = photoMapper.selectAllPhotoNotInRecycleBinByAlbumIdOrderByUploadTimeDescLimitPage(albumId,(page - 1) * 50);
        }
        else
        {
            photos = photoMapper.selectAllPhotoNotInRecycleBinByAlbumIdOrderByUploadTimeDesc(albumId);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        for(Photo photo : photos)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("photoId",photo.getPhotoId());
            map.put("name",photo.getName());
            map.put("description",photo.getDescription());
            map.put("albumId",photo.getAlbumId());
            map.put("likes",photo.getLikes());
            map.put("isPublic",photo.getIsPublic());
            map.put("size",photo.getSize());
            map.put("width",photo.getWidth());
            map.put("height",photo.getHeight());
            map.put("originalTime",photo.getOriginalTime());
            map.put("uploadTime",photo.getUploadTime());
            List<String> photoTagList = new ArrayList<>();
            List<Integer> photoTagIdList = photoTagRelationMapper.selectTagIdByPhotoId(photo.getPhotoId());
            for(int tagId : photoTagIdList)
            {
                photoTagList.add(tagMapper.selectNameByTagId(tagId));
            }
            map.put("tags",photoTagList);
            listMap.add(map);
        }
        mapReturn.put("photos",listMap);
        return mapReturn;
    }

    /**
     * 获取相册列表
     * @param userId
     * @return
     */
    @Override
    public List<Album> getAlbumList(int userId) {
        return albumMapper.selectAllAlbumByUserId(userId);
    }

    /**
     * 合并两个相册
     * @param userId
     * @param firstAlbumId
     * @param secondAlbumId
     */
    @Override
    public void merge(int userId, int firstAlbumId, int secondAlbumId) {
        Album firstAlbum = albumMapper.selectAllByAlbumId(firstAlbumId);
        Album secondAlbum = albumMapper.selectAllByAlbumId(secondAlbumId);
        if(!(firstAlbum.getUserId() == userId && secondAlbum.getUserId() == userId))
            throw new ForbiddenEditException();
        else if(firstAlbum.getIsDefaultAlbum() == 1)
            throw new ForbiddenEditException();
        else
        {
            List<Integer> photoIds = photoMapper.selectPhotoIdByAlbumId(firstAlbumId);
            for(int photoId : photoIds)
            {
                photoMapper.updateAlbumIdByPhotoId(photoId,secondAlbumId);
            }
            albumMapper.updatePhotoAmountByAlbumId(secondAlbumId,firstAlbum.getPhotoAmount());
            albumMapper.updateLastEditTimeByAlbumId(secondAlbumId,new Timestamp(System.currentTimeMillis()));
            albumMapper.deleteByAlbumId(firstAlbumId);
        }
    }
}
