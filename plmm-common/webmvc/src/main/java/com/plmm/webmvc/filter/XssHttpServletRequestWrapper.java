package com.plmm.webmvc.filter;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.HtmlUtils;


@SuppressWarnings("all")
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper{
	private static final Pattern P_MARKET = Pattern.compile("\"");
    private static final Pattern P_ACUTE = Pattern.compile("\\\\");
    private static final Pattern P_TAGS = Pattern.compile("../|./|/|\\\\|\\(|\\)|%2f|..%2f|.%2f");
    
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	public Map<String,String[]> getParameterMap(){
		//json类型，替换单引号，双引号
		String s=super.getHeader(HttpHeaders.CONTENT_TYPE);
		Map<String,String[]> requestMap=super.getParameterMap();
		Iterator iterator=requestMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry me=(Map.Entry)iterator.next();
			String[] values=(String[])me.getValue();
			for(int i=0;i<values.length;i++){
				values[i]=this.XssClean(values[i]);
				values[i]=P_TAGS.matcher(values[i]).replaceAll("");
				if(s!=null){
					if (s.contains(MediaType.APPLICATION_JSON_VALUE)) {
						values[i]=HtmlUtils.htmlEscape(values[i]);
			        }
				}
			}
		}
		return requestMap;
	}
	
	public String getParameter(String name) {
		String value = super.getParameter(name);
		//json类型，替换单引号，双引号
		String s=super.getHeader(HttpHeaders.CONTENT_TYPE);
		value=P_TAGS.matcher(value).replaceAll("");
		if(s!=null){
			if (s.contains(MediaType.APPLICATION_JSON_VALUE)) {
				value = XssClean(value);
				value=HtmlUtils.htmlEscape(value);
//				value=P_ACUTE.matcher(value).replaceAll("");
				return value;
	        }
		}
        
        if (StringUtils.isNotBlank(value)) {
            value = XssClean(value);
        }else{
        	value="";
        }
        return value;
    }
	
	
	public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }
      //json类型，替换单引号，双引号
  		String s=super.getHeader(HttpHeaders.CONTENT_TYPE);
        for (int i = 0; i < parameters.length; i++) {
        	parameters[i] = XssClean(parameters[i]);
        	parameters[i]=P_TAGS.matcher(parameters[i]).replaceAll("");
        	if(s!=null){
      			if (s.contains(MediaType.APPLICATION_JSON_VALUE)) {
      				parameters[i]=HtmlUtils.htmlEscape(parameters[i]);
      	        }
      		}
        }
        return parameters;
    }
	
	public String getHeader(String name) {
        String value = super.getHeader(name);
        if(value==null) return null;
        value=value.replaceAll("\r|\n", "");
        return XssClean(value);
    }

	public String XssClean(String value){
		XssEscape xss=new XssEscape();
		return xss.filter(value);
	}
	
}
