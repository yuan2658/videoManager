package com.yuan.manage.system.dao;

import com.yuan.manage.common.PageParam;
import com.yuan.manage.system.model.LoginRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangfan
 * @since 2019-02-11
 */
public interface LoginRecordMapper extends BaseMapper<LoginRecord> {

    // 分页查询
    List<LoginRecord> listFull(@Param("page") PageParam page);

    // 不分页
    List<LoginRecord> listAll(@Param("pageData") Map pageData);

}
