package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ISystemdictionaryitemService;
import cn.itsource.hrm.domain.Systemdictionaryitem;
import cn.itsource.hrm.query.SystemdictionaryitemQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemdictionaryitem")
public class SystemdictionaryitemController {
    @Autowired
    public ISystemdictionaryitemService systemdictionaryitemService;

    /**
    * 保存和修改公用的
    * @param systemdictionaryitem  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Systemdictionaryitem systemdictionaryitem){
        try {
            if(systemdictionaryitem.getId()!=null){
                systemdictionaryitemService.updateById(systemdictionaryitem);
            }else{
                systemdictionaryitemService.save(systemdictionaryitem);
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
            systemdictionaryitemService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Systemdictionaryitem get(@PathVariable("id")Long id)
    {
        return systemdictionaryitemService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Systemdictionaryitem> list(){

        return systemdictionaryitemService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<Systemdictionaryitem> json(@RequestBody SystemdictionaryitemQuery query)
    {
        Page<Systemdictionaryitem> page = new Page<Systemdictionaryitem>(query.getPage(),query.getPageSize());
        page = systemdictionaryitemService.page(page);
        return new PageList<Systemdictionaryitem>(page.getTotal(),page.getRecords());
    }

    /**
     * 根据数据字典目录的sn去查询明细
     * @param sn
     * @return
     */
    @GetMapping("/listBySn/{sn}")
    public AjaxResult listBySn(@PathVariable("sn")String sn){
        List<Systemdictionaryitem> items = systemdictionaryitemService.listBySn(sn);
        return AjaxResult.me().setResultObj(items);

    }
}
