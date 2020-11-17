package cn.itsource.basic.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据的封装
 * @param <T>
 */
public class PageList<T> {

    /**查询总数*/
    private Long total;
    /**分页数据*/
    private List<T> rows =new ArrayList();

    public PageList() {
    }

    public PageList(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
