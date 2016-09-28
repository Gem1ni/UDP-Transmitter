/*
 * Copyright 2016 Gem1ni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gem1ni.net.udp.transmitter;

import com.gem1ni.net.udp.transmitter.achieve.FileSender;
import com.gem1ni.net.udp.transmitter.achieve.StringSender;
import com.gem1ni.net.udp.transmitter.impl.IDispatcher;

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
        dispatcher.launch();
    }

    public void sendFile(File toSend) {
        IDispatcher dispatcher = new FileSender(mAddress, mLocalPort, mRemotePort, toSend);
        dispatcher.launch();
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
