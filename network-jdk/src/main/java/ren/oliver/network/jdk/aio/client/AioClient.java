package ren.oliver.network.jdk.aio.client;

import java.util.Scanner;

import static ren.oliver.network.jdk.bio.Constant.DEFAULT_PORT;
import static ren.oliver.network.jdk.bio.Constant.DEFAULT_SERVER_IP;

public class AioClient {

    // IO通信处理器
    private static AioClientHandler aioClientHandler;

    // 客户端程序的入口
    public static void main(String[] args) {
        if(aioClientHandler != null) {
            return;
        }
        aioClientHandler = new AioClientHandler(DEFAULT_SERVER_IP, DEFAULT_PORT);
        // 负责网络通讯的线程
        new Thread(aioClientHandler,"Client").start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(AioClient.sendMsg(scanner.nextLine()));
    }

    // 向服务器发送消息
    public static boolean sendMsg(String msg) {
        if(msg.equals("q")) {
            return false;
        }
        aioClientHandler.sendMessag(msg + System.getProperty("line.separator"));
        return true;
    }

}
