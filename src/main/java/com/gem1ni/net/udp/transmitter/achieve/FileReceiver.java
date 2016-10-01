/*
 * Copyright 2016 Gem1ni. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gem1ni.net.udp.transmitter.achieve;

import com.gem1ni.net.udp.transmitter.core.AbsReceiver;
import com.gem1ni.net.udp.transmitter.util.L;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * FileReceiver 文件接收
 * Created by GemIni on 2016/9/18.
 */
public class FileReceiver extends AbsReceiver {

    private RandomAccessFile mRandomAccessFile;
    private File mFile, mSavePath;

    public FileReceiver(int port) {
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
