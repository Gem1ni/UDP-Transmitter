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

package com.gem1ni.net.udp.transmitter.core;

import com.gem1ni.net.udp.transmitter.impl.ISender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * SenderImpl
 * Created by Gem1ni on 2016/9/18.
 */
public class SenderImpl implements ISender {

    private DatagramSocket mDataSocket;
    private InetAddress mAddress;
    private int mPort;
    private OnSendListener mOnSendListener;
    private byte[] mContent;

    SenderImpl(DatagramSocket dataSocket, InetAddress address, int port, byte[] bytes) {
        this(dataSocket, address, port, bytes, null);
    }

    SenderImpl(DatagramSocket dataSocket, InetAddress address, int port, byte[] bytes, OnSendListener onSendListener) {
        this.mDataSocket = dataSocket;
        this.mAddress = address;
        this.mPort = port;
        this.mContent = bytes;
        this.mOnSendListener = onSendListener;
    }

    @Override
    public void run() {
        try {
            send(mAddress, mPort, mContent);
        } catch (IOException e) {
            if (mOnSendListener != null)
                mOnSendListener.onSendFailed(e);
        }
    }

    /**
     * 发送数据
     *
     * @param recv
     * @param port
     * @param message
     * @throws IOException
     */
    private void send(InetAddress recv, int port, byte[] message) throws IOException {
        DatagramPacket packet = new DatagramPacket(message, message.length, recv, port);
        mDataSocket.send(packet);
    }
}
