package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IBannerService;
import cn.itsource.hrm.domain.Banner;
import cn.itsource.hrm.query.BannerQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    public IBannerService bannerService;

    /**
    * 保存和修改公用的
    * @param banner  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Banner banner){
        try {
            if(banner.getId()!=null){
                bannerService.updateById(banner);
            }else{
                bannerService.save(banner);
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
            bannerService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Banner get(@PathVariable("id")Long id)
    {
        return bannerService.getById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Banner> list(){

        return bannerService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public PageList<Banner> json(@RequestBody BannerQuery query)
    {
        Page<Banner> page = new Page<Banner>(query.getPage(),query.getPageSize());
        page = bannerService.page(page);
        return new PageList<Banner>(page.getTotal(),page.getRecords());
    }
}
