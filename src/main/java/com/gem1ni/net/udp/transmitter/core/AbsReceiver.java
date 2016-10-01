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

import com.gem1ni.net.udp.transmitter.impl.IDispatcher;
import com.gem1ni.net.udp.transmitter.impl.IMonitor;
import com.gem1ni.net.udp.transmitter.impl.ISender;
import com.gem1ni.net.udp.transmitter.impl.ITransfer;
import com.gem1ni.net.udp.transmitter.util.ByteUtil;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AbsReceiver 抽象的Receiver
 * Created by Gem1ni on 2016/9/18.
 */
public abstract class AbsReceiver extends Thread implements IDispatcher, IMonitor.OnMonitoringListener, ISender.OnSendListener, ITransfer.OnTransferListener {

    private ExecutorService mSenderPool;
    private DatagramSocket mDataSocket;
    private IMonitor mMonitor;
    private ITransfer mTransfer;
    private int mPort;

    public AbsReceiver(int port) {
        this.mSenderPool = Executors.newSingleThreadExecutor();
        this.mPort = port;
    }

    @Override
    public final void launch() {
        super.start();
    }

    @Override
    public final void run() {
        try {
            mDataSocket = new DatagramSocket(mPort);
            mDataSocket.setSoTimeout(DEFAULT_SO_TIMEOUT);
            mMonitor = new MonitorImpl(mDataSocket, this);
            mMonitor.commencer();
        } catch (SocketException e) {
            closeSocket();
        }
    }

    /**
     * 接收到客户端发送的正文长度和附加信息UDP报文时调用
     *
     * @param bytes  bytes 长度为 4 + n, 前4位存放正文长度, 类型为int, 后面的附加信息字节长度请参考 {@link com.gem1ni.net.udp.transmitter.impl.IBase} 里的注释
     * @param offset
     * @param length
     */
    @Override
    public final void onMonitorConnected(byte[] bytes, int offset, int length) {
        byte[] contentLength = ByteUtil.subBytes(bytes, 0, 4);
        int byteToReceive = ByteUtil.bytesToInt(contentLength);
        if (length > 4) {
            byte[] additionInfo = ByteUtil.subBytes(bytes, 4, length - 4);
            String additionString = new String(additionInfo);
            onHandleAdditionalInfo(additionString);
        }
        mTransfer = new TransferImpl(mDataSocket, byteToReceive, this);
        mTransfer.transfer();
    }

    /**
     * 处理附加信息
     *
     * @param additionalInfo
     * @return
     */
    public abstract void onHandleAdditionalInfo(String additionalInfo);

    /**
     * Connect Error
     *
     * @param e
     */
    @Override
    public void onMonitorFailed(Exception e) {
        closeSocket();
    }

    @Override
    public final void onMonitorReply(InetAddress address, int port) {
        mSenderPool.submit(new SenderImpl(mDataSocket, address, port, ByteUtil.intToBytes(0), this));
    }

    /**
     * Reply Error
     *
     * @param e
     */
    @Override
    public void onSendFailed(Exception e) {
        closeSocket();
    }

    @Override
    public final void onTransferring(InetAddress fromAddress, int remotePort, byte[] bytes, int offset, int length, int position, int total) {
        onDataTransferring(bytes, offset, length, position, total);
        mSenderPool.submit(new SenderImpl(mDataSocket, fromAddress, remotePort, ByteUtil.intToBytes(position + length), this));
    }

    public abstract void onDataTransferring(byte[] bytes, int offset, int length, int position, int total);

    @Override
    public final void onTransferComplete() {
        mSenderPool.shutdown();
        onTransferCompleted();
        closeSocket();
    }

    public abstract void onTransferCompleted();

    /**
     * Transfer Error
     *
     * @param e
     */
    @Override
    public void onTransferFailed(Exception e) {
        closeSocket();
    }

    private void closeSocket() {
        if (mDataSocket != null) mDataSocket.close();
    }
}
