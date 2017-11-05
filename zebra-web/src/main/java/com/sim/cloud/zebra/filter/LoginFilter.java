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

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月18日 下午3:58:50 
* 类说明 
*/
public class LoginFilter extends HttpServlet implements Filter{

	private static final long serialVersionUID = 8258422474769843246L;
	  protected static Logger LOG = LoggerFactory.getLogger(LoginFilter.class);
	  private String encoding = "UTF-8";

	  public LoginFilter() {}

	  public void destroy() {
	    encoding = null;
	  }

	  @Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	      throws IOException, ServletException {
	    HttpServletResponse resp = (HttpServletResponse) response;
	    HttpServletRequest req = (HttpServletRequest) request;
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    String path = req.getSession().getServletContext().getContextPath();
	    HttpSession session = req.getSession();
	    String url = req.getRequestURI();
	    System.out.println(url);
	    if (url.endsWith(".ico")  || url.contains("/druid") || url.contains("/assets/")
	            || url.endsWith(".png") || url.endsWith("/login")|| url.contains("/swagger")  ) {
	          chain.doFilter(req, resp);
	          return;
	        }
	    if( WebUtil.getCurrentUser(req)== null){
	    	 resp.sendRedirect(path + "/login");
	         return; 
	    }
	    chain.doFilter(req, resp);
	  }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
}
