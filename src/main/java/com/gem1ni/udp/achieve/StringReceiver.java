package com.gem1ni.udp.achieve;

import com.gem1ni.udp.core.AbsReceiver;
import com.gem1ni.udp.util.L;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * StringReceiver 字符串接收
 * Created by GemIni on 2016/9/18.
 */
public class StringReceiver extends AbsReceiver {

    private Map<Integer, byte[]> mBytesMap = Collections.synchronizedMap(new TreeMap<>());

    public StringReceiver(int port) {
        super(port);
    }

    @Override
    public void onHandleAdditionalInfo(String additionalInfo) {
        L.out("Additional info: " + additionalInfo);
    }

    @Override
    public void onDataTransferring(byte[] bytes, int offset, int length, int position, int total) {
        mBytesMap.put(position, bytes);
    }

    @Override
    public void onTransferCompleted() {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte[] bytes : mBytesMap.values()) {
            stringBuffer.append(new String(bytes));
        }
        L.out(stringBuffer.toString());
    }
}
