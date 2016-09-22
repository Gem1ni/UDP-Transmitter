package com.gem1ni.udp;

import com.gem1ni.udp.achieve.FileSender;
import com.gem1ni.udp.achieve.StringSender;
import com.gem1ni.udp.impl.IDispatcher;

import java.io.File;
import java.net.InetAddress;

/**
 * UDPClient
 * Created by GemIni on 2016/9/19.
 */
public class UDPClient {

    private final InetAddress mAddress;
    private final int mLocalPort;
    private final int mRemotePort;

    private UDPClient(InetAddress address, int localPort, int remotePort) {
        this.mAddress = address;
        this.mLocalPort = localPort;
        this.mRemotePort = remotePort;
    }

    public void sendString(String toSend) {
        IDispatcher dispatcher = new StringSender(mAddress, mLocalPort, mRemotePort, toSend);
        dispatcher.start();
    }

    public void sendFile(File toSend) {
        IDispatcher dispatcher = new FileSender(mAddress, mLocalPort, mRemotePort, toSend);
        dispatcher.start();
    }

    public static class Builder {
        private InetAddress mAddress;
        private int mLocalPort;
        private int mRemotePort;

        public Builder(InetAddress address) {
            if (address == null) throw new NullPointerException("address can not be null");
            this.mAddress = address;
        }

        public Builder localPort(int localPort) {
            this.mLocalPort = localPort;
            return this;
        }

        public Builder remotePort(int remotePort) {
            this.mRemotePort = remotePort;
            return this;
        }

        public UDPClient build() {
            return new UDPClient(mAddress, mLocalPort, mRemotePort);
        }
    }
}
