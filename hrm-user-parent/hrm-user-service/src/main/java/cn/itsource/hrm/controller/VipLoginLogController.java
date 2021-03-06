package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipLoginLogService;
import cn.itsource.hrm.domain.VipLoginLog;
import cn.itsource.hrm.query.VipLoginLogQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipLoginLog")
public class VipLoginLogController {
    @Autowired
    public IVipLoginLogService vipLoginLogService;

    /**
    * 保存和修改公用的
    * @param vipLoginLog  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipLoginLog vipLoginLog){
        try {
            if(vipLoginLog.getId()!=null){
                vipLoginLogService.updateById(vipLoginLog);
            }else{
                vipLoginLogService.save(vipLoginLog);
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
            vipLoginLogService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipLoginLog get(@PathVariable("id")Long id)
    {
        return vipLoginLogService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipLoginLog> list(){

        return vipLoginLogService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<VipLoginLog> json(@RequestBody VipLoginLogQuery query)
    {
        Page<VipLoginLog> page = new Page<VipLoginLog>(query.getPage(),query.getPageSize());
        page = vipLoginLogService.page(page);
        return new PageList<VipLoginLog>(page.getTotal(),page.getRecords());
    }
}
