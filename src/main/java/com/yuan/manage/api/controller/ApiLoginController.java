package com.yuan.manage.api.controller;

import com.yuan.manage.api.base.BaseApiController;
import com.yuan.manage.common.JsonResult;
import com.yuan.manage.common.shiro.EndecryptUtil;
import com.yuan.manage.common.utils.StringUtil;
import com.yuan.manage.system.model.User;
import com.yuan.manage.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;

import javax.servlet.http.HttpServletRequest;

/**
 * api登录相关接口
 * Created by wangfan on 2019-05-29 下午 3:43.
 */
@RequestMapping("/api")
@RestController
public class ApiLoginController extends BaseApiController {
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public JsonResult login(String username, String password) {
        if (StringUtil.isBlank(username, password)) {
            return JsonResult.error("账号或密码不能为空");
        }
        User user = userService.getByUsername(username);
        if (user == null) {
            return JsonResult.error("账号不存在");
        }
        if (user.getState() != 0) {
            return JsonResult.error("账号被锁定");
        }
        if (!EndecryptUtil.encrytMd5(password, 3).equals(user.getPassword())) {
            return JsonResult.error("密码错误");
        }
        // 签发token
        Token token = tokenStore.createNewToken(String.valueOf(user.getUserId()), new String[]{}, new String[]{}, 60 * 60 * 24 * 30);
        user.setPassword(null);
        return JsonResult.ok("登录成功").put("access_token", token.getAccessToken()).put("user", user);
    }

    /**
     * 修改个人信息
     */
    @PostMapping("/updateMyInfo")
    public JsonResult updateMyInfo(User user, HttpServletRequest request) {
        user.setUserId(getLoginUserId(request));
        if (userService.updateById(user)) {
            User newUser = userService.getById(user.getUserId());
            newUser.setPassword(null);
            return JsonResult.ok("修改成功").put("data", newUser);
        }
        return JsonResult.error("修改失败");
    }

}
