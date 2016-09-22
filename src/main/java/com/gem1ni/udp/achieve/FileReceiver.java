package com.gem1ni.udp.achieve;

import com.gem1ni.udp.core.AbsReceiver;
import com.gem1ni.udp.util.L;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketException;

/**
 * FileReceiver 文件接收
 * Created by GemIni on 2016/9/18.
 */
public class FileReceiver extends AbsReceiver {

    private RandomAccessFile mRandomAccessFile;
    private File mFile, mSavePath;

    public FileReceiver(int port) throws SocketException {
        super(port);
        this.mSavePath = new File("e:\\");
    }

    @Override
    public void onHandleAdditionalInfo(String additionalInfo) {
        try {
            mFile = new File(mSavePath, additionalInfo);
            if (!mFile.exists()) mFile.createNewFile();
            L.out(mFile.getAbsolutePath());
            mRandomAccessFile = new RandomAccessFile(mFile, "rwd");
        } catch (IOException e) {
            // file not found
            L.err(e.getMessage());
        }
    }

    @Override
    public void onDataTransferring(byte[] bytes, int offset, int length, int position, int total) {
        try {
            mRandomAccessFile.seek(position);
            mRandomAccessFile.write(bytes, 0, length);
        } catch (Exception e) {
            L.err(e.getMessage());
        }
    }

    @Override
    public void onTransferCompleted() {
        try {
            L.err("Transfer completed.");
            mRandomAccessFile.close();
        } catch (Exception e) {
            // IO Exception
        }
    }
}
