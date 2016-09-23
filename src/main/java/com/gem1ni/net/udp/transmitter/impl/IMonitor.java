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

package com.gem1ni.net.udp.transmitter.impl;

import java.net.InetAddress;

/**
 * IMonitor
 * Created by GemIni on 2016/9/18.
 */
public interface IMonitor extends IBase {

    void commencer();

    @Override
    void run();

    interface OnMonitoringListener {

        void onMonitorConnected(byte[] bytes, int offset, int length);

        void onMonitorReply(InetAddress address, int port);

        void onMonitorFailed(Exception e);
    }
}
