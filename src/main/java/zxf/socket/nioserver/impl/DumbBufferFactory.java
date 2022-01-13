package zxf.socket.nioserver.impl;

import zxf.socket.nioserver.BufferFactory;

import java.nio.ByteBuffer;


public class DumbBufferFactory implements BufferFactory {
    private int capacity;

    public DumbBufferFactory(int capacity) {
        this.capacity = capacity;
    }

    public ByteBuffer newBuffer() {
        return ByteBuffer.allocate(capacity);
    }

    public void returnBuffer(ByteBuffer buffer) {
        // do nothing
    }
}
