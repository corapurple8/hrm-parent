package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipBaseService;
import cn.itsource.hrm.domain.VipBase;
import cn.itsource.hrm.query.VipBaseQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipBase")
public class VipBaseController {
    @Autowired
    public IVipBaseService vipBaseService;

    /**
    * 保存和修改公用的
    * @param vipBase  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipBase vipBase){
        try {
            if(vipBase.getId()!=null){
                vipBaseService.updateById(vipBase);
            }else{
                vipBaseService.save(vipBase);
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
            vipBaseService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipBase get(@PathVariable("id")Long id)
    {
        return vipBaseService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipBase> list(){

        return vipBaseService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<VipBase> json(@RequestBody VipBaseQuery query)
    {
        Page<VipBase> page = new Page<VipBase>(query.getPage(),query.getPageSize());
        page = vipBaseService.page(page);
        return new PageList<VipBase>(page.getTotal(),page.getRecords());
    }
}
