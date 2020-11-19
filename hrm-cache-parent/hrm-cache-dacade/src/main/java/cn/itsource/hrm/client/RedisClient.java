package cn.itsource.hrm.client;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.fallback.RedisClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cache-server",path = "redis",fallbackFactory = RedisClientFallbackFactory.class)
public interface RedisClient {

    @GetMapping("/set")
    AjaxResult set(@RequestParam("key") String key,
                          @RequestParam("value") String value,
                          @RequestParam(value = "expire",required = false) Integer expire);
    /**
     * 根据键获取值
     * @param key
     * @return
     */
    @GetMapping("/get/{key}")
    AjaxResult get(@PathVariable("key")String key);

    /**
     * 删除
     * @param key
     * @return
     */
    @DeleteMapping("/del/{key}")
    AjaxResult del(@PathVariable("key")String key);

}
