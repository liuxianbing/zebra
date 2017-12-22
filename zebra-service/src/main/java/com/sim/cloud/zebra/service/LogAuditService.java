package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.LogAuditMapper;
import com.sim.cloud.zebra.model.LogAudit;
import com.sim.cloud.zebra.model.SysUser;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月27日 下午4:21:31 
* 类说明 
*/
@Service
@Transactional
public class LogAuditService extends AbstractService<LogAuditMapper, LogAudit>{

	
	public void insertAudit(SysUser user,String content,String typeStr){
		LogAudit la=new LogAudit();
		la.setUid(user.getId());
		la.setUserName(user.getUserName());
		la.setContent(content);
		la.setCreateTime(DateUtil.getDateTime());
		la.setTypeStr(typeStr);
		insert(la);
	}
}
