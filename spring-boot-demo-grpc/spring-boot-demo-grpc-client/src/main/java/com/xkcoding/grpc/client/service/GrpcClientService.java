package com.xkcoding.grpc.client.service;

import com.xkcoding.grpc.protocol.DemoRequest;
import com.xkcoding.grpc.protocol.DemoResponse;
import com.xkcoding.grpc.protocol.DemoServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author dengliming
 * @date 2020/3/29
 */
@Service
public class GrpcClientService {

    @GrpcClient("demo-grpc-server")
    private DemoServiceGrpc.DemoServiceBlockingStub demoServiceBlockingStub;

    public DemoResponse hello() {
        return demoServiceBlockingStub.hello(DemoRequest.newBuilder().build());
    }
}
