package org.fran.vertx.example.grpc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import org.fran.vertx.example.grpc.proto.GreeterGrpc;
import org.fran.vertx.example.grpc.proto.HelloReply;
import org.fran.vertx.example.grpc.proto.HelloRequest;

import java.io.IOException;

/**
 * http://vertx.io/docs/vertx-grpc/java/
 * Created by fran on 2017/9/27.
 */
public class TestGRPCServer extends AbstractVerticle {

    @Override
    public void start(Future<Void> future) {
        GreeterGrpc.GreeterVertxImplBase service = new GreeterGrpc.GreeterVertxImplBase() {
            @Override
            public void sayHello(HelloRequest request, Future<HelloReply> future) {
                System.out.println("request:["+ request.getName() +"]");
                future.complete(HelloReply.newBuilder().setMessage(request.getName()).build());
            }
        };

        VertxServer rpcServer = VertxServerBuilder
                .forAddress(vertx, "127.0.0.1", 8080)
                .addService(service)
                .build();

        // Start is asynchronous
        try {
            rpcServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Vertx.vertx().deployVerticle(TestGRPCServer.class.getName());
    }
}
