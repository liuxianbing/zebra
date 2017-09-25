package com.sim.cloud.zebra.exception;

import com.sim.cloud.zebra.common.util.HttpCode;

/**
 * 
 * @author liuxianbing
 * @version 2017年3月24日 下午9:30:10
 */
@SuppressWarnings("serial")
public class InstanceException extends BaseException {
	public InstanceException() {
		super();
	}

	public InstanceException(Throwable t) {
		super(t);
	}

	protected HttpCode getHttpCode() {
		return HttpCode.INTERNAL_SERVER_ERROR;
	}
}
