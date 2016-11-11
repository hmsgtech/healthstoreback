package com.hmsgtech.domain.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activity_goods")
public class ActivityGoods {
	@Id
	@GeneratedValue
	private int id;

	@Column(name = "good_id", columnDefinition = "int COMMENT '商品id' ")
	private long goodId;

	@Column(name = "activity_id", columnDefinition = "int COMMENT '活动id' ")
	private int activityId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getGoodId() {
		return goodId;
	}

	public void setGoodId(long goodId) {
		this.goodId = goodId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

}
