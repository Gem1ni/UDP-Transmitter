package com.gem1ni.udp.core;

import com.gem1ni.udp.impl.ISender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * SenderImpl
 * Created by GemIni on 2016/9/18.
 */
public class SenderImpl implements ISender {

    private DatagramSocket mDataSocket;
    private InetAddress mAddress;
    private int mPort;
    private OnSendListener mOnSendListener;
    private byte[] mContent;

    public SenderImpl(DatagramSocket dataSocket, InetAddress address, int port, byte[] bytes) {
        this(dataSocket, address, port, bytes, null);
    }

    public SenderImpl(DatagramSocket dataSocket, InetAddress address, int port, byte[] bytes, OnSendListener onSendListener) {
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
    public void send(InetAddress recv, int port, byte[] message) throws IOException {
        DatagramPacket packet = new DatagramPacket(message, message.length, recv, port);
        mDataSocket.send(packet);
    }
}
