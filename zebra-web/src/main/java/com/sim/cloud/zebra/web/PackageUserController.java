package com.sim.cloud.zebra.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.model.PackageUser;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.PackageUserService;
import com.sim.cloud.zebra.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午1:11:38 
* 类说明 
*/
@Controller
@Api(value = "用户套餐控制")
@RequestMapping(value = "/package")
public class PackageUserController extends AbstractController {

	@Autowired
	private PackageUserService packageUserService;
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "用户套餐列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		return "tariff/package_list";
	}
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "用户套餐列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<PackageUser> list() {
		Page<PackageUser> page=packageUserService.query(extractFromRequest());
		return new DataTableParameter<PackageUser>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
}
