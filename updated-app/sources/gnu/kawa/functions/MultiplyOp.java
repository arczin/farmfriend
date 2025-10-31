package gnu.kawa.functions;

import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MultiplyOp extends ArithOp {
    public static final MultiplyOp $St = new MultiplyOp("*");

    public MultiplyOp(String name) {
        super(name, 3);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forMul");
    }

    public Object defaultResult() {
        return IntNum.one();
    }

    public static Object apply(Object arg1, Object arg2) {
        return ((Numeric) arg1).mul(arg2);
    }

    public Object applyN(Object[] args) {
        int len;
        Object[] objArr = args;
        int len2 = objArr.length;
        if (len2 == 0) {
            return IntNum.one();
        }
        Number result = (Number) objArr[0];
        int code = Arithmetic.classifyValue(result);
        int i = 1;
        BigInteger bi1 = null;
        double d1 = 0.0d;
        double d12 = 0.0d;
        float f2 = 0;
        float f1 = 0;
        float f12 = 0.0f;
        float f13 = 0.0f;
        long l1 = 0;
        long l2 = 0;
        BigInteger bi12 = null;
        while (i < len2) {
            Object arg2 = objArr[i];
            int code2 = Arithmetic.classifyValue(arg2);
            code = code < code2 ? code2 : code;
            switch (code) {
                case 1:
                    len = len2;
                    int i1 = Arithmetic.asInt(result);
                    int i2 = Arithmetic.asInt(arg2);
                    result = new Integer(i1 * i2);
                    f2 = i1;
                    f1 = i2;
                    break;
                case 2:
                    len = len2;
                    float f = f1;
                    long l12 = Arithmetic.asLong(result);
                    long l22 = Arithmetic.asLong(arg2);
                    result = new Long(l12 * l22);
                    f2 = f2;
                    long j = l12;
                    l1 = l22;
                    l2 = j;
                    break;
                case 3:
                    len = len2;
                    long l23 = l1;
                    float f3 = f2;
                    float f4 = f1;
                    long l13 = l2;
                    BigInteger bi13 = Arithmetic.asBigInteger(result);
                    BigInteger bi2 = Arithmetic.asBigInteger(arg2);
                    result = bi13.multiply(bi2);
                    bi12 = bi13;
                    bi1 = bi2;
                    l2 = l13;
                    l1 = l23;
                    break;
                case 4:
                    len = len2;
                    result = IntNum.times(Arithmetic.asIntNum((Object) result), Arithmetic.asIntNum(arg2));
                    f2 = f2;
                    f1 = f1;
                    l1 = l1;
                    bi1 = bi1;
                    bi12 = bi12;
                    break;
                case 5:
                    len = len2;
                    BigInteger bi22 = bi1;
                    long l24 = l1;
                    float f5 = f2;
                    float f6 = f1;
                    long l14 = l2;
                    BigDecimal bd1 = Arithmetic.asBigDecimal(result);
                    BigDecimal bd2 = Arithmetic.asBigDecimal(arg2);
                    result = bd1.multiply(bd2);
                    BigDecimal bigDecimal = bd1;
                    BigDecimal bigDecimal2 = bd2;
                    l2 = l14;
                    l1 = l24;
                    bi1 = bi22;
                    bi12 = bi12;
                    break;
                case 6:
                    len = len2;
                    result = RatNum.times(Arithmetic.asRatNum(result), Arithmetic.asRatNum(arg2));
                    f2 = f2;
                    f1 = f1;
                    l1 = l1;
                    bi1 = bi1;
                    bi12 = bi12;
                    break;
                case 7:
                    BigInteger bi23 = bi1;
                    BigInteger bi14 = bi12;
                    float i12 = f2;
                    float i22 = f1;
                    float f14 = Arithmetic.asFloat(result);
                    f12 = Arithmetic.asFloat(arg2);
                    len = len2;
                    result = new Float(f14 * f12);
                    float f7 = i12;
                    bi12 = bi14;
                    bi1 = bi23;
                    f13 = f14;
                    f1 = i22;
                    f2 = f7;
                    break;
                case 8:
                    BigInteger bi24 = bi1;
                    BigInteger bi15 = bi12;
                    float i13 = f2;
                    float i23 = f1;
                    float f22 = f12;
                    float f15 = f13;
                    double d13 = Arithmetic.asDouble(result);
                    double d2 = Arithmetic.asDouble(arg2);
                    BigInteger bi16 = bi15;
                    float i14 = i13;
                    result = new Double(d13 * d2);
                    bi1 = bi24;
                    bi12 = bi16;
                    len = len2;
                    float f8 = f22;
                    f1 = i23;
                    f2 = i14;
                    float f9 = f15;
                    f12 = f8;
                    double d = d13;
                    f13 = f9;
                    d1 = d2;
                    d12 = d;
                    break;
                case 9:
                    d12 = Arithmetic.asDouble(result);
                    d1 = Arithmetic.asDouble(arg2);
                    int i3 = code2;
                    result = new DFloNum(d12 * d1);
                    bi1 = bi1;
                    bi12 = bi12;
                    len = len2;
                    break;
                default:
                    int i4 = code2;
                    len = len2;
                    BigInteger bi25 = bi1;
                    long l25 = l1;
                    float i15 = f2;
                    float f23 = f12;
                    result = Arithmetic.asNumeric(result).mul(Arithmetic.asNumeric(arg2));
                    float f10 = f23;
                    f2 = i15;
                    f1 = f1;
                    l1 = l25;
                    bi1 = bi25;
                    bi12 = bi12;
                    d12 = d12;
                    d1 = d1;
                    f12 = f10;
                    f13 = f13;
                    break;
            }
            i++;
            objArr = args;
            len2 = len;
        }
        return result;
    }
}
