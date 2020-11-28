package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ICarCourseService;
import cn.itsource.hrm.domain.CarCourse;
import cn.itsource.hrm.query.CarCourseQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carCourse")
public class CarCourseController {
    @Autowired
    public ICarCourseService carCourseService;

    /**
    * 保存和修改公用的
    * @param carCourse  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody CarCourse carCourse){
        try {
            if(carCourse.getId()!=null){
                carCourseService.updateById(carCourse);
            }else{
                carCourseService.save(carCourse);
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
            carCourseService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CarCourse get(@PathVariable("id")Long id)
    {
        return carCourseService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<CarCourse> list(){

        return carCourseService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<CarCourse> json(@RequestBody CarCourseQuery query)
    {
        Page<CarCourse> page = new Page<CarCourse>(query.getPage(),query.getPageSize());
        page = carCourseService.page(page);
        return new PageList<CarCourse>(page.getTotal(),page.getRecords());
    }
}
