package com.xkcoding.email.service;

import java.net.URL;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import com.xkcoding.email.SpringBootDemoEmailApplicationTests;

import cn.hutool.core.io.resource.ResourceUtil;

/**
 * <p>
 * 邮件测试
 * </p>
 *
 * @package: com.xkcoding.email.service
 * @description: 邮件测试
 * @author: yangkai.shen
 * @date: Created in 2018/11/21 13:49
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class MailServiceTest extends SpringBootDemoEmailApplicationTests {
    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ApplicationContext context;

    /**
     * 测试简单邮件
     */
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("237497819@qq.com", "这是一封简单邮件", "这是一封普通的SpringBoot测试邮件");
    }

    /**
     * 测试HTML邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    public void sendHtmlMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("project", "Spring Boot Demo");
        context.setVariable("author", "Yangkai.Shen");
        context.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("welcome", context);
        mailService.sendHtmlMail("237497819@qq.com", "这是一封模板HTML邮件", emailTemplate);
    }

    /**
     * 测试HTML邮件，自定义模板目录
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    public void sendHtmlMail2() throws MessagingException {

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(context);
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/email/");
        templateResolver.setSuffix(".html");

        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("project", "Spring Boot Demo");
        context.setVariable("author", "Yangkai.Shen");
        context.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("test", context);
        mailService.sendHtmlMail("237497819@qq.com", "这是一封模板HTML邮件", emailTemplate);
    }

    /**
     * 测试附件邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    public void sendAttachmentsMail() throws MessagingException {
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendAttachmentsMail("237497819@qq.com", "这是一封带附件的邮件", "邮件中有附件，请注意查收！", resource.getPath());
    }

    /**
     * 测试静态资源邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    public void sendResourceMail() throws MessagingException {
        String rscId = "xkcoding";
        String content = "<html><body>这是带静态资源的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendResourceMail("237497819@qq.com", "这是一封带静态资源的邮件", content, resource.getPath(), rscId);
    }
}