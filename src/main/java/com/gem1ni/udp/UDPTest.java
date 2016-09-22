package com.gem1ni.udp;

import java.io.File;
import java.net.InetAddress;

public class UDPTest {

    public static void main(String args[]) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 548; j++) {
                sb.append((char) (i + 65));
            }
        }
        String toSend = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        UDPServer server = new UDPServer.Builder(5231).build();
        server.receiveFile();
        UDPClient client = new UDPClient.Builder(InetAddress.getLocalHost())
                .localPort(1325)
                .remotePort(5231)
                .build();
        File file = new File("E:\\Xiaomi\\MiPhoneManager\\Download\\Rom\\aries\\5.10.29\\rom\\images\\8064_msimage.mbn");
//        File file = new File("E:\\Xiaomi\\MiPhoneManager\\Download\\Rom\\aries\\5.10.29\\rom\\images\\NON-HLOS.bin");
//        File file = new File("d:\\name.txt");
        client.sendFile(file);
//        client.sendString(sb.toString());
    }
}
