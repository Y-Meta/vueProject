package com.plmm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
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

    /**
     * 验证邮编是否合法
     * @param zipCode 邮编号码
     * @return 邮编正确则返回true,否则返回false
     */
    public static boolean validateZipCode(String zipCode){
        String regex = "^\\d{6}$";
        return validateInput(regex, zipCode);
    }

    /**
     * 验证Email是否合法
     * @param email Email地址
     * @return Email地址正确则返回true,否则返回false
     */
    public static boolean validateEmail(String email){
        String regex = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        return validateInput(regex, email);
    }

    /**
     * 验证电话号码是否合法
     * @param tel 电话号码,手机号或固定电话号码
     * @return 电话号码正确则返回true,否则返回false
     */
    public static boolean validateTel(String tel) {
        // 验证固定电话:(0xx)-xxxxxxxx或xxxx-xxxxxxxx
        String telRegex = "\\(?0(10|2[1-3]|\\d{3})\\)?-?\\d{7,8}";
        // 验证手机号
        String mobileRegex = "1(3[0-9])|(45|47)|(5[0-9])|(82|8[6-8])\\d{8}";
        return validateInput(telRegex, tel) || validateInput(mobileRegex, tel);
    }

    /**
     * 验证手机号码是否合法
     * @param mobile 手机号
     * @return 手机号正确则返回true,否则返回false
     */
    public static boolean validateMobile(String mobile) {
        // 验证手机号
        String mobileOne = "(^(1\\d{10})$)|(^(01\\d{10})$)";
        return  validateInput(mobileOne, mobile);
    }
    
    /**
     * 验证手机号码是否合法
     * @param mobile 手机号
     * @return 手机号正确则返回true,否则返回false
     */
    public static boolean validateMobile(String mobile,String mYFreePhone) {
    	// 验证手机号
    	String mobileOne = "";
    	if(mYFreePhone.indexOf(","+mobile.length()+",") != -1){
    			String str = (mobile.length()-1)+"";
    			mobileOne = "(^(1\\d{"+str+"})$)|(^(01\\d{"+str+"})$)";//蚂蚁保单14位手机号
    		return  validateInput(mobileOne, mobile);
    	}else{
    		return false;
    	}
    }

    /**
     * 验证固话是否合法
     * @param phone 固话号码
     * @return 固话号码正确则返回true,否则返回false
     */
    public static boolean validatePhoneLength(String phone, int... length) {

        // 验证固话
        boolean flag = false;
        String phoneRegex = "^\\d+$";
        //输入的号码如果包含非数字部分，将非数字部分过滤掉，只返回数字部分。liuhaibin 20120319 begin
        phone = clearMobile(phone) ;
        //输入的号码如果包含非数字部分，将非数字部分过滤掉，只返回数字部分。liuhaibin 20120319 end
        //如果不是手机号码，并且返回的固话长度为0，直接返回false
        //if((!isMobile(phone))&&(length[0]==0)&&(phone.length()>=11)){
        //	flag=false;
        //}
        flag=validateInput(phoneRegex, phone);
        if(flag){
            // 判断固定电话：7位，8位，11位，12位
            if (phone.length() == 7 || phone.length() == 8) {
                if(phone.length() !=length[0] ){
                    flag=false;
                }else{
                    flag=validatePhone(phone, length);
                }
            } else if (phone.length() == 11) {
                //liuhaibin 20120319 begin
                if(Integer.parseInt(phone.substring(0, 1))>1){
                    return false;
                }
                //liuhaibin 20120319 end
                Integer two =Integer.parseInt(phone.substring(0, 2));
                //判断前两位
                if (two == 01 || two == 02) {
                    phone = phone.substring(3, phone.length());
                    //长度不一致
                    if(phone.length() == length[0]){
                        flag=validatePhone(phone, length);
                    }else{
                        flag=false;
                    }
                }else if(two > 02){
                    phone = phone.substring(4, phone.length());
                    //长度不一致
                    if(phone.length() == length[0]){
                        flag=validatePhone(phone, length);
                    }else{
                        flag=false;
                    }
                }
            } else if (phone.length() == 12) {
                if(Integer.parseInt(phone.substring(0, 1))>0){
                    return false;
                }
                Integer two =Integer.parseInt(phone.substring(0, 2));
                //liuhaibin 20120319 begin
                if(two<3){
                    return false;
                }
                //liuhaibin 20120319 end
                if(two > 01){
                    phone = phone.substring(4, phone.length());
                    //长度不一致
                    if(phone.length() == length[0]){
                        flag=validatePhone(phone, length);
                    }else{
                        flag=false;
                    }
                }
            }else{
                flag=false;
            }
        }
        return flag;

    }
    public static boolean validatePhone(String phone, int...length) {
        // 验证固话
        boolean flag=false;
        String phoneRegex = "^([1-9])\\d{6,7}$";
        if (length != null && length.length > 0) {
            if (length[0] == 7) {
                phoneRegex = "^([1-9])\\d{6}$";  // 7位固话
                flag=validateInput(phoneRegex, phone);
            } else if (length[0] == 8) {
                phoneRegex = "^([1-9])\\d{7}$";  // 8位固话
                flag=validateInput(phoneRegex, phone);
            }
        }
        //判断区号长度是否为空
        if(null ==length || "".equals(length)){
            return false;
        }

        return 	flag;
    }
    public static boolean validateCardNum(String id){
        // 验证身份证号
        String idRegex = "^\\d{14}(\\d{1}|\\d{4}|(\\d{3}[xX]))$";
        return validateInput(idRegex,id);
    }
    /**
     * 验证日期是否满足时间格式（YYYY-MM-DD）
     * @param date 时间
     * @return 时间正确则返回true,否则返回false
     */
    public static boolean validateDate(String date) {
        // 验证时间
        String dateRegex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-9]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";

        return validateInput(dateRegex, date);
    }


    /**
     * 验证手机号码是否合法
     * @param mobile 手机号(手机号包含+86 ,或者以0开头的外地手机，不做其他限制)
     * @return 手机号正确则返回true,否则返回false
     */
    public static boolean validateCustomerMobile(String mobile) {
        // 验证手机号
        String mobileRegex = "^((\\+86)|(86)|(0))?(1)\\d{10}$";
        return validateInput(mobileRegex, mobile);
    }

    /**
     * 验证位身份证
     * @param id
     * @return map
     *   map.get("sex");  String 1:男 2：女
     *   map.get("birthdy") String 生日
     *   map.get("result")  String ture:身份证 false：不满足身份证规则
     */
    public static Map<String,String> validateID(String id){
        Map<String,String> res = new HashMap<String,String>();
        String result = "";
        String sex = "";
        String year = "";
        String month = "";
        String day = "";
        StringBuffer birthday = new StringBuffer();
        try{

            if(id.length() == 18){
                String[] POWERS = new String[]{"7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"};
                String[] PARTIYBIT = new String[]{"1","0","X","9","8","7","6","5","4","3","2"};
                int sum = 0;
                for(int i=0;i<17;i++){
                    if(id.charAt(i) < '0' || id.charAt(i) > '9'){
                        result = "false";
                        break;
                    }else{
                        sum += Integer.valueOf(id.substring(i,i+1))*Integer.valueOf(POWERS[i]);
                    }
                }
                int temp = sum % 11;
                String parityBit = id.substring(17);
                if(PARTIYBIT[temp].equalsIgnoreCase(parityBit)){
                    result = "true";
                    if(Integer.valueOf(id.substring(16,17))%2==0){
                        sex = "2";
                    }else{
                        sex = "1";
                    }
                    year = id.substring(6,10);
                    month = id.substring(10,12);
                    day = id.substring(12,14);
                    birthday.append(year).append("-").append(month).append("-").append(day);
                }else{
                    result = "false";
                }
            }else if(id.length() == 15){
                for(int i=0;i<id.length();i++){
                    if(id.charAt(i)<'0'||id.charAt(i)>'9'){
                        result = "false";
                        break;
                    }
                }
                year = id.substring(6,8);
                month = id.substring(8,10);
                day = id.substring(10,12);
                int sexBit = Integer.valueOf(id.substring(14));

                if(Integer.valueOf(year)< 1 || Integer.valueOf(year) > 90){
                    result = "false";
                }
                if(Integer.valueOf(month)<1 || Integer.valueOf(month) > 12){
                    result = "false";
                }
                if(Integer.valueOf(day) < 1 || Integer.valueOf(day) > 31){
                    result = "false";
                }
                result = "true";
                if("true".equalsIgnoreCase(result)){
                    birthday.append("19").append(year).append("-").append(month).append("-").append(day);
                    if(sexBit%2==0){
                        sex="2";
                    }else{
                        sex="1";
                    }
                }
            }
        }catch(Exception e){
            res.put("sex", "");
            res.put("birthday", "");
            res.put("result","false");
            return res;
        }

        res.put("sex", sex);
        res.put("birthday", birthday.toString());
        res.put("result",result);
        return res;
    }

    /**
     * 验证客户姓名是否合法，只允许输入汉字或英文
     * @param cName 客户姓名
     * @return 客户姓名正确则返回true,否则返回false
     */
    public static boolean validateCustomerName(String cName) {
        // 验证客户姓名
        String cNameRegex = "^([a-zA-Z]|[\u4E00-\u9FA5]|[a-zA-Z]){2,100}$";
        return validateInput(cNameRegex, cName);
    }

    /**
     * 培育文件验证客户姓名，大于1位汉字
     * @param cName 客户姓名
     * @return 客户姓名正确则返回true,否则返回false
     */
    public static boolean checkCName(String cName) {
        String nameRegex = "^([\u4E00-\u9FA5]){2,100}$";
        return validateInput(nameRegex,cName);
    }

    /**
     * 验证输入的字符串是否都是数字，将非数字部分过滤掉，余下数字部分返回。
     * @param str 数字
     * @return 返回纯数字的字符串
     * by liuhaibin 20120319
     */
    public static String validateIsNum(String str) {
        if((str!=null)&&(!"".equals(str))){
            // 验证是否是数字
            String returnNum = "";
            String temStr = "" ;
            int sLength = str.length();
            for(int i = 0 ; i < sLength ; i++){
                temStr = str.substring(i,i+1);
                if(validateInput("^\\d+$",temStr)){
                    returnNum += temStr ;
                }
            }
            return returnNum;
        }
        else{
            return "" ;
        }
    }
    /**
     * 验证输入的号码是否是手机
     * @param sMobile 数字
     * @return 正确则返回true,否则返回false
     * by liuhaibin 20120319
     */
    public static boolean isMobile(String sMobile) {
        if((sMobile!=null)&&(!"".equals(sMobile))){
            int iLength = sMobile.length();
            if(iLength==11){
                if(!"1".equals(sMobile.substring(0,1))){
                    return false ;
                }
            }
            else if(iLength==12){
                if(!("01".equals(sMobile.substring(0,2)))){
                    return false ;
                }
            }
            else{
                return false ;
            }
            return true ;
        }
        else{
            return false ;
        }
    }

    /**
     * 如果输入的字符串左侧有多余1个0，将左侧的0只保留一个。
     * @param str 数字
     * @return 数字
     * by liuhaibin 20120319
     */
    public static String leftOnlyZero(String str) {
        if((str!=null)&&(!"".equals(str))){
            if(str.length()>=2){
                while("00".equals(str.substring(0,2))){
                    if("00".equals(str)){
                        return "0" ;
                    }
                    else{
                        str = str.substring(1,str.length());
                    }

                }
            }
            return str ;
        }
        else{
            return "" ;
        }
    }

    /**
     * 整理号码串，将号码中的非数字过滤掉，将号码最前的0，只保留一个。
     * @param str 数字
     * @return 数字
     * by liuhaibin 20120319
     */
    public static String clearMobile(String str) {//log.info("neusoft-20120325");
        if((str!=null)&&(!"".equals(str))){
            str = leftOnlyZero(validateIsNum(str)) ;
            if(str.length()<=2){
                return str ;
            }
            if((str!=null)&&(!"".equals(str))){
                if(("01".equals(str.substring(0,2)))&&!("010".equals(str.substring(0,3)))&&(str.length()==12)){
                    str = str.substring(1,str.length());
                }
            }
            return str ;
        }
        else{
            return "" ;
        }
    }

    /**
     * 返回
     * @param str 固话号码
     * @return 固话号码区号长度
     * by liuhaibin 20120319
     */
    public static int getAreaCodeLength(String str) {
        if((str!=null)&&(!"".equals(str))){
            if(("02".equals(str.substring(0,2)))||("010".equals(str.substring(0,3)))){
                return 3 ;
            }
            else{
                return 4 ;
            }
        }
        else{
            return 0 ;
        }
    }

    /**
     * 返回
     * @param str 固话号码
     * @return 固话号码区号
     * by liuhaibin 20120319
     */
    public static String getAreaCode(String str) {
        if((str!=null)&&(!"".equals(str))){
            if(("02".equals(str.substring(0,2)))||("010".equals(str.substring(0,3)))){
                return str.substring(0,3) ;
            }
            else{
                return str.substring(0,4) ;
            }
        }
        else{
            return "" ;
        }
    }

    /**
     * 返回
     * @param str 固话号码
     * @return 固话号码区号-固话号码
     * by liuhaibin 20120319
     */
    public static String getFormatTel(String str) {
        if((str!=null)&&(!"".equals(str))){
            return str.substring(0,getAreaCodeLength(str)) + "-" + str.substring(getAreaCodeLength(str),str.length()) ;
        }
        else{
            return "" ;
        }
    }


    /**
     * 验证固话，没有区号。*******
     * @param phone
     * @return
     */
    public static boolean valtel(String phone) {
        // 验证固话
        String phoneRegex = "^([1-9])\\d{6,7}$";
        return validateInput(phoneRegex, phone);
    }
    /**
     * 验证固化，有区号。区号-号码
     * @param phone
     * @return
     */
    public static boolean valareatel(String phone) {
        // 验证固定电话:区号-号码
        String telRegex = "0(10|2[1-3]|\\d{2,3})-\\d{7,8}$";
        return validateInput(telRegex, phone);
    }
    /**
     * 验证固化，有区号。区号号码
     * @param phone
     * @return
     */
    public static boolean valnoareatel(String phone) {
        // 验证固定电话:区号号码
        String telRegex = "0(10|2[1-3]|\\d{2,3})\\d{7,8}";
        return validateInput(telRegex, phone);
    }
    /**
     * 验证车牌，包括正牌和临牌
     * @param vehicleLicense
     * @return
     */
//    public static boolean checkVehicleLicense(String vehicleLicense) {
//    		//新版车牌校验规则，按照分公司配置使用
//    	String vehicleLicenseUnicodeStr = NullProcessUtil.nvlToString(
//    	        ToolsUtil.getValueName(Constant.TYPE_CODE_1664, Constant.TYPE_CODE_1664_VEHICLELICENSEUNICODE), "").trim();
//    	StringBuffer vlRegex=new StringBuffer("^(([\\u4E00-\\u9FA5]{1}[A-Z]{1}\\w{5})");
//    	String[]expressionArray=StringUtils.split(vehicleLicenseUnicodeStr, ",");
//    	for(String expression:expressionArray)
//    	{
//    		vlRegex.append("|(["+expression+"]{1}[A-Z]{1}\\w{6})");
//    	}
//    	vlRegex.append("|((LS)[a-zA-Z0-9]{6}))$");
//
//
//    	return validateInput(vlRegex.toString(), vehicleLicense);
//    }

    /**
     * 验证手机
     * @param mobile
     * @return
     */
    public static String validateEcifMobile(String mobile) {
        // 去空格
        String tel = mobile.trim();
        String telp = null;
        //保留大于等于11位的号码
        if(tel.length()>=11){
            //如果号码前几位包含86或0
            if(validateCustomerMobile(tel)){
                //从右到左取号码的11位
                telp = tel.substring(tel.length()-11, tel.length());
                //如果取得的号码是以1开头的11位，则是正确的号码
                if("1".equals(telp.substring(0, 1))&&telp.length()==11){
                    return "true"+","+telp;
                }
            }
        }
        return "false"+","+mobile;
    }

    /**
     * @name 验证英文和数字
     * @desc 目前此方法应用与名单数据处理导入校验上年数据质量，只能输入英文+数字
     * @param input 输入值
     * @return true、false
     */
    public static boolean valEngAndNumber(String input) {
        // 验证input是英文+数字
        String regex = "^[A-Za-z0-9]+$";
        return validateInput(regex, input);
    }
}
