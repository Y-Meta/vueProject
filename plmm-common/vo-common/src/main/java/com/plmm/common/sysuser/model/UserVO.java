package com.plmm.common.sysuser.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @ClassName: UserVO
 * @Description: TODO
 * @autor: admin
 * @date: 2020-09-11 09:41:05
 * @version: V1.0
 */
 
@ApiModel
public class UserVO implements Serializable{
    private static final long serialVersionUID = 1L;
			@ApiModelProperty(value = "", position = 0)
	private String sex;
			@ApiModelProperty(value = "", position = 1)
	private String name;
			@ApiModelProperty(value = "", position = 2)
	private String remark;
			@ApiModelProperty(value = "", position = 3)
	private String id;
			
		public void setSex (String sex) {
		this.sex = sex;
	}
	public String getSex() {
		return this.sex;
	}
		public void setName (String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
		public void setRemark (String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return this.remark;
	}
		public void setId (String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
		
	
}
