package ren.oliver.network.jdk.aio.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AioClientWriteHandler implements CompletionHandler<Integer, ByteBuffer> {

    // 客户端通信的通道
    private AsynchronousSocketChannel clientAsynchronousSocketChannel;
    // 避免程序被关停
    private CountDownLatch countDownLatch;

    public AioClientWriteHandler(AsynchronousSocketChannel clientAsynchronousSocketChannel, CountDownLatch countDownLatch) {
        this.clientAsynchronousSocketChannel = clientAsynchronousSocketChannel;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
        // 有可能无法一次性将数据写完，需要检查缓冲区中是否还有数据需要继续进行网络写
        if(byteBuffer.hasRemaining()){
            clientAsynchronousSocketChannel.write(byteBuffer, byteBuffer,this);
        }else{
            // 写操作已经完成，为读取服务端传回的数据建立缓冲区
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            // 这个方法会迅速返回，需要提供一个接口让系统在读操作完成后通知我们的应用程序。
            clientAsynchronousSocketChannel.read(readBuffer,readBuffer, new AioClientReadHandler(clientAsynchronousSocketChannel, countDownLatch));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer byteBuffer) {
        System.err.println("数据发送失败");
        exc.printStackTrace();
        try {
            clientAsynchronousSocketChannel.close();
            countDownLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
