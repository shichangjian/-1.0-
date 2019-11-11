package com.zhitu.httpSessionListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/*
* 线上用户统计类
*
* */
@WebListener
public class OnlineUserStatistics implements HttpSessionListener {

    private int onlineUserCount = 0;  //在线用户数初始为0

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        synchronized(this) {
            onlineUserCount++;   //线上用户+1
            se.getSession().setAttribute("onlineUserCount", onlineUserCount);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        synchronized(this) {
            onlineUserCount--;  //线上用户*1
            se.getSession().setAttribute("onlineUserCount", onlineUserCount);
        }
    }

}
