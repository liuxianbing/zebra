package com.sim.cloud.zebra.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.model.FlowPoolVo;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;
import com.sim.cloud.zebra.service.SimcardPackViewService;
import com.sim.cloud.zebra.service.SysUserService;
import com.sim.cloud.zebra.service.TariffPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月23日 下午6:26:54 
* 类说明  流量池功能
*/
@Controller
@Api(value = "流量池管理")
@RequestMapping(value = "/flow")
public class FlowController extends AbstractController {

	@Autowired
	private SimcardPackViewService simCardServiceView;
	
	@Autowired
	private TariffPlanService tariffPlanService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@ApiOperation(value = "个人流量池列表页面")
	@RequestMapping(value = "/self", method = RequestMethod.GET)
	public String toSelfList(Model model) {
		model.addAttribute("list", simCardServiceView.selectSelfPoolList(getCurrUser().getId()));
		return "flow/self_flow";
	}
	
	@ApiOperation(value = "流量池列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		model.addAttribute("planList", tariffPlanService.selectList(null).
				stream().filter(f->f.getType()==TariffPlan.SHARE).collect(Collectors.toList()));
		return "flow/flow_list";
	}
	/**
	 * 流量池列表请求
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "流量池列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<FlowPoolVo> list() {
		List<Long> userIdList=new ArrayList<>();
		Integer flow=null;
		if(StringUtils.isNoneBlank(request.getParameter("keyword"))){
			Map<String,Object> params=new HashMap<>();
			params.put("keyword", request.getParameter("keyword"));
			userIdList=sysUserService.getBaseMapper().selectIdPage(params);
			if(userIdList==null || userIdList.size()==0){
				return new DataTableParameter<FlowPoolVo>(0,
						request.getParameter("sEcho"),new ArrayList<>());
			}
		}
		if(StringUtils.isNoneBlank(request.getParameter("flow"))){
			flow=Integer.parseInt(request.getParameter("flow"));
		}
		List<FlowPoolVo> list=simCardServiceView.selectAllPoolList(userIdList,flow);
		return new DataTableParameter<FlowPoolVo>(list.size(),
				request.getParameter("sEcho"),list);
	}
}
