package com.gem1ni.udp.achieve;

import com.gem1ni.udp.core.AbsSender;

import java.net.InetAddress;

/**
 * 发送字符串
 * Created by GemIni on 2016/9/19.
 */
public class StringSender extends AbsSender<String> {

    public StringSender(InetAddress address, int localPort, int remotePort, String stringToSend) {
        super(address, localPort, remotePort, stringToSend);
    }

    @Override
    public int specifyContentLength(String s) {
        return s.getBytes().length;
    }

    @Override
    public String specifyAdditionalInfo(String s) {
        return "name.txt";
    }

    @Override
    public byte[] toSendByteArray(String s, int offset, int length) {
        return subBytes(s.getBytes(), offset, length);
    }

    /**
     * 从一个byte[]数组中截取一部分
     *
     * @param src
     * @param begin
     * @param count
     * @return
     */
    private byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) bs[i - begin] = src[i];
        return bs;
    }

    @Override
    public void onSendCompleted() {

    }
}
