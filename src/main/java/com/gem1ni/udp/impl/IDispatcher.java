package com.gem1ni.udp.impl;

/**
 * UDP Dispatcher
 * Created by GemIni on 2016/9/18.
 */
public interface IDispatcher {

    int BUFFER_SIZE = IBase.BUFFER_SIZE - IBase.INDEX_SIZE;

    void start();
}
