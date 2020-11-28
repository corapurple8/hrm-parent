package cn.itsource.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients//启用feign客户端
public class ShopcarServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopcarServiceApplication.class);
    }
}
