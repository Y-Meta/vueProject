package com.plmm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

public class DateUtil {
	
	public static int FIRSTDAY_OF_WEEK=Calendar.MONDAY;
	
	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	public static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

    /**
     * 根据日期类型返回年月日(yyyyMMdd)
     * @param date
     * @return String
     */
    public static String getYMDByDate(String aMask, Date date) {
        SimpleDateFormat df = new SimpleDateFormat(aMask);
        return df.format(date);
    }

    /**
     * 转换字符串为日期类型
     * @param date
     * @return String
     */
    public static Date convertStringToDate(String aMask, String strDate) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * 转换字符串为日期类型
     * @param date
     * @return String
     */
    public static Date convertLongToDate(long time){
        Date date=new Date(time);
        return date;
    }

    /**
     * 根据日历类型返回年月日(yyyyMMdd)
     * @param date
     * @return String
     */
    public static String getYMDByCalendar(String aMask, Calendar date) {
        SimpleDateFormat df = new SimpleDateFormat(aMask);
        return df.format(date.getTime());
    }

    /**
     * 转换字符串为日历类型
     * @param date
     * @return String
     */
    public static Calendar convertStringToCalendar(String aMask, String strCalendar) {
        SimpleDateFormat df = null;
        Calendar calendar = Calendar.getInstance();
        df = new SimpleDateFormat(aMask);
        if(strCalendar!=null&&strCalendar!=""){
			try {
				calendar.setTime(df.parse(strCalendar));
			} catch (ParseException e) {
				calendar = null;
			}

        }
        return calendar;
    }

    /**
     * 获得给定日期的第一天和最后一天，如果传入的值为null，则取得当月的数据
     * @param date
     * @return
     */
    public static Date[] getFirstAndLastDate(Date date) {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

        if (date != null) {
            cal.setTime(date);
        }

        //当前月的最后一天
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Date endTime = cal.getTime();
        String lastTime = datef.format(endTime) + " 23:59:59";
        //当前月的第一天
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date beginTime = cal.getTime();
        String firstTime = datef.format(beginTime) + " 00:00:00";
        //日期格式的当月第一天
        Date firstDate = convertStringToDate("yyyy-MM-dd hh:mm:ss", firstTime);
        //日期格式的当月最后一天
        Date lastDate = convertStringToDate("yyyy-MM-dd hh:mm:ss", lastTime);
        return new Date[] { firstDate, lastDate };
    }

    //返回当前时间的字符串，用默认时间格式formatType
    public static String getThisDate() {
        return getThisDate("yyyy-MM-dd HH:mm:ss");
    }

    //返回输入时间的字符串，用默认时间格式formatType
    public static String getThisDate(Date date) {
        return getThisDate("yyyy-MM-dd HH:mm:ss", date);
    }

    //返回当前时间的字符串，时间格式为输入的参数(String formatType)
    public static String getThisDate(String formatType) {
        return getThisDate(formatType, new Date());
    }

    //返回输入时间的字符串，时间格式为输入的参数(String formatType)
    public static String getThisDate(String formatType, Date date) {
    	if(date != null){
    		SimpleDateFormat format = new SimpleDateFormat(formatType);
            return format.format(date);
    	}else{
    		return "";
    	}
        
    }

    //返回时间Date,用输入的时间字符串转化,输入的时间格式为默认时间格式formatType
    public static Date getAsStringDate(String strdate){
        return getAsStringDate(strdate, "yyyy-MM-dd HH:mm:ss");
    }

    //返回时间Date,用输入的时间字符串转化,输入的时间格式为String formatType
    public static Date getAsStringDate(String strdate, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            return format.parse(strdate);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String getTimeDash(Date date){
        String formatType = "HH-mm-ss";
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }
    
    /**
     * 求两个日期间隔的天数，第一个日期减第二个日期，如果发生异常返回-999999999
     * @param dateOne
     * @param dateTwo
     * @return
     */
    public static long getDaysBetweenTowDays(Date dateOne, Date dateTwo) {
    	long interval = 0;
    		try {
    			interval = (dateOne.getTime() - dateTwo.getTime()) / 1000 / 60 / 60 / 24;
    		} catch (Exception ex) {
    			interval = -999999999;
    		}
    	return interval;
    }
    /**
     * 
     * <p>Discription:[得到系统时间前一天]</p>
     * @param sysDate
     * @return
     * @author:[xdli]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static String getDayBefore(Date sysDate) {
    	
    	String sysDateStr=getThisDate(sysDate);
    	Calendar c = Calendar.getInstance();
    	Date date = null;
    	try{
    		date = new SimpleDateFormat("yyyy-MM-dd").parse(sysDateStr); 
    	}catch(Exception e){
    		
    	}
    	c.setTime(date);
    	int day = c.get(Calendar.DATE);
    	c.set(Calendar.DATE, day-1);
    	
    	String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    	
    	return dayBefore;
    }
    public static String getDayBefore(String sysDateStr) {
    	Calendar c = Calendar.getInstance();
    	Date date = null;
    	try{
    		date = new SimpleDateFormat("yyyy-MM-dd").parse(sysDateStr); 
    	}catch(Exception e){
    		
    	}
    	c.setTime(date);
    	int day = c.get(Calendar.DATE);
    	c.set(Calendar.DATE, day-1);
    	
    	String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    	
    	return dayBefore;
    }
    
    /**
     * 
     * <p>Discription:[得到传入时间加一天]</p>
     * @param sysDate
     * @return
     * @author:[xdli]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static String getDayAfter(String inDate) {
    	
    	Calendar c = Calendar.getInstance();
    	Date date = null;
    	try{
    		date = new SimpleDateFormat("yyyy-MM-dd").parse(inDate); 
    	}catch(Exception e){
    		
    	}
    	c.setTime(date);
    	int day = c.get(Calendar.DATE);
    	c.set(Calendar.DATE, day+1);
    	
    	String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    	
    	return dayAfter;
    }    
    
    /**
     * 获取传入日期的下一天
     * @param inDate
     * @return
     */
    public static String getDayAfter(Date inDate) {
    	
    	Date date = null;
    	try{
    		date = add(inDate, 1);
    	}catch(Exception e){
    		
    	}
    	
    	String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(date);
    	
    	return dayAfter;
    }
    
    //得到当前年份YYYY
    public static String getNowYear() {
    	Calendar cd =Calendar.getInstance();
    	int nowYear = cd.get(Calendar.YEAR);    	
    	return nowYear+"";
    }
   //得到当前月份MM
    public static int getNowMonth() {
    	Calendar cd =Calendar.getInstance();
    	int nowMonth = cd.get(Calendar.MONTH)+1;    	
    	return nowMonth;
    }
    
    /**
     * 获得给定日期最后一天，如果传入的值为null，则取得当月的数据
     * @param date
     * @return
     */
    public static String getLastDate(Date date) {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

        if (date != null) {
            cal.setTime(date);
        }

        //当前月的最后一天
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Date endTime = cal.getTime();
        String lastTime = datef.format(endTime);
        
        return lastTime;
    } 
    
    /**
     * 日期转换为秒的方法
     * @param Calendar calendar
     * @return String
     */
    public static String getDateTransition(Calendar calendar){
        long newDate = 0;
        if (calendar != null && !"".equals(calendar)) {
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            newDate = calendar.getTimeInMillis()/1000;
        }
        return String.valueOf(newDate);
    }
  
    /**
	 * 参见@see getFixedDate(Date start, int monthOffset, int day);
	 * @param start
	 * @param day
	 * @return
	 */
	public static Date getFixedDate(Date start, int day) {
		return getFixedDate(start, 0, day);
	}
	
	/**
	 * 获取指定offset月后的第day天，如果本月没有这一天，则指向最后一天。
	 * 
	 * @param start			开始时间点
	 * @param monthOffset   相差的月数
	 * @param day			指定到那一天
	 * @return 返回计算后的日期，精确到天
	 */
	public static Date getFixedDate(Date start, int monthOffset, int day) {
		java.util.Calendar c= java.util.Calendar.getInstance();
		c.setTime(start);
		
		// 指向第一天，保证月份计算安全
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, monthOffset);
		int lastDay= c.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 取最后一天与指定day之间较小的一个
		c.set(Calendar.DAY_OF_MONTH, Math.min(day, lastDay));
		
		return trunc(c.getTime(), "D");
	}
	
	
	public static Date add(Date date, int days) {
		java.util.Calendar c= java.util.Calendar.getInstance();
		c.setTime(date);
		c.set(java.util.Calendar.DAY_OF_YEAR, c.get(java.util.Calendar.DAY_OF_YEAR)+days);
		return c.getTime();
	}
	
	public static Date addMonths(Date date, int months){
		java.util.Calendar c= java.util.Calendar.getInstance();
		c.setTime(date);
		c.set(java.util.Calendar.MONTH, c.get(java.util.Calendar.MONTH)+months);
		return c.getTime();
	}
	
	public static Date lastDayOfMonth(Date date) {
		java.util.Calendar c= java.util.Calendar.getInstance();
		c.setTime(date);
		c.set(java.util.Calendar.DAY_OF_MONTH, c.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public static Date firstDayOfMonth(Date date) {
		java.util.Calendar c= java.util.Calendar.getInstance();
		c.setTime(date);
		c.set(java.util.Calendar.DAY_OF_MONTH, c.getActualMinimum(java.util.Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	public static Date firstDayOfYear(Date date) {
		java.util.Calendar c= java.util.Calendar.getInstance();
		c.setTime(date);
		c.set(java.util.Calendar.DAY_OF_YEAR, c.getActualMinimum(java.util.Calendar.DAY_OF_YEAR));
		return c.getTime();
	}
	public static Date lastDayOfYear(Date date) {
		java.util.Calendar c= java.util.Calendar.getInstance();
		c.setTime(date);
		c.set(java.util.Calendar.DAY_OF_YEAR, c.getActualMaximum(java.util.Calendar.DAY_OF_YEAR));
		return c.getTime();
	}
	

	/**
	 * 对时间精确度作trunc，使用String来作为pattern，支持以下情况
	 * <ul>
	 * <li>S 秒</li>
	 * <li>M 分钟</li>
	 * <li>H 小时</li>
	 * <li>D 天</li>
	 * <li><i>W 周</i></li>
	 * </ul>
	 * 
	 * @param date
	 * @param pattern:
	 *            s, m, h, d, w
	 * @return
	 */
	public static Date trunc(Date date, String pattern) {
		// timezone offset
		// long value= date.getTime();
		// long factor = getFactor(pattern);
		// //long mod= (value + offset) % factor;
		// long mod= (value) % factor;
		// //mod= 0;
		// return new Date(value - mod);
		SimpleDateFormat format = new SimpleDateFormat(getPattern(pattern.toUpperCase()));
		try {
			return format.parse(format.format(date));
		} catch (ParseException e) {
			throw new RuntimeException("不能处理的trunc格式" + pattern, e);
		}
	}

	private static Map patterns = new HashMap();
	static {
		patterns.put("S", "yyyyMMdd HH:mm:ss");
		patterns.put("M", "yyyyMMdd HH:mm");
		patterns.put("H", "yyyyMMdd HH");
		patterns.put("D", "yyyyMMdd");
	}

	private static String getPattern(String pattern) {
		String p = (String) patterns.get(pattern);
		if (null == p) {
			throw new java.lang.IllegalArgumentException("不能处理的trunc格式" + pattern);
		}
		return p;
	}

	private final static int OFFSET = SimpleTimeZone.getDefault().getRawOffset();

	private static long getFactor(String s) {
		if (null == s) {
			throw new IllegalArgumentException("Pattern should not null");
		}
		if (s.equalsIgnoreCase("S")) {
			return S;
		}
		if (s.equalsIgnoreCase("M")) {
			return M;
		}
		if (s.equalsIgnoreCase("H")) {
			return H;
		}
		if (s.equalsIgnoreCase("D")) {
			return D;
		}
		// if (s.equalsIgnoreCase("W")) {
		// return W;
		// }
		throw new IllegalArgumentException("Pattern should be one of s, m, h, d, w");
	}

	public static final long S = 1000;

	public static final long M = S * 60;

	public static final long H = M * 60;

	public static final long D = H * 24;

	public static final long W = D * 7;
	
	/**
	 * 
	 * <p>Discription:[根据输入时间得到当前月份第几周]</p>
	 * @return
	 * @author:[xdli]
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static int getCurrWeekForDate(Date inputDate){
		int currWeek =1;
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
		String nowDay = sdfDay.format(inputDate);		
		if(nowDay !=null){
			if(Integer.valueOf(nowDay)-1>=0 && Integer.valueOf(nowDay)-1<7){
				currWeek=1;
			}
			else if(Integer.valueOf(nowDay)-1>=7 && Integer.valueOf(nowDay)-1<14){
				currWeek=2;
			}
			else if(Integer.valueOf(nowDay)-1>=14 && Integer.valueOf(nowDay)-1<21){
				currWeek=3;
			}else{
				currWeek=4;
			}			
		}		
		return 	currWeek;
	}
	
	/**
	 * @name 校验字符串格式的日期是否为有效日期，格式必须为yyyy-MM-dd形式，否则都认为是错误的
	 * @return
	 */
	public static boolean validateDate(String inputDate) {
		// 先尝试对其进行转换
		Calendar cal = convertStringToCalendar("yyyy-MM-dd", inputDate);
		// 如果格式不符合要求，就会返回null
		if (cal != null) {
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			try {
				String[] date = inputDate.split("-");
				if (Integer.parseInt(date[0]) != year) {
					return false;
				}
				if (Integer.parseInt(date[1]) != month) {
					return false;
				}
				if (Integer.parseInt(date[2]) != day) {
					return false;
				}
			} catch (Exception e) {
				// 如果在转换的过程中出错，也认为日期不正确
				return false;
			}
			
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 获得指定日期的年 add by huhuan
	 * @param date
	 * @return
	 */
	public static String getYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return year+"";
	}
	
	/**
	 * 获得指定日期的月  add by huhuan
	 * @param date
	 * @return
	 */
	public static String getMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH)+1;
		return month+"";
	}
	
	/**
	 * 获得指定日期的日  add by huhuan
	 * @param date
	 * @return
	 */
	public static String getDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		return day+"";
	}
	/**
	 * 获得传入日期的前days天的日期
	 * @param sysDate
	 * @param days
	 * @return
	 */
	public static String getDateByInDay(Date sysDate, String days) {
    	
    	String sysDateStr=getThisDate(sysDate);
    	Calendar c = Calendar.getInstance();
    	Date date = null;
    	try{
    		date = new SimpleDateFormat("yyyy-MM-dd").parse(sysDateStr); 
    	}catch(Exception e){
    		
    	}
    	c.setTime(date);
    	int day = c.get(Calendar.DATE);
    	c.set(Calendar.DATE, day-Integer.parseInt(days));
    	
    	String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    	
    	return dayBefore;
    }
	
	
	/**
	 * 获得传入日期的后days天的日期
	 * @param sysDate
	 * @param days
	 * @return
	 */
	public static String getDateByAfterDay(Date sysDate, String days) {
    	
    	Calendar c = Calendar.getInstance();
    	Date date = null;
    	SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	String dateStr = DATEFORMAT.format(sysDate);
		try{
    		date = DATEFORMAT.parse(dateStr); 
    	}catch(Exception e){
    		
    	}
    	c.setTime(date);
    	int day = c.get(Calendar.DATE);
    	c.set(Calendar.DATE, day+Integer.parseInt(days));
    	
    	String dayBefore = DATEFORMAT.format(c.getTime());
    	
    	return dayBefore;
    }
	public static Date dateAdd(Date date,int field,int amount){
	
	if(date==null) {
		return null;
	}
	
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(field,amount);
	return calendar.getTime();
	}
	public static String getStringDateTime(){
	return DATEFORMAT.format(new Date());
	}
	/**
	转换日期的格式到yyyy-MM-dd HH:mm:ss
	*/
	public static String stringDateTime(Date date){
		if(date==null) {
			return "";
		} else {
			return DATEFORMAT.format(date);
		}	
	}
	
	/**
	 按格式转换日期的格式到固定格式的时间 <br>
	 转换时格式的字符必须符合要求.
	 @param date 
		待转换的日期. 
	 @param format 
		转换格式. 
		格式必须符合: <br>
			yyyy, 输出四位年 yy, 输出两位年 <br>
			MM, 月 <br>
			dd, 日期 <br>
			HH,	小时24小时制 <br>
			mm,	分钟 <br>
			ss,	秒 <br>
		中间间隔符号按照需要填写. 如: yyyy--MM--dd
	*/
	public static String stringDateTime(Date date,String format){
		
		if(date==null) {
			return null;
		}
		
		SimpleDateFormat subDateFormat = new SimpleDateFormat(format);
		return subDateFormat.format(date);
	}

}
