package com.ahfdkun.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class TestByteBuf {

    public static void main(String[] args) {
        // 堆缓冲区
        ByteBuf heapBuf = Unpooled.copiedBuffer("hello".getBytes());
        boolean heap = heapBuf.hasArray();
        System.out.println("is heap: " + heap);

        byte[] array = heapBuf.array();
        int offset = heapBuf.arrayOffset();
        int length = heapBuf.readableBytes();
        handleArray(array, offset, length);

        // 直接缓冲区
        ByteBuf directBuf = Unpooled.directBuffer();
        directBuf.writeBytes(heapBuf);
        System.out.println("is direct:" + !directBuf.hasArray());

        int length2 = directBuf.readableBytes();
        byte[] array2 = new byte[length2];
        directBuf.getBytes(directBuf.readerIndex(), array2);
        handleArray(array2, 0, length2);

        directBuf.writeBytes(Unpooled.copiedBuffer("abcdefg".getBytes()));

        // 复合缓冲区
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        compositeByteBuf.addComponents(Unpooled.copiedBuffer("hello".getBytes()), directBuf);

        for (ByteBuf buf : compositeByteBuf) {
            if (buf.hasArray()) {
                byte[] array3 = buf.array();
                int offset3 = buf.arrayOffset();
                int length3 = buf.readableBytes();
                handleArray(array3, offset3, length3);
            } else {
                byte[] array3 = new byte[buf.readableBytes()];
                buf.getBytes(buf.readerIndex(), array3);
                handleArray(array3, 0, array3.length);
            }
        }

        System.out.println("hexDump: " + ByteBufUtil.hexDump(Unpooled.copiedBuffer("hello".getBytes())));

    }

    private static void handleArray(byte[] array, int offset, int length) {
        System.out.println("result: " + new String(array, offset, length));
    }


}
