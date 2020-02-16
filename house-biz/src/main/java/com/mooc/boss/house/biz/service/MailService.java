package com.mooc.boss.house.biz.service;


public interface MailService {

    void sendMail(String title, String url, String email);

    void registerNotify(String email);

    void resetNotify(String email);

    boolean enable(String key);


    /**
     * 发送普通文本邮件
     * 我们这里的普通邮件是指最为普通的纯文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * HTML文件就是指在文件内容中可以添加<html>等标签，收件人收到邮件后显示内容也和网页一样，比较丰富多彩
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     * 带附件的邮件在HTML邮件上添加一些参数即可
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     */
    void sendAttachmentMail(String to, String subject, String content, String filePath);

    /**
     * 发送带图片的邮件
     * 带图片即在正文中使用<img>标签，并设置我们需要发送的图片，也是在HTML基础上添加一些参数
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId   图片ID，用于在<img>标签中使用，从而显示图片
     */
    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
