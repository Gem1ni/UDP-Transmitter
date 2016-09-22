package com.gem1ni.udp.impl;

import java.net.InetAddress;

/**
 * IMonitor
 * Created by GemIni on 2016/9/18.
 */
public interface IMonitor extends IBase {

    interface OnMonitoringListener {

        void onMonitorConnected(byte[] bytes, int offset, int length);

        void onMonitorReply(InetAddress address, int port);

        void onMonitorFailed(Exception e);
    }

    void commencer();

    @Override
    void run();
}
