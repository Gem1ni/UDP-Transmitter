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

import com.gem1ni.net.udp.transmitter.impl.IMonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MonitorImpl
 * Created by Gem1ni on 2016/9/18.
 */
public class MonitorImpl implements IMonitor {

    private ExecutorService mExecutor;
    private DatagramSocket mDataSocket;
    private OnMonitoringListener mOnMonitoringListener;
    private byte mReceiveByte[];

    public MonitorImpl(DatagramSocket datagramSocket, OnMonitoringListener monitoringListener) {
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mDataSocket = datagramSocket;
        this.mOnMonitoringListener = monitoringListener;
        this.mReceiveByte = new byte[BUFFER_SIZE];
    }

    @Override
    public void commencer() {
        mExecutor.execute(this);
    }

    @Override
    public void run() {
        try {
            DatagramPacket mDataPacket = receivePacket();
            mOnMonitoringListener.onMonitorConnected(mDataPacket.getData(), 0, mDataPacket.getLength());
            mOnMonitoringListener.onMonitorReply(mDataPacket.getAddress(), mDataPacket.getPort());
        } catch (IOException e) {
            mOnMonitoringListener.onMonitorFailed(e);
        }
        mExecutor.shutdown();
    }

    /**
     * 接收数据
     *
     * @return
     * @throws IOException
     */
    private DatagramPacket receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(mReceiveByte, mReceiveByte.length);
        mDataSocket.receive(packet);
        return packet;
    }
}
