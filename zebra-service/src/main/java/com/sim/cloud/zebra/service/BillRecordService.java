package com.sim.cloud.zebra.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.BillRecordMapper;
import com.sim.cloud.zebra.model.BillRecord;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月5日 下午12:46:28 
* 类说明 
*/
@Service
@Transactional
public class BillRecordService  extends AbstractService< BillRecordMapper,  BillRecord> {

	
	public List<BillRecord> selectByBillId(Long id){
		EntityWrapper<BillRecord> wrapper=new EntityWrapper<>();
		wrapper.eq("bill_id", id);
		return selectList(wrapper);
	}
}
