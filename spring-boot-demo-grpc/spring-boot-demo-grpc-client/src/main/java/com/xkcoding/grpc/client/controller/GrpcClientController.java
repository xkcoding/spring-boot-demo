package com.xkcoding.grpc.client.controller;

import com.xkcoding.grpc.client.service.GrpcClientService;
import com.xkcoding.grpc.protocol.DemoResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengliming
 * @date 2020/3/29
 */
@RestController
public class GrpcClientController {

    private final GrpcClientService grpcClientService;

    public GrpcClientController(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }

    @RequestMapping("/hello")
    public String hello() {
        DemoResponse rpcResponse = grpcClientService.hello();
        return rpcResponse.getMsg();
    }
}
