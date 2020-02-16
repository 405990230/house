package com.mooc.boss.house.biz.service.impl;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.mooc.boss.house.biz.mapper.UserMapper;
import com.mooc.boss.house.biz.service.MailService;
import com.mooc.boss.house.common.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("mailService")
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    @Value("${domain.name}")
    private String domainName;


    @Autowired
    private UserMapper userMapper;


    private final Cache<String, String> registerCache =
            CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    String email = notification.getValue();
                    User user = new User();
                    user.setEmail(email);
                    List<User> targetUser = userMapper.selectUsersByQuery(user);
                    if (!targetUser.isEmpty() && Objects.equal(targetUser.get(0).getEnable(), 0)) {
                        // 代码优化: 在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作
                        userMapper.delete(email);
                    }

                }
            }).build();


    private final Cache<String, String> resetCache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES).build();

    @Async
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(title);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }

    /**
     * 1.缓存key-email的关系
     * 2.借助spring mail 发送邮件
     * 3.借助异步框架进行异步操作
     *
     * @param email
     */
    @Async
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey, email);
        String url = domainName + "/accounts/verify?key=" + randomKey;
        sendMail("房产平台激活邮件", url, email);
    }

    /**
     * 发送重置密码邮件
     *
     * @param email
     */
    @Async
    public void resetNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphanumeric(10);
        resetCache.put(randomKey, email);
        String content = domainName + "/accounts/reset?key=" + randomKey;
        sendMail("房产平台密码重置邮件", content, email);
    }

    public String getResetEmail(String key) {
        return resetCache.getIfPresent(key);
    }

    public void invalidateRestKey(String key) {
        resetCache.invalidate(key);
    }

    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userMapper.update(updateUser);
        registerCache.invalidate(key);
        return true;
    }


    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(from);//发信人

        mailSender.send(message);
    }

    public void sendHtmlMail(String to, String subject, String content) {

        log.info("发送HTML邮件开始：{},{},{}", to, subject, content);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
            log.info("发送HTML邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
        }
    }

    public void sendAttachmentMail(String to, String subject, String content, String filePath) {

        log.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, filePath);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
            log.info("发送带附件邮件成功");
        } catch (MessagingException e) {
            log.error("发送带附件邮件失败", e);
        }
    }

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

        log.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            log.info("发送带图片邮件成功");
        } catch (MessagingException e) {
            log.error("发送带图片邮件失败", e);
        }
    }
}
