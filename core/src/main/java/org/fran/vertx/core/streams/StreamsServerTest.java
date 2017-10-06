package org.fran.vertx.core.streams;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

/**
 * Created by fran on 2017/10/6.
 */
public class StreamsServerTest extends AbstractVerticle{

    public void start(Future<Void> startFuture) throws Exception {
        NetServer server = vertx.createNetServer(
                new NetServerOptions()
                        .setPort(1234)
                        .setHost("localhost")
        );
        server.connectHandler(sock -> {
            sock.setWriteQueueMaxSize(10);

            sock.handler(buffer -> {
                sock.write(buffer);
                if (sock.writeQueueFull()) {
                    System.out.println("writeQueueFull");
                    sock.pause();
                    sock.drainHandler(done -> {
                        System.out.println("resume");
                        sock.resume();
                    });
                }
            });
        }).listen();
    }

    public static void main(String[] args){
        Vertx.vertx().deployVerticle(StreamsServerTest.class.getName());

    }
}
