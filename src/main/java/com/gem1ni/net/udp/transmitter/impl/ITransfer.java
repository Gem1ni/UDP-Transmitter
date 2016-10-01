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
 * ITransfer
 * Created by Gem1ni on 2016/9/18.
 */
public interface ITransfer extends IBase {

    interface OnTransferListener {

        /**
         * onTransferring
         *
         * @param fromAddress the address of remote client
         * @param remotePort  the port of remote client
         * @param bytes       the data
         * @param offset      the offset of the bytes, always 0
         * @param length      the length of the content, not the bytes
         * @param position    the start position of the content
         * @param total       the total length of the transmission
         */
        void onTransferring(InetAddress fromAddress, int remotePort, byte[] bytes, int offset, int length, int position, int total);

        void onTransferComplete();

        void onTransferFailed(Exception e);
    }

    @Override
    void run();

    /**
     * start to transmit data
     */
    void transfer();
}
