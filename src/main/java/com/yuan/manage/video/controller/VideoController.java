package com.yuan.manage.video.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yuan.manage.common.PageParam;
import com.yuan.manage.common.PageResult;
import com.yuan.manage.system.model.Role;
import com.yuan.manage.system.model.User;
import com.yuan.manage.video.model.Video;
import com.yuan.manage.video.service.VideoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangfan
 * @since 2020-02-16
 */
@Controller
@RequestMapping("/video")
public class VideoController {


    @Autowired
    VideoService videoService;



    // @RequiresPermissions("video:view")
    @RequestMapping("/list")
    public String video() {
        return "video/list.html";
    }

    /**
     * 查询用户列表
     */
    // @RequiresPermissions("video:view")
    @ResponseBody
    @RequestMapping("/find")
    public PageResult<Video> list(HttpServletRequest request) {
        return videoService.listAll(new PageParam(request));
    }


    @RequestMapping("/detail")
    public String detail(String id,Model model) {
        Video video = videoService.getById(id);
        model.addAttribute("video",video);
        return "video/video.html";
    }







}
