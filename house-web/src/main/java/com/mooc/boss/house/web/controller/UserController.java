package com.mooc.boss.house.web.controller;

import com.mooc.boss.house.biz.service.UserService;
import com.mooc.boss.house.common.constants.CommonConstants;
import com.mooc.boss.house.common.model.User;
import com.mooc.boss.house.common.result.ResultMsg;
import com.mooc.boss.house.common.utils.HashUtils;
import com.mooc.boss.house.web.utils.UserHelper;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("user")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * 注册提交:1.注册验证 2 发送邮件 3验证失败重定向到注册页面
     * 注册页获取:根据account对象为依据判断是否注册页获取请求
     *
     * @param account
     * @param modelMap
     * @return
     */

    @RequestMapping("accounts/register")
    public String accountRegister(User account, ModelMap modelMap) {
        if (account == null || account.getName() == null) {
            //modelMap.put("agencyList",  agencyService.getAllAgency());
            return "/user/accounts/register";
        }

        // 用户验证
        ResultMsg resultMsg = UserHelper.validate(account);
        if (resultMsg.isSuccess() && userService.addAccount(account)) {
            modelMap.put("email", account.getEmail());
            return "/user/accounts/registerSubmit";
        } else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期").asUrlParams();
        }
    }


    // ----------------------------登录流程------------------------------------

    /**
     * 登录接口
     */
    @RequestMapping("/accounts/signin")
    public String signin(HttpServletRequest req, ModelMap modelMap,
                         @RequestParam(value = "username", required = false) String username,
                         @RequestParam(value = "password", required = false) String password,
                         @RequestParam(value = "target", required = false) String target) {
        if (username == null || password == null) {
            req.setAttribute("target", target);
            return "/user/accounts/signin";
        }
        User user = userService.auth(username, password);
        if (user == null) {
            return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&"
                    + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        } else {
            HttpSession session = req.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
            return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
        }
    }

    /**
     * 登出操作
     *
     * @param request
     * @return
     */
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }


    // ---------------------个人信息页-------------------------

    /**
     * 1.能够提供页面信息
     * 2.更新用户信息
     *
     * @param updateUser
     * @return
     */
    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest req, User updateUser) {
        //email不可修改，隐藏的put中填充了email
        if (updateUser.getEmail() == null) {
            return "/user/accounts/profile";
        }
        userService.updateUser(updateUser, updateUser.getEmail());
        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> users = userService.getUserByQuery(query);
        req.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }

    /**
     * 修改密码操作
     *
     * @param email
     * @param password
     * @param newPassword
     * @param confirmPassword
     * @param mode
     * @return
     */
    @RequestMapping("accounts/changePassword")
    public String changePassword(String email, String password, String newPassword,
                                 String confirmPassword, ModelMap mode) {
        User user = userService.auth(email, password);
        if (user == null || !confirmPassword.equals(newPassword) || newPassword.length() < 6) {
            return "redirct:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User updateUser = new User();
        updateUser.setPasswd(HashUtils.encryPassword(newPassword));
        userService.updateUser(updateUser, email);
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }
}
