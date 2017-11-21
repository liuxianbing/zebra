package com.sim.cloud.zebra.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import com.sim.cloud.zebra.common.util.PropertiesFileReader;
import com.sim.cloud.zebra.model.Company;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.SysAddress;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.CompanyService;
import com.sim.cloud.zebra.service.PackageService;
import com.sim.cloud.zebra.service.SysUserService;
import com.sim.cloud.zebra.service.TariffPlanService;

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
	
	@Autowired
	private PackageService packageUserService;
	
	@Autowired
	private TariffPlanService tariffPlanService;
	
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
		model.addAttribute("uid", getCurrUser().getId());
		model.addAttribute("company", company);
		
		return "system/user_info";
	}
	
	  @ApiOperation(value = "重置密码")
	  @RequestMapping(value = "reset", produces = {"application/json"}, method = RequestMethod.POST)
	  public @ResponseBody Map<String, String> resetPasswd(@RequestParam Long uid){
		  SysUser user=sysUserService.selectById(uid);
		  if(getCurrUser().getRole()==SysUser.ROLE_MANAGER && getCurrUser().getId().longValue()!=user.getCreateUserId().longValue()){
			  throw new RuntimeException();
		  }
		  SysUser su=new SysUser();
		  su.setId(uid);
		  su.setPasswd(DigestUtils.md5Hex(user.getPhone().substring(6)+"_sim"));
		  sysUserService.updateById(su);
		  return SUCCESS;
	  }
	  
	  @ApiOperation(value = "删除用户")
	  @RequestMapping(value = "del", produces = {"application/json"}, method = RequestMethod.POST)
	  public @ResponseBody Map<String, String> delUser(@RequestParam Long uid){
		  SysUser user=sysUserService.selectById(uid);
		  if(getCurrUser().getRole()==SysUser.ROLE_MANAGER && getCurrUser().getId().longValue()!=user.getCreateUserId().longValue()){
			  throw new RuntimeException();
		  }
		  SysUser su=new SysUser();
		  su.setId(uid);
		  su.setCid(0l);
		  su.setRole(100);//删除
		  su.setPhone(user.getPhone()+"-"+DateUtil.getDateTime());
		  su.setStatus(SysUser.disable);
		  sysUserService.updateById(su);
		  return SUCCESS;
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
		SysAddress sa=new SysAddress();
		if(user.getId()==null || user.getId()==0l){
			sa=new SysAddress();
			sa.setLocation(user.getAddress());
			sa.setUserName(user.getUserName());
			sa.setPhone(user.getPhone());
			user.setCreateTime(DateUtil.getDateTime());
			user.setCreateUserId(getCurrUser().getId());
			user.setPasswd(DigestUtils.md5Hex(user.getPhone().substring(6)+"_sim"));
		}
		if(checkIfManager()){
			user.setRole(SysUser.ROLE_MANAGER);
		}else{
			user.setRole(SysUser.ROLE_USER);
			user.setCid(getCurrUser().getCid());
			user.setAuth(getCurrUser().getAuth());
		}
		sysUserService.insertOrUpdate(user);
		if(checkIfManager() && sa!=null){
			sa.setUid(user.getId());
			sysAddressService.insert(sa);
		}
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
		Map<String,Object> map=extractFromRequest();
		if(checkIfManager()){
			map.put("role", SysUser.ROLE_MANAGER);
		}else{
			map.put("role", SysUser.ROLE_USER);
		}
		Page<SysUser> page=sysUserService.query(map);
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
	public @ResponseBody Company auth(@RequestBody Company company) {
		companyService.saveAuthInfo(company);
		if(!checkIfManager()){
			getCurrUser().setCid(company.getId());
		}
		return company;
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
		 String path=PropertiesFileReader.getPropertiesVal("zebra.properties", "auth.pic.url");
		 String subPath=path.substring(path.lastIndexOf("/")+1);
		 checkDir(path);
		  // String url="auth_business";//PropertiesUtil.getString("auth.business.url")
		   String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
		 //  String realpath = request.getSession().getServletContext().getRealPath("/"+url);
		   String storeName = upload.getOriginalFilename().replace(fileType, "") + "_" + DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSS) + "." + fileType;
		   FileUtils.copyInputStreamToFile(upload.getInputStream(), new File(path+File.separator+storeName));
		   Map<String,String> res=new HashMap<String, String>();
		  // res.put("filePath", realpath+File.separator+storeName);
		   res.put("filePath", "images"+File.separator+subPath+File.separator+storeName);
		   return res;
	 }
	 
	 @RequestMapping(value = "/uploadPositive")
	 @ApiOperation(value = "上传身份证正面")
	    public @ResponseBody Map<String, String> cardPosiUpload(@RequestParam("file") CommonsMultipartFile upload, HttpServletResponse response,
	        HttpServletRequest request, Model model) throws IOException {
		 String path=PropertiesFileReader.getPropertiesVal("zebra.properties", "front.pic.url");
		 String subPath=path.substring(path.lastIndexOf("/")+1);
		 checkDir(path);
		   String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
		   String storeName = upload.getOriginalFilename().replace(fileType, "") + "_" + DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSS) + "." + fileType;
		   FileUtils.copyInputStreamToFile(upload.getInputStream(), new File(path+File.separator+storeName));
		   Map<String,String> res=new HashMap<String, String>();
		   res.put("filePath", "images"+File.separator+subPath+File.separator+storeName);
		   return res;
	 }
	 
	 @RequestMapping(value = "/uploadBack")
	 @ApiOperation(value = "上传身份证背面")
	    public @ResponseBody Map<String, String> cardBackUpload(@RequestParam("file") CommonsMultipartFile upload, HttpServletResponse response,
	        HttpServletRequest request, Model model) throws IOException {
		 String path=PropertiesFileReader.getPropertiesVal("zebra.properties", "back.pic.url");
		 String subPath=path.substring(path.lastIndexOf("/")+1);
		 checkDir(path);
		   String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
		   String storeName = upload.getOriginalFilename().replace(fileType, "") + "_" + DateUtil.getDateTime(DATE_PATTERN.YYYYMMDDHHMMSS) + "." + fileType;
		   FileUtils.copyInputStreamToFile(upload.getInputStream(), new File(path+File.separator+storeName));
		   Map<String,String> res=new HashMap<String, String>();
		   res.put("filePath", "images"+File.separator+subPath+File.separator+storeName);
		   return res;
	 }
	 

	 private void checkDir(String path){
		 File file = new File(path);
		//如果路径不存在，新建
		if(!file.exists()&&!file.isDirectory()) {
		    file.mkdirs();
		}
	 }
	 
	 /**
		 * 列表页面
		 * @param model
		 * @return
		 */
		@ApiOperation(value = "用户套餐列表页面")
		@RequestMapping(value = "/packlist", method = RequestMethod.GET)
		public String toPackList(Model model,@RequestParam Long id) {
			if(getCurrUser().getRole()==SysUser.ROLE_MANAGER && id!=getCurrUser().getId()){
				return "/error/error";
			}
			model.addAttribute("id", id);
			model.addAttribute("user", sysUserService.selectById(id));
			model.addAttribute("planList", tariffPlanService.selectList(null));
			return "tariff/user_package";
		}
		
		/**
		 * 列表页面
		 * @param model
		 * @return
		 */
		@ApiOperation(value = "用户套餐列表请求")
		@RequestMapping(value = "packlist", method = RequestMethod.POST, produces = { "application/json" })
		public @ResponseBody DataTableParameter<Package> packList() {
			if(getCurrUser().getRole()==SysUser.ROLE_MANAGER && 
					Long.parseLong(request.getParameter("uid"))!=getCurrUser().getId()){
				throw new RuntimeException("无权限");
			}
			Page<Package> page=packageUserService.selectPage(extractFromRequest(),Package.class);
			return new DataTableParameter<Package>(page.getTotal(),
					request.getParameter("sEcho"),page.getRecords());
		}
	
}
