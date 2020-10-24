package com.plmm.common.base;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalPage; //总页数

    private int totalSize;//总条数

    private int pageSize;//每页条数

    private int currentPage; //当前页数

    private List<T> list; //当前数据

    private int offset; //查询起始行


    public int getOffset() {
        return (this.currentPage -1 ) *pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;

    }

    //计算总页数
    public int getTotalPage() {
        if(totalSize<0){
            totalSize = 0;
        }
        return (totalSize-1)/pageSize + 1;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageSize() {
        return pageSize ;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage<1?1:currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page() {
    }
    public Page(int currentPage,int pageSize){
        setCurrentPage(currentPage);
        setPageSize(pageSize);
    }

}
