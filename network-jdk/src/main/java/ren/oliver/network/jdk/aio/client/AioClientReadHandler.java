package ren.oliver.network.jdk.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AioClientReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    // 客户端通信的渠道
    private AsynchronousSocketChannel clientAsynchronousSocketChannel;

    // 避免被关停
    private CountDownLatch countDownLatch;

    public AioClientReadHandler(AsynchronousSocketChannel clientAsynchronousSocketChannel, CountDownLatch countDownLatch) {
        this.clientAsynchronousSocketChannel = clientAsynchronousSocketChannel;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        String msg;
        try {
            msg = new String(bytes,"UTF-8");
            System.out.println("Client accept message: " + msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer byteBuffer) {
        System.err.println("数据读取失败");
        exc.printStackTrace();
        try {
            clientAsynchronousSocketChannel.close();
            countDownLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
