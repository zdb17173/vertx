package org.fran.vertx.core.http;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

/**
 * Created by fran on 2017/9/25.
 */
public class Application {
    public static void main(String[] args){

        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(createRouter(vertx)::accept).listen(8080);

    }

    static Router createRouter(Vertx vertx){

        FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();
        TemplateHandler handler = TemplateHandler.create(engine);

        Router router = Router.router(vertx);
        router.get("/fr").handler(ctx -> {
            ctx.put("a", "aaaa");
            ctx.put("b", 123);
        });
        router.get("/fr").handler(handler);

        router.get("/fran").handler(ctx -> {
            ctx.response()
                    .putHeader("Content-Length", "hello word fran".length() + "")
                    .setStatusCode(200)
                    .write("hello word fran")
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

}
