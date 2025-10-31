package gnu.text;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class RomanIntegerFormat extends NumberFormat {
    static final String codes = "IVXLCDM";
    private static RomanIntegerFormat newRoman;
    private static RomanIntegerFormat oldRoman;
    public boolean oldStyle;

    public RomanIntegerFormat(boolean oldStyle2) {
        this.oldStyle = oldStyle2;
    }

    public RomanIntegerFormat() {
    }

    public static RomanIntegerFormat getInstance(boolean oldStyle2) {
        if (oldStyle2) {
            if (oldRoman == null) {
                oldRoman = new RomanIntegerFormat(true);
            }
            return oldRoman;
        }
        if (newRoman == null) {
            newRoman = new RomanIntegerFormat(false);
        }
        return newRoman;
    }

    public static String format(int num, boolean oldStyle2) {
        if (num <= 0 || num >= 4999) {
            return Integer.toString(num);
        }
        StringBuffer sbuf = new StringBuffer(20);
        int unit = 1000;
        for (int power = 3; power >= 0; power--) {
            int digit = num / unit;
            num -= digit * unit;
            if (digit != 0) {
                if (oldStyle2 || !(digit == 4 || digit == 9)) {
                    int rest = digit;
                    if (rest >= 5) {
                        sbuf.append(codes.charAt((power * 2) + 1));
                        rest -= 5;
                    }
                    while (true) {
                        rest--;
                        if (rest < 0) {
                            break;
                        }
                        sbuf.append(codes.charAt(power * 2));
                    }
                } else {
                    sbuf.append(codes.charAt(power * 2));
                    sbuf.append(codes.charAt((power * 2) + ((digit + 1) / 5)));
                }
            }
            unit /= 10;
        }
        return sbuf.toString();
    }

    public static String format(int num) {
        return format(num, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.StringBuffer format(long r10, java.lang.StringBuffer r12, java.text.FieldPosition r13) {
        /*
            r9 = this;
            r0 = 0
            int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x001c
            boolean r0 = r9.oldStyle
            if (r0 == 0) goto L_0x000d
            r0 = 4999(0x1387, float:7.005E-42)
            goto L_0x000f
        L_0x000d:
            r0 = 3999(0xf9f, float:5.604E-42)
        L_0x000f:
            long r0 = (long) r0
            int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x001c
            int r0 = (int) r10
            boolean r1 = r9.oldStyle
            java.lang.String r0 = format(r0, r1)
            goto L_0x0020
        L_0x001c:
            java.lang.String r0 = java.lang.Long.toString(r10)
        L_0x0020:
            if (r13 == 0) goto L_0x0045
            r1 = 1
            int r3 = r0.length()
            r4 = r3
        L_0x0029:
            int r4 = r4 + -1
            if (r4 <= 0) goto L_0x0036
            r5 = 10
            long r5 = r5 * r1
            r7 = 9
            long r1 = r5 + r7
            goto L_0x0029
        L_0x0036:
            java.lang.StringBuffer r4 = new java.lang.StringBuffer
            r4.<init>(r3)
            java.text.DecimalFormat r5 = new java.text.DecimalFormat
            java.lang.String r6 = "0"
            r5.<init>(r6)
            r5.format(r1, r4, r13)
        L_0x0045:
            r12.append(r0)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.RomanIntegerFormat.format(long, java.lang.StringBuffer, java.text.FieldPosition):java.lang.StringBuffer");
    }

    public StringBuffer format(double num, StringBuffer sbuf, FieldPosition fpos) {
        long inum = (long) num;
        if (((double) inum) == num) {
            return format(inum, sbuf, fpos);
        }
        sbuf.append(Double.toString(num));
        return sbuf;
    }

    public Number parse(String text, ParsePosition status) {
        throw new Error("RomanIntegerFormat.parseObject - not implemented");
    }
}
