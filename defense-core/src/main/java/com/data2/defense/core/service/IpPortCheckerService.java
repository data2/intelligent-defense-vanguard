package com.data2.defense.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IpPortCheckerService {

    /**
     * 检查指定的IP和端口是否可通信。
     *
     * @param ip   要检查的IP地址
     * @param port 要检查的端口号
     * @return 如果IP和端口可通信，则返回true；否则返回false
     */
    protected boolean isReachable(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 1000); // 尝试连接，超时时间为1000毫秒
            return true; // 如果连接成功，则认为IP和端口可通信
        } catch (IOException e) {
            // 连接失败，可能是因为网络问题、IP不存在、端口未开放等原因
            System.out.println("端口："+port+"连接超时");
            return false;
        }
    }
}
