package com.sim.cloud.zebra.web;

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

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.common.util.DateUtil;
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
@RequestMapping(value = "/user")
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
		return "system/user_list";
	}
	
	
	@ApiOperation(value = "添加客户页面")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model,@RequestParam(required=false) String id) {
		SysUser user=new SysUser();
		if(StringUtils.isNotBlank(id)){
			user=sysUserService.queryById(Long.parseLong(id));
		}
		model.addAttribute("user", user);
		return "system/user_add";
	}
	
	@ApiOperation(value = "添加客户")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String,String>  addOrUpdate(Model model,@RequestBody SysUser user) {
		if(user.getId()==null || user.getId()==0l){
			user.setCreateTime(DateUtil.getDateTime());
			user.setPasswd(DigestUtils.md5Hex(user.getPhone().substring(6)+"_sim"));
		}
		sysUserService.insertOrUpdate(user);
		return SUCCESS;
	}
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "客户管理列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<SysUser> list() {
		Page<SysUser> page=sysUserService.query(extractFromRequest());
		return new DataTableParameter<SysUser>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
}
