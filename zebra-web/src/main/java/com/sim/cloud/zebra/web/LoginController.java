package com.sim.cloud.zebra.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sim.cloud.zebra.common.util.WebUtil;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.SysUserService;

import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月16日 下午5:45:35 
* 类说明 
*/
@Controller
public class LoginController extends AbstractController {

	@Autowired
	private SysUserService sysUserService;

	@ApiOperation(value = "登录页面")
	  @RequestMapping(value = "/login", method = RequestMethod.GET)
	  public String initLoginPage() {
	    if (getCurrUser() != null) {
	      return "redirect:/user/list";
	    } else {
	      return "login";
	    }
	  }
	
	@ApiOperation(value = "登录请求")
	  @RequestMapping(value = "/login", method = RequestMethod.POST)
	  public String doLogin( Model model) {
			Map<String,Object> map=extractFromRequest();
			map.put("passwd", DigestUtils.md5Hex(map.get("passwd").toString()));
			List<SysUser> list= sysUserService.selectByMap(map);
			  if(list==null || list.size()==0){
				  model.addAttribute("msg", "用户名或密码错误!");
				  return "login";
			  }
			  WebUtil.setCurrentUser(request, list.get(0));
		  return "redirect:/user/list";
	  }
	
	@ApiOperation(value = "退出")
	  @RequestMapping(value = "/logout", method = RequestMethod.GET)
	  public String logout(Model model) {
		request.getSession().invalidate();
		  return "login";
	  }
	
}
