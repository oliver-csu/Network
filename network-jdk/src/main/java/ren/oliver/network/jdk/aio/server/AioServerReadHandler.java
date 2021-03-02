package ren.oliver.network.jdk.aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import static ren.oliver.network.jdk.bio.Constant.response;

public class AioServerReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asynchronousSocketChannel;

    public AioServerReadHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        // 如果条件成立，说明客户端主动终止了TCP套接字，这时服务端终止就可以了
        if(result == -1) {
            try {
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // flip操作
        attachment.flip();
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);
        try {
            System.out.println(result);
            String msg = new String(message,"UTF-8");
            System.out.println("Server accept message:" + msg);
            String responseStr = response(msg);
            // 向客户端发送消息
            doWrite(responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 发送消息
    private void doWrite(String result) {
        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        // 异步写数据
        asynchronousSocketChannel.write(writeBuffer, writeBuffer, new AioServerWriteHandler(asynchronousSocketChannel));
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        try {
            asynchronousSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
