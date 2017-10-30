package com.sim.cloud.zebra.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sim.cloud.zebra.common.util.JackSonUtil;
import com.sim.cloud.zebra.model.TariffPlan;
import com.sim.cloud.zebra.service.CompanyService;
import com.sim.cloud.zebra.service.SimCardService;
import com.sim.cloud.zebra.service.SimcardPackViewService;
import com.sim.cloud.zebra.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年9月27日 上午10:51:28 
* 类说明   首页控制类
*/
@Controller
@Api(value = "首页")
public class MainController  extends AbstractController {
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private SimcardPackViewService simCardServiceView;
	
	@Autowired
	private SimCardService simcardService;
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@ApiOperation(value = "首页信息")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toMainPage(Model model) throws JsonProcessingException {
		Long uid=null;
		if(!checkIfManager()){
			uid=getCurrUser().getId();
		}
		
		int customerSize=sysUserService.selectCustomers().size();
		long authSize=companyService.selectList(null).stream().
		filter(f->f.getBusinessAuth()==1 && f.getLegalAuth()==1).count();
		model.addAttribute("customerSize", customerSize);
		model.addAttribute("authSize", authSize);
		model.addAttribute("cardSize", simcardService.statisCardCount(uid));
		model.addAttribute("orderSize", 100);
		model.addAttribute("money", 1000.98);
		
		
		//common
		model.addAttribute("cardDevice", JackSonUtil.getObjectMapper().writeValueAsString(simcardService.statisDevice(uid)));
		model.addAttribute("cardNet", JackSonUtil.getObjectMapper().writeValueAsString(simcardService.statisNet(uid)));
		model.addAttribute("cardType", JackSonUtil.getObjectMapper().writeValueAsString(simcardService.statisType(uid)));
		
		//model.addAttribute("allFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisFlow(uid, null)));
		model.addAttribute("shareFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisShareFlow(uid)));
		
	//	model.addAttribute("cardFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisCardOfFlow(uid, null)));
		model.addAttribute("sharedCardFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisCardOfFlow(uid, TariffPlan.SHARE)));
		
		model.addAttribute("singleFlow",JackSonUtil.getObjectMapper().writeValueAsString(simCardServiceView.statisSingleCards(uid)));
		return "home";
	}
}
