package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IOrderCourseService;
import cn.itsource.hrm.domain.OrderCourse;
import cn.itsource.hrm.query.OrderCourseQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderCourse")
public class OrderCourseController {
    @Autowired
    public IOrderCourseService orderCourseService;

    /**
    * 保存和修改公用的
    * @param orderCourse  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody OrderCourse orderCourse){
        try {
            if(orderCourse.getId()!=null){
                orderCourseService.updateById(orderCourse);
            }else{
                orderCourseService.save(orderCourse);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            orderCourseService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public OrderCourse get(@PathVariable("id")Long id)
    {
        return orderCourseService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<OrderCourse> list(){

        return orderCourseService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<OrderCourse> json(@RequestBody OrderCourseQuery query)
    {
        Page<OrderCourse> page = new Page<OrderCourse>(query.getPage(),query.getPageSize());
        page = orderCourseService.page(page);
        return new PageList<OrderCourse>(page.getTotal(),page.getRecords());
    }
}
