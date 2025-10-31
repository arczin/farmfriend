package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.Type;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.MethodProc;
import java.lang.reflect.Array;

/* compiled from: LambdaExp */
class Closure extends MethodProc {
    Object[][] evalFrames;
    LambdaExp lambda;

    public int numArgs() {
        return this.lambda.min_args | (this.lambda.max_args << 12);
    }

    public Closure(LambdaExp lexp, CallContext ctx) {
        this.lambda = lexp;
        Object[][] oldFrames = ctx.evalFrames;
        if (oldFrames != null) {
            int n = oldFrames.length;
            while (n > 0 && oldFrames[n - 1] == null) {
                n--;
            }
            this.evalFrames = new Object[n][];
            System.arraycopy(oldFrames, 0, this.evalFrames, 0, n);
        }
        setSymbol(this.lambda.getSymbol());
    }

    public int match0(CallContext ctx) {
        return matchN(new Object[0], ctx);
    }

    public int match1(Object arg1, CallContext ctx) {
        return matchN(new Object[]{arg1}, ctx);
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2}, ctx);
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3}, ctx);
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    public int matchN(Object[] args, CallContext ctx) {
        int min;
        int nargs;
        int num;
        int i;
        Object value;
        int num2;
        Object[] value2;
        Object value3;
        Object[] objArr = args;
        CallContext callContext = ctx;
        int i2 = numArgs();
        int nargs2 = objArr.length;
        int min2 = i2 & 4095;
        if (nargs2 < min2) {
            return -983040 | min2;
        }
        int max = i2 >> 12;
        if (nargs2 > max && max >= 0) {
            return -917504 | max;
        }
        Object[] evalFrame = new Object[this.lambda.frameSize];
        int opt_args = this.lambda.defaultArgs == null ? 0 : this.lambda.defaultArgs.length - (this.lambda.keywords == null ? 0 : this.lambda.keywords.length);
        int min_args = this.lambda.min_args;
        Declaration decl = this.lambda.firstDecl();
        int key_i = 0;
        int opt_i = 0;
        int i3 = 0;
        while (decl != null) {
            if (i3 < min_args) {
                int i4 = i3 + 1;
                value = objArr[i3];
                num = i2;
                nargs = nargs2;
                min = min2;
                num2 = i4;
                i = max;
            } else if (i3 < min_args + opt_args) {
                if (i3 < nargs2) {
                    Object obj = objArr[i3];
                    i3++;
                    value3 = obj;
                } else {
                    value3 = this.lambda.evalDefaultArg(opt_i, callContext);
                }
                opt_i++;
                num = i2;
                nargs = nargs2;
                min = min2;
                i = max;
                num2 = i3;
                value = value3;
            } else if (this.lambda.max_args >= 0 || i3 != min_args + opt_args) {
                num = i2;
                nargs = nargs2;
                min = min2;
                i = max;
                int key_i2 = key_i + 1;
                Object value4 = Keyword.searchForKeyword(objArr, min_args + opt_args, this.lambda.keywords[key_i]);
                if (value4 == Special.dfault) {
                    value4 = this.lambda.evalDefaultArg(opt_i, callContext);
                }
                opt_i++;
                key_i = key_i2;
                num2 = i3;
                value = value4;
            } else if (decl.type instanceof ArrayType) {
                num = i2;
                int num3 = nargs2 - i3;
                nargs = nargs2;
                Type elementType = ((ArrayType) decl.type).getComponentType();
                if (elementType == Type.objectType) {
                    Object[] rest = new Object[num3];
                    min = min2;
                    System.arraycopy(objArr, i3, rest, 0, num3);
                    value2 = rest;
                    i = max;
                } else {
                    min = min2;
                    Class elementClass = elementType.getReflectClass();
                    i = max;
                    Object value5 = Array.newInstance(elementClass, num3);
                    Class cls = elementClass;
                    int j = 0;
                    while (j < num3) {
                        try {
                            Object el = elementType.coerceFromObject(objArr[i3 + j]);
                            Array.set(value5, j, el);
                            j++;
                            Object obj2 = el;
                        } catch (ClassCastException e) {
                            return (i3 + j) | MethodProc.NO_MATCH_BAD_TYPE;
                        }
                    }
                    value2 = value5;
                }
                num2 = i3;
                value = value2;
            } else {
                num = i2;
                nargs = nargs2;
                min = min2;
                i = max;
                num2 = i3;
                value = LList.makeList(objArr, i3);
            }
            if (decl.type != null) {
                try {
                    value = decl.type.coerceFromObject(value);
                } catch (ClassCastException e2) {
                    return num2 | MethodProc.NO_MATCH_BAD_TYPE;
                }
            }
            if (decl.isIndirectBinding()) {
                Location loc = decl.makeIndirectLocationFor();
                loc.set(value);
                value = loc;
            }
            evalFrame[decl.evalIndex] = value;
            decl = decl.nextDecl();
            i3 = num2;
            max = i;
            i2 = num;
            nargs2 = nargs;
            min2 = min;
        }
        callContext.values = evalFrame;
        callContext.where = 0;
        callContext.next = 0;
        callContext.proc = this;
        return 0;
    }

    public void apply(CallContext ctx) throws Throwable {
        int level = ScopeExp.nesting(this.lambda);
        Object[] evalFrame = ctx.values;
        Object[][] saveFrames = ctx.evalFrames;
        int numFrames = this.evalFrames == null ? 0 : this.evalFrames.length;
        if (level >= numFrames) {
            numFrames = level;
        }
        Object[][] newFrames = new Object[(numFrames + 10)][];
        if (this.evalFrames != null) {
            System.arraycopy(this.evalFrames, 0, newFrames, 0, this.evalFrames.length);
        }
        newFrames[level] = evalFrame;
        ctx.evalFrames = newFrames;
        try {
            if (this.lambda.body == null) {
                StringBuffer sbuf = new StringBuffer("procedure ");
                String name = this.lambda.getName();
                if (name == null) {
                    name = "<anonymous>";
                }
                sbuf.append(name);
                int line = this.lambda.getLineNumber();
                if (line > 0) {
                    sbuf.append(" at line ");
                    sbuf.append(line);
                }
                sbuf.append(" was called before it was expanded");
                throw new RuntimeException(sbuf.toString());
            }
            this.lambda.body.apply(ctx);
        } finally {
            ctx.evalFrames = saveFrames;
        }
    }

    public Object getProperty(Object key, Object defaultValue) {
        Object value = super.getProperty(key, defaultValue);
        if (value == null) {
            return this.lambda.getProperty(key, defaultValue);
        }
        return value;
    }
}
