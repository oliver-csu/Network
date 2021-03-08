package ren.oliver.network.jdk.nio.server;

import static ren.oliver.network.jdk.bio.Constant.DEFAULT_PORT;
import static ren.oliver.network.jdk.bio.Constant.DEFAULT_SERVER_IP;

// NIO通信服务端
public class NioServer {

    private static NioServerHandler nioServerHandler;

    public static void main(String[] args) {
        if(nioServerHandler != null) {
            nioServerHandler.stop();
        }
        nioServerHandler = new NioServerHandler(DEFAULT_SERVER_IP, DEFAULT_PORT);
        new Thread(nioServerHandler,"Server").start();
    }

}
