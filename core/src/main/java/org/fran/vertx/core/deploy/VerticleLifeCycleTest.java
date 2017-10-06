package org.fran.vertx.core.deploy;

import io.vertx.core.AbstractVerticle;

/**
 * Created by fran on 2017/10/6.
 */
public class VerticleLifeCycleTest extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        System.out.println("VerticleLifeCycleTest start");

        System.out.println("Config is " + config());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("VerticleLifeCycleTest stop");
    }
}
