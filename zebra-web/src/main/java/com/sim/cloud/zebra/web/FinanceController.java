package com.sim.cloud.zebra.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.common.util.FinanceEnum;
import com.sim.cloud.zebra.model.Finance;
import com.sim.cloud.zebra.model.SimcardPackageView;
import com.sim.cloud.zebra.service.FinanceService;
import com.sim.cloud.zebra.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月11日 上午11:56:08 
* 类说明 
*/
@Controller
@Api(value = "管理")
@RequestMapping(value = "/finance")
public class FinanceController  extends AbstractController{

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private FinanceService financeService;
	@ApiOperation(value = "页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		model.addAttribute("userList", sysUserService.selectCustomers());
		model.addAttribute("bance", financeService.selectBance(getCurrUser().getId()));
		return "finance/finance_list";
	}
	
	@ApiOperation(value = "add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model) {
		model.addAttribute("userList", sysUserService.selectCustomers());
		return "finance/finance_add";
	}
	
	@ApiOperation(value = "列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<Finance> list() {
		Page<Finance> page=financeService.selectPage(extractFromRequest(), Finance.class);
		return new DataTableParameter<Finance>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
	
	@ApiOperation(value = "add请求")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> addFinance(@RequestBody Finance fa){
		financeService.insertFinance(fa.getUid(),FinanceEnum.CHONGZHI.getType(),fa.getMoney());
		return SUCCESS;
	}
}
