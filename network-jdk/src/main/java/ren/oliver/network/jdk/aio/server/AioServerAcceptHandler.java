package ren.oliver.network.jdk.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioServerAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AioServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel asynchronousSocketChannel, AioServerHandler aioServerHandler) {
        AioServer.clientCount++;
        System.out.println("连接的客户端数：" + AioServer.clientCount);
        // 重新注册监听，让别的客户端也可以连接
        aioServerHandler.serverAsynchronousServerSocketChannel.accept(aioServerHandler,this);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        // 1)ByteBuffer dst：接收缓冲区，用于从异步Channel中读取数据包；
        // 2)A attachment：异步Channel携带的附件，通知回调的时候作为入参使用；
        // 3)CompletionHandler<Integer,? super A>：系统回调的业务handler，进行读操作
        asynchronousSocketChannel.read(readBuffer,readBuffer, new AioServerReadHandler(asynchronousSocketChannel));
    }

    @Override
    public void failed(Throwable exc, AioServerHandler aioServerHandler) {
        exc.printStackTrace();
        aioServerHandler.countDownLatch.countDown();
    }

}
