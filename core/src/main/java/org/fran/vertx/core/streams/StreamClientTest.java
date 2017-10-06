package org.fran.vertx.core.streams;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

/**
 * Created by fran on 2017/10/6.
 */
public class StreamClientTest extends AbstractVerticle {

    public void start(Future<Void> startFuture) throws Exception {
        NetClientOptions options = new NetClientOptions()
                .setConnectTimeout(10000)
                .setSendBufferSize(32)
                .setReceiveBufferSize(32);
        NetClient client = vertx.createNetClient(options);
        client.connect(1234, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Connected!");
                NetSocket socket = res.result();
                socket.handler(buf ->{
                    try {
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("receiving:" + buf.toString("UTF-8"));
                });
                for(int i = 0 ; i < 1000; i ++){
                    String str = "hello " + i + "\r\n";
                    System.out.println("sending:" + str);
                    socket.write(str);
                }
            } else {
                System.out.println("Failed to connect: " + res.cause().getMessage());
            }
        });
    }

    public static void main(String[] args){
        Vertx.vertx().deployVerticle(StreamClientTest.class.getName());

    }
}
