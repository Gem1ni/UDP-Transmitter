package com.gem1ni.udp.core;

import com.gem1ni.udp.impl.IMonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MonitorImpl
 * Created by GemIni on 2016/9/18.
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
    public DatagramPacket receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(mReceiveByte, mReceiveByte.length);
        mDataSocket.receive(packet);
        return packet;
    }
}
