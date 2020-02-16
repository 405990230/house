package com.mooc.boss.house.biz.service;

import com.mooc.boss.house.common.model.Agency;
import com.mooc.boss.house.common.model.User;
import com.mooc.boss.house.common.page.PageData;
import com.mooc.boss.house.common.page.PageParams;

public interface AgencyService {
    User getAgentDeail(Long userId);

    PageData<User> getAllAgent(PageParams pageParams);

    Agency getAgency(Integer id);
}
