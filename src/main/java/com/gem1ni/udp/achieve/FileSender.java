package com.gem1ni.udp.achieve;

import com.gem1ni.udp.core.AbsSender;
import com.gem1ni.udp.util.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * 发送文件
 * Created by GemIni on 2016/9/22.
 */
public class FileSender extends AbsSender<File> {

    FileInputStream mFileInputStream;

    public FileSender(InetAddress address, int localPort, int remotePort, File fileToSend) {
        super(address, localPort, remotePort, fileToSend);
    }

    @Override
    public int specifyContentLength(File file) {
        try {
            this.mFileInputStream = new FileInputStream(file);
            return mFileInputStream.available();
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public String specifyAdditionalInfo(File file) {
        return file.getName();
    }

    @Override
    public byte[] toSendByteArray(File file, int offset, int length) {
        byte[] bytes = new byte[length];
        try {
            mFileInputStream.read(bytes);
        } catch (Exception e) {
            try {
                L.err("available: " + mFileInputStream.available() + " length: " + length);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void onSendCompleted() {
        try {
            L.out("Send completed.");
            mFileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
