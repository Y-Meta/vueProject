package com.plmm.common.base;

import java.io.Serializable;

public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String returnCode;
	private String returnMsg;
	private T data;
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString(){
		if(data!=null) {
			return "returnCode:" + returnCode + ",returnMsg:" + returnMsg + ",data:" + data;
		}else {
			return "returnCode:" + returnCode + ",returnMsg:" + returnMsg;
		}
	}

	public Result(String returnCode, String returnMsg, T data) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
		this.data = data;
	}

	public Result() {
	}

	public Result(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public static Result successResult(Object object){
		return new Result(ResultEnum.SUCCESS.getReturnCode(),ResultEnum.SUCCESS.getReturnMsg(),object);
	}

	public  static Result successResult(){
		return new Result(ResultEnum.SUCCESS.getReturnCode(),ResultEnum.SUCCESS.getReturnMsg());
	}


	public static Result failResult(ResultEnum resultEnum){
		return new Result(resultEnum.getReturnCode(),resultEnum.getReturnMsg());
	}

}

