package com.gem1ni.udp.impl;

import java.net.InetAddress;

/**
 * IReceiver
 * Created by GemIni on 2016/9/19.
 */
public interface IReceiver extends IBase {

    interface OnReceiveListener {

        void onReceiveReply(byte[] bytes, int offset, int length, InetAddress address, int port);

        void onReceiveFailed(Exception e);
    }

    void receive();

    void shutdown();

    @Override
    void run();
}
