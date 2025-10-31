package com.google.protobuf;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

final class UnsafeUtil {
    private static final long ARRAY_BASE_OFFSET = ((long) byteArrayBaseOffset());
    private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(field(Buffer.class, "address"));
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();
    private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();
    private static final Unsafe UNSAFE = getUnsafe();

    private UnsafeUtil() {
    }

    static boolean hasUnsafeArrayOperations() {
        return HAS_UNSAFE_ARRAY_OPERATIONS;
    }

    static boolean hasUnsafeByteBufferOperations() {
        return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }

    static long getArrayBaseOffset() {
        return ARRAY_BASE_OFFSET;
    }

    static byte getByte(byte[] target, long offset) {
        return UNSAFE.getByte(target, offset);
    }

    static void putByte(byte[] target, long offset, byte value) {
        UNSAFE.putByte(target, offset, value);
    }

    static void copyMemory(byte[] src, long srcOffset, byte[] target, long targetOffset, long length) {
        UNSAFE.copyMemory(src, srcOffset, target, targetOffset, length);
    }

    static long getLong(byte[] target, long offset) {
        return UNSAFE.getLong(target, offset);
    }

    static byte getByte(long address) {
        return UNSAFE.getByte(address);
    }

    static void putByte(long address, byte value) {
        UNSAFE.putByte(address, value);
    }

    static long getLong(long address) {
        return UNSAFE.getLong(address);
    }

    static void copyMemory(long srcAddress, long targetAddress, long length) {
        UNSAFE.copyMemory(srcAddress, targetAddress, length);
    }

    static long addressOffset(ByteBuffer buffer) {
        return UNSAFE.getLong(buffer, BUFFER_ADDRESS_OFFSET);
    }

    private static Unsafe getUnsafe() {
        try {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    Class<Unsafe> k = Unsafe.class;
                    for (Field f : k.getDeclaredFields()) {
                        f.setAccessible(true);
                        Object x = f.get((Object) null);
                        if (k.isInstance(x)) {
                            return k.cast(x);
                        }
                    }
                    return null;
                }
            });
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean supportsUnsafeArrayOperations() {
        if (UNSAFE == null) {
            return false;
        }
        try {
            Class<?> clazz = UNSAFE.getClass();
            clazz.getMethod("arrayBaseOffset", new Class[]{Class.class});
            clazz.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            clazz.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            Class<Object> cls = Object.class;
            Class cls2 = Long.TYPE;
            clazz.getMethod("copyMemory", new Class[]{cls, cls2, cls, cls2, cls2});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static boolean supportsUnsafeByteBufferOperations() {
        if (UNSAFE == null) {
            return false;
        }
        try {
            Class<?> clazz = UNSAFE.getClass();
            clazz.getMethod("objectFieldOffset", new Class[]{Field.class});
            clazz.getMethod("getByte", new Class[]{Long.TYPE});
            clazz.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            clazz.getMethod("getLong", new Class[]{Long.TYPE});
            Class cls = Long.TYPE;
            clazz.getMethod("copyMemory", new Class[]{cls, cls, cls});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static int byteArrayBaseOffset() {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return UNSAFE.arrayBaseOffset(byte[].class);
        }
        return -1;
    }

    private static long fieldOffset(Field field) {
        if (field == null || UNSAFE == null) {
            return -1;
        }
        return UNSAFE.objectFieldOffset(field);
    }

    private static Field field(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (Throwable th) {
            return null;
        }
    }
}
