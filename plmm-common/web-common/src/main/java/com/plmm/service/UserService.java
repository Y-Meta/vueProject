package com.plmm.service;

import java.util.List;
import java.util.Map;

import com.plmm.common.base.Result;
import com.plmm.common.sysuser.model.UserVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import com.plmm.common.sysuser.service.IUserDubboService;

@Component
public class UserService {

	@Reference(version="${dubbo.version}",check=false,timeout=30000,interfaceClass=IUserDubboService.class)
    private IUserDubboService userDubboService;
	
	public Object[] getUser(Map<String, Object> paramMap, int offset, int limit) {
		return userDubboService.getUser(paramMap,offset,limit);
	}
	
	public List<UserVO> getUserList(Map<String, Object> paramMap) {
		return userDubboService.getUserList(paramMap);
	}
	
	public UserVO selectOneUser(String primaryKey){
		return userDubboService.selectOneUser(primaryKey);
	}
	
	public UserVO selectOneUser(Map<String, Object> paramMap) {
		return userDubboService.selectOneUser(paramMap);
	}
	
	public Result saveUser(Map<String, Object> paramMap, String staffNo) {
		return userDubboService.saveUser(paramMap,staffNo);
	}

	public Result deleteUser(Map<String, Object> paramMap,String staffNo) {
		return userDubboService.deleteUser(paramMap,staffNo);
	}

	public Result updateUser(Map<String, Object> paramMap,String staffNo) {
		return userDubboService.updateUser(paramMap,staffNo);
	}
}


