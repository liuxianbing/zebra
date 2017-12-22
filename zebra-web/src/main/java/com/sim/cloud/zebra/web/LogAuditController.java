package com.sim.cloud.zebra.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.model.LogAudit;
import com.sim.cloud.zebra.model.TariffPlan;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月27日 下午5:19:30 
* 类说明 
*/
@Controller
@Api(value = "审计管理", description = "审计管理处理模块")
@RequestMapping(value = "/audit")
public class LogAuditController  extends AbstractController{

	@ApiOperation(value = "页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toAudit(Model model) {
		model.addAttribute("userList", sysUserService.selectSys());
		return "log/log_list";
	}
	
	
	@ApiOperation(value = "页面列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<LogAudit> audit() {
		Page<LogAudit> page=logAuditService.selectPage(extractFromRequest(), LogAudit.class);
		return new DataTableParameter<LogAudit>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
	
//	@ApiOperation(value = "页面")
//	@RequestMapping(value = "/logList", method = RequestMethod.GET)
//	public String toList(Model model) {
//		model.addAttribute("userList", sysUserService);
//		return "log/log_list";
//	}
}
