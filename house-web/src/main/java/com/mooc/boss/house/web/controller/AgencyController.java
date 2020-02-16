package com.mooc.boss.house.web.controller;

import com.mooc.boss.house.biz.service.AgencyService;
import com.mooc.boss.house.biz.service.HouseService;
import com.mooc.boss.house.common.model.House;
import com.mooc.boss.house.common.model.User;
import com.mooc.boss.house.common.page.PageData;
import com.mooc.boss.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AgencyController {
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private HouseService houseService;

    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
        if (pageSize == null) {
            pageSize = 4;
        }
        PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
//        List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
//        modelMap.put("recomHouses", houses);
        modelMap.put("ps", ps);
        return "/user/agent/agentList";
    }

    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id, ModelMap modelMap) {
        User user = agencyService.getAgentDeail(id);
        //List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        House query = new House();
        query.setUserId(id);
        query.setBookmarked(false);
        PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3, 1));
        if (bindHouse != null) {
            modelMap.put("bindHouses", bindHouse.getList());
        }
        //modelMap.put("recomHouses", houses);
        modelMap.put("agent", user);
        modelMap.put("agencyName", user.getAgencyName());
        return "/user/agent/agentDetail";
    }

}
