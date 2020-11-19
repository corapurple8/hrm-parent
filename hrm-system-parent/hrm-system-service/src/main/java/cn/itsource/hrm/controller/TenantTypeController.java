package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ITenantTypeService;
import cn.itsource.hrm.domain.TenantType;
import cn.itsource.hrm.query.TenantTypeQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenantType")
public class TenantTypeController {
    @Autowired
    public ITenantTypeService tenantTypeService;

    /**
    * 保存和修改公用的
    * @param tenantType  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody TenantType tenantType){
        try {
            if(tenantType.getId()!=null){
                tenantTypeService.updateById(tenantType);
            }else{
                tenantTypeService.save(tenantType);
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
            tenantTypeService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public TenantType get(@PathVariable("id")Long id)
    {
        return tenantTypeService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<TenantType> list(){

        return tenantTypeService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<TenantType> json(@RequestBody TenantTypeQuery query)
    {
        Page<TenantType> page = new Page<TenantType>(query.getPage(),query.getPageSize());
        page = tenantTypeService.page(page,
                new QueryWrapper<TenantType>()
                        .like("name",query.getKeyword())
                        .or()
                        .like("description",query.getKeyword()));
        return new PageList<TenantType>(page.getTotal(),page.getRecords());
    }
}
