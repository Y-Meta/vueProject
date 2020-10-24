package com.plmm.datasource.druid.aop;

import javax.servlet.http.HttpServletRequest;

import com.plmm.datasource.druid.routing.DatasourceSelectHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
@Aspect
@ConditionalOnExpression("'${plmm.datasource-config.type}'=='routingreadwrite' || '${plmm.datasource-config.type}'=='routing'")
public class AspectMultiDatasource {
	Logger logger = org.slf4j.LoggerFactory.getLogger(AspectMultiDatasource.class);
	
	static final String DS_KEY = "dsKey";
	
	@Before("execution(* com.plmm..controller.*.*(..))")
	public void doBefore(JoinPoint jp) {
		ServletRequestAttributes hsr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = hsr.getRequest();
		String dsKey = request.getHeader(DS_KEY);
		if(dsKey == null || "".equals(dsKey)) {
			dsKey = request.getParameter(DS_KEY);
		}
		if(!StringUtils.isEmpty(dsKey)) {
			DatasourceSelectHelper.setKey(dsKey);
//			DatasourceSelectHelper.setKey(cache.getBusinessDbBySubCompanyNo(dsKey));
		}
	};
	
//	@Autowired
//	RestfulCacheManager cache;
	
}
