
# vert.x core

## streams

vert.x中有一些对象允许从中读取和写入。
在以前的版本中，stream.adoc包只能操作buffer对象来实现流处理。现在开始，流不在与buffer耦合，可以与任何对象结合使用。
在vert.x中，写入调用会被立即返回，写入操作实际会在队列中内部运行。
这不难看出，如果你写入对象的速度比实际写入资源文件的速度要快，那么写入队列可能会增长到无限，最终导致内存耗尽。
为了解决这个问题，Vert.x API中的一些对象提供了一个简单的流量控制（back-pressure）。
用来完成写入的流程控制对象需要实现WriteStream，而用来完成读取控制的对象需要实现ReadStream。

实现了ReadStream的：HttpClientResponse, DatagramSocket, HttpClientRequest, HttpServerFileUpload, HttpServerRequest, MessageConsumer, NetSocket, WebSocket, TimeoutStream,AsyncFile实现。

实现了WriteStream的：HttpClientRequest, HttpServerResponse WebSocket, NetSocket, AsyncFile, MessageProducer


## recordParser

recordParser可以帮助解决序列化字节传输时的简单协议。
例如：
```
buffer1:HELLO\nHOW ARE Y
buffer2:OU?\nI AM
buffer3: DOING OK
buffer4:\n
```
由于buffer的大小限制，传输的数据被分割在多次buffer传输中，协议规定以简单的\n做分割，此时就可以使用recordParser进行切割。


## verticle

使用verticles，vert.x会变成简单的、可扩展的、类似actor的部署和并发模式，你可以使用它来保存、编写自己的verticle。
这个模式完全是可选的，vert.x不会强制你通过这种方式创建你的应用，如果你不需要的话。
这个模式没有严格按照actor-model实现，但是他在并发、扩展和部署等方面有着许多相似之处。
为了使用这个模式，你需要编写一组verticle代码
verticle是一个代码块，他会被部署在vert.x中，并会被其运行。一个vert.x实例维护了N个事件循环线程（N一般为核心数*2）。verticle可以通过任何vert.x支持的语言编写，单个vert.x可以支持多种语言编写的verticle
你可以认为verticle有一点像actor模式的actor。
一个应用可以被多个verticle实例组成，在相同的vert.x实例中运行。不同的verticle实例互相通讯通过event bus的消息来实现。

