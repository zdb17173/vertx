package org.fran.vertx.web;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.Router;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Server extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    runExample(Server.class.getName());
  }

  public static void runExample(String verticleID) {
    VertxOptions options = new VertxOptions();


    MessagePassingQueue.Consumer<Vertx> runner = vertx -> {
      vertx.deployVerticle(verticleID);
    };


    Vertx vertx = Vertx.vertx(options);
    runner.accept(vertx);
  }

  @Override
  public void start() throws Exception {

    Router router = Router.router(vertx);

    router.route().handler(routingContext -> {
      routingContext.response().putHeader("content-type", "text/html").end("Hello World!");
    });

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
  }
}