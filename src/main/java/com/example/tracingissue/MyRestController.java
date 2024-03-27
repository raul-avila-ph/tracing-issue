package com.example.tracingissue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyRestController {

    private final MyService myService;

    @GetMapping("/greetings")
    String greetings() {
        log.info("Within greetings method");
        myService.doSomething();
        return "Hi there!";
    }
}
