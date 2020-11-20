package cn.itsource.hrm.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

/**
 * 用户查询关键字+前台展示内容
 */
@Document(indexName = "hrm",type = "course")
@Data
public class CourseDoc {
    @Id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    /**
     * 关键字查询 ： 课程名称  课程描述
     *
     * 前端:java学习  Java  学习
     *
     * 课程名称：Java提高课程   Java  提高  课程
     * 课程描述：提供Java高级课程的学习  提供  Java  高级  课程  学习
     *
     * all = 课程名称 课程描述
     * all = Java提高课程 提供Java高级课程的学习
     *
     */
    private String all; //这个字段的内容是：  name + description
    /**
     * 课程名称
     */
    @Field(type =FieldType.Keyword,store = true,index = false)
    private String name;
    /**
     * 适用人群
     */
    @Field(type =FieldType.Keyword,store = true,index = false)
    private String forUser;
    /**
     * 课程大分类
     */
    @Field(type =FieldType.Long, store = true)
    private Long courseTypeId;

    @Field(type =FieldType.Keyword,store = true)
    private String gradeName;
    /**
     * 课程等级
     */
    @Field(type =FieldType.Long, store = true)
    private Long gradeId;
    /**
     * 教育机构
     */
    @Field(type =FieldType.Long, store = true)
    private Long tenantId;
    @Field(type =FieldType.Text, store = true,analyzer = "ik_max_word")
    private String tenantName;
    @Field(type = FieldType.Long,store = true)
    private Long startTime;
    @Field(type = FieldType.Long,store = true)
    private Long endTime;
    @Field(type =FieldType.Keyword, store = true)
    private String pic;
    /**
     * 收费规则，对应数据字典,免费，收费
     */
    @Field(type = FieldType.Integer,store = true)
    private Integer charge;
    /**
     * 原价
     */
    @Field(type = FieldType.Float,store = true)
    private Float priceOld;
    /**
     * 价格
     */
    @Field(type = FieldType.Float,store = true)
    private Float price;
    /**
     * 简介
     */
    @Field(type =FieldType.Keyword)
    private String description;

    //排序条件
    //上线时间
    private Long onlineTime;
    //销量
    private int saleCount;
    //评论数
    private int commentCount;

}
