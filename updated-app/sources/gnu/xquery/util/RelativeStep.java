package gnu.xquery.util;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.kawa.xml.Nodes;
import gnu.kawa.xml.TreeScanner;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class RelativeStep extends MethodProc implements Inlineable {
    public static final RelativeStep relativeStep = new RelativeStep();

    RelativeStep() {
        setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateApplyRelativeStep");
    }

    public int numArgs() {
        return 8194;
    }

    public void apply(CallContext ctx) throws Throwable {
        Nodes values;
        Object arg = ctx.getNextArg();
        Procedure proc = (Procedure) ctx.getNextArg();
        Consumer out = ctx.consumer;
        if (arg instanceof Nodes) {
            values = (Nodes) arg;
        } else {
            values = new Nodes();
            Values.writeValues(arg, values);
        }
        int count = values.size();
        int it = 0;
        IntNum countObj = IntNum.make(count);
        RelativeStepFilter filter = new RelativeStepFilter(out);
        for (int pos = 1; pos <= count; pos++) {
            it = values.nextPos(it);
            proc.check3(values.getPosPrevious(it), IntNum.make(pos), countObj, ctx);
            Values.writeValues(ctx.runUntilValue(), filter);
        }
        filter.finish();
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0119  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compile(gnu.expr.ApplyExp r26, gnu.expr.Compilation r27, gnu.expr.Target r28) {
        /*
            r25 = this;
            r6 = r27
            r7 = r28
            gnu.expr.Expression[] r8 = r26.getArgs()
            r9 = 0
            r10 = r8[r9]
            r0 = 1
            r11 = r8[r0]
            boolean r1 = r7 instanceof gnu.expr.IgnoreTarget
            if (r1 == 0) goto L_0x0019
            r10.compile((gnu.expr.Compilation) r6, (gnu.expr.Target) r7)
            r11.compile((gnu.expr.Compilation) r6, (gnu.expr.Target) r7)
            return
        L_0x0019:
            gnu.bytecode.Type r1 = r26.getTypeRaw()
            if (r1 != 0) goto L_0x0023
            gnu.bytecode.ClassType r1 = gnu.bytecode.Type.pointer_type
            r12 = r1
            goto L_0x0024
        L_0x0023:
            r12 = r1
        L_0x0024:
            gnu.bytecode.Type r13 = gnu.kawa.reflect.OccurrenceType.itemPrimeType(r12)
            gnu.kawa.xml.NodeType r1 = gnu.kawa.xml.NodeType.anyNodeTest
            int r14 = r1.compare(r13)
            if (r14 < 0) goto L_0x0033
            r1 = 78
            goto L_0x003b
        L_0x0033:
            r1 = -3
            if (r14 != r1) goto L_0x0039
            r1 = 65
            goto L_0x003b
        L_0x0039:
            r1 = 32
        L_0x003b:
            gnu.kawa.xml.TreeScanner r15 = extractStep(r11)
            r5 = 78
            if (r15 == 0) goto L_0x0067
            gnu.bytecode.Type r2 = r10.getType()
            boolean r3 = r15 instanceof gnu.kawa.xml.ChildAxis
            if (r3 != 0) goto L_0x0053
            boolean r3 = r15 instanceof gnu.kawa.xml.AttributeAxis
            if (r3 != 0) goto L_0x0053
            boolean r3 = r15 instanceof gnu.kawa.xml.SelfAxis
            if (r3 == 0) goto L_0x0067
        L_0x0053:
            boolean r3 = r2 instanceof gnu.kawa.xml.NodeSetType
            if (r3 != 0) goto L_0x0063
            if (r1 != r5) goto L_0x0067
            gnu.bytecode.Type r3 = r10.getType()
            boolean r3 = gnu.kawa.reflect.OccurrenceType.itemCountIsZeroOrOne(r3)
            if (r3 == 0) goto L_0x0067
        L_0x0063:
            r1 = 83
            r4 = r1
            goto L_0x0068
        L_0x0067:
            r4 = r1
        L_0x0068:
            boolean r1 = r7 instanceof gnu.expr.ConsumerTarget
            if (r1 != 0) goto L_0x0070
            gnu.expr.ConsumerTarget.compileUsingConsumer(r26, r27, r28)
            return
        L_0x0070:
            gnu.bytecode.CodeAttr r3 = r27.getCode()
            gnu.bytecode.Scope r2 = r3.pushScope()
            r1 = 65
            if (r4 == r1) goto L_0x00cf
            r1 = 83
            if (r4 != r1) goto L_0x0085
            r17 = r2
            r2 = 78
            goto L_0x00d3
        L_0x0085:
            java.lang.String r1 = "<init>"
            if (r4 != r5) goto L_0x0094
            java.lang.String r0 = "gnu.kawa.xml.SortedNodes"
            gnu.bytecode.ClassType r0 = gnu.bytecode.ClassType.make(r0)
            gnu.bytecode.Method r1 = r0.getDeclaredMethod((java.lang.String) r1, (int) r9)
            goto L_0x009f
        L_0x0094:
            java.lang.String r16 = "gnu.xquery.util.RelativeStepFilter"
            gnu.bytecode.ClassType r9 = gnu.bytecode.ClassType.make(r16)
            gnu.bytecode.Method r1 = r9.getDeclaredMethod((java.lang.String) r1, (int) r0)
            r0 = r9
        L_0x009f:
            r9 = 0
            gnu.bytecode.Variable r9 = r2.addVariable(r3, r0, r9)
            gnu.expr.ConsumerTarget r5 = new gnu.expr.ConsumerTarget
            r5.<init>(r9)
            r3.emitNew(r0)
            r3.emitDup((gnu.bytecode.Type) r0)
            r17 = r7
            gnu.expr.ConsumerTarget r17 = (gnu.expr.ConsumerTarget) r17
            r18 = r0
            gnu.bytecode.Variable r0 = r17.getConsumerVariable()
            r17 = r2
            r2 = 78
            if (r4 == r2) goto L_0x00c2
            r3.emitLoad(r0)
        L_0x00c2:
            r3.emitInvoke(r1)
            r3.emitStore(r9)
            r16 = r5
            r1 = r9
            r5 = r18
            r9 = r0
            goto L_0x00e0
        L_0x00cf:
            r17 = r2
            r2 = 78
        L_0x00d3:
            r5 = r28
            r0 = 0
            r9 = 0
            r1 = 0
            r16 = r5
            r5 = r0
            r24 = r9
            r9 = r1
            r1 = r24
        L_0x00e0:
            r0 = r11
            gnu.expr.LambdaExp r0 = (gnu.expr.LambdaExp) r0
            r18 = 1
            r19 = 0
            r20 = r1
            r1 = r10
            r21 = 78
            r2 = r18
            r22 = r3
            r3 = r19
            r23 = r4
            r4 = r27
            r6 = r5
            r7 = 78
            r5 = r16
            gnu.kawa.functions.ValuesMap.compileInlined(r0, r1, r2, r3, r4, r5)
            r1 = r23
            if (r1 != r7) goto L_0x0119
            r2 = r20
            r0 = r22
            r0.emitLoad(r2)
            r0.emitLoad(r9)
            gnu.bytecode.ClassType r3 = gnu.expr.Compilation.typeValues
            java.lang.String r4 = "writeValues"
            r5 = 2
            gnu.bytecode.Method r3 = r3.getDeclaredMethod((java.lang.String) r4, (int) r5)
            r0.emitInvokeStatic(r3)
            goto L_0x012e
        L_0x0119:
            r2 = r20
            r0 = r22
            r3 = 32
            if (r1 != r3) goto L_0x012e
            r0.emitLoad(r2)
            java.lang.String r3 = "finish"
            r4 = 0
            gnu.bytecode.Method r3 = r6.getDeclaredMethod((java.lang.String) r3, (int) r4)
            r0.emitInvoke(r3)
        L_0x012e:
            r0.popScope()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.RelativeStep.compile(gnu.expr.ApplyExp, gnu.expr.Compilation, gnu.expr.Target):void");
    }

    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }

    public static TreeScanner extractStep(Expression exp) {
        while (exp instanceof ApplyExp) {
            ApplyExp aexp = (ApplyExp) exp;
            Expression func = aexp.getFunction();
            if (func instanceof QuoteExp) {
                Object value = ((QuoteExp) func).getValue();
                if (value instanceof TreeScanner) {
                    return (TreeScanner) value;
                }
                if (value instanceof ValuesFilter) {
                    exp = aexp.getArgs()[0];
                }
            }
            return null;
        }
        return null;
    }
}
