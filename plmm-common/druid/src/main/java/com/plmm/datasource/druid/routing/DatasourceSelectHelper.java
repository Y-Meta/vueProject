package com.plmm.datasource.druid.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatasourceSelectHelper {
	static final Logger LOGGER = LoggerFactory.getLogger(DatasourceSelectHelper.class);
	
	public static final String BA = "ba";
	
	public static final String ARI = "ari";
	
	public static final String DSP = "dsp";
	
	public static final String DSS = "dss";
	
	public static final String COMMON = "common";
	
	private static final ThreadLocal<String> DATASOURCEKEY = new ThreadLocal<String>();
	
	public static String getKey(){
		return DATASOURCEKEY.get();
	}
	
	public static void setKey(String key){
		DATASOURCEKEY.set(key);
		LOGGER.info("datasource dsKey switch to ######{}#####", key);
	}
	
}
