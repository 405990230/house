package com.mooc.boss.house.biz.service;

import com.mooc.boss.house.common.model.House;

import java.util.List;

public interface RecommendService {
    void increase(Long id);

    List<House> getHotHouse(Integer size);

    List<House> getLastest();
}
