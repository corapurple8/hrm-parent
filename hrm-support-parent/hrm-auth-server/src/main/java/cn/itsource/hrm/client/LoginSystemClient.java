package cn.itsource.hrm.client;

import cn.itsource.hrm.client.fallback.LoginSystemClientFallbackFacktory;
import cn.itsource.hrm.domain.Permission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "hrm-system",path = "permission",fallbackFactory = LoginSystemClientFallbackFacktory.class)
public interface LoginSystemClient {
    @GetMapping("/getPermissionsByUserId/{loginId}")
    List<Permission> getPermissionsByUserId(@PathVariable("loginId")Long loginId);
}
