package ren.oliver.network.jdk.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ren.oliver.network.jdk.bio.Constant.DEFAULT_PORT;

// 服务端
public class BioServer {

    // 服务器端
    private static ServerSocket serverSocket;

    // 线程池，处理每个客户端的请求
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    // 程序入口
    public static void main(String[] args) throws IOException {
        try {
            // 通过构造函数创建ServerSocket，如果端口合法且空闲，服务端就监听成功
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务器已启动，端口号：" + DEFAULT_PORT);
            while(true) {
                Socket socket= serverSocket.accept();
                System.out.println("有新的客户端连接：" + socket.getPort());
                // 当有新的客户端接入时，打包成一个任务，投入线程池
                executorService.execute(new BioServerHandler(socket));
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

}
