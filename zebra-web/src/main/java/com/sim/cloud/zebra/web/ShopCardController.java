package com.sim.cloud.zebra.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sim.cloud.zebra.common.util.CartCardEnum;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.JackSonUtil;
import com.sim.cloud.zebra.model.CartCard;
import com.sim.cloud.zebra.model.LogAudit;
import com.sim.cloud.zebra.model.OrderGoods;
import com.sim.cloud.zebra.model.SysAddress;
import com.sim.cloud.zebra.model.SysArea;
import com.sim.cloud.zebra.model.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author liuxianbing:
 * @version 创建时间：2017年10月31日 下午7:10:14 类说明
 */
@Controller
@Api(value = "购物管理", description = "购物管理处理模块")
@RequestMapping(value = "/cart")
public class ShopCardController extends AbstractController {

	@ApiOperation(value = "购物车列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model, @RequestParam Long uid) {
		model.addAttribute("user", sysUserService.selectById(uid));
		model.addAttribute("goodsList", cartCardService.selectCarts(uid, CartCard.INCART));
		return "purchase/cart_list";
	}

	@ApiOperation(value = "订单详细页面")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, @RequestParam Long id) {
		OrderGoods og = orderGoodsService.selectById(id);
		if (SysUser.ROLE_MANAGER == getCurrUser().getRole() && og.getUid() != getCurrUser().getId()) {
			return "/error/error";
		}
		model.addAttribute("order", og);
		model.addAttribute("addr", sysAddressService.selectById(og.getAddrId()));
		List<CartCard> list = cartCardService.selectByOrderId(id);
		model.addAttribute("goodsList", list);
		model.addAttribute("uid", og.getUid());
		model.addAttribute("records", list.stream().map(m -> m.getId() + "").collect(Collectors.joining(",")));

		if(og.getType()>=CartCardEnum.CHECKOK_ORDER.getStatus()){
			model.addAttribute("cards", 
					simCardServiceView.selectByOrderCarts(list.stream().map(m->m.getId()).
							collect(Collectors.toList())));
		}
		return "purchase/order_detail";
	}

	@ApiOperation(value = "购卡记录列表页面")
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public String recordList(Model model) {
		Map<String, Object> params = extractFromRequest();
		params.put("orderBy", "id");
		if (getCurrUser().getRole() == SysUser.ROLE_MANAGER) {
			params.put("uid", getCurrUser().getId());
		} else if (StringUtils.isNotBlank(request.getParameter("uid"))) {
			params.put("uid", Long.parseLong(request.getParameter("uid")));
			model.addAttribute("uid", request.getParameter("uid"));
		}
		List<SysUser> list = sysUserService.selectCustomers().stream().filter(f -> f.getAuth() == 1)
				.collect(Collectors.toList());
		list.parallelStream().filter(f -> f.getCid() != null && f.getCid() > 0l).forEach(e -> {
			e.setCompanyName(companyService.selectById(e.getCid()).getName());
		});
		if (getCurrUser().getRole() == SysUser.ROLE_SYS) {
			model.addAttribute("userList", list);
		}
		Map<Long, SysUser> userMaps = list.stream().collect(Collectors.toMap(SysUser::getId, c -> c));
		Page<OrderGoods> page = orderGoodsService.selectPage(params, OrderGoods.class);

		page.getRecords().parallelStream().forEach(e -> {
			e.setContents(cartCardService.selectByOrderId(e.getId()));
			try {
				e.setUserInfo(userMaps.get(e.getUid()).getUserName());
			} catch (Exception e2) {
			}
		});
		model.addAttribute("page", page);
		return "purchase/buy_record";
	}

	@ApiOperation(value = "更改订单状态")
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> updateStatus(@RequestBody OrderGoods og) {
		Map<String, String> map = new HashMap<>();
		map.put("res", orderGoodsService.updateOrderStatus(og, getCurrUser().getUserName()) + "");
		logAuditService.insertAudit(getCurrUser(),"订单编号:"+orderGoodsService.selectById(og.getId()).
				getOrderCode(),CartCardEnum.getEnumByStatus(og.getType()).getStatusStr());
		return map;
	}

	@ApiOperation(value = "支付页面")
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public String payPage(Model model, @RequestParam Long id) {
		OrderGoods og = orderGoodsService.selectById(id);
		model.addAttribute("order", og);
		model.addAttribute("bance", financeService.selectBance(og.getUid()));
		return "purchase/pay";
	}

	@ApiOperation(value = "支付")
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> pay(@RequestParam Long id) {
		Map<String, String> map = new HashMap<>();
		OrderGoods og = new OrderGoods();
		og.setType(CartCardEnum.PAYOK_ORDER.getStatus());
		og.setId(id);
		map.put("res", orderGoodsService.updateOrderStatus(og, getCurrUser().getUserName()) + "");
		logAuditService.insertAudit(getCurrUser(),"订单编号:"+orderGoodsService.selectById(og.getId()).getOrderCode(),CartCardEnum.getEnumByStatus(og.getType()).getStatusStr());
		return map;
	}

	@ApiOperation(value = "保存清单")
	@RequestMapping(value = "setlement", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody Map<String, String> setlement(@RequestParam String ids) {
		List<CartCard> list = Arrays.asList(ids.split(",")).stream().map(m -> {
			CartCard cc = new CartCard();
			cc.setId(Long.parseLong(m.split("_")[0]));
			cc.setNum(Integer.parseInt(m.split("_")[1]));
			return cc;
		}).collect(Collectors.toList());
		cartCardService.updateBatchById(list);
		return SUCCESS;
	}

	@ApiOperation(value = "订单页面")
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String toOrder(Model model, @RequestParam Long uid) {
		model.addAttribute("provinceList", sysAddressService.selectAllProvinces());
		model.addAttribute("addressList", sysAddressService.selectSelfAddress(uid));
		List<CartCard> list = cartCardService.selectCarts(uid, CartCard.INCART);
		model.addAttribute("goodsList", list);
		model.addAttribute("uid", uid);
		model.addAttribute("records", list.stream().map(m -> m.getId() + "").collect(Collectors.joining(",")));
		return "purchase/order";
	}

	@ApiOperation(value = "查询省份")
	@RequestMapping(value = "/queryCity", method = RequestMethod.POST)
	public @ResponseBody List<SysArea> queryArea(@RequestParam Long id, @RequestParam Integer type) {
		return sysAddressService.selectParentId(id, type);
	}

	@ApiOperation(value = "删除一条购物记录")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> delOneCart(@RequestParam Long id) {
		cartCardService.deleteById(id);
		return SUCCESS;
	}

	@ApiOperation(value = "去结算")
	@RequestMapping(value = "/saveRecords", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> saveRecords(@RequestBody List<CartCard> list) {
		cartCardService.insertOrUpdateBatch(list);
		return SUCCESS;
	}

	@ApiOperation(value = "设置默认地址")
	@RequestMapping(value = "/setDefaultAddr", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> setDefaultAddr(@RequestParam Long uid, @RequestParam Long id) {
		sysAddressService.saveDefaultAddress(uid, id);
		return SUCCESS;
	}

	@ApiOperation(value = "删除地址")
	@RequestMapping(value = "/delAddr", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> delAddr(@RequestParam Long id) {
		sysAddressService.deleteById(id);
		return SUCCESS;
	}

	@ApiOperation(value = "增加地址")
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public @ResponseBody SysAddress addAddress(@RequestBody SysAddress add) {
		sysAddressService.insertOrUpdate(add);
		if (add.getOpt() != null && add.getOpt() == 1) {// 默认地址
			sysAddressService.saveDefaultAddress(add.getUid(), add.getId());
		}
		return add;
	}

	@ApiOperation(value = "购买页面")
	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public String toBuy(Model model) {
		List<SysUser> list = sysUserService.selectCustomers().stream().filter(f -> f.getAuth() == 1)
				.collect(Collectors.toList());
		list.parallelStream().filter(f -> f.getCid() != null && f.getCid() > 0l).forEach(e -> {
			e.setCompanyName(companyService.selectById(e.getCid()).getName());
		});
		model.addAttribute("userList", list);
		// model.addAttribute("packageList",
		// packageService.selectSystemPacks());
		return "purchase/buy";
	}

	@ApiOperation(value = "提交订单")
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public @ResponseBody OrderGoods saveOrder(@RequestBody OrderGoods cc) {
		List<Long> params = Arrays.asList(cc.getRecords().split(",")).stream().map(m -> Long.parseLong(m))
				.collect(Collectors.toList());
		cc.setCreateUserId(getCurrUser().getId());
		orderGoodsService.saveOrder(cc, params);
//		try {
//			logAuditService.insertAudit(getCurrUser(),JackSonUtil.getObjectMapper().writeValueAsString(cc),
//					"提交购卡订单");
//		} catch (JsonProcessingException e) {
//		}
		return cc;
	}

	@ApiOperation(value = "购买卡片")
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> buyCard(@RequestBody CartCard cc) {
		cc.setCid(sysUserService.selectById(cc.getUid()).getCid());
		cc.setCreateUserId(getCurrUser().getId());
		cartCardService.insert(cc);
//		try {
//			logAuditService.insertAudit(getCurrUser(),JackSonUtil.getObjectMapper().writeValueAsString(cc),
//					"购买卡片");
//		} catch (JsonProcessingException e) {
//		}
		return SUCCESS;
	}
}
