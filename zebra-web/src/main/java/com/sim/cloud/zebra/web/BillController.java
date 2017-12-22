package com.sim.cloud.zebra.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.model.Bill;
import com.sim.cloud.zebra.model.BillRecord;
import com.sim.cloud.zebra.model.OrderGoods;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.BillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月24日 下午4:21:50 
* 类说明 
*/
@Api(value = "计费管理", description = "计费管理处理模块")
@Controller
@RequestMapping(value = "/bill")
public class BillController  extends AbstractController {


	@ApiOperation(value = "计费列表页面")
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public String recordList(Model model) {
		Map<String,Object> params=extractFromRequest();
		params.put("orderBy", "id");
		if(getCurrUser().getRole()==SysUser.ROLE_MANAGER){
			params.put("uid",getCurrUser().getId());
		}else if(StringUtils.isNotBlank(request.getParameter("uid"))){
			params.put("uid",Long.parseLong(request.getParameter("uid")));
			model.addAttribute("uid", request.getParameter("uid"));
		}
		List<SysUser> list=sysUserService.selectCustomers().stream().
				filter(f->f.getAuth()==1).collect(Collectors.toList());
		list.parallelStream().filter(f->f.getCid()!=null && f.getCid()>0l).forEach(e->{
			e.setCompanyName(companyService.selectById(e.getCid()).getName());
		});
		if(getCurrUser().getRole()==SysUser.ROLE_SYS){
			model.addAttribute("userList", list);
		}
		Map<Long,SysUser> userMaps=list.stream().collect(Collectors.toMap(SysUser::getId, c->c));
		Page<Bill> page=billService.selectPage(params,Bill.class);
				
		
		page.getRecords().parallelStream().forEach(e->{
			List<BillRecord> recordList=billRecordService.selectByBillId(e.getId());
			recordList.sort((a,b)-> a.getType()-b.getType());
			e.setContents(billRecordService.selectByBillId(e.getId()));
			try {
				e.setUserInfo(userMaps.get(e.getUid()).getUserName());
			} catch (Exception e2) {
			}
		});
		model.addAttribute("page", page);
		return "bill/bill_record";
	}
	
}
