package com.sim.cloud.zebra.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.sim.cloud.zebra.core.BaseMapper;
import com.sim.cloud.zebra.model.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {

	List<SysUser> selectUsers(SysUser users);

	List<SysUser> selectMutiTablePage(Pagination pagination, EntityWrapper<SysUser> wrapper);
}
