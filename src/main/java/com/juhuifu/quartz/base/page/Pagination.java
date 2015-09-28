package com.juhuifu.quartz.base.page;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable {

    /** */
    private static final long serialVersionUID = 8005365053141216310L;

    private List<T> result;

    private int record;

    private int page;

    private int pageSize;

    public Pagination(){
        
    }
    
    public Pagination(int page, int pageSize, int record,List<T> result) {
        super();
        this.result = result;
        this.record = record;
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getTotal(){
        
        return (int)Math.ceil((double) getRecord() / (double) getPageSize());
    }
    
    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
