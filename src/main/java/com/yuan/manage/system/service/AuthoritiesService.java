package com.yuan.manage.system.service;

import com.yuan.manage.system.model.Authorities;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
public interface AuthoritiesService extends IService<Authorities> {

    List<Authorities> listByUserId(Integer userId);

    List<Authorities> listByRoleIds(List<Integer> roleIds);

    List<Authorities> listByRoleId(Integer roleId);
}
