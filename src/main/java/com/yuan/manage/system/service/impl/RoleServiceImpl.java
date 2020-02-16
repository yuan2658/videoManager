package com.yuan.manage.system.service.impl;

import com.yuan.manage.system.model.Role;
import com.yuan.manage.system.dao.RoleMapper;
import com.yuan.manage.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> listByUserId(Integer userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public Role getByUserId(Integer userId) {
        return baseMapper.getByUserId(userId);
    }
}
