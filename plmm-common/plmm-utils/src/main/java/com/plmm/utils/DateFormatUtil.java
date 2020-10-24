package com.plmm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateFormatUtil {

	public static Logger logger = LoggerFactory.getLogger(DateFormatUtil.class);
	public static final String COMMON_TYPE = "yyyy-MM-dd HH:mm:ss";

	public static String getDateString(Date date, String type) {
		if(date == null) {
			return "";
		}
		try {
			DateFormat df = new SimpleDateFormat(type);
			return df.format(date);
		} catch (Exception ex) {
			logger.error("", ex);
		}
		return "";
	}

}
