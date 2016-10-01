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
 * IMonitor
 * Created by Gem1ni on 2016/9/18.
 */
public interface IMonitor extends IBase {

    interface OnMonitoringListener {

        /**
         * Called when received the initial data from UDPClient
         *
         * @param bytes  composed of two parts
         *               1. the first 4 bytes represents the total length of data
         *               2. from 5 to the end of bytes represents the additional info
         * @param offset the offset of the bytes, always 0
         * @param length the length of the bytes, length > 4 {@link com.gem1ni.net.udp.transmitter.impl.IBase#INDEX_SIZE} means data containing additional info
         */
        void onMonitorConnected(byte[] bytes, int offset, int length);

        /**
         * Reply to the UDPClient to start transmission
         *
         * @param address ip address of the UDPClient
         * @param port    port of the UDPClient
         */
        void onMonitorReply(InetAddress address, int port);

        void onMonitorFailed(Exception e);
    }

    @Override
    void run();

    /**
     * start monitoring.
     */
    void commencer();
}
