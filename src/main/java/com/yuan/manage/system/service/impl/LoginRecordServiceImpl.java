package com.yuan.manage.system.service.impl;

import com.yuan.manage.common.PageParam;
import com.yuan.manage.common.PageResult;
import com.yuan.manage.system.model.LoginRecord;
import com.yuan.manage.system.dao.LoginRecordMapper;
import com.yuan.manage.system.service.LoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
@Service
public class LoginRecordServiceImpl extends ServiceImpl<LoginRecordMapper, LoginRecord> implements LoginRecordService {

    @Override
    public PageResult<LoginRecord> listFull(PageParam pageParam) {
        List<LoginRecord> records = baseMapper.listFull(pageParam);
        return new PageResult<>(records, pageParam.getTotal());
    }

    @Override
    public List<LoginRecord> listAll(Map pageData) {
        return baseMapper.listAll(pageData);
    }
}
