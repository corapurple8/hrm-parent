package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IShopcarService;
import cn.itsource.hrm.domain.Shopcar;
import cn.itsource.hrm.query.ShopcarQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopcar")
public class ShopcarController {
    @Autowired
    public IShopcarService shopcarService;

    /**
    * 保存和修改公用的
    * @param shopcar  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Shopcar shopcar){
        try {
            if(shopcar.getId()!=null){
                shopcarService.updateById(shopcar);
            }else{
                shopcarService.save(shopcar);
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
            shopcarService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Shopcar get(@PathVariable("id")Long id)
    {
        return shopcarService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Shopcar> list(){

        return shopcarService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<Shopcar> json(@RequestBody ShopcarQuery query)
    {
        Page<Shopcar> page = new Page<Shopcar>(query.getPage(),query.getPageSize());
        page = shopcarService.page(page);
        return new PageList<Shopcar>(page.getTotal(),page.getRecords());
    }
}
