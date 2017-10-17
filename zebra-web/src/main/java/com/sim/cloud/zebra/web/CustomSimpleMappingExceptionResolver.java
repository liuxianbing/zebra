package com.sim.cloud.zebra.web;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.sim.cloud.zebra.common.util.JackSonUtil;

public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

  @Override
  protected ModelAndView doResolveException(HttpServletRequest request,
      HttpServletResponse response, Object handler, Exception ex) {

    String viewName = determineViewName(ex, request);
    if (viewName != null) {// JSP格式返回
      if (!(request.getHeader("accept").indexOf("application/json") > -1
          || (request.getHeader("X-Requested-With") != null
              && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
        // 如果不是异步请求
        // Apply HTTP status code for error views, if specified.
        // Only apply it if we're processing a top-level request.
        Integer statusCode = determineStatusCode(request, viewName);
        if (statusCode != null) {
          applyStatusCodeIfPossible(request, response, statusCode);
        }
        return getModelAndView(viewName, ex, request);
      } else {// JSON格式返回
    	  Map<String,String> res=new HashMap<>();
        try {
        	if(ex.getMessage().contains("Duplicate entry")){
        		int be=ex.getMessage().lastIndexOf("for key");
        		int en=ex.getMessage().lastIndexOf("_UNIQUE");
        		res.put("msg", "字段"+ex.getMessage().substring(be+9,en)+"取值已经存在");
        	}else if(ex.getMessage().length()>=50){
        		res.put("msg", "系统走神了,请稍候再试.");
        	}else{
        		res.put("msg", ex.getMessage());
        	}
          PrintWriter writer = response.getWriter();
          writer.write(JackSonUtil.getObjectMapper().writeValueAsString(res));
          writer.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }
    } else {
      return null;
    }
  }

}
