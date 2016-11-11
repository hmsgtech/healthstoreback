package com.hmsgtech.domain.store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 晓丰 on 2016/5/10.
 * 总店
 */
@Entity
public class Company {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    
    private int type; //1代表有分店，0代表基因套餐

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    
}
