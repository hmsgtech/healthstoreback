package com.hmsgtech.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单，一个订单对应一个商品一个商户一个报告
 */
@Entity
public class BuyOrder {
	@Id
	@GeneratedValue
	private long id;

	private String userid;

	private Date createTime;

	@Deprecated
	@Column(columnDefinition = "int Default 0 COMMENT '商品详情,废弃字段'")
	private String details;
	private int status;// 1全部, 2 未付款,3 待完成, 4 已完成,5 已退款 ,6 已评价

	@Column(columnDefinition = "int Default 0 COMMENT '订单价格'")
	private int price;

	@Column(columnDefinition = "int Default 0 COMMENT '商品ID'")
	private long goodsId;

	@Column(columnDefinition = "VARCHAR(255)  Default '' COMMENT '商品名称'")
	private String goodsName;

	@Column(columnDefinition = "VARCHAR(255)  Default '' COMMENT '检查报告下载地址'")
	private String reportUrl;

	@Column(columnDefinition = "int Default 0 COMMENT '报告状态:0未上传,1已上传,2已下载'")
	private int reportStatus;

	@Column(columnDefinition = "datetime COMMENT '报告更新时间'")
	private Date reportTime;

	@Column(columnDefinition = "int Default 0 COMMENT '优惠券金额'")
	private int coupon;

	@Column(columnDefinition = "int Default 0 COMMENT '订单类型/商品类型:0仅购买1美年预约型13爱基因收件型'", nullable = false)
	private int companyId;

	/* 体检商品有以下字段 type=1 */
	@Column(columnDefinition = "int Default 0 COMMENT '联系人ID-下单人'")
	private long contactId;

	@Column(columnDefinition = "int Default 0 COMMENT '商户ID:美年预约时设置，爱基因下单时确定'")
	private long storeId;

	@Column(columnDefinition = "int Default 0 COMMENT '预约人ID!=联系人'")
	private long reserveId;

	@Column(columnDefinition = "datetime COMMENT '预约时间'")
	private Date reserveTime;

	/* 爱基因商品有以下字段 type=2 */
	@Column(columnDefinition = "int Default 0 COMMENT '收件人ID'")
	private long recipientId;

	@Column(columnDefinition = "VARCHAR(255) Default '' COMMENT '快递单号,收货型商品不空'")
	private String expressNumber;

	@Column(columnDefinition = "VARCHAR(50) Default '' COMMENT '激活码'")
	private String activation_code;

	@Column(columnDefinition = "datetime COMMENT '到店时间'")
	private Date arrive_time;

	/* 报告名称 */
	@Transient
	private String reportName = "";

	/* 店铺名称 */
	@Transient
	private String storeName = "";

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(long recipientId) {
		this.recipientId = recipientId;
	}

	public long getReserveId() {
		return reserveId;
	}

	public void setReserveId(long reserveId) {
		this.reserveId = reserveId;
	}

	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public int getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(Date reserveTime) {
		this.reserveTime = reserveTime;
	}

	public String getActivation_code() {
		return activation_code;
	}

	public void setActivation_code(String activation_code) {
		this.activation_code = activation_code;
	}

	public Date getArrive_time() {
		return arrive_time;
	}

	public void setArrive_time(Date arrive_time) {
		this.arrive_time = arrive_time;
	}

}
