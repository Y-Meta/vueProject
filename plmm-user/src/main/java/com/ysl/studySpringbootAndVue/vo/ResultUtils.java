package com.ysl.studySpringbootAndVue.vo;

public class ResultUtils {


    public Object data;
    public static ResultUtils succeed(Object data){
        ResultUtils resultUtils = new ResultUtils();
        resultUtils.setData(data);
        return resultUtils;
    }



    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
