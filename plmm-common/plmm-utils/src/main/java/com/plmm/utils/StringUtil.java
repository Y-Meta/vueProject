package com.plmm.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Description: 处理字符串的工具类
 */
public class StringUtil {
	
	/** 
	 将字符串中的某位置的字符替换为其它字符
	 @param origin 
		待转换的字符串
	 @param begin 
		起始位置：0 开始
	 @param end
		结束位置：0 开始
	 @param target
		目标替换字符串
	*/
	public static String replace(String origin, int begin, int end, String target){
	
		StringBuffer strTemp=new StringBuffer(origin.substring(0,begin));
		int length = origin.length();	
		strTemp.append(target);
		strTemp.append(origin.substring(end+1,length));
		return strTemp.toString();
	
	}
	
	/** 
	 * 转换字符串格式：定长截取和填充
	 * @param param 
		将要转换的字符串。
	 * @param keepLength
		保留字串的长度。
	 * @param doMethod 
		0: 后补
		1: 前补
	 * @param ins 
		填充的内容。
	*/
	public static String changeString(String param, int keepLength, int doMethod, String ins){
	
	if (param == null) {
		param = "";
	}	
	String outStr = param;
	
	if(param.length()<keepLength){
		// 如果填充的内容是空串，就不进行拼接了，少走好多for循环
		if (ins == null || "".equals(ins)) {
			return outStr;
		}
		String temp = "";
		for(int i=0;i<keepLength-param.length();i++){
			temp += ins;
		}
	
		if(doMethod==0){
			outStr = temp + outStr;
		}else{
			outStr = outStr + temp;
		}
	
	}else{
		outStr = param.substring(0,keepLength);	
	}
	return outStr;		
	}
	
	/**
	返回几个tab推进的字符串
	*/
	public static String tabs(int tabs){
		//推进几个tab
		String str_tab="";
		
		for(int i=0;i<tabs;i++) {
			str_tab = str_tab + "	";
		}
		
		return str_tab;
	}
	
	
	/** 
		取字符串的左端
		@str 
			被检查的字符串
		@target
			分隔的字符串
		@notFound 
			如果没有分隔字符串, 返回该值 
	*/
	public static String left(String str,String target,String notFound) {
		
		if(str==null) {
			return null;
		}
		
		int pos = str.indexOf(target);
		if(pos >= 0 ) {
			return str.substring(0,pos);
		}else {
			return notFound;
		}
		
	}
	/** 
		取字符串的左端
		@str 
			被检查的字符串
		@target
			分隔的字符串
		@notFound 
			如果没有分隔字符串, 返回该值 
		@dir
			方向. 从左边 ? 右边
	*/
	public static String left(String str,String target,String notFound,int dir) {
		
		if(dir==0) {
			return left(str, target, notFound);
		}
		
		if(str==null) {
			return null;
		}
		
		int pos = str.lastIndexOf(target);
		if(pos >= 0 ) {
			return str.substring(0,pos);
		}else {
			return notFound;
		}
		
	}

	/** 
		取字符串的右端 		
		@str 
			被检查的字符串
		@target
			分隔的字符串
		@notFound 
			如果没有分隔字符串, 返回该值 
	*/
	public static String right(String str, String target, String notFound) {
		
		if(str==null) {
			return null;
		}
		
		int pos = str.indexOf(target);
		int length = str.length();
		if(pos >= 0 ) {
			if(pos == length - 1) {
				return "";
			} else {
				return str.substring(pos + target.length(), length);
			}
		}else {
			return notFound;
		}
		
	}

	/** 
		取字符串的右端 		
		@str 
			被检查的字符串
		@target
			分隔的字符串
		@notFound 
			如果没有分隔字符串, 返回该值 
		@dir
			方向. 从左边 ? 右边
	*/
	public static String right(String str, String target, String notFound, int dir) {
		
		if(dir==0) {
			return right(str, target, notFound);
		}
		
		if(str==null) {
			return null;
		}
		
		int pos = str.lastIndexOf(target);
		int length = str.length();
		if(pos >= 0 ) {
			if(pos == length - 1) {
				return "";
			} else {
				return str.substring(pos + target.length(), length);
			}
		}else {
			return notFound;
		}
		
	}
	
	/** 
		计算字符串的某字符的个数 
		@str 
			被检查的字符串
		@target
			分隔的字符串
	*/
	public static int count(String str,String target) {
		int indexOf = str.indexOf(target);
		if(indexOf>0) {
			return 1 + count(right(str, target, ""), target);
		} else {
			return 0;
		}
	}
	
	/**
	 * 修改串的字符类型,将ISO8859-1转化为gb2312
	 * 如果已经是中文的字符,不能转化.
	 */
	public static String convertToChinese(String str){
		
		//空值判断
		if(str==null) {
			return null;
		}
		
		try{
			return new String(str.getBytes("ISO8859-1"),"gb2312");
		}catch(java.io.UnsupportedEncodingException stre){
			 
		}
		return null;
	}
	
	/**
	 * 获得堆栈信息串。
	 * @param exception
	 * @return String
	 */
	public static String getStackTrace(Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		return sw.toString();
	}
	
	public static String emptyTo(String inStr,String newStr){
		if (inStr==null || inStr.trim().length()==0){
			return newStr;
		}else{
			return inStr;
		}
	}
	
	/**
	 * 用双引号封装字符串
	 * @param str
	 * @return
	 */
	public static String packStringByQuot(String str){
		return packString(str,"\"","\"");
	}
	/**
	 * 给字符串加前缀后缀封装字符串
	 * @param str
	 * @param prefix 前缀字符
	 * @param suffix 后缀字符
	 * @return
	 */
	public static String packString(String str, String prefix, String suffix){
		return prefix+str+suffix;
	}
	
	public static String getStringValue(Object obj){
		if(obj==null){
			return "";
		}
		return obj.toString();
	}
	
	public static int getStringLength(String str){
		if(str==null){
			return 0;
		}
		return str.getBytes().length;
	}
	
	public static boolean hasValue(Object o){
		if(o==null||o.toString().trim().equals("")){
			return false;
		}
		return true;
	}
	
	public static boolean isNumber(String str){
		String reg="^[-|+]?\\d*([.]\\d+)?$";
		return str.matches(reg);
	}
	
	/**无效返回0*/
	public static String getStringO(Object obj){
		if(obj==null){
			return "0";
		}
		return obj.toString();
	}
	//传入    1,2,3,4,5,6
	//返回 "1","2","3","4","5","6"
	public static String getStrList(String str){
		if(str != null && !"".equals(str)){
			String[] strList = str.split(",");
			StringBuffer result=new StringBuffer();
			String res="";
			for(String strTemp : strList){
				if(!"".equals(strTemp.trim())){
					result.append("'");
					result.append(strTemp);
					result.append("',");
				}
			}
			if(result.length() > 1){
				res = result.substring(0,result.length()-1);
			}
			return res;
		}else{
			return "";
		}
	}
	
	/**
	 * String类型的参数列表转换为Set类型参数，仅限于后台sql入参中使用，前台慎用
	 * @param inputVal
	 * @return
	 */
	public static Object convertStr2Set(Object inputVal) {
		if (inputVal == null) {
			return null;
		}
		if (inputVal instanceof String) {

			String stringVal = (String) inputVal;
			if (StringUtils.isBlank(stringVal)) {
				return null;
			}
			HashSet<String> retSet = new HashSet<String>();
			String[] split = stringVal.split(",");
			for (String string : split) {
				if (StringUtils.isNotBlank(string)) {
					retSet.add(string);
				}
			}
			if (retSet.isEmpty()) {
				return null;
			}
			return convertStr2Set(retSet);
		} else if (inputVal instanceof List) {
			Set retSet = new HashSet();
			for (Object itemVal : (List) inputVal) {
				if (StringUtils.isNotBlank((String) itemVal)) {
					retSet.add(itemVal);
				}
			}

			if (retSet.isEmpty()) {
				return null;
			}
			return convertStr2Set(retSet);
		} else if (inputVal instanceof Array) {
			return convertStr2Set(Arrays.asList((Object[]) inputVal));
		} else if (inputVal instanceof Set) {
			StringBuilder result = new StringBuilder("");
			for (Object itemVal : (Set) inputVal) {
				if (StringUtils.isNotBlank((String) itemVal)) {
					result.append(",'").append(itemVal).append("'");
				}
			}
			if (StringUtils.isNotBlank(result.toString())) {
				return result.toString().substring(1);
			}

			return null;
		}
		return null;
	}
	public static String fillStriNull(String str) {
		if(str == null) {
			return "";
		} else {
			return str.trim();
		}
	}
}
