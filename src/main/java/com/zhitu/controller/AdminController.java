package com.zhitu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController //@RestController是：@ResponseBody和@Controller两个注解的合体，标注在类上，表示：这个Controller类的所有方法返回的数据直接写给浏览器。
@RequestMapping("/api/admin")//请求映射标签
/**
 * 管理员控制器类
 */
public class AdminController {

    @RequestMapping("/onlineUserCount")//请求映射标签
    /**
     * 联机用户计数方法
     */
    public int onlineUserCount(HttpSession session)
    {
        //应该是用来进行联机用户计数的
        Object onlineUserCountObject = session.getAttribute("onlineUserCount");
        if(onlineUserCountObject == null)
            return 0;
        else
            //不知道是返回什么，返回在线人数？
            return Integer.parseInt(onlineUserCountObject.toString());
    }
}
