package com.sim.cloud.zebra.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sim.cloud.zebra.common.util.JackSonUtil;
import com.sim.cloud.zebra.common.util.JasperUtils;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimcardPackViewMapper;
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
	private SysUserService sysUserService;
	
	
	/**
	 * 查询某个客户的流量池的卡片列表
	 * @param uid
	 * @param flow
	 * @param iccid
	 * @return
	 */
	public List<SimcardPackageView> selectFlowCards(Long uid,Integer flow,final String iccid){
		Map<String,Object> map=new HashMap<>();
		map.put("uid", uid);
		map.put("flow", flow);
		map.put("type", TariffPlan.SHARE);//共享卡
		List<SimcardPackageView> list=selectByMap(map);
		if(org.apache.commons.lang3.StringUtils.isNotBlank(iccid)){
			return list.stream().filter(f->f.getIccid().contains(iccid)).collect(Collectors.toList());
		}
		return list;
	}
	
	/**
	 * 流量池详细
	 * @param uid
	 * @param flow
	 * @return
	 */
	public SimcardPackageView statisFlowPool(String phone,Integer flow){
		SysUser user=sysUserService.selectByPhone(phone);
		Long uid=user.getId();
		Map<String,Object> map=new HashMap<>();
		map.put("uid", uid);
		map.put("flow", flow);
		map.put("type", TariffPlan.SHARE);//共享卡
		List<SimcardPackageView> list=selectByMap(map);
		Map<Integer,List<SimcardPackageView>> cards=list.stream().collect(Collectors.groupingBy(SimcardPackageView::getNetType));
		SimcardPackageView cardFlow=new SimcardPackageView();
		cardFlow.setUid(uid);
		cardFlow.setPackageUsed((float) list.stream().mapToDouble(m->m.getPackageUsed()).sum());
		cardFlow.setPackageLeft( (float) list.stream().mapToDouble(m->m.getPackageLeft()).sum());
		cardFlow.setFlow(list.stream().mapToInt(m->m.getFlow()).sum());
		cardFlow.setLastSyncTime(list.stream().max((a,b)-> a.getLastSyncTime().compareTo(b.getLastSyncTime())).get().getLastSyncTime());
		cardFlow.setPhone(user.getPhone()+"-"+user.getUserName());
		Map<String,Integer> mapCard=new HashMap<>();
		cards.entrySet().stream().forEach(e->{
			mapCard.put(JasperUtils.getStatusStringMap().get("STATUS_"+e), e.getValue().size());
		});
		try {
			cardFlow.setRemark(JackSonUtil.getObjectMapper().writeValueAsString(mapCard));
		} catch (JsonProcessingException e1) {
		}
		cardFlow.setKeyword(""+list.size());//卡片总量
		return cardFlow;    
	}
	
	
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
			SysUser user=sysUserService.selectById(Long.parseLong(e.getKey().split("-")[1]));
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
				SysUser user=sysUserService.selectById(Long.parseLong(m.split("-")[1]));
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
	
	
	public List<Map<String,Object>> statisSingleCards(Long uid){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("iccid","flow","used_flow as usedFlow");
		if(null!=uid){
			wrapper.ge("uid", uid);
		}
		wrapper.gt("flow", 0);
		wrapper.eq("type", 0);//单卡
		List<SimcardPackageView> list=selectList(wrapper);
		Map<Integer,List<SimcardPackageView>>  maps=list.stream().collect(Collectors.groupingBy(p->(int)(p.getUsedFlow()*100/p.getFlow())));
		
		return maps.entrySet().stream().map(m->{
			Map<String,Object> map=new HashMap<>();
			String key=m.getKey()*10+"-"+(m.getKey()*10+9)+"%";
			map.put("name", key);
			map.put("value", m.getValue().size());
			return map;
		}).collect(Collectors.toList());
	}
	
	
	public List<Map<String,Object>> statisCardOfFlow(Long uid,Integer type){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("cast(flow as signed) as name ","count(1) as value");
		if(null!=uid){
			wrapper.ge("uid", uid);
		}
		if(null!=type){
			wrapper.ge("type", type);
		}
		wrapper.gt("flow", 0);
		wrapper.groupBy("flow");
		return selectMaps(wrapper);
	}
	
	
	
	
	
	
	/**
	 * 流量统计
	 * @param uid
	 * @param type
	 * @return
	 */
	public Map<String,Object> statisShareFlow(Long uid){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("cast(flow as signed) as name ",
				"sum(package_used) as used","sum(package_left) as left1");
		if(null!=uid){
			wrapper.ge("uid", uid);
		}
		wrapper.gt("flow", 0).eq("type", TariffPlan.SHARE).groupBy("flow").orderBy("flow");
		List<Map<String,Object>> list=selectMaps(wrapper);
		
		List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
		List<String> xName = new LinkedList<String>();
		 List<Object> data = new LinkedList<Object>();
		list.stream().forEach(e->{
			Map<String, Object>  dataVal = new HashMap<String, Object>();
			      dataVal.put("name",e.get("name").toString());
			      dataVal.put("value", e.get("used"));
			      xName.add(e.get("name").toString());
			      data.add(dataVal);
		});
		
		Map<String, Object>  dataVal = new HashMap<String, Object>();
	    dataVal.put("data", data);
	    dataVal.put("name", "已使用");
	    dataVal.put("type", "bar");
	    dataVal.put("stack", "stack1");
	    series.add(dataVal);
	    
	    List<Object> data2 = new LinkedList<Object>();
		list.stream().forEach(e->{
			Map<String, Object>  dataVal2 = new HashMap<String, Object>();
			dataVal2.put("name",e.get("name").toString());
			dataVal2.put("value", e.get("left1"));
			data2.add(dataVal2);
		});
		
		Map<String, Object>  dataVal2 = new HashMap<String, Object>();
		dataVal2.put("data", data2);
		dataVal2.put("name", "剩余流量");
		dataVal2.put("type", "bar");
		dataVal2.put("stack", "stack2");
	    series.add(dataVal2);
	    
	    Map<String,Object> res=new HashMap<>();
	    res.put("xName", xName);
	    res.put("series", series);
		return res;
	}
	
	
	
	
}
