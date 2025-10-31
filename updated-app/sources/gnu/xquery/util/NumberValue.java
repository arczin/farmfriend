package gnu.xquery.util;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.location.LocationRequestCompat;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XIntegerType;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RealNum;
import gnu.xml.TextUtils;
import java.math.BigDecimal;

public class NumberValue extends Procedure1 {
    public static final Double NaN = new Double(Double.NaN);
    public static final NumberValue numberValue = new NumberValue();

    public static boolean isNaN(Object arg) {
        return ((arg instanceof Double) || (arg instanceof Float) || (arg instanceof DFloNum)) && Double.isNaN(((Number) arg).doubleValue());
    }

    public Object apply1(Object arg) {
        if (!(arg == Values.empty || arg == null)) {
            try {
                return numberValue(arg);
            } catch (Throwable th) {
            }
        }
        return NaN;
    }

    public static Number numberCast(Object value) {
        if (value == Values.empty || value == null) {
            return null;
        }
        if (value instanceof Values) {
            Values vals = (Values) value;
            int ipos = vals.startPos();
            int count = 0;
            while (true) {
                int nextPos = vals.nextPos(ipos);
                ipos = nextPos;
                if (nextPos == 0) {
                    break;
                } else if (count <= 0) {
                    value = vals.getPosPrevious(ipos);
                    count++;
                } else {
                    throw new ClassCastException("non-singleton sequence cast to number");
                }
            }
        }
        if ((value instanceof KNode) || (value instanceof UntypedAtomic)) {
            return (Double) XDataType.doubleType.valueOf(TextUtils.stringValue(value));
        }
        return (Number) value;
    }

    public static Object numberValue(Object value) {
        double d;
        Object value2 = KNode.atomicValue(value);
        if ((value2 instanceof UntypedAtomic) || (value2 instanceof String)) {
            try {
                return XDataType.doubleType.valueOf(TextUtils.stringValue(value2));
            } catch (Throwable th) {
                d = Double.NaN;
            }
        } else {
            if (!(value2 instanceof Number) || (!(value2 instanceof RealNum) && (value2 instanceof Numeric))) {
                d = Double.NaN;
            } else {
                d = ((Number) value2).doubleValue();
            }
            return XDataType.makeDouble(d);
        }
    }

    public static Object abs(Object value) {
        if (value == null || value == Values.empty) {
            return value;
        }
        Number numberCast = numberCast(value);
        if (numberCast instanceof Double) {
            Double d = (Double) numberCast;
            long bits = Double.doubleToRawLongBits(d.doubleValue());
            if (bits >= 0) {
                return d;
            }
            return Double.valueOf(Double.longBitsToDouble(bits & LocationRequestCompat.PASSIVE_INTERVAL));
        } else if (numberCast instanceof Float) {
            Float d2 = (Float) numberCast;
            int bits2 = Float.floatToRawIntBits(d2.floatValue());
            if (bits2 >= 0) {
                return d2;
            }
            return Float.valueOf(Float.intBitsToFloat(bits2 & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
        } else if (!(numberCast instanceof BigDecimal)) {
            return ((Numeric) numberCast).abs();
        } else {
            BigDecimal dec = (BigDecimal) numberCast;
            if (dec.signum() < 0) {
                return dec.negate();
            }
            return dec;
        }
    }

    public static Object floor(Object val) {
        Number value = numberCast(val);
        if (value == null) {
            return val;
        }
        if (value instanceof Double) {
            return XDataType.makeDouble(Math.floor(((Double) value).doubleValue()));
        }
        if (value instanceof Float) {
            return XDataType.makeFloat((float) Math.floor((double) ((Float) value).floatValue()));
        }
        if (value instanceof BigDecimal) {
            return Arithmetic.asIntNum(((BigDecimal) value).divide(XDataType.DECIMAL_ONE, 0, 3).toBigInteger());
        }
        return ((RealNum) value).toInt(1);
    }

    public static Object ceiling(Object val) {
        Number value = numberCast(val);
        if (value == null) {
            return val;
        }
        if (value instanceof Double) {
            return XDataType.makeDouble(Math.ceil(((Double) value).doubleValue()));
        }
        if (value instanceof Float) {
            return XDataType.makeFloat((float) Math.ceil((double) ((Float) value).floatValue()));
        }
        if (value instanceof BigDecimal) {
            return Arithmetic.asIntNum(((BigDecimal) value).divide(XDataType.DECIMAL_ONE, 0, 2).toBigInteger());
        }
        return ((RealNum) value).toInt(2);
    }

    public static Object round(Object arg) {
        float val;
        double val2;
        Number value = numberCast(arg);
        if (value == null) {
            return arg;
        }
        if (value instanceof Double) {
            double val3 = ((Double) value).doubleValue();
            if (val3 < -0.5d || val3 > 0.0d || (val3 >= 0.0d && Double.doubleToLongBits(val3) >= 0)) {
                val2 = Math.floor(0.5d + val3);
            } else {
                val2 = -0.0d;
            }
            return XDataType.makeDouble(val2);
        } else if (value instanceof Float) {
            float val4 = ((Float) value).floatValue();
            if (((double) val4) < -0.5d || ((double) val4) > 0.0d || (((double) val4) >= 0.0d && Float.floatToIntBits(val4) >= 0)) {
                double d = (double) val4;
                Double.isNaN(d);
                val = (float) Math.floor(d + 0.5d);
            } else {
                val = -0.0f;
            }
            return XDataType.makeFloat(val);
        } else {
            int mode = 4;
            if (!(value instanceof BigDecimal)) {
                return ((RealNum) value).toInt(4);
            }
            BigDecimal dec = (BigDecimal) value;
            if (dec.signum() < 0) {
                mode = 5;
            }
            return Arithmetic.asIntNum(dec.divide(XDataType.DECIMAL_ONE, 0, mode).toBigInteger());
        }
    }

    public static Object roundHalfToEven(Object value, IntNum precision) {
        Number number = numberCast(value);
        if (number == null) {
            return value;
        }
        if ((value instanceof Double) || (value instanceof Float)) {
            double v = ((Number) value).doubleValue();
            if (v == 0.0d || Double.isInfinite(v) || Double.isNaN(v)) {
                return value;
            }
        }
        BigDecimal dec = ((BigDecimal) XDataType.decimalType.cast(number)).setScale(precision.intValue(), 6);
        if (number instanceof Double) {
            return XDataType.makeDouble(dec.doubleValue());
        }
        if (number instanceof Float) {
            return XDataType.makeFloat(dec.floatValue());
        }
        if (number instanceof IntNum) {
            return XIntegerType.integerType.cast(dec);
        }
        return dec;
    }

    public static Object roundHalfToEven(Object value) {
        return roundHalfToEven(value, IntNum.zero());
    }
}
