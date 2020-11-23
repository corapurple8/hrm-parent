package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.doc.CourseDoc;
import cn.itsource.hrm.doc.CourseQueryDoc;
import cn.itsource.hrm.repository.CourseDocRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courseDoc")
public class CourseDocController {

    @Autowired
    private CourseDocRepository repository;
    private SortBuilders sortBuilders;

    /**
     * es批量上线
     * @param courseDocList
     * @return
     */
    @PostMapping("/saveBatch")
    public AjaxResult saveBatch(@RequestBody List<CourseDoc> courseDocList){
        repository.saveAll(courseDocList);
        return AjaxResult.me();
    }

    /**
     * 批量es下线删除
     * @param courseDocList
     * @return
     */
    @PostMapping("/deleteBatch")
    public AjaxResult delBatch(@RequestBody List<CourseDoc> courseDocList){
        repository.deleteAll(courseDocList);
        return AjaxResult.me();
    }

    /**
     * 高级查询课程 前台展示
     * @param queryDoc
     * @return
     */
    @PostMapping("/queryCoursesOnline")
    public AjaxResult queryCourses(CourseQueryDoc queryDoc){
        //创建query
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //查询
        //boolquery
        BoolQueryBuilder bool = new BoolQueryBuilder();
        //添加过滤
        List<QueryBuilder> filter = bool.filter();
        //条件判断
        if (StringUtils.isNotBlank(queryDoc.getKeyword())){
            filter.add(new MatchQueryBuilder("all",queryDoc.getKeyword()));
        }
        if (queryDoc.getProductType()!=null){
            filter.add(new TermQueryBuilder("courseTypeId",queryDoc.getProductType()));
        }
        //过滤
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("price");
        if (queryDoc.getPriceMin()!=null){
            rangeQueryBuilder.gte(queryDoc.getPriceMin());
        }
        if (queryDoc.getPriceMax()!=null){
            rangeQueryBuilder.lte(queryDoc.getPriceMax());
        }
        filter.add(rangeQueryBuilder);
        builder.withQuery(bool);

        //排序
        String sortField = queryDoc.getSortField();
        String filedName = null;
        switch (sortField){
            case "xl":
                filedName = "saleCount";
                break;
            case "xp":
                filedName = "onlineTime";
                break;
            case "pl":
                filedName = "commentCount";
                break;
            case "jg":
                filedName = "price";
                break;
            default:
                filedName = "saleCount";
                break;
        }

        SortOrder type = null;
        if (queryDoc.getSortType().equals("DESC")||queryDoc.getSortType().equals("desc")){
            type = SortOrder.DESC;
        }else {
            type = SortOrder.ASC;
        }
        builder.withSort(new FieldSortBuilder(filedName).order(type));
        //分页
        builder.withPageable(PageRequest.of(queryDoc.getPage()-1,queryDoc.getRows()));
        //查询
        Page<CourseDoc> page = repository.search(builder.build());
        PageList<CourseDoc> pageList = new PageList<>();
        pageList.setTotal(page.getTotalElements());
        pageList.setRows(page.getContent());
        return AjaxResult.me().setResultObj(pageList);
    }


}
