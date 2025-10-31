package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.mapping.Procedure1;
import gnu.text.Printable;
import gnu.text.ReportFormat;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SyntaxRules extends Procedure1 implements Printable, Externalizable {
    Object[] literal_identifiers;
    int maxVars = 0;
    SyntaxRule[] rules;

    public SyntaxRules() {
    }

    public SyntaxRules(Object[] literal_identifiers2, SyntaxRule[] rules2, int maxVars2) {
        this.literal_identifiers = literal_identifiers2;
        this.rules = rules2;
        this.maxVars = maxVars2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v0, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v1, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v2, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v3, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v4, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v25, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v39, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v42, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: kawa.lang.SyntaxForm} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SyntaxRules(java.lang.Object[] r26, java.lang.Object r27, kawa.lang.Translator r28) {
        /*
            r25 = this;
            r1 = r25
            r8 = r26
            r9 = r28
            r25.<init>()
            r0 = 0
            r1.maxVars = r0
            r1.literal_identifiers = r8
            int r2 = kawa.lang.Translator.listLength(r27)
            if (r2 >= 0) goto L_0x001c
            r2 = 0
            java.lang.String r3 = "missing or invalid syntax-rules"
            r9.syntaxError(r3)
            r10 = r2
            goto L_0x001d
        L_0x001c:
            r10 = r2
        L_0x001d:
            kawa.lang.SyntaxRule[] r2 = new kawa.lang.SyntaxRule[r10]
            r1.rules = r2
            r2 = 0
            r3 = 0
            r11 = r3
            r3 = r2
            r2 = r27
        L_0x0027:
            if (r11 >= r10) goto L_0x01dd
            r12 = r2
            r13 = r3
        L_0x002b:
            boolean r2 = r12 instanceof kawa.lang.SyntaxForm
            if (r2 == 0) goto L_0x0037
            r13 = r12
            kawa.lang.SyntaxForm r13 = (kawa.lang.SyntaxForm) r13
            java.lang.Object r12 = r13.getDatum()
            goto L_0x002b
        L_0x0037:
            r14 = r12
            gnu.lists.Pair r14 = (gnu.lists.Pair) r14
            r2 = r13
            java.lang.Object r3 = r14.getCar()
            r15 = r2
        L_0x0040:
            boolean r2 = r3 instanceof kawa.lang.SyntaxForm
            if (r2 == 0) goto L_0x004c
            r15 = r3
            kawa.lang.SyntaxForm r15 = (kawa.lang.SyntaxForm) r15
            java.lang.Object r3 = r15.getDatum()
            goto L_0x0040
        L_0x004c:
            boolean r2 = r3 instanceof gnu.lists.Pair
            java.lang.String r4 = "'th syntax rule"
            if (r2 != 0) goto L_0x006d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "missing pattern in "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r0 = r0.append(r11)
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            r9.syntaxError(r0)
            return
        L_0x006d:
            r2 = r15
            r5 = r3
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r6 = r5.getCar()
            java.lang.String r7 = r28.getFileName()
            r16 = r10
            int r10 = r28.getLineNumber()
            r27 = r12
            int r12 = r28.getColumnNumber()
            r17 = r15
            r9.setLine((java.lang.Object) r5)     // Catch:{ all -> 0x01d5 }
            java.lang.Object r18 = r5.getCdr()     // Catch:{ all -> 0x01d5 }
            r3 = r18
            r19 = r17
        L_0x0092:
            boolean r0 = r3 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x01ce }
            if (r0 == 0) goto L_0x00a6
            r0 = r3
            kawa.lang.SyntaxForm r0 = (kawa.lang.SyntaxForm) r0     // Catch:{ all -> 0x00a2 }
            r19 = r0
            java.lang.Object r0 = r19.getDatum()     // Catch:{ all -> 0x00a2 }
            r3 = r0
            r0 = 0
            goto L_0x0092
        L_0x00a2:
            r0 = move-exception
            r8 = r7
            goto L_0x01d9
        L_0x00a6:
            boolean r0 = r3 instanceof gnu.lists.Pair     // Catch:{ all -> 0x01ce }
            if (r0 != 0) goto L_0x00d6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d0 }
            r0.<init>()     // Catch:{ all -> 0x00d0 }
            r18 = r2
            java.lang.String r2 = "missing template in "
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x00ca }
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ all -> 0x00ca }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x00ca }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00ca }
            r9.syntaxError(r0)     // Catch:{ all -> 0x00ca }
            r9.setLine(r7, r10, r12)
            return
        L_0x00ca:
            r0 = move-exception
            r8 = r7
            r2 = r18
            goto L_0x01d9
        L_0x00d0:
            r0 = move-exception
            r18 = r2
            r8 = r7
            goto L_0x01d9
        L_0x00d6:
            r18 = r2
            r0 = r3
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x01c7 }
            r20 = r0
            java.lang.Object r0 = r20.getCdr()     // Catch:{ all -> 0x01be }
            gnu.lists.LList r2 = gnu.lists.LList.Empty     // Catch:{ all -> 0x01be }
            if (r0 == r2) goto L_0x010b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0103 }
            r0.<init>()     // Catch:{ all -> 0x0103 }
            java.lang.String r2 = "junk after "
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0103 }
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ all -> 0x0103 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x0103 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0103 }
            r9.syntaxError(r0)     // Catch:{ all -> 0x0103 }
            r9.setLine(r7, r10, r12)
            return
        L_0x0103:
            r0 = move-exception
            r8 = r7
            r2 = r18
            r5 = r20
            goto L_0x01d9
        L_0x010b:
            java.lang.Object r0 = r20.getCar()     // Catch:{ all -> 0x01be }
            kawa.lang.PatternScope r2 = kawa.lang.PatternScope.push(r28)     // Catch:{ all -> 0x01be }
            r5 = r2
            r9.push((gnu.expr.ScopeExp) r5)     // Catch:{ all -> 0x01be }
        L_0x0117:
            boolean r2 = r6 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x01be }
            if (r2 == 0) goto L_0x0126
            r2 = r6
            kawa.lang.SyntaxForm r2 = (kawa.lang.SyntaxForm) r2     // Catch:{ all -> 0x0103 }
            r18 = r2
            java.lang.Object r2 = r18.getDatum()     // Catch:{ all -> 0x0103 }
            r6 = r2
            goto L_0x0117
        L_0x0126:
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ all -> 0x01be }
            r2.<init>()     // Catch:{ all -> 0x01be }
            boolean r4 = r6 instanceof gnu.lists.Pair     // Catch:{ all -> 0x01be }
            if (r4 == 0) goto L_0x01a4
            r4 = r6
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ all -> 0x01be }
            java.lang.Object r4 = r4.getCar()     // Catch:{ all -> 0x01be }
            r17 = 0
            r8[r17] = r4     // Catch:{ all -> 0x01be }
            r4 = r6
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ all -> 0x01be }
            r21 = r3
            r3 = 12
            r2.append(r3)     // Catch:{ all -> 0x019b }
            r3 = 24
            r2.append(r3)     // Catch:{ all -> 0x019b }
            java.lang.Object r3 = r4.getCdr()     // Catch:{ all -> 0x019b }
            r4 = r3
            kawa.lang.SyntaxPattern r22 = new kawa.lang.SyntaxPattern     // Catch:{ all -> 0x0191 }
            r23 = r2
            r2 = r22
            r3 = r23
            r24 = r5
            r5 = r18
            r6 = r26
            r8 = r7
            r7 = r28
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0188 }
            r2 = r22
            kawa.lang.SyntaxRule[] r3 = r1.rules     // Catch:{ all -> 0x0188 }
            kawa.lang.SyntaxRule r5 = new kawa.lang.SyntaxRule     // Catch:{ all -> 0x0188 }
            r7 = r19
            r5.<init>(r2, r0, r7, r9)     // Catch:{ all -> 0x0188 }
            r3[r11] = r5     // Catch:{ all -> 0x0188 }
            kawa.lang.PatternScope.pop(r28)     // Catch:{ all -> 0x0188 }
            r28.pop()     // Catch:{ all -> 0x0188 }
            r9.setLine(r8, r10, r12)
            int r11 = r11 + 1
            java.lang.Object r2 = r14.getCdr()
            r8 = r26
            r3 = r13
            r10 = r16
            r0 = 0
            goto L_0x0027
        L_0x0188:
            r0 = move-exception
            r6 = r4
            r2 = r18
            r5 = r20
            r3 = r21
            goto L_0x01d9
        L_0x0191:
            r0 = move-exception
            r8 = r7
            r6 = r4
            r2 = r18
            r5 = r20
            r3 = r21
            goto L_0x01d9
        L_0x019b:
            r0 = move-exception
            r8 = r7
            r2 = r18
            r5 = r20
            r3 = r21
            goto L_0x01d9
        L_0x01a4:
            r23 = r2
            r21 = r3
            r24 = r5
            r8 = r7
            r7 = r19
            java.lang.String r2 = "pattern does not start with name"
            r9.syntaxError(r2)     // Catch:{ all -> 0x01b6 }
            r9.setLine(r8, r10, r12)
            return
        L_0x01b6:
            r0 = move-exception
            r2 = r18
            r5 = r20
            r3 = r21
            goto L_0x01d9
        L_0x01be:
            r0 = move-exception
            r21 = r3
            r8 = r7
            r2 = r18
            r5 = r20
            goto L_0x01d9
        L_0x01c7:
            r0 = move-exception
            r21 = r3
            r8 = r7
            r2 = r18
            goto L_0x01d9
        L_0x01ce:
            r0 = move-exception
            r18 = r2
            r21 = r3
            r8 = r7
            goto L_0x01d9
        L_0x01d5:
            r0 = move-exception
            r18 = r2
            r8 = r7
        L_0x01d9:
            r9.setLine(r8, r10, r12)
            throw r0
        L_0x01dd:
            r16 = r10
            kawa.lang.SyntaxRule[] r0 = r1.rules
            int r0 = r0.length
        L_0x01e2:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x01f7
            kawa.lang.SyntaxRule[] r4 = r1.rules
            r4 = r4[r0]
            java.lang.String r4 = r4.patternNesting
            int r4 = r4.length()
            int r5 = r1.maxVars
            if (r4 <= r5) goto L_0x01f6
            r1.maxVars = r4
        L_0x01f6:
            goto L_0x01e2
        L_0x01f7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxRules.<init>(java.lang.Object[], java.lang.Object, kawa.lang.Translator):void");
    }

    public Object apply1(Object arg) {
        if (!(arg instanceof SyntaxForm)) {
            return expand(arg, (Translator) Compilation.getCurrent());
        }
        SyntaxForm sf = (SyntaxForm) arg;
        Translator tr = (Translator) Compilation.getCurrent();
        ScopeExp save_scope = tr.currentScope();
        tr.setCurrentScope(sf.getScope());
        try {
            return expand(sf, tr);
        } finally {
            tr.setCurrentScope(save_scope);
        }
    }

    public Object expand(Object obj, Translator tr) {
        Object[] vars = new Object[this.maxVars];
        Macro macro = (Macro) tr.getCurrentSyntax();
        for (SyntaxRule rule : this.rules) {
            if (rule == null) {
                return new ErrorExp("error defining " + macro);
            }
            if (rule.pattern.match(obj, vars, 0)) {
                return rule.execute(vars, tr, TemplateScope.make(tr));
            }
        }
        return tr.syntaxError("no matching syntax-rule for " + this.literal_identifiers[0]);
    }

    public void print(Consumer out) {
        out.write("#<macro ");
        ReportFormat.print(this.literal_identifiers[0], out);
        out.write(62);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.literal_identifiers);
        out.writeObject(this.rules);
        out.writeInt(this.maxVars);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literal_identifiers = (Object[]) in.readObject();
        this.rules = (SyntaxRule[]) in.readObject();
        this.maxVars = in.readInt();
    }
}
