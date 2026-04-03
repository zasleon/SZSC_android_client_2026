package com.example.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class Broadcast {
    private static final int BROADCAST_PORT = 8888;
    private static final int SOCKET_TIMEOUT = 30000; // 30秒超时
    private static final String SERVER_PREFIX = "SERVER:";

    /**
     * 接收广播消息，只接收一次成功消息
     * @param timeoutMs 超时时间（毫秒），-1表示无限等待
     * @return 包含IP和端口的ServerInfo对象，失败返回null
     */
    public static ServerInfo receiveBroadcastOnce(int timeoutMs) {
        DatagramSocket socket = null;

        try {
            // 创建socket并绑定到广播端口
            socket = new DatagramSocket(BROADCAST_PORT);
            socket.setBroadcast(true);

            // 设置超时
            if (timeoutMs > 0) {
                socket.setSoTimeout(timeoutMs);
            }

            System.out.println("开始监听广播，端口: " + BROADCAST_PORT);
            if (timeoutMs > 0) {
                System.out.println("超时设置: " + timeoutMs + "ms");
            }

            // 准备接收缓冲区
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // 接收数据包
            socket.receive(packet);

            // 解析消息
            String message = new String(packet.getData(), 0, packet.getLength(),
                    StandardCharsets.UTF_8);

            System.out.println("收到消息: " + message + " 来自: " +
                    packet.getAddress().getHostAddress());

            // 验证消息格式
            if (message.startsWith(SERVER_PREFIX)) {
                String[] parts = message.split(":");
                if (parts.length >= 3) {
                    String serverIp = parts[1];
                    int serverPort = Integer.parseInt(parts[2]);

                    // 验证IP地址格式
                    if (isValidIpAddress(serverIp) && serverPort > 0 && serverPort <= 65535) {
                        System.out.println("成功解析服务器地址: " + serverIp + ":" + serverPort);
                        return new ServerInfo(serverIp, serverPort);
                    } else {
                        System.err.println("IP或端口格式无效: " + serverIp + ":" + serverPort);
                    }
                } else {
                    System.err.println("消息格式错误: " + message);
                }
            } else {
                System.err.println("非服务器广播消息: " + message);
            }

        } catch (SocketTimeoutException e) {
            System.out.println("接收广播超时，未发现服务器");
        } catch (Exception e) {
            System.err.println("接收广播失败: " + e.getMessage());
        } finally {
            // 确保socket关闭
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("广播监听socket已关闭");
            }
        }

        return null;
    }

    /**
     * 带重试机制的广播接收
     * @param maxRetries 最大重试次数
     * @param retryIntervalMs 重试间隔（毫秒）
     * @return ServerInfo或null
     */
    public static ServerInfo receiveBroadcastWithRetry(int maxRetries, int retryIntervalMs) {
        for (int i = 0; i < maxRetries; i++) {
            System.out.println("尝试接收广播 (" + (i + 1) + "/" + maxRetries + ")");

            ServerInfo info = receiveBroadcastOnce(retryIntervalMs);
            if (info != null) {
                return info;
            }

            if (i < maxRetries - 1) {
                System.out.println("等待 " + retryIntervalMs + "ms 后重试...");
                try {
                    Thread.sleep(retryIntervalMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        System.out.println("达到最大重试次数，未发现服务器");
        return null;
    }

    /**
     * 简易IP地址格式验证
     */
    private static boolean isValidIpAddress(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            try {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * 服务器信息封装类
     */
    public static class ServerInfo {
        private final String ip;
        private final int port;

        public ServerInfo(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }

        @Override
        public String toString() {
            return ip + ":" + port;
        }
    }
}
