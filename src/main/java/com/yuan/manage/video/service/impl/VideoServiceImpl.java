package com.yuan.manage.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yuan.manage.common.PageParam;
import com.yuan.manage.common.PageResult;
import com.yuan.manage.video.model.Video;
import com.yuan.manage.video.dao.VideoMapper;
import com.yuan.manage.video.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangfan
 * @since 2020-02-16
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    VideoMapper videoMapper;

    @Override
    public PageResult<Video> listAll(PageParam pageParam){
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        Object name = pageParam.getPageData().get("name");
        Object type = pageParam.getPageData().get("type");
        if(name != null){
            queryWrapper.like("name",name);
        }
        if(type!=null){
            queryWrapper.eq("type",type);
        }
        IPage page = page(pageParam, queryWrapper);
        return new PageResult<Video> (page.getRecords(),pageParam.getTotal() );
    }



}
