package com.plmm.common.utils;

import com.plmm.common.base.Result;
import com.plmm.common.base.ResultEnum;

public class ResultUtil {

	public static Result<Object> error(String returnCode, String returnMsg) {
		Result<Object> result = new Result<Object>();
		result.setReturnCode(returnCode);
		result.setReturnMsg(returnMsg);
		return result;
	}

	public static Result<Object> error(ResultEnum resultEnum) {
		Result<Object> result = new Result<Object>();
		result.setReturnCode(resultEnum.getReturnCode());
		result.setReturnMsg(resultEnum.getReturnMsg());
		return result;
	}

	public static Result<Object> sucess(Object object) {
		Result<Object> result = new Result<Object>();
		result.setReturnCode(ResultEnum.SUCCESS.getReturnCode());
		result.setReturnMsg(ResultEnum.SUCCESS.getReturnMsg());
		result.setData(object);
		return result;
	}

	public static Result<Object> error(String message) {
		Result<Object> result = new Result<Object>();
		result.setReturnCode(ResultEnum.ST999999.getReturnCode());
		result.setReturnMsg(ResultEnum.ST999999.getReturnMsg());
		result.setData(message);
		return result;
	}

}
