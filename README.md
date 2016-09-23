# UDP-Transmitter

An UDP-Based Transmission

一个基于UDP协议的可扩展的数据传输小程序

Usage
-------

#### 1. Build an UDPServer to receive data by listening a local port.
```java
// Create an UDPServer instance
UDPServer server = new UDPServer.Builder(int port).build();
// Start listening to receive a string
server.receiveString();
// Or to receive a file
server.receiveFile();
```

#### 2. Build an UDPClient to send data.
```java
// Create an UDPClient instance
UDPClient client = new UDPClient.Builder(InetAddress address)   // address of the UDPServer
                        .localPort(int localPort)               // use local port
                        .remotePort(int remotePort)             // the port which the UDPServer is listening
                        .build();                               // return the UDPClient instance
// Send a string
client.sendString(String string);
// Or send a file
client.sendFile(File file);
```

To do
-------
Add comments. (It all depends)

![Garfield](garfield.jpg)

License
-------

    Copyright 2016 Gem1ni

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
