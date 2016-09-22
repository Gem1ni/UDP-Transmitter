package com.gem1ni.udp.core;

import com.gem1ni.udp.impl.IDispatcher;
import com.gem1ni.udp.impl.IReceiver;
import com.gem1ni.udp.util.ByteUtil;
import com.gem1ni.udp.util.L;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AbsSender 抽象的发送器
 * Created by GemIni on 2016/9/19.
 */
public abstract class AbsSender<T> implements IDispatcher, IReceiver.OnReceiveListener {

    private ExecutorService mSenderPool;
    private DatagramSocket mDataSocket;
    private IReceiver mReceiver;
    private InetAddress mAddress;
    private int mLocalPort, mRemotePort;
    private T mObjectToSend;
    private int mTotalLength;

    public AbsSender(InetAddress address, int localPort, int remotePort, T objectToSend) {
        this.mSenderPool = Executors.newSingleThreadExecutor();
        this.mAddress = address;
        this.mLocalPort = localPort;
        this.mRemotePort = remotePort;
        this.mObjectToSend = objectToSend;
        this.mTotalLength = 0;
        try {
            this.mDataSocket = new DatagramSocket(mLocalPort, mAddress);
            this.mDataSocket.setSoTimeout(10 * 1000);
            this.mReceiver = new ReceiverImpl(mDataSocket, this);
        } catch (SocketException e) {
            closeSocket();
        }
    }

    /**
     * 指定正文长度
     *
     * @param t
     * @return
     */
    public abstract int specifyContentLength(T t);

    /**
     * 指定附加信息
     *
     * @param t
     * @return
     */
    public abstract String specifyAdditionalInfo(T t);

    @Override
    public void start() {
        mReceiver.receive();
        mTotalLength = specifyContentLength(mObjectToSend);
        L.out("Length to send: " + mTotalLength);
        String temp = specifyAdditionalInfo(mObjectToSend);
        byte[] prepareToSend = ByteUtil.intToBytes(mTotalLength);
        if (temp != null) {
            byte[] additionInfoByteArr = temp.getBytes();
            if (additionInfoByteArr.length > BUFFER_SIZE) {
                throw new IllegalArgumentException("addition info over length!");
            }
            L.out("          with addition info: " + temp);
            mSenderPool.submit(new SenderImpl(mDataSocket, mAddress, mRemotePort, ByteUtil.concatByteArray(prepareToSend, additionInfoByteArr)));
            return;
        }
        mSenderPool.submit(new SenderImpl(mDataSocket, mAddress, mRemotePort, prepareToSend));
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
                int contentLength = (received + BUFFER_SIZE > mTotalLength) ? mTotalLength - received : BUFFER_SIZE;
                byte[] toSend = toSendByteArray(mObjectToSend, received, contentLength);
                byte[] indexByteArr = ByteUtil.intToBytes(received / BUFFER_SIZE);
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
