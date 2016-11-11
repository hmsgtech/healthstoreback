package com.hmsgtech.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * Created by 晓丰 on 2016/5/10. 首页显示
 */
@Entity
public class FrontPage {
	@Id
	private long id;

	private String imgs;

	private String categories;

	private String goodsIds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}


}
