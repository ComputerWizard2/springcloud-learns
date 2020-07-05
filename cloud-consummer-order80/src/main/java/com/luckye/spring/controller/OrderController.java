package com.luckye.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {
    public static final String INVOKE_URL="http://cloud-provider-payment";
    @Resource
    private RestTemplate restTemplate;
    @RequestMapping("/getzk")
    public String getZk(){

        return restTemplate.getForObject(INVOKE_URL+"/payment/zk",String.class);
    }

}
