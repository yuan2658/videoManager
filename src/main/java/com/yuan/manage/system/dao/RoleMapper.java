package com.yuan.manage.system.dao;

import com.yuan.manage.system.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> listByUserId(@Param("userId") Integer userId);

    Role getByUserId(@Param("userId") Integer userId);
}
