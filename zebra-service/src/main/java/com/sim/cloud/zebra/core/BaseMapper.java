package com.sim.cloud.zebra.core;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * 
 * @author liuxianbing
 * 
 * @version 2017年6月3日 下午2:30:14
 * 
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {

	List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

	List<Long> selectIdPage(Pagination rowBounds, @Param("cm") Map<String, Object> params);

	List<T> selectMutiTablePage(Pagination pagination, Map<String, Object> params);

}
