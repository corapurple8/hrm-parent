package cn.itsource.basic.query;

/**
 * 查询参数的基础封装
 */
public class BaseQuery {
    /**当前页*/
    private Integer pageNum;
    /**每页条数*/
    private Integer pageSize;
    /**关键词*/
    private String keyword;

    public BaseQuery(Integer pageNum, Integer pageSize, String keyword) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.keyword = keyword;
    }

    public BaseQuery() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
