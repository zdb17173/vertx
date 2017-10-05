package org.fran.vertx.example.grpc;

import io.grpc.ManagedChannel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxChannelBuilder;
import org.fran.vertx.example.grpc.proto.GreeterGrpc;
import org.fran.vertx.example.grpc.proto.HelloRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * http://vertx.io/docs/vertx-grpc/java/
 * Created by fran on 2017/9/27.
 */
public class TestGRPCClient extends AbstractVerticle {

    ManagedChannel channel;

    @Override
    public void start(Future<Void> future) {

        channel = VertxChannelBuilder
                .forAddress(vertx, "127.0.0.1", 8080)
                .usePlaintext(true)
                .build();

        sendMessage();
        try {
            shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown();
    }

    public void sendMessage(){
        // Get a stub to use for interacting with the remote service
        GreeterGrpc.GreeterVertxStub stub = GreeterGrpc.newVertxStub(channel);
        HelloRequest request = HelloRequest.newBuilder().setName("Julien").build();

        // Call the remote service
        stub.sayHello(request, ar -> {
            if (ar.succeeded()) {
                System.out.println("Got the server response: " + ar.result().getMessage());
            } else {
                System.out.println("Coult not reach server " + ar.cause().getMessage());
            }
        });
    }


    public static void main(String[] args){

        Vertx.vertx().deployVerticle(TestGRPCClient.class.getName());

    }
}
