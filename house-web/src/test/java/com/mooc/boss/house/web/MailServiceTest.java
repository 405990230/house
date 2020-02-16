package com.mooc.boss.house.web;

import com.mooc.boss.house.biz.service.MailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MailServiceTest extends HouseWebApplicationTests {

    @Autowired
    private MailService mailService;

    /**
     * 发送简单纯文本邮件
     */
    @Test
    public void sendMail() {
        mailService.sendMail("发送邮件测试", "sendMail 大家好，这是我用springboot进行发送邮件测试", "405990230@qq.com");
    }

    /**
     * 发送简单纯文本邮件
     */
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("405990230@qq.com", "发送邮件测试", "sendSimpleMail 大家好，这是我用springboot进行发送邮件测试");
    }

    /**
     * 发送HTML邮件
     */
    @Test
    public void sendHtmlMail() {
        String content = "<html><body><h3><font color=\"red\">" + "sendHtmlMail 大家好，这是springboot发送的HTML邮件" + "</font></h3></body></html>";
        mailService.sendHtmlMail("405990230@qq.com", "发送邮件测试", content);
    }

    /**
     * 发送带附件的邮件
     */
    @Test
    public void sendAttachmentMail() {
        String content = "<html><body><h3><font color=\"red\">" + "sendAttachmentMail 大家好，这是springboot发送的HTML邮件，有附件哦" + "</font></h3></body></html>";
        String filePath = "/Users/qxr4383/Documents/work/myself/code/infos-SpringBoot/house/images/1573007483/333.png";
        mailService.sendAttachmentMail("405990230@qq.com", "发送邮件测试", content, filePath);
    }

    /**
     * 发送带图片的邮件
     */
    @Test
    public void sendInlineResourceMail() {
        String rscPath = "/Users/qxr4383/Documents/work/myself/code/infos-SpringBoot/house/images/1573007483/333.png";
        String rscId = "001";
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有图片哦" + "</font></h3>"
                + "<img src=\'cid:" + rscId + "\'></body></html>";
        mailService.sendInlineResourceMail("405990230@qq.com", "发送邮件测试", content, rscPath, rscId);
    }
}
