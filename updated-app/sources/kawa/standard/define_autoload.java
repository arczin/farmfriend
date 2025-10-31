package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.mapping.Symbol;
import gnu.text.SyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import kawa.lang.AutoloadProcedure;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_autoload extends Syntax {
    public static final define_autoload define_autoload = new define_autoload("define-autoload", false);
    public static final define_autoload define_autoloads_from_file = new define_autoload("define-autoloads-from-file", true);
    boolean fromFile;

    public define_autoload(String name, boolean fromFile2) {
        super(name);
        this.fromFile = fromFile2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: gnu.lists.Pair} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean scanForDefinitions(gnu.lists.Pair r6, java.util.Vector r7, gnu.expr.ScopeExp r8, kawa.lang.Translator r9) {
        /*
            r5 = this;
            java.lang.Object r0 = r6.getCdr()
            boolean r0 = r0 instanceof gnu.lists.Pair
            if (r0 != 0) goto L_0x000d
            boolean r0 = super.scanForDefinitions(r6, r7, r8, r9)
            return r0
        L_0x000d:
            java.lang.Object r0 = r6.getCdr()
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            boolean r1 = r5.fromFile
            r2 = 0
            if (r1 == 0) goto L_0x004d
        L_0x0018:
            java.lang.Object r1 = r0.getCar()
            boolean r1 = r1 instanceof java.lang.CharSequence
            if (r1 != 0) goto L_0x0021
            goto L_0x003f
        L_0x0021:
            java.lang.Object r1 = r0.getCar()
            java.lang.String r1 = r1.toString()
            boolean r1 = r5.scanFile(r1, r8, r9)
            if (r1 != 0) goto L_0x0030
            return r2
        L_0x0030:
            java.lang.Object r1 = r0.getCdr()
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            if (r1 != r3) goto L_0x003a
            r2 = 1
            return r2
        L_0x003a:
            boolean r3 = r1 instanceof gnu.lists.Pair
            if (r3 != 0) goto L_0x0045
        L_0x003f:
            java.lang.String r1 = "invalid syntax for define-autoloads-from-file"
            r9.syntaxError(r1)
            return r2
        L_0x0045:
            java.lang.Object r3 = r0.getCdr()
            r0 = r3
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            goto L_0x0018
        L_0x004d:
            java.lang.Object r1 = r0.getCar()
            r3 = 0
            java.lang.Object r4 = r0.getCdr()
            boolean r4 = r4 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x006a
            java.lang.Object r2 = r0.getCdr()
            r0 = r2
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            java.lang.Object r2 = r0.getCar()
            boolean r3 = process(r1, r2, r7, r8, r9)
            return r3
        L_0x006a:
            java.lang.String r4 = "invalid syntax for define-autoload"
            r9.syntaxError(r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.define_autoload.scanForDefinitions(gnu.lists.Pair, java.util.Vector, gnu.expr.ScopeExp, kawa.lang.Translator):boolean");
    }

    public boolean scanFile(String filespec, ScopeExp defs, Translator tr) {
        File file;
        String str = filespec;
        Translator translator = tr;
        str.endsWith(".el");
        File file2 = new File(str);
        if (!file2.isAbsolute()) {
            file = new File(new File(tr.getFileName()).getParent(), str);
        } else {
            file = file2;
        }
        String filename = file.getPath();
        int dot = filename.lastIndexOf(46);
        if (dot >= 0) {
            String extension = filename.substring(dot);
            Language language = Language.getInstance(extension);
            if (language == null) {
                translator.syntaxError("unknown extension for " + filename);
                return true;
            }
            String prefix = translator.classPrefix;
            String cname = str.substring(0, filespec.length() - extension.length());
            while (cname.startsWith("../")) {
                int i = prefix.lastIndexOf(46, prefix.length() - 2);
                if (i < 0) {
                    translator.syntaxError("cannot use relative filename \"" + str + "\" with simple prefix \"" + prefix + "\"");
                    return false;
                }
                prefix = prefix.substring(0, i + 1);
                cname = cname.substring(3);
            }
            try {
                try {
                    try {
                        findAutoloadComments((LispReader) language.getLexer(InPort.openFile(filename), tr.getMessages()), (prefix + cname).replace('/', '.'), defs, translator);
                        return true;
                    } catch (Exception e) {
                        ex = e;
                        translator.syntaxError("error reading " + filename + ": " + ex);
                        return true;
                    }
                } catch (Exception e2) {
                    ex = e2;
                    ScopeExp scopeExp = defs;
                    translator.syntaxError("error reading " + filename + ": " + ex);
                    return true;
                }
            } catch (Exception e3) {
                ex = e3;
                ScopeExp scopeExp2 = defs;
                translator.syntaxError("error reading " + filename + ": " + ex);
                return true;
            }
        } else {
            ScopeExp scopeExp3 = defs;
            return true;
        }
    }

    public static void findAutoloadComments(LispReader in, String filename, ScopeExp defs, Translator tr) throws IOException, SyntaxException {
        int i;
        Translator translator;
        LispReader lispReader = in;
        Translator translator2 = tr;
        boolean i2 = true;
        int magicLength = ";;;###autoload".length();
        while (true) {
            int ch = in.peek();
            if (ch >= 0) {
                if (ch == 10 || ch == 13) {
                    String str = filename;
                    ScopeExp scopeExp = defs;
                    int i3 = i;
                    in.read();
                    i2 = true;
                    translator2 = tr;
                } else {
                    if (i == 0 || ch != 59) {
                        String str2 = filename;
                        ScopeExp scopeExp2 = defs;
                        int i4 = i;
                    } else {
                        int i5 = 0;
                        while (true) {
                            if (i5 != magicLength) {
                                String str3 = filename;
                                ScopeExp scopeExp3 = defs;
                                int lineStart = i;
                                ch = in.read();
                                if (ch >= 0) {
                                    if (ch == 10 || ch == 13) {
                                        i2 = true;
                                        translator2 = tr;
                                    } else if (i5 >= 0) {
                                        int i6 = i5 + 1;
                                        if (ch == ";;;###autoload".charAt(i5)) {
                                            translator = tr;
                                            i5 = i6;
                                            i = lineStart;
                                        } else {
                                            i5 = -1;
                                            translator = tr;
                                            i = lineStart;
                                        }
                                    } else {
                                        translator = tr;
                                        i = lineStart;
                                    }
                                } else {
                                    return;
                                }
                            } else if (i5 > 0) {
                                Object form = in.readObject();
                                if (form instanceof Pair) {
                                    Pair pair = (Pair) form;
                                    Object value = null;
                                    String name = null;
                                    Object car = pair.getCar();
                                    if ((car instanceof String ? car.toString() : car instanceof Symbol ? ((Symbol) car).getName() : null) == "defun") {
                                        name = ((Pair) pair.getCdr()).getCar().toString();
                                        Object obj = form;
                                        value = new AutoloadProcedure(name, filename, tr.getLanguage());
                                    } else {
                                        Object form2 = filename;
                                        translator.error('w', "unsupported ;;;###autoload followed by: " + pair.getCar());
                                    }
                                    if (value != null) {
                                        Declaration decl = defs.getDefine(name, 'w', translator);
                                        Expression ex = new QuoteExp(value);
                                        int i7 = i;
                                        decl.setFlag(16384);
                                        decl.noteValue(ex);
                                        decl.setProcedureDecl(true);
                                        decl.setType(Compilation.typeProcedure);
                                    } else {
                                        ScopeExp scopeExp4 = defs;
                                        int i8 = i;
                                    }
                                } else {
                                    ScopeExp scopeExp5 = defs;
                                    int i9 = i;
                                    Object obj2 = form;
                                    Object form3 = filename;
                                }
                                i2 = false;
                                translator2 = tr;
                            } else {
                                String str4 = filename;
                                ScopeExp scopeExp6 = defs;
                                int i10 = i;
                            }
                        }
                        i2 = true;
                        translator2 = tr;
                    }
                    i2 = false;
                    in.skip();
                    if (ch == 35 && in.peek() == 124) {
                        in.skip();
                        lispReader.readNestedComment('#', '|');
                    } else if (!Character.isWhitespace((char) ch) && lispReader.readObject(ch) == Sequence.eofValue) {
                        return;
                    }
                    translator2 = tr;
                }
            } else {
                return;
            }
        }
    }

    public static boolean process(Object names, Object filename, Vector forms, ScopeExp defs, Translator tr) {
        if (names instanceof Pair) {
            Pair p = (Pair) names;
            if (!process(p.getCar(), filename, forms, defs, tr) || !process(p.getCdr(), filename, forms, defs, tr)) {
                return false;
            }
            return true;
        } else if (names == LList.Empty) {
            return true;
        } else {
            if (!(names instanceof String) && !(names instanceof Symbol)) {
                return false;
            }
            String name = names.toString();
            Declaration decl = defs.getDefine(name, 'w', tr);
            if (filename instanceof String) {
                String str = (String) filename;
                String fn = str;
                int length = str.length();
                int len = length;
                if (length > 2 && fn.charAt(0) == '<' && fn.charAt(len - 1) == '>') {
                    filename = fn.substring(1, len - 1);
                }
            }
            Expression ex = new QuoteExp(new AutoloadProcedure(name, filename.toString(), tr.getLanguage()));
            decl.setFlag(16384);
            decl.noteValue(ex);
            return true;
        }
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
