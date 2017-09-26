package org.fran.vertx.example.mysql;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http://vertx.io/docs/vertx-mysql-postgresql-client/java/
 * Created by fran on 2017/9/26.
 */
public class TestMySqlClient {

    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();

        JsonObject mySQLClientConfig = new JsonObject()
                .put("host", "localhost")
                .put("port", 3306)
                .put("maxPoolSize", 50)
                .put("username", "root")
                .put("password", "zdbzdb1")
                .put("database", "birman")
                .put("charset", "utf-8")
                .put("queryTimeout", 10000);

        SQLClient client = MySQLClient.createShared(vertx, mySQLClientConfig, "franPool");

        client.getConnection(con ->{
           if(con.succeeded()){
               SQLConnection conn = con.result();
               String sql = "select * from publish_news";
               
               conn
                   .setQueryTimeout(5000)
                   .query(sql, res -> {
                       if(res.succeeded()){
                           ResultSet resSet = res.result();
                           List<JsonObject> rows = resSet.getRows();
                           System.out.println(resSet.getNumRows());
                           for(int i = 0; i < rows.size(); i ++){
                               JsonObject json = rows.get(i);
                               Map<String, Object> map = json.getMap();
                               System.out.println(map);
                           }
                       }else{
                           res.cause().printStackTrace();
                       }
               });
           }else {
               con.cause().printStackTrace();
           }
       });

    }
}
