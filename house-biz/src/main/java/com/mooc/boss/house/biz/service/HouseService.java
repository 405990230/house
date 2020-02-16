package com.mooc.boss.house.biz.service;

import com.mooc.boss.house.common.constants.HouseUserType;
import com.mooc.boss.house.common.model.*;
import com.mooc.boss.house.common.page.PageData;
import com.mooc.boss.house.common.page.PageParams;

import java.util.List;

public interface HouseService {
    PageData<House> queryHouse(House query, PageParams pageParams);

    List<House> queryAndSetImg(House query, PageParams pageParams);

    List<Community> getAllCommunitys();

    void addHouse(House house, User user);

    HouseUser getHouseUser(Long houseId);

    House queryOneHouse(Long id);

    void addUserMsg(UserMsg userMsg);

    void bindUser2House(Long houseId, Long userId, boolean collect);

    void updateRating(Long id, Double rating);

    void unbindUser2House(Long id, Long userId, HouseUserType type);

}
