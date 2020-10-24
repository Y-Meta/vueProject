package com.plmm.common.customer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * @ClassName: 
 * @Description: TODO
 * @autor: admin
 * @date: 2020-03-25 10:59:10
 * @version: V1.0
 */

@ApiModel("角色表实体类")
public class TpSysRoleVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "系统编码", position = 0)
	private String sysCode;
	@ApiModelProperty(value = "角色编码", position = 1)
	private String code;
	@ApiModelProperty(value = "创建时间", position = 2)
	private String createTime;
	@ApiModelProperty(value = "角色名称", position = 3)
	private String roleName;
	@ApiModelProperty(value = "更新人员", position = 4)
	private String updateUser;
	@ApiModelProperty(value = "创建人员", position = 5)
	private String createUser;
	@ApiModelProperty(value = "更新时间", position = 6)
	private String updateTime;
	@ApiModelProperty(value = "id", position = 7)
	private String id;
	@ApiModelProperty(value = "启用状态(1.启用；2.停用)", position = 8)
	private String state;
	@ApiModelProperty(value = "删除标记", position = 9)
	private String delFlag;

	public void setSysCode (String sysCode) {
		this.sysCode = sysCode;
	}
	public String getSysCode() {
		return this.sysCode;
	}
	public void setCode (String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
	public void setCreateTime (String createTime) {
		this.createTime = createTime;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	public void setRoleName (String roleName) {
		this.roleName = roleName;
	}
	public String getRoleName() {
		return this.roleName;
	}
	public void setUpdateUser (String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser;
	}
	public void setCreateUser (String createUser) {
		this.createUser = createUser;
	}
	public String getCreateUser() {
		return this.createUser;
	}
	public void setUpdateTime (String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime;
	}
	public void setId (String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	public void setState (String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
