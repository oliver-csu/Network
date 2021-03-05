package ren.oliver.network.jdk.buffer;

import java.nio.ByteBuffer;

// Buffer方法演示
public class BufferMethod {

    public static void main(String[] args) {
        // 初始化，准备ByteBuffer
        ByteBuffer byteBuffer = init();

        // filp test
        filpTest(byteBuffer);

        // get test
        getTest(byteBuffer);

        // get(index)不影响position的值
        getIndexTest(byteBuffer);

        // get dst offset index
        getDstOffsetIndexTest(byteBuffer);

        // put test
        putTest(byteBuffer);

        // reset test
        resetTest(byteBuffer);

        // rewind test
        rewindTest(byteBuffer);

        // compact test
        compactTest(byteBuffer);
    }

    public static ByteBuffer init() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer
                // 0
                .put((byte) 'a')
                // 1
                .put((byte) 'b')
                // 2
                .put((byte) 'c')
                // 3
                .put((byte) 'd')
                // 4
                .put((byte) 'e')
                // 5
                .put((byte) 'f');
        return byteBuffer;
    }

    // flip test
    public static void filpTest(ByteBuffer byteBuffer) {
        System.out.println("before flip(): " + byteBuffer);
        // 转换为读取模式
        byteBuffer.flip();
        System.out.println("after flip(): " + byteBuffer);
    }

    // get test
    public static void getTest(ByteBuffer byteBuffer) {
        System.out.println("before get():" + byteBuffer);
        System.out.println((char) byteBuffer.get());
        System.out.println("after get():" + byteBuffer);
    }

    // get index test
    public static void getIndexTest(ByteBuffer byteBuffer) {
        System.out.println("before get(index): " + byteBuffer);
        System.out.println((char) byteBuffer.get(2));
        System.out.println("after get(index): " + byteBuffer);
    }

    // get dst offset index
    public static void getDstOffsetIndexTest(ByteBuffer byteBuffer) {
        System.out.println("after get(dst, 0, 2): " + byteBuffer);
        byte[] dst = new byte[10];
        // position移动两位
        byteBuffer.get(dst, 0, 2);
        // 这里的buffer是abcdef[pos=3 lim=6 cap=32]
        System.out.println("after get(dst, 0, 2): " + byteBuffer);
        System.out.println("dst: " + new String(dst));
    }

    public static void putTest(ByteBuffer byteBuffer) {
        System.out.println("--------Test put-------");
        ByteBuffer bb = ByteBuffer.allocate(32);
        System.out.println("before put(byte):" + bb);
        System.out.println("after put(byte):" + bb.put((byte) 'z'));
        // put(2,(byte) 'c')不改变position的位置
        bb.put(2, (byte) 'c');
        System.out.println("after put(2,(byte) 'c'):" + bb);
        System.out.println(new String(bb.array()));

        // 这里的buffer是 abcdef[pos=3 lim=6 cap=32]
        bb.put(byteBuffer);
        System.out.println("after put(byteBuffer):" + bb);
        System.out.println(new String(bb.array()));
    }

    public static void resetTest(ByteBuffer byteBuffer) {
        System.out.println("--------Test reset----------");
        byteBuffer = ByteBuffer.allocate(20);
        System.out.println("byteBuffer = " + byteBuffer);
        byteBuffer.clear();
        byteBuffer.position(5);//移动position到5
        byteBuffer.mark();//记录当前position的位置
        byteBuffer.position(10);//移动position到10
        System.out.println("before reset:" + byteBuffer);
        byteBuffer.reset();//复位position到记录的地址
        System.out.println("after reset:" + byteBuffer);
    }

    public static void rewindTest(ByteBuffer byteBuffer) {
        System.out.println("--------Test rewind--------");
        byteBuffer.clear();
        byteBuffer.position(10);//移动position到10
        byteBuffer.limit(15);//限定最大可写入的位置为15
        System.out.println("before rewind:" + byteBuffer);
        byteBuffer.rewind();//将position设回0
        System.out.println("before rewind:" + byteBuffer);
    }

    public static void compactTest(ByteBuffer byteBuffer) {
        System.out.println("--------Test compact--------");
        byteBuffer.clear();
        //放入4个字节，position移动到下个可写入的位置，也就是4
        byteBuffer.put("abcd".getBytes());
        System.out.println("before compact:" + byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        byteBuffer.flip();//将position设回0，并将limit设置成之前position的值
        System.out.println("after flip:" + byteBuffer);
        //从Buffer中读取数据的例子，每读一次，position移动一次
        System.out.println((char) byteBuffer.get());
        System.out.println((char) byteBuffer.get());
        System.out.println((char) byteBuffer.get());
        System.out.println("after three gets:" + byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        //compact()方法将所有未读的数据拷贝到Buffer起始处。
        // 然后将position设到最后一个未读元素正后面。
        byteBuffer.compact();
        System.out.println("after compact:" + byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }

}
