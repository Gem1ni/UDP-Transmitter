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

package com.gem1ni.udp.impl;

import java.net.InetAddress;

/**
 * IReceiver
 * Created by GemIni on 2016/9/19.
 */
public interface IReceiver extends IBase {

    void receive();

    void shutdown();

    @Override
    void run();

    interface OnReceiveListener {

        void onReceiveReply(byte[] bytes, int offset, int length, InetAddress address, int port);

        void onReceiveFailed(Exception e);
    }
}
