package com.mooc.boss.house.web;

import com.mooc.boss.house.biz.mapper.HouseMapper;
import com.mooc.boss.house.biz.service.HouseService;
import com.mooc.boss.house.common.model.House;
import com.mooc.boss.house.common.page.PageParams;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HouseServiceTest extends HouseWebApplicationTests {

    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private HouseService houseService;

    @Test
    public void queryHouse() {
        House query = new House();
        PageParams pageParams = new PageParams();
        Long count = houseMapper.selectPageCount(query);
        List<House> houses = houseService.queryAndSetImg(query, pageParams);
        System.out.println("-----------" + count + "------" + houses.size());
    }
}
