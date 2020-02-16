package com.mooc.boss.house.web;

import com.mooc.boss.house.biz.service.FileService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FileServiceTest extends HouseWebApplicationTests {

    @Autowired
    FileService fileService;

    @Test
    public void testgGetImgPaths() {
        fileService.getImgPaths(null);
    }
}
