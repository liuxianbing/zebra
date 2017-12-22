package com.sim.cloud.zebra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.common.util.CardDeviceStatusEnum;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.JasperUtils;
import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.CartCardMapper;
import com.sim.cloud.zebra.mapper.PackageMapper;
import com.sim.cloud.zebra.mapper.SimCardMapper;
import com.sim.cloud.zebra.mapper.SysUserMapper;
import com.sim.cloud.zebra.mapper.TariffPlanMapper;
import com.sim.cloud.zebra.model.CartCard;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;
import com.simclouds.unicom.jasper.JasperClient;

/**
 * @author liuxianbing:
 * @version 创建时间：2017年10月14日 下午1:03:18 类说明
 */
@Service
@Transactional
public class SimCardService extends AbstractService<SimCardMapper, SimCard> {

	@Autowired
	private PackageMapper packageMapper;
	@Autowired
	private TariffPlanMapper tariffPlanMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private CartCardMapper cartCardMapper;

	/**
	 * 根据iccid查询记录的Bean
	 * 
	 * @param iccid
	 * @return
	 */
	public Long selectIdByIccid(String iccid) {
		EntityWrapper<SimCard> wrapper = new EntityWrapper<>();
		wrapper.eq("iccid", iccid);
		List<SimCard> list = selectList(wrapper);
		if (null == list || list.size() == 0) {
			return null;
		} else {
			return list.get(0).getId();
		}
	}
	
	/**
	 * 查询当天 过期的卡片列表
	 * @param day
	 * @return
	 */
	public List<SimCard> selectNearlyExpireCards(String day){
		EntityWrapper<SimCard> wrapper = new EntityWrapper<>();
		wrapper.eq("expire_time", day);
		return selectList(wrapper);
	}
	
	/**
	 * 卡片总数统计
	 * @param uid
	 * @return
	 */
	public int statisCardCount(Long uid,Long cid,Integer objType){
		EntityWrapper<SimCard> wrapper=new EntityWrapper<>();
		if(null!=uid){
			wrapper.eq("uid", uid);
		}
		if(null!=cid){
			wrapper.eq("cid", cid);
		}
		if(null!=objType){
			wrapper.eq("obj_type", objType);
		}
		return selectCount(wrapper);
	}
	
	/**
	 * 设备状态统计
	 * @param uid
	 * @return
	 */
	public List<Map<String,Object>> statisDevice(Long uid,Long cid){
		EntityWrapper<SimCard> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect("cast(obj_type as signed) as name ","count(1) as value");
		if(null!=uid){
			wrapper.eq("uid", uid);
		}
		if(null!=cid){
			wrapper.eq("cid", cid);
		}
		wrapper.groupBy("obj_type");
		List<Map<String,Object>> list= selectMaps(wrapper);
		list.stream().forEach(e->{
			e.put("name", CardDeviceStatusEnum.getEnumByStatus((Integer)e.get("name")).getSimStatus());
		});
		return list;
	}
	/**
	 * 网络状态统计
	 * @param uid
	 * @return
	 */
	public List<Map<String,Object>> statisNet(Long uid,Long cid){
		EntityWrapper<SimCard> wrapper=new EntityWrapper<>();
		wrapper.setSqlSelect(" (case when net_type=1 then '开启' else '关闭' end) as name ","count(1) as value");
		if(null!=uid){
			wrapper.eq("uid", uid);
		}
		if(null!=cid){
			wrapper.eq("cid", cid);
		}
		wrapper.groupBy("net_type");
		return selectMaps(wrapper);
	}
	
	

//	/**
//	 * 根据流量池名称查询Bean ID
//	 * 
//	 * @param name
//	 * @return
//	 */
//	private Long selectIdByPoolName(String name) {
//		EntityWrapper<FlowPool> wrapper = new EntityWrapper<>();
//		wrapper.eq("pool_name", name);
//		List<FlowPool> list = flowPoolMapper.selectList(wrapper);
//		if (null == list || list.size() == 0) {
//			return null;
//		} else {
//			return list.get(0).getId();
//		}
//	}

//	/**
//	 * 根据sim卡ID查询Bean ID
//	 * 
//	 * @param id
//	 * @return
//	 */
//	private Long selectIdByCardId(Long id) {
//		EntityWrapper<SimPoolRelation> wrapper = new EntityWrapper<>();
//		wrapper.eq("sim_card_id", id);
//		List<SimPoolRelation> list = simPoolRelMapper.selectList(wrapper);
//		if (null == list || list.size() == 0) {
//			return null;
//		} else {
//			return list.get(0).getId();
//		}
//	}
	
//	private SimPoolRelation selectRelByCardId(Long id) {
//		EntityWrapper<SimPoolRelation> wrapper = new EntityWrapper<>();
//		wrapper.eq("sim_card_id", id);
//		List<SimPoolRelation> list = simPoolRelMapper.selectList(wrapper);
//		if (null == list || list.size() == 0) {
//			return new SimPoolRelation();
//		} else {
//			return list.get(0);
//		}
//	}
	
	/**
	 * 卡片分配和重置
	 * @param idsList
	 * @param uid
	 */
	public void allocCardToUsers(List<String> idsList,Long uid){
		for (String id : idsList) {
			SimCard card = new SimCard();
			card.setId(Long.parseLong(id));
			card.setUid(uid);
			if(uid==0l){//如果是重置操作 重置 cid和订单数量还原
				card.setCid(0l);
				if(null!=card.getCartCardId() && card.getCartCardId()>0){
					CartCard cc=cartCardMapper.selectById(card.getCartCardId());
					int allocNum=cc.getAllocNum();
					if(allocNum-1>=0){
						CartCard tmp=new CartCard();
						tmp.setId(cc.getId());
						tmp.setAllocNum(allocNum-1);
						cartCardMapper.updateById(tmp);
					}
				}
			}
			super.updateById(card);
		}
	}
	
	/**
	 * 延期卡片
	 * @param idsList
	 * @param term
	 */
	public void delayCards(List<String> idsList,int term){
	//	String endDay=DateUtil.addMonth(DateUtil.getDate().substring(0,7)+"-01", term);
		List<SimCard> tmpUpdate=new ArrayList<>();
		for (String id : idsList) {
			SimCard card = new SimCard();
			card.setId(Long.parseLong(id));
			String expireTime=selectById(card.getId()).getExpireTime();
			expireTime=DateUtil.addMonth(expireTime.substring(0,7)+"-01", term)+expireTime.substring(8,10);
			card.setExpireTime(expireTime);
			tmpUpdate.add(card);
		}
		super.updateBatchById(tmpUpdate);  
	}

	/**
	 * 划拨物联卡
	 * @return
	 */
	public List<String> saveCardPlanRel(List<String> iccidsList, List<String> idsList, Long cartCardId,
			Long uid) {
		CartCard cc=cartCardMapper.selectById(cartCardId);
		if((cc.getNum()-cc.getAllocNum())<idsList.size()){
			throw new RuntimeException("订单卡片剩余分配数量"+(cc.getNum()-cc.getAllocNum())+"个,不足"+idsList.size());
		}
	    Package pack=	packageMapper.selectById(cc.getPackageId());
	    String planName=tariffPlanMapper.selectById(pack.getPlanId()).getName();
		SysUser user = sysUserMapper.selectById(uid);
		// 保存用户套餐
		String nowDate = DateUtil.getDate();
		String endDay=DateUtil.addMonth(nowDate.substring(0,7)+"-01", cc.getTerm());
		List<SimCard> allCardList = new ArrayList<SimCard>();
		List<SimCard> tmpUpdate=new ArrayList<>();
		// 更新物联卡关联的用户和套餐信息
		for (String id : idsList) {
			allCardList.add(super.selectById(Long.parseLong(id)));
			SimCard card = new SimCard();
			card.setId(Long.parseLong(id));
			card.setUid(uid);
			card.setCid(user.getCid());
			card.setOpenTime(nowDate);
			card.setExpireTime(endDay);
			card.setPackageId(cc.getPackageId());
			card.setCartCardId(cc.getId());
			tmpUpdate.add(card);
		}
		super.updateBatchById(tmpUpdate);

		// 逻辑删除历史套餐
//		allCardList.stream().filter(f -> f.getPackageId() != null && f.getPackageId() > 0).
//		map(m -> m.getPackageId())
//				.collect(Collectors.toSet()).stream().forEach(p -> {
//					com.sim.cloud.zebra.model.Package tmpPack = new com.sim.cloud.zebra.model.Package();
//					tmpPack.setId(new Long(p.intValue()));
//					tmpPack.setStatus(0);// 删除
//					//packageMapper.updateById(tmpPack);
//				});

		List<String> errorIccids = new ArrayList<>();
		
		// 更新jasper
		String accountInfo = ZebraConfig.getAccounts().get(pack.getAccount());
		JasperClient jasperClient = JasperClient.getInstance(accountInfo);
		errorIccids.addAll(jasperClient.changeRatePlan(iccidsList, planName));
		
		try {
			if ( errorIccids.size() > 0) {// 失败的卡信息还原
				allCardList.stream().filter(f -> errorIccids.contains(f.getIccid())).forEach(m -> {
					super.updateById(m);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//更新订单分配的卡片数量
		CartCard tmpCard=new CartCard();
		tmpCard.setId(cc.getId());
		tmpCard.setAllocNum(cc.getAllocNum()+(idsList.size()-errorIccids.size()));
		cartCardMapper.updateById(tmpCard);
		return errorIccids;
	}

//	/**
//	 * 保存流量池 卡 关系
//	 * 
//	 * @param flow
//	 * @param idsList
//	 * @param errorIccids
//	 * @param uid
//	 * @param cid
//	 */
//	private void savePool(int flow, List<String> idsList, Long uid, Long cid) {
//		String name = "中国联通-" + flow + "MB";
//		Long id = selectIdByPoolName(name);
//		FlowPool fp = new FlowPool();
//		fp.setId(id);
//		fp.setPoolName(name);
//		fp.setUid(uid);
//		fp.setCid(cid);
//		if (null != id) {
//			fp.setTotalPool(flowPoolMapper.selectById(id).getTotalPool() + idsList.size() * flow);
//			flowPoolMapper.updateById(fp);
//		} else {
//			fp.setTotalPool(idsList.size() * flow);
//			fp.setCreateTime(DateUtil.getDateTime());
//			flowPoolMapper.insert(fp);
//		}
//		// 添加卡片--流量池 依赖
//		idsList.stream().map(m -> Long.parseLong(m)).forEach(e -> {
//			Long relId = selectIdByCardId(e);
//			SimPoolRelation spr = new SimPoolRelation();
//			spr.setId(relId);
//			spr.setCid(cid);
//			spr.setUid(uid);
//			spr.setFlowPoolId(fp.getId());
//			spr.setSimCardId(e);
//			if (null == relId) {
//				simPoolRelMapper.insert(spr);
//			} else {
//				simPoolRelMapper.updateById(spr);
//			}
//		});
//	}
//
//	private void resetPool(int flow, List<String> idsList, List<Long> errorCards, List<SimPoolRelation> relList) {
//		String name = "中国联通-" + flow + "MB";
//		Long id = selectIdByPoolName(name);
//		FlowPool fp = new FlowPool();
//		fp.setId(id);
//		fp.setPoolName(name);
//		if (null != id) {
//			fp.setTotalPool(flowPoolMapper.selectById(id).getTotalPool() + (idsList.size() - errorCards.size()) * flow);
//			flowPoolMapper.updateById(fp);
//		}
//		// 还原卡片--流量池 依赖
//		relList.stream().filter(f -> errorCards.contains(f.getSimCardId())).forEach(e -> {
//			if (null == e.getId()) {
//				simPoolRelMapper.deleteById(selectIdByCardId(e.getSimCardId()));
//			} else {
//				simPoolRelMapper.updateById(e);
//			}
//		});
//	}
	
	
	/**
	 * 打开网络
	 * @param iccids
	 * @param status
	 * @return
	 */
	public List<String> openCardNet(List<Long> ids,Long uid){
		return changeCardStatus(ids,SimCard.NET_OPEN,SimCard.ACTIVATED_NAME,uid);
	}
	
	/**
	 * 关闭网络
	 * @param iccids
	 * @param status
	 * @return
	 */
	public List<String> closeCardNet(List<Long> ids,Long uid){
		return changeCardStatus(ids,SimCard.NET_CLOSE,SimCard.RETIRED_NAME,uid);
	}
	
	/**
	 * 打开网络
	 * @param iccids
	 * @param status
	 * @return
	 */
	private List<String> changeCardStatus(List<Long> ids,int netType,int deviceType,Long uid){
		List<SimCard> list=new ArrayList<>();
		String account=null;
		for(Long e:ids){
			SimCard obj=selectById(e);
			if(null!=uid && obj.getUid()!=uid){
				throw new RuntimeException("无权限操作!");
			}
			SimCard tmp=new SimCard();
			tmp.setId(obj.getId());
			tmp.setIccid(obj.getIccid());
			tmp.setNetType(obj.getNetType());
			tmp.setObjType(obj.getObjType());
			list.add(tmp);
			
			obj.setNetType(netType);
			obj.setObjType(deviceType);
			super.updateById(obj);
			account=obj.getAccount();
		}
		String accountInfo = ZebraConfig.getAccounts().get(account);
		JasperClient jasperClient = JasperClient.getInstance(accountInfo);
		List<String> failList=jasperClient.changeStatus(list.stream().map(m->m.getIccid()).collect(Collectors.toList()), 
				JasperUtils.getStatusStringMap().get("STATUS_"+deviceType));
		if(failList.size()>0){//回退
			list.stream().filter(f->failList.contains(f.getIccid())).forEach(e->{
				super.updateById(e);
			});
		}
		return failList;
	}
	
	public List<SimCard> selectByAccount(String account){
		EntityWrapper<SimCard> wrapper=new EntityWrapper<>();
		wrapper.eq("account", account);
		return selectList(wrapper);
	}
}
