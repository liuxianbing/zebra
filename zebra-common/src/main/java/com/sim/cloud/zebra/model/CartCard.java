package com.sim.cloud.zebra.model;
/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月3日 下午4:12:03 
* 类说明 
*/
public class CartCard extends BaseModel{
	
	public static final Integer INCART=0;
	public static final Integer ORDERED=1;

	private Long uid;
	private Long cid;
	private Long orderId;
	private Float price;
	private Integer flow;
	private Integer num;
	private Integer term;
	private Long packageId;
	private Integer operator;
	private Integer type;//1:共享
	private String name;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getFlow() {
		return flow;
	}
	public void setFlow(Integer flow) {
		this.flow = flow;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	
	
}
