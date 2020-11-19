package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.util.RedisUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    /**
     * 设置值
     * @param key
     * @param value
     * @param expire null表示永不超时
     * @return
     */
    @GetMapping("/set")
    public AjaxResult set(@ApiParam("键") @RequestParam("key") String key,
                          @ApiParam("值") @RequestParam("value") String value,
                          @ApiParam("过期时间") @RequestParam(value = "expire",required = false) Integer expire){
        RedisUtil.set(key, value, expire);
        return AjaxResult.me();
    }

    /**
     * 根据键获取值
     * @param key
     * @return
     */
    @GetMapping("/get/{key}")
    public AjaxResult get(@PathVariable("key")@ApiParam("键")String key){
        String value = RedisUtil.get(key);
        return AjaxResult.me().setResultObj(value);
    }

    /**
     * 删除
     * @param key
     * @return
     */
    @DeleteMapping("/del/{key}")
    public AjaxResult del(@PathVariable("key")@ApiParam("键")String key){
        RedisUtil.delete(key);
        return AjaxResult.me();
    }
}
