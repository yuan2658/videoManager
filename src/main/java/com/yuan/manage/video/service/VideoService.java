package com.yuan.manage.video.service;

import com.yuan.manage.common.PageParam;
import com.yuan.manage.common.PageResult;
import com.yuan.manage.video.model.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangfan
 * @since 2020-02-16
 */
public interface VideoService extends IService<Video> {


    PageResult<Video> listAll(PageParam pageParam);

}
