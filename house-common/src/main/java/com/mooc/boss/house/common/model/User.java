package com.mooc.boss.house.common.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class User {

    private Long id;

    private String email;

    private String phone;

    private String name;

    private String passwd;

    private String confirmPasswd;

    private Integer type;//普通用户1，经纪人2

    private Date createTime;

    private Integer enable;

    private String avatar;

    private MultipartFile avatarFile;

    private String newPassword;

    private String key;

    private Long agencyId;

    private String aboutme;

    private String agencyName;

}
