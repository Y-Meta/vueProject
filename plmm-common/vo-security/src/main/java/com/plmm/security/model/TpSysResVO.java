package com.plmm.security.model;
//package com.plmm.security.model;
//
//import com.plmm.security.core.authority.GrantedAuthority;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//
//@ApiModel(value = "资源菜单实体")
//public class TpSysResVO implements GrantedAuthority {
//	private static final long serialVersionUID = 4184373305252713516L;
//	
//	@ApiModelProperty(value = "路径id", position = 0)
//	private int id;
//	
//	@ApiModelProperty(value = "路径名称", position = 1)
//	private String urlName;
//	
//	@ApiModelProperty(value = "是否公共资源", position = 2)
//	private String isPublic;
//	
//	@ApiModelProperty(value = "启用标志", position = 3)
//	private String delflag;
//	
//	@ApiModelProperty(value = "路径资源", position = 4)
//	private String url;
//	
//	@ApiModelProperty(value = "路径描述", position = 5)
//	private String urlDesc;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//	
//	public String getUrlId() {
//		return String.valueOf(id);
//	}
//
//	public String getUrlName() {
//		return this.urlName;
//	}
//
//	public void setUrlName(String urlName) {
//		this.urlName = urlName;
//	}
//
//	public String getIsPublic() {
//		return this.isPublic;
//	}
//
//	public void setIsPublic(String isPublic) {
//		this.isPublic = isPublic;
//	}
//
//	public String getDelFlag() {
//		return this.delflag;
//	}
//
//	public void setDelFlag(String delflag) {
//		this.delflag = delflag;
//	}
//
//	public String getUrl() {
//		return this.url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getUrlDesc() {
//		return this.urlDesc;
//	}
//
//	public void setUrlDesc(String urlDesc) {
//		this.urlDesc = urlDesc;
//	}
//
//	public String getAuthority() {
//		return String.valueOf(this.id);
//	}
//}