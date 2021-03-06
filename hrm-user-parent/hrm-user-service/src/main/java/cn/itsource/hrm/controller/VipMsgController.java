package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipMsgService;
import cn.itsource.hrm.domain.VipMsg;
import cn.itsource.hrm.query.VipMsgQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipMsg")
public class VipMsgController {
    @Autowired
    public IVipMsgService vipMsgService;

    /**
    * 保存和修改公用的
    * @param vipMsg  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipMsg vipMsg){
        try {
            if(vipMsg.getId()!=null){
                vipMsgService.updateById(vipMsg);
            }else{
                vipMsgService.save(vipMsg);
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
            vipMsgService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipMsg get(@PathVariable("id")Long id)
    {
        return vipMsgService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipMsg> list(){

        return vipMsgService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<VipMsg> json(@RequestBody VipMsgQuery query)
    {
        Page<VipMsg> page = new Page<VipMsg>(query.getPage(),query.getPageSize());
        page = vipMsgService.page(page);
        return new PageList<VipMsg>(page.getTotal(),page.getRecords());
    }
}
