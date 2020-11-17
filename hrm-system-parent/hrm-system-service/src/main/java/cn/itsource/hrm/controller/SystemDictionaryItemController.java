package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ISystemDictionaryItemService;
import cn.itsource.hrm.domain.SystemDictionaryItem;
import cn.itsource.hrm.query.SystemDictionaryItemQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemDictionaryItem")
public class SystemDictionaryItemController {
    @Autowired
    public ISystemDictionaryItemService systemDictionaryItemService;

    /**
    * 保存和修改公用的
    * @param systemDictionaryItem  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody SystemDictionaryItem systemDictionaryItem){
        try {
            if(systemDictionaryItem.getId()!=null){
                systemDictionaryItemService.updateById(systemDictionaryItem);
            }else{
                systemDictionaryItemService.save(systemDictionaryItem);
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
            systemDictionaryItemService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public SystemDictionaryItem get(@PathVariable("id")Long id)
    {
        return systemDictionaryItemService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<SystemDictionaryItem> list(){

        return systemDictionaryItemService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<SystemDictionaryItem> json(@RequestBody SystemDictionaryItemQuery query)
    {
        Page<SystemDictionaryItem> page = new Page<SystemDictionaryItem>(query.getPageNum(),query.getPageSize());
        page = systemDictionaryItemService.page(page);
        return new PageList<SystemDictionaryItem>(page.getTotal(),page.getRecords());
    }
}
