package com.yuan.manage.system.service;

import com.yuan.manage.common.PageParam;
import com.yuan.manage.common.PageResult;
import com.yuan.manage.system.model.LoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
public interface LoginRecordService extends IService<LoginRecord> {

    // 分页查询
    PageResult<LoginRecord> listFull(PageParam page);

    // 不分页
    List<LoginRecord> listAll(Map pageData);

}
