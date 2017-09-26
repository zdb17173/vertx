package org.fran.vertx.example.httpclient;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * http://vertx.io/docs/vertx-web-client/java/
 * Created by fran on 2017/9/26.
 */
public class TestHttpClient {

    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        WebClientOptions options = new WebClientOptions()
                .setUserAgent("My-App/1.2.3");
        options.setKeepAlive(false);
        WebClient client = WebClient.create(vertx,options);

        client.get(80, "mapi.cgtn.com","/mobileapp/v2/home/list")
                .putHeader("Accept","application/json")
                .timeout(5000)
                .addQueryParam("curPage", "1")
                .send(ar->{
                    if(ar.succeeded()){
                        HttpResponse<Buffer> response = ar.result();
                        Buffer buffer = response.body();
//                        System.out.println(buffer.getString(0, buffer.length()));
                        JsonObject jsonObject = new JsonObject(buffer);
                        Map<String, Object> res = jsonObject.getMap();
                        System.out.println(res);
                    }else{
                        if(ar.cause() instanceof TimeoutException){
                            System.out.println("timeout");
                        }else{
                            ar.cause().printStackTrace();
                            System.out.println("Something went wrong " + ar.cause().getMessage());
                        }

                    }
                    vertx.close();
                });
    }
}
