package com.plmm.common.exception;

import com.plmm.common.base.ResultEnum;

/**
 * Description: 数据库异常处理类
 */
public class PlmmSqlException extends RuntimeException {

    private static final long serialVersionUID = -395519744859162811L;

    private String returnCode;
    
    public PlmmSqlException(Exception e) {
        super(e);
        this.returnCode=ResultEnum.ST000001.getReturnCode();
    }

    public PlmmSqlException(String msg) {
        super(msg);
        this.returnCode=ResultEnum.ST000001.getReturnCode();
    }

    public PlmmSqlException(String msg, Throwable ex) {
        super(msg, ex);
        this.returnCode=ResultEnum.ST000001.getReturnCode();
    }
    
    public PlmmSqlException(ResultEnum resultEnum) {
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

