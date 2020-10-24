package com.plmm.datasource.druid.aop;
//package com.plmm.datasource.druid.aop;
//
//import java.util.Random;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.stereotype.Component;
//
//import com.plmm.datasource.druid.config.DatasourceProperty;
//import com.plmm.datasource.druid.routing.DatasourceSelectHelper;
//
//
//@Component
//@Aspect
//@ConditionalOnExpression("'${plmm.datasource-config.type}'=='routingreadwrite'")
//public class AspectReadWriteDatasource {
//	Logger logger = org.slf4j.LoggerFactory.getLogger(AspectReadWriteDatasource.class);
//	
//	@Autowired
//	DatasourceProperty multiplyDatasourceConfig;
//	
//	@Before("execution(* com.plmm..service.*.*(..))")
//	public void doBefore(JoinPoint jp) {
//		long start = System.currentTimeMillis();
//		Signature s = jp.getSignature();
//		String name = s.getName();
//		String key = DatasourceSelectHelper.getKey();
//		//用于识别读写操作
//		if(isWrite(name,multiplyDatasourceConfig.getPrefix()))  {
//			logger.info("{} write cmd sent to master node[{}]", s.toLongString(), key);
//		} else {
//			Random r = new Random();
//			if(r.nextInt(2) == 1) {
//				key = DatasourceSelectHelper.getKey() + "-slave";
//				DatasourceSelectHelper.setKey(key);
//			} 
//			logger.info("{} read cmd sent to node[{}]", s.toLongString(), key);
//		}
//		long end = System.currentTimeMillis();
//		logger.info("used time: {} ms", end-start);
//	}
//	
//	private boolean isWrite(String methodName, String prefixString) {
//		String[] prefixs = prefixString.split(",");
//		boolean isWrite = false;
//		for(String prefix : prefixs) {
//			if(methodName.startsWith(prefix)){
//				isWrite=true;
//				break;
//			};
//		}
//		return isWrite;
//	}
//	
//}
