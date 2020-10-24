package com.plmm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelUtil {
    /**
     * 判定电话号码为手机或固话
     * @param tel
     * @return telType 0:手机  1:固话
     */
    public static String getTelType(String tel) {
        String telType = "0";
        String check = "^(13[1,4,5,6,7,8,9]|15[0,8,9,1,7]|186|187)\\d{8}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(tel);
        if (!matcher.matches()) {
            telType = "1";
        }
        return telType;
        
    }
    
    /**
     * 将输入的字符串进行*号屏蔽
     * 例如：1234567890->123****890 shieldNumber("1234567890", 3, 4)
     * @param inputStr 输入的字符串
     * @param rightLen 最右边可以正常显示的长度
     * @param shieldLen 使用*号屏蔽的长度
     * @return
     */
    public static String shieldNumber(String inputStr, int rightLen, int shieldLen) {
    	
    	StringBuffer returnVal = new StringBuffer();
    	// 如果输入的字符串为null，或两个长度参数为负，则返回空字符串
    	if (inputStr == null || rightLen < 0 || shieldLen < 0) {
    		return "";
    	}
    	// 字符串的长度
    	int length = inputStr.length();
    	// 如果字符串长度小于右侧正常显示的长度，则直接返回该字符串
    	if (length <= rightLen) {
    		return inputStr;
    	}
    	if (length > rightLen && length <= rightLen + shieldLen) {
    		for (int i = 0; i < length - rightLen; i++) {
    			returnVal.append("*");
    		}
    		returnVal.append(inputStr.substring(length - rightLen, length));
    		return returnVal.toString();
    	}
    	if (length > rightLen + shieldLen) {
    		returnVal.append(inputStr.substring(0, length - rightLen - shieldLen));
    		for (int i = 0; i < shieldLen; i++) {
    			returnVal.append("*");
    		}
    		returnVal.append(inputStr.substring(length - rightLen, length));
    		return returnVal.toString();
    	}
    	return "";
    }
    /**
     * 验证输入是否匹配正则表达式
     * @param regex 需要匹配的正则表达式
     * @param input 输入字符串
     * @return 字符串匹配则返回true,否则返回false
     */
    public static boolean validateInput(String regex, String input) {
        Pattern pat  = Pattern.compile(regex);
        Matcher match = pat.matcher(input);
        if(!match.find()){
            return false;
        }
        return true;
    }
}
