package com.sim.cloud.zebra.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.sim.cloud.zebra.model.CartCard;
import com.sim.cloud.zebra.model.Package;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 上午10:11:21 
* 类说明 
*/
@Controller
@Api(value = "套餐管理", description = "套餐管理处理模块")
@RequestMapping(value = "/pack")
public class PackController  extends AbstractController {
	
	

	@ApiOperation(value = "套餐列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toSelfList(Model model) {
		model.addAttribute("companyList",companyService.selectList(null).
				stream().filter(f->f.getBusinessAuth()==1 && f.getLegalAuth()==1).
				collect(Collectors.toList()) );
		model.addAttribute("planList", tariffPlanService.selectList(null));
		return "package/package_list";
	}
	
	@ApiOperation(value = "套餐添加页面")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model,@RequestParam(required=false) Long id) {
		
		model.addAttribute("companyList",companyService.selectList(null).
				stream().filter(f->f.getBusinessAuth()==1 && f.getLegalAuth()==1).
				collect(Collectors.toList()) );
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
			user.setUid(sysUserService.selectManager(user.getCid()).getId());
			user.setCreateTime(DateUtil.getDateTime());
		}
		packageService.insertOrUpd(user);
		return SUCCESS;
	}
	
	@ApiOperation(value = "用户自己的套餐")
	@RequestMapping(value = "/selfAllocPacks", method = RequestMethod.POST)
	public @ResponseBody List<CartCard> queryUserPacks(@RequestParam Long cid){
		return cartCardService.selectUserAllocPacks(cid);
	}
	
	@ApiOperation(value = "用户自己的套餐")
	@RequestMapping(value = "/selfUserPacks", method = RequestMethod.POST)
	public @ResponseBody List<Package> selfUserPacks(@RequestParam Long uid){
		return packageService.selectCompanyPacks(sysUserService.selectById(uid).getCid());
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
		page.getRecords().parallelStream().forEach(e->{
			if(e.getCid()!=null && e.getCid()>0l){
				e.setUserName(companyService.selectById(e.getCid()).getName());
			}
		});
		return new DataTableParameter<Package>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
}
