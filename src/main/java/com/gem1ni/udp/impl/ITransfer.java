package com.gem1ni.udp.impl;

import java.net.InetAddress;

/**
 * ITransfer
 * Created by GemIni on 2016/9/18.
 */
public interface ITransfer extends IBase {

    interface OnTransferListener {

        void onTransferring(InetAddress fromAddress, int remotePort, byte[] bytes, int offset, int length, int position, int total);

        void onTransferComplete();

        void onTransferFailed(Exception e);
    }

    void transfer();

    @Override
    void run();
}
