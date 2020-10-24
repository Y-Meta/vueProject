//package com.plmm.webmvc.aspect;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.dubbo.config.annotation.Reference;
//import org.apache.rocketmq.common.message.Message;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import com.alibaba.fastjson.JSON;
//import com.plmm.dsp.log.service.ITpSysLogDubboService;
//import com.plmm.dsp.model.TpSysLogVO;
//import com.plmm.localcache.service.ICacheContextService;
//import com.plmm.mq.plmmMQProducer;
//import com.plmm.mq.TpSysLogProducer;
//import com.plmm.security.core.userdetails.User;
//import com.plmm.utils.Constant;
//import com.plmm.utils.IpUtils;
//import com.plmm.webmvc.util.RedisUtil;
//import com.maihaoche.starter.mq.base.MessageBuilder;
//import io.swagger.annotations.Api;
//
//@Aspect
//@Component
//public class LogAspect {
//
//	public Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Reference(version="${dubbo.version}",check=false,timeout=30000,interfaceClass=ITpSysLogDubboService.class)
//	private ITpSysLogDubboService tpSysLogDubboService;
//
//	@Autowired
//	private ICacheContextService cacheContextService;
//
//	@Autowired
//	private RedisUtil redisUtil;
//
//	//0 表示关闭 1 表示开启记录日志
//	@Value("${tpSysLogSwitch:1}")
//	private String tpSysLogSwitch;
//
//	@Autowired
//	plmmMQProducer plmmMQProducer;
//
//	//消息生产者依赖
//	@Autowired
//	TpSysLogProducer tpSysLogProducer;
//
//	@Pointcut("execution(public * com.plmm.controller.*.*(..))")
//	public void logControllerTrace(){
//
//	};
//
//
//	@Before("logControllerTrace()")
//	public void before(JoinPoint joinPoint){
//
//		if(StringUtils.isNotEmpty(tpSysLogSwitch) && "1".equals(tpSysLogSwitch)){
//			//如果是1,则表示记录日志到日志表中
//			try{
//				ServletRequestAttributes attribute = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//				HttpServletRequest request = attribute.getRequest();
//				//获取ip
//				String hostIp = IpUtils.getIpAddr(request);
//				String loginName = redisUtil.getLoginName();
////			String loginName = "xiaoming";
//				if(loginName==null)
//					return;
//				//当前登录用户
//				Object user = redisUtil.getAuth().getPrincipal();
//				//获取userCode
//				String userCode = "";
////			String userCode = "3";
//				//获取用户名称
//				String userName = "";
////			String userName = "门户管理员";
//				if(user != null){
//					userCode = ((User)user).getUsercode();
//					userName = ((User)user).getUserName();
//				}
//				MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//				//封装数据
//				Map<String,Object> paramMap = new HashMap<>();
//				// uri = /dsp/tpSysMenu/selectTpSysMenuBySysCode/US
//				String uri = request.getRequestURI();
//				//获取类名(全路径)
//				String className = signature.getDeclaringTypeName();
//				//获取方法名
//				String methodName = signature.getMethod().getName();
//				String redisKey = "tpSysLog" + className + "." + methodName;
//				String accessPage = cacheContextService.get(redisKey);
//				if(StringUtils.isEmpty(accessPage) || "/".equals(accessPage)){
//					/**
//					 * 下面是获取api注解中的value值
//					 */
//					//1 通过反射获取到类，填入类名
//					Class clazz = Class.forName(className);
//					//获取类上面的Api注解
//					Api annoApi = (Api)clazz.getAnnotation(Api.class);
//					//获取类上面的api中的value
//					String apiValue = "";
//					if(annoApi != null){
//						apiValue = annoApi.value();
//					}
//					//2 定义目标方法
//		/*	Method targetMethod = null;
//			Method[] methods = clazz.getMethods();
//			for(Method method : methods){
//				if(methodName.equals(method.getName())){
//					targetMethod = method;
//				}
//			}*/
///*			String apiOperationValue = "";
//			//3 获取方法上的api注解
//			if(null != targetMethod){
//				ApiOperation apiOperation = targetMethod.getAnnotation(ApiOperation.class);
//				//获取方法上的注解的值(api)
//				if(apiOperation != null){
//					apiOperationValue = apiOperation.value();
//				}
//			}*/
//					//4 页面路径
////			accessPage = apiValue + "/" + apiOperationValue;
//					accessPage = apiValue ;
//					//5 设置存入redis中的key
//					cacheContextService.set(redisKey,accessPage);
//					//6 设置过期时间(12小时)
//					cacheContextService.expire(redisKey,86400);
//				}
//				String sysCode = "";
//				if(!StringUtils.isEmpty(uri)){
//					if (uri.contains("dsp")){
//						//门户
//						sysCode = Constant.DSP_CODE;
//					}
//					if (uri.contains("dsrp")){
//						//补录
//						sysCode = Constant.dsrfSysCode;
//					}
//					if(uri.contains("ari")){
//						//关联风险
//						sysCode = Constant.AriSysCode;
//					}
//				}
//				String optType = getOptType(methodName);
//				//定义路径
//				String path = uri;
//				if(StringUtils.isNotEmpty(accessPage) && "login".equals(accessPage)){
//					accessPage = "登录";
//				}
//				if(StringUtils.isNotEmpty(accessPage) && "logout".equals(accessPage)){
//					accessPage = "退出";
//				}
//				//获取子系统名称
//				Map<String,Object> queryMap = new HashMap<>();
//				//用户code
//				queryMap.put("userCode",userCode);
//				//系统code
//				queryMap.put("sysCode",sysCode);
//				List<TpSysLogVO> childTpSysLogVO = tpSysLogDubboService.findChildSysName(queryMap);
//				List<TpSysLogVO> tpSysLogVOList = tpSysLogDubboService.findNeedColumn(queryMap);
//
//				//子系统的名称
//				String sysName = "";
//				//部门名称
//				String depName = "";
//				//用户id
//				String userId = "";
//				//角色名称
//				String roleName = "";
//				//角色的code
//				String roleCode = "";
//				if(childTpSysLogVO.size() == 1){
//					sysName = childTpSysLogVO.get(0).getSysName();
//				}
//				if(tpSysLogVOList.size() > 0){
//
//					depName = tpSysLogVOList.get(0).getDepName();
//					userId = tpSysLogVOList.get(0).getUserId();
//					for(TpSysLogVO tpSysLogVO : tpSysLogVOList){
//						String roleStr = tpSysLogVO.getRoleName();
//						if(StringUtils.isNotEmpty(roleStr)){
//							roleName = roleName + tpSysLogVO.getRoleName() + ",";
//							roleCode = roleCode + tpSysLogVO.getRoleCode() + ",";
//						}
//					}
////			roleName = tpSysLogVOList.get(0).getRoleName();
////			roleCode = tpSysLogVOList.get(0).getRoleCode();
//					if(roleName.length() > 0){
//						int roleNameLength = roleName.length();
//						int roleCodeLength = roleCode.length();
//						roleName = roleName.substring(0,roleNameLength-1);
//						roleCode = roleCode.substring(0,roleCodeLength-1);
//					}
//
//				}
//
//				if(StringUtils.isNotEmpty(uri) && !(uri.contains("get")|| uri.equals("/ari/") || uri.equals("/dsrp/") || uri.equals("/dsp/") || uri.contains("find") || uri.contains("select") || uri.contains("login") || uri.contains("logout") || uri.contains("is"))) {//过滤掉查询的登录退出的，因为别的地方已经做了
//					paramMap.put("sysCode", sysCode);
//					paramMap.put("path", path);
//					paramMap.put("accessPage", accessPage);
//					paramMap.put("type", optType);
//					paramMap.put("userCode", userCode);
//					paramMap.put("loginName", loginName);
//					paramMap.put("udpateUser", hostIp);
//					paramMap.put("sysName",sysName);
//					paramMap.put("depName",depName);
//					paramMap.put("userId",userId);
//					paramMap.put("roleName",roleName);
//					paramMap.put("userName",userName);
//					paramMap.put("roleCode",roleCode);
//					if (sysCode.equals(Constant.DSP_CODE)) {//门户的
//						String staffNo = loginName;
//						tpSysLogDubboService.saveTpSysLog(paramMap, staffNo);
//					} else {//非门户的
//						//构建消息对象
//						try {
//							TpSysLogVO tpSysLogVO = new TpSysLogVO();
//							tpSysLogVO.setAccessPage((String) paramMap.get("accessPage"));
//							tpSysLogVO.setSysCode((String) paramMap.get("sysCode"));
//							tpSysLogVO.setPath((String) paramMap.get("path"));
//							tpSysLogVO.setType((String) paramMap.get("type"));
//							tpSysLogVO.setUserCode((String) paramMap.get("userCode"));
//							tpSysLogVO.setLoginName((String) paramMap.get("loginName"));
//							tpSysLogVO.setUdpateUser((String) paramMap.get("udpateUser"));
//
//							tpSysLogVO.setSysName((String) paramMap.get("sysName"));
//							tpSysLogVO.setDepName((String) paramMap.get("depName"));
//							tpSysLogVO.setUserId((String) paramMap.get("userId"));
//							tpSysLogVO.setRoleName((String) paramMap.get("roleName"));
//							tpSysLogVO.setUserName((String) paramMap.get("userName"));
//							tpSysLogVO.setRoleCode((String) paramMap.get("roleCode"));
//							//消息的类型
//							tpSysLogVO.setMsgType("tpSysLog");
//							//转成String
//							String jsonString = JSON.toJSONString(tpSysLogVO);
//							//生产者发送消息
//							logger.info(jsonString);
//							Message message = MessageBuilder.of(jsonString).topic("tpSysLobTopic_new").build();
//							plmmMQProducer.syncSend(message);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//					}
//				}
//			}catch (Exception e){
//				//如果有异常的或
//				e.printStackTrace();
//			}
//		}
//
//
//
//
//	}
//
//	/**
//	 * 获取操作类型
//	 * @param methodName
//	 * @return
//	 */
//	public String getOptType(String methodName){
//		String targetType = "";
//		if(!StringUtils.isEmpty(methodName)){
//			if (methodName.contains("save") || methodName.contains("add")){
//				//门户
//				targetType = "3";
//			}
//			if (methodName.contains("select") || methodName.contains("get")){
//				//补录
//				targetType ="6";
//			}
//			if (methodName.contains("update")){
//				//补录update
//				targetType ="5";
//			}
//			if (methodName.contains("delete")){
//				//补录update
//				targetType ="4";
//			}if(methodName.contains("isSys") || methodName.contains("is")){
//
//				//补录update
//				targetType ="7";
//			}
//			if(methodName.contains("upload")){
//				//补录 文件上传
//				targetType ="8";
//			}
//			if(methodName.contains("download")){
//				//补录 文件下载
//				targetType ="9";
//			}
//		}
//		return targetType;
//	}
//
//
//}