package com.sim.cloud.zebra.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@SessionAttributes("user")
@RequestMapping(value = "/user")
@Api(value = "用户接口")
public class UserController extends AbstractController {

	/**
	 * 列表页面
	 * 
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "当前用户信息")
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(Model model) {
		return "/finance/finance_list";
	}
	
	
}
