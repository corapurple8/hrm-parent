package cn.itsource.hrm.doc;

import lombok.Data;

@Data
public class CourseQueryDoc {
    /**关键字all*/
    private String keyword;
    /**课程类型*/
    private Long productType;
    /**价格范围*/
    private String priceMin;
    private String priceMax;
    /**排序字段*/
    private String sortField="xp";
    private String sortType="DESC";
    //分页给默认值
    private Integer page=1;
    private Integer rows=12;
}
