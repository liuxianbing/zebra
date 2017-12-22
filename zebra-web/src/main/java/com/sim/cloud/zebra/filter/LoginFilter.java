package com.sim.cloud.zebra.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sim.cloud.zebra.common.util.WebUtil;
import com.sim.cloud.zebra.model.SysUser;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月18日 下午3:58:50 
* 类说明 
*/
public class LoginFilter extends HttpServlet implements Filter{

	private static final long serialVersionUID = 8258422474769843246L;
	  protected static Logger LOG = LoggerFactory.getLogger(LoginFilter.class);
	  
	  protected UrlAuthCheck managerCheck;
	  
	  protected UrlAuthCheck userCheck;

	  public LoginFilter() {}

	  public void destroy() {
	  }

	  @Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	      throws IOException, ServletException {
	    HttpServletResponse resp = (HttpServletResponse) response;
	    HttpServletRequest req = (HttpServletRequest) request;
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    String path = req.getSession().getServletContext().getContextPath();
	    String url = req.getRequestURI();
	    if (url.endsWith(".ico")  || url.contains("/druid") || url.contains("/assets/") || url.endsWith("/logout")
	            || url.endsWith(".png") || url.endsWith("/login") || url.endsWith("/error") ) {
	          chain.doFilter(req, resp);
	          return;
	        }
	    SysUser user=WebUtil.getCurrentUser(req);
	    if(user== null){
	    	 resp.sendRedirect(path + "/login");
	         return; 
	    }
	    if( user.getRole()==SysUser.ROLE_USER && !userCheck.check(url)){
	    	 resp.sendRedirect(path + "/error");
	         return; 
	    }
	    if( user.getRole()==SysUser.ROLE_MANAGER && !managerCheck.check(url)){
	    	 resp.sendRedirect(path + "/error");
	         return; 
	    }
	    chain.doFilter(req, resp);
	  }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		managerCheck = new UrlAuthCheck();
		managerCheck.setPaths(filterConfig.getInitParameter("managerCheck"));
		userCheck = new UrlAuthCheck();
		userCheck.setPaths(filterConfig.getInitParameter("userCheck"));
	}
}
