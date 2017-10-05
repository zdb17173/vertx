
# gRPC example

#### make proto file

create file in folder 'proto'
```
syntax = "proto3";

option java_multiple_files = true;
option java_package = "org.fran.vertx.core.proto";
option java_outer_classname = "HelloWorldProto";
package org.fran.vertx.example.grpc.proto;

// The greeting service definition.
service Greeter {
  // Sends a greeting
  rpc SayHello (HelloRequest) returns (HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}
```

#### protocol-buffers syntax

[google proto2](https://developers.google.com/protocol-buffers/docs/proto)

[google proto3](https://developers.google.com/protocol-buffers/docs/proto3)



#### generate serializing object
mvn protobuf:compile


#### general service definition
mvn protobuf:compile-custom



