package com.hmsgtech.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 晓丰 on 2016/5/10.
 * 分类（套餐专场。。。）
 */
@Entity
public class Category {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    
    private String description;
    
    private String img;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	

	
    
    
}
