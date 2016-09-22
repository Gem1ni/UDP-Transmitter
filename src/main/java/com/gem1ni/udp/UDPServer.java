package com.gem1ni.udp;

import com.gem1ni.udp.achieve.FileReceiver;
import com.gem1ni.udp.achieve.StringReceiver;
import com.gem1ni.udp.impl.IDispatcher;

/**
 * UDP Server
 * Created by GemIni on 2016/9/18.
 */
public class UDPServer {

    private final int mPort;

    private UDPServer(int port) {
        this.mPort = port;
    }

    public void receiveString() {
        try {
            IDispatcher dispatcher = new StringReceiver(mPort);
            dispatcher.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveFile() {
        try {
            IDispatcher dispatcher = new FileReceiver(mPort);
            dispatcher.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        private int port;

        public Builder(int port) {
            this.port = port;
        }

        public UDPServer build() {
            return new UDPServer(port);
        }
    }
}
