package com.google.protobuf;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class UnsafeByteOperations {
    private UnsafeByteOperations() {
    }

    public static ByteString unsafeWrap(ByteBuffer buffer) {
        if (!buffer.hasArray()) {
            return new NioByteString(buffer);
        }
        return ByteString.wrap(buffer.array(), buffer.position() + buffer.arrayOffset(), buffer.remaining());
    }

    public static void unsafeWriteTo(ByteString bytes, ByteOutput output) throws IOException {
        bytes.writeTo(output);
    }
}
