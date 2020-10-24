//package com.plmm.webmvc.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.plmm.common.base.Result;
//import com.plmm.common.utils.ResultUtil;
//import com.plmm.dsp.model.TpSysLogVO;
//import com.plmm.mq.plmmMQProducer;
//import com.plmm.security.core.userdetails.User;
//import com.plmm.utils.*;
//import com.plmm.webmvc.filter.XssHttpServletRequestWrapper;
//import com.plmm.webmvc.util.RedisUtil;
//import com.maihaoche.starter.mq.base.MessageBuilder;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.rocketmq.common.message.Message;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.DefaultParameterNameDiscoverer;
//import org.springframework.core.ParameterNameDiscoverer;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.*;
//
///**
// * @ProjectName: plmmProject
// * @Description:
// * @Author: qiguohui
// * @Date: 2020/4/28 17:34
// */
//
//@Aspect
//@Component
//public class LogFileAspect<T> {
//
//    public Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Autowired
//    plmmMQProducer plmmMQProducer;
//
//    @Resource(name="tpAsyncLogFileExecutor")
//    TaskExecutor asyncService;
//
//    // fileLogSwitch 值为1时表示的时通过mq去写日志,值为2时通过异步线程写日志，值为0时表示不写日志
//    @Value("${fileLogSwitch:1}")
//    private String fileLogSwitch;
//
//    @Pointcut("execution(public * com.plmm.controller.*.*(..))")
//    public void logControllerTrace(){
//    };
//
//    /**
//     * 获取请求参数中所有的Map 的位置
//     * @return
//     */
///*    public List<Integer> getMapIndex(Object[] args){
//        //定义返回的目标类型
//        List<Integer> mapIndex = new ArrayList<>();
//        for(int i = 0;i < args.length;i++){
//            //获取class
//            Class<?> aClass = args[i].getClass();
//            //获取参数的类型
//            String typeName = aClass.getTypeName();
//            //判断参数类型，这里只可能存在两种，1 String,2 Map
//            if(StringUtils.isNotEmpty(typeName) && typeName.equals("java.util.LinkedHashMap")){
//                //如果是Map类型，则把requestId放到Map里
//                mapIndex.add(i);
//            }
//        }
//        return mapIndex;
//    }*/
//
//   /* private Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) throws Exception {
//        String classType = joinPoint.getTarget().getClass().getName();
//        String methodName = joinPoint.getSignature().getName();
//        // 参数值
//        Object[] args = joinPoint.getArgs();
//        System.out.println(args);
////        for ()
//        Class<?>[] classes = new Class[args.length];
//        for (int k = 0; k < args.length; k++) {
//            // 对于接受参数中含有MultipartFile，ServletRequest，ServletResponse类型的特殊处理，我这里是直接返回了null。（如果不对这三种类型判断，会报异常）
//            if (args[k] instanceof MultipartFile || args[k] instanceof ServletRequest || args[k] instanceof ServletResponse) {
//                return null;
//            }
//            if (!args[k].getClass().isPrimitive()) {
//                // 当方法参数是基础类型，但是获取到的是封装类型的就需要转化成基础类型
////                String result = args[k].getClass().getName();
////                Class s = map.get(result);
//                // 当方法参数是封装类型
//                Class s = args[k].getClass();
//                classes[k] = s == null ? args[k].getClass() : s;
//            }
//        }
//        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
//        // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
//        Method method = Class.forName(classType).getMethod(methodName, classes);
//        // 参数名
//        String[] parameterNames = pnd.getParameterNames(method);
//        // 通过map封装参数和参数值
//        HashMap<String, Object> paramMap = new HashMap();
//        for (int i = 0; i < parameterNames.length; i++) {
//            paramMap.put(parameterNames[i], args[i]);
//        }
//        return paramMap;
//    }*/
//
//
//    /**
//     * 获取参数列表
//     *
//     * @param joinPoint
//     * @return
//     * @throws ClassNotFoundException
//     * @throws NoSuchMethodException
//     */
//    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
//        // 参数值
//        Object[] args = joinPoint.getArgs();
//        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        String[] parameterNames = pnd.getParameterNames(method);
//        Map<String, Object> paramMap = new HashMap<>(32);
//        for (int i = 0; i < parameterNames.length; i++) {
//           /* if(args[i] instanceof MultipartFile){
//                paramMap.put(parameterNames[i], "");
//            }else if(args[i].getClass().isArray()){
//                try{
//                    MultipartFile[] fileDate = (MultipartFile[])args[i];
//                    paramMap.put(parameterNames[i], "");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }else */
//           if(args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile || args[i] instanceof XssHttpServletRequestWrapper){
//                paramMap.put("key","1");
//                break;
//            }
//            else {
//                paramMap.put(parameterNames[i], args[i]);
//            }
//        }
//        return paramMap;
//    }
//
//
//    @Around("logControllerTrace()")
//    public Object around(ProceedingJoinPoint joinPoint)  throws Throwable {
//        // 2 请求头的时间戳
//        String headSysTime = DateUtils.getTime();
//        //定义拦截的字段
//        String stag = "0";
//        Date startDate = new Date();
//        Long start1 = startDate.getTime();
//        // 定义每个日志的id
//        String requestId = IdUtil.getSeqId().toString();
//        //获取ip
//        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attribute.getRequest();
////        String hostIp = IpUtils.getIpAddr(request);
//        //请求的路径
//        String uri = request.getRequestURI();
//        //当前登录用户
//        String loginName = redisUtil.getLoginName();
//        if (StringUtils.isNotEmpty(fileLogSwitch) && !"0".equals(fileLogSwitch)) {
//            try {
//
//                //获取到请求参数
//                Map<String, Object> requestParam = getFieldsName(joinPoint);
//                if(requestParam.containsKey("request") || requestParam.containsKey("response") || requestParam.containsKey("key")){
//                    stag = "1" ;
//                }
//                String requestParamJson = "";
//                if("0".equals(stag)){
//                    requestParamJson = JSON.toJSONString(requestParam);
//                }
//                //用户
//                /**
//                 * 定义日志头的部分
//                 */
//                //1 定义日志的级别
//                String headLevel = "INFO";//默认为INFO
//                //3 请求头中的Id
//                String headRequestId = requestId;
//                // 4 标识
//                String start = "start";
//                //把请求头写入日志中
//                if ("0".equals(stag) && StringUtils.isNotEmpty(uri) && StringUtils.isNotEmpty(loginName) && !uri.equals("/dsp/logout") && !uri.equals("/dsrp/logout")) {
//                    //发送的日志体
//                    String logMsg = "[" + headSysTime + "]" + "[INFO]" + "[" + requestId + "]" + "[" + "START" + "][" + loginName + "]" + "[" + uri + "][" + requestParamJson + "]";
//                    try {
//                        if (StringUtils.isNotEmpty(fileLogSwitch) && "1".equals(fileLogSwitch)) {
//                            //生产者发送消息
//                            Message message = MessageBuilder.of(logMsg).topic("tpSysFileTopic").build();
//                            plmmMQProducer.syncSend(message);
//                        } else {
//                            asyncService.execute(() -> {
//                                logger.info(logMsg);
//                            });
//                        }
//                    } catch (Exception e) {
//                        asyncService.execute(() -> {
//                            logger.error("rocketMq 发送消息失败,可能是服务器挂了");
//                            logger.info(logMsg);
//                        });
//                    }
//                }
//                //调用目标方法
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
////        Result result =  ResultUtil.sucess("成功");
//
//        //调用目标方法
//        Object obj = joinPoint.proceed();
//
//        if ("0".equals(stag) && StringUtils.isNotEmpty(fileLogSwitch) && !"0".equals(fileLogSwitch)) {
//            try {
//                // 1 时间戳
//                headSysTime = DateUtils.getTime();
//                //日志体
//                String resultMsg = "";
//                Result result = new Result();
//                if (obj instanceof Result) {
//                    result = (Result) obj;
//                    resultMsg = result.getReturnMsg();
//                }
//                Object data = result.getData();
//                if (null != data) {
//                    if (data.getClass().isArray()) {
//                        Object[] resultData = (Object[]) data;
//                        List<Object> lists = (ArrayList) resultData[1];
//                        int size = lists.size();
//                        resultMsg = "查询" + resultMsg + ",查询出了" + size + "条";
//                    }
//                }
//                if (StringUtils.isNotEmpty(loginName) && !uri.equals("/dsp/logout") && !uri.equals("/dsrp/logout")) {
//                    //发送的日志体
//                    String logMsg = "[" + headSysTime + "]" + "[INFO]" + "[" + requestId + "]" + "[" + resultMsg + "]";
//                    try {
//                        if (StringUtils.isNotEmpty(fileLogSwitch) && "1".equals(fileLogSwitch)) {
//                            //生产者发送消息
//                            Message message = MessageBuilder.of(logMsg).topic("tpSysFileTopic").build();
//                            plmmMQProducer.syncSend(message);
//                        } else {
//                            asyncService.execute(() -> {
//                                logger.info(logMsg);
//                            });
//                        }
//                    } catch (Exception e) {
//                        asyncService.execute(() -> {
//                            logger.error("rocketMq 发送消息失败,可能是服务器挂了");
//                            logger.info(logMsg);
//                        });
//                    }
//                }
//                headSysTime = DateUtils.getTime();
//                Date endDate = new Date();
//                Long end = endDate.getTime();
//                Long during = end - start1;
//                //日志尾
//                if (StringUtils.isNotEmpty(loginName) && !uri.equals("/dsp/logout") && !uri.equals("/dsrp/logout")) {
//                    //发送的日志体
//                    String logMsg = "[" + headSysTime + "]" + "[INFO]" + "[" + requestId + "][END][" + during + "ms]";
//                    try {
//                        if (StringUtils.isNotEmpty(fileLogSwitch) && "1".equals(fileLogSwitch)) {
//                            //生产者发送消息
//                            Message message = MessageBuilder.of(logMsg).topic("tpSysFileTopic").build();
//                            plmmMQProducer.syncSend(message);
//                        } else {
//                            asyncService.execute(() -> {
//                                logger.info(logMsg);
//                            });
//                        }
//                    } catch (Exception e) {
//                        asyncService.execute(() -> {
//                            logger.error("rocketMq 发送消息失败,可能是服务器挂了");
//                            logger.info(logMsg);
//                        });
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return obj;
//    }
//
//}
