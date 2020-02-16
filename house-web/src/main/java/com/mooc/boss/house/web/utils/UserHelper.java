package com.mooc.boss.house.web.utils;

import com.google.common.base.Objects;
import com.mooc.boss.house.common.model.User;
import com.mooc.boss.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserHelper {

    public static ResultMsg validate(User accout) {
        if (!isEmail(accout.getEmail())) {
            return ResultMsg.errorMsg("Email 格式不正确！");
        }
        if (StringUtils.isBlank(accout.getName())) {
            return ResultMsg.errorMsg("账户名 有误");
        }
        if (StringUtils.isBlank(accout.getConfirmPasswd()) || StringUtils.isBlank(accout.getPasswd()) || !accout.getPasswd().equals(accout.getConfirmPasswd())) {
            return ResultMsg.errorMsg("密码 有误");
        }
        if (accout.getPasswd().length() < 6) {
            return ResultMsg.errorMsg("密码不能小于6位");
        }
        return ResultMsg.successMsg("");
    }

    public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return ResultMsg.errorMsg("参数有误");
        }
        if (!Objects.equal(password, confirmPassword)) {
            return ResultMsg.errorMsg("密码必须与确认密码一致");
        }
        return ResultMsg.successMsg("");
    }


    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
