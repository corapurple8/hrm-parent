package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipRealInfoService;
import cn.itsource.hrm.domain.VipRealInfo;
import cn.itsource.hrm.query.VipRealInfoQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipRealInfo")
public class VipRealInfoController {
    @Autowired
    public IVipRealInfoService vipRealInfoService;

    /**
    * 保存和修改公用的
    * @param vipRealInfo  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipRealInfo vipRealInfo){
        try {
            if(vipRealInfo.getId()!=null){
                vipRealInfoService.updateById(vipRealInfo);
            }else{
                vipRealInfoService.save(vipRealInfo);
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
            vipRealInfoService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipRealInfo get(@PathVariable("id")Long id)
    {
        return vipRealInfoService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipRealInfo> list(){

        return vipRealInfoService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<VipRealInfo> json(@RequestBody VipRealInfoQuery query)
    {
        Page<VipRealInfo> page = new Page<VipRealInfo>(query.getPage(),query.getPageSize());
        page = vipRealInfoService.page(page);
        return new PageList<VipRealInfo>(page.getTotal(),page.getRecords());
    }
}
