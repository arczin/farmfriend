package gnu.xquery.lang;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ResolveNames;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.functions.GetModuleClass;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.mapping.WrongArguments;
import gnu.xml.NamespaceBinding;
import gnu.xquery.util.NamedCollator;
import kawa.standard.Scheme;

public class XQResolveNames extends ResolveNames {
    public static final int BASE_URI_BUILTIN = -11;
    public static final int CASTABLE_AS_BUILTIN = -34;
    public static final int CAST_AS_BUILTIN = -33;
    public static final int COLLECTION_BUILTIN = -8;
    public static final int COMPARE_BUILTIN = -4;
    public static final int DEEP_EQUAL_BUILTIN = -25;
    public static final int DEFAULT_COLLATION_BUILTIN = -29;
    public static final int DISTINCT_VALUES_BUILTIN = -5;
    public static final int DOC_AVAILABLE_BUILTIN = -10;
    public static final int DOC_BUILTIN = -9;
    public static final int HANDLE_EXTENSION_BUILTIN = -3;
    public static final int IDREF_BUILTIN = -31;
    public static final int ID_BUILTIN = -30;
    public static final int INDEX_OF_BUILTIN = -15;
    public static final int LANG_BUILTIN = -23;
    public static final int LAST_BUILTIN = -1;
    public static final int LOCAL_NAME_BUILTIN = -6;
    public static final int MAX_BUILTIN = -27;
    public static final int MIN_BUILTIN = -26;
    public static final int NAMESPACE_URI_BUILTIN = -7;
    public static final int NAME_BUILTIN = -24;
    public static final int NORMALIZE_SPACE_BUILTIN = -17;
    public static final int NUMBER_BUILTIN = -28;
    public static final int POSITION_BUILTIN = -2;
    public static final int RESOLVE_PREFIX_BUILTIN = -13;
    public static final int RESOLVE_URI_BUILTIN = -12;
    public static final int ROOT_BUILTIN = -32;
    public static final int STATIC_BASE_URI_BUILTIN = -14;
    public static final int STRING_BUILTIN = -16;
    public static final int UNORDERED_BUILTIN = -18;
    public static final int XS_QNAME_BUILTIN = -35;
    public static final int XS_QNAME_IGNORE_DEFAULT_BUILTIN = -36;
    public static final Declaration castAsDecl = makeBuiltin("(cast as)", -33);
    public static final Declaration castableAsDecl = makeBuiltin("(castable as)", -34);
    public static final Declaration handleExtensionDecl = makeBuiltin("(extension)", -3);
    public static final Declaration lastDecl = makeBuiltin("last", -1);
    public static final Declaration resolvePrefixDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "(resolve-prefix)"), -13);
    public static final Declaration staticBaseUriDecl = makeBuiltin("static-base-uri", -14);
    public static final Declaration xsQNameDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "QName"), -35);
    public static final Declaration xsQNameIgnoreDefaultDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "(QName-ignore-default)"), -36);
    public Namespace[] functionNamespacePath;
    private Declaration moduleDecl;
    public XQParser parser;

    public static Declaration makeBuiltin(String name, int code) {
        return makeBuiltin(Symbol.make(XQuery.XQUERY_FUNCTION_NAMESPACE, name, "fn"), code);
    }

    public static Declaration makeBuiltin(Symbol name, int code) {
        Declaration decl = new Declaration((Object) name);
        decl.setProcedureDecl(true);
        decl.setCode(code);
        return decl;
    }

    public XQResolveNames() {
        this((Compilation) null);
    }

    /* access modifiers changed from: package-private */
    public void pushBuiltin(String name, int code) {
        this.lookup.push(makeBuiltin(name, code));
    }

    public XQResolveNames(Compilation comp) {
        super(comp);
        this.functionNamespacePath = XQuery.defaultFunctionNamespacePath;
        this.lookup.push(lastDecl);
        this.lookup.push(xsQNameDecl);
        this.lookup.push(staticBaseUriDecl);
        pushBuiltin("position", -2);
        pushBuiltin("compare", -4);
        pushBuiltin("distinct-values", -5);
        pushBuiltin("local-name", -6);
        pushBuiltin("name", -24);
        pushBuiltin("namespace-uri", -7);
        pushBuiltin("root", -32);
        pushBuiltin("base-uri", -11);
        pushBuiltin("lang", -23);
        pushBuiltin("resolve-uri", -12);
        pushBuiltin("collection", -8);
        pushBuiltin("doc", -9);
        pushBuiltin("document", -9);
        pushBuiltin("doc-available", -10);
        pushBuiltin("index-of", -15);
        pushBuiltin(PropertyTypeConstants.PROPERTY_TYPE_STRING, -16);
        pushBuiltin("normalize-space", -17);
        pushBuiltin("unordered", -18);
        pushBuiltin("deep-equal", -25);
        pushBuiltin("min", -26);
        pushBuiltin("max", -27);
        pushBuiltin("number", -28);
        pushBuiltin("default-collation", -29);
        pushBuiltin("id", -30);
        pushBuiltin("idref", -31);
    }

    /* access modifiers changed from: protected */
    public void push(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            push(decl);
        }
    }

    /* access modifiers changed from: package-private */
    public void push(Declaration decl) {
        Compilation comp = getCompilation();
        Object name = decl.getSymbol();
        boolean function = decl.isProcedureDecl();
        if (name instanceof String) {
            if (decl.getLineNumber() <= 0 || comp == null) {
                name = this.parser.namespaceResolve((String) name, function);
            } else {
                String saveFilename = comp.getFileName();
                int saveLine = comp.getLineNumber();
                int saveColumn = comp.getColumnNumber();
                comp.setLocation(decl);
                name = this.parser.namespaceResolve((String) name, function);
                comp.setLine(saveFilename, saveLine, saveColumn);
            }
            if (name != null) {
                decl.setName(name);
            } else {
                return;
            }
        }
        Declaration old = this.lookup.lookup(name, XQuery.instance.getNamespaceOf(decl));
        if (old != null) {
            if (decl.context == old.context) {
                ScopeExp.duplicateDeclarationError(old, decl, comp);
            } else if (XQParser.warnHidePreviousDeclaration && (!(name instanceof Symbol) || ((Symbol) name).getNamespace() != null)) {
                comp.error('w', decl, "declaration ", " hides previous declaration");
            }
        }
        this.lookup.push(decl);
    }

    /* access modifiers changed from: package-private */
    public Declaration flookup(Symbol sym) {
        Declaration decl;
        Location loc = XQuery.xqEnvironment.lookup(sym, EnvironmentKey.FUNCTION);
        if (loc == null) {
            return null;
        }
        Location loc2 = loc.getBase();
        if ((loc2 instanceof StaticFieldLocation) && (decl = ((StaticFieldLocation) loc2).getDeclaration()) != null) {
            return decl;
        }
        Object val = loc2.get((Object) null);
        if (val != null) {
            return procToDecl(sym, val);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Expression visitReferenceExp(ReferenceExp exp, Void ignored) {
        ApplyExp applyExp = null;
        return visitReferenceExp(exp, (ApplyExp) null);
    }

    /* access modifiers changed from: protected */
    public Expression visitReferenceExp(ReferenceExp exp, ApplyExp call) {
        Symbol sym;
        String mname;
        if (exp.getBinding() == null) {
            Object symbol = exp.getSymbol();
            boolean needFunction = exp.isProcedureName();
            boolean needType = exp.getFlag(16);
            int namespace = call == null ? 1 : XQuery.namespaceForFunctions(call.getArgCount());
            Declaration decl = this.lookup.lookup(symbol, namespace);
            if (decl == null) {
                if (symbol instanceof Symbol) {
                    Symbol symbol2 = (Symbol) symbol;
                    Symbol sym2 = symbol2;
                    if ("".equals(symbol2.getNamespaceURI())) {
                        String name = sym2.getLocalName();
                        if ("request".equals(name)) {
                            mname = "getCurrentRequest";
                        } else if ("response".equals(name)) {
                            mname = "getCurrentResponse";
                        } else {
                            mname = null;
                        }
                        if (mname != null) {
                            return new ApplyExp(ClassType.make("gnu.kawa.servlet.ServletRequestContext").getDeclaredMethod(mname, 0), Expression.noExpressions);
                        }
                    }
                }
                if (symbol instanceof Symbol) {
                    decl = flookup((Symbol) symbol);
                } else {
                    String name2 = (String) symbol;
                    if (name2.indexOf(58) < 0) {
                        name2 = name2.intern();
                        if (needFunction) {
                            int i = 0;
                            while (i < this.functionNamespacePath.length && (decl = this.lookup.lookup((Object) (sym = this.functionNamespacePath[i].getSymbol(name2)), namespace)) == null && (decl = flookup(sym)) == null) {
                                i++;
                            }
                        }
                    }
                    if (decl == null && (sym = this.parser.namespaceResolve(name2, needFunction)) != null && (decl = this.lookup.lookup((Object) sym, namespace)) == null && (needFunction || needType)) {
                        String uri = sym.getNamespaceURI();
                        Type type = null;
                        if (XQuery.SCHEMA_NAMESPACE.equals(uri)) {
                            type = XQuery.getStandardType(sym.getName());
                        } else if (needType && uri == "" && !getCompilation().isPedantic()) {
                            type = Scheme.string2Type(sym.getName());
                        }
                        if (type != null) {
                            return new QuoteExp(type).setLine((Expression) exp);
                        }
                        if (uri != null && uri.length() > 6 && uri.startsWith("class:")) {
                            return CompileNamedPart.makeExp((Type) ClassType.make(uri.substring(6)), sym.getName());
                        }
                        decl = flookup(sym);
                    }
                }
            }
            if (decl != null) {
                exp.setBinding(decl);
            } else if (needFunction) {
                error('e', "unknown function " + symbol);
            } else if (needType) {
                this.messages.error('e', exp, "unknown type " + symbol, "XPST0051");
            } else {
                this.messages.error('e', exp, "unknown variable $" + symbol, "XPST0008");
            }
        }
        return exp;
    }

    /* access modifiers changed from: protected */
    public Expression visitSetExp(SetExp exp, Void ignored) {
        Expression result = super.visitSetExp(exp, ignored);
        Declaration decl = exp.getBinding();
        if (decl != null && !getCompilation().immediate) {
            Object symbol = decl.getSymbol();
            Object name = symbol;
            if ((symbol instanceof Symbol) && XQuery.LOCAL_NAMESPACE.equals(((Symbol) name).getNamespaceURI())) {
                Expression newValue = exp.getNewValue();
                Expression new_value = newValue;
                if (!(newValue instanceof ApplyExp) || ((ApplyExp) new_value).getFunction() != XQParser.getExternalFunction) {
                    decl.setFlag(16777216);
                    decl.setPrivate(true);
                }
            }
        }
        return result;
    }

    private Expression visitStatements(Expression exp) {
        if (exp instanceof BeginExp) {
            BeginExp bbody = (BeginExp) exp;
            Expression[] exps = bbody.getExpressions();
            int nexps = bbody.getExpressionCount();
            for (int i = 0; i < nexps; i++) {
                exps[i] = visitStatements(exps[i]);
            }
            return exp;
        } else if (!(exp instanceof SetExp)) {
            return (Expression) visit(exp, null);
        } else {
            Declaration decl = this.moduleDecl;
            SetExp sexp = (SetExp) exp;
            Expression exp2 = visitSetExp(sexp, (Void) null);
            if (sexp.isDefining() && sexp.getBinding() == decl) {
                if (!decl.isProcedureDecl()) {
                    push(decl);
                }
                decl = decl.nextDecl();
            }
            this.moduleDecl = decl;
            return exp2;
        }
    }

    public void resolveModule(ModuleExp exp) {
        this.currentLambda = exp;
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.isProcedureDecl()) {
                push(decl);
            }
        }
        this.moduleDecl = exp.firstDecl();
        exp.body = visitStatements(exp.body);
        for (Declaration decl2 = exp.firstDecl(); decl2 != null; decl2 = decl2.nextDecl()) {
            if (decl2.getSymbol() != null) {
                this.lookup.removeSubsumed(decl2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Expression getCollator(Expression[] args, int argno) {
        if (args == null || args.length <= argno) {
            NamedCollator coll = this.parser.defaultCollator;
            return coll == null ? QuoteExp.nullExp : new QuoteExp(coll);
        }
        return new ApplyExp(ClassType.make("gnu.xquery.util.NamedCollator").getDeclaredMethod("find", 1), args[argno]);
    }

    /* access modifiers changed from: package-private */
    public Expression withCollator(Method method, Expression[] args, String name, int minArgs) {
        return withCollator((Expression) new QuoteExp(new PrimProcedure(method)), args, name, minArgs);
    }

    /* access modifiers changed from: package-private */
    public Expression withCollator(Expression function, Expression[] args, String name, int minArgs) {
        String err = WrongArguments.checkArgCount(name, minArgs, minArgs + 1, args.length);
        if (err != null) {
            return getCompilation().syntaxError(err);
        }
        Expression[] xargs = new Expression[(minArgs + 1)];
        System.arraycopy(args, 0, xargs, 0, minArgs);
        xargs[minArgs] = getCollator(args, minArgs);
        return new ApplyExp(function, xargs);
    }

    /* access modifiers changed from: package-private */
    public Expression withContext(Method method, Expression[] args, String name, int minArgs) {
        String err = WrongArguments.checkArgCount(name, minArgs, minArgs + 1, args.length);
        if (err != null) {
            return getCompilation().syntaxError(err);
        }
        if (args.length == minArgs) {
            Expression[] xargs = new Expression[(minArgs + 1)];
            System.arraycopy(args, 0, xargs, 0, minArgs);
            Declaration dot = this.lookup.lookup((Object) XQParser.DOT_VARNAME, false);
            if (dot == null) {
                String message = "undefined context for " + name;
                this.messages.error('e', message, "XPDY0002");
                return new ErrorExp(message);
            }
            xargs[minArgs] = new ReferenceExp(dot);
            args = xargs;
        }
        return new ApplyExp(method, args);
    }

    private Expression checkArgCount(Expression[] args, Declaration decl, int min, int max) {
        String err = WrongArguments.checkArgCount("fn:" + decl.getName(), min, max, args.length);
        if (err == null) {
            return null;
        }
        return getCompilation().syntaxError(err);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x041f  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0422  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0441  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x0453  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x0465  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression visitApplyExp(gnu.expr.ApplyExp r18, java.lang.Void r19) {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            r2 = r19
            gnu.expr.Expression r3 = r18.getFunction()
            gnu.xquery.lang.XQParser r4 = r1.parser
            gnu.xml.NamespaceBinding r4 = r4.constructorNamespaces
            java.lang.Object r5 = r18.getFunctionValue()
            boolean r6 = r5 instanceof gnu.kawa.xml.MakeElement
            if (r6 == 0) goto L_0x0027
            gnu.kawa.xml.MakeElement r5 = (gnu.kawa.xml.MakeElement) r5
            gnu.xml.NamespaceBinding r6 = r5.getNamespaceNodes()
            gnu.xml.NamespaceBinding r6 = gnu.xml.NamespaceBinding.nconc(r6, r4)
            r5.setNamespaceNodes(r6)
            gnu.xquery.lang.XQParser r5 = r1.parser
            r5.constructorNamespaces = r6
        L_0x0027:
            boolean r5 = r3 instanceof gnu.expr.ReferenceExp
            if (r5 == 0) goto L_0x0032
            gnu.expr.ReferenceExp r3 = (gnu.expr.ReferenceExp) r3
            gnu.expr.Expression r3 = r1.visitReferenceExp((gnu.expr.ReferenceExp) r3, (gnu.expr.ApplyExp) r0)
            goto L_0x0038
        L_0x0032:
            java.lang.Object r3 = r1.visit(r3, r2)
            gnu.expr.Expression r3 = (gnu.expr.Expression) r3
        L_0x0038:
            r0.setFunction(r3)
            gnu.expr.Expression[] r3 = r18.getArgs()
            r1.visitExps(r3, r2)
            gnu.xquery.lang.XQParser r3 = r1.parser
            r3.constructorNamespaces = r4
            gnu.expr.Expression r3 = r18.getFunction()
            boolean r4 = r3 instanceof gnu.expr.ReferenceExp
            java.lang.String r5 = "castAs"
            java.lang.String r6 = "gnu.xquery.util.CastAs"
            r8 = 101(0x65, float:1.42E-43)
            r9 = 2
            r10 = 0
            r11 = 1
            if (r4 == 0) goto L_0x0510
            gnu.expr.ReferenceExp r3 = (gnu.expr.ReferenceExp) r3
            gnu.expr.Declaration r3 = r3.getBinding()
            if (r3 == 0) goto L_0x050e
            int r4 = r3.getCode()
            if (r4 >= 0) goto L_0x050e
            java.lang.String r12 = "gnu.xquery.util.StringUtils"
            java.lang.String r13 = "gnu.xquery.util.SequenceUtils"
            java.lang.String r14 = "gnu.xquery.util.MinMax"
            java.lang.String r15 = "gnu.xquery.util.QNameUtils"
            r7 = 3
            java.lang.String r16 = "gnu.xquery.util.NodeUtils"
            switch(r4) {
                case -36: goto L_0x049a;
                case -35: goto L_0x049a;
                case -34: goto L_0x03ed;
                case -33: goto L_0x03ed;
                case -32: goto L_0x03d8;
                case -31: goto L_0x03c3;
                case -30: goto L_0x03ae;
                case -29: goto L_0x0391;
                case -28: goto L_0x037a;
                case -27: goto L_0x0365;
                case -26: goto L_0x0350;
                case -25: goto L_0x033b;
                case -24: goto L_0x0326;
                case -23: goto L_0x0311;
                case -22: goto L_0x0073;
                case -21: goto L_0x0073;
                case -20: goto L_0x0073;
                case -19: goto L_0x0073;
                case -18: goto L_0x0303;
                case -17: goto L_0x02ee;
                case -16: goto L_0x02d7;
                case -15: goto L_0x02c1;
                case -14: goto L_0x02b1;
                case -13: goto L_0x0225;
                case -12: goto L_0x01f6;
                case -11: goto L_0x01e1;
                case -10: goto L_0x018a;
                case -9: goto L_0x018a;
                case -8: goto L_0x0158;
                case -7: goto L_0x0143;
                case -6: goto L_0x012e;
                case -5: goto L_0x0117;
                case -4: goto L_0x0102;
                case -3: goto L_0x00aa;
                case -2: goto L_0x0076;
                case -1: goto L_0x0076;
                default: goto L_0x0073;
            }
        L_0x0073:
            r10 = 0
            goto L_0x0511
        L_0x0076:
            r0 = -1
            if (r4 != r0) goto L_0x007c
            gnu.mapping.Symbol r0 = gnu.xquery.lang.XQParser.LAST_VARNAME
            goto L_0x007e
        L_0x007c:
            gnu.mapping.Symbol r0 = gnu.xquery.lang.XQParser.POSITION_VARNAME
        L_0x007e:
            gnu.expr.NameLookup r2 = r1.lookup
            gnu.expr.Declaration r2 = r2.lookup((java.lang.Object) r0, (boolean) r10)
            if (r2 != 0) goto L_0x00a1
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "undefined context for "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r0.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.error(r8, r3)
            goto L_0x00a4
        L_0x00a1:
            r2.setCanRead(r11)
        L_0x00a4:
            gnu.expr.ReferenceExp r3 = new gnu.expr.ReferenceExp
            r3.<init>(r0, r2)
            return r3
        L_0x00aa:
            gnu.expr.Compilation r2 = r17.getCompilation()
            gnu.expr.Expression[] r0 = r18.getArgs()
            r3 = 0
        L_0x00b3:
            int r4 = r0.length
            int r4 = r4 - r11
            if (r3 >= r4) goto L_0x00e8
            r4 = r0[r3]
            gnu.expr.QuoteExp r4 = (gnu.expr.QuoteExp) r4
            java.lang.Object r4 = r4.getValue()
            java.lang.String r4 = (java.lang.String) r4
            gnu.xquery.lang.XQParser r5 = r1.parser
            gnu.mapping.Symbol r4 = r5.namespaceResolve(r4, r10)
            if (r4 != 0) goto L_0x00ca
            goto L_0x00e5
        L_0x00ca:
            java.lang.String r5 = r4.getNamespaceURI()
            int r5 = r5.length()
            if (r5 != 0) goto L_0x00da
            java.lang.String r4 = "pragma name cannot be in the empty namespace"
            r2.error(r8, r4)
            goto L_0x00e5
        L_0x00da:
            int r5 = r3 + 1
            r5 = r0[r5]
            gnu.expr.Expression r4 = r1.checkPragma(r4, r5)
            if (r4 == 0) goto L_0x00e5
            return r4
        L_0x00e5:
            int r3 = r3 + 2
            goto L_0x00b3
        L_0x00e8:
            int r2 = r0.length
            if (r3 >= r2) goto L_0x00f0
            int r2 = r0.length
            int r2 = r2 - r11
            r0 = r0[r2]
            return r0
        L_0x00f0:
            gnu.text.SourceMessages r0 = r17.getMessages()
            java.lang.String r2 = "XQST0079"
            java.lang.String r3 = "no recognized pragma or default in extension expression"
            r0.error((char) r8, (java.lang.String) r3, (java.lang.String) r2)
            gnu.expr.ErrorExp r0 = new gnu.expr.ErrorExp
            r0.<init>(r3)
            return r0
        L_0x0102:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r12)
            java.lang.String r3 = "compare"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r7)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:compare"
            gnu.expr.Expression r0 = r1.withCollator((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r0, (java.lang.String) r3, (int) r9)
            return r0
        L_0x0117:
            java.lang.String r2 = "gnu.xquery.util.DistinctValues"
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r2)
            java.lang.String r3 = "distinctValues$X"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r7)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:distinct-values"
            gnu.expr.Expression r0 = r1.withCollator((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r0, (java.lang.String) r3, (int) r11)
            return r0
        L_0x012e:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "localName"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:local-name"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x0143:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "namespaceURI"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:namespace-uri"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x0158:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r4 = "collection"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r4, (int) r9)
            gnu.expr.Expression r3 = r1.checkArgCount(r0, r3, r10, r11)
            if (r3 == 0) goto L_0x016d
            return r3
        L_0x016d:
            gnu.expr.Expression r3 = r17.getBaseUriExpr()
            int r4 = r0.length
            if (r4 <= 0) goto L_0x0177
            r0 = r0[r10]
            goto L_0x0179
        L_0x0177:
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
        L_0x0179:
            gnu.expr.ApplyExp r4 = new gnu.expr.ApplyExp
            gnu.expr.Expression[] r5 = new gnu.expr.Expression[r9]
            r5[r10] = r0
            r5[r11] = r3
            r4.<init>((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r5)
            gnu.kawa.xml.NodeType r0 = gnu.kawa.xml.NodeType.documentNodeTest
            r4.setType(r0)
            return r4
        L_0x018a:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            r5 = -9
            if (r4 != r5) goto L_0x01b5
            boolean r6 = gnu.xquery.lang.XQParser.warnOldVersion
            if (r6 == 0) goto L_0x01b2
            java.lang.String r6 = "document"
            java.lang.String r7 = r3.getName()
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x01b2
            gnu.expr.Compilation r6 = r17.getCompilation()
            r7 = 119(0x77, float:1.67E-43)
            java.lang.String r8 = "replace 'document' by 'doc'"
            r6.error(r7, r8)
        L_0x01b2:
            java.lang.String r6 = "docCached"
            goto L_0x01b7
        L_0x01b5:
            java.lang.String r6 = "availableCached"
        L_0x01b7:
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r6, (int) r9)
            gnu.expr.Expression r3 = r1.checkArgCount(r0, r3, r11, r11)
            if (r3 == 0) goto L_0x01c2
            return r3
        L_0x01c2:
            gnu.expr.Expression r3 = r17.getBaseUriExpr()
            gnu.expr.ApplyExp r6 = new gnu.expr.ApplyExp
            gnu.expr.Expression[] r7 = new gnu.expr.Expression[r9]
            r0 = r0[r10]
            r7[r10] = r0
            r7[r11] = r3
            r6.<init>((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r7)
            if (r4 != r5) goto L_0x01db
            gnu.kawa.xml.NodeType r0 = gnu.kawa.xml.NodeType.documentNodeTest
            r6.setType(r0)
            goto L_0x01e0
        L_0x01db:
            gnu.kawa.xml.XDataType r0 = gnu.kawa.xml.XDataType.booleanType
            r6.setType(r0)
        L_0x01e0:
            return r6
        L_0x01e1:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "baseUri"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:base-uri"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x01f6:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.expr.Expression r2 = r1.checkArgCount(r0, r3, r11, r9)
            if (r2 == 0) goto L_0x0201
            return r2
        L_0x0201:
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r9]
            r3 = r0[r10]
            r2[r10] = r3
            int r3 = r0.length
            if (r3 != r11) goto L_0x0211
            gnu.expr.Expression r0 = r17.getBaseUriExpr()
            r2[r11] = r0
            goto L_0x0215
        L_0x0211:
            r0 = r0[r11]
            r2[r11] = r0
        L_0x0215:
            gnu.bytecode.ClassType r0 = gnu.bytecode.ClassType.make(r15)
            java.lang.String r3 = "resolveURI"
            gnu.bytecode.Method r0 = r0.getDeclaredMethod((java.lang.String) r3, (int) r9)
            gnu.expr.ApplyExp r3 = new gnu.expr.ApplyExp
            r3.<init>((gnu.bytecode.Method) r0, (gnu.expr.Expression[]) r2)
            return r3
        L_0x0225:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.expr.Expression r2 = r1.checkArgCount(r0, r3, r11, r11)
            if (r2 == 0) goto L_0x0230
            return r2
        L_0x0230:
            r2 = r0[r10]
            boolean r2 = r2 instanceof gnu.expr.QuoteExp
            if (r2 == 0) goto L_0x027c
            r0 = r0[r10]
            gnu.expr.QuoteExp r0 = (gnu.expr.QuoteExp) r0
            java.lang.Object r0 = r0.getValue()
            if (r0 != 0) goto L_0x0242
            r7 = 0
            goto L_0x0246
        L_0x0242:
            java.lang.String r7 = r0.toString()
        L_0x0246:
            gnu.xquery.lang.XQParser r0 = r1.parser
            gnu.xml.NamespaceBinding r0 = r0.constructorNamespaces
            gnu.xquery.lang.XQParser r2 = r1.parser
            gnu.xml.NamespaceBinding r2 = r2.prologNamespaces
            java.lang.String r0 = gnu.xquery.util.QNameUtils.lookupPrefix(r7, r0, r2)
            if (r0 != 0) goto L_0x0276
            gnu.expr.Compilation r0 = r17.getCompilation()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "unknown namespace prefix '"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r3 = "'"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            gnu.expr.Expression r0 = r0.syntaxError(r2)
            return r0
        L_0x0276:
            gnu.expr.QuoteExp r2 = new gnu.expr.QuoteExp
            r2.<init>(r0)
            return r2
        L_0x027c:
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r7]
            r0 = r0[r10]
            r2[r10] = r0
            gnu.expr.QuoteExp r0 = new gnu.expr.QuoteExp
            gnu.xquery.lang.XQParser r3 = r1.parser
            gnu.xml.NamespaceBinding r3 = r3.constructorNamespaces
            r0.<init>(r3)
            r2[r11] = r0
            gnu.expr.QuoteExp r0 = new gnu.expr.QuoteExp
            gnu.xquery.lang.XQParser r3 = r1.parser
            gnu.xml.NamespaceBinding r3 = r3.prologNamespaces
            r0.<init>(r3)
            r2[r9] = r0
            gnu.expr.PrimProcedure r0 = new gnu.expr.PrimProcedure
            gnu.bytecode.ClassType r3 = gnu.bytecode.ClassType.make(r15)
            java.lang.String r4 = "resolvePrefix"
            gnu.bytecode.Method r3 = r3.getDeclaredMethod((java.lang.String) r4, (int) r7)
            r0.<init>(r3)
            gnu.expr.ApplyExp r3 = new gnu.expr.ApplyExp
            r3.<init>((gnu.mapping.Procedure) r0, (gnu.expr.Expression[]) r2)
            r0 = 4
            r3.setFlag(r0)
            return r3
        L_0x02b1:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.expr.Expression r0 = r1.checkArgCount(r0, r3, r10, r10)
            if (r0 == 0) goto L_0x02bc
            return r0
        L_0x02bc:
            gnu.expr.Expression r0 = r17.getBaseUriExpr()
            return r0
        L_0x02c1:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r13)
            java.lang.String r3 = "indexOf$X"
            r4 = 4
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r4)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:index-of"
            gnu.expr.Expression r0 = r1.withCollator((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r0, (java.lang.String) r3, (int) r9)
            return r0
        L_0x02d7:
            java.lang.String r2 = "gnu.xml.TextUtils"
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r2)
            java.lang.String r3 = "asString"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:string"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x02ee:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r12)
            java.lang.String r3 = "normalizeSpace"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:normalize-space"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x0303:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.expr.Expression r2 = r1.checkArgCount(r0, r3, r11, r11)
            if (r2 == 0) goto L_0x030e
            return r2
        L_0x030e:
            r0 = r0[r10]
            return r0
        L_0x0311:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "lang"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r9)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:lang"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r11)
            return r0
        L_0x0326:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "name"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:name"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x033b:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r13)
            java.lang.String r3 = "deepEqual"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r7)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:deep-equal"
            gnu.expr.Expression r0 = r1.withCollator((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r0, (java.lang.String) r3, (int) r9)
            return r0
        L_0x0350:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r14)
            java.lang.String r3 = "min"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r9)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:min"
            gnu.expr.Expression r0 = r1.withCollator((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r0, (java.lang.String) r3, (int) r11)
            return r0
        L_0x0365:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r14)
            java.lang.String r3 = "max"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r9)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:max"
            gnu.expr.Expression r0 = r1.withCollator((gnu.bytecode.Method) r2, (gnu.expr.Expression[]) r0, (java.lang.String) r3, (int) r11)
            return r0
        L_0x037a:
            java.lang.String r2 = "gnu.xquery.util.NumberValue"
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r2)
            java.lang.String r3 = "numberValue"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:number"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x0391:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.expr.Expression r0 = r1.checkArgCount(r0, r3, r10, r10)
            if (r0 == 0) goto L_0x039c
            return r0
        L_0x039c:
            gnu.xquery.lang.XQParser r0 = r1.parser
            gnu.xquery.util.NamedCollator r0 = r0.defaultCollator
            if (r0 == 0) goto L_0x03a7
            java.lang.String r0 = r0.getName()
            goto L_0x03a9
        L_0x03a7:
            java.lang.String r0 = "http://www.w3.org/2005/xpath-functions/collation/codepoint"
        L_0x03a9:
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.getInstance(r0)
            return r0
        L_0x03ae:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "id$X"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r7)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:id"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r11)
            return r0
        L_0x03c3:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "idref"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r9)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:idref"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r11)
            return r0
        L_0x03d8:
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r16)
            java.lang.String r3 = "root"
            gnu.bytecode.Method r2 = r2.getDeclaredMethod((java.lang.String) r3, (int) r11)
            gnu.expr.Expression[] r0 = r18.getArgs()
            java.lang.String r3 = "fn:root"
            gnu.expr.Expression r0 = r1.withContext(r2, r0, r3, r10)
            return r0
        L_0x03ed:
            gnu.expr.Expression[] r3 = r18.getArgs()
            r7 = -33
            if (r4 != r7) goto L_0x03f7
            r9 = 0
            goto L_0x03f8
        L_0x03f7:
            r9 = 1
        L_0x03f8:
            r9 = r3[r9]
            boolean r12 = r9 instanceof gnu.expr.ApplyExp
            if (r12 == 0) goto L_0x0413
            r13 = r9
            gnu.expr.ApplyExp r13 = (gnu.expr.ApplyExp) r13
            gnu.expr.Expression r14 = r13.getFunction()
            java.lang.Object r14 = r14.valueIfConstant()
            gnu.expr.PrimProcedure r15 = gnu.xquery.lang.XQParser.proc_OccurrenceType_getInstance
            if (r14 != r15) goto L_0x0413
            gnu.expr.Expression r13 = r13.getArg(r10)
            goto L_0x0414
        L_0x0413:
            r13 = r9
        L_0x0414:
            java.lang.Object r13 = r13.valueIfConstant()
            gnu.kawa.reflect.SingletonType r14 = gnu.kawa.reflect.SingletonType.getInstance()
            if (r13 != r14) goto L_0x0422
            java.lang.String r14 = "type to 'cast as' or 'castable as' must be atomic"
            goto L_0x043f
        L_0x0422:
            gnu.kawa.xml.XDataType r14 = gnu.kawa.xml.XDataType.anyAtomicType
            if (r13 != r14) goto L_0x0429
            java.lang.String r14 = "type to 'cast as' or 'castable as' cannot be anyAtomicType"
            goto L_0x043f
        L_0x0429:
            gnu.kawa.xml.XDataType r14 = gnu.kawa.xml.XDataType.anySimpleType
            if (r13 != r14) goto L_0x0430
            java.lang.String r14 = "type to 'cast as' or 'castable as' cannot be anySimpleType"
            goto L_0x043f
        L_0x0430:
            gnu.kawa.xml.XDataType r14 = gnu.kawa.xml.XDataType.untypedType
            if (r13 != r14) goto L_0x0437
            java.lang.String r14 = "type to 'cast as' or 'castable as' cannot be untyped"
            goto L_0x043f
        L_0x0437:
            gnu.kawa.xml.XDataType r14 = gnu.kawa.xml.XDataType.NotationType
            if (r13 != r14) goto L_0x043e
            java.lang.String r14 = "type to 'cast as' or 'castable as' cannot be NOTATION"
            goto L_0x043f
        L_0x043e:
            r14 = 0
        L_0x043f:
            if (r14 == 0) goto L_0x0448
            gnu.text.SourceMessages r15 = r1.messages
            java.lang.String r10 = "XPST0080"
            r15.error(r8, r9, r14, r10)
        L_0x0448:
            gnu.bytecode.ClassType r8 = gnu.expr.Compilation.typeSymbol
            if (r13 != r8) goto L_0x0450
            if (r12 != 0) goto L_0x0450
            r8 = 1
            goto L_0x0451
        L_0x0450:
            r8 = 0
        L_0x0451:
            if (r4 != r7) goto L_0x0465
            if (r8 == 0) goto L_0x0460
            r0 = r3[r11]
            gnu.expr.ApplyExp r0 = gnu.xquery.lang.XQParser.castQName(r0, r11)
            gnu.expr.Expression r0 = r1.visitApplyExp((gnu.expr.ApplyExp) r0, (java.lang.Void) r2)
            return r0
        L_0x0460:
            gnu.expr.Expression r2 = gnu.xquery.lang.XQParser.makeFunctionExp(r6, r5)
            goto L_0x0490
        L_0x0465:
            if (r8 == 0) goto L_0x0488
            r2 = 0
            r4 = r3[r2]
            boolean r4 = r4 instanceof gnu.expr.QuoteExp
            if (r4 == 0) goto L_0x0488
            r0 = r3[r2]
            gnu.expr.QuoteExp r0 = (gnu.expr.QuoteExp) r0
            java.lang.Object r0 = r0.getValue()
            gnu.xquery.lang.XQParser r2 = r1.parser     // Catch:{ RuntimeException -> 0x0484 }
            gnu.xml.NamespaceBinding r2 = r2.constructorNamespaces     // Catch:{ RuntimeException -> 0x0484 }
            gnu.xquery.lang.XQParser r3 = r1.parser     // Catch:{ RuntimeException -> 0x0484 }
            gnu.xml.NamespaceBinding r3 = r3.prologNamespaces     // Catch:{ RuntimeException -> 0x0484 }
            gnu.xquery.util.QNameUtils.resolveQName(r0, r2, r3)     // Catch:{ RuntimeException -> 0x0484 }
            gnu.expr.QuoteExp r0 = gnu.xquery.lang.XQuery.trueExp     // Catch:{ RuntimeException -> 0x0484 }
            return r0
        L_0x0484:
            r0 = move-exception
            gnu.expr.QuoteExp r0 = gnu.xquery.lang.XQuery.falseExp
            return r0
        L_0x0488:
            java.lang.String r2 = "gnu.xquery.lang.XQParser"
            java.lang.String r4 = "castableAs"
            gnu.expr.Expression r2 = gnu.xquery.lang.XQParser.makeFunctionExp(r2, r4)
        L_0x0490:
            gnu.expr.ApplyExp r4 = new gnu.expr.ApplyExp
            r4.<init>((gnu.expr.Expression) r2, (gnu.expr.Expression[]) r3)
            gnu.expr.Expression r0 = r4.setLine((gnu.expr.Expression) r0)
            return r0
        L_0x049a:
            gnu.expr.Expression[] r0 = r18.getArgs()
            gnu.expr.Expression r2 = r1.checkArgCount(r0, r3, r11, r11)
            if (r2 == 0) goto L_0x04a5
            return r2
        L_0x04a5:
            gnu.xquery.lang.XQParser r2 = r1.parser
            gnu.xml.NamespaceBinding r2 = r2.constructorNamespaces
            r3 = -36
            if (r4 != r3) goto L_0x04b6
            gnu.xml.NamespaceBinding r3 = new gnu.xml.NamespaceBinding
            java.lang.String r4 = ""
            r10 = 0
            r3.<init>(r10, r4, r2)
            r2 = r3
        L_0x04b6:
            r3 = 0
            r4 = r0[r3]
            boolean r4 = r4 instanceof gnu.expr.QuoteExp
            if (r4 == 0) goto L_0x04e1
            r0 = r0[r3]     // Catch:{ RuntimeException -> 0x04d3 }
            gnu.expr.QuoteExp r0 = (gnu.expr.QuoteExp) r0     // Catch:{ RuntimeException -> 0x04d3 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ RuntimeException -> 0x04d3 }
            gnu.xquery.lang.XQParser r3 = r1.parser     // Catch:{ RuntimeException -> 0x04d3 }
            gnu.xml.NamespaceBinding r3 = r3.prologNamespaces     // Catch:{ RuntimeException -> 0x04d3 }
            java.lang.Object r0 = gnu.xquery.util.QNameUtils.resolveQName(r0, r2, r3)     // Catch:{ RuntimeException -> 0x04d3 }
            gnu.expr.QuoteExp r2 = new gnu.expr.QuoteExp     // Catch:{ RuntimeException -> 0x04d3 }
            r2.<init>(r0)     // Catch:{ RuntimeException -> 0x04d3 }
            return r2
        L_0x04d3:
            r0 = move-exception
            gnu.expr.Compilation r2 = r17.getCompilation()
            java.lang.String r0 = r0.getMessage()
            gnu.expr.Expression r0 = r2.syntaxError(r0)
            return r0
        L_0x04e1:
            gnu.expr.Expression[] r3 = new gnu.expr.Expression[r7]
            r4 = 0
            r0 = r0[r4]
            r3[r4] = r0
            gnu.expr.QuoteExp r0 = new gnu.expr.QuoteExp
            r0.<init>(r2)
            r3[r11] = r0
            gnu.expr.QuoteExp r0 = new gnu.expr.QuoteExp
            gnu.xquery.lang.XQParser r2 = r1.parser
            gnu.xml.NamespaceBinding r2 = r2.prologNamespaces
            r0.<init>(r2)
            r3[r9] = r0
            gnu.bytecode.ClassType r0 = gnu.bytecode.ClassType.make(r15)
            java.lang.String r2 = "resolveQName"
            gnu.bytecode.Method r0 = r0.getDeclaredMethod((java.lang.String) r2, (int) r7)
            gnu.expr.ApplyExp r2 = new gnu.expr.ApplyExp
            r2.<init>((gnu.bytecode.Method) r0, (gnu.expr.Expression[]) r3)
            r0 = 4
            r2.setFlag(r0)
            return r2
        L_0x050e:
            r10 = 0
            goto L_0x0511
        L_0x0510:
            r10 = 0
        L_0x0511:
            java.lang.Object r2 = r18.getFunctionValue()
            boolean r3 = r2 instanceof gnu.bytecode.Type
            if (r3 == 0) goto L_0x053f
            gnu.expr.Expression[] r2 = r18.getArgs()
            int r3 = r2.length
            if (r3 == r11) goto L_0x0528
            gnu.text.SourceMessages r2 = r1.messages
            java.lang.String r3 = "type constructor requires a single argument"
            r2.error(r8, r3)
            return r0
        L_0x0528:
            gnu.expr.ApplyExp r3 = new gnu.expr.ApplyExp
            gnu.expr.Expression r4 = gnu.xquery.lang.XQParser.makeFunctionExp(r6, r5)
            gnu.expr.Expression[] r5 = new gnu.expr.Expression[r9]
            gnu.expr.Expression r0 = r18.getFunction()
            r6 = 0
            r5[r6] = r0
            r0 = r2[r6]
            r5[r11] = r0
            r3.<init>((gnu.expr.Expression) r4, (gnu.expr.Expression[]) r5)
            return r3
        L_0x053f:
            boolean r3 = r2 instanceof gnu.kawa.xml.MakeElement
            if (r3 == 0) goto L_0x05be
            gnu.kawa.xml.MakeElement r2 = (gnu.kawa.xml.MakeElement) r2
            gnu.xml.NamespaceBinding r3 = r2.getNamespaceNodes()
            gnu.mapping.Symbol r4 = r2.tag
            if (r4 != 0) goto L_0x0551
            gnu.mapping.Symbol r4 = gnu.kawa.xml.MakeElement.getTagName(r18)
        L_0x0551:
            r5 = 0
            gnu.xml.NamespaceBinding r3 = maybeAddNamespace(r4, r5, r3)
            gnu.expr.Expression[] r4 = r18.getArgs()
            int r6 = r4.length
            gnu.mapping.Symbol[] r6 = new gnu.mapping.Symbol[r6]
            r9 = r3
            r3 = 0
            r7 = 0
        L_0x0561:
            int r12 = r4.length
            if (r3 >= r12) goto L_0x05b9
            r12 = r4[r3]
            boolean r13 = r12 instanceof gnu.expr.ApplyExp
            if (r13 == 0) goto L_0x05b4
            gnu.expr.ApplyExp r12 = (gnu.expr.ApplyExp) r12
            gnu.expr.Expression r13 = r12.getFunction()
            gnu.expr.QuoteExp r14 = gnu.kawa.xml.MakeAttribute.makeAttributeExp
            if (r13 != r14) goto L_0x05b4
            gnu.mapping.Symbol r13 = gnu.kawa.xml.MakeElement.getTagName(r12)
            if (r13 == 0) goto L_0x05b4
            r14 = 0
        L_0x057b:
            if (r14 != r7) goto L_0x0589
            int r12 = r7 + 1
            r6[r7] = r13
            gnu.xml.NamespaceBinding r7 = maybeAddNamespace(r13, r11, r9)
            r9 = r7
            r7 = r12
            goto L_0x05b4
        L_0x0589:
            r15 = r6[r14]
            boolean r15 = r13.equals(r15)
            if (r15 == 0) goto L_0x05af
            gnu.expr.Compilation r15 = r17.getCompilation()
            r15.setLine((gnu.expr.Expression) r12)
            gnu.mapping.Symbol r15 = gnu.kawa.xml.MakeElement.getTagName(r18)
            if (r15 != 0) goto L_0x05a0
            r15 = r10
            goto L_0x05a4
        L_0x05a0:
            java.lang.String r15 = r15.toString()
        L_0x05a4:
            gnu.text.SourceMessages r5 = r1.messages
            java.lang.String r15 = gnu.xml.XMLFilter.duplicateAttributeMessage(r13, r15)
            java.lang.String r10 = "XQST0040"
            r5.error((char) r8, (java.lang.String) r15, (java.lang.String) r10)
        L_0x05af:
            int r14 = r14 + 1
            r5 = 0
            r10 = 0
            goto L_0x057b
        L_0x05b4:
            int r3 = r3 + 1
            r5 = 0
            r10 = 0
            goto L_0x0561
        L_0x05b9:
            if (r9 == 0) goto L_0x05be
            r2.setNamespaceNodes(r9)
        L_0x05be:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQResolveNames.visitApplyExp(gnu.expr.ApplyExp, java.lang.Void):gnu.expr.Expression");
    }

    public Expression checkPragma(Symbol name, Expression contents) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public Expression getBaseUriExpr() {
        Compilation comp = getCompilation();
        String staticBaseUri = this.parser.getStaticBaseUri();
        if (staticBaseUri != null) {
            return QuoteExp.getInstance(staticBaseUri);
        }
        return GetModuleClass.getModuleClassURI(comp);
    }

    static NamespaceBinding maybeAddNamespace(Symbol qname, boolean isAttribute, NamespaceBinding bindings) {
        if (qname == null) {
            return bindings;
        }
        String prefix = qname.getPrefix();
        String uri = qname.getNamespaceURI();
        if (prefix == "") {
            prefix = null;
        }
        if (uri == "") {
            uri = null;
        }
        if (isAttribute && prefix == null && uri == null) {
            return bindings;
        }
        return NamespaceBinding.maybeAdd(prefix, uri, bindings);
    }

    static Declaration procToDecl(Object symbol, Object val) {
        Declaration decl = new Declaration(symbol);
        decl.setProcedureDecl(true);
        decl.noteValue(new QuoteExp(val));
        decl.setFlag(16384);
        return decl;
    }
}
