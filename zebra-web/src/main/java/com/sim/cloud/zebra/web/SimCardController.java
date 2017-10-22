package com.sim.cloud.zebra.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.Constants;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.model.Company;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SimcardPackageView;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.CompanyService;
import com.sim.cloud.zebra.service.PackageService;
import com.sim.cloud.zebra.service.SimCardService;
import com.sim.cloud.zebra.service.SimcardPackViewService;
import com.sim.cloud.zebra.service.SysUserService;
import com.sim.cloud.zebra.service.TariffPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午1:15:19 
* 类说明 
*/
@Controller
@Api(value = "物联网卡控制")
@RequestMapping(value = "/simcard")
public class SimCardController extends AbstractController{

	@Autowired
	private SimcardPackViewService simCardServiceView;
	@Autowired
	private SimCardService simcardService;
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private TariffPlanService tariffPlanService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private PackageService packageService;
	/**
	 * 物联网卡列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "物联网卡列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		model.addAttribute("userList", sysUserService.selectCustomers());
		model.addAttribute("planList", tariffPlanService.selectList(null));
		model.addAttribute("termList",Constants.TERM_LIST);
		return "simcard/card_list";
	}
	
	@ApiOperation(value = "物联网卡列表页面")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String toDetail(Model model,@RequestParam String id) {
		SimcardPackageView card= simCardServiceView.selectById(Long.parseLong(id));
		model.addAttribute("card", card);
		SysUser user=new SysUser();
		Company company=new Company();
		Package pack=new Package();
		if(card.getUid()!=null){
			user=sysUserService.selectById(card.getUid());
		}
		if(user.getCid()!=null){
			company=companyService.selectById(user.getCid());
		}
		if(card.getPackageId()!=null){
			pack=packageService.selectById(card.getPackageId());
		}
		model.addAttribute("company", company);
		model.addAttribute("user",user);
		model.addAttribute("pack",pack);
		return "simcard/card_detail";
	}
	
	@ApiOperation(value = "物联网卡备注")
	@RequestMapping(value = "remark", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody Map<String,String> remarkCards(@RequestBody Map<String,String> params) {
		List<SimCard> list=Arrays.asList(params.get("ids").split(",")).stream().map(m->{
			SimCard sc=new SimCard();
			sc.setId(Long.parseLong(m));
			sc.setRemark(params.get("remark"));
			return sc;
		}).collect(Collectors.toList());
		simcardService.updateBatchById(list);
		return SUCCESS;
	}
	
	@ApiOperation(value = "物联网卡划拨")
	@RequestMapping(value = "alloc", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody Map<String,String> alloc(@RequestBody Map<String,Object> params) {
		List<String> iccidsList=Arrays.asList(params.get("iccid").toString());
		List<String> list=simcardService.saveCardPlanRel(iccidsList,
				Arrays.asList(params.get("ids").toString()),Float.parseFloat(params.get("externalQuote").toString()),
				Long.parseLong(params.get("planId").toString()), Long.parseLong(params.get("uid").toString())
				, Integer.parseInt(params.get("term").toString()));
		Map<String,String> map=new HashMap<>();
		if(null!=list && list.size()>0){
			String msg=list.stream().collect(Collectors.joining(","));
			map.put("msg", "成功划拨卡片:"+(iccidsList.size()-list.size())+"个,失败划拨卡片:"+list.size()+"个,ICCID为:"+msg);
			return map;
		}
		return SUCCESS;
	}
	
	/**
	 * 物联网卡请求
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "物联网卡列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<SimcardPackageView> list() {
		Page<SimcardPackageView> page=simCardServiceView.query(extractFromRequest());
		Map<Long,SysUser> userMap=sysUserService.selectCustomers().stream().collect(Collectors.toMap(SysUser::getId, c->c));
		page.getRecords().stream().forEach(e->{//设置用户信息
			if(e.getUid()!=null && e.getUid()>0l && userMap.get(e.getUid())!=null){
				e.setUserInfo(userMap.get(e.getUid()).getUserName());
			}
		});
		return new DataTableParameter<SimcardPackageView>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
}
