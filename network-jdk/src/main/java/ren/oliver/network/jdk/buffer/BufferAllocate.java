package ren.oliver.network.jdk.buffer;

import java.nio.ByteBuffer;

// Buffer的分配
public class BufferAllocate {

    public static void main(String[] args) {
        System.out.println("----------Test allocate--------");
        System.out.println("before alocate:"
                + Runtime.getRuntime().freeMemory());

        // 堆上分配
        ByteBuffer buffer = ByteBuffer.allocate(1024000);
        System.out.println("buffer = " + buffer);
        System.out.println("after alocate:"
                + Runtime.getRuntime().freeMemory());

        // 直接内存
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(102400);
        System.out.println("directBuffer = " + directBuffer);
        System.out.println("after direct alocate:"
                + Runtime.getRuntime().freeMemory());

        System.out.println("----------Test wrap--------");
        byte[] bytes = new byte[32];
        buffer = ByteBuffer.wrap(bytes);
        System.out.println(buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println(buffer);
    }

}
