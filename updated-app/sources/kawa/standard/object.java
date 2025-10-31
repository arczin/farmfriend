package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Keyword;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class object extends Syntax {
    public static final Keyword accessKeyword = Keyword.make("access");
    public static final Keyword allocationKeyword = Keyword.make("allocation");
    public static final Keyword classNameKeyword = Keyword.make("class-name");
    static final Symbol coloncolon = Namespace.EmptyNamespace.getSymbol("::");
    static final Keyword initKeyword = Keyword.make("init");
    static final Keyword init_formKeyword = Keyword.make("init-form");
    static final Keyword init_keywordKeyword = Keyword.make("init-keyword");
    static final Keyword init_valueKeyword = Keyword.make("init-value");
    static final Keyword initformKeyword = Keyword.make("initform");
    public static final Keyword interfaceKeyword = Keyword.make("interface");
    public static final object objectSyntax = new object(SchemeCompilation.lambda);
    public static final Keyword throwsKeyword = Keyword.make("throws");
    static final Keyword typeKeyword = Keyword.make("type");
    Lambda lambda;

    static {
        objectSyntax.setName("object");
    }

    public object(Lambda lambda2) {
        this.lambda = lambda2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: gnu.lists.Pair} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r4, kawa.lang.Translator r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r4.getCdr()
            boolean r0 = r0 instanceof gnu.lists.Pair
            if (r0 != 0) goto L_0x000f
            java.lang.String r0 = "missing superclass specification in object"
            gnu.expr.Expression r0 = r5.syntaxError(r0)
            return r0
        L_0x000f:
            java.lang.Object r0 = r4.getCdr()
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            gnu.expr.ObjectExp r1 = new gnu.expr.ObjectExp
            r1.<init>()
            java.lang.Object r2 = r0.getCar()
            boolean r2 = r2 instanceof gnu.lists.FString
            if (r2 == 0) goto L_0x0038
            java.lang.Object r2 = r0.getCdr()
            boolean r2 = r2 instanceof gnu.lists.Pair
            if (r2 != 0) goto L_0x0031
            java.lang.String r2 = "missing superclass specification after object class name"
            gnu.expr.Expression r2 = r5.syntaxError(r2)
            return r2
        L_0x0031:
            java.lang.Object r2 = r0.getCdr()
            r0 = r2
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
        L_0x0038:
            java.lang.Object[] r2 = r3.scanClassDef(r0, r1, r5)
            if (r2 == 0) goto L_0x0041
            r3.rewriteClassDef(r2, r5)
        L_0x0041:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:160:0x04d5, code lost:
        r1 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object[] scanClassDef(gnu.lists.Pair r44, gnu.expr.ClassExp r45, kawa.lang.Translator r46) {
        /*
            r43 = this;
            r0 = r45
            r8 = r46
            r46.mustCompileHere()
            java.lang.Object r9 = r44.getCar()
            java.lang.Object r10 = r44.getCdr()
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            java.util.Vector r6 = new java.util.Vector
            r7 = 20
            r6.<init>(r7)
            r11 = r6
            r6 = r10
            r12 = r1
            r13 = r2
            r14 = r3
            r2 = r6
            r1 = r44
            r6 = r4
        L_0x0024:
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            if (r2 == r3) goto L_0x0585
        L_0x0028:
            boolean r3 = r2 instanceof kawa.lang.SyntaxForm
            if (r3 == 0) goto L_0x0034
            r3 = r2
            kawa.lang.SyntaxForm r3 = (kawa.lang.SyntaxForm) r3
            java.lang.Object r2 = r3.getDatum()
            goto L_0x0028
        L_0x0034:
            boolean r3 = r2 instanceof gnu.lists.Pair
            java.lang.String r5 = "object member not a list"
            r17 = 0
            r15 = 101(0x65, float:1.42E-43)
            if (r3 != 0) goto L_0x0042
            r8.error(r15, r5)
            return r17
        L_0x0042:
            r3 = r2
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            java.lang.Object r1 = r3.getCar()
        L_0x0049:
            boolean r4 = r1 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x0055
            r4 = r1
            kawa.lang.SyntaxForm r4 = (kawa.lang.SyntaxForm) r4
            java.lang.Object r1 = r4.getDatum()
            goto L_0x0049
        L_0x0055:
            java.lang.Object r2 = r3.getCdr()
            java.lang.Object r4 = r8.pushPositionOf(r3)
            boolean r15 = r1 instanceof gnu.expr.Keyword
            if (r15 == 0) goto L_0x011c
            r15 = r2
        L_0x0062:
            boolean r2 = r15 instanceof kawa.lang.SyntaxForm
            if (r2 == 0) goto L_0x006f
            r2 = r15
            kawa.lang.SyntaxForm r2 = (kawa.lang.SyntaxForm) r2
            java.lang.Object r15 = r2.getDatum()
            goto L_0x0062
        L_0x006f:
            boolean r2 = r15 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x0110
            gnu.expr.Keyword r2 = interfaceKeyword
            if (r1 != r2) goto L_0x009b
            r2 = r15
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r2 = r2.getCar()
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r2 != r5) goto L_0x0088
            r5 = 65536(0x10000, float:9.18355E-41)
            r0.setFlag(r5)
            goto L_0x008e
        L_0x0088:
            r5 = 32768(0x8000, float:4.5918E-41)
            r0.setFlag(r5)
        L_0x008e:
            r5 = r15
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r5 = r5.getCdr()
            r8.popPositionOf(r4)
            r1 = r3
            r2 = r5
            goto L_0x0024
        L_0x009b:
            gnu.expr.Keyword r2 = classNameKeyword
            if (r1 != r2) goto L_0x00b6
            if (r12 == 0) goto L_0x00a8
            java.lang.String r2 = "duplicate class-name specifiers"
            r5 = 101(0x65, float:1.42E-43)
            r8.error(r5, r2)
        L_0x00a8:
            r12 = r15
            r2 = r15
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r2 = r2.getCdr()
            r8.popPositionOf(r4)
            r1 = r3
            goto L_0x0024
        L_0x00b6:
            gnu.expr.Keyword r2 = accessKeyword
            if (r1 != r2) goto L_0x0104
            java.lang.Object r5 = r8.pushPositionOf(r15)
            r2 = r15
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r2 = r2.getCar()
            r16 = 25820135424(0x603000000, double:1.2756841884E-313)
            java.lang.String r18 = "class"
            r19 = r1
            r1 = r2
            r20 = r3
            r2 = r6
            r22 = r9
            r21 = r12
            r12 = r4
            r9 = r5
            r4 = r16
            r23 = r6
            r6 = r18
            r7 = r46
            long r6 = addAccessFlags(r1, r2, r4, r6, r7)
            gnu.expr.Declaration r1 = r0.nameDecl
            if (r1 != 0) goto L_0x00ef
            java.lang.String r1 = "access specifier for anonymous class"
            r2 = 101(0x65, float:1.42E-43)
            r8.error(r2, r1)
        L_0x00ef:
            r8.popPositionOf(r9)
            r1 = r15
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            java.lang.Object r2 = r1.getCdr()
            r8.popPositionOf(r12)
            r1 = r20
            r12 = r21
            r9 = r22
            goto L_0x0024
        L_0x0104:
            r19 = r1
            r20 = r3
            r23 = r6
            r22 = r9
            r21 = r12
            r12 = r4
            goto L_0x0128
        L_0x0110:
            r19 = r1
            r20 = r3
            r23 = r6
            r22 = r9
            r21 = r12
            r12 = r4
            goto L_0x0128
        L_0x011c:
            r19 = r1
            r20 = r3
            r23 = r6
            r22 = r9
            r21 = r12
            r12 = r4
            r15 = r2
        L_0x0128:
            r1 = r19
            boolean r2 = r1 instanceof gnu.lists.Pair
            if (r2 != 0) goto L_0x0134
            r2 = 101(0x65, float:1.42E-43)
            r8.error(r2, r5)
            return r17
        L_0x0134:
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r1 = r2.getCar()
            r9 = r1
        L_0x013c:
            boolean r1 = r9 instanceof kawa.lang.SyntaxForm
            if (r1 == 0) goto L_0x0149
            r1 = r9
            kawa.lang.SyntaxForm r1 = (kawa.lang.SyntaxForm) r1
            java.lang.Object r9 = r1.getDatum()
            goto L_0x013c
        L_0x0149:
            boolean r1 = r9 instanceof java.lang.String
            if (r1 != 0) goto L_0x019e
            boolean r1 = r9 instanceof gnu.mapping.Symbol
            if (r1 != 0) goto L_0x019e
            boolean r1 = r9 instanceof gnu.expr.Keyword
            if (r1 == 0) goto L_0x0156
            goto L_0x019e
        L_0x0156:
            boolean r1 = r9 instanceof gnu.lists.Pair
            if (r1 == 0) goto L_0x018e
            r1 = r9
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            java.lang.Object r3 = r1.getCar()
            boolean r4 = r3 instanceof java.lang.String
            if (r4 != 0) goto L_0x0171
            boolean r4 = r3 instanceof gnu.mapping.Symbol
            if (r4 != 0) goto L_0x0171
            java.lang.String r4 = "missing method name"
            r5 = 101(0x65, float:1.42E-43)
            r8.error(r5, r4)
            return r17
        L_0x0171:
            gnu.expr.LambdaExp r4 = new gnu.expr.LambdaExp
            r4.<init>()
            gnu.expr.Declaration r5 = r0.addMethod(r4, r3)
            kawa.lang.Translator.setLine((gnu.expr.Declaration) r5, (java.lang.Object) r1)
            if (r14 != 0) goto L_0x0181
            r13 = r4
            goto L_0x0183
        L_0x0181:
            r14.nextSibling = r4
        L_0x0183:
            r1 = r4
            r14 = r1
            r1 = r2
            r38 = r9
            r39 = r10
            r29 = r15
            goto L_0x0574
        L_0x018e:
            java.lang.String r1 = "invalid field/method definition"
            r3 = 101(0x65, float:1.42E-43)
            r8.error(r3, r1)
            r1 = r2
            r38 = r9
            r39 = r10
            r29 = r15
            goto L_0x0574
        L_0x019e:
            r1 = 0
            r7 = r9
            r3 = 0
            r4 = 0
            boolean r6 = r7 instanceof gnu.expr.Keyword
            if (r6 == 0) goto L_0x01b1
            r6 = 0
            r19 = r2
            r41 = r4
            r4 = r19
            r19 = r41
            goto L_0x01c8
        L_0x01b1:
            gnu.expr.Declaration r6 = r0.addDeclaration((java.lang.Object) r7)
            r19 = r4
            r4 = 0
            r6.setSimple(r4)
            r4 = 1048576(0x100000, double:5.180654E-318)
            r6.setFlag(r4)
            kawa.lang.Translator.setLine((gnu.expr.Declaration) r6, (java.lang.Object) r2)
            java.lang.Object r4 = r2.getCdr()
        L_0x01c8:
            r5 = 0
            r25 = 0
            r26 = 0
            r27 = r26
            r41 = r4
            r4 = r1
            r1 = r41
            r42 = r5
            r5 = r3
            r3 = r42
        L_0x01d9:
            r26 = r2
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            if (r1 == r2) goto L_0x04d7
        L_0x01df:
            boolean r2 = r1 instanceof kawa.lang.SyntaxForm
            if (r2 == 0) goto L_0x01eb
            r2 = r1
            kawa.lang.SyntaxForm r2 = (kawa.lang.SyntaxForm) r2
            java.lang.Object r1 = r2.getDatum()
            goto L_0x01df
        L_0x01eb:
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            r26 = r2
            java.lang.Object r28 = r2.getCar()
            r29 = r4
            r4 = r28
        L_0x01f8:
            r28 = r1
            boolean r1 = r4 instanceof kawa.lang.SyntaxForm
            if (r1 == 0) goto L_0x0208
            r1 = r4
            kawa.lang.SyntaxForm r1 = (kawa.lang.SyntaxForm) r1
            java.lang.Object r4 = r1.getDatum()
            r1 = r28
            goto L_0x01f8
        L_0x0208:
            java.lang.Object r1 = r8.pushPositionOf(r2)
            r30 = r1
            java.lang.Object r1 = r2.getCdr()
            r28 = r2
            gnu.mapping.Symbol r2 = coloncolon
            if (r4 == r2) goto L_0x0233
            boolean r2 = r4 instanceof gnu.expr.Keyword
            if (r2 == 0) goto L_0x021d
            goto L_0x0233
        L_0x021d:
            r0 = r7
            r38 = r9
            r39 = r10
            r33 = r13
            r44 = r14
            r9 = r29
            r13 = r30
            r16 = 0
            r14 = r5
            r10 = r6
            r29 = r15
            r15 = r4
            goto L_0x0484
        L_0x0233:
            boolean r2 = r1 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x0470
            int r31 = r3 + 1
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r3 = r2.getCar()
            java.lang.Object r28 = r2.getCdr()
            gnu.mapping.Symbol r1 = coloncolon
            if (r4 == r1) goto L_0x044e
            gnu.expr.Keyword r1 = typeKeyword
            if (r4 != r1) goto L_0x0266
            r37 = r3
            r0 = r7
            r38 = r9
            r39 = r10
            r33 = r13
            r44 = r14
            r9 = r29
            r13 = r30
            r16 = 0
            r30 = r2
            r14 = r5
            r10 = r6
            r29 = r15
            r15 = r4
            goto L_0x0466
        L_0x0266:
            gnu.expr.Keyword r1 = allocationKeyword
            r32 = r6
            java.lang.String r6 = "'"
            if (r4 != r1) goto L_0x0303
            if (r5 == 0) goto L_0x027a
            java.lang.String r1 = "duplicate allocation: specification"
            r33 = r5
            r5 = 101(0x65, float:1.42E-43)
            r8.error(r5, r1)
            goto L_0x027c
        L_0x027a:
            r33 = r5
        L_0x027c:
            java.lang.String r1 = "class"
            boolean r1 = matches(r3, r1, r8)
            if (r1 != 0) goto L_0x02e7
            java.lang.String r1 = "static"
            boolean r1 = matches(r3, r1, r8)
            if (r1 == 0) goto L_0x028d
            goto L_0x02e7
        L_0x028d:
            java.lang.String r1 = "instance"
            boolean r1 = matches(r3, r1, r8)
            if (r1 == 0) goto L_0x02b1
            r5 = 4096(0x1000, float:5.74E-42)
            r0 = r7
            r38 = r9
            r39 = r10
            r33 = r13
            r44 = r14
            r13 = r30
            r10 = r32
            r16 = 0
            r30 = r2
            r41 = r15
            r15 = r4
            r4 = r29
            r29 = r41
            goto L_0x0469
        L_0x02b1:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = "unknown allocation kind '"
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            r5 = 101(0x65, float:1.42E-43)
            r8.error(r5, r1)
            r0 = r7
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r10 = r32
            r14 = r33
            r16 = 0
            r33 = r13
            r29 = r15
            r13 = r30
            r30 = r2
            r15 = r4
            goto L_0x0419
        L_0x02e7:
            r5 = 2048(0x800, float:2.87E-42)
            r0 = r7
            r38 = r9
            r39 = r10
            r33 = r13
            r44 = r14
            r13 = r30
            r10 = r32
            r16 = 0
            r30 = r2
            r41 = r15
            r15 = r4
            r4 = r29
            r29 = r41
            goto L_0x0469
        L_0x0303:
            r33 = r5
            gnu.expr.Keyword r1 = initKeyword
            if (r4 == r1) goto L_0x041d
            gnu.expr.Keyword r1 = initformKeyword
            if (r4 == r1) goto L_0x041d
            gnu.expr.Keyword r1 = init_formKeyword
            if (r4 == r1) goto L_0x041d
            gnu.expr.Keyword r1 = init_valueKeyword
            if (r4 != r1) goto L_0x0331
            r37 = r3
            r0 = r7
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r10 = r32
            r14 = r33
            r16 = 0
            r33 = r13
            r29 = r15
            r13 = r30
            r30 = r2
            r15 = r4
            goto L_0x0437
        L_0x0331:
            gnu.expr.Keyword r1 = init_keywordKeyword
            r5 = 119(0x77, float:1.67E-43)
            if (r4 != r1) goto L_0x03a2
            boolean r1 = r3 instanceof gnu.expr.Keyword
            if (r1 != 0) goto L_0x035c
            java.lang.String r1 = "invalid 'init-keyword' - not a keyword"
            r5 = 101(0x65, float:1.42E-43)
            r8.error(r5, r1)
            r0 = r7
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r10 = r32
            r14 = r33
            r16 = 0
            r33 = r13
            r29 = r15
            r13 = r30
            r30 = r2
            r15 = r4
            goto L_0x0419
        L_0x035c:
            r1 = r3
            gnu.expr.Keyword r1 = (gnu.expr.Keyword) r1
            java.lang.String r1 = r1.getName()
            java.lang.String r6 = r7.toString()
            if (r1 == r6) goto L_0x0388
            java.lang.String r1 = "init-keyword option ignored"
            r8.error(r5, r1)
            r0 = r7
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r10 = r32
            r14 = r33
            r16 = 0
            r33 = r13
            r29 = r15
            r13 = r30
            r30 = r2
            r15 = r4
            goto L_0x0419
        L_0x0388:
            r0 = r7
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r10 = r32
            r14 = r33
            r16 = 0
            r33 = r13
            r29 = r15
            r13 = r30
            r30 = r2
            r15 = r4
            goto L_0x0419
        L_0x03a2:
            gnu.expr.Keyword r1 = accessKeyword
            if (r4 != r1) goto L_0x03e5
            java.lang.Object r6 = r8.pushPositionOf(r2)
            r34 = 32463912960(0x78f000000, double:1.6039304123E-313)
            java.lang.String r36 = "field"
            r5 = r30
            r1 = r3
            r30 = r2
            r37 = r3
            r2 = r19
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r14 = r33
            r10 = 1
            r16 = 0
            r33 = r13
            r29 = r15
            r15 = r4
            r13 = r5
            r4 = r34
            r40 = r6
            r10 = r32
            r6 = r36
            r0 = r7
            r7 = r46
            long r19 = addAccessFlags(r1, r2, r4, r6, r7)
            r1 = r40
            r8.popPositionOf(r1)
            r4 = r9
            r5 = r14
            goto L_0x0469
        L_0x03e5:
            r37 = r3
            r0 = r7
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r10 = r32
            r14 = r33
            r16 = 0
            r33 = r13
            r29 = r15
            r13 = r30
            r30 = r2
            r15 = r4
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "unknown slot keyword '"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r15)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            r8.error(r5, r1)
        L_0x0419:
            r4 = r9
            r5 = r14
            goto L_0x0469
        L_0x041d:
            r37 = r3
            r0 = r7
            r38 = r9
            r39 = r10
            r44 = r14
            r9 = r29
            r10 = r32
            r14 = r33
            r16 = 0
            r33 = r13
            r29 = r15
            r13 = r30
            r30 = r2
            r15 = r4
        L_0x0437:
            if (r25 == 0) goto L_0x0440
            java.lang.String r1 = "duplicate initialization"
            r2 = 101(0x65, float:1.42E-43)
            r8.error(r2, r1)
        L_0x0440:
            r25 = 1
            gnu.expr.Keyword r1 = initKeyword
            if (r15 == r1) goto L_0x044b
            r27 = r30
            r4 = r9
            r5 = r14
            goto L_0x0469
        L_0x044b:
            r4 = r9
            r5 = r14
            goto L_0x0469
        L_0x044e:
            r37 = r3
            r0 = r7
            r38 = r9
            r39 = r10
            r33 = r13
            r44 = r14
            r9 = r29
            r13 = r30
            r16 = 0
            r30 = r2
            r14 = r5
            r10 = r6
            r29 = r15
            r15 = r4
        L_0x0466:
            r4 = r30
            r5 = r14
        L_0x0469:
            r1 = r28
            r2 = r30
            r3 = r31
            goto L_0x04be
        L_0x0470:
            r0 = r7
            r38 = r9
            r39 = r10
            r33 = r13
            r44 = r14
            r9 = r29
            r13 = r30
            r16 = 0
            r14 = r5
            r10 = r6
            r29 = r15
            r15 = r4
        L_0x0484:
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            if (r1 != r2) goto L_0x0496
            if (r25 != 0) goto L_0x0496
            r2 = r26
            r4 = 1
            r27 = r2
            r25 = r4
            r4 = r9
            r5 = r14
            r2 = r28
            goto L_0x04be
        L_0x0496:
            boolean r2 = r1 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x04d3
            if (r3 != 0) goto L_0x04d3
            if (r25 != 0) goto L_0x04d3
            if (r9 != 0) goto L_0x04d3
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            r4 = r2
            java.lang.Object r2 = r2.getCdr()
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            if (r2 != r5) goto L_0x04d1
            r2 = r26
            r5 = r4
            java.lang.Object r1 = r4.getCdr()
            r6 = 1
            r27 = r5
            r25 = r6
            r5 = r14
            r41 = r4
            r4 = r2
            r2 = r41
        L_0x04be:
            r8.popPositionOf(r13)
            r14 = r44
            r7 = r0
            r6 = r10
            r15 = r29
            r13 = r33
            r9 = r38
            r10 = r39
            r0 = r45
            goto L_0x01d9
        L_0x04d1:
            r2 = r4
            goto L_0x04d5
        L_0x04d3:
            r2 = r28
        L_0x04d5:
            r1 = 0
            goto L_0x04e9
        L_0x04d7:
            r0 = r7
            r38 = r9
            r39 = r10
            r33 = r13
            r44 = r14
            r29 = r15
            r16 = 0
            r9 = r4
            r14 = r5
            r10 = r6
            r2 = r26
        L_0x04e9:
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            if (r1 == r4) goto L_0x0523
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "invalid argument list for slot '"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r0)
            r5 = 39
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = " args:"
            java.lang.StringBuilder r4 = r4.append(r5)
            if (r1 != 0) goto L_0x050d
            java.lang.String r5 = "null"
            goto L_0x0515
        L_0x050d:
            java.lang.Class r5 = r1.getClass()
            java.lang.String r5 = r5.getName()
        L_0x0515:
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 101(0x65, float:1.42E-43)
            r8.error(r5, r4)
            return r17
        L_0x0523:
            if (r25 == 0) goto L_0x0540
            r4 = 2048(0x800, float:2.87E-42)
            if (r14 != r4) goto L_0x052b
            r4 = 1
            goto L_0x052c
        L_0x052b:
            r4 = 0
        L_0x052c:
            if (r10 == 0) goto L_0x0530
            r6 = r10
            goto L_0x0537
        L_0x0530:
            if (r4 == 0) goto L_0x0535
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            goto L_0x0537
        L_0x0535:
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
        L_0x0537:
            r11.addElement(r6)
            r5 = r27
            r11.addElement(r5)
            goto L_0x0542
        L_0x0540:
            r5 = r27
        L_0x0542:
            if (r10 != 0) goto L_0x054e
            if (r25 != 0) goto L_0x056f
            java.lang.String r4 = "missing field name"
            r6 = 101(0x65, float:1.42E-43)
            r8.error(r6, r4)
            return r17
        L_0x054e:
            if (r9 == 0) goto L_0x0557
            gnu.bytecode.Type r4 = r8.exp2Type(r9)
            r10.setType(r4)
        L_0x0557:
            if (r14 == 0) goto L_0x055d
            long r6 = (long) r14
            r10.setFlag(r6)
        L_0x055d:
            r6 = r19
            r15 = 0
            int r4 = (r6 > r15 ? 1 : (r6 == r15 ? 0 : -1))
            if (r4 == 0) goto L_0x0568
            r10.setFlag(r6)
        L_0x0568:
            r4 = 1
            r10.setCanRead(r4)
            r10.setCanWrite(r4)
        L_0x056f:
            r14 = r44
            r1 = r2
            r13 = r33
        L_0x0574:
            r8.popPositionOf(r12)
            r0 = r45
            r12 = r21
            r9 = r22
            r6 = r23
            r2 = r29
            r10 = r39
            goto L_0x0024
        L_0x0585:
            r23 = r6
            r22 = r9
            r39 = r10
            r21 = r12
            r33 = r13
            r44 = r14
            r16 = 0
            r2 = 0
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x05a1
            r0 = r45
            gnu.expr.Declaration r2 = r0.nameDecl
            r2.setFlag(r6)
            goto L_0x05a3
        L_0x05a1:
            r0 = r45
        L_0x05a3:
            r2 = 6
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r16] = r0
            r3 = 1
            r2[r3] = r39
            r3 = 2
            r2[r3] = r11
            r3 = 3
            r2[r3] = r33
            r3 = 4
            r2[r3] = r22
            r3 = 5
            r2[r3] = r21
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.scanClassDef(gnu.lists.Pair, gnu.expr.ClassExp, kawa.lang.Translator):java.lang.Object[]");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v0, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v4, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v5, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v17, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v22, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v18, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v13, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v24, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v15, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v16, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v18, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v23, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v12, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v58, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v60, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v20, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v5, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v21, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v25, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v27, resolved type: int} */
    /* JADX WARNING: type inference failed for: r27v4 */
    /* JADX WARNING: type inference failed for: r27v7 */
    /* JADX WARNING: type inference failed for: r27v8 */
    /* JADX WARNING: type inference failed for: r27v9 */
    /* JADX WARNING: type inference failed for: r27v11 */
    /* JADX WARNING: type inference failed for: r1v42, types: [gnu.expr.ScopeExp] */
    /* JADX WARNING: type inference failed for: r27v14 */
    /* JADX WARNING: type inference failed for: r0v37, types: [gnu.expr.ScopeExp] */
    /* JADX WARNING: type inference failed for: r27v19 */
    /* JADX WARNING: type inference failed for: r27v25 */
    /* JADX WARNING: type inference failed for: r14v15 */
    /* JADX WARNING: type inference failed for: r9v24 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0253 A[Catch:{ all -> 0x025f, all -> 0x0296 }] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x02d2 A[Catch:{ all -> 0x0401 }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x02d5 A[Catch:{ all -> 0x0401 }] */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x02df A[Catch:{ all -> 0x0401 }, LOOP:8: B:142:0x02df->B:147:0x02ec, LOOP_START, PHI: r2 r19 
      PHI: (r2v17 'args' java.lang.Object) = (r2v13 'args' java.lang.Object), (r2v26 'args' java.lang.Object) binds: [B:141:0x02dd, B:147:0x02ec] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r19v3 'memberSyntax' kawa.lang.SyntaxForm) = (r19v2 'memberSyntax' kawa.lang.SyntaxForm), (r19v4 'memberSyntax' kawa.lang.SyntaxForm) binds: [B:141:0x02dd, B:147:0x02ec] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x03ae  */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x03e6  */
    /* JADX WARNING: Removed duplicated region for block: B:267:0x03a6 A[SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteClassDef(java.lang.Object[] r33, kawa.lang.Translator r34) {
        /*
            r32 = this;
            r7 = r34
            r0 = 0
            r1 = r33[r0]
            r8 = r1
            gnu.expr.ClassExp r8 = (gnu.expr.ClassExp) r8
            r9 = 1
            r10 = r33[r9]
            r1 = 2
            r1 = r33[r1]
            r11 = r1
            java.util.Vector r11 = (java.util.Vector) r11
            r1 = 3
            r1 = r33[r1]
            r12 = r1
            gnu.expr.LambdaExp r12 = (gnu.expr.LambdaExp) r12
            r1 = 4
            r1 = r33[r1]
            r2 = 5
            r13 = r33[r2]
            r8.firstChild = r12
            int r2 = kawa.lang.Translator.listLength(r1)
            r3 = 101(0x65, float:1.42E-43)
            if (r2 >= 0) goto L_0x002f
            java.lang.String r4 = "object superclass specification not a list"
            r7.error(r3, r4)
            r2 = 0
            r14 = r2
            goto L_0x0030
        L_0x002f:
            r14 = r2
        L_0x0030:
            gnu.expr.Expression[] r15 = new gnu.expr.Expression[r14]
            r2 = 0
            r16 = r1
        L_0x0035:
            if (r2 >= r14) goto L_0x007d
            r1 = r16
        L_0x0039:
            boolean r4 = r1 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x0045
            r4 = r1
            kawa.lang.SyntaxForm r4 = (kawa.lang.SyntaxForm) r4
            java.lang.Object r1 = r4.getDatum()
            goto L_0x0039
        L_0x0045:
            r4 = r1
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            gnu.expr.Expression r5 = r7.rewrite_car((gnu.lists.Pair) r4, (boolean) r0)
            r15[r2] = r5
            r5 = r15[r2]
            boolean r5 = r5 instanceof gnu.expr.ReferenceExp
            if (r5 == 0) goto L_0x0075
            r5 = r15[r2]
            gnu.expr.ReferenceExp r5 = (gnu.expr.ReferenceExp) r5
            gnu.expr.Declaration r5 = r5.getBinding()
            gnu.expr.Declaration r5 = gnu.expr.Declaration.followAliases(r5)
            if (r5 == 0) goto L_0x0075
            gnu.expr.Expression r6 = r5.getValue()
            r16 = r6
            boolean r6 = r6 instanceof gnu.expr.ClassExp
            if (r6 == 0) goto L_0x0075
            r6 = r16
            gnu.expr.ClassExp r6 = (gnu.expr.ClassExp) r6
            r9 = 131072(0x20000, float:1.83671E-40)
            r6.setFlag(r9)
        L_0x0075:
            java.lang.Object r16 = r4.getCdr()
            int r2 = r2 + 1
            r9 = 1
            goto L_0x0035
        L_0x007d:
            if (r13 == 0) goto L_0x00a8
            r1 = r13
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            gnu.expr.Expression r1 = r7.rewrite_car((gnu.lists.Pair) r1, (boolean) r0)
            java.lang.Object r2 = r1.valueIfConstant()
            boolean r4 = r2 instanceof java.lang.CharSequence
            if (r4 == 0) goto L_0x009c
            java.lang.String r5 = r2.toString()
            r6 = r5
            int r5 = r5.length()
            if (r5 <= 0) goto L_0x009c
            r8.classNameSpecifier = r6
            goto L_0x00a8
        L_0x009c:
            java.lang.Object r5 = r7.pushPositionOf(r13)
            java.lang.String r6 = "class-name specifier must be a non-empty string literal"
            r7.error(r3, r6)
            r7.popPositionOf(r5)
        L_0x00a8:
            r8.supers = r15
            r8.setTypes(r7)
            int r9 = r11.size()
            r1 = 0
        L_0x00b2:
            r6 = 0
            if (r1 >= r9) goto L_0x00ca
            int r2 = r1 + 1
            java.lang.Object r2 = r11.elementAt(r2)
            if (r2 == 0) goto L_0x00c7
            java.lang.Object r3 = r11.elementAt(r1)
            r4 = r2
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            rewriteInit(r3, r8, r4, r7, r6)
        L_0x00c7:
            int r1 = r1 + 2
            goto L_0x00b2
        L_0x00ca:
            r7.push((gnu.expr.ScopeExp) r8)
            r1 = r12
            r2 = 0
            r3 = 0
            r4 = r10
            r5 = r1
            r1 = r4
            r4 = r2
        L_0x00d4:
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            if (r1 == r2) goto L_0x045a
            r17 = r3
        L_0x00da:
            boolean r2 = r1 instanceof kawa.lang.SyntaxForm
            if (r2 == 0) goto L_0x00e7
            r17 = r1
            kawa.lang.SyntaxForm r17 = (kawa.lang.SyntaxForm) r17
            java.lang.Object r1 = r17.getDatum()
            goto L_0x00da
        L_0x00e7:
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r3 = r7.pushPositionOf(r2)
            java.lang.Object r18 = r2.getCar()
            r19 = r17
            r6 = r18
        L_0x00f6:
            boolean r0 = r6 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x0104
            r19 = r6
            kawa.lang.SyntaxForm r19 = (kawa.lang.SyntaxForm) r19
            java.lang.Object r6 = r19.getDatum()
            r0 = 0
            goto L_0x00f6
        L_0x0104:
            java.lang.Object r0 = r2.getCdr()     // Catch:{ all -> 0x0447 }
            r1 = r0
            boolean r0 = r6 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x0435 }
            if (r0 == 0) goto L_0x0131
            boolean r0 = r1 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0121 }
            if (r0 == 0) goto L_0x0131
            r0 = r1
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0121 }
            java.lang.Object r0 = r0.getCdr()     // Catch:{ all -> 0x0121 }
            r1 = r0
            r7.popPositionOf(r3)
            r3 = r17
            r0 = 0
            r6 = 0
            goto L_0x00d4
        L_0x0121:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            goto L_0x0456
        L_0x0131:
            r0 = r6
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0435 }
            r2 = r0
            java.lang.Object r0 = r2.getCar()     // Catch:{ all -> 0x0421 }
            r6 = r19
            r31 = r6
            r6 = r0
            r0 = r31
        L_0x0140:
            r21 = r1
            boolean r1 = r6 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x040c }
            if (r1 == 0) goto L_0x0164
            r1 = r6
            kawa.lang.SyntaxForm r1 = (kawa.lang.SyntaxForm) r1     // Catch:{ all -> 0x0152 }
            r0 = r1
            java.lang.Object r1 = r0.getDatum()     // Catch:{ all -> 0x0152 }
            r6 = r1
            r1 = r21
            goto L_0x0140
        L_0x0152:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r9 = r3
            goto L_0x0456
        L_0x0164:
            boolean r1 = r6 instanceof java.lang.String     // Catch:{ all -> 0x040c }
            if (r1 != 0) goto L_0x02b8
            boolean r1 = r6 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x02a1 }
            if (r1 != 0) goto L_0x02b8
            boolean r1 = r6 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x02a1 }
            if (r1 == 0) goto L_0x0186
            r23 = r0
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r20 = 0
            r9 = r3
            r12 = r4
            r13 = r5
            r14 = r6
            goto L_0x02cc
        L_0x0186:
            boolean r1 = r6 instanceof gnu.lists.Pair     // Catch:{ all -> 0x02a1 }
            if (r1 == 0) goto L_0x0277
            gnu.expr.ScopeExp r1 = r34.currentScope()     // Catch:{ all -> 0x02a1 }
            if (r19 == 0) goto L_0x019a
            r22 = r1
            kawa.lang.TemplateScope r1 = r19.getScope()     // Catch:{ all -> 0x0152 }
            r7.setCurrentScope(r1)     // Catch:{ all -> 0x0152 }
            goto L_0x019c
        L_0x019a:
            r22 = r1
        L_0x019c:
            java.lang.String r1 = "*init*"
            r23 = r3
            java.lang.String r3 = r5.getName()     // Catch:{ all -> 0x025f }
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x025f }
            if (r1 == 0) goto L_0x01c3
            gnu.bytecode.PrimType r1 = gnu.bytecode.Type.voidType     // Catch:{ all -> 0x01b0 }
            r5.setReturnType(r1)     // Catch:{ all -> 0x01b0 }
            goto L_0x01c3
        L_0x01b0:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r9 = r23
            goto L_0x0456
        L_0x01c3:
            kawa.lang.Translator.setLine((gnu.expr.Expression) r5, (java.lang.Object) r2)     // Catch:{ all -> 0x025f }
            gnu.expr.LambdaExp r1 = r7.curMethodLambda     // Catch:{ all -> 0x025f }
            r3 = r1
            r7.curMethodLambda = r5     // Catch:{ all -> 0x025f }
            r1 = r32
            r24 = r3
            kawa.lang.Lambda r3 = r1.lambda     // Catch:{ all -> 0x025f }
            r25 = r6
            gnu.lists.Pair r25 = (gnu.lists.Pair) r25     // Catch:{ all -> 0x025f }
            java.lang.Object r25 = r25.getCdr()     // Catch:{ all -> 0x025f }
            java.lang.Object r26 = r2.getCdr()     // Catch:{ all -> 0x025f }
            if (r0 == 0) goto L_0x0221
            if (r19 == 0) goto L_0x0203
            kawa.lang.TemplateScope r1 = r0.getScope()     // Catch:{ all -> 0x01ee }
            r27 = r2
            kawa.lang.TemplateScope r2 = r19.getScope()     // Catch:{ all -> 0x020c }
            if (r1 == r2) goto L_0x0223
            goto L_0x0205
        L_0x01ee:
            r0 = move-exception
            r27 = r2
            r28 = r5
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r27 = r9
            r9 = r23
            goto L_0x0456
        L_0x0203:
            r27 = r2
        L_0x0205:
            kawa.lang.TemplateScope r1 = r0.getScope()     // Catch:{ all -> 0x020c }
            r28 = r1
            goto L_0x0225
        L_0x020c:
            r0 = move-exception
            r28 = r5
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r2 = r27
            r27 = r9
            r9 = r23
            goto L_0x0456
        L_0x0221:
            r27 = r2
        L_0x0223:
            r28 = 0
        L_0x0225:
            r2 = r22
            r1 = r3
            r3 = r2
            r22 = r27
            r2 = r5
            r27 = r9
            r9 = r23
            r23 = r0
            r0 = r3
            r31 = r24
            r24 = r10
            r10 = r31
            r3 = r25
            r25 = r12
            r12 = r4
            r4 = r26
            r26 = r13
            r13 = r5
            r5 = r34
            r29 = r14
            r20 = 0
            r14 = r6
            r6 = r28
            r1.rewrite(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0296 }
            r7.curMethodLambda = r10     // Catch:{ all -> 0x0296 }
            if (r19 == 0) goto L_0x0256
            r7.setCurrentScope(r0)     // Catch:{ all -> 0x0296 }
        L_0x0256:
            gnu.expr.LambdaExp r1 = r13.nextSibling     // Catch:{ all -> 0x0296 }
            r0 = r1
            r5 = r0
            r4 = r12
            r2 = r22
            goto L_0x03ea
        L_0x025f:
            r0 = move-exception
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r23
            r12 = r4
            r13 = r5
            r14 = r6
            r28 = r13
            r1 = r21
            goto L_0x0456
        L_0x0277:
            r23 = r0
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r20 = 0
            r9 = r3
            r12 = r4
            r13 = r5
            r14 = r6
            java.lang.String r0 = "invalid field/method definition"
            r7.syntaxError(r0)     // Catch:{ all -> 0x0296 }
            r4 = r12
            r5 = r13
            r2 = r22
            goto L_0x03ea
        L_0x0296:
            r0 = move-exception
            r4 = r12
            r28 = r13
            r6 = r14
            r1 = r21
            r2 = r22
            goto L_0x0456
        L_0x02a1:
            r0 = move-exception
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            r13 = r5
            r14 = r6
            r28 = r13
            r1 = r21
            goto L_0x0456
        L_0x02b8:
            r23 = r0
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r20 = 0
            r9 = r3
            r12 = r4
            r13 = r5
            r14 = r6
        L_0x02cc:
            r0 = 0
            r1 = 0
            boolean r2 = r14 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x0401 }
            if (r2 == 0) goto L_0x02d5
            r2 = r22
            goto L_0x02d9
        L_0x02d5:
            java.lang.Object r2 = r22.getCdr()     // Catch:{ all -> 0x0401 }
        L_0x02d9:
            r3 = 0
            r4 = 0
        L_0x02db:
            gnu.lists.LList r5 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0401 }
            if (r2 == r5) goto L_0x03a6
        L_0x02df:
            boolean r5 = r2 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x0401 }
            if (r5 == 0) goto L_0x02ee
            r5 = r2
            kawa.lang.SyntaxForm r5 = (kawa.lang.SyntaxForm) r5     // Catch:{ all -> 0x0296 }
            r19 = r5
            java.lang.Object r5 = r19.getDatum()     // Catch:{ all -> 0x0296 }
            r2 = r5
            goto L_0x02df
        L_0x02ee:
            r5 = r2
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5     // Catch:{ all -> 0x0401 }
            java.lang.Object r6 = r5.getCar()     // Catch:{ all -> 0x039c }
        L_0x02f5:
            boolean r10 = r6 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x039c }
            if (r10 == 0) goto L_0x030c
            r10 = r6
            kawa.lang.SyntaxForm r10 = (kawa.lang.SyntaxForm) r10     // Catch:{ all -> 0x0302 }
            java.lang.Object r10 = r10.getDatum()     // Catch:{ all -> 0x0302 }
            r6 = r10
            goto L_0x02f5
        L_0x0302:
            r0 = move-exception
            r2 = r5
            r4 = r12
            r28 = r13
            r6 = r14
            r1 = r21
            goto L_0x0456
        L_0x030c:
            java.lang.Object r10 = r7.pushPositionOf(r5)     // Catch:{ all -> 0x039c }
            java.lang.Object r22 = r5.getCdr()     // Catch:{ all -> 0x039c }
            r2 = r22
            r28 = r13
            gnu.mapping.Symbol r13 = coloncolon     // Catch:{ all -> 0x03d6 }
            if (r6 == r13) goto L_0x0320
            boolean r13 = r6 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x03d6 }
            if (r13 == 0) goto L_0x0359
        L_0x0320:
            boolean r13 = r2 instanceof gnu.lists.Pair     // Catch:{ all -> 0x03d6 }
            if (r13 == 0) goto L_0x0359
            int r1 = r1 + 1
            r13 = r2
            gnu.lists.Pair r13 = (gnu.lists.Pair) r13     // Catch:{ all -> 0x03d6 }
            r5 = r13
            java.lang.Object r13 = r5.getCar()     // Catch:{ all -> 0x03d6 }
            java.lang.Object r22 = r5.getCdr()     // Catch:{ all -> 0x03d6 }
            r2 = r22
            r22 = r1
            gnu.mapping.Symbol r1 = coloncolon     // Catch:{ all -> 0x03d6 }
            if (r6 == r1) goto L_0x0353
            gnu.expr.Keyword r1 = typeKeyword     // Catch:{ all -> 0x03d6 }
            if (r6 != r1) goto L_0x033f
            goto L_0x0353
        L_0x033f:
            gnu.expr.Keyword r1 = initKeyword     // Catch:{ all -> 0x03d6 }
            if (r6 == r1) goto L_0x034f
            gnu.expr.Keyword r1 = initformKeyword     // Catch:{ all -> 0x03d6 }
            if (r6 == r1) goto L_0x034f
            gnu.expr.Keyword r1 = init_formKeyword     // Catch:{ all -> 0x03d6 }
            if (r6 == r1) goto L_0x034f
            gnu.expr.Keyword r1 = init_valueKeyword     // Catch:{ all -> 0x03d6 }
            if (r6 != r1) goto L_0x0354
        L_0x034f:
            r3 = r5
            r4 = r19
            goto L_0x0354
        L_0x0353:
            r0 = r13
        L_0x0354:
            r1 = r22
            r22 = r5
            goto L_0x0388
        L_0x0359:
            gnu.lists.LList r13 = gnu.lists.LList.Empty     // Catch:{ all -> 0x03d6 }
            if (r2 != r13) goto L_0x0365
            if (r3 != 0) goto L_0x0365
            r3 = r5
            r4 = r19
            r22 = r5
            goto L_0x0388
        L_0x0365:
            boolean r13 = r2 instanceof gnu.lists.Pair     // Catch:{ all -> 0x03d6 }
            if (r13 == 0) goto L_0x0398
            if (r1 != 0) goto L_0x0398
            if (r3 != 0) goto L_0x0398
            if (r0 != 0) goto L_0x0398
            r13 = r2
            gnu.lists.Pair r13 = (gnu.lists.Pair) r13     // Catch:{ all -> 0x03d6 }
            r5 = r13
            java.lang.Object r13 = r13.getCdr()     // Catch:{ all -> 0x03d6 }
            r30 = r0
            gnu.lists.LList r0 = gnu.lists.LList.Empty     // Catch:{ all -> 0x03d6 }
            if (r13 != r0) goto L_0x039a
            r0 = r6
            r3 = r5
            r4 = r19
            java.lang.Object r13 = r5.getCdr()     // Catch:{ all -> 0x03d6 }
            r2 = r13
            r22 = r5
        L_0x0388:
            r7.popPositionOf(r10)     // Catch:{ all -> 0x038f }
            r13 = r28
            goto L_0x02db
        L_0x038f:
            r0 = move-exception
            r4 = r12
            r6 = r14
            r1 = r21
            r2 = r22
            goto L_0x0456
        L_0x0398:
            r30 = r0
        L_0x039a:
            r2 = 0
            goto L_0x03ac
        L_0x039c:
            r0 = move-exception
            r28 = r13
            r2 = r5
            r4 = r12
            r6 = r14
            r1 = r21
            goto L_0x0456
        L_0x03a6:
            r30 = r0
            r28 = r13
            r5 = r22
        L_0x03ac:
            if (r3 == 0) goto L_0x03e6
            int r6 = r12 + 1
            java.lang.Object r0 = r11.elementAt(r12)     // Catch:{ all -> 0x03de }
            boolean r10 = r0 instanceof gnu.expr.Declaration     // Catch:{ all -> 0x03de }
            if (r10 == 0) goto L_0x03c2
            r10 = r0
            gnu.expr.Declaration r10 = (gnu.expr.Declaration) r10     // Catch:{ all -> 0x03de }
            r12 = 2048(0x800, double:1.0118E-320)
            boolean r10 = r10.getFlag(r12)     // Catch:{ all -> 0x03de }
            goto L_0x03c9
        L_0x03c2:
            java.lang.Boolean r10 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x03de }
            if (r0 != r10) goto L_0x03c8
            r10 = 1
            goto L_0x03c9
        L_0x03c8:
            r10 = 0
        L_0x03c9:
            int r12 = r6 + 1
            java.lang.Object r6 = r11.elementAt(r6)     // Catch:{ all -> 0x03d6 }
            if (r6 != 0) goto L_0x03d4
            rewriteInit(r0, r8, r3, r7, r4)     // Catch:{ all -> 0x03d6 }
        L_0x03d4:
            r4 = r12
            goto L_0x03e7
        L_0x03d6:
            r0 = move-exception
            r2 = r5
            r4 = r12
            r6 = r14
            r1 = r21
            goto L_0x0456
        L_0x03de:
            r0 = move-exception
            r2 = r5
            r4 = r6
            r6 = r14
            r1 = r21
            goto L_0x0456
        L_0x03e6:
            r4 = r12
        L_0x03e7:
            r2 = r5
            r5 = r28
        L_0x03ea:
            r7.popPositionOf(r9)
            r3 = r17
            r6 = r20
            r1 = r21
            r10 = r24
            r12 = r25
            r13 = r26
            r9 = r27
            r14 = r29
            r0 = 0
            goto L_0x00d4
        L_0x0401:
            r0 = move-exception
            r28 = r13
            r4 = r12
            r6 = r14
            r1 = r21
            r2 = r22
            goto L_0x0456
        L_0x040c:
            r0 = move-exception
            r22 = r2
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            r14 = r6
            r1 = r21
            goto L_0x0456
        L_0x0421:
            r0 = move-exception
            r21 = r1
            r22 = r2
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            goto L_0x0456
        L_0x0435:
            r0 = move-exception
            r21 = r1
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            goto L_0x0456
        L_0x0447:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
        L_0x0456:
            r7.popPositionOf(r9)
            throw r0
        L_0x045a:
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r12 = r4
            gnu.expr.LambdaExp r0 = r8.initMethod
            if (r0 == 0) goto L_0x046f
            gnu.expr.LambdaExp r0 = r8.initMethod
            r0.outer = r8
        L_0x046f:
            gnu.expr.LambdaExp r0 = r8.clinitMethod
            if (r0 == 0) goto L_0x0477
            gnu.expr.LambdaExp r0 = r8.clinitMethod
            r0.outer = r8
        L_0x0477:
            r7.pop(r8)
            r8.declareParts(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteClassDef(java.lang.Object[], kawa.lang.Translator):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: gnu.expr.SetExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: gnu.expr.ApplyExp} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void rewriteInit(java.lang.Object r7, gnu.expr.ClassExp r8, gnu.lists.Pair r9, kawa.lang.Translator r10, kawa.lang.SyntaxForm r11) {
        /*
            boolean r0 = r7 instanceof gnu.expr.Declaration
            r1 = 1
            if (r0 == 0) goto L_0x000f
            r0 = r7
            gnu.expr.Declaration r0 = (gnu.expr.Declaration) r0
            r2 = 2048(0x800, double:1.0118E-320)
            boolean r0 = r0.getFlag(r2)
            goto L_0x0016
        L_0x000f:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            if (r7 != r0) goto L_0x0015
            r0 = 1
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            if (r0 == 0) goto L_0x001b
            gnu.expr.LambdaExp r2 = r8.clinitMethod
            goto L_0x001d
        L_0x001b:
            gnu.expr.LambdaExp r2 = r8.initMethod
        L_0x001d:
            r3 = 0
            if (r2 != 0) goto L_0x0054
            gnu.expr.LambdaExp r4 = new gnu.expr.LambdaExp
            gnu.expr.BeginExp r5 = new gnu.expr.BeginExp
            r5.<init>()
            r4.<init>((gnu.expr.Expression) r5)
            r2 = r4
            r2.setClassMethod(r1)
            gnu.bytecode.PrimType r1 = gnu.bytecode.Type.voidType
            r2.setReturnType(r1)
            if (r0 == 0) goto L_0x003d
            java.lang.String r1 = "$clinit$"
            r2.setName(r1)
            r8.clinitMethod = r2
            goto L_0x004e
        L_0x003d:
            java.lang.String r1 = "$finit$"
            r2.setName(r1)
            r8.initMethod = r2
            gnu.expr.Declaration r1 = new gnu.expr.Declaration
            java.lang.String r4 = gnu.expr.ThisExp.THIS_NAME
            r1.<init>((java.lang.Object) r4)
            r2.add(r3, r1)
        L_0x004e:
            gnu.expr.LambdaExp r1 = r8.firstChild
            r2.nextSibling = r1
            r8.firstChild = r2
        L_0x0054:
            r10.push((gnu.expr.ScopeExp) r2)
            gnu.expr.LambdaExp r1 = r10.curMethodLambda
            r10.curMethodLambda = r2
            gnu.expr.Expression r4 = r10.rewrite_car((gnu.lists.Pair) r9, (kawa.lang.SyntaxForm) r11)
            boolean r5 = r7 instanceof gnu.expr.Declaration
            if (r5 == 0) goto L_0x0073
            r5 = r7
            gnu.expr.Declaration r5 = (gnu.expr.Declaration) r5
            gnu.expr.SetExp r6 = new gnu.expr.SetExp
            r6.<init>((gnu.expr.Declaration) r5, (gnu.expr.Expression) r4)
            r6.setLocation(r5)
            r5.noteValue(r3)
            r3 = r6
            goto L_0x007e
        L_0x0073:
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp
            gnu.bytecode.PrimType r5 = gnu.bytecode.Type.voidType
            r3.<init>(r5)
            gnu.expr.ApplyExp r3 = gnu.expr.Compilation.makeCoercion((gnu.expr.Expression) r4, (gnu.expr.Expression) r3)
        L_0x007e:
            gnu.expr.Expression r4 = r2.body
            gnu.expr.BeginExp r4 = (gnu.expr.BeginExp) r4
            r4.add(r3)
            r10.curMethodLambda = r1
            r10.pop(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteInit(java.lang.Object, gnu.expr.ClassExp, gnu.lists.Pair, kawa.lang.Translator, kawa.lang.SyntaxForm):void");
    }

    static boolean matches(Object exp, String tag, Translator tr) {
        String value;
        if (exp instanceof Keyword) {
            value = ((Keyword) exp).getName();
        } else if (exp instanceof FString) {
            value = ((FString) exp).toString();
        } else {
            if (exp instanceof Pair) {
                Object matchQuoted = tr.matchQuoted((Pair) exp);
                Object qvalue = matchQuoted;
                if (matchQuoted instanceof SimpleSymbol) {
                    value = qvalue.toString();
                }
            }
            return false;
        }
        if (tag == null || tag.equals(value)) {
            return true;
        }
        return false;
    }

    static long addAccessFlags(Object value, long previous, long allowed, String kind, Translator tr) {
        long flags = matchAccess(value, tr);
        if (flags == 0) {
            tr.error('e', "unknown access specifier " + value);
        } else if (((-1 ^ allowed) & flags) != 0) {
            tr.error('e', "invalid " + kind + " access specifier " + value);
        } else if ((previous & flags) != 0) {
            tr.error('w', "duplicate " + kind + " access specifiers " + value);
        }
        return previous | flags;
    }

    static long matchAccess(Object value, Translator tr) {
        while (value instanceof SyntaxForm) {
            value = ((SyntaxForm) value).getDatum();
        }
        if (value instanceof Pair) {
            Pair pair = (Pair) value;
            value = tr.matchQuoted((Pair) value);
            if (value instanceof Pair) {
                return matchAccess2((Pair) value, tr);
            }
        }
        return matchAccess1(value, tr);
    }

    private static long matchAccess2(Pair pair, Translator tr) {
        long icar = matchAccess1(pair.getCar(), tr);
        Object cdr = pair.getCdr();
        if (cdr == LList.Empty || icar == 0) {
            return icar;
        }
        if (cdr instanceof Pair) {
            long icdr = matchAccess2((Pair) cdr, tr);
            if (icdr != 0) {
                return icar | icdr;
            }
        }
        return 0;
    }

    private static long matchAccess1(Object value, Translator tr) {
        if (value instanceof Keyword) {
            value = ((Keyword) value).getName();
        } else if (value instanceof FString) {
            value = ((FString) value).toString();
        } else if (value instanceof SimpleSymbol) {
            value = value.toString();
        }
        if ("private".equals(value)) {
            return 16777216;
        }
        if ("protected".equals(value)) {
            return 33554432;
        }
        if ("public".equals(value)) {
            return 67108864;
        }
        if ("package".equals(value)) {
            return 134217728;
        }
        if ("volatile".equals(value)) {
            return Declaration.VOLATILE_ACCESS;
        }
        if ("transient".equals(value)) {
            return Declaration.TRANSIENT_ACCESS;
        }
        if ("enum".equals(value)) {
            return Declaration.ENUM_ACCESS;
        }
        if ("final".equals(value)) {
            return Declaration.FINAL_ACCESS;
        }
        return 0;
    }
}
