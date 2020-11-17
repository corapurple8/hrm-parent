package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ISystemDictionaryService;
import cn.itsource.hrm.domain.SystemDictionary;
import cn.itsource.hrm.query.SystemDictionaryQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemDictionary")
@Api(value = "数据字典",tags = "数据字典接口")
public class SystemDictionaryController {
    @Autowired
    public ISystemDictionaryService systemDictionaryService;

    /**
    * 保存和修改公用的
    * @param systemDictionary  传递的实体
    * @return Ajaxresult转换结果
    */
    @ApiOperation(value = "保存数据字典信息",notes = "有id修改 无id添加 ")
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody @ApiParam(name = "数据字典对象",value = "传入json格式",required = true) SystemDictionary systemDictionary){
        try {
            if(systemDictionary.getId()!=null){
                systemDictionaryService.updateById(systemDictionary);
            }else{
                systemDictionaryService.save(systemDictionary);
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
            systemDictionaryService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public SystemDictionary get(@PathVariable("id")Long id)
    {
        return systemDictionaryService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<SystemDictionary> list(){

        return systemDictionaryService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<SystemDictionary> json(@RequestBody SystemDictionaryQuery query)
    {
        Page<SystemDictionary> page = new Page<SystemDictionary>(query.getPageNum(),query.getPageSize());
        page = systemDictionaryService.page(page);
        return new PageList<SystemDictionary>(page.getTotal(),page.getRecords());
    }
}
