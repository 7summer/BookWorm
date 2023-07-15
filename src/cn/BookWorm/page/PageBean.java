package cn.BookWorm.page;

import java.util.List;

/**
 * 分页Bean，会在各层之间传递
 */
public class PageBean {
    private int pageCode; //当前页码
    private int totalRecord; //总记录数
    private int pageSize; //每页记录数
    private int totalPage; //页数
    private String url; //请求路径和参数
    private List beanList;

    //得到总页数
    public int getTotalPage()
    {
        int totalPage = totalRecord/pageSize;
        return totalRecord % pageSize == 0 ? totalPage : totalPage+1;
    }

    public int getPageCode() {
        return pageCode;
    }

    public void setPageCode(int pageCode) {
        this.pageCode = pageCode;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List getBeanList() {
        return beanList;
    }

    public void setBeanList(List beanList) {
        this.beanList = beanList;
    }
}
