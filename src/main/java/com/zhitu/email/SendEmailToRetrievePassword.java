package com.zhitu.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//带此注解的类看为组件，当使用基于注解的配置和类路径扫描的时候，这些类就会被实例化
//这个值可能作为逻辑组件(即类)的名称，在自动扫描的时候转化为spring bean
@Component

/**
 * 发送电子邮件获取密码
 */
public class SendEmailToRetrievePassword {

    private final String FROM = "565064489@qq.com";    //自己的电子邮箱

    private final String DOMAIN = "http://www.newbee.cf:3000";  //打开线上页面的网址

    private final String ROUTE = "/retrievePassword";   //检索密码

    /**
     * autowired有4种模式，byName、byType、constructor、autodectect
     *
     * 其中@Autowired注解是使用byType方式的
     *
     * byType方式是根据属性类型在容器中寻找bean类
     *
     * 规则：
     * 1.Spring先去容器中寻找NewsSevice类型的bean（先不扫描newsService字段）
     *
     * 2.若找不到一个bean，会抛出异常
     *
     * 3.若找到一个NewsSevice类型的bean，自动匹配，并把bean装配到newsService中
     *
     * 4.若NewsService类型的bean有多个，则扫描后面newsService字段进行名字匹配，匹配成功后将bean装配到newsService中
     * （若是其中一类型的bean有多个，还可以指定名称）
     */
    @Autowired   //此处是指定找mailSender这个bean，装配到JavaMailSender
    private JavaMailSender mailSender;

    @Autowired  //此处是指定找templateEngine这个bean，装配到SpringTemplateEngine
    private SpringTemplateEngine templateEngine;

    /**
     * @param to
     * @param name
     * @param sid
     * throws避免异常
     * @throws MessagingException
     */
    public void send(String to,String name,String sid) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);
        helper.setFrom(FROM);
        helper.setTo(to);
        helper.setSubject("找回密码");
        Context context = new Context();
        context.setVariable("link",DOMAIN + ROUTE + "?sid=" + sid);
        context.setVariable("name",name);
        String text = templateEngine.process("retrievePasswordEmail",context);
        helper.setText(text,true);
        mailSender.send(mailMessage);
    }
}
