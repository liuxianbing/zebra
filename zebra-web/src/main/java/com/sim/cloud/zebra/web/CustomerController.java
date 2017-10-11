package com.sim.cloud.zebra.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月11日 下午3:52:26 
* 类说明 
*/
@Controller
@Api(value = "客户管理")
@RequestMapping(value = "/customer")
public class CustomerController  extends AbstractController {

	@Autowired
	private SysUserService sysUserService;
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "客户管理列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		return "customer/customer_list";
	}
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "客户管理列表请求")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public DataTableParameter<SysUser> list(Model model) {
		Page<SysUser> page=sysUserService.query(extractFromRequest());
		return new DataTableParameter<SysUser>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
}
