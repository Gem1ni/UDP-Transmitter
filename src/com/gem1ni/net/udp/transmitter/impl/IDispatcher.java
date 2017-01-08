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

package com.gem1ni.net.udp.transmitter.impl;

/**
 * UDP Dispatcher
 * Created by Gem1ni on 2016/9/18.
 */
public interface IDispatcher {

    /**
     * Buffer size of the content in transmission
     * <p>
     * the UDP datagram packet is mainly composed of two parts.
     * --   1. first 4 bytes {@link com.gem1ni.net.udp.transmitter.impl.IBase#INDEX_SIZE} is the index of datagram packet.
     * --   2. from 5 to the end, those are the bytes of content in datagram packet
     */
    int CONTENT_BUFFER_SIZE = IBase.BUFFER_SIZE - IBase.INDEX_SIZE;

    /**
     * Default Timeout 10 seconds
     */
    int DEFAULT_SO_TIMEOUT = 10 * 1000;

    /**
     * launch the dispatcher.
     */
    void launch();
}
