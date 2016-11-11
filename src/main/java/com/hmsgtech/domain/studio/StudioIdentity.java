package com.hmsgtech.domain.studio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 技师身份
 * 
 * @author lujq
 *
 */
@Entity
@Table(name = "t_studio_identity")
public class StudioIdentity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar(50)  COMMENT '员工身份'")
	private String name;

	@Column(name = "authority", columnDefinition = "varchar(50) default 'TECH'  COMMENT '员工权限:TECH,MANAGE'")
	private String authority;

	@Column(name = "status", columnDefinition = "int(1) COMMENT '状态：1正常0冻结'")
	private Integer status;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
