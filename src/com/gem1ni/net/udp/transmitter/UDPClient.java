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

package com.gem1ni.net.udp.transmitter;

import com.gem1ni.net.udp.transmitter.achieve.FileSender;
import com.gem1ni.net.udp.transmitter.achieve.StringSender;
import com.gem1ni.net.udp.transmitter.impl.IDispatcher;

import java.io.File;
import java.net.InetAddress;

/**
 * UDPClient
 * Created by Gem1ni on 2016/9/19.
 */
public class UDPClient {

    private int mLocalPort;
    private InetAddress mRemoteAddress;
    private int mRemotePort;

    private UDPClient(int localPort, InetAddress address, int remotePort) {
        this.mLocalPort = localPort;
        this.mRemoteAddress = address;
        this.mRemotePort = remotePort;
    }

    public void sendString(String toSend) {
        IDispatcher dispatcher = new StringSender(mLocalPort, mRemoteAddress, mRemotePort, toSend);
        dispatcher.launch();
    }

    public void sendFile(File toSend) {
        IDispatcher dispatcher = new FileSender(mLocalPort, mRemoteAddress, mRemotePort, toSend);
        dispatcher.launch();
    }

    public static class Builder {
        private InetAddress mRemoteAddress;
        private int mLocalPort;
        private int mRemotePort;

        public Builder(int localPort) {
            this.mLocalPort = localPort;
        }

        public Builder remoteAddress(InetAddress remoteAddress) {
            this.mRemoteAddress = remoteAddress;
            return this;
        }

        public Builder remotePort(int remotePort) {
            this.mRemotePort = remotePort;
            return this;
        }

        public UDPClient build() {
            if (mRemoteAddress == null)
                throw new NullPointerException("remote address can not be null");
            return new UDPClient(mLocalPort, mRemoteAddress, mRemotePort);
        }
    }
}
