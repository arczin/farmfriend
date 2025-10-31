package gnu.expr;

import gnu.bytecode.Type;

public class FindTailCalls extends ExpExpVisitor<Expression> {
    public static void findTailCalls(Expression exp, Compilation comp) {
        FindTailCalls visitor = new FindTailCalls();
        visitor.setContext(comp);
        visitor.visit(exp, exp);
    }

    /* access modifiers changed from: protected */
    public Expression visitExpression(Expression exp, Expression returnContinuation) {
        return (Expression) super.visitExpression(exp, exp);
    }

    public Expression[] visitExps(Expression[] exps) {
        int n = exps.length;
        for (int i = 0; i < n; i++) {
            Expression expi = exps[i];
            exps[i] = (Expression) visit(expi, expi);
        }
        return exps;
    }

    /* JADX WARNING: type inference failed for: r5v6, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r6v1, types: [gnu.expr.Expression] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression visitApplyExp(gnu.expr.ApplyExp r9, gnu.expr.Expression r10) {
        /*
            r8 = this;
            gnu.expr.LambdaExp r0 = r8.currentLambda
            gnu.expr.Expression r0 = r0.body
            r1 = 0
            r2 = 1
            if (r10 != r0) goto L_0x000a
            r0 = 1
            goto L_0x000b
        L_0x000a:
            r0 = 0
        L_0x000b:
            if (r0 == 0) goto L_0x0010
            r9.setTailCall(r2)
        L_0x0010:
            gnu.expr.LambdaExp r3 = r8.currentLambda
            r9.context = r3
            r3 = 0
            r4 = 0
            gnu.expr.Expression r5 = r9.func
            boolean r5 = r5 instanceof gnu.expr.ReferenceExp
            if (r5 == 0) goto L_0x0050
            gnu.expr.Expression r1 = r9.func
            gnu.expr.ReferenceExp r1 = (gnu.expr.ReferenceExp) r1
            gnu.expr.Declaration r2 = r1.binding
            gnu.expr.Declaration r2 = gnu.expr.Declaration.followAliases(r2)
            if (r2 == 0) goto L_0x004f
            r5 = 2048(0x800, double:1.0118E-320)
            boolean r5 = r2.getFlag(r5)
            if (r5 != 0) goto L_0x0036
            gnu.expr.ApplyExp r5 = r2.firstCall
            r9.nextCall = r5
            r2.firstCall = r9
        L_0x0036:
            gnu.expr.Compilation r5 = r8.getCompilation()
            r2.setCanCall()
            boolean r6 = r5.mustCompile
            if (r6 != 0) goto L_0x0044
            r2.setCanRead()
        L_0x0044:
            gnu.expr.Expression r6 = r2.getValue()
            boolean r7 = r6 instanceof gnu.expr.LambdaExp
            if (r7 == 0) goto L_0x004f
            r3 = r6
            gnu.expr.LambdaExp r3 = (gnu.expr.LambdaExp) r3
        L_0x004f:
            goto L_0x0086
        L_0x0050:
            gnu.expr.Expression r5 = r9.func
            boolean r5 = r5 instanceof gnu.expr.LambdaExp
            if (r5 == 0) goto L_0x0068
            gnu.expr.Expression r5 = r9.func
            boolean r5 = r5 instanceof gnu.expr.ClassExp
            if (r5 != 0) goto L_0x0068
            gnu.expr.Expression r5 = r9.func
            r3 = r5
            gnu.expr.LambdaExp r3 = (gnu.expr.LambdaExp) r3
            r8.visitLambdaExp((gnu.expr.LambdaExp) r3, (boolean) r1)
            r3.setCanCall(r2)
            goto L_0x0086
        L_0x0068:
            gnu.expr.Expression r1 = r9.func
            boolean r1 = r1 instanceof gnu.expr.QuoteExp
            if (r1 == 0) goto L_0x007c
            gnu.expr.Expression r1 = r9.func
            gnu.expr.QuoteExp r1 = (gnu.expr.QuoteExp) r1
            java.lang.Object r1 = r1.getValue()
            gnu.kawa.functions.AppendValues r2 = gnu.kawa.functions.AppendValues.appendValues
            if (r1 != r2) goto L_0x007c
            r4 = 1
            goto L_0x0086
        L_0x007c:
            gnu.expr.Expression r1 = r9.func
            gnu.expr.Expression r2 = r9.func
            gnu.expr.Expression r1 = r8.visitExpression((gnu.expr.Expression) r1, (gnu.expr.Expression) r2)
            r9.func = r1
        L_0x0086:
            if (r3 == 0) goto L_0x00bb
            gnu.expr.Expression r1 = r3.returnContinuation
            if (r1 != r10) goto L_0x008d
            goto L_0x00bb
        L_0x008d:
            gnu.expr.LambdaExp r1 = r8.currentLambda
            if (r3 != r1) goto L_0x0094
            if (r0 == 0) goto L_0x0094
            goto L_0x00bb
        L_0x0094:
            if (r0 == 0) goto L_0x00a9
            java.util.Set<gnu.expr.LambdaExp> r1 = r3.tailCallers
            if (r1 != 0) goto L_0x00a1
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            r3.tailCallers = r1
        L_0x00a1:
            java.util.Set<gnu.expr.LambdaExp> r1 = r3.tailCallers
            gnu.expr.LambdaExp r2 = r8.currentLambda
            r1.add(r2)
            goto L_0x00bb
        L_0x00a9:
            gnu.expr.Expression r1 = r3.returnContinuation
            if (r1 != 0) goto L_0x00b4
            r3.returnContinuation = r10
            gnu.expr.LambdaExp r1 = r8.currentLambda
            r3.inlineHome = r1
            goto L_0x00bb
        L_0x00b4:
            gnu.expr.ApplyExp r1 = gnu.expr.LambdaExp.unknownContinuation
            r3.returnContinuation = r1
            r1 = 0
            r3.inlineHome = r1
        L_0x00bb:
            gnu.expr.Expression[] r1 = r9.args
            gnu.expr.Expression[] r1 = r8.visitExps(r1)
            r9.args = r1
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.FindTailCalls.visitApplyExp(gnu.expr.ApplyExp, gnu.expr.Expression):gnu.expr.Expression");
    }

    /* access modifiers changed from: protected */
    public Expression visitBlockExp(BlockExp exp, Expression returnContinuation) {
        exp.body = (Expression) exp.body.visit(this, returnContinuation);
        if (exp.exitBody != null) {
            exp.exitBody = (Expression) exp.exitBody.visit(this, exp.exitBody);
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitBeginExp(BeginExp exp, Expression returnContinuation) {
        int n = exp.length - 1;
        int i = 0;
        while (i <= n) {
            exp.exps[i] = (Expression) exp.exps[i].visit(this, i == n ? returnContinuation : exp.exps[i]);
            i++;
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitFluidLetExp(FluidLetExp exp, Expression returnContinuation) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            decl.setCanRead(true);
            if (decl.base != null) {
                decl.base.setCanRead(true);
            }
        }
        visitLetDecls(exp);
        exp.body = (Expression) exp.body.visit(this, exp.body);
        postVisitDecls(exp);
        return exp;
    }

    /* access modifiers changed from: package-private */
    public void visitLetDecls(LetExp exp) {
        Declaration decl = exp.firstDecl();
        int n = exp.inits.length;
        int i = 0;
        while (i < n) {
            Expression init = visitSetExp(decl, exp.inits[i]);
            if (init == QuoteExp.undefined_exp) {
                Expression value = decl.getValue();
                if ((value instanceof LambdaExp) || (value != init && (value instanceof QuoteExp))) {
                    init = value;
                }
            }
            exp.inits[i] = init;
            i++;
            decl = decl.nextDecl();
        }
    }

    /* access modifiers changed from: protected */
    public Expression visitLetExp(LetExp exp, Expression returnContinuation) {
        visitLetDecls(exp);
        exp.body = (Expression) exp.body.visit(this, returnContinuation);
        postVisitDecls(exp);
        return exp;
    }

    public void postVisitDecls(ScopeExp exp) {
        Declaration context;
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            Expression value = decl.getValue();
            if (value instanceof LambdaExp) {
                LambdaExp lexp = (LambdaExp) value;
                if (decl.getCanRead()) {
                    lexp.setCanRead(true);
                }
                if (decl.getCanCall()) {
                    lexp.setCanCall(true);
                }
            }
            if (decl.getFlag(1024) && (value instanceof ReferenceExp) && (context = ((ReferenceExp) value).contextDecl()) != null && context.isPrivate()) {
                context.setFlag(524288);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Expression visitIfExp(IfExp exp, Expression returnContinuation) {
        exp.test = (Expression) exp.test.visit(this, exp.test);
        exp.then_clause = (Expression) exp.then_clause.visit(this, returnContinuation);
        Expression else_clause = exp.else_clause;
        if (else_clause != null) {
            exp.else_clause = (Expression) else_clause.visit(this, returnContinuation);
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitLambdaExp(LambdaExp exp, Expression returnContinuation) {
        visitLambdaExp(exp, true);
        return exp;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public final void visitLambdaExp(LambdaExp exp, boolean canRead) {
        LambdaExp parent = this.currentLambda;
        this.currentLambda = exp;
        if (canRead) {
            exp.setCanRead(true);
        }
        try {
            if (exp.defaultArgs != null) {
                exp.defaultArgs = visitExps(exp.defaultArgs);
            }
            if (this.exitValue == null && exp.body != null) {
                exp.body = (Expression) exp.body.visit(this, exp.getInlineOnly() ? exp : exp.body);
            }
            this.currentLambda = parent;
            postVisitDecls(exp);
        } catch (Throwable th) {
            this.currentLambda = parent;
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public Expression visitClassExp(ClassExp exp, Expression returnContinuation) {
        LambdaExp parent = this.currentLambda;
        this.currentLambda = exp;
        try {
            for (LambdaExp child = exp.firstChild; child != null && this.exitValue == null; child = child.nextSibling) {
                visitLambdaExp(child, false);
            }
            return exp;
        } finally {
            this.currentLambda = parent;
        }
    }

    /* access modifiers changed from: protected */
    public Expression visitReferenceExp(ReferenceExp exp, Expression returnContinuation) {
        Declaration decl = Declaration.followAliases(exp.binding);
        if (decl != null) {
            Type type = decl.type;
            if (type != null && type.isVoid()) {
                return QuoteExp.voidExp;
            }
            decl.setCanRead(true);
        }
        Declaration ctx = exp.contextDecl();
        if (ctx != null) {
            ctx.setCanRead(true);
        }
        return exp;
    }

    /* access modifiers changed from: package-private */
    public final Expression visitSetExp(Declaration decl, Expression value) {
        if (decl == null || decl.getValue() != value || !(value instanceof LambdaExp) || (value instanceof ClassExp) || decl.isPublic()) {
            return (Expression) value.visit(this, value);
        }
        LambdaExp lexp = (LambdaExp) value;
        visitLambdaExp(lexp, false);
        return lexp;
    }

    /* access modifiers changed from: protected */
    public Expression visitSetExp(SetExp exp, Expression returnContinuation) {
        Declaration decl = exp.binding;
        if (decl != null && decl.isAlias()) {
            if (exp.isDefining()) {
                exp.new_value = (Expression) exp.new_value.visit(this, exp.new_value);
                return exp;
            }
            decl = Declaration.followAliases(decl);
        }
        Declaration ctx = exp.contextDecl();
        if (ctx != null) {
            ctx.setCanRead(true);
        }
        Expression value = visitSetExp(decl, exp.new_value);
        if (decl != null && (decl.context instanceof LetExp) && value == decl.getValue() && ((value instanceof LambdaExp) || (value instanceof QuoteExp))) {
            return QuoteExp.voidExp;
        }
        exp.new_value = value;
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitTryExp(TryExp exp, Expression returnContinuation) {
        exp.try_clause = (Expression) exp.try_clause.visit(this, exp.finally_clause == null ? returnContinuation : exp.try_clause);
        CatchClause catch_clause = exp.catch_clauses;
        while (this.exitValue == null && catch_clause != null) {
            catch_clause.body = (Expression) catch_clause.body.visit(this, exp.finally_clause == null ? returnContinuation : catch_clause.body);
            catch_clause = catch_clause.getNext();
        }
        Expression finally_clause = exp.finally_clause;
        if (finally_clause != null) {
            exp.finally_clause = (Expression) finally_clause.visit(this, finally_clause);
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitSynchronizedExp(SynchronizedExp exp, Expression returnContinuation) {
        exp.object = (Expression) exp.object.visit(this, exp.object);
        exp.body = (Expression) exp.body.visit(this, exp.body);
        return exp;
    }
}
