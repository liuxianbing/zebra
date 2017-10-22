package com.sim.cloud.zebra.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量表
 * 
 * @author liuxianbing
 * @version $Id: Constants.java, v 0.1 2014-2-28 上午11:18:28 liuxianbing Exp $
 */
public interface Constants {
	/**
	 * 异常信息统一头信息<br>
	 * 非常遗憾的通知您,程序发生了异常
	 */
	static final String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
	/** 缓存键值 */
	static final Map<Class<?>, String> cacheKeyMap = InstanceUtil.newHashMap();
	/** 操作名称 */
	static final String OPERATION_NAME = "OPERATION_NAME";
	/** 客户端语言 */
	static final String USERLANGUAGE = "userLanguage";
	/** 客户端主题 */
	static final String WEBTHEME = "webTheme";
	/** 当前用户 */
	static final String CURRENT_USER = "CURRENT_USER";
	/** 客户端信息 */
	static final String USER_AGENT = "USER-AGENT";
	/** 客户端信息 */
	static final String USER_IP = "USER_IP";
	/** 上次请求地址 */
	static final String PREREQUEST = "PREREQUEST";
	/** 上次请求时间 */
	static final String PREREQUEST_TIME = "PREREQUEST_TIME";
	/** 登录地址 */
	static final String LOGIN_URL = "/login.html";
	/** 非法请求次数 */
	static final String MALICIOUS_REQUEST_TIMES = "MALICIOUS_REQUEST_TIMES";
	/** 缓存命名空间 */
	static final String CACHE_NAMESPACE = "iBase4J:";
	/** 在线用户数量 */
	static final String ALLUSER_NUMBER = "SYSTEM:" + CACHE_NAMESPACE + "ALLUSER_NUMBER";
	/** TOKEN */
	static final String TOKEN_KEY = CACHE_NAMESPACE + "TOKEN_KEY";

	/** 日志表状态 */
	interface JOBSTATE {
		/**
		 * 日志表状态，初始状态，插入
		 */
		static final String INIT_STATS = "I";
		/**
		 * 日志表状态，成功
		 */
		static final String SUCCESS_STATS = "S";
		/**
		 * 日志表状态，失败
		 */
		static final String ERROR_STATS = "E";
		/**
		 * 日志表状态，未执行
		 */
		static final String UN_STATS = "N";
	}
	
	static final List<String> CARRIER=Arrays.asList("中国联通".split(","));
	
	static final List<String> TERM_LIST=Arrays.asList("1,2,3,4,5,6,7,8,9,10,11,12,18,24".split(","));
	
	static final Map<String, Integer> statusIntegerMap = JasperUtils.getStatusIntegerMap();
	static final Map<String, String> statusStringMap = JasperUtils.getStatusStringMap();
}