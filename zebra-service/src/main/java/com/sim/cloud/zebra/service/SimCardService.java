package com.sim.cloud.zebra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.PackageMapper;
import com.sim.cloud.zebra.mapper.SimCardMapper;
import com.sim.cloud.zebra.mapper.SysUserMapper;
import com.sim.cloud.zebra.mapper.TariffPlanMapper;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午1:03:18 
* 类说明 
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
	 * @param iccid
	 * @return
	 */
	public Long selectIdByIccid(String iccid) {
		EntityWrapper<SimCard> wrapper=new EntityWrapper<>();
		wrapper.eq("iccid", iccid);
		List<SimCard> list= selectList(wrapper);
		if(null==list || list.size()==0){
			return null;
		}else{
			return list.get(0).getId();
		}
	}
	/**
	 * 划拨物联卡
	 * @return
	 */
	public List<String> saveCardPlanRel(List<String> iccidsList,List<String> idsList,
			Float externalQuote,Long planId,Long uid,Integer term){
		TariffPlan tp=tariffPlanMapper.selectById(planId);
		SysUser user=sysUserMapper.selectById(uid);
		//保存用户套餐
		com.sim.cloud.zebra.model.Package pack=new com.sim.cloud.zebra.model.Package();
		pack.setPlatQuote(tp.getCost());
		pack.setExternalQuote(externalQuote);
		pack.setFlow(tp.getFlow());
		pack.setAccount(user.getAccount());
		pack.setOperator(tp.getOperator());
		pack.setTerm(term);
		pack.setPlanId(planId);
		pack.setUid(uid);
		pack.setCreateTime(DateUtil.getDateTime());
		Integer packId=packageMapper.insert(pack);
		
		String nowDate=DateUtil.getDate();
		
		List<SimCard> allCardList=new ArrayList<SimCard>();
		//更新物联卡关联的用户和套餐信息
		for(String id:idsList){
			allCardList.add(super.selectById(Long.parseLong(id)));
			SimCard card=new SimCard();
			card.setId(Long.parseLong(id));
			card.setUid(uid);
			card.setCid(user.getCid());
			card.setOpenTime(nowDate);
			card.setPackageId(packId);
			super.updateById(card);
		}
		List<String> errorIccids=new ArrayList<>();
		if(null!=errorIccids){//失败的还原
			allCardList.stream().filter(f->errorIccids.contains(f.getIccid())).forEach(m->{
				super.updateById(m);
			});
		}
		return null;
	}
}
