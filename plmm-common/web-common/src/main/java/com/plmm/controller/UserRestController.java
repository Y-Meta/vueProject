package com.plmm.controller;

import java.util.List;
import java.util.Map;

import com.plmm.common.base.Result;
import com.plmm.common.base.ResultEnum;
import com.plmm.common.exception.PlmmSqlException;
import com.plmm.common.sysuser.model.UserVO;
import com.plmm.common.utils.ResultUtil;
import com.plmm.security.core.context.SecurityContextHolder;
import com.plmm.webmvc.aspect.annotation.AvoidRepeatableCommit;
import com.plmm.webmvc.config.ApiJsonObject;
import com.plmm.webmvc.config.ApiJsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plmm.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
/**
 * 
 * @ClassName: UserRestController 
 * @Description: 提供UserVO类的restful接口调用  
 * @autor: admin
 * @date: 2020-09-11 09:52:53
 * @version: V1.0
 */
@Api
@ApiSort
@RestController
@RequestMapping(value = "user")
public class UserRestController {

	private static Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	UserService userService;
	
	/**
	 * @Description: 获取User列表,带分页数据
	 * @param param
	 * @return Object[] 数组第一位数为总记录数，第二位为数据集
	 */
	@ApiOperation(value = "获取User列表,带分页数据")
	@PostMapping(value="/getUser")
	public Result getUser(@RequestBody Map<String,Object> param) {
		try {
			Map<String, Object> map = (Map<String, Object>)param.get("map");
			Integer offset = Integer.parseInt(param.get("offset").toString());
			Integer pageSize = Integer.parseInt(param.get("pagesize").toString());
			Object[] objArray = userService.getUser(map, offset, pageSize);
			return ResultUtil.sucess(objArray);
		} catch (PlmmSqlException e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST000001);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	}
	
	/**
	 * 
	 * @Description: 根据主键查询VO
	 * @param code
	 * @return UserVO
	 */
	@ApiOperation(value = "根据主键查询VO")
	@PostMapping(value="/selectOneUserByPk/{code}")
	public Result selectOneUser(@PathVariable String code) {
		try {
			UserVO vo = userService.selectOneUser(code);
			return ResultUtil.sucess(vo);
		} catch (PlmmSqlException e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST000001);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	} 
	
	/**
	 * 
	 * @Description: 根据map参数查询VO
	 * @param param  Map类型的参数
	 * @return UserVO
	 */
	@ApiOperation(value = "根据map参数查询VO")
	@PostMapping(value="/selectOneUserByMap")
	public Result selectOneUser(@RequestBody Map<String,Object> param){
		try {
			UserVO vo = userService.selectOneUser(param);
			return ResultUtil.sucess(vo); 
		} catch (PlmmSqlException e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST000001);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	} 
	
	/**
	 * 
	 * @Description: 根据map查询UserVO数据集
	 * @param param map对象
	 * @return List<UserVO>
	 */
	@ApiOperation(value = "根据map查询UserVO数据集")
	@PostMapping(value="/selectUserListByMap")
	public Result getUserList(@RequestBody Map<String, Object> param) {
		try {
			List<UserVO> vo = userService.getUserList(param);
			return ResultUtil.sucess(vo);
		} catch (PlmmSqlException e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST000001);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	}
	
	/**
	 * 
	 * @Description: 新增UserVO
	 * @param param map对象
	 * @return List<UserVO>
	 */
	@ApiOperation(value = "根据map新增UserVO")
	@AvoidRepeatableCommit
	@PostMapping(value="/saveUser")
	public Result saveUser(@ApiJsonObject(name = "UserRestController", value = {
										            		@ApiJsonProperty(key = "sex", example = "2020-10-10", description = "") ,
            								            		@ApiJsonProperty(key = "name", example = "2020-10-10", description = "") , 
            								            		@ApiJsonProperty(key = "remark", example = "2020-10-10", description = "") , 
            								            		@ApiJsonProperty(key = "id", example = "2020-10-10", description = "")
            				    })@RequestBody Map<String, Object> param) {
		try {
			String staffNo = SecurityContextHolder.getContext().getAuthentication().getName();
			return userService.saveUser(param,staffNo);
		} catch (PlmmSqlException e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST000001);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	}
	
	/**
	 * 
	 * @Description: 修改UserVO
	 * @param param map对象
	 * @return List<UserVO>
	 */
	@ApiOperation(value = "根据map修改UserVO")
	@PostMapping(value="/updateUser")
	public Result updateUser(@RequestBody Map<String, Object> param) {
		try {
			String staffNo = SecurityContextHolder.getContext().getAuthentication().getName();
			return userService.updateUser(param,staffNo);
		} catch (PlmmSqlException e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST000001);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	}
	
	/**
	 * 
	 * @Description: 删除UserVO
	 * @param param map对象
	 * @return List<UserVO>
	 */
	@ApiOperation(value = "根据map删除UserVO")
	@PostMapping(value="/deleteUser")
	public Result deleteUser(@RequestBody Map<String, Object> param) {
		try {
			String staffNo = SecurityContextHolder.getContext().getAuthentication().getName();
			return userService.deleteUser(param,staffNo);
		} catch (PlmmSqlException e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST000001);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtil.error(ResultEnum.ST999999);
		}
	}
	
}
