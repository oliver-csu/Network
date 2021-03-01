package ren.oliver.network.jdk.bio;

import java.util.Date;

public class Constant {

    // 服务器端口号
    public static int DEFAULT_PORT = 12345;
    // 对外提供服务的IP
    public static String DEFAULT_SERVER_IP = "127.0.0.1";

    // 返回给客户端的应答
    public static String response(String msg){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello, ");
        stringBuilder.append(msg);
        stringBuilder.append(", Now is ");
        stringBuilder.append(new Date(System.currentTimeMillis()).toString());
        return stringBuilder.toString();
    }

}
