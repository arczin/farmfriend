package gnu.kawa.xml;

public class Base64Binary extends BinaryObject {
    public static final String ENCODING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public Base64Binary(byte[] data) {
        this.data = data;
    }

    public static Base64Binary valueOf(String str) {
        return new Base64Binary(str);
    }

    public Base64Binary(String str) {
        int v;
        int len = str.length();
        int blen = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (!Character.isWhitespace(ch) && ch != '=') {
                blen++;
            }
        }
        byte[] bytes = new byte[((blen * 3) / 4)];
        int blen2 = 0;
        int buffered = 0;
        int padding = 0;
        int blen3 = 0;
        for (int i2 = 0; i2 < len; i2++) {
            char ch2 = str.charAt(i2);
            if (ch2 >= 'A' && ch2 <= 'Z') {
                v = ch2 - 'A';
            } else if (ch2 >= 'a' && ch2 <= 'z') {
                v = (ch2 - 'a') + 26;
            } else if (ch2 >= '0' && ch2 <= '9') {
                v = (ch2 - '0') + 52;
            } else if (ch2 == '+') {
                v = 62;
            } else if (ch2 == '/') {
                v = 63;
            } else {
                if (Character.isWhitespace(ch2) != 0) {
                    continue;
                } else if (ch2 == '=') {
                    padding++;
                } else {
                    v = -1;
                }
            }
            if (v < 0 || padding > 0) {
                throw new IllegalArgumentException("illegal character in base64Binary string at position " + i2);
            }
            int value = (blen2 << 6) + v;
            buffered++;
            if (buffered == 4) {
                int blen4 = blen3 + 1;
                bytes[blen3] = (byte) (value >> 16);
                int blen5 = blen4 + 1;
                bytes[blen4] = (byte) (value >> 8);
                bytes[blen5] = (byte) value;
                buffered = 0;
                blen3 = blen5 + 1;
                blen2 = value;
            } else {
                blen2 = value;
            }
        }
        if (buffered + padding <= 0 ? blen3 != bytes.length : !(buffered + padding == 4 && (blen2 & ((1 << padding) - 1)) == 0 && (blen3 + 3) - padding == bytes.length)) {
            throw new IllegalArgumentException();
        }
        switch (padding) {
            case 1:
                int blen6 = blen3 + 1;
                bytes[blen3] = (byte) (blen2 << 10);
                int i3 = blen6 + 1;
                bytes[blen6] = (byte) (blen2 >> 2);
                break;
            case 2:
                bytes[blen3] = (byte) (blen2 >> 4);
                int i4 = blen3 + 1;
                break;
        }
        this.data = bytes;
    }

    public StringBuffer toString(StringBuffer sbuf) {
        byte[] bb = this.data;
        int len = bb.length;
        int value = 0;
        int i = 0;
        while (i < len) {
            value = (value << 8) | (bb[i] & 255);
            i++;
            if (i % 3 == 0) {
                sbuf.append(ENCODING.charAt((value >> 18) & 63));
                sbuf.append(ENCODING.charAt((value >> 12) & 63));
                sbuf.append(ENCODING.charAt((value >> 6) & 63));
                sbuf.append(ENCODING.charAt(value & 63));
            }
        }
        switch (len % 3) {
            case 1:
                sbuf.append(ENCODING.charAt((value >> 2) & 63));
                sbuf.append(ENCODING.charAt((value << 4) & 63));
                sbuf.append("==");
                break;
            case 2:
                sbuf.append(ENCODING.charAt((value >> 10) & 63));
                sbuf.append(ENCODING.charAt((value >> 4) & 63));
                sbuf.append(ENCODING.charAt((value << 2) & 63));
                sbuf.append('=');
                break;
        }
        return sbuf;
    }

    public String toString() {
        return toString(new StringBuffer()).toString();
    }
}
