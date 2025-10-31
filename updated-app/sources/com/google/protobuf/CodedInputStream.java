package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.appinventor.components.runtime.util.Ev3Constants;
import com.google.protobuf.MessageLite;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CodedInputStream {
    private static final int BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 100;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    /* access modifiers changed from: private */
    public final byte[] buffer;
    private final boolean bufferIsImmutable;
    /* access modifiers changed from: private */
    public int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int currentLimit = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private boolean enableAliasing = false;
    private final InputStream input;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit = 100;
    private RefillCallback refillCallback = null;
    private int sizeLimit = 67108864;
    private int totalBytesRetired;

    private interface RefillCallback {
        void onRefill();
    }

    public static CodedInputStream newInstance(InputStream input2) {
        return new CodedInputStream(input2, 4096);
    }

    static CodedInputStream newInstance(InputStream input2, int bufferSize2) {
        return new CodedInputStream(input2, bufferSize2);
    }

    public static CodedInputStream newInstance(byte[] buf) {
        return newInstance(buf, 0, buf.length);
    }

    public static CodedInputStream newInstance(byte[] buf, int off, int len) {
        return newInstance(buf, off, len, false);
    }

    static CodedInputStream newInstance(byte[] buf, int off, int len, boolean bufferIsImmutable2) {
        CodedInputStream result = new CodedInputStream(buf, off, len, bufferIsImmutable2);
        try {
            result.pushLimit(len);
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static CodedInputStream newInstance(ByteBuffer buf) {
        if (buf.hasArray()) {
            return newInstance(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
        }
        ByteBuffer temp = buf.duplicate();
        byte[] buffer2 = new byte[temp.remaining()];
        temp.get(buffer2);
        return newInstance(buffer2);
    }

    public int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
            return this.lastTag;
        }
        throw InvalidProtocolBufferException.invalidTag();
    }

    public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
        if (this.lastTag != value) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    public int getLastTag() {
        return this.lastTag;
    }

    public boolean skipField(int tag) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                skipRawVarint();
                return true;
            case 1:
                skipRawBytes(8);
                return true;
            case 2:
                skipRawBytes(readRawVarint32());
                return true;
            case 3:
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            case 4:
                return false;
            case 5:
                skipRawBytes(4);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public boolean skipField(int tag, CodedOutputStream output) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                long value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            case 1:
                long value2 = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value2);
                return true;
            case 2:
                ByteString value3 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value3);
                return true;
            case 3:
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            case 4:
                return false;
            case 5:
                int value4 = readRawLittleEndian32();
                output.writeRawVarint32(tag);
                output.writeFixed32NoTag(value4);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public void skipMessage() throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0 || !skipField(tag)) {
            }
            tag = readTag();
            return;
        } while (!skipField(tag));
    }

    public void skipMessage(CodedOutputStream output) throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0 || !skipField(tag, output)) {
            }
            tag = readTag();
            return;
        } while (!skipField(tag, output));
    }

    private class SkippedDataSink implements RefillCallback {
        private ByteArrayOutputStream byteArrayStream;
        private int lastPos = CodedInputStream.this.bufferPos;

        private SkippedDataSink() {
        }

        public void onRefill() {
            if (this.byteArrayStream == null) {
                this.byteArrayStream = new ByteArrayOutputStream();
            }
            this.byteArrayStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            this.lastPos = 0;
        }

        /* access modifiers changed from: package-private */
        public ByteBuffer getSkippedData() {
            if (this.byteArrayStream == null) {
                return ByteBuffer.wrap(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            }
            this.byteArrayStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos);
            return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
        }
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readRawLittleEndian64());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readRawLittleEndian32());
    }

    public long readUInt64() throws IOException {
        return readRawVarint64();
    }

    public long readInt64() throws IOException {
        return readRawVarint64();
    }

    public int readInt32() throws IOException {
        return readRawVarint32();
    }

    public long readFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public boolean readBool() throws IOException {
        return readRawVarint64() != 0;
    }

    public String readString() throws IOException {
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            String result = new String(this.buffer, this.bufferPos, size, Internal.UTF_8);
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return "";
        } else {
            if (size > this.bufferSize) {
                return new String(readRawBytesSlowPath(size), Internal.UTF_8);
            }
            refillBuffer(size);
            String result2 = new String(this.buffer, this.bufferPos, size, Internal.UTF_8);
            this.bufferPos += size;
            return result2;
        }
    }

    public String readStringRequireUtf8() throws IOException {
        int pos;
        byte[] bytes;
        int size = readRawVarint32();
        int oldPos = this.bufferPos;
        if (size <= this.bufferSize - oldPos && size > 0) {
            bytes = this.buffer;
            this.bufferPos = oldPos + size;
            pos = oldPos;
        } else if (size == 0) {
            return "";
        } else {
            if (size <= this.bufferSize) {
                refillBuffer(size);
                bytes = this.buffer;
                pos = 0;
                this.bufferPos = 0 + size;
            } else {
                bytes = readRawBytesSlowPath(size);
                pos = 0;
            }
        }
        if (Utf8.isValidUtf8(bytes, pos, pos + size)) {
            return new String(bytes, pos, size, Internal.UTF_8);
        }
        throw InvalidProtocolBufferException.invalidUtf8();
    }

    public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth < this.recursionLimit) {
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistry);
            checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            this.recursionDepth--;
            return;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth < this.recursionLimit) {
            this.recursionDepth++;
            T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
            checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            this.recursionDepth--;
            return result;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    @Deprecated
    public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
        readGroup(fieldNumber, builder, (ExtensionRegistryLite) null);
    }

    public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth < this.recursionLimit) {
            int oldLimit = pushLimit(length);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistry);
            checkLastTagWas(0);
            this.recursionDepth--;
            popLimit(oldLimit);
            return;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth < this.recursionLimit) {
            int oldLimit = pushLimit(length);
            this.recursionDepth++;
            T result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
            checkLastTagWas(0);
            this.recursionDepth--;
            popLimit(oldLimit);
            return result;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public ByteString readBytes() throws IOException {
        ByteString result;
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            if (!this.bufferIsImmutable || !this.enableAliasing) {
                result = ByteString.copyFrom(this.buffer, this.bufferPos, size);
            } else {
                result = ByteString.wrap(this.buffer, this.bufferPos, size);
            }
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return ByteString.EMPTY;
        } else {
            return ByteString.wrap(readRawBytesSlowPath(size));
        }
    }

    public byte[] readByteArray() throws IOException {
        int size = readRawVarint32();
        if (size > this.bufferSize - this.bufferPos || size <= 0) {
            return readRawBytesSlowPath(size);
        }
        byte[] result = Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size);
        this.bufferPos += size;
        return result;
    }

    public ByteBuffer readByteBuffer() throws IOException {
        ByteBuffer result;
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            if (this.input != null || this.bufferIsImmutable || !this.enableAliasing) {
                result = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size));
            } else {
                result = ByteBuffer.wrap(this.buffer, this.bufferPos, size).slice();
            }
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
        } else {
            return ByteBuffer.wrap(readRawBytesSlowPath(size));
        }
    }

    public int readUInt32() throws IOException {
        return readRawVarint32();
    }

    public int readEnum() throws IOException {
        return readRawVarint32();
    }

    public int readSFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return decodeZigZag64(readRawVarint64());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0071, code lost:
        if (r1[r2] < 0) goto L_0x0074;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readRawVarint32() throws java.io.IOException {
        /*
            r6 = this;
            int r0 = r6.bufferPos
            int r1 = r6.bufferSize
            if (r1 != r0) goto L_0x0008
            goto L_0x0074
        L_0x0008:
            byte[] r1 = r6.buffer
            int r2 = r0 + 1
            byte r0 = r1[r0]
            r3 = r0
            if (r0 < 0) goto L_0x0014
            r6.bufferPos = r2
            return r3
        L_0x0014:
            int r0 = r6.bufferSize
            int r0 = r0 - r2
            r4 = 9
            if (r0 >= r4) goto L_0x001c
            goto L_0x0074
        L_0x001c:
            int r0 = r2 + 1
            byte r2 = r1[r2]
            int r2 = r2 << 7
            r2 = r2 ^ r3
            r3 = r2
            if (r2 >= 0) goto L_0x0029
            r2 = r3 ^ -128(0xffffffffffffff80, float:NaN)
            goto L_0x007f
        L_0x0029:
            int r2 = r0 + 1
            byte r0 = r1[r0]
            int r0 = r0 << 14
            r0 = r0 ^ r3
            r3 = r0
            if (r0 < 0) goto L_0x0039
            r0 = r3 ^ 16256(0x3f80, float:2.278E-41)
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x007f
        L_0x0039:
            int r0 = r2 + 1
            byte r2 = r1[r2]
            int r2 = r2 << 21
            r2 = r2 ^ r3
            r3 = r2
            if (r2 >= 0) goto L_0x0048
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r2 = r2 ^ r3
            goto L_0x007f
        L_0x0048:
            int r2 = r0 + 1
            byte r0 = r1[r0]
            int r4 = r0 << 28
            r3 = r3 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r3 = r3 ^ r4
            if (r0 >= 0) goto L_0x007d
            int r4 = r2 + 1
            byte r2 = r1[r2]
            if (r2 >= 0) goto L_0x007a
            int r2 = r4 + 1
            byte r4 = r1[r4]
            if (r4 >= 0) goto L_0x007d
            int r4 = r2 + 1
            byte r2 = r1[r2]
            if (r2 >= 0) goto L_0x007a
            int r2 = r4 + 1
            byte r4 = r1[r4]
            if (r4 >= 0) goto L_0x007d
            int r4 = r2 + 1
            byte r2 = r1[r2]
            if (r2 >= 0) goto L_0x007a
        L_0x0074:
            long r0 = r6.readRawVarint64SlowPath()
            int r1 = (int) r0
            return r1
        L_0x007a:
            r2 = r3
            r0 = r4
            goto L_0x007f
        L_0x007d:
            r0 = r2
            r2 = r3
        L_0x007f:
            r6.bufferPos = r0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.readRawVarint32():int");
    }

    private void skipRawVarint() throws IOException {
        if (this.bufferSize - this.bufferPos >= 10) {
            byte[] buffer2 = this.buffer;
            int pos = this.bufferPos;
            int i = 0;
            while (i < 10) {
                int pos2 = pos + 1;
                if (buffer2[pos] >= 0) {
                    this.bufferPos = pos2;
                    return;
                } else {
                    i++;
                    pos = pos2;
                }
            }
        }
        skipRawVarintSlowPath();
    }

    private void skipRawVarintSlowPath() throws IOException {
        int i = 0;
        while (i < 10) {
            if (readRawByte() < 0) {
                i++;
            } else {
                return;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    static int readRawVarint32(InputStream input2) throws IOException {
        int firstByte = input2.read();
        if (firstByte != -1) {
            return readRawVarint32(firstByte, input2);
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    public static int readRawVarint32(int firstByte, InputStream input2) throws IOException {
        if ((firstByte & 128) == 0) {
            return firstByte;
        }
        int result = firstByte & 127;
        int offset = 7;
        while (offset < 32) {
            int b = input2.read();
            if (b != -1) {
                result |= (b & 127) << offset;
                if ((b & 128) == 0) {
                    return result;
                }
                offset += 7;
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
        while (offset < 64) {
            int b2 = input2.read();
            if (b2 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if ((b2 & 128) == 0) {
                return result;
            } else {
                offset += 7;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00bf, code lost:
        if (((long) r1[r2]) < 0) goto L_0x00c2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readRawVarint64() throws java.io.IOException {
        /*
            r10 = this;
            int r0 = r10.bufferPos
            int r1 = r10.bufferSize
            if (r1 != r0) goto L_0x0008
            goto L_0x00c2
        L_0x0008:
            byte[] r1 = r10.buffer
            int r2 = r0 + 1
            byte r0 = r1[r0]
            r3 = r0
            if (r0 < 0) goto L_0x0015
            r10.bufferPos = r2
            long r4 = (long) r3
            return r4
        L_0x0015:
            int r0 = r10.bufferSize
            int r0 = r0 - r2
            r4 = 9
            if (r0 >= r4) goto L_0x001e
            goto L_0x00c2
        L_0x001e:
            int r0 = r2 + 1
            byte r2 = r1[r2]
            int r2 = r2 << 7
            r2 = r2 ^ r3
            r3 = r2
            if (r2 >= 0) goto L_0x002d
            r2 = r3 ^ -128(0xffffffffffffff80, float:NaN)
            long r4 = (long) r2
            goto L_0x00c8
        L_0x002d:
            int r2 = r0 + 1
            byte r0 = r1[r0]
            int r0 = r0 << 14
            r0 = r0 ^ r3
            r3 = r0
            if (r0 < 0) goto L_0x003d
            r0 = r3 ^ 16256(0x3f80, float:2.278E-41)
            long r4 = (long) r0
            r0 = r2
            goto L_0x00c8
        L_0x003d:
            int r0 = r2 + 1
            byte r2 = r1[r2]
            int r2 = r2 << 21
            r2 = r2 ^ r3
            r3 = r2
            if (r2 >= 0) goto L_0x004e
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r2 = r2 ^ r3
            long r4 = (long) r2
            goto L_0x00c8
        L_0x004e:
            long r4 = (long) r3
            int r2 = r0 + 1
            byte r0 = r1[r0]
            long r6 = (long) r0
            r0 = 28
            long r6 = r6 << r0
            long r4 = r4 ^ r6
            r6 = r4
            r8 = 0
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 < 0) goto L_0x0065
            r4 = 266354560(0xfe03f80, double:1.315966377E-315)
            long r4 = r4 ^ r6
            r0 = r2
            goto L_0x00c8
        L_0x0065:
            int r0 = r2 + 1
            byte r2 = r1[r2]
            long r4 = (long) r2
            r2 = 35
            long r4 = r4 << r2
            long r4 = r4 ^ r6
            r6 = r4
            int r2 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x007a
            r4 = -34093383808(0xfffffff80fe03f80, double:NaN)
            long r4 = r4 ^ r6
            goto L_0x00c8
        L_0x007a:
            int r2 = r0 + 1
            byte r0 = r1[r0]
            long r4 = (long) r0
            r0 = 42
            long r4 = r4 << r0
            long r4 = r4 ^ r6
            r6 = r4
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 < 0) goto L_0x0090
            r4 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
            long r4 = r4 ^ r6
            r0 = r2
            goto L_0x00c8
        L_0x0090:
            int r0 = r2 + 1
            byte r2 = r1[r2]
            long r4 = (long) r2
            r2 = 49
            long r4 = r4 << r2
            long r4 = r4 ^ r6
            r6 = r4
            int r2 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x00a5
            r4 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
            long r4 = r4 ^ r6
            goto L_0x00c8
        L_0x00a5:
            int r2 = r0 + 1
            byte r0 = r1[r0]
            long r4 = (long) r0
            r0 = 56
            long r4 = r4 << r0
            long r4 = r4 ^ r6
            r6 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
            long r4 = r4 ^ r6
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 >= 0) goto L_0x00c7
            int r0 = r2 + 1
            byte r2 = r1[r2]
            long r6 = (long) r2
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x00c8
        L_0x00c2:
            long r0 = r10.readRawVarint64SlowPath()
            return r0
        L_0x00c7:
            r0 = r2
        L_0x00c8:
            r10.bufferPos = r0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.readRawVarint64():long");
    }

    /* access modifiers changed from: package-private */
    public long readRawVarint64SlowPath() throws IOException {
        long result = 0;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b = readRawByte();
            result |= ((long) (b & Ev3Constants.Opcode.MEMORY_READ)) << shift;
            if ((b & 128) == 0) {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int readRawLittleEndian32() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 4) {
            refillBuffer(4);
            pos = this.bufferPos;
        }
        byte[] buffer2 = this.buffer;
        this.bufferPos = pos + 4;
        return (buffer2[pos] & Ev3Constants.Opcode.TST) | ((buffer2[pos + 1] & Ev3Constants.Opcode.TST) << 8) | ((buffer2[pos + 2] & Ev3Constants.Opcode.TST) << 16) | ((buffer2[pos + 3] & Ev3Constants.Opcode.TST) << 24);
    }

    public long readRawLittleEndian64() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 8) {
            refillBuffer(8);
            pos = this.bufferPos;
        }
        byte[] buffer2 = this.buffer;
        this.bufferPos = pos + 8;
        return (((long) buffer2[pos]) & 255) | ((((long) buffer2[pos + 1]) & 255) << 8) | ((((long) buffer2[pos + 2]) & 255) << 16) | ((((long) buffer2[pos + 3]) & 255) << 24) | ((((long) buffer2[pos + 4]) & 255) << 32) | ((((long) buffer2[pos + 5]) & 255) << 40) | ((((long) buffer2[pos + 6]) & 255) << 48) | ((((long) buffer2[pos + 7]) & 255) << 56);
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    private CodedInputStream(byte[] buffer2, int off, int len, boolean bufferIsImmutable2) {
        this.buffer = buffer2;
        this.bufferSize = off + len;
        this.bufferPos = off;
        this.totalBytesRetired = -off;
        this.input = null;
        this.bufferIsImmutable = bufferIsImmutable2;
    }

    private CodedInputStream(InputStream input2, int bufferSize2) {
        this.buffer = new byte[bufferSize2];
        this.bufferPos = 0;
        this.totalBytesRetired = 0;
        this.input = input2;
        this.bufferIsImmutable = false;
    }

    public void enableAliasing(boolean enabled) {
        this.enableAliasing = enabled;
    }

    public int setRecursionLimit(int limit) {
        if (limit >= 0) {
            int oldLimit = this.recursionLimit;
            this.recursionLimit = limit;
            return oldLimit;
        }
        throw new IllegalArgumentException("Recursion limit cannot be negative: " + limit);
    }

    public int setSizeLimit(int limit) {
        if (limit >= 0) {
            int oldLimit = this.sizeLimit;
            this.sizeLimit = limit;
            return oldLimit;
        }
        throw new IllegalArgumentException("Size limit cannot be negative: " + limit);
    }

    public void resetSizeCounter() {
        this.totalBytesRetired = -this.bufferPos;
    }

    public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
        if (byteLimit >= 0) {
            int byteLimit2 = byteLimit + this.totalBytesRetired + this.bufferPos;
            int oldLimit = this.currentLimit;
            if (byteLimit2 <= oldLimit) {
                this.currentLimit = byteLimit2;
                recomputeBufferSizeAfterLimit();
                return oldLimit;
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int bufferEnd = this.totalBytesRetired + this.bufferSize;
        if (bufferEnd > this.currentLimit) {
            this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
            this.bufferSize -= this.bufferSizeAfterLimit;
            return;
        }
        this.bufferSizeAfterLimit = 0;
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
        recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        if (this.currentLimit == Integer.MAX_VALUE) {
            return -1;
        }
        return this.currentLimit - (this.totalBytesRetired + this.bufferPos);
    }

    public boolean isAtEnd() throws IOException {
        return this.bufferPos == this.bufferSize && !tryRefillBuffer(1);
    }

    public int getTotalBytesRead() {
        return this.totalBytesRetired + this.bufferPos;
    }

    private void refillBuffer(int n) throws IOException {
        if (!tryRefillBuffer(n)) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    private boolean tryRefillBuffer(int n) throws IOException {
        if (this.bufferPos + n <= this.bufferSize) {
            throw new IllegalStateException("refillBuffer() called when " + n + " bytes were already available in buffer");
        } else if (this.totalBytesRetired + this.bufferPos + n > this.currentLimit) {
            return false;
        } else {
            if (this.refillCallback != null) {
                this.refillCallback.onRefill();
            }
            if (this.input != null) {
                int pos = this.bufferPos;
                if (pos > 0) {
                    if (this.bufferSize > pos) {
                        System.arraycopy(this.buffer, pos, this.buffer, 0, this.bufferSize - pos);
                    }
                    this.totalBytesRetired += pos;
                    this.bufferSize -= pos;
                    this.bufferPos = 0;
                }
                int bytesRead = this.input.read(this.buffer, this.bufferSize, this.buffer.length - this.bufferSize);
                if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
                    throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + bytesRead + "\nThe InputStream implementation is buggy.");
                } else if (bytesRead > 0) {
                    this.bufferSize += bytesRead;
                    if ((this.totalBytesRetired + n) - this.sizeLimit <= 0) {
                        recomputeBufferSizeAfterLimit();
                        if (this.bufferSize >= n) {
                            return true;
                        }
                        return tryRefillBuffer(n);
                    }
                    throw InvalidProtocolBufferException.sizeLimitExceeded();
                }
            }
            return false;
        }
    }

    public byte readRawByte() throws IOException {
        if (this.bufferPos == this.bufferSize) {
            refillBuffer(1);
        }
        byte[] bArr = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return bArr[i];
    }

    public byte[] readRawBytes(int size) throws IOException {
        int pos = this.bufferPos;
        if (size > this.bufferSize - pos || size <= 0) {
            return readRawBytesSlowPath(size);
        }
        this.bufferPos = pos + size;
        return Arrays.copyOfRange(this.buffer, pos, pos + size);
    }

    private byte[] readRawBytesSlowPath(int size) throws IOException {
        if (size > 0) {
            int currentMessageSize = this.totalBytesRetired + this.bufferPos + size;
            if (currentMessageSize > this.sizeLimit) {
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            } else if (currentMessageSize > this.currentLimit) {
                skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (this.input != null) {
                int originalBufferPos = this.bufferPos;
                int bufferedBytes = this.bufferSize - this.bufferPos;
                this.totalBytesRetired += this.bufferSize;
                this.bufferPos = 0;
                this.bufferSize = 0;
                int sizeLeft = size - bufferedBytes;
                if (sizeLeft < 4096 || sizeLeft <= this.input.available()) {
                    byte[] bytes = new byte[size];
                    System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
                    int pos = bufferedBytes;
                    while (pos < bytes.length) {
                        int n = this.input.read(bytes, pos, size - pos);
                        if (n != -1) {
                            this.totalBytesRetired += n;
                            pos += n;
                        } else {
                            throw InvalidProtocolBufferException.truncatedMessage();
                        }
                    }
                    return bytes;
                }
                List<byte[]> chunks = new ArrayList<>();
                while (sizeLeft > 0) {
                    byte[] chunk = new byte[Math.min(sizeLeft, 4096)];
                    int pos2 = 0;
                    while (pos2 < chunk.length) {
                        int n2 = this.input.read(chunk, pos2, chunk.length - pos2);
                        if (n2 != -1) {
                            this.totalBytesRetired += n2;
                            pos2 += n2;
                        } else {
                            throw InvalidProtocolBufferException.truncatedMessage();
                        }
                    }
                    sizeLeft -= chunk.length;
                    chunks.add(chunk);
                }
                byte[] bytes2 = new byte[size];
                System.arraycopy(this.buffer, originalBufferPos, bytes2, 0, bufferedBytes);
                int pos3 = bufferedBytes;
                for (byte[] chunk2 : chunks) {
                    System.arraycopy(chunk2, 0, bytes2, pos3, chunk2.length);
                    pos3 += chunk2.length;
                }
                return bytes2;
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        } else if (size == 0) {
            return Internal.EMPTY_BYTE_ARRAY;
        } else {
            throw InvalidProtocolBufferException.negativeSize();
        }
    }

    public void skipRawBytes(int size) throws IOException {
        if (size > this.bufferSize - this.bufferPos || size < 0) {
            skipRawBytesSlowPath(size);
        } else {
            this.bufferPos += size;
        }
    }

    private void skipRawBytesSlowPath(int size) throws IOException {
        if (size < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (this.totalBytesRetired + this.bufferPos + size <= this.currentLimit) {
            int pos = this.bufferSize - this.bufferPos;
            this.bufferPos = this.bufferSize;
            refillBuffer(1);
            while (size - pos > this.bufferSize) {
                pos += this.bufferSize;
                this.bufferPos = this.bufferSize;
                refillBuffer(1);
            }
            this.bufferPos = size - pos;
        } else {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }
}
