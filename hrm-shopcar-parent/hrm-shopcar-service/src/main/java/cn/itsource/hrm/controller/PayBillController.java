package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IPayBillService;
import cn.itsource.hrm.domain.PayBill;
import cn.itsource.hrm.query.PayBillQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payBill")
public class PayBillController {
    @Autowired
    public IPayBillService payBillService;

    /**
    * 保存和修改公用的
    * @param payBill  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody PayBill payBill){
        try {
            if(payBill.getId()!=null){
                payBillService.updateById(payBill);
            }else{
                payBillService.save(payBill);
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
            payBillService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PayBill get(@PathVariable("id")Long id)
    {
        return payBillService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<PayBill> list(){

        return payBillService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<PayBill> json(@RequestBody PayBillQuery query)
    {
        Page<PayBill> page = new Page<PayBill>(query.getPage(),query.getPageSize());
        page = payBillService.page(page);
        return new PageList<PayBill>(page.getTotal(),page.getRecords());
    }
}
