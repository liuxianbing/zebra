package com.sim.cloud.zebra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.PackageMapper;
import com.sim.cloud.zebra.mapper.SimCardMapper;
import com.sim.cloud.zebra.mapper.SysUserMapper;
import com.sim.cloud.zebra.mapper.TariffPlanMapper;
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
	 * 划拨物联卡
	 * @return
	 */
	public List<String> saveCardPlanRel(List<String> iccidsList, List<String> idsList, Float externalQuote, Long planId,
			Long uid, Integer term, String remark) {
		TariffPlan tp = tariffPlanMapper.selectById(planId);
		SysUser user = sysUserMapper.selectById(uid);
		// 保存用户套餐
		com.sim.cloud.zebra.model.Package pack = new com.sim.cloud.zebra.model.Package();
		pack.setPlatQuote(tp.getCost());
		pack.setExternalQuote(externalQuote);
		pack.setFlow(tp.getFlow());
		pack.setAccount(tp.getAccount());
		pack.setOperator(tp.getOperator());
		pack.setTerm(term);
		pack.setPlanId(planId);
		pack.setUid(uid);
		pack.setRemark(remark);
		pack.setCreateTime(DateUtil.getDateTime());
		packageMapper.insert(pack);

		String nowDate = DateUtil.getDate();
		

		List<SimCard> allCardList = new ArrayList<SimCard>();
		// 更新物联卡关联的用户和套餐信息
		for (String id : idsList) {
			allCardList.add(super.selectById(Long.parseLong(id)));
			SimCard card = new SimCard();
			card.setId(Long.parseLong(id));
			card.setUid(uid);
			card.setCid(user.getCid());
			card.setOpenTime(nowDate);
			card.setPackageId(pack.getId());
			super.updateById(card);
		}

		// 逻辑删除历史套餐
		allCardList.stream().filter(f -> f.getPackageId() != null && f.getPackageId() > 0).map(m -> m.getPackageId())
				.collect(Collectors.toSet()).stream().forEach(p -> {
					com.sim.cloud.zebra.model.Package tmpPack = new com.sim.cloud.zebra.model.Package();
					tmpPack.setId(new Long(p.intValue()));
					tmpPack.setStatus(1);// 删除
					packageMapper.updateById(tmpPack);
				});

		List<String> errorIccids = new ArrayList<>();
		
		// 更新jasper
		String accountInfo = ZebraConfig.getAccounts().get(tp.getAccount());
		JasperClient jasperClient = JasperClient.getInstance(accountInfo);
		errorIccids.addAll(jasperClient.changeRatePlan(iccidsList, tp.getName()));
		
		try {
			if ( errorIccids.size() > 0) {// 失败的卡信息还原
				allCardList.stream().filter(f -> errorIccids.contains(f.getIccid())).forEach(m -> {
					super.updateById(m);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
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
}
