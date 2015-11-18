package com.camut.pageModel;

import java.util.Date;
import java.util.List;

public class PageOrderHeader {
	
	//Consumer类中的属性
	private String consumerName; //订餐人姓名 firstName+“ ”+lastName
	private String loginname;//会员登录名
	private String phone;// 联系电话
	private String memo;// 备注
	//OrderHeader类中属性
	private int id;//订单头id
	private int orderType;// 订单种类，1:外送 2：自取 3：到店就餐; 1:delivery 2:pick up 3dine-in
	private Date createdate;// 下单时间
	private double total;// 菜的总金额
	private int status;// 订单状态  0：处于删除状态 1:已下单未付款 2：已下单已付款 3：店家已接单 4:店家拒绝接单 5：顾客要求退单 6：已退单未退款 7：已退单已退款
	private Date orderDate;// 就餐时间
	private String strOrderDate;// 字符串就餐时间
	private int number;// 就餐人数
	private String address;// 地址
	private String zipcode;// 邮编
	private String rejection;// 拒绝理由 rejection reason
	private double tip;// 小费
	private double logistics;// 送餐费
	private double gst;// 联邦税
	private double pst;// 省税
	private String orderNo;//订单号
	private double tax;
	private double amount;//全部总金额
	private List<PageOrderItem> pageOrderItems;// 与订单详情一对多关系;1OrderHeader: manyOrderItem
	private int itemSize;//记录订单条目的数量
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public List<PageOrderItem> getPageOrderItems() {
		return pageOrderItems;
	}
	public void setPageOrderItems(List<PageOrderItem> pageOrderItems) {
		this.pageOrderItems = pageOrderItems;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getRejection() {
		return rejection;
	}
	public void setRejection(String rejection) {
		this.rejection = rejection;
	}
	public double getTip() {
		return tip;
	}
	public void setTip(double tip) {
		this.tip = tip;
	}
	public double getLogistics() {
		return logistics;
	}
	public void setLogistics(double logistics) {
		this.logistics = logistics;
	}
	public double getGst() {
		return gst;
	}
	public void setGst(double gst) {
		this.gst = gst;
	}
	public double getPst() {
		return pst;
	}
	public void setPst(double pst) {
		this.pst = pst;
	}
	/*public Set<PageOrderItem> getPageOrderItems() {
		return pageOrderItems;
	}
	public void setPageOrderItems(Set<PageOrderItem> pageOrderItems) {
		this.pageOrderItems = pageOrderItems;
	}*/
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStrOrderDate() {
		return strOrderDate;
	}
	public void setStrOrderDate(String strOrderDate) {
		this.strOrderDate = strOrderDate;
	}
	public int getItemSize() {
		return itemSize;
	}
	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}
	
	
	
	
	
	
}
