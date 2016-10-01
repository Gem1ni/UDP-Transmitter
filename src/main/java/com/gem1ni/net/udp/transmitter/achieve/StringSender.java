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

package com.gem1ni.net.udp.transmitter.achieve;

import com.gem1ni.net.udp.transmitter.core.AbsSender;
import com.gem1ni.net.udp.transmitter.util.ByteUtil;

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
        return ByteUtil.subBytes(s.getBytes(), offset, length);
    }

    @Override
    public void onSendCompleted() {

    }
}
