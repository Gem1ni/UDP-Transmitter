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

/**
 * UDP IBase
 * Created by GemIni on 2016/9/20.
 */
public interface IBase extends Runnable {

    /**
     * UDP数据包理想长度
     * <p>
     * --  理论上UDP报文最大长度是65507字节，实际上发送这么大的数据
     * 包效果最好吗？我们知道UDP是不可靠的传输协议，为了减少UDP包丢
     * 失的风险，我们最好能控制UDP包在下层协议的传输过程中不要被切割
     * 。相信大家都知道MTU这个概念。MTU最大传输单元，这个最大传输单
     * 元实际上和链路层协议有着密切的关系，EthernetII帧的结构DMAC+
     * SMAC+Type+Data+CRC由于以太网传输电气方面的限制，每个以太网
     * 帧都有最小的大小64字节，最大不能超过1518字节，对于小于或者大
     * 于这个限制的以太网帧我们都可以视之为错误的数据帧，一般的以太
     * 网转发设备会丢弃这些数据帧。
     * <p>
     * --  由于以太网EthernetII最大的数据帧是1518字节，除去以太网
     * 帧的帧头（DMAC目的MAC地址48bit=6Bytes+SMAC源MAC地址48bit
     * =6Bytes+Type域2bytes）14Bytes和帧尾CRC校验部分4Bytes那么
     * 剩下承载上层协议的地方也就是Data域最大就只能有1500字节这个值
     * 我们就把它称之为MTU。
     * <p>
     * --  在下层数据链路层最大传输单元是1500字节的情况下，要想IP层
     * 不分包，那么UDP数据包的最大大小应该是1500字节 – IP头(20字节)
     * – UDP头(8字节) = 1472字节。不过鉴于Internet上的标准MTU值为
     * 576字节，所以建议在进行Internet的UDP编程时，最好将UDP的数据
     * 长度控制在 (576-8-20)548字节以内。
     */
    int BUFFER_SIZE = 1472;

    /**
     * UDP包序号占用的字节数
     */
    int INDEX_SIZE = 4;

    @Override
    void run();
}
