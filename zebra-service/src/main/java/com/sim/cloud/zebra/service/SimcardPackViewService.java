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
import com.mysql.fabric.xmlrpc.base.Array;
import com.sim.cloud.zebra.common.util.CardDeviceStatusEnum;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.JackSonUtil;
import com.sim.cloud.zebra.common.util.JasperUtils;
import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimcardPackViewMapper;
import com.sim.cloud.zebra.model.FlowPoolVo;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SimcardPackageView;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;
import com.simclouds.unicom.jasper.JasperClient;

import javafx.beans.property.SimpleListProperty;

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
	@Autowired
	private CompanyService companyService;
	
	
	public List<Map<String,Object>> statisCardsNum(Integer flow){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("cast(real_flow as signed) as name ","count(1) as value");
			wrapper.eq("type", 2);//流量卡
			wrapper.eq("obj_type", CardDeviceStatusEnum.ACTIVATED_NAME.getStatus());//已经激活
			wrapper.eq("real_flow", flow);
			wrapper.like("last_sync_time", DateUtil.getDate().substring(0,7));//当月
			
		return selectMaps(wrapper);
	}
	
	public Map<String,Object> statisCardsFlow(Integer flow){
		
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("cast(real_flow as signed) as name ","sum(used_flow) as used,sum(real_flow) as total");
		wrapper.eq("type", 2);//流量卡
		wrapper.eq("obj_type", CardDeviceStatusEnum.ACTIVATED_NAME.getStatus());//已经激活
		wrapper.eq("real_flow", flow);
		wrapper.like("last_sync_time", DateUtil.getDate().substring(0,7));//当月
		
		List<Map<String,Object>> list=selectMaps(wrapper);
		
		List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
		List<String> xName = new LinkedList<String>();
		 List<Object> data = new LinkedList<Object>();
		list.stream().forEach(e->{
			Map<String, Object>  dataVal = new HashMap<String, Object>();
			      dataVal.put("name",e.get("name").toString()+"MB");
			      dataVal.put("value", e.get("used"));
			      xName.add(e.get("name").toString()+"MB");
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
			dataVal2.put("name",e.get("name").toString()+"MB");
			dataVal2.put("value", e.get("total"));
			data2.add(dataVal2);
		});
		
		Map<String, Object>  dataVal2 = new HashMap<String, Object>();
		dataVal2.put("data", data2);
		dataVal2.put("name", "总流量");
		dataVal2.put("type", "bar");
		dataVal2.put("stack", "stack2");
	    series.add(dataVal2);
	    
	    Map<String,Object> res=new HashMap<>();
	    res.put("xName", xName);
	    res.put("series", series);
		return res;
		
	}
	
	
	/**
	 * 查询某个客户的流量池的卡片列表
	 * @param uid
	 * @param flow
	 * @param iccid
	 * @return
	 */
	public List<SimcardPackageView> selectFlowCards(Long uid,Long cid,Integer flow,final String iccid){
		Map<String,Object> map=new HashMap<>();
		if(null!=uid){
			map.put("uid", uid);
		}
		if(null!=cid){
			map.put("cid", cid);
		}
		map.put("flow", flow);
		map.put("card_type", TariffPlan.SHARE);//共享卡
		List<SimcardPackageView> list=selectByMap(map);
		if(org.apache.commons.lang3.StringUtils.isNotBlank(iccid)){
			return list.stream().filter(f->f.getIccid().contains(iccid)).collect(Collectors.toList());
		}
		return list;
	}
	
	/**
	 * 查询用户下套餐的卡片列表
	 * @param uid
	 * @param packId
	 * @return
	 */
	public List<SimcardPackageView> selectUserPackCards(Long uid,Long packId){
		Map<String,Object> map=new HashMap<>();
		map.put("uid", uid);
		map.put("package_id", packId);
		return selectByMap(map);
	}
	/**
	 * 查询指定客户、指定套餐的卡片列表
	 * @param cid
	 * @param packId
	 * @return
	 */
	public List<SimcardPackageView> selectCompanyPackCards(Long cid,Long packId){
		Map<String,Object> map=new HashMap<>();
		map.put("cid", cid);
		map.put("package_id", packId);
		return selectByMap(map);
	}
	
	/**
	 * 流量池详细
	 * @param uid
	 * @param flow
	 * @return
	 */
	public SimcardPackageView statisFlowPool(Long uid,Long cid,Integer flow){
		Map<String,Object> map=new HashMap<>();
		if(uid!=null){
			map.put("uid", uid);
		}
		if(cid!=null){
			map.put("cid", cid);
		}
		map.put("flow", flow);
		map.put("card_type", TariffPlan.SHARE);//共享卡
		List<SimcardPackageView> list=selectByMap(map);
		Map<Integer,List<SimcardPackageView>> cards=list.stream().collect(Collectors.groupingBy(SimcardPackageView::getNetType));
		SimcardPackageView cardFlow=new SimcardPackageView();
		cardFlow.setUid(uid);
		cardFlow.setCid(cid);
		
		String companyName="";
		if(cid!=null){
			companyName=companyService.selectById(cid).getName();
		}
		if(null!=uid){
			companyName=companyService.selectById(sysUserService.selectById(uid).getCid()).getName();
		}
		
		cardFlow.setPackageUsed((float) list.stream().mapToDouble(m->m.getPackageUsed()).sum());
		cardFlow.setPackageLeft( (float) list.stream().mapToDouble(m->m.getPackageLeft()).sum());
		cardFlow.setFlow(list.stream().mapToInt(m->m.getFlow()).sum());
		cardFlow.setLastSyncTime(list.stream().max((a,b)-> a.getLastSyncTime().compareTo(b.getLastSyncTime())).get().getLastSyncTime());
		cardFlow.setPhone(companyName);
		Map<String,Integer> mapCard=new HashMap<>();
		cards.entrySet().stream().forEach(e->{
			mapCard.put(CardDeviceStatusEnum.getEnumByStatus(e.getKey()).getSimStatus(), e.getValue().size());
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
	public  List<FlowPoolVo> selectSelfPoolList(Long uid,Long cid){
		Map<String,Object> map=new HashMap<>();
		if(uid!=null){
			map.put("uid", uid);
		}
		if(cid!=null){
			map.put("cid", cid);
		}
		map.put("card_type", TariffPlan.SHARE);//共享卡
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
			fpv.setBlockNum(mapInner.get(SimCard.RETIRED_NAME)==null?0:mapInner.get(SimCard.RETIRED_NAME).size());
			fpv.setTestNum(mapInner.get(SimCard.TEST_READY_NAME)==null?0:mapInner.get(SimCard.TEST_READY_NAME).size());
			fpv.setAllNum(e.getValue().size());
			fpv.setLastSyncTime(e.getValue().stream().max((a,b)-> 
			a.getLastSyncTime().compareTo(b.getLastSyncTime())).get().
					getLastSyncTime());
			  return fpv;
					}).collect(Collectors.toList());
		
	}
	/**
	 * 管理员查询所有的流量池
	 * @return
	 */
	public List<FlowPoolVo> selectAllPoolList(List<Long> cidList,Integer flowSize){
		Map<String,Object> map=new HashMap<>();
		map.put("card_type", TariffPlan.SHARE);//共享卡
		map.put("flow", flowSize);
		Map<String,List<SimcardPackageView>> mapList=selectByMap(map).stream().
		filter(f->{
			if(f.getPackageId()==0l){
				return false;
			}
			if(f.getCid()==null || f.getCid()==0l){
			return false;	
			}
			if(cidList.size()==0 || (cidList.size()>0 && cidList.contains(f.getCid()))){
				return true;
			}else{
				return false;
			}
		}).collect(Collectors.groupingBy(s->s.getFlow()+"-"+s.getCid()));
		
		
		return mapList.entrySet().stream().filter(f->f.getKey()!=null && f.getKey().split("-")!=null).map(e->{
				Integer flow=Integer.parseInt(e.getKey().split("-")[0]);
				FlowPoolVo fpv=new FlowPoolVo();
				fpv.setFlow(flow);
				SysUser user=sysUserService.selectManager(Long.parseLong(e.getKey().split("-")[1]));
				fpv.setCompanyName(companyService.selectById(user.getCid()).getName());
				fpv.setUserName(user.getUserName());
				fpv.setCid(user.getCid());
				Map<Integer,List<SimcardPackageView>> mapInner=
						e.getValue().stream().collect(Collectors.groupingBy(SimcardPackageView::getObjType));
				fpv.setUsePool(Float.parseFloat(e.getValue().stream().mapToDouble(m->m.getPackageUsed()).sum()+""));
				fpv.setTotalPool(e.getValue().size()*flow);
				fpv.setAllNum(e.getValue().size());
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
	public Page<FlowPoolVo> selectPoolByPage(Map<String, Object> params,List<Long> cidList){
		 Page<SimcardPackageView> page=getPage(params);
		 Page<FlowPoolVo> pageRes = new Page<FlowPoolVo>(page.getCurrent(), page.getSize());
		 
		 Map<String,Object> map=new HashMap<>();
			map.put("card_type", TariffPlan.SHARE);
			if(null!=params.get("flow") && params.get("flow").toString().trim().length()>0)
			map.put("flow", params.get("flow"));
			Map<String,List<SimcardPackageView>> mapList=selectByMap(map).stream().
			filter(f->{
				if(f.getPackageId()==0l){
					return false;
				}
				if(cidList.size()==0 || (cidList.size()>0 && cidList.contains(f.getUid()))){
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
				SysUser user=sysUserService.selectManager(Long.parseLong(m.split("-")[1]));
				fpv.setUserName(user.getUserName());
				fpv.setCid(user.getCid());
				Map<Integer,List<SimcardPackageView>> mapInner=
						mapList.get(m).stream().collect(Collectors.groupingBy(SimcardPackageView::getObjType));
				fpv.setUsePool(Float.parseFloat(mapList.get(m).stream().mapToDouble(s1->s1.getPackageUsed()).sum()+""));
				fpv.setTotalPool(mapList.get(m).size()*flow);
				fpv.setAllNum(mapList.get(m).size());
				fpv.setActiveNum(mapInner.get(SimCard.ACTIVATED_NAME)==null?0:mapInner.get(SimCard.ACTIVATED_NAME).size());
				fpv.setStockNum(mapInner.get(SimCard.INVENTORY_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
				fpv.setBlockNum(mapInner.get(SimCard.RETIRED_NAME)==null?0:mapInner.get(SimCard.INVENTORY_NAME).size());
				fpv.setLastSyncTime(mapList.get(m).stream().max((a,b)-> a.getLastSyncTime().compareTo(b.getLastSyncTime())).get().getLastSyncTime());
			    return fpv;
			}).collect(Collectors.toList());
			pageRes.setRecords(tmpList);
			return pageRes;
			
	}
	
	
	public List<Map<String,Object>> statisSingleCards(Long uid,Long cid){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("iccid","flow","used_flow as usedFlow");
		if(null!=uid){
			wrapper.eq("uid", uid);
		}
		if(null!=cid){
			wrapper.eq("cid", cid);
		}
		wrapper.gt("flow", 0);
		wrapper.eq("card_type", 0);//单卡
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
	
	/**
	 * 卡片类型统计
	 * @param uid
	 * @return
	 */
	public List<Map<String,Object>> statisCardType(Long uid,Long cid){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect(" (case when card_type=1 then '流量池卡' else '单卡'  end) as name ","count(1) as value");
		if(null!=uid){
			wrapper.eq("uid", uid);
		}
		if(null!=cid){
			wrapper.eq("cid", cid);
		}
		wrapper.ge("card_type", 0);
		wrapper.groupBy("card_type");
		return selectMaps(wrapper);
	}
	
	/**
	 * 卡片类型统计
	 * @param uid
	 * @return
	 */
	public List<Map<String,Object>> statisType(){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect(" (case when type=2 then '流量池卡' else '单卡'  end) as name ","count(1) as value");
		wrapper.ge("type", 0);
		wrapper.groupBy(" (case when type=2 then '流量池卡' else '单卡'  end) ");
		return selectMaps(wrapper);
	}
	
	/**
	 * 卡片数量统计
	 * @param uid
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> statisCardOfFlow(Long uid,Long cid,Integer type){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("CONCAT(flow,'MB') as name ","count(1) as value");
		if(null!=uid){
			wrapper.eq("uid", uid);
		}
		if(null!=cid){
			wrapper.eq("cid", cid);
		}
		if(null!=type){
			wrapper.eq("card_type", type);
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
	public Map<String,Object> statisShareFlow(Long uid,Long cid){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("cast(flow as signed) as name ",
				"sum(package_used) as used","sum(flow) as total");
		if(null!=uid){
			wrapper.eq("uid", uid);
		}
		if(null!=cid){
			wrapper.eq("cid", cid);
		}
		wrapper.gt("flow", 0).eq("card_type", TariffPlan.SHARE).groupBy("flow").orderBy("flow");
		List<Map<String,Object>> list=selectMaps(wrapper);
		
		List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
		List<String> xName = new LinkedList<String>();
		 List<Object> data = new LinkedList<Object>();
		list.stream().forEach(e->{
			Map<String, Object>  dataVal = new HashMap<String, Object>();
			      dataVal.put("name",e.get("name").toString()+"MB");
			      dataVal.put("value", e.get("used"));
			      xName.add(e.get("name").toString()+"MB");
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
			dataVal2.put("name",e.get("name").toString()+"MB");
			dataVal2.put("value", e.get("total"));
			data2.add(dataVal2);
		});
		
		Map<String, Object>  dataVal2 = new HashMap<String, Object>();
		dataVal2.put("data", data2);
		dataVal2.put("name", "总流量");
		dataVal2.put("type", "bar");
		dataVal2.put("stack", "stack2");
	    series.add(dataVal2);
	    
	    Map<String,Object> res=new HashMap<>();
	    res.put("xName", xName);
	    res.put("series", series);
		return res;
	}
	
	public List<SimcardPackageView> selectByOrderCarts(List<Long> cartIds){
		EntityWrapper<SimcardPackageView> wrapper=new EntityWrapper<>();
		wrapper.in("cart_card_id", cartIds);
		return selectList(wrapper);
	}
	
	
	
	
	
}
