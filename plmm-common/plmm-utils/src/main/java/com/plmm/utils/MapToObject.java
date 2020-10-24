package com.plmm.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapToObject {  
    
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null) {
        	return null;
		}  
  
        Object obj = beanClass.newInstance();  
  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
  
        return obj;  
    }    
      
    public static Map<?, ?> objectToMap(Object obj) {  
        if(obj == null) {
			return null;

		}   
  
        return new org.apache.commons.beanutils.BeanMap(obj);  
    }
    public static String mapInfoToString(Map<?, ?> infoMap){
        StringBuffer sb = new StringBuffer();

        Set<?> entrySet = infoMap.entrySet();
        Iterator<?> it = entrySet.iterator();
        sb.append("?");
        while(it.hasNext()){
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>)it.next();
            String key = (String)entry.getKey();
            String[] value = (String[])entry.getValue();
            sb.append(key + "=" + value[0]);
            if(it.hasNext()){
                sb.append("&");
            }
        }
        return sb.toString();
    }
}  