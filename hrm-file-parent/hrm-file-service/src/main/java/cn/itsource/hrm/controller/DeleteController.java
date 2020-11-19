package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.util.FastDfsApiOpr;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delete")
@Api(value = "删除文件",tags = "删除文件")
public class DeleteController {
    /**
     * 参数：完整路径 /goup1/xxxxx/yyyy
     * 返回值：成功与否，还要返回地址
     * 删除
     * 前端请求地址：GET  地址?参数名称=值
     * 后端：@GetMapping("/地址")
     *      方法名称(String 参数名称)
     *
     * 前端请求地址：GET  地址/值
     * 后端：@GetMapping("/地址/{变量}")
     *      方法名称(@PathVariable("路径变量") String 参数名称)
     *
     *
     */
    @GetMapping("/fastdfs")
    public AjaxResult del(String path){
        String pathTmp = path.substring(1); // goup1/xxxxx/yyyy
        String groupName =  pathTmp.substring(0, pathTmp.indexOf("/")); //goup1
        String remotePath = pathTmp.substring(pathTmp.indexOf("/")+1);// /xxxxx/yyyy
        System.out.println(groupName);
        System.out.println(remotePath);
        FastDfsApiOpr.delete(groupName, remotePath);
        return  AjaxResult.me();
    }
}
