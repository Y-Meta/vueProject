package com.plmm.common.exception;

import com.plmm.common.base.ResultEnum;

/**
 * Description: 系统异常处理类
 */
public class PlmmSysException extends RuntimeException {

    private static final long serialVersionUID = -395519744859162811L;

    private String returnCode;
    
    public PlmmSysException(Exception e) {
        super(e);
        this.returnCode=ResultEnum.ST999999.getReturnCode();
    }

    public PlmmSysException(String msg) {
        super(msg);
        this.returnCode=ResultEnum.ST999999.getReturnCode();
    }

    public PlmmSysException(String msg, Throwable ex) {
        super(msg, ex);
        this.returnCode=ResultEnum.ST999999.getReturnCode();
    }
    
    public PlmmSysException(ResultEnum resultEnum) {
        super(resultEnum.getReturnMsg());
        this.returnCode=resultEnum.getReturnCode();
    }

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
}

