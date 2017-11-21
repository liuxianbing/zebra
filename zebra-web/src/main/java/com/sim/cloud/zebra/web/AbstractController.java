package com.sim.cloud.zebra.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.HttpCode;
import com.sim.cloud.zebra.common.util.InstanceUtil;
import com.sim.cloud.zebra.common.util.WebUtil;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.CartCardService;
import com.sim.cloud.zebra.service.CompanyService;
import com.sim.cloud.zebra.service.FinanceService;
import com.sim.cloud.zebra.service.OrderGoodsService;
import com.sim.cloud.zebra.service.PackageService;
import com.sim.cloud.zebra.service.SimCardService;
import com.sim.cloud.zebra.service.SimcardPackViewService;
import com.sim.cloud.zebra.service.StatisCardFlowService;
import com.sim.cloud.zebra.service.SysAddressService;
import com.sim.cloud.zebra.service.SysUserService;
import com.sim.cloud.zebra.service.TariffPlanService;

/**
 * 控制器基类
 * 
 * @author liuxianbing
 * @version 2017年5月20日 下午3:47:58
 */
public abstract class AbstractController {
	protected final Logger logger = LogManager.getLogger();

	protected static final Map<String, String> SUCCESS = new HashMap<String, String>();
	
	@Autowired
	protected SimcardPackViewService simCardServiceView;
	
	@Autowired
	protected TariffPlanService tariffPlanService;
	
	@Autowired
	protected SysUserService sysUserService;
	

	@Autowired
	protected SimCardService simcardService;
	@Autowired
	protected CompanyService companyService;
	@Autowired
	protected PackageService packageService;
	@Autowired
	protected StatisCardFlowService statisCardFlowService;
	@Autowired
	protected FinanceService financeService;
	@Autowired
	protected SysAddressService sysAddressService;
	@Autowired
	protected OrderGoodsService orderGoodsService;
	@Autowired
	protected CartCardService cartCardService;
	
	static{
		SUCCESS.put("result", "success");
	}
	// 线程安全
	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected HttpServletResponse response;

	// /** 获取当前用户Id */
	protected SysUser getCurrUser() {
		return WebUtil.getCurrentUser(request);
	}
	/**
	 * 判断当前用户是否管理员
	 * @return
	 */
	protected boolean checkIfManager(){
		if(getCurrUser()!=null && getCurrUser().getRole()==SysUser.ROLE_SYS){
			return true;
		}
		return false;
	}
	
	protected HttpSession getHttpSession(){
		return request.getSession();
	}

//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new DateFormat(), true));
//	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
		return setSuccessModelMap(modelMap, null);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
		return setModelMap(modelMap, HttpCode.OK, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
		return setModelMap(modelMap, code, null);
	}
	
	protected Map<String,Object> extractFromRequest(Map<String,Object> params){
		 Map<String,Object> res2=new HashMap<>();
		 params.entrySet().stream().filter(e->{
			 if(!(e.getValue() instanceof String[])){
				 if(StringUtils.isBlank(e.getValue().toString())){
					 return false;
				 }
			 }
			 return true;
		 }).forEach(e->{
			 res2.put(e.getKey(), e.getValue());
		 });
		 return res2;
	}
	/**
	 * 从request获取参数 转化为Map类型
	 * @return
	 */
	protected Map<String,Object> extractFromRequest(){
		 Map<String,Object> res=new HashMap<>();
		 request.getParameterMap().entrySet().stream().
		 filter(f->f.getValue()!=null && StringUtils.isNotBlank(f.getValue().toString()))
		 .forEach(m->{
			 if(m.getValue()!=null && m.getValue().length==1){
				 res.put(m.getKey(), m.getValue()[0]);
			 }else{
				 res.put(m.getKey(), m.getValue());
			 }
		 });
		 Map<String,Object> res2=new HashMap<>();
		 res.entrySet().stream().filter(e->{
			 if(!(e.getValue() instanceof String[])){
				 if(StringUtils.isBlank(e.getValue().toString())){
					 return false;
				 }
			 }
			 return true;
		 }).forEach(e->{
			 res2.put(e.getKey(), e.getValue());
		 });
		 return res2;
	}
	
	

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
		Map<String, Object> map = InstanceUtil.newLinkedHashMap();
		map.putAll(modelMap);
		modelMap.clear();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (!key.startsWith("org.springframework.validation.BindingResult") && !key.equals("void")) {
				modelMap.put(key, map.get(key));
			}
		}
		if (data != null) {
			if (data instanceof Page) {
				Page<?> page = (Page<?>) data;
				modelMap.put("data", page.getRecords());
				modelMap.put("current", page.getCurrent());
				modelMap.put("size", page.getSize());
				modelMap.put("pages", page.getPages());
				modelMap.put("total", page.getTotal());
				modelMap.put("iTotalRecords", page.getTotal());
				modelMap.put("iTotalDisplayRecords", page.getTotal());
			} else if (data instanceof List<?>) {
				modelMap.put("data", data);
				modelMap.put("iTotalRecords", ((List<?>) data).size());
				modelMap.put("iTotalDisplayRecords", ((List<?>) data).size());
			} else {
				modelMap.put("data", data);
			}
		}
		modelMap.put("httpCode", code.value());
		modelMap.put("msg", code.msg());
		modelMap.put("timestamp", System.currentTimeMillis());
		return ResponseEntity.ok(modelMap);
	}

	/** 异常处理 
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		ModelMap modelMap = new ModelMap();
		if (ex instanceof BaseException) {
			((BaseException) ex).handler(modelMap);
		} else if (ex instanceof IllegalArgumentException) {
			new IllegalParameterException(ex.getMessage()).handler(modelMap);
		} else {
			modelMap.put("httpCode", HttpCode.INTERNAL_SERVER_ERROR.value());
			String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
			modelMap.put("msg", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
		}
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("exceptionHandler--------------");
		modelMap.put("timestamp", System.currentTimeMillis());
		ex.printStackTrace();
		logger.info("RESPONSE-exceptionHandler : " + JackSonUtil.getObjectMapper().writeValueAsString(modelMap));
		response.getOutputStream().write(JackSonUtil.getObjectMapper().writeValueAsBytes(modelMap));
	}
	*/
}