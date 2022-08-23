package com.xkcoding.email.service;

import cn.hutool.core.io.resource.ResourceUtil;
import com.xkcoding.email.EmailApplication;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import java.net.URL;

/**
 * <p>
 * 邮件测试
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-21 13:49
 */
@SpringBootTest(classes = EmailApplication.class)
class MailServiceTest {
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
     * 测试HTML邮件（使用 classpath://templates/ 下的模板）
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    void sendHtmlMail1() throws MessagingException {
        Context varContext = new Context();
        varContext.setVariable("project", "Spring Boot Demo");
        varContext.setVariable("author", "Yangkai.Shen");
        varContext.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("welcome", varContext);
        mailService.sendHtmlMail("237497819@qq.com", "这是一封模板HTML邮件(templates目录)", emailTemplate);
    }

    /**
     * 测试HTML邮件，自定义模板目录
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    void sendHtmlMail2() throws MessagingException {

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(context);
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/email/");
        templateResolver.setSuffix(".html");

        templateEngine.setTemplateResolver(templateResolver);

        Context varContext = new Context();
        varContext.setVariable("project", "Spring Boot Demo");
        varContext.setVariable("author", "Yangkai.Shen");
        varContext.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("test", varContext);
        mailService.sendHtmlMail("237497819@qq.com", "这是一封模板HTML邮件(自定义目录)", emailTemplate);
    }

    /**
     * 测试附件邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    void sendAttachmentsMail() throws MessagingException {
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendAttachmentsMail("237497819@qq.com", "这是一封带附件的邮件", "邮件中有附件，请注意查收！", resource.getPath());
    }

    /**
     * 测试静态资源邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    void sendResourceMail() throws MessagingException {
        String rscId = "xkcoding";
        String content = "<html><body>这是带静态资源的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendResourceMail("237497819@qq.com", "这是一封带静态资源的邮件", content, resource.getPath(), rscId);
    }
}
