package com.sim.cloud.zebra.common.util;

import java.util.List;

/**
 * @ClassName: DataTableParameter
 * @Description:
 * @author xianbing.liu@renren-inc.com
 * @date 2013-4-27 下午06:03:56
 * 
 */
public class DataTableParameter<T> {

	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private String sEcho;
	private List<T> aaData;

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	public DataTableParameter(int totalRecords, String echo, List<T> d) {
		this.iTotalRecords = totalRecords;
		this.iTotalDisplayRecords = totalRecords;
		this.sEcho = echo;
		this.aaData = d;
	}

	public DataTableParameter(int totalRecords, List<T> d) {
		this.iTotalRecords = totalRecords;
		this.iTotalDisplayRecords = totalRecords;
		this.aaData = d;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

}
