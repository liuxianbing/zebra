package com.sim.cloud.zebra.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.PropertiesUtil;
import com.sim.cloud.zebra.common.util.DateUtil.DATE_PATTERN;
import com.sim.cloud.zebra.model.Company;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.CompanyService;
import com.sim.cloud.zebra.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月11日 下午3:52:26 
* 类说明 
*/
@Controller
@Api(value = "客户管理")
@RequestMapping(value = "/user")
public class CustomerController  extends AbstractController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CompanyService companyService;
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "客户管理列表页面")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toList(Model model) {
		return "system/user_list";
	}
	
	
	@ApiOperation(value = "添加客户页面")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model,@RequestParam(required=false) String id) {
		SysUser user=new SysUser();
		if(StringUtils.isNotBlank(id)){
			user=sysUserService.queryById(Long.parseLong(id));
		}
		model.addAttribute("user", user);
		return "system/user_add";
	}
	
	@ApiOperation(value = "账号设置页面")
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String toInfo(Model model) {
		model.addAttribute("user", getCurrUser());
		Company company=new Company();
		if(getCurrUser().getCid()!=null && getCurrUser().getCid()>0l){
			try {
				company=companyService.selectById(getCurrUser().getCid());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.println(company);
		model.addAttribute("uid", getCurrUser().getId());
		model.addAttribute("company", company);
		
		return "system/user_info";
	}
	
	  @ApiOperation(value = "更新密码")
	  @RequestMapping(value = "modifyPwd", produces = {"application/json"}, method = RequestMethod.POST)
	  public @ResponseBody Map<String, String> modifyPwd(@RequestBody Map<String, Object> map) {
	    String oldPwd = (String) map.get("oldPwd");
	    String newPwd = (String) map.get("newPwd");
	    oldPwd = DigestUtils.md5Hex(oldPwd);
	    newPwd = DigestUtils.md5Hex(newPwd);
	    if(!getCurrUser().getPasswd().equals(oldPwd)){
	    	throw new RuntimeException("原始密码错误");
	    }
	    getCurrUser().setPasswd(newPwd);
	    sysUserService.updateById( getCurrUser());
	    return SUCCESS;
	  }
	
	@ApiOperation(value = "添加客户")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String,String>  addOrUpdate(Model model,@RequestBody SysUser user) {
		if(user.getId()==null || user.getId()==0l){
			user.setCreateTime(DateUtil.getDateTime());
			user.setPasswd(DigestUtils.md5Hex(user.getPhone().substring(6)+"_sim"));
		}
		sysUserService.insertOrUpdate(user);
		return SUCCESS;
	}
	
	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "客户管理列表请求")
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody DataTableParameter<SysUser> list() {
		Page<SysUser> page=sysUserService.query(extractFromRequest());
		return new DataTableParameter<SysUser>(page.getTotal(),
				request.getParameter("sEcho"),page.getRecords());
	}
	
	@ApiOperation(value = "认证客户页面")
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public String toAuth(Model model,@RequestParam String uid,@RequestParam(required=false) String cid) {
		Company company=new Company();
		if(StringUtils.isNumeric(cid)){
			company=companyService.queryById(Long.parseLong(cid));
		}
		model.addAttribute("uid", uid);
		model.addAttribute("company", company);
		return "system/user_auth";
	}
	
	@ApiOperation(value = "企业认证")
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> auth(@RequestBody Company company) {
		companyService.saveAuthInfo(company);
		if(!checkIfManager()){
			getCurrUser().setCid(company.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 上传工商执照
	 * @param upload
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */
	 @RequestMapping(value = "/uploadBusiness")
	 @ApiOperation(value = "上传工商执照")
	    public @ResponseBody Map<String, String> businessUpload(@RequestParam("file") CommonsMultipartFile upload, HttpServletResponse response,
	        HttpServletRequest request, Model model) throws IOException {
		   String url="auth_business";//PropertiesUtil.getString("auth.business.url")
		   String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
		   String realpath = request.getSession().getServletContext().getRealPath("/"+url);
		   String storeName = upload.getOriginalFilename().replace(fileType, "") + "_" + DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSS) + "." + fileType;
		   FileUtils.copyInputStreamToFile(upload.getInputStream(), new File(realpath+File.separator+storeName));
		   Map<String,String> res=new HashMap<String, String>();
		  // res.put("filePath", realpath+File.separator+storeName);
		   res.put("filePath", url+File.separator+storeName);
		   return res;
	 }
	 
	 @RequestMapping(value = "/uploadPositive")
	 @ApiOperation(value = "上传身份证正面")
	    public @ResponseBody Map<String, String> cardPosiUpload(@RequestParam("file") CommonsMultipartFile upload, HttpServletResponse response,
	        HttpServletRequest request, Model model) throws IOException {
		   String url="positive_business";//PropertiesUtil.getString("auth.business.url")
		   String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
		   String realpath = request.getSession().getServletContext().getRealPath("/"+url);
		   String storeName = upload.getOriginalFilename().replace(fileType, "") + "_" + DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSS) + "." + fileType;
		   FileUtils.copyInputStreamToFile(upload.getInputStream(), new File(realpath+File.separator+storeName));
		   Map<String,String> res=new HashMap<String, String>();
		  // res.put("filePath", realpath+File.separator+storeName);
		   res.put("filePath", url+File.separator+storeName);
		   return res;
	 }
	 
	 @RequestMapping(value = "/uploadBack")
	 @ApiOperation(value = "上传身份证背面")
	    public @ResponseBody Map<String, String> cardBackUpload(@RequestParam("file") CommonsMultipartFile upload, HttpServletResponse response,
	        HttpServletRequest request, Model model) throws IOException {
		   String url="back_business";//PropertiesUtil.getString("auth.business.url")
		   String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
		   String realpath = request.getSession().getServletContext().getRealPath("/"+url);
		   String storeName = upload.getOriginalFilename().replace(fileType, "") + "_" + DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSS) + "." + fileType;
		   FileUtils.copyInputStreamToFile(upload.getInputStream(), new File(realpath+File.separator+storeName));
		   Map<String,String> res=new HashMap<String, String>();
		  // res.put("filePath", realpath+File.separator+storeName);
		   res.put("filePath", url+File.separator+storeName);
		   return res;
	 }
	
}
