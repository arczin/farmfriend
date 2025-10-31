package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.util.Vector;

public class SyntaxPattern extends Pattern implements Externalizable {
    static final int MATCH_ANY = 3;
    static final int MATCH_ANY_CAR = 7;
    static final int MATCH_EQUALS = 2;
    static final int MATCH_IGNORE = 24;
    static final int MATCH_LENGTH = 6;
    static final int MATCH_LREPEAT = 5;
    static final int MATCH_MISC = 0;
    static final int MATCH_NIL = 8;
    static final int MATCH_PAIR = 4;
    static final int MATCH_VECTOR = 16;
    static final int MATCH_WIDE = 1;
    Object[] literals;
    String program;
    int varCount;

    public int varCount() {
        return this.varCount;
    }

    public boolean match(Object obj, Object[] vars, int start_vars) {
        return match(obj, vars, start_vars, 0, (SyntaxForm) null);
    }

    public SyntaxPattern(String program2, Object[] literals2, int varCount2) {
        this.program = program2;
        this.literals = literals2;
        this.varCount = varCount2;
    }

    public SyntaxPattern(Object pattern, Object[] literal_identifiers, Translator tr) {
        this(new StringBuffer(), pattern, (SyntaxForm) null, literal_identifiers, tr);
    }

    SyntaxPattern(StringBuffer programbuf, Object pattern, SyntaxForm syntax, Object[] literal_identifiers, Translator tr) {
        Vector literalsbuf = new Vector();
        translate(pattern, programbuf, literal_identifiers, 0, literalsbuf, (SyntaxForm) null, 0, tr);
        this.program = programbuf.toString();
        this.literals = new Object[literalsbuf.size()];
        literalsbuf.copyInto(this.literals);
        this.varCount = tr.patternScope.pattern_names.size();
    }

    public void disassemble() {
        disassemble(OutPort.errDefault(), (Translator) Compilation.getCurrent(), 0, this.program.length());
    }

    public void disassemble(PrintWriter ps, Translator tr) {
        disassemble(ps, tr, 0, this.program.length());
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disassemble(java.io.PrintWriter r11, kawa.lang.Translator r12, int r13, int r14) {
        /*
            r10 = this;
            r0 = 0
            if (r12 == 0) goto L_0x000b
            kawa.lang.PatternScope r1 = r12.patternScope
            if (r1 == 0) goto L_0x000b
            kawa.lang.PatternScope r1 = r12.patternScope
            java.util.Vector r0 = r1.pattern_names
        L_0x000b:
            r1 = 0
            r2 = r13
        L_0x000d:
            if (r2 >= r14) goto L_0x01e4
            java.lang.String r3 = r10.program
            char r3 = r3.charAt(r2)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = " "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r6 = ": "
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            r11.print(r4)
            int r2 = r2 + 1
            r4 = r3 & 7
            int r6 = r1 << 13
            int r7 = r3 >> 3
            r1 = r6 | r7
            r6 = 3
            java.lang.String r7 = "]"
            switch(r4) {
                case 0: goto L_0x017f;
                case 1: goto L_0x0167;
                case 2: goto L_0x0136;
                case 3: goto L_0x0101;
                case 4: goto L_0x00e5;
                case 5: goto L_0x0074;
                case 6: goto L_0x0047;
                case 7: goto L_0x0101;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x01c1
        L_0x0047:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = " - LENGTH "
            java.lang.StringBuilder r5 = r5.append(r6)
            int r6 = r1 >> 1
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = " pairs. "
            java.lang.StringBuilder r5 = r5.append(r6)
            r6 = r1 & 1
            if (r6 != 0) goto L_0x0065
            java.lang.String r6 = "pure list"
            goto L_0x0067
        L_0x0065:
            java.lang.String r6 = "impure list"
        L_0x0067:
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r11.println(r5)
            goto L_0x01e1
        L_0x0074:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = " - LREPEAT["
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r1)
            java.lang.StringBuilder r7 = r8.append(r7)
            java.lang.String r7 = r7.toString()
            r11.println(r7)
            int r7 = r2 + r1
            r10.disassemble(r11, r12, r2, r7)
            int r2 = r2 + r1
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r5)
            java.lang.StringBuilder r7 = r7.append(r2)
            java.lang.String r8 = ": - repeat first var:"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r10.program
            int r9 = r2 + 1
            char r2 = r8.charAt(r2)
            int r2 = r2 >> r6
            java.lang.StringBuilder r2 = r7.append(r2)
            java.lang.String r2 = r2.toString()
            r11.println(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.StringBuilder r2 = r2.append(r9)
            java.lang.String r5 = ": - repeast nested vars:"
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r5 = r10.program
            int r7 = r9 + 1
            char r5 = r5.charAt(r9)
            int r5 = r5 >> r6
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r2 = r2.toString()
            r11.println(r2)
            r2 = r7
            goto L_0x01e1
        L_0x00e5:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = " - PAIR["
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            r11.println(r5)
            goto L_0x01e1
        L_0x0101:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            if (r4 != r6) goto L_0x010b
            java.lang.String r6 = " - ANY["
            goto L_0x010d
        L_0x010b:
            java.lang.String r6 = " - ANY_CAR["
        L_0x010d:
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            r11.print(r5)
            if (r0 == 0) goto L_0x0131
            if (r1 < 0) goto L_0x0131
            int r5 = r0.size()
            if (r1 >= r5) goto L_0x0131
            java.lang.Object r5 = r0.elementAt(r1)
            r11.print(r5)
        L_0x0131:
            r11.println()
            goto L_0x01e1
        L_0x0136:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = " - EQUALS["
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            r11.print(r5)
            java.lang.Object[] r5 = r10.literals
            if (r5 == 0) goto L_0x0162
            if (r1 < 0) goto L_0x0162
            java.lang.Object[] r5 = r10.literals
            int r5 = r5.length
            if (r1 >= r5) goto L_0x0162
            java.lang.Object[] r5 = r10.literals
            r5 = r5[r1]
            r11.print(r5)
        L_0x0162:
            r11.println()
            goto L_0x01e1
        L_0x0167:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = " - WIDE "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r5 = r5.toString()
            r11.println(r5)
            goto L_0x000d
        L_0x017f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "[misc ch:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r6 = " n:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r6 = 8
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            r11.print(r5)
            if (r3 != r6) goto L_0x01ad
            java.lang.String r5 = " - NIL"
            r11.println(r5)
            goto L_0x01e1
        L_0x01ad:
            r5 = 16
            if (r3 != r5) goto L_0x01b7
            java.lang.String r5 = " - VECTOR"
            r11.println(r5)
            goto L_0x01e1
        L_0x01b7:
            r5 = 24
            if (r3 != r5) goto L_0x01c1
            java.lang.String r5 = " - IGNORE"
            r11.println(r5)
            goto L_0x01e1
        L_0x01c1:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = " - "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r4)
            r6 = 47
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r5 = r5.toString()
            r11.println(r5)
        L_0x01e1:
            r1 = 0
            goto L_0x000d
        L_0x01e4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxPattern.disassemble(java.io.PrintWriter, kawa.lang.Translator, int, int):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x00fd A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00df A[Catch:{ all -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00e1 A[Catch:{ all -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00e8 A[Catch:{ all -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0106  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void translate(java.lang.Object r28, java.lang.StringBuffer r29, java.lang.Object[] r30, int r31, java.util.Vector r32, kawa.lang.SyntaxForm r33, char r34, kawa.lang.Translator r35) {
        /*
            r27 = this;
            r10 = r29
            r11 = r30
            r12 = r32
            r13 = r35
            kawa.lang.PatternScope r14 = r13.patternScope
            java.util.Vector r15 = r14.pattern_names
            r9 = r28
            r1 = r33
            r2 = r34
        L_0x0012:
            boolean r0 = r9 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x001e
            r1 = r9
            kawa.lang.SyntaxForm r1 = (kawa.lang.SyntaxForm) r1
            java.lang.Object r9 = r1.getDatum()
            goto L_0x0012
        L_0x001e:
            boolean r0 = r9 instanceof gnu.lists.Pair
            r16 = 3
            r17 = 1
            if (r0 == 0) goto L_0x0168
            java.lang.Object r6 = r13.pushPositionOf(r9)
            int r0 = r29.length()     // Catch:{ all -> 0x015e }
            r5 = 4
            r10.append(r5)     // Catch:{ all -> 0x015e }
            r7 = r9
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7     // Catch:{ all -> 0x015e }
            r18 = r7
            r7 = r1
            java.lang.Object r19 = r18.getCdr()     // Catch:{ all -> 0x015e }
            r3 = r19
        L_0x003e:
            boolean r5 = r3 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x015e }
            if (r5 == 0) goto L_0x0055
            r5 = r3
            kawa.lang.SyntaxForm r5 = (kawa.lang.SyntaxForm) r5     // Catch:{ all -> 0x004d }
            r1 = r5
            java.lang.Object r5 = r1.getDatum()     // Catch:{ all -> 0x004d }
            r3 = r5
            r5 = 4
            goto L_0x003e
        L_0x004d:
            r0 = move-exception
            r26 = r9
            r23 = r14
            r14 = r6
            goto L_0x0164
        L_0x0055:
            r5 = 0
            boolean r8 = r3 instanceof gnu.lists.Pair     // Catch:{ all -> 0x015e }
            if (r8 == 0) goto L_0x0087
            r8 = r3
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8     // Catch:{ all -> 0x004d }
            java.lang.Object r8 = r8.getCar()     // Catch:{ all -> 0x004d }
            java.lang.String r4 = "..."
            boolean r4 = r13.matches(r8, r4)     // Catch:{ all -> 0x004d }
            if (r4 == 0) goto L_0x0087
            r5 = 1
            r4 = r3
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ all -> 0x004d }
            java.lang.Object r4 = r4.getCdr()     // Catch:{ all -> 0x004d }
            r3 = r4
        L_0x0072:
            boolean r4 = r3 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x004d }
            if (r4 == 0) goto L_0x0081
            r4 = r3
            kawa.lang.SyntaxForm r4 = (kawa.lang.SyntaxForm) r4     // Catch:{ all -> 0x004d }
            r1 = r4
            java.lang.Object r4 = r1.getDatum()     // Catch:{ all -> 0x004d }
            r3 = r4
            goto L_0x0072
        L_0x0081:
            r20 = r1
            r8 = r3
            r21 = r5
            goto L_0x008c
        L_0x0087:
            r20 = r1
            r8 = r3
            r21 = r5
        L_0x008c:
            int r1 = r15.size()     // Catch:{ all -> 0x0155 }
            r22 = r1
            r1 = 80
            if (r2 != r1) goto L_0x0099
            r1 = 0
            r5 = r1
            goto L_0x009a
        L_0x0099:
            r5 = r2
        L_0x009a:
            java.lang.Object r2 = r18.getCar()     // Catch:{ all -> 0x0148 }
            if (r21 == 0) goto L_0x00a5
            int r1 = r31 + 1
            r23 = r1
            goto L_0x00a7
        L_0x00a5:
            r23 = r31
        L_0x00a7:
            r1 = 86
            if (r5 != r1) goto L_0x00ae
            r19 = 0
            goto L_0x00b0
        L_0x00ae:
            r19 = 80
        L_0x00b0:
            r1 = r27
            r3 = r29
            r4 = r30
            r25 = r5
            r24 = 4
            r5 = r23
            r23 = r14
            r14 = r6
            r6 = r32
            r28 = r8
            r12 = 8
            r8 = r19
            r26 = r9
            r9 = r35
            r1.translate(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0142 }
            int r1 = r15.size()     // Catch:{ all -> 0x0142 }
            int r1 = r1 - r22
            int r2 = r29.length()     // Catch:{ all -> 0x0142 }
            int r2 = r2 - r0
            int r2 = r2 + -1
            int r2 = r2 << 3
            if (r21 == 0) goto L_0x00e1
            r5 = 5
            goto L_0x00e2
        L_0x00e1:
            r5 = 4
        L_0x00e2:
            r2 = r2 | r5
            r3 = 65535(0xffff, float:9.1834E-41)
            if (r2 <= r3) goto L_0x00f1
            int r3 = r2 >> 13
            int r3 = r3 + 1
            int r3 = insertInt(r0, r10, r3)     // Catch:{ all -> 0x0142 }
            int r0 = r0 + r3
        L_0x00f1:
            char r3 = (char) r2     // Catch:{ all -> 0x0142 }
            r10.setCharAt(r0, r3)     // Catch:{ all -> 0x0142 }
            int r3 = kawa.lang.Translator.listLength(r28)     // Catch:{ all -> 0x0142 }
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r3 != r4) goto L_0x0106
            java.lang.String r4 = "cyclic pattern list"
            r13.syntaxError(r4)     // Catch:{ all -> 0x0142 }
            r13.popPositionOf(r14)
            return
        L_0x0106:
            if (r21 == 0) goto L_0x0132
            int r4 = r22 << 3
            addInt(r10, r4)     // Catch:{ all -> 0x0142 }
            int r4 = r1 << 3
            addInt(r10, r4)     // Catch:{ all -> 0x0142 }
            gnu.lists.LList r4 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0142 }
            r5 = r28
            if (r5 != r4) goto L_0x011f
            r10.append(r12)     // Catch:{ all -> 0x0142 }
            r13.popPositionOf(r14)
            return
        L_0x011f:
            if (r3 < 0) goto L_0x0124
            int r4 = r3 << 1
            goto L_0x0129
        L_0x0124:
            int r4 = -r3
            int r4 = r4 << 1
            int r4 = r4 + -1
        L_0x0129:
            r3 = r4
            int r4 = r3 << 3
            r4 = r4 | 6
            addInt(r10, r4)     // Catch:{ all -> 0x0142 }
            goto L_0x0134
        L_0x0132:
            r5 = r28
        L_0x0134:
            r9 = r5
            r13.popPositionOf(r14)
            r12 = r32
            r1 = r20
            r14 = r23
            r2 = r25
            goto L_0x0012
        L_0x0142:
            r0 = move-exception
            r1 = r20
            r2 = r25
            goto L_0x0164
        L_0x0148:
            r0 = move-exception
            r25 = r5
            r26 = r9
            r23 = r14
            r14 = r6
            r1 = r20
            r2 = r25
            goto L_0x0164
        L_0x0155:
            r0 = move-exception
            r26 = r9
            r23 = r14
            r14 = r6
            r1 = r20
            goto L_0x0164
        L_0x015e:
            r0 = move-exception
            r26 = r9
            r23 = r14
            r14 = r6
        L_0x0164:
            r13.popPositionOf(r14)
            throw r0
        L_0x0168:
            r26 = r9
            r23 = r14
            r12 = 8
            boolean r0 = r9 instanceof gnu.mapping.Symbol
            if (r0 == 0) goto L_0x0214
            int r0 = r11.length
        L_0x0173:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x01c1
            gnu.expr.ScopeExp r3 = r35.currentScope()
            if (r1 != 0) goto L_0x017f
            r4 = r3
            goto L_0x0183
        L_0x017f:
            kawa.lang.TemplateScope r4 = r1.getScope()
        L_0x0183:
            r5 = r11[r0]
            boolean r6 = r5 instanceof kawa.lang.SyntaxForm
            if (r6 == 0) goto L_0x0195
            r6 = r5
            kawa.lang.SyntaxForm r6 = (kawa.lang.SyntaxForm) r6
            java.lang.Object r5 = r6.getDatum()
            kawa.lang.TemplateScope r6 = r6.getScope()
            goto L_0x01a1
        L_0x0195:
            kawa.lang.Macro r6 = r13.currentMacroDefinition
            if (r6 == 0) goto L_0x01a0
            kawa.lang.Macro r6 = r13.currentMacroDefinition
            gnu.expr.ScopeExp r6 = r6.getCapturedScope()
            goto L_0x01a1
        L_0x01a0:
            r6 = r3
        L_0x01a1:
            boolean r7 = literalIdentifierEq(r9, r4, r5, r6)
            if (r7 == 0) goto L_0x01be
            r7 = r32
            int r8 = kawa.lang.SyntaxTemplate.indexOf(r7, r9)
            if (r8 >= 0) goto L_0x01b6
            int r8 = r32.size()
            r7.addElement(r9)
        L_0x01b6:
            int r12 = r8 << 3
            r12 = r12 | 2
            addInt(r10, r12)
            return
        L_0x01be:
            r7 = r32
            goto L_0x0173
        L_0x01c1:
            r7 = r32
            boolean r0 = r15.contains(r9)
            if (r0 == 0) goto L_0x01df
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "duplicated pattern variable "
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r0 = r0.toString()
            r13.syntaxError(r0)
        L_0x01df:
            int r0 = r15.size()
            r15.addElement(r9)
            r3 = 80
            if (r2 != r3) goto L_0x01ec
            r3 = 1
            goto L_0x01ed
        L_0x01ec:
            r3 = 0
        L_0x01ed:
            int r4 = r31 << 1
            if (r3 == 0) goto L_0x01f2
            goto L_0x01f4
        L_0x01f2:
            r17 = 0
        L_0x01f4:
            int r4 = r4 + r17
            r5 = r23
            java.lang.StringBuffer r6 = r5.patternNesting
            char r8 = (char) r4
            r6.append(r8)
            gnu.expr.Declaration r6 = r5.addDeclaration((java.lang.Object) r9)
            r6.setLocation(r13)
            r13.push((gnu.expr.Declaration) r6)
            int r8 = r0 << 3
            if (r3 == 0) goto L_0x020e
            r16 = 7
        L_0x020e:
            r8 = r8 | r16
            addInt(r10, r8)
            return
        L_0x0214:
            r7 = r32
            r5 = r23
            r0 = 8
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            if (r9 != r3) goto L_0x0222
            r10.append(r0)
            return
        L_0x0222:
            boolean r0 = r9 instanceof gnu.lists.FVector
            if (r0 == 0) goto L_0x0238
            r0 = 16
            r10.append(r0)
            r0 = r9
            gnu.lists.FVector r0 = (gnu.lists.FVector) r0
            gnu.lists.LList r9 = gnu.lists.LList.makeList(r0)
            r2 = 86
            r14 = r5
            r12 = r7
            goto L_0x0012
        L_0x0238:
            int r0 = kawa.lang.SyntaxTemplate.indexOf(r7, r9)
            if (r0 >= 0) goto L_0x0245
            int r0 = r32.size()
            r7.addElement(r9)
        L_0x0245:
            int r3 = r0 << 3
            r3 = r3 | 2
            addInt(r10, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxPattern.translate(java.lang.Object, java.lang.StringBuffer, java.lang.Object[], int, java.util.Vector, kawa.lang.SyntaxForm, char, kawa.lang.Translator):void");
    }

    private static void addInt(StringBuffer sbuf, int val) {
        if (val > 65535) {
            addInt(sbuf, (val << 13) + 1);
        }
        sbuf.append((char) val);
    }

    private static int insertInt(int offset, StringBuffer sbuf, int val) {
        if (val > 65535) {
            offset += insertInt(offset, sbuf, (val << 13) + 1);
        }
        sbuf.insert(offset, (char) val);
        return offset + 1;
    }

    /* access modifiers changed from: package-private */
    public boolean match_car(Pair p, Object[] vars, int start_vars, int pc, SyntaxForm syntax) {
        int pc_start = pc;
        int pc2 = pc + 1;
        char pc3 = this.program.charAt(pc);
        char ch = pc3;
        int value = pc3 >> 3;
        char ch2 = ch;
        int pc4 = pc2;
        while ((ch2 & 7) == 1) {
            char charAt = this.program.charAt(pc4);
            ch2 = charAt;
            value = (value << 13) | (charAt >> 3);
            pc4++;
        }
        if ((ch2 & 7) == 7) {
            if (syntax != null && !(p.getCar() instanceof SyntaxForm)) {
                p = Translator.makePair(p, SyntaxForms.fromDatum(p.getCar(), syntax), p.getCdr());
            }
            vars[start_vars + value] = p;
            return true;
        }
        return match(p.getCar(), vars, start_vars, pc_start, syntax);
    }

    public boolean match(Object obj, Object[] objArr, int i, int i2, SyntaxForm syntaxForm) {
        ScopeExp scopeExp;
        ScopeExp scopeExp2;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        boolean z3;
        Object obj2 = obj;
        int i5 = i2;
        SyntaxForm syntaxForm2 = syntaxForm;
        int i6 = 0;
        while (true) {
            boolean z4 = obj2 instanceof SyntaxForm;
            if (z4) {
                syntaxForm2 = (SyntaxForm) obj2;
                obj2 = syntaxForm2.getDatum();
            } else {
                int i7 = i5 + 1;
                char charAt = this.program.charAt(i5);
                int i8 = (i6 << 13) | (charAt >> 3);
                switch (charAt & 7) {
                    case 0:
                        if (charAt == 8) {
                            if (obj2 == LList.Empty) {
                                return true;
                            }
                            return false;
                        } else if (charAt == 16) {
                            if (!(obj2 instanceof FVector)) {
                                return false;
                            }
                            return match(LList.makeList((FVector) obj2), objArr, i, i7, syntaxForm2);
                        } else if (charAt == 24) {
                            return true;
                        } else {
                            throw new Error("unknwon pattern opcode");
                        }
                    case 1:
                        i5 = i7;
                        i6 = i8;
                        break;
                    case 2:
                        Object obj3 = this.literals[i8];
                        Translator translator = (Translator) Compilation.getCurrent();
                        if (obj3 instanceof SyntaxForm) {
                            SyntaxForm syntaxForm3 = (SyntaxForm) obj3;
                            Object datum = syntaxForm3.getDatum();
                            scopeExp = syntaxForm3.getScope();
                            obj3 = datum;
                        } else {
                            Syntax currentSyntax = translator.getCurrentSyntax();
                            scopeExp = currentSyntax instanceof Macro ? ((Macro) currentSyntax).getCapturedScope() : null;
                        }
                        if (z4) {
                            SyntaxForm syntaxForm4 = (SyntaxForm) obj2;
                            Object datum2 = syntaxForm4.getDatum();
                            scopeExp2 = syntaxForm4.getScope();
                            obj2 = datum2;
                        } else {
                            scopeExp2 = syntaxForm2 == null ? translator.currentScope() : syntaxForm2.getScope();
                        }
                        return literalIdentifierEq(obj3, scopeExp, obj2, scopeExp2);
                    case 3:
                        if (syntaxForm2 != null) {
                            obj2 = SyntaxForms.fromDatum(obj2, syntaxForm2);
                        }
                        objArr[i + i8] = obj2;
                        return true;
                    case 4:
                        if (obj2 instanceof Pair) {
                            Pair pair = (Pair) obj2;
                            if (match_car(pair, objArr, i, i7, syntaxForm2)) {
                                i5 = i7 + i8;
                                obj2 = pair.getCdr();
                                i6 = 0;
                                break;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    case 5:
                        int i9 = i8 + i7;
                        int i10 = i9 + 1;
                        char charAt2 = this.program.charAt(i9);
                        int i11 = charAt2 >> 3;
                        while ((charAt2 & 7) == 1) {
                            int i12 = i10 + 1;
                            char charAt3 = this.program.charAt(i10);
                            i11 = (charAt3 >> 3) | (i11 << 13);
                            charAt2 = charAt3;
                            i10 = i12;
                        }
                        int i13 = i11 + i;
                        int i14 = i10 + 1;
                        int charAt4 = this.program.charAt(i10) >> 3;
                        while ((charAt2 & 7) == 1) {
                            char charAt5 = this.program.charAt(i14);
                            charAt4 = (charAt4 << 13) | (charAt5 >> 3);
                            charAt2 = charAt5;
                            i14++;
                        }
                        int i15 = i14 + 1;
                        char charAt6 = this.program.charAt(i14);
                        if (charAt6 == 8) {
                            i4 = i15;
                            z = true;
                            i3 = 0;
                        } else {
                            int i16 = charAt6 >> 3;
                            while ((charAt6 & 7) == 1) {
                                int i17 = i16 << 13;
                                char charAt7 = this.program.charAt(i15);
                                i15++;
                                char c = charAt7;
                                i16 = i17 | (charAt7 >> 3);
                                charAt6 = c;
                            }
                            if ((i16 & 1) != 0) {
                                z3 = false;
                            } else {
                                z3 = true;
                            }
                            z = z3;
                            i3 = i16 >> 1;
                            i4 = i15;
                        }
                        int listLength = Translator.listLength(obj2);
                        if (listLength >= 0) {
                            z2 = true;
                        } else {
                            listLength = -1 - listLength;
                            z2 = false;
                        }
                        if (listLength < i3 || (z && !z2)) {
                            return false;
                        }
                        int i18 = listLength - i3;
                        Object[][] objArr2 = new Object[charAt4][];
                        for (int i19 = 0; i19 < charAt4; i19++) {
                            objArr2[i19] = new Object[i18];
                        }
                        int i20 = 0;
                        while (i20 < i18) {
                            while (obj2 instanceof SyntaxForm) {
                                syntaxForm2 = (SyntaxForm) obj2;
                                obj2 = syntaxForm2.getDatum();
                            }
                            Pair pair2 = (Pair) obj2;
                            int i21 = i20;
                            Object[][] objArr3 = objArr2;
                            int i22 = i18;
                            if (!match_car(pair2, objArr, i, i7, syntaxForm2)) {
                                return false;
                            }
                            obj2 = pair2.getCdr();
                            for (int i23 = 0; i23 < charAt4; i23++) {
                                objArr3[i23][i21] = objArr[i13 + i23];
                            }
                            i20 = i21 + 1;
                            objArr2 = objArr3;
                            i18 = i22;
                        }
                        Object[][] objArr4 = objArr2;
                        for (int i24 = 0; i24 < charAt4; i24++) {
                            objArr[i13 + i24] = objArr4[i24];
                        }
                        if (i3 != 0 || !z) {
                            i5 = i4;
                            i6 = 0;
                            break;
                        } else {
                            return true;
                        }
                        break;
                    case 6:
                        int i25 = i8 >> 1;
                        Object obj4 = obj2;
                        int i26 = 0;
                        while (true) {
                            if (obj4 instanceof SyntaxForm) {
                                obj4 = ((SyntaxForm) obj4).getDatum();
                            } else if (i26 == i25) {
                                if ((i8 & 1) == 0) {
                                    if (obj4 != LList.Empty) {
                                        break;
                                    }
                                    i5 = i7;
                                    i6 = 0;
                                    break;
                                } else {
                                    if (obj4 instanceof Pair) {
                                        break;
                                    }
                                    i5 = i7;
                                    i6 = 0;
                                }
                            } else if (!(obj4 instanceof Pair)) {
                                return false;
                            } else {
                                obj4 = ((Pair) obj4).getCdr();
                                i26++;
                            }
                        }
                    case 8:
                        if (obj2 == LList.Empty) {
                            return true;
                        }
                        return false;
                    default:
                        disassemble();
                        throw new Error("unrecognized pattern opcode @pc:" + i7);
                }
            }
        }
        return false;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.program);
        out.writeObject(this.literals);
        out.writeInt(this.varCount);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literals = (Object[]) in.readObject();
        this.program = (String) in.readObject();
        this.varCount = in.readInt();
    }

    public static Object[] allocVars(int varCount2, Object[] outer) {
        Object[] vars = new Object[varCount2];
        if (outer != null) {
            System.arraycopy(outer, 0, vars, 0, outer.length);
        }
        return vars;
    }

    public static boolean literalIdentifierEq(Object id1, ScopeExp sc1, Object id2, ScopeExp sc2) {
        if (id1 != id2 && (id1 == null || id2 == null || !id1.equals(id2))) {
            return false;
        }
        if (sc1 == sc2) {
            return true;
        }
        Declaration d1 = null;
        Declaration d2 = null;
        while (sc1 != null && !(sc1 instanceof ModuleExp)) {
            d1 = sc1.lookup(id1);
            if (d1 != null) {
                break;
            }
            sc1 = sc1.outer;
        }
        while (sc2 != null && !(sc2 instanceof ModuleExp)) {
            d2 = sc2.lookup(id2);
            if (d2 != null) {
                break;
            }
            sc2 = sc2.outer;
        }
        if (d1 == d2) {
            return true;
        }
        return false;
    }

    public static Object[] getLiteralsList(Object list, SyntaxForm syntax, Translator tr) {
        Object wrapped;
        Object savePos = tr.pushPositionOf(list);
        int count = Translator.listLength(list);
        if (count < 0) {
            tr.error('e', "missing or malformed literals list");
            count = 0;
        }
        Object[] literals2 = new Object[(count + 1)];
        for (int i = 1; i <= count; i++) {
            while (list instanceof SyntaxForm) {
                list = ((SyntaxForm) list).getDatum();
            }
            Pair pair = (Pair) list;
            tr.pushPositionOf(pair);
            Object literal = pair.getCar();
            if (literal instanceof SyntaxForm) {
                wrapped = literal;
                literal = ((SyntaxForm) literal).getDatum();
            } else {
                wrapped = literal;
            }
            if (!(literal instanceof Symbol)) {
                tr.error('e', "non-symbol '" + literal + "' in literals list");
            }
            literals2[i] = wrapped;
            list = pair.getCdr();
        }
        tr.popPositionOf(savePos);
        return literals2;
    }

    public void print(Consumer out) {
        out.write("#<syntax-pattern>");
    }
}
