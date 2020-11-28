package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IOrderAddressService;
import cn.itsource.hrm.domain.OrderAddress;
import cn.itsource.hrm.query.OrderAddressQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderAddress")
public class OrderAddressController {
    @Autowired
    public IOrderAddressService orderAddressService;

    /**
    * 保存和修改公用的
    * @param orderAddress  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody OrderAddress orderAddress){
        try {
            if(orderAddress.getId()!=null){
                orderAddressService.updateById(orderAddress);
            }else{
                orderAddressService.save(orderAddress);
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
            orderAddressService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public OrderAddress get(@PathVariable("id")Long id)
    {
        return orderAddressService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<OrderAddress> list(){

        return orderAddressService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<OrderAddress> json(@RequestBody OrderAddressQuery query)
    {
        Page<OrderAddress> page = new Page<OrderAddress>(query.getPage(),query.getPageSize());
        page = orderAddressService.page(page);
        return new PageList<OrderAddress>(page.getTotal(),page.getRecords());
    }
}
