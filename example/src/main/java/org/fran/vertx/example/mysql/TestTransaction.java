package org.fran.vertx.example.mysql;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

/**
 * Created by fran on 2017/9/27.
 */
public class TestTransaction {

    public static void main(String[] args) {
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

        client.getConnection(con -> {
            if (con.failed()) {
                con.cause().printStackTrace();
            }

            startTx(con.result(), beginTrans -> {

                String sql1 = "insert into `app_config` (`name`, `value`, `channel`, `ver`, `os`, `status`) values('ccc1','111','1','1','1','0');";
                String sql2 = "insert into `app_config` (`name`, `value`, `channel`, `ver`, `os`, `status`) values('ccc2','111','1','1','1','0');";

                execute(con.result(), sql1, insert1 -> {
                    execute(con.result(), sql2, insert2 -> {

                        if(insert1.failed() || insert2.failed()){
                            rollbackTx(con.result(), back ->{
                                System.out.println("rollback");
                                con.result().close();
                            });
                        }else{
                            endTx(con.result(), endTrans ->{
                                System.out.println("committed");
                                con.result().close();
                            });
                        }
                    });
                });
            });
        });
    }

    private static void execute(SQLConnection conn, String sql, Handler<AsyncResult<Void>> handler) {
        conn.execute(sql, res -> {
            handler.handle(res);
        });
    }

    private static void startTx(SQLConnection conn, Handler<AsyncResult<Void>> handler){
        conn.setAutoCommit(false, res ->{
            handler.handle(res);
        });
    }

    private static void endTx(SQLConnection conn, Handler<AsyncResult<Void>> handler){
        conn.commit(res -> {
            handler.handle(res);
        });
    }

    private static void rollbackTx(SQLConnection conn, Handler<AsyncResult<Void>> handler){
        conn.rollback(res -> {
            handler.handle(res);
        });
    }
}
