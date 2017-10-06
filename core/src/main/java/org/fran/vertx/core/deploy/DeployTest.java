package org.fran.vertx.core.deploy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by fran on 2017/10/6.
 */
public class DeployTest extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(DeployTest.class.getName());
    }

    @Override
    public void start() throws Exception {

        vertx.deployVerticle("org.fran.vertx.core.deploy.VerticleLifeCycleTest");

        vertx.deployVerticle("org.fran.vertx.core.deploy.VerticleLifeCycleTest", res -> {
            if (res.succeeded()) {
                String deploymentID = res.result();
                System.out.println("deploy ok , deploymentID[" + deploymentID + "]");
                vertx.undeploy(deploymentID, res2 -> {
                    if (res2.succeeded()) {
                        System.out.println("Undeployed ok!");
                    } else {
                        res2.cause().printStackTrace();
                    }
                });
            }else {
                res.cause().printStackTrace();
            }
        });

        // Deploy specifying some config
        JsonObject config = new JsonObject().put("foo", "bar");
        vertx.deployVerticle("org.fran.vertx.core.deploy.VerticleLifeCycleTest", new DeploymentOptions().setConfig(config));

        // Deploy 10 instances
        vertx.deployVerticle("org.fran.vertx.core.deploy.VerticleLifeCycleTest", new DeploymentOptions().setInstances(10));

        // Deploy it as a worker verticle
        vertx.deployVerticle("org.fran.vertx.core.deploy.VerticleLifeCycleTest", new DeploymentOptions().setWorker(true));
    }

}
