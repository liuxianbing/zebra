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
import com.sim.cloud.zebra.model.CartCard;
import com.sim.cloud.zebra.model.OrderGoods;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.SysAddress;
import com.sim.cloud.zebra.model.SysArea;
import com.sim.cloud.zebra.service.CartCardService;
import com.sim.cloud.zebra.service.OrderGoodsService;
import com.sim.cloud.zebra.service.PackageService;
import com.sim.cloud.zebra.service.SysAddressService;
import com.sim.cloud.zebra.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 下午7:10:14 
* 类说明 
*/
@Controller
@Api(value = "购物管理")
@RequestMapping(value = "/cart")
public class ShopCardController extends AbstractController{

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private SysAddressService sysAddressService;
	@Autowired
	private OrderGoodsService orderGoodsService;
	@Autowired
	private CartCardService cartCardService;
	
	@ApiOperation(value = "购物车列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model,@RequestParam Long uid) {
		model.addAttribute("user", sysUserService.selectById(uid));
		model.addAttribute("goodsList",cartCardService.selectList(null));
		return "purchase/cart_list";
	}
	
	@ApiOperation(value = "购卡记录列表页面")
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public String recordList(Model model) {
		Map<String,Object> params=extractFromRequest();
		Page<OrderGoods> page=
				orderGoodsService.selectPage(params,OrderGoods.class);
		model.addAttribute("page", page);
		return "purchase/buy_record";
	}
	
	@ApiOperation(value = "订单页面")
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String toOrder(Model model) {
		model.addAttribute("provinceList", sysAddressService.selectAllProvinces());
		model.addAttribute("addressList", sysAddressService.selectSelfAddress(2l));
		return "purchase/order";
	}
	
	@ApiOperation(value = "查询省份")
	@RequestMapping(value = "/queryCity", method = RequestMethod.POST)
	public @ResponseBody List<SysArea> queryArea(@RequestParam Long id,@RequestParam Integer type){
		return sysAddressService.selectParentId(id,type);
	}
	
	@ApiOperation(value = "设置默认地址")
	@RequestMapping(value = "/setDefaultAddr", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> setDefaultAddr(@RequestParam Long uid,@RequestParam Long id){
		sysAddressService.saveDefaultAddress(uid, id);
		return SUCCESS;
	}
	
	@ApiOperation(value = "删除地址")
	@RequestMapping(value = "/delAddr", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> delAddr(@RequestParam Long id){
		sysAddressService.deleteById(id);
		return SUCCESS;
	}
	
	
	@ApiOperation(value = "增加地址")
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public @ResponseBody SysAddress addAddress(@RequestBody SysAddress add){
		sysAddressService.insertOrUpdate(add);
		if(add.getOpt()!=null && add.getOpt()==1){//默认地址
			sysAddressService.saveDefaultAddress(add.getUid(), add.getId());
		}
		return add;
	}
	
	@ApiOperation(value = "购买页面")
	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public String toBuy(Model model) {
		model.addAttribute("userList", sysUserService.selectCustomers());
		model.addAttribute("packageList", packageService.selectSystemPacks());
		return "purchase/buy";
	}
	
	@ApiOperation(value = "购买卡片")
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> buyCard(@RequestBody CartCard cc) {
		cartCardService.insert(cc);
		return SUCCESS;
	}
}
