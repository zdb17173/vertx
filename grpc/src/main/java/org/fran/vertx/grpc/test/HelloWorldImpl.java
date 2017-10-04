package org.fran.vertx.grpc.test;

import io.grpc.stub.StreamObserver;
import org.fran.vertx.core.proto.GreeterGrpc;
import org.fran.vertx.core.proto.HelloReply;
import org.fran.vertx.core.proto.HelloRequest;

/**
 * Created by fran on 2017/10/4.
 */
public class HelloWorldImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {

        System.out.println("接收到客户端的信息:"+req.getName());
        HelloReply responseInfo = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(responseInfo);
        responseObserver.onCompleted();
    }
}
