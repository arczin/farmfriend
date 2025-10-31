package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;

public class ProcInitializer extends Initializer {
    LambdaExp proc;

    public ProcInitializer(LambdaExp lexp, Compilation comp, Field field) {
        this.field = field;
        this.proc = lexp;
        LambdaExp heapLambda = field.getStaticFlag() ? comp.getModule() : lexp.getOwningLambda();
        if (!(heapLambda instanceof ModuleExp) || !comp.isStatic()) {
            this.next = heapLambda.initChain;
            heapLambda.initChain = this;
            return;
        }
        this.next = comp.clinitChain;
        comp.clinitChain = this;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: gnu.expr.ModuleMethod} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void emitLoadModuleMethod(gnu.expr.LambdaExp r16, gnu.expr.Compilation r17) {
        /*
            r0 = r16
            r1 = r17
            gnu.expr.Declaration r2 = r0.nameDecl
            if (r2 != 0) goto L_0x000d
            java.lang.String r3 = r16.getName()
            goto L_0x0011
        L_0x000d:
            java.lang.Object r3 = r2.getSymbol()
        L_0x0011:
            r4 = 0
            boolean r5 = r1.immediate
            if (r5 == 0) goto L_0x004a
            if (r3 == 0) goto L_0x004a
            if (r2 == 0) goto L_0x004a
            gnu.mapping.Environment r5 = gnu.mapping.Environment.getCurrent()
            boolean r6 = r3 instanceof gnu.mapping.Symbol
            if (r6 == 0) goto L_0x0026
            r6 = r3
            gnu.mapping.Symbol r6 = (gnu.mapping.Symbol) r6
            goto L_0x0034
        L_0x0026:
            java.lang.String r6 = r3.toString()
            java.lang.String r6 = r6.intern()
            java.lang.String r7 = ""
            gnu.mapping.Symbol r6 = gnu.mapping.Symbol.make(r7, r6)
        L_0x0034:
            gnu.expr.Language r7 = r17.getLanguage()
            gnu.expr.Declaration r8 = r0.nameDecl
            java.lang.Object r7 = r7.getEnvPropertyFor(r8)
            r8 = 0
            java.lang.Object r8 = r5.get(r6, r7, r8)
            boolean r9 = r8 instanceof gnu.expr.ModuleMethod
            if (r9 == 0) goto L_0x004a
            r4 = r8
            gnu.expr.ModuleMethod r4 = (gnu.expr.ModuleMethod) r4
        L_0x004a:
            gnu.bytecode.CodeAttr r5 = r17.getCode()
            gnu.bytecode.ClassType r6 = gnu.expr.Compilation.typeModuleMethod
            r7 = 1
            if (r4 != 0) goto L_0x005c
            r5.emitNew(r6)
            r5.emitDup((int) r7)
            java.lang.String r8 = "<init>"
            goto L_0x0065
        L_0x005c:
            gnu.expr.Target r8 = gnu.expr.Target.pushValue(r6)
            r1.compileConstant(r4, r8)
            java.lang.String r8 = "init"
        L_0x0065:
            r9 = 4
            gnu.bytecode.Method r9 = r6.getDeclaredMethod((java.lang.String) r8, (int) r9)
            boolean r10 = r16.getNeedsClosureEnv()
            if (r10 == 0) goto L_0x0075
            gnu.expr.LambdaExp r10 = r16.getOwningLambda()
            goto L_0x0079
        L_0x0075:
            gnu.expr.ModuleExp r10 = r17.getModule()
        L_0x0079:
            boolean r11 = r10 instanceof gnu.expr.ClassExp
            if (r11 == 0) goto L_0x008d
            gnu.bytecode.Field r11 = r10.staticLinkField
            if (r11 == 0) goto L_0x008d
            gnu.bytecode.Scope r11 = r5.getCurrentScope()
            gnu.bytecode.Variable r11 = r11.getVariable(r7)
            r5.emitLoad(r11)
            goto L_0x00fd
        L_0x008d:
            boolean r11 = r10 instanceof gnu.expr.ModuleExp
            if (r11 == 0) goto L_0x00fa
            gnu.bytecode.ClassType r11 = r1.moduleClass
            gnu.bytecode.ClassType r12 = r1.mainClass
            if (r11 != r12) goto L_0x00a0
            gnu.bytecode.Method r11 = r1.method
            boolean r11 = r11.getStaticFlag()
            if (r11 != 0) goto L_0x00a0
            goto L_0x00fa
        L_0x00a0:
            gnu.bytecode.Variable r11 = r1.moduleInstanceVar
            if (r11 != 0) goto L_0x00f4
            gnu.bytecode.LocalVarsAttr r11 = r5.locals
            gnu.bytecode.Scope r11 = r11.current_scope
            gnu.bytecode.ClassType r12 = r1.moduleClass
            java.lang.String r13 = "$instance"
            gnu.bytecode.Variable r11 = r11.addVariable(r5, r12, r13)
            r1.moduleInstanceVar = r11
            gnu.bytecode.ClassType r11 = r1.moduleClass
            gnu.bytecode.ClassType r12 = r1.mainClass
            if (r11 == r12) goto L_0x00ea
            boolean r11 = r17.isStatic()
            if (r11 != 0) goto L_0x00ea
            gnu.bytecode.ClassType r11 = r1.moduleClass
            r5.emitNew(r11)
            gnu.bytecode.ClassType r11 = r1.moduleClass
            r5.emitDup((gnu.bytecode.Type) r11)
            gnu.bytecode.ClassType r11 = r1.moduleClass
            gnu.bytecode.Method r11 = r11.constructor
            r5.emitInvokeSpecial(r11)
            gnu.bytecode.ClassType r11 = r1.moduleClass
            gnu.bytecode.ClassType r12 = r1.mainClass
            r13 = 0
            java.lang.String r14 = "$main"
            gnu.bytecode.Field r11 = r11.addField(r14, r12, r13)
            r1.moduleInstanceMainField = r11
            gnu.bytecode.ClassType r11 = r1.moduleClass
            r5.emitDup((gnu.bytecode.Type) r11)
            r5.emitPushThis()
            gnu.bytecode.Field r11 = r1.moduleInstanceMainField
            r5.emitPutField(r11)
            goto L_0x00ef
        L_0x00ea:
            gnu.bytecode.Field r11 = r1.moduleInstanceMainField
            r5.emitGetStatic(r11)
        L_0x00ef:
            gnu.bytecode.Variable r11 = r1.moduleInstanceVar
            r5.emitStore(r11)
        L_0x00f4:
            gnu.bytecode.Variable r11 = r1.moduleInstanceVar
            r5.emitLoad(r11)
            goto L_0x00fd
        L_0x00fa:
            r5.emitPushThis()
        L_0x00fd:
            int r11 = r16.getSelectorValue(r17)
            r5.emitPushInt(r11)
            gnu.expr.Target r11 = gnu.expr.Target.pushObject
            r1.compileConstant(r3, r11)
            int r11 = r0.min_args
            gnu.expr.Keyword[] r12 = r0.keywords
            if (r12 != 0) goto L_0x0112
            int r12 = r0.max_args
            goto L_0x0113
        L_0x0112:
            r12 = -1
        L_0x0113:
            int r12 = r12 << 12
            r11 = r11 | r12
            r5.emitPushInt(r11)
            r5.emitInvoke(r9)
            java.lang.Object[] r11 = r0.properties
            if (r11 == 0) goto L_0x0164
            java.lang.Object[] r11 = r0.properties
            int r11 = r11.length
            r12 = 0
        L_0x0124:
            if (r12 >= r11) goto L_0x0164
            java.lang.Object[] r13 = r0.properties
            r13 = r13[r12]
            if (r13 == 0) goto L_0x015c
            gnu.mapping.Symbol r14 = gnu.mapping.PropertySet.nameKey
            if (r13 == r14) goto L_0x015c
            java.lang.Object[] r14 = r0.properties
            int r15 = r12 + 1
            r14 = r14[r15]
            r5.emitDup((int) r7)
            r1.compileConstant(r13)
            gnu.expr.Target r15 = gnu.expr.Target.pushObject
            boolean r7 = r14 instanceof gnu.expr.Expression
            if (r7 == 0) goto L_0x0149
            r7 = r14
            gnu.expr.Expression r7 = (gnu.expr.Expression) r7
            r7.compile((gnu.expr.Compilation) r1, (gnu.expr.Target) r15)
            goto L_0x014c
        L_0x0149:
            r1.compileConstant(r14, r15)
        L_0x014c:
            java.lang.String r7 = "gnu.mapping.PropertySet"
            gnu.bytecode.ClassType r7 = gnu.bytecode.ClassType.make(r7)
            java.lang.String r0 = "setProperty"
            r1 = 2
            gnu.bytecode.Method r0 = r7.getDeclaredMethod((java.lang.String) r0, (int) r1)
            r5.emitInvokeVirtual(r0)
        L_0x015c:
            int r12 = r12 + 2
            r7 = 1
            r0 = r16
            r1 = r17
            goto L_0x0124
        L_0x0164:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ProcInitializer.emitLoadModuleMethod(gnu.expr.LambdaExp, gnu.expr.Compilation):void");
    }

    public void emit(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (!this.field.getStaticFlag()) {
            code.emitPushThis();
        }
        emitLoadModuleMethod(this.proc, comp);
        if (this.field.getStaticFlag()) {
            code.emitPutStatic(this.field);
        } else {
            code.emitPutField(this.field);
        }
    }

    public void reportError(String message, Compilation comp) {
        String saveFile = comp.getFileName();
        int saveLine = comp.getLineNumber();
        int saveColumn = comp.getColumnNumber();
        comp.setLocation(this.proc);
        String name = this.proc.getName();
        StringBuffer sbuf = new StringBuffer(message);
        if (name == null) {
            sbuf.append("unnamed procedure");
        } else {
            sbuf.append("procedure ");
            sbuf.append(name);
        }
        comp.error('e', sbuf.toString());
        comp.setLine(saveFile, saveLine, saveColumn);
    }
}
