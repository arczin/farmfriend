package kawa.standard;

import gnu.expr.Keyword;
import gnu.expr.ScopeExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.text.Options;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class with_compile_options extends Syntax {
    public static final with_compile_options with_compile_options = new with_compile_options();

    static {
        with_compile_options.setName("with-compile-options");
    }

    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Stack stack = new Stack();
        Object rest = getOptions(form.getCdr(), stack, this, tr);
        if (rest != LList.Empty) {
            if (rest == form.getCdr()) {
                tr.scanBody(rest, defs, false);
                return;
            }
            Object rest2 = new Pair(stack, tr.scanBody(rest, defs, true));
            tr.currentOptions.popOptionValues(stack);
            tr.formStack.add(Translator.makePair(form, form.getCar(), rest2));
        }
    }

    public static Object getOptions(Object form, Stack stack, Syntax command, Translator tr) {
        boolean seenKey = false;
        Options options = tr.currentOptions;
        SyntaxForm syntax = null;
        while (true) {
            if (form instanceof SyntaxForm) {
                syntax = (SyntaxForm) form;
                form = syntax.getDatum();
            } else if (!(form instanceof Pair)) {
                break;
            } else {
                Pair pair = (Pair) form;
                Object pair_car = Translator.stripSyntax(pair.getCar());
                if (!(pair_car instanceof Keyword)) {
                    break;
                }
                String key = ((Keyword) pair_car).getName();
                seenKey = true;
                Object savePos = tr.pushPositionOf(pair);
                try {
                    Object form2 = pair.getCdr();
                    while (form2 instanceof SyntaxForm) {
                        syntax = (SyntaxForm) form2;
                        form2 = syntax.getDatum();
                    }
                    if (!(form2 instanceof Pair)) {
                        tr.error('e', "keyword " + key + " not followed by value");
                        LList lList = LList.Empty;
                        tr.popPositionOf(savePos);
                        return lList;
                    }
                    Pair pair2 = (Pair) form2;
                    Object value = Translator.stripSyntax(pair2.getCar());
                    form = pair2.getCdr();
                    Object oldValue = options.getLocal(key);
                    if (options.getInfo(key) == null) {
                        tr.error('w', "unknown compile option: " + key);
                    } else {
                        if (value instanceof FString) {
                            value = value.toString();
                        } else if (!(value instanceof Boolean)) {
                            if (!(value instanceof Number)) {
                                value = null;
                                tr.error('e', "invalid literal value for key " + key);
                            }
                        }
                        options.set(key, value, tr.getMessages());
                        if (stack != null) {
                            stack.push(key);
                            stack.push(oldValue);
                            stack.push(value);
                        }
                        tr.popPositionOf(savePos);
                    }
                } finally {
                    tr.popPositionOf(savePos);
                }
            }
        }
        if (!seenKey) {
            tr.error('e', "no option keyword in " + command.getName());
        }
        return Translator.wrapSyntax(form, syntax);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0039 A[Catch:{ all -> 0x004e }] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0035 A[Catch:{ all -> 0x004e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r8, kawa.lang.Translator r9) {
        /*
            r7 = this;
            java.lang.Object r0 = r8.getCdr()
            boolean r1 = r0 instanceof gnu.lists.Pair
            if (r1 == 0) goto L_0x0024
            r1 = r0
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            r2 = r1
            java.lang.Object r1 = r1.getCar()
            boolean r1 = r1 instanceof java.util.Stack
            if (r1 == 0) goto L_0x0024
            java.lang.Object r1 = r2.getCar()
            java.util.Stack r1 = (java.util.Stack) r1
            java.lang.Object r3 = r2.getCdr()
            gnu.text.Options r4 = r9.currentOptions
            r4.pushOptionValues(r1)
            goto L_0x002d
        L_0x0024:
            java.util.Stack r1 = new java.util.Stack
            r1.<init>()
            java.lang.Object r3 = getOptions(r0, r1, r7, r9)
        L_0x002d:
            gnu.expr.Expression r2 = r9.rewrite_body(r3)     // Catch:{ all -> 0x004e }
            boolean r4 = r2 instanceof gnu.expr.BeginExp     // Catch:{ all -> 0x004e }
            if (r4 == 0) goto L_0x0039
            r4 = r2
            gnu.expr.BeginExp r4 = (gnu.expr.BeginExp) r4     // Catch:{ all -> 0x004e }
            goto L_0x0044
        L_0x0039:
            gnu.expr.BeginExp r4 = new gnu.expr.BeginExp     // Catch:{ all -> 0x004e }
            r5 = 1
            gnu.expr.Expression[] r5 = new gnu.expr.Expression[r5]     // Catch:{ all -> 0x004e }
            r6 = 0
            r5[r6] = r2     // Catch:{ all -> 0x004e }
            r4.<init>(r5)     // Catch:{ all -> 0x004e }
        L_0x0044:
            r4.setCompileOptions(r1)     // Catch:{ all -> 0x004e }
            gnu.text.Options r5 = r9.currentOptions
            r5.popOptionValues(r1)
            return r4
        L_0x004e:
            r2 = move-exception
            gnu.text.Options r4 = r9.currentOptions
            r4.popOptionValues(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.with_compile_options.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
