package com.gem1ni.udp.impl;

/**
 * UDP Sender
 * Created by GemIni on 2016/9/18.
 */
public interface ISender extends IBase {

    interface OnSendListener {
        void onSendFailed(Exception e);
    }

    @Override
    void run();
}
