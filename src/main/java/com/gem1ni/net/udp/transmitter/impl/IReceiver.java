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

package com.gem1ni.net.udp.transmitter.impl;

import java.net.InetAddress;

/**
 * IReceiver
 * Created by Gem1ni on 2016/9/19.
 */
public interface IReceiver extends IBase {

    interface OnReceiveListener {

        /**
         * Called when receiving the reply from the UDPServer, to let the UDPClient know how many bytes the UDPServer received.
         *
         * @param bytes   received bytes contains a int value represent the received bytes length by the UDPServer
         * @param offset  the offset of the bytes, always 0
         * @param length  the length of the bytes
         * @param address ip address of the UDPServer
         * @param port    port of the UDPServer
         */
        void onReceiveReply(byte[] bytes, int offset, int length, InetAddress address, int port);

        void onReceiveFailed(Exception e);
    }

    @Override
    void run();

    /**
     * start to receive reply from UDPServer
     */
    void receive();

    /**
     * stop receiving
     */
    void shutdown();
}
