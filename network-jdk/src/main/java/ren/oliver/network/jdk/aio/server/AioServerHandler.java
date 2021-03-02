package ren.oliver.network.jdk.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AioServerHandler implements Runnable {

    // 进行异步通信的通道
    public AsynchronousServerSocketChannel serverAsynchronousServerSocketChannel;

    // 避免程序被关停
    public CountDownLatch countDownLatch;

    public AioServerHandler(int port) {
        try {
            // 创建服务端通道
            serverAsynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            // 绑定端口
            serverAsynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("服务端启动成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        // 用于接收客户端的连接，异步操作，需要实现了CompletionHandler接口的处理器处理和客户端的连接操作
        serverAsynchronousServerSocketChannel.accept(this, new AioServerAcceptHandler());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
