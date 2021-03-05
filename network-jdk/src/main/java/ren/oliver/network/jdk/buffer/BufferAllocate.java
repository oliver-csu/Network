package ren.oliver.network.jdk.buffer;

import java.nio.ByteBuffer;

// Buffer的分配
public class BufferAllocate {

    public static void main(String[] args) {
        // 堆上分配
        alocateTest();

        // 直接内存
        allocateDirectTest();

        // 测试wrap方法
        wrapTest();
    }

    // 堆上分配
    public static void alocateTest() {
        System.out.println("before alocate:" + Runtime.getRuntime().freeMemory());
        ByteBuffer buffer = ByteBuffer.allocate(1024000);
        System.out.println("buffer = " + buffer);
        System.out.println("after alocate:" + Runtime.getRuntime().freeMemory());
    }

    // 直接内存
    public static void allocateDirectTest() {
        System.out.println("before allocateDirect:" + Runtime.getRuntime().freeMemory());
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(102400);
        System.out.println("directBuffer = " + directBuffer);
        System.out.println("after allocateDirect:" + Runtime.getRuntime().freeMemory());
    }

    // 测试wrap方法
    public static void wrapTest() {
        byte[] bytes = new byte[32];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        System.out.println("only wrap: " + buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println("wrap offset+length: " + buffer);
    }

}
