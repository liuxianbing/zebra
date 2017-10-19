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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SimcardPackageView;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.SimCardService;
import com.sim.cloud.zebra.service.SimcardPackViewService;
import com.sim.cloud.zebra.service.SysUserService;

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
	private SimcardPackViewService simCardService;
	
	@Autowired
	private SysUserService sysUserService;
	/**
	 * 物联网卡列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "物联网卡列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		model.addAttribute("userList", sysUserService.selectCustomers());
		return "simcard/card_list";
	}
	
	/**
	 * 物联网卡请求
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "物联网卡列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<SimcardPackageView> list() {
		Page<SimcardPackageView> page=simCardService.query(extractFromRequest());
		//List<SimcardPackageView> list=page.getRecords();
		return new DataTableParameter<SimcardPackageView>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
}
