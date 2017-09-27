
# fat jar


### maven package

mvn package

### start

Static deploy verticle

* Main-Class: org.fran.vertx.fatjar.Application
* java -jar fatjar-1.0-fat.jar

Hot deploy verticle

* Main-Class: io.vertx.core.Launcher
* java -jar fatjar-1.0-fat.jar run --redeploy="src/*/*.class" --launcher-class=org.fran.vertx.fatjar.Application

### test

http://127.0.0.1:8080/