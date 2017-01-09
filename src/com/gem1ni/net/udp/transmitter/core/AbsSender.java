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
import com.gem1ni.net.udp.transmitter.impl.IReceiver;
import com.gem1ni.net.udp.transmitter.util.ByteUtil;
import com.gem1ni.net.udp.transmitter.util.L;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AbsSender 抽象的发送器
 * Created by Gem1ni on 2016/9/19.
 */
public abstract class AbsSender<T> extends Thread implements IDispatcher, IReceiver.OnReceiveListener {

    private ExecutorService mSenderPool;
    private DatagramSocket mDataSocket;
    private IReceiver mReceiver;
    private InetAddress mRemoteAddress;
    private int mLocalPort, mRemotePort;
    private T mObjectToSend;
    private int mTotalLength;

    public AbsSender(int localPort, InetAddress address, int remotePort, T objectToSend) {
        this.mSenderPool = Executors.newSingleThreadExecutor();
        this.mLocalPort = localPort;
        this.mRemoteAddress = address;
        this.mRemotePort = remotePort;
        this.mObjectToSend = objectToSend;
        this.mTotalLength = 0;
    }

    /**
     * 指定正文长度
     *
     * @param t
     * @return
     */
    public abstract int getContentLength(T t);

    @Override
    public void launch() {
        super.start();
    }

    @Override
    public void run() {
        try {
            mDataSocket = new DatagramSocket(mLocalPort);
            mDataSocket.setSoTimeout(DEFAULT_SO_TIMEOUT);
            mReceiver = new ReceiverImpl(mDataSocket, this);
            mReceiver.receive();
            mTotalLength = getContentLength(mObjectToSend);
            L.out("Length to send: " + mTotalLength);
            byte[] toSend = ByteUtil.intToBytes(mTotalLength);
            mSenderPool.submit(new SenderImpl(mDataSocket, mRemoteAddress, mRemotePort, toSend));
        } catch (SocketException e) {
            closeSocket();
        }
    }

    @Override
    public final void onReceiveReply(byte[] bytes, int offset, int length, InetAddress address, int port) {
        int received = ByteUtil.bytesToInt(bytes);
        switch (received) {
            case 0:
                L.out("------ Send Begin ------");
            default:
                if (received == mTotalLength) {
                    onSendCompleted();
                    L.out("------ Send End ------");
                    mReceiver.shutdown();
                    mSenderPool.shutdown();
                    closeSocket();
                    break;
                }
                int contentLength = (received + CONTENT_BUFFER_SIZE > mTotalLength) ? mTotalLength - received : CONTENT_BUFFER_SIZE;
                byte[] toSend = toSendByteArray(mObjectToSend, received, contentLength);
                byte[] indexByteArr = ByteUtil.intToBytes(received / CONTENT_BUFFER_SIZE);
                mSenderPool.submit(new SenderImpl(mDataSocket, address, port, ByteUtil.concatByteArray(indexByteArr, toSend)));
                break;
        }
    }

    public abstract byte[] toSendByteArray(T t, int offset, int length);

    public abstract void onSendCompleted();

    @Override
    public final void onReceiveFailed(Exception e) {
        closeSocket();
    }

    private void closeSocket() {
        if (mDataSocket != null) mDataSocket.close();
    }
}
