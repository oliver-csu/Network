package ren.oliver.network.jdk.aio.server;

import static ren.oliver.network.jdk.bio.Constant.DEFAULT_PORT;

public class AioServer {

    // 统计客户端个数
    public volatile static long clientCount = 0;

    private static AioServerHandler aioServerHandler;

    // 服务端程序的入口
    public static void main(String[] args) {
        if(aioServerHandler!=null)
            return;
        aioServerHandler = new AioServerHandler(DEFAULT_PORT);
        new Thread(aioServerHandler, "Server").start();
    }

}
