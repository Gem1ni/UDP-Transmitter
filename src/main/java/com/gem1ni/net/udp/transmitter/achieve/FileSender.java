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

import com.gem1ni.net.udp.transmitter.core.AbsSender;
import com.gem1ni.net.udp.transmitter.util.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * 发送文件
 * Created by Gem1ni on 2016/9/22.
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
