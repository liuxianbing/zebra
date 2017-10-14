package com.sim.cloud.zebra.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.common.util.Constants;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;
import com.sim.cloud.zebra.service.TariffPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 上午11:39:21 
* 类说明 
*/
@Controller
@Api(value = "资费计划控制")
@RequestMapping(value = "/tariffplan")
public class TariffPlanController extends AbstractController {
	@Autowired
	private TariffPlanService tariffPlanService;
	
	@ApiOperation(value = "资费计划")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		return "tariff/plan_list";
	}
	
	@ApiOperation(value = "添加资费计划")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model,@RequestParam(required=false) String id) {
		TariffPlan user=new TariffPlan();
		if(StringUtils.isNotBlank(id)){
			user=tariffPlanService.queryById(Long.parseLong(id));
		}
		model.addAttribute("card", user);
		model.addAttribute("carries", Constants.CARRIER);
		model.addAttribute("accounts", ZebraConfig.getAccounts().keySet());
		return "tariff/plan_add";
	}
	
	@ApiOperation(value = "删除资费计划")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public @ResponseBody Map<String,String>  del(Model model,@RequestParam(required=true) String id) {
		tariffPlanService.delete(Long.parseLong(id));
		return SUCCESS;
	}
	
	@ApiOperation(value = "添加资费计划")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String,String>  addOrUpdate(Model model,@RequestBody TariffPlan user) {
		if(user.getId()==null || user.getId()==0l){
			user.setCreateTime(DateUtil.getDateTime());
		}
		tariffPlanService.insertOrUpdate(user);
		return SUCCESS;
	}
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "资费计划列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<TariffPlan> list() {
		List<TariffPlan> list=tariffPlanService.selectList(null);
		return new DataTableParameter<TariffPlan>(list.size(),
				request.getParameter("sEcho"),list);
	}
	
	
}
