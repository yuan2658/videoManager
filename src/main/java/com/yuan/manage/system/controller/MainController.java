package com.yuan.manage.system.controller;

import com.google.common.collect.Lists;
import com.wf.captcha.utils.CaptchaUtil;
import com.yuan.manage.common.BaseController;
import com.yuan.manage.common.JsonResult;
import com.yuan.manage.common.shiro.EndecryptUtil;
import com.yuan.manage.common.utils.StringUtil;
import com.yuan.manage.common.utils.UserAgentGetter;
import com.yuan.manage.system.model.Authorities;
import com.yuan.manage.system.model.LoginRecord;
import com.yuan.manage.system.model.User;
import com.yuan.manage.system.service.AuthoritiesService;
import com.yuan.manage.system.service.LoginRecordService;
import com.yuan.manage.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MainController
 */
@Controller
public class MainController extends BaseController implements ErrorController {
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private UserService userService;

    /**
     * 主页
     */
    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        if (getLoginUser() == null) {
            return "redirect:login";
        }
        List<Authorities> authorities = authoritiesService.listByUserId(getLoginUserId());
        List<Map<String, Object>> menuTree = getMenuTree(authorities, -1);
        model.addAttribute("menus", menuTree);  // 左侧菜单
        model.addAttribute("loginUser", getLoginUser());  // 登录用户信息
        return "index.html";
    }

    /**
     * 登录页
     */
    @GetMapping("/login")
    public String login() {
        if (getLoginUser() != null) {
            return "redirect:index";
        }
        return "login.html";
    }

    /**
     * 登录页
     */
    @GetMapping("/reg")
    public String reg() {
        return "reg.html";
    }

    /**
     * 注册
     * @return
     */
    @PostMapping("reg")
    @ResponseBody
    public JsonResult reg(String username, String password,String code, HttpServletRequest request){
        if (StringUtil.isBlank(username, password)) {
            return JsonResult.error("账号或密码不能为空");
        }
        if (!CaptchaUtil.ver(code, request)) {
            // CaptchaUtil.clear(request);
            return JsonResult.error("验证码不正确");
        }
        User user = new User();
        user.setPassword(EndecryptUtil.encrytMd5(password, 3));
        user.setUsername(username);
        user.setNickName(username);
        user.setState(0);
        List<Integer> stringList = Lists.newArrayList();
        stringList.add(3);
        userService.addUser(user,stringList);
        return JsonResult.ok("200");
    }

    /**
     * 登录
     */
    @ResponseBody
    @PostMapping("/login")
    public JsonResult doLogin(String username, String password, String code, HttpServletRequest request) {
        if("test".equals(username)){
            return JsonResult.error("系统升级，原账号已失效请自行注册");
        }
        if (StringUtil.isBlank(username, password)) {
            return JsonResult.error("账号或密码不能为空");
        }
        if (!CaptchaUtil.ver(code, request)) {
            // CaptchaUtil.clear(request);
            return JsonResult.error("验证码不正确");
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            addLoginRecord(getLoginUserId(), request);
            return JsonResult.ok("登录成功");
        } catch (IncorrectCredentialsException ice) {
            return JsonResult.error("密码错误");
        } catch (UnknownAccountException uae) {
            return JsonResult.error("账号不存在");
        } catch (LockedAccountException e) {
            return JsonResult.error("账号被锁定");
        } catch (ExcessiveAttemptsException eae) {
            return JsonResult.error("操作频繁，请稍后再试");
        }
    }

    /**
     * 控制台
     */
    @RequestMapping("/console")
    public String console() {
        return "system/console.html";
    }

    /**
     * 图形验证码，用assets开头可以排除shiro拦截
     */
    @RequestMapping("/assets/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaptchaUtil.out(5, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 错误页
     */
    @RequestMapping("/error")
    public String error(String code) {
        if ("403".equals(code)) {
            return "error/403.html";
        } else if ("500".equals(code)) {
            return "error/500.html";
        }
        return "error/404.html";
    }

    @Override
    public String getErrorPath() {
        return "/error?code=404";
    }

    /**
     * 递归转化树形菜单
     */
    private List<Map<String, Object>> getMenuTree(List<Authorities> authorities, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i++) {
            Authorities temp = authorities.get(i);
            if (temp.getIsMenu() == 0 && parentId.intValue() == temp.getParentId().intValue()) {
                Map<String, Object> map = new HashMap<>();
                map.put("menuName", temp.getAuthorityName());
                map.put("menuIcon", temp.getMenuIcon());
                map.put("menuUrl", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : (temp.getMenuUrl()));
                map.put("subMenus", getMenuTree(authorities, authorities.get(i).getAuthorityId()));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 添加登录日志
     */
    private void addLoginRecord(Integer userId, HttpServletRequest request) {
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setOsName(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setIpAddress(agentGetter.getIpAddr());
        loginRecordService.save(loginRecord);
    }

    /**
     * 消息弹窗页
     */
    @RequestMapping("/tpl/message")
    public String message() {
        return "system/tpl-message.html";
    }

    /**
     * 修改密码弹窗页
     */
    @RequestMapping("/tpl/password")
    public String password() {
        return "system/tpl-password.html";
    }

    /**
     * 主题设置弹窗页
     */
    @RequestMapping("/tpl/theme")
    public String theme() {
        return "system/tpl-theme.html";
    }

    /**
     * 便签设置弹窗页
     */
    @RequestMapping("/tpl/note")
    public String note() {
        return "system/tpl-note.html";
    }
}
