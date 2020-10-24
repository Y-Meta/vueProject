package com.plmm.common.sysuser.mapper;

import java.util.List;
import java.util.Map;
import com.plmm.utils.DateUtils;
import com.plmm.common.sysuser.model.UserVO;
import com.plmm.datasource.druid.mapper.IDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserMapper {
	public static final String NAME_SPACE = "com.plmm.common.sysuser.config.";
	
	@Autowired
    IDefaultService service;
	
	public Object[] getUser(Map<String, Object> paramMap, int offset, int limit) {
		Object[] objs = new Object[2];
		objs[0] = service.selectOne(NAME_SPACE + "countUser", paramMap);
		objs[1] = service.selectList(NAME_SPACE + "queryUser", paramMap, offset, limit);
		return objs;
	}
	
	public List<UserVO> getUserList(Map<String, Object> paramMap) {
		return service.selectList(NAME_SPACE + "queryUser", paramMap);
	}
	
	
	public UserVO selectOneUser(String primaryKey) {
		return service.selectOne(NAME_SPACE + "queryOneUser", primaryKey);
	}
	
	public UserVO selectOneUser(Map<String, Object> paramMap) {
		return service.selectOne(NAME_SPACE + "queryUser", paramMap);
	}
	
	public void saveUser(Map<String, Object> paramMap,String staffNo) {
		String now = DateUtils.getTime();
		paramMap.put("createUser", staffNo);//创建人
		paramMap.put("createTime", now);
		service.insert(NAME_SPACE + "insertOneUser", paramMap);
	}
	
	public void deleteUser(Map<String, Object> paramMap,String staffNo) {
		String now = DateUtils.getTime();
		paramMap.put("updateUser", staffNo);//最近逻辑删除者
		paramMap.put("updateTime", now);
		service.update(NAME_SPACE + "deleteLogicOneUser", paramMap);
	}
	
	public void updateUser(Map<String, Object> paramMap,String staffNo) {
		String now = DateUtils.getTime();
		paramMap.put("updateUser", staffNo);//最近更新者
		paramMap.put("updateTime", now);
		service.update(NAME_SPACE + "updateOneUser", paramMap);
	}
}


