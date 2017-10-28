package com.sim.cloud.zebra.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.StatisCardFlowMapper;
import com.sim.cloud.zebra.model.StatisCardFlow;


/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月26日 下午4:16:27 
* 类说明 
*/
@Service
@Transactional
public class StatisCardFlowService extends AbstractService<StatisCardFlowMapper, StatisCardFlow> {

	
	/**
	 * 统计每个卡的流量使用情况
	 * @param begin
	 * @param end
	 * @param iccid
	 * @return
	 */
	public List<StatisCardFlow> selectDateRangeFlow(String begin,String end,String iccid ){
		EntityWrapper<StatisCardFlow> wrapper=new EntityWrapper<>();
		wrapper.ge("day", begin);
		wrapper.le("day", end);
		wrapper.eq("iccid", iccid);
		Map<String,StatisCardFlow> maps= selectList(wrapper).stream().collect(Collectors.toMap(StatisCardFlow::getDay,
				c->c));
		List<String> dayList=DateUtil.displayAllDayDate(begin, end);
		return dayList.stream().map(e->{
			if(maps.get(e)==null){
				StatisCardFlow scf= new StatisCardFlow();
				scf.setDay(e);
				scf.setFlow(0.0f);
				return scf;
			}
			return maps.get(e);
		}).collect(Collectors.toList());
	}
}
