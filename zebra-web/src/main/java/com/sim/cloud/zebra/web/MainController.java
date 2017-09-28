package com.sim.cloud.zebra.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年9月27日 上午10:51:28 
* 类说明   首页控制类
*/
@Controller
@Api(value = "首页")
public class MainController  extends AbstractController {
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "首页信息")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toMainPage(Model model) {
		model.addAttribute("pa", "papapapapa...");
		      //return getHttpSession().getAttribute("user") != null?"root_main":"login";
		return "root_main";
	}
}
