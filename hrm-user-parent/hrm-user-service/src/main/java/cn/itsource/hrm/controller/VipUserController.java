package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipUserService;
import cn.itsource.hrm.domain.VipUser;
import cn.itsource.hrm.query.VipUserQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipUser")
public class VipUserController {
    @Autowired
    public IVipUserService vipUserService;

    /**
    * 保存和修改公用的
    * @param vipUser  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipUser vipUser){
        try {
            if(vipUser.getId()!=null){
                vipUserService.updateById(vipUser);
            }else{
                vipUserService.save(vipUser);
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
            vipUserService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipUser get(@PathVariable("id")Long id)
    {
        return vipUserService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipUser> list(){

        return vipUserService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<VipUser> json(@RequestBody VipUserQuery query)
    {
        Page<VipUser> page = new Page<VipUser>(query.getPage(),query.getPageSize());
        page = vipUserService.page(page);
        return new PageList<VipUser>(page.getTotal(),page.getRecords());
    }
}
