package com.zhitu.controller;

import com.zhitu.entity.Album;
import com.zhitu.exception.NotLogInException;
import com.zhitu.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //@RestController是：@ResponseBody和@Controller两个注解的合体，标注在类上，表示：这个Controller类的所有方法返回的数据直接写给浏览器。
@RequestMapping("/api/album")//请求映射标签
/**
 * 相册控制器，控制,怀疑下面的方法是用来与前端接口的？？？
 */
public class AlbumController {

    @Autowired  //这个注解就是spring可以自动帮你把bean里面引用的对象的setter/getter方法省略，它会自动帮你set/get
    private AlbumService albumService;

    //用来处理请求地址映射
    //@RequestMapping 注解中的 method 元素声明了 HTTP 请求的 HTTP 方法的类型,一个 POST 类型的请求 /home 会交给 post() 方法来处理.
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    //将 HTTP 请求正文插入方法中
    public Map<String,String> create(@RequestBody Map<String,String> map, HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        albumService.create(userId,map.get("name"),map.get("description"));
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Map<String,String> edit(@RequestBody Map<String,Object> map,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        albumService.edit(userId,Integer.parseInt(map.get("albumId").toString()),
                map.get("name").toString(),Integer.parseInt(map.get("photoId").toString()),
                map.get("description").toString());
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,String> delete(@RequestBody Map<String,Object> map,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        albumService.delete(userId,Integer.parseInt(map.get("albumId").toString()));
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/download",method = RequestMethod.POST)
    public void download(@RequestParam int albumId, HttpServletResponse response)
    {
        albumService.download(albumId,response);
    }

    @RequestMapping(value = "/getAlbumPhotos",method = RequestMethod.GET)
    public Map<String, Object> getAlbumPhotos(@RequestParam int albumId,@RequestParam int page, HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        return albumService.getAlbumPhotos(userId,albumId,page);
    }

    @RequestMapping(value = "/getAlbumList")
    public List<Album> getAlbumList(HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());

        List<Album> l = albumService.getAlbumList(userId);

        System.err.print("测试数据"+l);
        return l;
    }

    /*
     * 相册合并功能
     * 从firstAlbum合并到secondAlbum
     * 沿用secondAlbum的名字和描述
     */
    @RequestMapping(value = "/merge")
    public Map<String,String> merge(@RequestParam int firstAlbumId,@RequestParam int secondAlbumId,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        albumService.merge(userId,firstAlbumId,secondAlbumId);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }
}
