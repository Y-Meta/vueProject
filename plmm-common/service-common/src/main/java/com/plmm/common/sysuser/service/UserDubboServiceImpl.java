package com.plmm.common.sysuser.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.plmm.common.base.Result;
import com.plmm.common.sysuser.model.UserVO;
import com.plmm.common.utils.ResultUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.plmm.common.sysuser.mapper.UserMapper;

@Service(version = "${dubbo.version}")
public class UserDubboServiceImpl implements IUserDubboService{
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Object[] getUser(Map<String, Object> paramMap, int offset, int limit) {
		return userMapper.getUser(paramMap,offset,limit);
	}
	
	@Override
	public List<UserVO> getUserList(Map<String, Object> paramMap) {
//		return userMapper.getUserList(paramMap);
		
		List<UserVO> testData = new ArrayList<UserVO>();
		
		UserVO vo1 = new UserVO();
		vo1.setId("1");
		vo1.setName("张三");
		vo1.setSex("男");
		UserVO vo2 = new UserVO();
		vo2.setId("2");
		vo2.setName("李四");
		vo2.setSex("女");
		
		testData.add(vo1);
		testData.add(vo2);
		
		return testData;
	}
	
	@Override
	public UserVO selectOneUser(String primaryKey){
		return userMapper.selectOneUser(primaryKey);
	}
	
	@Override
	public UserVO selectOneUser(Map<String, Object> paramMap) {
		return userMapper.selectOneUser(paramMap);
	}
	
	@Override
	public Result saveUser(Map<String, Object> paramMap, String staffNo) {
		userMapper.saveUser(paramMap,staffNo);
		return ResultUtil.sucess("保存成功");
	}
	
	@Override
	public Result deleteUser(Map<String, Object> paramMap,String staffNo) {
		userMapper.deleteUser(paramMap,staffNo);
		return ResultUtil.sucess("删除成功");
	}
	
	@Override
	public Result updateUser(Map<String, Object> paramMap,String staffNo) {
		userMapper.updateUser(paramMap,staffNo);
		return ResultUtil.sucess("更新成功");
	}
}


