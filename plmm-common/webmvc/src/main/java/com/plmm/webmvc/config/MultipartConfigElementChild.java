package com.plmm.webmvc.config;

import javax.servlet.MultipartConfigElement;

/**
 * @ProjectName: plmmProject
 * @Description:
 * @Date: 2020/5/9 18:46
 */
public class MultipartConfigElementChild extends MultipartConfigElement {

    public MultipartConfigElementChild(String location, long maxFileSize, long maxRequestSize, int fileSizeThreshold) {
        super(location, maxFileSize, maxRequestSize, fileSizeThreshold);
    }

    public long setMaxFileSize(long maxFileSize) {
        return maxFileSize;
    }

}
