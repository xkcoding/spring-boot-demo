package com.xkcoding.grpc.server.service;

import com.xkcoding.grpc.protocol.DemoRequest;
import com.xkcoding.grpc.protocol.DemoResponse;
import com.xkcoding.grpc.protocol.DemoServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author dengliming
 * @date 2020/3/29
 */
@GrpcService
public class GrpcServerService extends DemoServiceGrpc.DemoServiceImplBase {

    @Override
    public void hello(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {
        DemoResponse response = DemoResponse.newBuilder().setMsg("Hi~").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
