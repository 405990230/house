package com.mooc.boss.house.biz.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileService {
    List<String> getImgPaths(List<MultipartFile> files);


}
