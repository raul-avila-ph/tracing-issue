package com.example.tracingissue;

import feign.Logger.Level;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value = "myFeignClient",
        url = "${demo.feign.url}",
        configuration = MyFeignClient.FeignConfig.class
)
public interface MyFeignClient {

    @GetMapping(value = "/say-hello")
    String sayHello();

    class FeignConfig {

        @Bean
        Level feignLoggerLevel() {
            return Level.FULL;
        }
    }
}
