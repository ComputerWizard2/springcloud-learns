package com.luckye.controller;

import com.luckye.entities.CommonResult;
import com.luckye.entities.Payment;
import com.luckye.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("payment")
class PaymentController {

    @Autowired
    private PaymentService payementService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/create")
    public CommonResult createPayment(@RequestBody Payment payment) {
        boolean flag = payementService.create(payment);
       log.debug("=======>插入的结果：" + flag);
        if (flag) {
            return new CommonResult(200, "插入一条账单成功"+serverPort);
        } else {
            return new CommonResult(446, "插入一条流水数据失败"+serverPort);
        }


    }
    @ResponseBody
    @GetMapping(value="/get/{id}")
    public CommonResult findPaymentById(@PathVariable("id") Long id) {

        Payment paymentById = payementService.findPaymentById(id);
        log.info("查询结果" + paymentById);

        if (paymentById != null) {

            return new CommonResult(200, serverPort+"成功", paymentById);

        }

        return new CommonResult(433, serverPort+"失败");


    }

    @DeleteMapping()
    public CommonResult deletePaymentById(@PathVariable("id") Long id) {
        boolean b = payementService.deletPaymentById(id);
        if (b) {
            return new CommonResult(200, "删除成功");
        }
        return new CommonResult(400, "删除失败");

    }
    @RequestMapping("/discovery")
    @ResponseBody
    public Object discovery(){

        final List<String> services = discoveryClient.getServices();
        log.info("======service的列表==========");
        log.info(services.toString());
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        log.info("========instance的列表=========");
        log.info(instances.toString());
        for (ServiceInstance instance : instances) {
            log.info(instance.getHost()+"主机名");
            log.info(instance.getPort()+"端口号");
            log.info(instance.getUri()+"uri的路径");
        }






        return services;

    }

}
