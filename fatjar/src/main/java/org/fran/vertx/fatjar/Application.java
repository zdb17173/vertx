package org.fran.vertx.fatjar;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;

/**
 * Created by fran on 2017/9/27.
 */
public class Application extends AbstractVerticle{

    HttpServer server;

    @Override
    public void start(Future<Void> future) {
        server = vertx.createHttpServer();
        server.requestHandler(createRouter(vertx)::accept).listen(8080);
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        server.close(res -> server.close(stopFuture.completer()));
    }

    private Router createRouter(Vertx vertx){
        Router router = Router.router(vertx);
        router.get("/").handler(ctx -> {
            String welcome = "welcome";
            ctx.response()
                    .putHeader("content-type","text/html")
                    .putHeader("Content-Length", welcome.length() + "")
                    .setStatusCode(200)
                    .write(welcome)
                    .end();
        });

        return router;
    }


    public static void main(String[] args){
        Vertx.vertx().deployVerticle(Application.class.getName());
    }
}
