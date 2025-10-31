package com.google.appinventor.components.runtime.multidex;

import androidx.core.internal.view.SupportMenu;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

class ZipEntryReader {
    private static final long CENSIG = 33639248;
    private static final int GPBF_ENCRYPTED_FLAG = 1;
    private static final int GPBF_UNSUPPORTED_MASK = 1;
    static final Charset UTF_8 = Charset.forName("UTF-8");

    ZipEntryReader() {
    }

    static ZipEntry readEntry(ByteBuffer in) throws IOException {
        int compressionMethod;
        ByteBuffer byteBuffer = in;
        int sig = in.getInt();
        if (((long) sig) == CENSIG) {
            ByteBuffer byteBuffer2 = (ByteBuffer) byteBuffer.position(8);
            int gpbf = in.getShort() & SupportMenu.USER_MASK;
            if ((gpbf & 1) == 0) {
                short s = in.getShort() & 65535;
                int time = in.getShort() & SupportMenu.USER_MASK;
                int modDate = in.getShort() & SupportMenu.USER_MASK;
                int nameLength = in.getShort() & 65535;
                int i = in.getShort() & SupportMenu.USER_MASK;
                short s2 = 65535 & in.getShort();
                ByteBuffer byteBuffer3 = (ByteBuffer) byteBuffer.position(42);
                int i2 = sig;
                int i3 = gpbf;
                byte[] nameBytes = new byte[nameLength];
                long j = ((long) in.getInt()) & 4294967295L;
                byteBuffer.get(nameBytes, 0, nameBytes.length);
                int i4 = nameLength;
                int i5 = i;
                String name = new String(nameBytes, 0, nameBytes.length, UTF_8);
                ZipEntry entry = new ZipEntry(name);
                entry.setMethod(s);
                short s3 = s2;
                entry.setTime(getTime(time, modDate));
                entry.setCrc(((long) in.getInt()) & 4294967295L);
                entry.setCompressedSize(((long) in.getInt()) & 4294967295L);
                entry.setSize(((long) in.getInt()) & 4294967295L);
                if (s3 > 0) {
                    int commentByteCount = s3;
                    byte[] commentBytes = new byte[commentByteCount];
                    byteBuffer.get(commentBytes, 0, commentByteCount);
                    String str = name;
                    int i6 = commentByteCount;
                    short s4 = s;
                    compressionMethod = 0;
                    entry.setComment(new String(commentBytes, 0, commentBytes.length, UTF_8));
                } else {
                    short s5 = s;
                    short s6 = s3;
                    compressionMethod = 0;
                }
                if (i5 > 0) {
                    int extraLength = i5;
                    byte[] extra = new byte[extraLength];
                    byteBuffer.get(extra, compressionMethod, extraLength);
                    entry.setExtra(extra);
                } else {
                    int extraLength2 = i5;
                }
                return entry;
            }
            throw new ZipException("Invalid General Purpose Bit Flag: " + gpbf);
        }
        throw new ZipException("Central Directory Entry not found");
    }

    private static long getTime(int time, int modDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(14, 0);
        GregorianCalendar gregorianCalendar = cal;
        gregorianCalendar.set(((modDate >> 9) & 127) + 1980, ((modDate >> 5) & 15) - 1, modDate & 31, (time >> 11) & 31, (time >> 5) & 63, (time & 31) << 1);
        return cal.getTime().getTime();
    }
}
