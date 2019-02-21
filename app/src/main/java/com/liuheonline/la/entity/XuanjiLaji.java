package com.liuheonline.la.entity;

import java.util.List;

/**
 * Auther: RyanLi
 * Data: 2018-09-30 16:02
 * Description:
 */
public class XuanjiLaji {


    private boolean Result;
    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private int recordCount;
    private List<XuanJiEntity> items;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public List<XuanJiEntity> getItems() {
        return items;
    }

    public void setItems(List<XuanJiEntity> items) {
        this.items = items;
    }

}
