package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipCourseViewService;
import cn.itsource.hrm.domain.VipCourseView;
import cn.itsource.hrm.query.VipCourseViewQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipCourseView")
public class VipCourseViewController {
    @Autowired
    public IVipCourseViewService vipCourseViewService;

    /**
    * 保存和修改公用的
    * @param vipCourseView  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipCourseView vipCourseView){
        try {
            if(vipCourseView.getId()!=null){
                vipCourseViewService.updateById(vipCourseView);
            }else{
                vipCourseViewService.save(vipCourseView);
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
            vipCourseViewService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipCourseView get(@PathVariable("id")Long id)
    {
        return vipCourseViewService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipCourseView> list(){

        return vipCourseViewService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<VipCourseView> json(@RequestBody VipCourseViewQuery query)
    {
        Page<VipCourseView> page = new Page<VipCourseView>(query.getPage(),query.getPageSize());
        page = vipCourseViewService.page(page);
        return new PageList<VipCourseView>(page.getTotal(),page.getRecords());
    }
}
