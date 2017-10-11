package com.sim.cloud.zebra.interceptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;

import com.sim.cloud.zebra.common.util.Constants;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.ExceptionUtil;
import com.sim.cloud.zebra.common.util.JackSonUtil;
import com.sim.cloud.zebra.common.util.WebUtil;
import com.sim.cloud.zebra.model.SysEvent;
import com.sim.cloud.zebra.model.SysUser;

import io.swagger.annotations.ApiOperation;

/**
 * 日志拦截器
 * 
 * @author liuxianbing
 * @version 2017年6月14日 下午6:18:46
 */
public class EventInterceptor extends BaseInterceptor {
	protected static Logger logger = LogManager.getLogger();

	private final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	private ExecutorService executorService = Executors.newCachedThreadPool();

	//操作日志服务类
//	@Autowired
//	private ISysEventService sysEventService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 开始时间（该数据只有当前请求的线程可见）
		startTimeThreadLocal.set(System.currentTimeMillis());
		return super.preHandle(request, response, handler);
	}

	public void afterCompletion(final HttpServletRequest request, HttpServletResponse response, Object handler,
			final Exception ex) throws Exception {
		final Long startTime = startTimeThreadLocal.get();
		final Long endTime = System.currentTimeMillis();
		String path = request.getServletPath();
		// 保存日志
		if (handler instanceof HandlerMethod) {
			SysUser user = WebUtil.getCurrentUser(request);
			String userAgent = (String) request.getSession().getAttribute(Constants.USER_AGENT);
			String clientIp = (String) request.getSession().getAttribute(Constants.USER_IP);
			if (!path.contains("/read/") && !path.contains("/unauthorized") && !path.contains("/forbidden")) {
				final SysEvent record = new SysEvent();
				record.setMethod(request.getMethod());
				record.setRequestUri(request.getServletPath());
				record.setClientHost(clientIp);
				record.setUserAgent(userAgent);
				if (path.contains("/upload/")) {
					record.setParameters("");
				} else {
					record.setParameters(JackSonUtil.getObjectMapper().writeValueAsString(request.getParameterMap()));
				}
				record.setStatus(response.getStatus());
				if (user != null) {
					record.setUserName(user.getUserName()+"-"+user.getAccount());
				}
				final String msg = (String) request.getAttribute("msg");
				try {
					HandlerMethod handlerMethod = (HandlerMethod) handler;
					ApiOperation apiOperation = handlerMethod.getMethod().getAnnotation(ApiOperation.class);
					record.setTitle(apiOperation.value());
				} catch (Exception e) {
					logger.error("", e);
				}
				executorService.submit(new Runnable() {
					public void run() {
						try { // 保存操作
							if (StringUtils.isNotBlank(msg)) {
								record.setRemark(msg);
							} else {
								record.setRemark(ExceptionUtil.getStackTraceAsString(ex));
							}
							//存储操作日志
							//sysEventService.update(record);
						} catch (Exception e) {
							logger.error("Save event log cause error :", e);
						}
					}
				});
			} else if (path.contains("/unauthorized")) {
				logger.warn("用户[{}]没有登录", clientIp + "@" + userAgent);
			} else if (path.contains("/forbidden")) {
				//logger.warn("用户[{}]没有权限", WebUtil.getCurrentUser() + "@" + clientIp + "@" + userAgent);
			} else {
				logger.info(user.getUserName() + "@" + path + "@" + clientIp + userAgent);
			}
		}
		// 内存信息
		if (logger.isDebugEnabled()) {
			String message = "开始时间: {}; 结束时间: {}; 耗时: {}s; URI: {}; ";
			// 最大内存: {}M; 已分配内存: {}M; 已分配内存中的剩余空间: {}M;
			// 最大可用内存:
			// {}M.
			// long total =
			// Runtime.getRuntime().totalMemory() /
			// 1024 / 1024;
			// long max = Runtime.getRuntime().maxMemory() /
			// 1024 / 1024;
			// long free = Runtime.getRuntime().freeMemory()
			// /
			// 1024 / 1024;
			// , max, total, free, max - total + free
			logger.debug(message, DateUtil.format(startTime, "HH:mm:ss.SSS"), DateUtil.format(endTime, "HH:mm:ss.SSS"),
					(endTime - startTime) / 1000.00, path);
		}
		super.afterCompletion(request, response, handler, ex);
	}
}
