package com.mooc.boss.house.biz.service.impl;

import com.google.common.collect.Lists;
import com.mooc.boss.house.biz.service.CityService;
import com.mooc.boss.house.common.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cityService")
public class CityServiceImpl implements CityService {

    public List<City> getAllCitys() {
        City city = new City();
        city.setCityCode("110000");
        city.setCityName("北京");
        city.setId(1);
        City city2 = new City();
        city2.setCityCode("110001");
        city2.setCityName("上海");
        city2.setId(2);
        City city3 = new City();
        city3.setCityCode("110002");
        city3.setCityName("武汉");
        city3.setId(2);
        List<City> list = Lists.newArrayList(city, city2, city3);
        return list;
    }

}
