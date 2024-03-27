package com.example.tracingissue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MyService {

    private final MyFeignClient myFeignClient;

    public void doSomething() {
        log.info("Doing something...");
        log.info("Response from feign client: {}", myFeignClient.sayHello());
    }
}
