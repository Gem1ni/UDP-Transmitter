/*
 * Copyright 2016 Gem1ni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
