package com.sim.cloud.zebra.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sim.cloud.zebra.common.util.CardDeviceStatusEnum;
import com.sim.cloud.zebra.common.util.CartCardEnum;
import com.sim.cloud.zebra.common.util.JackSonUtil;
import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年9月27日 上午10:51:28 
* 类说明   首页控制类
*/
@Controller
@Api(value = "首页管理", description = "首页管理处理模块")
public class MainController  extends AbstractController {
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@ApiOperation(value = "首页信息")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toMainPage(Model model) throws JsonProcessingException {
		if(getCurrUser().getRole()==SysUser.ROLE_SYS){
			return adminHomePage(model);
		}else{
			return commonHomePage(model);
		}
	}
	
	@ApiOperation(value = "请求")
	@RequestMapping(value = "adminStatis", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody Object queryFlow(@RequestParam Integer flow,@RequestParam Integer type){
		if(type==0){
			return simCardServiceView.statisCardsNum(flow);
		}else{
			return simCardServiceView.statisCardsFlow(flow);
		}
	}
	
	private String adminHomePage(Model model) throws JsonProcessingException{
		List<String> shareAccounts= ZebraConfig.getAccounts().entrySet().stream().
				filter(f->f.getValue().trim().endsWith("2")).
				map(m->m.getKey()).collect(Collectors.toList());
		model.addAttribute("accounts",tariffPlanService.selectList(null).stream().
				filter(f-> shareAccounts.contains(f.getAccount())).collect(Collectors.toList()));
	
		long authSize=companyService.selectList(null).stream().
				filter(f->f.getBusinessAuth()==1 && f.getLegalAuth()==1).count();
		model.addAttribute("authSize", authSize);
		model.addAttribute("cardSize", simcardService.statisCardCount(null,null,null));
		model.addAttribute("cardActiveSize", simcardService.statisCardCount(null,null,CardDeviceStatusEnum.ACTIVATED_NAME.getStatus()));
		model.addAttribute("orderSize", orderGoodsService.selectList(null).stream().
				filter(f->f.getType()==CartCardEnum.SUCCESS_ORDER.getStatus()).count());
		model.addAttribute("cardDevice", JackSonUtil.getObjectMapper().writeValueAsString(simcardService.statisDevice(null,null)));
		model.addAttribute("cardNet", JackSonUtil.getObjectMapper().writeValueAsString(simcardService.statisNet(null,null)));
		model.addAttribute("cardType", JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisType()));
		return "home_admin";
	}
	private String commonHomePage(Model model) throws JsonProcessingException{
		Long uid=null;
		Long cid=null;
		if(getCurrUser().getRole()==SysUser.ROLE_MANAGER){
			cid=getCurrUser().getCid();
		}
		if(getCurrUser().getAuth()==0){
			uid=getCurrUser().getId();
		}
		if(getCurrUser().getRole()==SysUser.ROLE_USER){
			uid=getCurrUser().getId();
		}
		model.addAttribute("cardSize", simcardService.statisCardCount(uid,cid,null));
		model.addAttribute("money", financeService.selectBance(getCurrUser().getId()));
		
		
		//common
		model.addAttribute("cardDevice", JackSonUtil.getObjectMapper().writeValueAsString(simcardService.statisDevice(uid,cid)));
		model.addAttribute("cardNet", JackSonUtil.getObjectMapper().writeValueAsString(simcardService.statisNet(uid,cid)));
		model.addAttribute("cardType", JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisCardType(uid,cid)));
		
		model.addAttribute("shareFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisShareFlow(uid,cid)));
		
	//	model.addAttribute("cardFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisCardOfFlow(uid, null)));
		model.addAttribute("sharedCardFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisCardOfFlow(uid,cid, TariffPlan.SHARE)));
		
	//	model.addAttribute("singleFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisSingleCards(uid,cid)));
		return "home_common";
	}
}
