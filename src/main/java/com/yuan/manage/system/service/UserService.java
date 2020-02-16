package com.yuan.manage.system.service;

import com.yuan.manage.common.PageParam;
import com.yuan.manage.common.PageResult;
import com.yuan.manage.system.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户表 服务类
 *
 * @author wangfan
 * @since 2019-02-11
 */
public interface UserService extends IService<User> {

    User getByUsername(String username);

    PageResult<User> listUser(PageParam pageParam);

    boolean addUser(User user, List<Integer> roleIds);

    boolean updateUser(User user, List<Integer> roleIds);

}
