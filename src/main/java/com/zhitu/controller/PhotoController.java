package com.zhitu.controller;

import com.zhitu.exception.NotLogInException;
import com.zhitu.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //@RestController是：@ResponseBody和@Controller两个注解的合体，标注在类上，表示：这个Controller类的所有方法返回的数据直接写给浏览器。
@RequestMapping("/api/photo")//请求映射标签
/**
 * 照片控制器
 */
public class PhotoController {

    @Autowired //自动帮你把bean里面引用的对象的setter/getter方法省略，它会自动帮你set/get
    private PhotoService photoService;

    //用来处理请求地址映射
    //@RequestMapping 注解中的 method 元素声明了 HTTP 请求的 HTTP 方法的类型,一个 POST 类型的请求 /home 会交给 post() 方法来处理.
    /***
     * 上传
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map<String, Object> upload(@RequestParam MultipartFile file, //将请求参数绑定到你控制器的方法参数上
                                      @RequestParam String name,
                                      @RequestParam String description,
                                      @RequestParam int albumId,
                                      @RequestParam int isPublic,
                                      @RequestParam String[] tags,
                                      HttpServletRequest request) throws IOException {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        photoService.upload(userId,file,name,description,albumId,isPublic,tags);
        Map<String,Object> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/uploads",method = RequestMethod.POST)
    public Map<String, Object> uploads(@RequestParam MultipartFile[] files,
                                       @RequestParam int albumId,
                                      HttpServletRequest request) throws IOException {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        return photoService.uploads(userId,albumId,files);
    }

    @RequestMapping(value = "/downloads",method = RequestMethod.POST)
    public void downloads(@RequestBody List<Map<String, Integer>> listMap, HttpServletResponse response)
    {
        List<Integer> photos = new ArrayList<>();
        for(Map<String, Integer> map : listMap)
        {
            photos.add(map.get("photoId"));
        }
        if(photos.size() == 1)
            photoService.download(photos.get(0),response);
        else
        {
            photoService.downloads(photos,response);
        }
    }

    @RequestMapping(value = "/moveToRecycleBin",method = RequestMethod.POST)
    public Map<String,String> moveToRecycleBin(@RequestBody List<Map<String, Integer>> listMap,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        List<Integer> photos = new ArrayList<>();
        for(Map<String, Integer> map : listMap)
        {
            photos.add(map.get("photoId"));
        }
        photoService.moveToRecycleBin(userId,photos);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Map<String,String> edit(@RequestBody Map<String, Object> map,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        photoService.edit(userId,Integer.parseInt(map.get("photoId").toString()),
                map.get("name").toString(),map.get("description").toString(),
                Integer.parseInt(map.get("isPublic").toString()),(ArrayList<String>)map.get("tags"));
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public void show(@RequestParam int photoId,HttpServletRequest request,HttpServletResponse response)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        photoService.show(userIdObject,photoId,response);
    }

    @RequestMapping(value = "/showThumbnail",method = RequestMethod.GET)
    public void showThumbnail(@RequestParam int photoId,HttpServletRequest request,HttpServletResponse response)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        photoService.showThumbnail(userIdObject,photoId,response);
    }

    @RequestMapping(value = "/getRecycleBinPhotos")
    public List<Map<String, Object>> getRecycleBinPhotos (HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        return photoService.getRecycleBinPhotos(userId);
    }

    @RequestMapping(value = "/move",method = RequestMethod.GET)
    public Map<String,String> move(@RequestParam int photoId,@RequestParam int albumId,@RequestParam int tagId,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        photoService.move(userId,photoId,albumId,tagId);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/moveOutRecycleBin",method = RequestMethod.POST)
    public Map<String,String> moveOutRecycleBin(@RequestBody List<Map<String, Integer>> listMap,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        List<Integer> photos = new ArrayList<>();
        for(Map<String, Integer> map : listMap)
        {
            photos.add(map.get("photoId"));
        }
        photoService.moveOutRecycleBin(userId,photos);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/completelyDelete",method = RequestMethod.POST)
    public Map<String,String> completelyDelete(@RequestBody List<Map<String, Integer>> listMap, HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        List<Integer> photos = new ArrayList<>();
        for(Map<String, Integer> map : listMap)
        {
            photos.add(map.get("photoId"));
        }
        photoService.completelyDelete(userId,photos);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    //获取用户所有照片
    @RequestMapping(value = "/getPhotos")
    public Map<String,Object> getPhotos(@RequestParam int page, HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        return photoService.getPhotos(userId,page);
    }
    //用来处理请求地址映射
    //@RequestMapping 注解中的 method 元素声明了 HTTP 请求的 HTTP 方法的类型,一个 POST 类型的请求 /home 会交给 post() 方法来处理.
    @RequestMapping(value = "/globalSearch",method = RequestMethod.GET)
    public Map<String,Object> globalSearch(@RequestParam String keyword,@RequestParam int page, HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        return photoService.globalSearch(userIdObject,keyword,page);
    }

    @RequestMapping(value = "/like")
    public Map<String,String> like(@RequestParam int photoId,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status",photoService.like(userId,photoId));
        return mapReturn;
    }

    @RequestMapping(value = "/personalSearch",method = RequestMethod.GET)
    public Map<String,Object> personalSearch(@RequestParam String keyword,@RequestParam int page, HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        return photoService.personalSearch(userId,keyword,page);
    }

    @RequestMapping(value = "/timeline")
    public List<Map<String,Object>> timeline(HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        return photoService.timeline(userId);
    }

    @RequestMapping(value = "/recommend")
    public List<Map<String,Object>> recommend(HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        return photoService.recommend(userIdObject);
    }
}
