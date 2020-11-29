package cn.itsource.hrm.client;

import cn.itsource.hrm.client.fallback.LoginSystemClientFallbackFacktory;
import cn.itsource.hrm.domain.Permission;
import cn.itsource.hrm.security.Oauth2FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 调用接口  只拦截这一个请求 所以拦截器没有全局设置
 */
//@FeignClient(value = "hrm-system",path = "permission",fallbackFactory = LoginSystemClientFallbackFacktory.class,configuration = Oauth2FeignRequestInterceptor.class)
public interface LoginSystemClient {
    @GetMapping("/getPermissionsByUserId/{loginId}")
    List<Permission> getPermissionsByUserId(@PathVariable("loginId")Long loginId);
}
