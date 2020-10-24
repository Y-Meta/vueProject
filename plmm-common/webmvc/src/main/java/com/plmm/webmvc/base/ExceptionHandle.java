package com.plmm.webmvc.base;

import com.plmm.common.base.Result;
import com.plmm.common.base.ResultEnum;
import com.plmm.common.exception.PlmmSysException;
import com.plmm.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionHandle {
	
	private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);	
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result handler(Exception e){
		if(e instanceof PlmmSysException){
			PlmmSysException ex = (PlmmSysException)e;
			return ResultUtil.error(ex.getReturnCode(), ex.getMessage());
		}else{
			logger.error("系统异常{}",e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	}
}

