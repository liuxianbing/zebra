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
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;
import com.sim.cloud.zebra.service.PackageService;
import com.sim.cloud.zebra.service.SysUserService;
import com.sim.cloud.zebra.service.TariffPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 上午10:11:21 
* 类说明 
*/
@Controller
@Api(value = "系统套餐")
@RequestMapping(value = "/pack")
public class PackController  extends AbstractController {
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private TariffPlanService tariffPlanService;

	@ApiOperation(value = "套餐列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toSelfList(Model model) {
		model.addAttribute("userList", sysUserService.selectCustomers());
		return "package/package_list";
	}
	
	@ApiOperation(value = "套餐添加页面")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model,@RequestParam(required=false) Long id) {
		model.addAttribute("userList", sysUserService.selectCustomers());
		model.addAttribute("planList", tariffPlanService.selectList(null));
		Package pack=new Package();
		if(null!=id){
			pack=packageService.selectById(id);
		}
		model.addAttribute("pack", pack);
		return "package/package_add";
	}
	
	@ApiOperation(value = "添加套餐")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String,String>  addOrUpdate(Model model,@RequestBody Package user) {
		if(user.getId()==null || user.getId()==0l){
			user.setCreateTime(DateUtil.getDateTime());
		}
		packageService.insertOrUpd(user);
		return SUCCESS;
	}
	
	@ApiOperation(value = "用户自己的套餐")
	@RequestMapping(value = "/selfPacks", method = RequestMethod.POST)
	public @ResponseBody List<Package> queryUserPacks(@RequestParam Long uid){
		return packageService.selectUserPacks(uid);
	}
	
	@ApiOperation(value = "删除套餐")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public @ResponseBody Map<String,String>  del(Model model,@RequestParam(required=true) Long id) {
		packageService.delete(id);
		return SUCCESS;
	}
	
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "系统列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<Package> packList() {
		Page<Package> page=packageService.selectPage(extractFromRequest(),Package.class);
		page.getRecords().stream().forEach(e->{
			if(e.getUid()!=null && e.getUid()>0l){
				SysUser su=sysUserService.selectById(e.getUid());
				e.setUserName(su.getPhone()+"-"+su.getUserName());
			}
		});
		return new DataTableParameter<Package>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
}
