package com.plmm.common.sysuser.service;

import com.plmm.common.base.Result;
import java.util.Map;
import java.util.List;

import com.plmm.common.sysuser.model.UserVO;

/**
 * @ClassName: IUserDubboService
 * @Description: TODO  
 * @autor: admin
 * @date: 2020-09-11 09:41:05
 * @version: V1.0
 */
public interface IUserDubboService {
	String BEAN_ID = "userService";
	/**
	 *查询User数据
	 *@param paramMap 查询条件参数
	 *@param offset 分页下标
	 *@param limit 每页条数
	 */
	Object[] getUser(Map<String, Object> paramMap, int offset, int limit);
	/**
	 *通过主键查询一个UserVO对象
	 *@param primaryKey 主键
	 *@return UserVO对象
	 */
	UserVO selectOneUser(String primaryKey);
	/**
	 *通过map型参数查询一个UserVO对象
	 *@param paramMap map参数
	 *@return UserVO对象
	 */
	UserVO selectOneUser(Map<String, Object> paramMap);
	/**
	 *通过map型参数查询一个UserVO集合
	 *@param paramMap map参数
	 *@return UserVO集合
	 */
	List<UserVO> getUserList(Map<String, Object> paramMap);
	/**
	 *新增一个UserVO
	 *@param paramMap map参数
	 *@param staffNo 操作人
	 *@return result 返回结果
	 */
	public Result saveUser(Map<String, Object> paramMap, String staffNo);
	/**
	 *逻辑删除一个UserVO
	 *@param paramMap map参数
	 *@param staffNo 操作人
	 *@return result 返回结果
	 */
	public Result deleteUser(Map<String, Object> paramMap,String staffNo);
	/**
	 *更新一个UserVO
	 *@param paramMap map参数
	 *@param staffNo 操作人
	 *@return result 返回结果
	 */
	public Result updateUser(Map<String, Object> paramMap,String staffNo);
}

