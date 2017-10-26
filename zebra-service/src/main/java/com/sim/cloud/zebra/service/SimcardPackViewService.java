package com.sim.cloud.zebra.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimcardPackViewMapper;
import com.sim.cloud.zebra.mapper.SysUserMapper;
import com.sim.cloud.zebra.model.FlowPoolVo;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SimcardPackageView;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月18日 下午6:29:27 
* 类说明  页面查询物联网卡服务类
*/
@Service
@Transactional
public class SimcardPackViewService extends AbstractService<SimcardPackViewMapper, SimcardPackageView>{

	
	@Autowired
	private SysUserMapper sysUserMapper;
	/**
	 * 查询自己的流量池
	 * @param uid
	 * @return
	 */
	public  List<FlowPoolVo> selectSelfPoolList(Long uid){
		Map<String,Object> map=new HashMap<>();
		map.put("uid", uid);
		map.put("type", TariffPlan.SHARE);//共享卡
		Map<Integer,List<SimcardPackageView>> mapList=selectByMap(map).stream().
		collect(Collectors.groupingBy(s->s.getFlow()));
		return mapList.entrySet().stream().map(e->{
			FlowPoolVo fpv=new FlowPoolVo();
			fpv.setFlow(e.getKey());
			Map<Integer,List<SimcardPackageView>> mapInner=
					e.getValue().stream().collect(Collectors.groupingBy(SimcardPackageView::getObjType));
			fpv.setUsePool(Float.parseFloat(e.getValue().stream().mapToDouble(m->m.getPackageUsed()).sum()+""));
			fpv.setTotalPool(e.getValue().size()*e.getKey());
			fpv.setActiveNum(mapInner.get(SimCard.ACTIVATED_NAME)==null?0:mapInner.get(SimCard.ACTIVATED_NAME).size());
			fpv.setStockNum(mapInner.get(SimCard.INVENTORY_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
			fpv.setBlockNum(mapInner.get(SimCard.RETIRED_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
			fpv.setLastSyncTime(e.getValue().stream().max((a,b)-> a.getLastSyncTime().compareTo(b.getLastSyncTime())).get().getLastSyncTime());
			  return fpv;
					}).collect(Collectors.toList());
		
	}
	/**
	 * 管理员查询所有的流量池
	 * @return
	 */
	public List<FlowPoolVo> selectAllPoolList(List<Long> userIdList,Integer flowSize){
		Map<String,Object> map=new HashMap<>();
		map.put("type", TariffPlan.SHARE);//共享卡
		map.put("flow", flowSize);
		Map<String,List<SimcardPackageView>> mapList=selectByMap(map).stream().
		filter(f->{
			if(f.getPackageId()==0l){
				return false;
			}
			if(userIdList.size()==0 || (userIdList.size()>0 && userIdList.contains(f.getUid()))){
				return true;
			}else{
				return false;
			}
		}).collect(Collectors.groupingBy(s->s.getFlow()+"-"+s.getUid()));
		
		
		return mapList.entrySet().stream().map(e->{
			Integer flow=Integer.parseInt(e.getKey().split("-")[0]);
			FlowPoolVo fpv=new FlowPoolVo();
			fpv.setFlow(flow);
			SysUser user=sysUserMapper.selectById(Long.parseLong(e.getKey().split("-")[1]));
			fpv.setUserName(user.getUserName());
			fpv.setPhone(user.getPhone());
			Map<Integer,List<SimcardPackageView>> mapInner=
					e.getValue().stream().collect(Collectors.groupingBy(SimcardPackageView::getObjType));
			fpv.setUsePool(Float.parseFloat(e.getValue().stream().mapToDouble(m->m.getPackageUsed()).sum()+""));
			fpv.setTotalPool(e.getValue().size()*flow);
			fpv.setActiveNum(mapInner.get(SimCard.ACTIVATED_NAME)==null?0:mapInner.get(SimCard.ACTIVATED_NAME).size());
			fpv.setStockNum(mapInner.get(SimCard.INVENTORY_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
			fpv.setBlockNum(mapInner.get(SimCard.RETIRED_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
			fpv.setLastSyncTime(e.getValue().stream().max((a,b)-> a.getLastSyncTime().compareTo(b.getLastSyncTime())).get().getLastSyncTime());
		    return fpv;
		}).collect(Collectors.toList());
		
	}
	
	/**
	 * 管理员分页查询流量池
	 * @param params
	 * @return
	 */
	public Page<FlowPoolVo> selectPoolByPage(Map<String, Object> params,List<Long> userIdList){
		 Page<SimcardPackageView> page=getPage(params);
		 Page<FlowPoolVo> pageRes = new Page<FlowPoolVo>(page.getCurrent(), page.getSize());
		 
		 Map<String,Object> map=new HashMap<>();
			map.put("type", TariffPlan.SHARE);
			if(null!=params.get("flow") && params.get("flow").toString().trim().length()>0)
			map.put("flow", params.get("flow"));
			Map<String,List<SimcardPackageView>> mapList=selectByMap(map).stream().
			filter(f->{
				if(f.getPackageId()==0l){
					return false;
				}
				if(userIdList.size()==0 || (userIdList.size()>0 && userIdList.contains(f.getUid()))){
					return true;
				}else{
					return false;
				}
			}).collect(Collectors.groupingBy(s->s.getFlow()+"-"+s.getUid()));
			List<String> keyList=mapList.keySet().stream().collect(Collectors.toList());
			pageRes.setTotal(keyList.size());
			int end=page.getCurrent()*page.getSize()>keyList.size()?keyList.size():page.getCurrent()*page.getSize();
			List<FlowPoolVo> tmpList=keyList.subList((page.getCurrent()-1)*page.getSize(), end).stream().map(m->{
				Integer flow=Integer.parseInt(m.split("-")[0]);
				FlowPoolVo fpv=new FlowPoolVo();
				fpv.setFlow(flow);
				SysUser user=sysUserMapper.selectById(Long.parseLong(m.split("-")[1]));
				fpv.setUserName(user.getUserName());
				fpv.setPhone(user.getPhone());
				Map<Integer,List<SimcardPackageView>> mapInner=
						mapList.get(m).stream().collect(Collectors.groupingBy(SimcardPackageView::getObjType));
				fpv.setUsePool(Float.parseFloat(mapList.get(m).stream().mapToDouble(s1->s1.getPackageUsed()).sum()+""));
				fpv.setTotalPool(mapList.get(m).size()*flow);
				fpv.setActiveNum(mapInner.get(SimCard.ACTIVATED_NAME)==null?0:mapInner.get(SimCard.ACTIVATED_NAME).size());
				fpv.setStockNum(mapInner.get(SimCard.INVENTORY_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
				fpv.setBlockNum(mapInner.get(SimCard.RETIRED_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
				fpv.setLastSyncTime(mapList.get(m).stream().max((a,b)-> a.getLastSyncTime().compareTo(b.getLastSyncTime())).get().getLastSyncTime());
			    return fpv;
			}).collect(Collectors.toList());
			pageRes.setRecords(tmpList);
			return pageRes;
			
	}
}
