package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IPermissionService;
import cn.itsource.hrm.domain.Permission;
import cn.itsource.hrm.query.PermissionQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    public IPermissionService permissionService;

    /**
    * 保存和修改公用的
    * @param permission  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Permission permission){
        try {
            if(permission.getId()!=null){
                permissionService.updateById(permission);
            }else{
                permissionService.save(permission);
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
            permissionService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Permission get(@PathVariable("id")Long id)
    {
        return permissionService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Permission> list(){

        return permissionService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<Permission> json(@RequestBody PermissionQuery query)
    {
        Page<Permission> page = new Page<Permission>(query.getPageNum(),query.getPageSize());
        page = permissionService.page(page);
        return new PageList<Permission>(page.getTotal(),page.getRecords());
    }
}
