package gnu.kawa.functions;

import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat */
class LispEscapeFormat extends ReportFormat {
    public static final int ESCAPE_ALL = 242;
    public static final int ESCAPE_NORMAL = 241;
    public static final LispEscapeFormat alwaysTerminate = new LispEscapeFormat(0, -1073741824);
    boolean escapeAll;
    int param1;
    int param2;
    int param3;

    public LispEscapeFormat(int param12, int param22) {
        this.param1 = param12;
        this.param2 = param22;
        this.param3 = -1073741824;
    }

    public LispEscapeFormat(int param12, int param22, int param32) {
        this.param1 = param12;
        this.param2 = param22;
        this.param3 = param32;
    }

    static Numeric getParam(int param, Object[] args, int start) {
        if (param == -1342177280) {
            return IntNum.make(args.length - start);
        }
        if (param != -1610612736) {
            return IntNum.make(param);
        }
        Numeric numeric = args[start];
        if (numeric instanceof Numeric) {
            return numeric;
        }
        if (numeric instanceof Number) {
            if ((numeric instanceof Float) || (numeric instanceof Double)) {
                return new DFloNum(numeric.doubleValue());
            }
            return IntNum.make(numeric.longValue());
        } else if (numeric instanceof Char) {
            return new IntNum(((Char) numeric).intValue());
        } else {
            if (numeric instanceof Character) {
                return new IntNum(numeric.charValue());
            }
            return new DFloNum(Double.NaN);
        }
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        boolean do_terminate;
        int i = start;
        int i2 = 0;
        boolean z = true;
        if (this.param1 == -1073741824) {
            if (start != args.length) {
                z = false;
            }
            do_terminate = z;
        } else if (this.param2 == -1073741824 && this.param1 == 0) {
            do_terminate = true;
        } else {
            Numeric arg1 = getParam(this.param1, args, start);
            if (this.param1 == -1610612736) {
                start++;
            }
            if (this.param2 == -1073741824) {
                do_terminate = arg1.isZero();
            } else {
                Numeric arg2 = getParam(this.param2, args, start);
                if (this.param2 == -1610612736) {
                    start++;
                }
                if (this.param3 == -1073741824) {
                    do_terminate = arg1.equals(arg2);
                } else {
                    Numeric arg3 = getParam(this.param3, args, start);
                    if (this.param3 == -1610612736) {
                        start++;
                    }
                    if (!arg2.geq(arg1) || !arg3.geq(arg2)) {
                        z = false;
                    }
                    do_terminate = z;
                }
            }
        }
        if (do_terminate) {
            i2 = this.escapeAll ? ESCAPE_ALL : ESCAPE_NORMAL;
        }
        return result(i2, start);
    }
}
