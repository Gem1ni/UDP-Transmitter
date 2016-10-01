/*
 * Copyright 2016 Gem1ni. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.gem1ni.net.udp.transmitter.UDPClient;
import com.gem1ni.net.udp.transmitter.UDPServer;

import java.io.File;
import java.net.InetAddress;

/**
 * Created by Gem1ni on 2016/9/27.
 */
public class UDPTest {

    public static void main(String args[]) {
        try {
            UDPServer server = new UDPServer.Builder(5231).build();
            server.receiveFile();
            UDPClient client = new UDPClient.Builder(1234)
                    .remoteAddress(InetAddress.getByName("192.168.8.102"))
                    .remotePort(5231)
                    .build();
//            client.sendString("Android");
            client.sendFile(new File("D:\\name.txt"));
//            System.out.println("".getBytes().length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
