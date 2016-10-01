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

import com.gem1ni.net.udp.transmitter.impl.ITransfer;
import com.gem1ni.net.udp.transmitter.util.ByteUtil;
import com.gem1ni.net.udp.transmitter.util.L;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TransferImpl
 * Created by GemIni on 2016/9/18.
 */
public class TransferImpl implements ITransfer {

    private ExecutorService mExecutor;
    private DatagramSocket mDataSocket;
    private int mBytesToReceive;
    private OnTransferListener mOnTransferListener;
    private byte mReceiveByte[];
    private int mBytesReceived = 0;

    public TransferImpl(DatagramSocket mDataSocket, int bytesToReceive, OnTransferListener mOnTransferListener) {
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mDataSocket = mDataSocket;
        this.mBytesToReceive = bytesToReceive;
        this.mOnTransferListener = mOnTransferListener;
        this.mReceiveByte = new byte[BUFFER_SIZE];
    }

    @Override
    public void transfer() {
        mExecutor.execute(this);
    }

    @Override
    public void run() {
        try {
            while (mBytesReceived < mBytesToReceive) {
                DatagramPacket mDataPacket = receivePacket();   // 从客户端发过来的UDP包
                byte[] data = mDataPacket.getData();            // UDP包中的content
                byte[] indexBytes = ByteUtil.subBytes(data, 0, INDEX_SIZE);
                int index = ByteUtil.bytesToInt(indexBytes);
                byte[] contentBytes = ByteUtil.subBytes(data, INDEX_SIZE, data.length - INDEX_SIZE);
                mOnTransferListener.onTransferring(mDataPacket.getAddress(), mDataPacket.getPort(), contentBytes, 0, mDataPacket.getLength() - INDEX_SIZE, index * (BUFFER_SIZE - INDEX_SIZE), mBytesToReceive);
                mBytesReceived += mDataPacket.getLength() - INDEX_SIZE;
                L.err("Received : " + mBytesReceived + " byte(s) of " + mBytesToReceive + " total byte(s), process: " + ((long) mBytesReceived) * 100 / mBytesToReceive + " %");
            }
            mOnTransferListener.onTransferComplete();
            mExecutor.shutdown();
        } catch (IOException e) {
            mOnTransferListener.onTransferFailed(e);
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
