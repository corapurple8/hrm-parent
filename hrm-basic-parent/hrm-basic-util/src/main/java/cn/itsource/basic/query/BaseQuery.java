package cn.itsource.basic.query;

/**
 * 查询参数的基础封装
 */
public class BaseQuery {
    /**当前页*/
    private Integer page;
    /**每页条数 默认十条*/
    private Integer pageSize = 10;
    /**关键词*/
    private String keyword;

    public BaseQuery(Integer page, Integer pageSize, String keyword) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
    }

    public BaseQuery() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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
