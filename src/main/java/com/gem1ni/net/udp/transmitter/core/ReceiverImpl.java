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

package com.gem1ni.net.udp.transmitter.core;

import com.gem1ni.net.udp.transmitter.impl.IReceiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ReceiverImpl
 * Created by GemIni on 2016/9/19.
 */
public class ReceiverImpl implements IReceiver {

    private ExecutorService mExecutor;
    private DatagramSocket mDataSocket;
    private IReceiver.OnReceiveListener mOnReceiveListener;
    private byte mReceiveByte[];
    private boolean mReceiving;

    public ReceiverImpl(DatagramSocket dataSocket, OnReceiveListener onReceiveListener) {
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mDataSocket = dataSocket;
        this.mOnReceiveListener = onReceiveListener;
        this.mReceiveByte = new byte[BUFFER_SIZE];
        this.mReceiving = false;
    }

    @Override
    public void receive() {
        mReceiving = true;
        mExecutor.execute(this);
    }

    @Override
    public void shutdown() {
        mReceiving = false;
        mExecutor.shutdown();
    }

    @Override
    public void run() {
        while (mReceiving) {
            try {
                DatagramPacket mDataPacket = receivePacket();
                if (mDataPacket.getLength() > 0)
                    mOnReceiveListener.onReceiveReply(mDataPacket.getData(), 0, mDataPacket.getLength(), mDataPacket.getAddress(), mDataPacket.getPort());
            } catch (IOException e) {
                mOnReceiveListener.onReceiveFailed(e);
            }
        }
    }

    /**
     * 接收数据
     *
     * @return
     * @throws IOException
     */
    public DatagramPacket receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(mReceiveByte, mReceiveByte.length);
        mDataSocket.receive(packet);
        return packet;
    }
}
