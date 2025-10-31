package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class InlineCalls extends ExpExpVisitor<Type> {
    private static Class[] inlinerMethodArgTypes;

    public static Expression inlineCalls(Expression exp, Compilation comp) {
        return new InlineCalls(comp).visit(exp, (Type) null);
    }

    public InlineCalls(Compilation comp) {
        setContext(comp);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: gnu.expr.Expression} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression visit(gnu.expr.Expression r3, gnu.bytecode.Type r4) {
        /*
            r2 = this;
            r0 = 1
            boolean r1 = r3.getFlag(r0)
            if (r1 != 0) goto L_0x0014
            r3.setFlag(r0)
            java.lang.Object r1 = super.visit(r3, r4)
            r3 = r1
            gnu.expr.Expression r3 = (gnu.expr.Expression) r3
            r3.setFlag(r0)
        L_0x0014:
            gnu.expr.Expression r0 = r2.checkType(r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.InlineCalls.visit(gnu.expr.Expression, gnu.bytecode.Type):gnu.expr.Expression");
    }

    public Expression checkType(Expression exp, Type required) {
        boolean incompatible;
        Expression converted;
        Method amethod;
        Type expType = exp.getType();
        boolean z = true;
        if (!(required instanceof ClassType) || !((ClassType) required).isInterface() || !expType.isSubtype(Compilation.typeProcedure) || expType.isSubtype(required)) {
            if (expType == Type.toStringType) {
                expType = Type.javalangStringType;
            }
            if (required == null || required.compare(expType) != -3) {
                z = false;
            }
            incompatible = z;
            if (incompatible && (required instanceof TypeValue) && (converted = ((TypeValue) required).convertValue(exp)) != null) {
                return converted;
            }
        } else if (!(exp instanceof LambdaExp) || (amethod = ((ClassType) required).checkSingleAbstractMethod()) == null) {
            incompatible = true;
        } else {
            LambdaExp lexp = (LambdaExp) exp;
            ObjectExp oexp = new ObjectExp();
            oexp.setLocation(exp);
            oexp.supers = new Expression[]{new QuoteExp(required)};
            oexp.setTypes(getCompilation());
            Object mname = amethod.getName();
            oexp.addMethod(lexp, mname);
            Declaration addDeclaration = oexp.addDeclaration(mname, Compilation.typeProcedure);
            oexp.firstChild = lexp;
            oexp.declareParts(this.comp);
            return visit((Expression) oexp, required);
        }
        if (incompatible) {
            Language language = this.comp.getLanguage();
            this.comp.error('w', "type " + language.formatType(expType) + " is incompatible with required type " + language.formatType(required), exp);
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitApplyExp(ApplyExp exp, Type required) {
        Expression func = exp.func;
        if (func instanceof LambdaExp) {
            LambdaExp lambdaExp = (LambdaExp) func;
            Expression inlined = inlineCall((LambdaExp) func, exp.args, false);
            if (inlined != null) {
                return visit(inlined, required);
            }
        }
        Expression func2 = visit(func, (Type) null);
        exp.func = func2;
        return func2.validateApply(exp, this, required, (Declaration) null);
    }

    public final Expression visitApplyOnly(ApplyExp exp, Type required) {
        return exp.func.validateApply(exp, this, required, (Declaration) null);
    }

    public static Integer checkIntValue(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            return null;
        }
        QuoteExp qarg = (QuoteExp) exp;
        Object value = qarg.getValue();
        if (qarg.isExplicitlyTyped() || !(value instanceof IntNum)) {
            return null;
        }
        IntNum ivalue = (IntNum) value;
        if (ivalue.inIntRange()) {
            return Integer.valueOf(ivalue.intValue());
        }
        return null;
    }

    public static Long checkLongValue(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            return null;
        }
        QuoteExp qarg = (QuoteExp) exp;
        Object value = qarg.getValue();
        if (qarg.isExplicitlyTyped() || !(value instanceof IntNum)) {
            return null;
        }
        IntNum ivalue = (IntNum) value;
        if (ivalue.inLongRange()) {
            return Long.valueOf(ivalue.longValue());
        }
        return null;
    }

    public QuoteExp fixIntValue(Expression exp) {
        Integer ival = checkIntValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Integer.TYPE));
        }
        return null;
    }

    public QuoteExp fixLongValue(Expression exp) {
        Long ival = checkLongValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Long.TYPE));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Expression visitQuoteExp(QuoteExp exp, Type required) {
        if (exp.getRawType() != null || exp.isSharedConstant()) {
            return exp;
        }
        Object value = exp.getValue();
        Object value2 = value;
        if (value == null) {
            return exp;
        }
        Type vtype = this.comp.getLanguage().getTypeFor((Class) value2.getClass());
        if (vtype == Type.toStringType) {
            vtype = Type.javalangStringType;
        }
        exp.type = vtype;
        if (!(required instanceof PrimType)) {
            return exp;
        }
        char sig1 = required.getSignature().charAt(0);
        QuoteExp ret = sig1 == 'I' ? fixIntValue(exp) : sig1 == 'J' ? fixLongValue(exp) : null;
        if (ret != null) {
            return ret;
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitReferenceExp(ReferenceExp exp, Type required) {
        Declaration decl = exp.getBinding();
        if (decl != null && decl.field == null && !decl.getCanWrite()) {
            Expression dval = decl.getValue();
            if ((dval instanceof QuoteExp) && dval != QuoteExp.undefined_exp) {
                return visitQuoteExp((QuoteExp) dval, required);
            }
            if ((dval instanceof ReferenceExp) && !decl.isAlias()) {
                ReferenceExp rval = (ReferenceExp) dval;
                Declaration rdecl = rval.getBinding();
                Type dtype = decl.getType();
                if (rdecl != null && !rdecl.getCanWrite() && ((dtype == null || dtype == Type.pointer_type || dtype == rdecl.getType()) && !rval.getDontDereference())) {
                    return visitReferenceExp(rval, required);
                }
            }
            if (!exp.isProcedureName() && (decl.flags & 1048704) == 1048704) {
                this.comp.error('e', "unimplemented: reference to method " + decl.getName() + " as variable");
                this.comp.error('e', decl, "here is the definition of ", "");
            }
        }
        return (Expression) super.visitReferenceExp(exp, required);
    }

    /* access modifiers changed from: protected */
    public Expression visitIfExp(IfExp exp, Type required) {
        Declaration decl;
        Expression test = (Expression) exp.test.visit(this, null);
        if ((test instanceof ReferenceExp) && (decl = ((ReferenceExp) test).getBinding()) != null) {
            Expression value = decl.getValue();
            if ((value instanceof QuoteExp) && value != QuoteExp.undefined_exp) {
                test = value;
            }
        }
        exp.test = test;
        if (this.exitValue == null) {
            exp.then_clause = visit(exp.then_clause, required);
        }
        if (this.exitValue == null && exp.else_clause != null) {
            exp.else_clause = visit(exp.else_clause, required);
        }
        if (test instanceof QuoteExp) {
            return exp.select(this.comp.getLanguage().isTrue(((QuoteExp) test).getValue()));
        }
        if (!test.getType().isVoid()) {
            return exp;
        }
        boolean truth = this.comp.getLanguage().isTrue(Values.empty);
        this.comp.error('w', "void-valued condition is always " + truth);
        return new BeginExp(test, exp.select(truth));
    }

    /* access modifiers changed from: protected */
    public Expression visitBeginExp(BeginExp exp, Type required) {
        int last = exp.length - 1;
        int i = 0;
        while (i <= last) {
            exp.exps[i] = visit(exp.exps[i], i < last ? null : required);
            i++;
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitScopeExp(ScopeExp exp, Type required) {
        exp.visitChildren(this, null);
        visitDeclarationTypes(exp);
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.type == null) {
                Expression val = decl.getValue();
                decl.type = Type.objectType;
                decl.setType((val == null || val == QuoteExp.undefined_exp) ? Type.objectType : val.getType());
            }
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
        r2 = (gnu.expr.ReferenceExp) r10.body;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression visitLetExp(gnu.expr.LetExp r10, gnu.bytecode.Type r11) {
        /*
            r9 = this;
            gnu.expr.Declaration r0 = r10.firstDecl()
            gnu.expr.Expression[] r1 = r10.inits
            int r1 = r1.length
            r2 = 0
        L_0x0008:
            r3 = 0
            if (r2 >= r1) goto L_0x003e
            gnu.expr.Expression[] r4 = r10.inits
            r4 = r4[r2]
            r5 = 8192(0x2000, double:4.0474E-320)
            boolean r5 = r0.getFlag(r5)
            if (r5 == 0) goto L_0x001f
            gnu.expr.QuoteExp r6 = gnu.expr.QuoteExp.undefined_exp
            if (r4 == r6) goto L_0x001f
            gnu.bytecode.Type r3 = r0.getType()
        L_0x001f:
            gnu.expr.Expression r6 = r9.visit((gnu.expr.Expression) r4, (gnu.bytecode.Type) r3)
            gnu.expr.Expression[] r7 = r10.inits
            r7[r2] = r6
            gnu.expr.Expression r7 = r0.value
            if (r7 != r4) goto L_0x0037
            r7 = r6
            r0.value = r6
            if (r5 != 0) goto L_0x0037
            gnu.bytecode.Type r8 = r7.getType()
            r0.setType(r8)
        L_0x0037:
            int r2 = r2 + 1
            gnu.expr.Declaration r0 = r0.nextDecl()
            goto L_0x0008
        L_0x003e:
            java.lang.Object r2 = r9.exitValue
            if (r2 != 0) goto L_0x004a
            gnu.expr.Expression r2 = r10.body
            gnu.expr.Expression r2 = r9.visit((gnu.expr.Expression) r2, (gnu.bytecode.Type) r11)
            r10.body = r2
        L_0x004a:
            gnu.expr.Expression r2 = r10.body
            boolean r2 = r2 instanceof gnu.expr.ReferenceExp
            if (r2 == 0) goto L_0x007d
            gnu.expr.Expression r2 = r10.body
            gnu.expr.ReferenceExp r2 = (gnu.expr.ReferenceExp) r2
            gnu.expr.Declaration r4 = r2.getBinding()
            if (r4 == 0) goto L_0x007d
            gnu.expr.ScopeExp r5 = r4.context
            if (r5 != r10) goto L_0x007d
            boolean r5 = r2.getDontDereference()
            if (r5 != 0) goto L_0x007d
            r5 = 1
            if (r1 != r5) goto L_0x007d
            gnu.expr.Expression[] r5 = r10.inits
            r6 = 0
            r5 = r5[r6]
            gnu.expr.Expression r6 = r4.getTypeExp()
            gnu.expr.QuoteExp r7 = gnu.expr.QuoteExp.classObjectExp
            if (r6 == r7) goto L_0x007c
            gnu.expr.ApplyExp r7 = gnu.expr.Compilation.makeCoercion((gnu.expr.Expression) r5, (gnu.expr.Expression) r6)
            gnu.expr.Expression r5 = r9.visitApplyOnly(r7, r3)
        L_0x007c:
            return r5
        L_0x007d:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.InlineCalls.visitLetExp(gnu.expr.LetExp, gnu.bytecode.Type):gnu.expr.Expression");
    }

    /* access modifiers changed from: protected */
    public Expression visitLambdaExp(LambdaExp exp, Type required) {
        Declaration firstDecl = exp.firstDecl();
        if (firstDecl != null && firstDecl.isThisParameter() && !exp.isClassMethod() && firstDecl.type == null) {
            firstDecl.setType(this.comp.mainClass);
        }
        return visitScopeExp((ScopeExp) exp, required);
    }

    /* access modifiers changed from: protected */
    public Expression visitTryExp(TryExp exp, Type required) {
        if (exp.getCatchClauses() == null && exp.getFinallyClause() == null) {
            return visit(exp.try_clause, required);
        }
        return (Expression) super.visitTryExp(exp, required);
    }

    /* access modifiers changed from: protected */
    public Expression visitSetExpValue(Expression new_value, Type required, Declaration decl) {
        return visit(new_value, (decl == null || decl.isAlias()) ? null : decl.type);
    }

    /* access modifiers changed from: protected */
    public Expression visitSetExp(SetExp exp, Type required) {
        Declaration decl = exp.getBinding();
        super.visitSetExp(exp, required);
        if (!exp.isDefining() && decl != null && (decl.flags & 1048704) == 1048704) {
            this.comp.error('e', "can't assign to method " + decl.getName(), exp);
        }
        if (decl != null && decl.getFlag(8192) && CompileReflect.checkKnownClass(decl.getType(), this.comp) < 0) {
            decl.setType(Type.errorType);
        }
        return exp;
    }

    private static synchronized Class[] getInlinerMethodArgTypes() throws Exception {
        Class[] t;
        synchronized (InlineCalls.class) {
            t = inlinerMethodArgTypes;
            if (t == null) {
                t = new Class[]{Class.forName("gnu.expr.ApplyExp"), Class.forName("gnu.expr.InlineCalls"), Class.forName("gnu.bytecode.Type"), Class.forName("gnu.mapping.Procedure")};
                inlinerMethodArgTypes = t;
            }
        }
        return t;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005e, code lost:
        if (r2 == null) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r3 = new java.lang.Object[]{r13, r12, r14, r15};
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006f, code lost:
        if ((r2 instanceof gnu.mapping.Procedure) == false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007a, code lost:
        return (gnu.expr.Expression) ((gnu.mapping.Procedure) r2).applyN(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007d, code lost:
        if ((r2 instanceof java.lang.reflect.Method) == false) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0088, code lost:
        return (gnu.expr.Expression) ((java.lang.reflect.Method) r2).invoke((java.lang.Object) null, r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression maybeInline(gnu.expr.ApplyExp r13, gnu.bytecode.Type r14, gnu.mapping.Procedure r15) {
        /*
            r12 = this;
            r0 = 101(0x65, float:1.42E-43)
            r1 = 0
            monitor-enter(r15)     // Catch:{ all -> 0x008f }
            r2 = 0
            gnu.mapping.Symbol r3 = gnu.mapping.Procedure.validateApplyKey     // Catch:{ all -> 0x008a }
            java.lang.Object r2 = r15.getProperty(r3, r1)     // Catch:{ all -> 0x008a }
            boolean r3 = r2 instanceof java.lang.String     // Catch:{ all -> 0x008d }
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x005d
            r3 = r2
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x008d }
            r6 = 58
            int r6 = r3.indexOf(r6)     // Catch:{ all -> 0x008d }
            r7 = 0
            if (r6 <= 0) goto L_0x003c
            java.lang.String r8 = r3.substring(r4, r6)     // Catch:{ all -> 0x008d }
            int r9 = r6 + 1
            java.lang.String r9 = r3.substring(r9)     // Catch:{ all -> 0x008d }
            java.lang.Class r10 = r15.getClass()     // Catch:{ all -> 0x008d }
            java.lang.ClassLoader r10 = r10.getClassLoader()     // Catch:{ all -> 0x008d }
            java.lang.Class r10 = java.lang.Class.forName(r8, r5, r10)     // Catch:{ all -> 0x008d }
            java.lang.Class[] r11 = getInlinerMethodArgTypes()     // Catch:{ all -> 0x008d }
            java.lang.reflect.Method r11 = r10.getDeclaredMethod(r9, r11)     // Catch:{ all -> 0x008d }
            r7 = r11
        L_0x003c:
            if (r7 != 0) goto L_0x005c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x008d }
            r4.<init>()     // Catch:{ all -> 0x008d }
            java.lang.String r5 = "inliner property string for "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x008d }
            java.lang.StringBuilder r4 = r4.append(r15)     // Catch:{ all -> 0x008d }
            java.lang.String r5 = " is not of the form CLASS:METHOD"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x008d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x008d }
            r12.error(r0, r4)     // Catch:{ all -> 0x008d }
            monitor-exit(r15)     // Catch:{ all -> 0x008d }
            return r1
        L_0x005c:
            r2 = r7
        L_0x005d:
            monitor-exit(r15)     // Catch:{ all -> 0x008d }
            if (r2 == 0) goto L_0x0089
            r3 = 4
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x008f }
            r3[r4] = r13     // Catch:{ all -> 0x008f }
            r3[r5] = r12     // Catch:{ all -> 0x008f }
            r4 = 2
            r3[r4] = r14     // Catch:{ all -> 0x008f }
            r4 = 3
            r3[r4] = r15     // Catch:{ all -> 0x008f }
            boolean r4 = r2 instanceof gnu.mapping.Procedure     // Catch:{ all -> 0x008f }
            if (r4 == 0) goto L_0x007b
            r4 = r2
            gnu.mapping.Procedure r4 = (gnu.mapping.Procedure) r4     // Catch:{ all -> 0x008f }
            java.lang.Object r4 = r4.applyN(r3)     // Catch:{ all -> 0x008f }
            gnu.expr.Expression r4 = (gnu.expr.Expression) r4     // Catch:{ all -> 0x008f }
            return r4
        L_0x007b:
            boolean r4 = r2 instanceof java.lang.reflect.Method     // Catch:{ all -> 0x008f }
            if (r4 == 0) goto L_0x0089
            r4 = r2
            java.lang.reflect.Method r4 = (java.lang.reflect.Method) r4     // Catch:{ all -> 0x008f }
            java.lang.Object r4 = r4.invoke(r1, r3)     // Catch:{ all -> 0x008f }
            gnu.expr.Expression r4 = (gnu.expr.Expression) r4     // Catch:{ all -> 0x008f }
            return r4
        L_0x0089:
            goto L_0x00bd
        L_0x008a:
            r3 = move-exception
        L_0x008b:
            monitor-exit(r15)     // Catch:{ all -> 0x008d }
            throw r3     // Catch:{ all -> 0x008f }
        L_0x008d:
            r3 = move-exception
            goto L_0x008b
        L_0x008f:
            r2 = move-exception
            boolean r3 = r2 instanceof java.lang.reflect.InvocationTargetException
            if (r3 == 0) goto L_0x009b
            r3 = r2
            java.lang.reflect.InvocationTargetException r3 = (java.lang.reflect.InvocationTargetException) r3
            java.lang.Throwable r2 = r3.getTargetException()
        L_0x009b:
            gnu.text.SourceMessages r3 = r12.messages
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "caught exception in inliner for "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r15)
            java.lang.String r5 = " - "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r4 = r4.toString()
            r3.error((char) r0, (java.lang.String) r4, (java.lang.Throwable) r2)
        L_0x00bd:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.InlineCalls.maybeInline(gnu.expr.ApplyExp, gnu.bytecode.Type, gnu.mapping.Procedure):gnu.expr.Expression");
    }

    public static Expression inlineCall(LambdaExp lexp, Expression[] args, boolean makeCopy) {
        Expression[] cargs;
        IdentityHashTable mapper;
        if (lexp.keywords != null || (lexp.nameDecl != null && !makeCopy)) {
            return null;
        }
        boolean varArgs = lexp.max_args < 0;
        if ((lexp.min_args != lexp.max_args || lexp.min_args != args.length) && (!varArgs || lexp.min_args != 0)) {
            return null;
        }
        Declaration prev = null;
        int i = 0;
        if (makeCopy) {
            mapper = new IdentityHashTable();
            cargs = Expression.deepCopy(args, mapper);
            if (cargs == null && args != null) {
                return null;
            }
        } else {
            mapper = null;
            cargs = args;
        }
        if (varArgs) {
            Expression[] xargs = new Expression[(args.length + 1)];
            xargs[0] = QuoteExp.getInstance(lexp.firstDecl().type);
            System.arraycopy(args, 0, xargs, 1, args.length);
            cargs = new Expression[]{new ApplyExp((Procedure) Invoke.make, xargs)};
        }
        LetExp let = new LetExp(cargs);
        Declaration param = lexp.firstDecl();
        while (param != null) {
            Declaration next = param.nextDecl();
            if (makeCopy) {
                Declaration ldecl = let.addDeclaration(param.symbol, param.type);
                if (param.typeExp != null) {
                    ldecl.typeExp = Expression.deepCopy(param.typeExp);
                    if (ldecl.typeExp == null) {
                        return null;
                    }
                }
                mapper.put(param, ldecl);
            } else {
                lexp.remove(prev, param);
                let.add(prev, param);
            }
            if (!varArgs && !param.getCanWrite()) {
                param.setValue(cargs[i]);
            }
            prev = param;
            param = next;
            i++;
        }
        Expression body = lexp.body;
        if (makeCopy && (body = Expression.deepCopy(body, mapper)) == null && lexp.body != null) {
            return null;
        }
        let.body = body;
        return let;
    }
}
