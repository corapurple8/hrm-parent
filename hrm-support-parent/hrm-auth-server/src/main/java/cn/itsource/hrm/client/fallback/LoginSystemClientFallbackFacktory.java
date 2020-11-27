package cn.itsource.hrm.client.fallback;

import cn.itsource.hrm.client.LoginSystemClient;
import cn.itsource.hrm.domain.Permission;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component
public class LoginSystemClientFallbackFacktory implements FallbackFactory<LoginSystemClient> {
    @Override
    public LoginSystemClient create(Throwable throwable) {
        return new LoginSystemClient() {
            @Override
            public List<Permission> getPermissionsByUserId(Long loginId) {
                return Collections.emptyList();
            }
        };

    }
}
