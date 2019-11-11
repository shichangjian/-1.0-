package com.zhitu.controller;

import com.zhitu.exception.AlreadyLogInException;
import com.zhitu.exception.NotLogInException;
import com.zhitu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController  //@RestController是：@ResponseBody和@Controller两个注解的合体，标注在类上，表示：这个Controller类的所有方法返回的数据直接写给浏览器。
@RequestMapping("/api/user")//请求映射标签
public class UserController {

    @Autowired //自动帮你把bean里面引用的对象的setter/getter方法省略，它会自动帮你set/get
    private UserService userService;

    //用来处理请求地址映射
    //@RequestMapping 注解中的 method 元素声明了 HTTP 请求的 HTTP 方法的类型,一个 POST 类型的请求 /...会交给 post() 方法来处理.
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody Map<String,String> map,HttpServletRequest request)
    {
        if(request.getSession().getAttribute("userId") != null)
            throw new AlreadyLogInException();
        int userId = userService.register(map.get("username"),map.get("password"),map.get("email"));
        request.getSession().setAttribute("userId",userId);
        Map<String,Object> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody Map<String,String> map, HttpServletRequest request, HttpServletResponse response)
    {
        if(request.getSession().getAttribute("userId") != null)
            throw new AlreadyLogInException();
        int userId = userService.login(map.get("username"),map.get("password"));
        HttpSession session = request.getSession();
        session.setAttribute("userId",userId);
        Map<String,Object> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "logout")
    public Map<String, Object> logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("userId");
        Map<String,Object> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Map<String,String> changePassword(@RequestParam String prePassword,@RequestParam String newPassword,HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        userService.changePassword(userId,prePassword,newPassword);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/getInfo")
    public Map<String,Object> getInfo(HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        return userService.getInfo(userId);
    }

    @RequestMapping(value = "/editInfo",method = RequestMethod.POST)
    public Map<String,String> editInfo(@RequestParam(required = false) MultipartFile avatar,
                                       @RequestParam String nickname,
                                       @RequestParam int gender,
                                       @RequestParam String signature,
                                       HttpServletRequest request)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        userService.editInfo(userId,avatar,nickname,gender,signature);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/retrievePasswordByEmail",method = RequestMethod.GET)
    public Map<String,String> retrievePasswordByEmail(@RequestParam String email)
    {
        userService.retrievePasswordByEmail(email);
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/verifySid",method = RequestMethod.GET)
    public Map<String,Object> verifySid(@RequestParam String sid)
    {
        int userId = userService.verifySid(sid);
        Map<String,Object> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        mapReturn.put("userId",userId);
        return mapReturn;
    }

    @RequestMapping(value = "/retrievePassword",method = RequestMethod.POST)
    public Map<String,String> retrievePassword(@RequestParam String sid,@RequestParam int userId,@RequestParam String newPassword,HttpServletRequest request)
    {
        userService.retrievePassword(sid,userId,newPassword);
        request.getSession().removeAttribute("userId");
        Map<String,String> mapReturn = new HashMap<>();
        mapReturn.put("status","ok");
        return mapReturn;
    }

    @RequestMapping(value = "/checkLoginStatus")
    public Map<String,String> checkLoginStatus(HttpServletRequest request)
    {
        Map<String,String> mapReturn = new HashMap<>();
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            mapReturn.put("status","not login");
        else
            mapReturn.put("status","already login");
        return mapReturn;
    }

    @RequestMapping(value = "/showAvatar")
    public void showAvatar(HttpServletRequest request,HttpServletResponse response)
    {
        Object userIdObject = request.getSession().getAttribute("userId");
        if(userIdObject == null)
            throw new NotLogInException();
        int userId = Integer.parseInt(userIdObject.toString());
        userService.showAvatar(userId,response);
    }
}
