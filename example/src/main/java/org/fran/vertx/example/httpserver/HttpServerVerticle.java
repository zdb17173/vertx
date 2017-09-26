package org.fran.vertx.example.httpserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

/**
 * http://vertx.io/docs/vertx-web/java/
 * Created by fran on 2017/9/25.
 */
public class HttpServerVerticle extends AbstractVerticle {

    HttpServer server;

    @Override
    public void start(Future<Void> future) {
        server = vertx.createHttpServer();
        server.requestHandler(createRouter(vertx)::accept).listen(8080);
    }

    @Override
    public void stop(Future<Void> future) {
        server.close(res -> server.close(future.completer()));
    }

    private Router createRouter(Vertx vertx){

        Router router = Router.router(vertx);

        //Session
        router.route().handler(CookieHandler.create());
        SessionStore store = LocalSessionStore.create(vertx);
        SessionHandler sessionHandler = SessionHandler.create(store);
        router.route().handler(sessionHandler);

        //FreeMarker
        FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();
        engine.setMaxCacheSize(1000);

        //route /fr -> /templates/fr.ftl
        router.get("/fr").handler(ctx -> {
            Session session = ctx.session();
            session.put("foo", "bar");
            ctx.put("a", "aaaa");
            ctx.put("b", 123);
            ctx.next();
        });
        router.get("/fr").handler(ctx -> {
            engine.render(ctx, "/templates/", "fr.ftl", res ->{
                if(res.succeeded()){
                    Buffer buf = res.result();
                    ctx.response()
                            .putHeader("Content-Type", "text/html;charset=utf-8")
                            .putHeader("Content-Length", buf.length()+"")
                            .setStatusCode(200)
                            .write(buf)
                            .end();
                }else{
                    ctx.fail(res.cause());
//                    res.cause().printStackTrace();
//                    System.out.println("error");
                }
            });
        });


        router.get("/fran").handler(ctx -> {
            Object foo = ctx.session().get("foo");
            String out = "hello word fran " + foo;

            ctx.response()
                    .putHeader("Content-Length", out.length() + "")
                    .setStatusCode(200)
                    .write(out)
                    .end();
        });
        router.get("/test").handler(ctx -> {
            ctx.response()
                    .putHeader("Content-Length", "hello word test".length() + "")
                    .setStatusCode(200)
                    .write("hello word test")
                    .end();
        });
        return router;
    }

    public static void main(String[] args){
        Vertx.vertx().deployVerticle(HttpServerVerticle.class.getName());
    }

}
