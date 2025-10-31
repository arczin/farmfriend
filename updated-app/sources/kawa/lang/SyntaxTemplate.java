package kawa.lang;

import gnu.expr.Compilation;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Vector;

public class SyntaxTemplate implements Externalizable {
    static final int BUILD_CONS = 1;
    static final int BUILD_DOTS = 5;
    static final int BUILD_LIST1 = 8;
    static final int BUILD_LITERAL = 4;
    static final int BUILD_MISC = 0;
    static final int BUILD_NIL = 16;
    static final int BUILD_SYNTAX = 24;
    static final int BUILD_VAR = 2;
    static final int BUILD_VAR_CAR = 3;
    static final int BUILD_VECTOR = 40;
    static final int BUILD_WIDE = 7;
    static final String dots3 = "...";
    Object[] literal_values;
    int max_nesting;
    String patternNesting;
    String template_program;

    protected SyntaxTemplate() {
    }

    public SyntaxTemplate(String patternNesting2, String template_program2, Object[] literal_values2, int max_nesting2) {
        this.patternNesting = patternNesting2;
        this.template_program = template_program2;
        this.literal_values = literal_values2;
        this.max_nesting = max_nesting2;
    }

    public SyntaxTemplate(Object template, SyntaxForm syntax, Translator tr) {
        this.patternNesting = (tr == null || tr.patternScope == null) ? "" : tr.patternScope.patternNesting.toString();
        StringBuffer program = new StringBuffer();
        Vector literals_vector = new Vector();
        convert_template(template, syntax, program, 0, literals_vector, new IdentityHashMap(), false, tr);
        this.template_program = program.toString();
        this.literal_values = new Object[literals_vector.size()];
        literals_vector.copyInto(this.literal_values);
    }

    public int convert_template(Object form, SyntaxForm syntax, StringBuffer template_program2, int nesting, Vector literals_vector, Object seen, boolean isVector, Translator tr) {
        Object obj;
        Object obj2;
        int pattern_var_num;
        int ret_car;
        int ret_cdr;
        StringBuffer stringBuffer = template_program2;
        int i = nesting;
        Vector vector = literals_vector;
        Translator translator = tr;
        Object form2 = form;
        SyntaxForm syntax2 = syntax;
        while (form2 instanceof SyntaxForm) {
            syntax2 = (SyntaxForm) form2;
            form2 = syntax2.getDatum();
        }
        if ((form2 instanceof Pair) || (form2 instanceof FVector)) {
            IdentityHashMap seen_map = (IdentityHashMap) seen;
            if (seen_map.containsKey(form2)) {
                translator.syntaxError("self-referential (cyclic) syntax template");
                return -2;
            }
            seen_map.put(form2, form2);
        }
        if (form2 instanceof Pair) {
            Pair pair = (Pair) form2;
            int save_pc = template_program2.length();
            Object car = pair.getCar();
            if (translator.matches(car, dots3)) {
                Object cdr = Translator.stripSyntax(pair.getCdr());
                if (cdr instanceof Pair) {
                    Pair cdr_pair = (Pair) cdr;
                    if (cdr_pair.getCar() == dots3 && cdr_pair.getCdr() == LList.Empty) {
                        form2 = dots3;
                        obj = dots3;
                    }
                }
            }
            int save_literals = literals_vector.size();
            stringBuffer.append(8);
            int num_dots3 = 0;
            Object rest = pair.getCdr();
            while ((rest instanceof Pair) != 0) {
                Pair p = (Pair) rest;
                if (!translator.matches(p.getCar(), dots3)) {
                    break;
                }
                num_dots3++;
                rest = p.getCdr();
                stringBuffer.append(5);
            }
            int save_literals2 = save_literals;
            Object obj3 = car;
            Object form3 = form2;
            int save_pc2 = save_pc;
            obj = dots3;
            int ret_car2 = convert_template(car, syntax2, template_program2, i + num_dots3, literals_vector, seen, false, tr);
            Object rest2 = rest;
            if (rest2 != LList.Empty) {
                stringBuffer.setCharAt(save_pc2, (char) ((((template_program2.length() - save_pc2) - 1) << 3) + 1));
                Object obj4 = rest2;
                ret_car = ret_car2;
                ret_cdr = convert_template(rest2, syntax2, template_program2, nesting, literals_vector, seen, isVector, tr);
            } else {
                ret_car = ret_car2;
                ret_cdr = -2;
            }
            if (num_dots3 > 0) {
                if (ret_car < 0) {
                    translator.syntaxError("... follows template with no suitably-nested pattern variable");
                }
                int i2 = num_dots3;
                while (true) {
                    i2--;
                    if (i2 < 0) {
                        break;
                    }
                    stringBuffer.setCharAt(save_pc2 + i2 + 1, (char) ((ret_car << 3) + 5));
                    int n = i + num_dots3;
                    if (n >= this.max_nesting) {
                        this.max_nesting = n;
                    }
                }
            }
            if (ret_car >= 0) {
                return ret_car;
            }
            if (ret_cdr >= 0) {
                return ret_cdr;
            }
            if (ret_car == -1) {
                Vector vector2 = literals_vector;
                int i3 = save_literals2;
                return -1;
            } else if (ret_cdr == -1) {
                Vector vector3 = literals_vector;
                int i4 = save_literals2;
                return -1;
            } else if (isVector) {
                return -2;
            } else {
                Vector vector4 = literals_vector;
                vector4.setSize(save_literals2);
                stringBuffer.setLength(save_pc2);
                form2 = form3;
                vector = vector4;
            }
        } else {
            obj = dots3;
            Vector vector5 = vector;
            Object obj5 = form2;
            if (form2 instanceof FVector) {
                stringBuffer.append('(');
                Vector vector6 = vector5;
                return convert_template(LList.makeList((FVector) form2), syntax2, template_program2, nesting, literals_vector, seen, true, tr);
            }
            vector = vector5;
            if (form2 == LList.Empty) {
                stringBuffer.append(16);
                return -2;
            } else if ((form2 instanceof Symbol) && translator != null && translator.patternScope != null && (pattern_var_num = indexOf(translator.patternScope.pattern_names, form2)) >= 0) {
                int var_nesting = this.patternNesting.charAt(pattern_var_num);
                int op = (var_nesting & 1) != 0 ? 3 : 2;
                int var_nesting2 = var_nesting >> 1;
                if (var_nesting2 > i) {
                    translator.syntaxError("inconsistent ... nesting of " + form2);
                }
                stringBuffer.append((char) ((pattern_var_num * 8) + op));
                if (var_nesting2 == i) {
                    return pattern_var_num;
                }
                return -1;
            }
        }
        int literals_index = indexOf(vector, form2);
        if (literals_index < 0) {
            literals_index = literals_vector.size();
            vector.addElement(form2);
        }
        if (form2 instanceof Symbol) {
            translator.noteAccess(form2, tr.currentScope());
        }
        if (!(form2 instanceof SyntaxForm)) {
            obj2 = obj;
            if (form2 != obj2) {
                stringBuffer.append(24);
            }
        } else {
            obj2 = obj;
        }
        stringBuffer.append((char) ((literals_index * 8) + 4));
        if (form2 == obj2) {
            return -1;
        }
        return -2;
    }

    static int indexOf(Vector vec, Object elem) {
        int len = vec.size();
        for (int i = 0; i < len; i++) {
            if (vec.elementAt(i) == elem) {
                return i;
            }
        }
        return -1;
    }

    private int get_count(Object var, int nesting, int[] indexes) {
        for (int level = 0; level < nesting; level++) {
            var = var[indexes[level]];
        }
        return var.length;
    }

    public Object execute(Object[] vars, TemplateScope templateScope) {
        return execute(0, vars, 0, new int[this.max_nesting], (Translator) Compilation.getCurrent(), templateScope);
    }

    public Object execute(Object[] vars, Translator tr, TemplateScope templateScope) {
        return execute(0, vars, 0, new int[this.max_nesting], tr, templateScope);
    }

    /* access modifiers changed from: package-private */
    public Object get_var(int var_num, Object[] vars, int[] indexes) {
        Object var = vars[var_num];
        if (var_num < this.patternNesting.length()) {
            int var_nesting = this.patternNesting.charAt(var_num) >> 1;
            for (int level = 0; level < var_nesting; level++) {
                var = var[indexes[level]];
            }
        }
        return var;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: gnu.lists.LList} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.lists.LList executeToList(int r20, java.lang.Object[] r21, int r22, int[] r23, kawa.lang.Translator r24, kawa.lang.TemplateScope r25) {
        /*
            r19 = this;
            r14 = r19
            r15 = r21
            r13 = r22
            r12 = r23
            r8 = r20
            java.lang.String r0 = r14.template_program
            r1 = r20
            char r0 = r0.charAt(r1)
            r16 = r0
        L_0x0014:
            r0 = r16 & 7
            r2 = 7
            if (r0 != r2) goto L_0x0028
            int r0 = r16 + -7
            int r0 = r0 << 13
            java.lang.String r2 = r14.template_program
            int r1 = r1 + 1
            char r2 = r2.charAt(r1)
            r16 = r0 | r2
            goto L_0x0014
        L_0x0028:
            r0 = r16 & 7
            r2 = 3
            if (r0 != r2) goto L_0x0040
            int r0 = r16 >> 3
            java.lang.Object r0 = r14.get_var(r0, r15, r12)
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            java.lang.Object r2 = r0.getCar()
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            gnu.lists.Pair r2 = kawa.lang.Translator.makePair(r0, r2, r3)
            return r2
        L_0x0040:
            r0 = r16 & 7
            r2 = 5
            if (r0 != r2) goto L_0x0091
            int r7 = r16 >> 3
            r9 = r15[r7]
            int r10 = r14.get_count(r9, r13, r12)
            gnu.lists.LList r0 = gnu.lists.LList.Empty
            r2 = 0
            int r11 = r1 + 1
            r1 = 0
            r17 = r0
            r6 = r1
            r5 = r2
        L_0x0057:
            if (r6 >= r10) goto L_0x0090
            r12[r13] = r6
            int r3 = r13 + 1
            r0 = r19
            r1 = r11
            r2 = r21
            r4 = r23
            r20 = r7
            r7 = r5
            r5 = r24
            r18 = r6
            r6 = r25
            gnu.lists.LList r0 = r0.executeToList(r1, r2, r3, r4, r5, r6)
            if (r7 != 0) goto L_0x0078
            r1 = r0
            r17 = r1
            r5 = r7
            goto L_0x007c
        L_0x0078:
            r7.setCdrBackdoor(r0)
            r5 = r7
        L_0x007c:
            boolean r1 = r0 instanceof gnu.lists.Pair
            if (r1 == 0) goto L_0x008b
            r5 = r0
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r1 = r5.getCdr()
            r0 = r1
            gnu.lists.LList r0 = (gnu.lists.LList) r0
            goto L_0x007c
        L_0x008b:
            int r6 = r18 + 1
            r7 = r20
            goto L_0x0057
        L_0x0090:
            return r17
        L_0x0091:
            r7 = r19
            r9 = r21
            r10 = r22
            r11 = r23
            r12 = r24
            r13 = r25
            java.lang.Object r0 = r7.execute(r8, r9, r10, r11, r12, r13)
            gnu.lists.Pair r2 = new gnu.lists.Pair
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            r2.<init>(r0, r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxTemplate.executeToList(int, java.lang.Object[], int, int[], kawa.lang.Translator, kawa.lang.TemplateScope):gnu.lists.LList");
    }

    /* access modifiers changed from: package-private */
    public Object execute(int pc, Object[] vars, int nesting, int[] indexes, Translator tr, TemplateScope templateScope) {
        int pc2;
        int i = pc;
        int ch = this.template_program.charAt(i);
        int pc3 = i;
        while ((ch & 7) == 7) {
            pc3++;
            ch = ((ch - 7) << 13) | this.template_program.charAt(pc3);
        }
        if (ch == 8) {
            return executeToList(pc3 + 1, vars, nesting, indexes, tr, templateScope);
        } else if (ch == 16) {
            return LList.Empty;
        } else {
            if (ch == 24) {
                Object v = execute(pc3 + 1, vars, nesting, indexes, tr, templateScope);
                return v == LList.Empty ? v : SyntaxForms.makeForm(v, templateScope);
            }
            TemplateScope templateScope2 = templateScope;
            if ((ch & 7) == 1) {
                int ch2 = ch;
                Object result = null;
                int i2 = pc3;
                Pair p = null;
                int pc4 = i2;
                while (true) {
                    int pc5 = pc4 + 1;
                    Object q = executeToList(pc5, vars, nesting, indexes, tr, templateScope);
                    if (p == null) {
                        result = q;
                    } else {
                        p.setCdrBackdoor(q);
                    }
                    while (q instanceof Pair) {
                        p = (Pair) q;
                        q = p.getCdr();
                    }
                    pc2 = pc5 + (ch2 >> 3);
                    ch2 = this.template_program.charAt(pc2);
                    if ((ch2 & 7) != 1) {
                        break;
                    }
                    pc4 = pc2;
                }
                Object cdr = execute(pc2, vars, nesting, indexes, tr, templateScope);
                if (p == null) {
                    return cdr;
                }
                p.setCdrBackdoor(cdr);
                return result;
            } else if (ch == 40) {
                return new FVector((List) (LList) execute(pc3 + 1, vars, nesting, indexes, tr, templateScope));
            } else if ((ch & 7) == 4) {
                return this.literal_values[ch >> 3];
            } else if ((ch & 6) == 2) {
                Object var = get_var(ch >> 3, vars, indexes);
                if ((ch & 7) == 3) {
                    return ((Pair) var).getCar();
                }
                return var;
            } else {
                Object[] objArr = vars;
                int[] iArr = indexes;
                throw new Error("unknown template code: " + ch + " at " + pc3);
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.patternNesting);
        out.writeObject(this.template_program);
        out.writeObject(this.literal_values);
        out.writeInt(this.max_nesting);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.patternNesting = (String) in.readObject();
        this.template_program = (String) in.readObject();
        this.literal_values = (Object[]) in.readObject();
        this.max_nesting = in.readInt();
    }
}
