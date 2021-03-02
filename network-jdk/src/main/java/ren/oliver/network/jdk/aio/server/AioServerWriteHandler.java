package ren.oliver.network.jdk.aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioServerWriteHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asynchronousSocketChannel;

    public AioServerWriteHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
        if(byteBuffer.hasRemaining()){
            // 将没有写完的数据写完
            asynchronousSocketChannel.write(byteBuffer, byteBuffer, this);
        }else{
            // 读取客户端传回的数据
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            // 异步读数据
            asynchronousSocketChannel.read(readBuffer, readBuffer, new AioServerReadHandler(asynchronousSocketChannel));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        try {
            // 关闭通道
            asynchronousSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
