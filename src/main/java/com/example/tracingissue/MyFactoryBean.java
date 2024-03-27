package com.example.tracingissue;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyFactoryBean implements FactoryBean<MyService> {

    private final MyFeignClient myFeignClient;

    @Override
    public MyService getObject() {
        return new MyService(myFeignClient);
    }

    @Override
    public Class<MyService> getObjectType() {
        return MyService.class;
    }
}
