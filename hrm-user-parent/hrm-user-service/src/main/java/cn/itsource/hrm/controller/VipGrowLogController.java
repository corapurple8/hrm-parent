package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipGrowLogService;
import cn.itsource.hrm.domain.VipGrowLog;
import cn.itsource.hrm.query.VipGrowLogQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipGrowLog")
public class VipGrowLogController {
    @Autowired
    public IVipGrowLogService vipGrowLogService;

    /**
    * 保存和修改公用的
    * @param vipGrowLog  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipGrowLog vipGrowLog){
        try {
            if(vipGrowLog.getId()!=null){
                vipGrowLogService.updateById(vipGrowLog);
            }else{
                vipGrowLogService.save(vipGrowLog);
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
            vipGrowLogService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipGrowLog get(@PathVariable("id")Long id)
    {
        return vipGrowLogService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipGrowLog> list(){

        return vipGrowLogService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<VipGrowLog> json(@RequestBody VipGrowLogQuery query)
    {
        Page<VipGrowLog> page = new Page<VipGrowLog>(query.getPage(),query.getPageSize());
        page = vipGrowLogService.page(page);
        return new PageList<VipGrowLog>(page.getTotal(),page.getRecords());
    }
}
