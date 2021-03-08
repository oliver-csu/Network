package ren.oliver.network.jdk.nio.client;

import java.util.Scanner;

import static ren.oliver.network.jdk.bio.Constant.DEFAULT_PORT;
import static ren.oliver.network.jdk.bio.Constant.DEFAULT_SERVER_IP;

// NIO通信客户端
public class NioClient {

    private static NioClientHandler nioClientHandler;

    public static void main(String[] args) throws Exception {
        // 启动
        if (nioClientHandler != null) {
            nioClientHandler.stop();
        }
        nioClientHandler = new NioClientHandler(DEFAULT_SERVER_IP, DEFAULT_PORT);
        new Thread(nioClientHandler, "Client").start();

        // 持续发送消息
        Scanner scanner = new Scanner(System.in);
        while (NioClient.sendMsg(scanner.next())) ;
    }

    // 向服务器发送消息
    public static boolean sendMsg(String msg) throws Exception {
        nioClientHandler.sendMsg(msg);
        return true;
    }

}
