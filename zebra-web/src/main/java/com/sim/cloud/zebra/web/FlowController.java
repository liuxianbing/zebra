package com.sim.cloud.zebra.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.model.FlowPoolVo;
import com.sim.cloud.zebra.model.SimcardPackageView;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月23日 下午6:26:54 
* 类说明  流量池功能
*/
@Controller
@Api(value = "流量池管理", description = "流量池管理处理模块")
@RequestMapping(value = "/flow")
public class FlowController extends AbstractController {

	
	@ApiOperation(value = "个人流量池列表页面")
	@RequestMapping(value = "/self", method = RequestMethod.GET)
	public String toSelfList(Model model) {
		Long cid=null;
		Long uid=null;
		if(getCurrUser().getRole()==SysUser.ROLE_MANAGER){
			cid=getCurrUser().getCid();
		}else if(getCurrUser().getRole()==SysUser.ROLE_USER){
			uid=getCurrUser().getId();
		}
		model.addAttribute("list", simCardServiceView.selectSelfPoolList(uid,cid));
		return "flow/self_flow";
	}
	
	@ApiOperation(value = "单个流量池卡片列表请求")
	@RequestMapping(value = "cardlist", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<SimcardPackageView> cardList() {
		Map<String,Object> param=extractFromRequest();
		Long uid=null;
		Long cid=null;
		if(getCurrUser().getRole()==SysUser.ROLE_MANAGER){
			cid=getCurrUser().getCid();
		}else if(getCurrUser().getRole()==SysUser.ROLE_USER){
			uid=getCurrUser().getId();
		}else{
			cid=Long.parseLong(param.get("cid").toString());
		}
		String iccid=null;
		if(null!=param.get("iccid")){
			iccid=param.get("iccid").toString();
		}
		List<SimcardPackageView> list=simCardServiceView.selectFlowCards(uid,cid, Integer.parseInt(param.get("flow").toString()),
				iccid);
		return new DataTableParameter<SimcardPackageView>(list.size(),request.getParameter("sEcho"),list);
	}
	
	@ApiOperation(value = "流量池详细页面")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String toDetail(Model model,@RequestParam(required=false) Long cid,@RequestParam Integer flow) {
		Long uid=null;
		if(getCurrUser().getRole()==SysUser.ROLE_MANAGER){
			cid=getCurrUser().getCid();
		}else if(getCurrUser().getRole()==SysUser.ROLE_USER){
			uid=getCurrUser().getId();
		}
		SimcardPackageView spv=simCardServiceView.statisFlowPool(uid,cid, flow);
		model.addAttribute("flow", flow);
		model.addAttribute("cardflow",spv);
		return "flow/flow_detail";
	}
	
	@ApiOperation(value = "流量池列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		List<Integer> flowList=packageService.selectList(null).
		stream().filter(f->f.getCardType()==TariffPlan.SHARE).map(m->m.getFlow()).distinct()
		.collect(Collectors.toList());
		model.addAttribute("flowList", flowList);
		model.addAttribute("companyList",companyService.selectList(null).
				stream().filter(f->f.getBusinessAuth()==1 && f.getLegalAuth()==1).
				collect(Collectors.toList()) );
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
		List<Long> cidList=new ArrayList<>();
		Integer flow=null;
		if(StringUtils.isNoneBlank(request.getParameter("keyword"))){
			Map<String,Object> params=new HashMap<>();
			params.put("keyword", request.getParameter("keyword"));
			cidList.addAll(sysUserService.queryList(params).stream().filter(f->f.getCid()!=null && f.getCid()>0).
					map(m->m.getCid()).distinct().collect(Collectors.toList()));
			if( cidList.size()==0){
				return new DataTableParameter<FlowPoolVo>(0,
						request.getParameter("sEcho"),new ArrayList<>());
			}
		}
		if(StringUtils.isNoneBlank(request.getParameter("cid"))){
			cidList.clear();
			cidList.add(Long.parseLong(request.getParameter("cid")));
		}
		if(StringUtils.isNoneBlank(request.getParameter("flow"))){
			flow=Integer.parseInt(request.getParameter("flow"));
		}
		List<FlowPoolVo> list=simCardServiceView.selectAllPoolList(cidList,flow);
		return new DataTableParameter<FlowPoolVo>(list.size(),
				request.getParameter("sEcho"),list);
	}
}
