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
