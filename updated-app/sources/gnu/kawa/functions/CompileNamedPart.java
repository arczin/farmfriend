package gnu.kawa.functions;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.Access;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.HasNamedParts;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Translator;

public class CompileNamedPart {
    static final ClassType typeHasNamedParts = ClassType.make("gnu.mapping.HasNamedParts");

    public static Expression validateGetNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        ApplyExp applyExp = exp;
        InlineCalls inlineCalls = visitor;
        Type type = required;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length != 2 || !(args[1] instanceof QuoteExp) || !(applyExp instanceof GetNamedExp)) {
            return applyExp;
        }
        Expression context = args[0];
        Declaration decl = null;
        if (context instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) context;
            if ("*".equals(rexp.getName())) {
                return makeGetNamedInstancePartExp(args[1]);
            }
            decl = rexp.getBinding();
        }
        String mname = ((QuoteExp) args[1]).getValue().toString();
        Type type2 = context.getType();
        if (context == QuoteExp.nullExp) {
        }
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        Type typeval = language.getTypeFor(context, false);
        ClassType caller = comp == null ? null : comp.curClass != null ? comp.curClass : comp.mainClass;
        GetNamedExp nexp = (GetNamedExp) applyExp;
        if (typeval != null) {
            if (mname.equals(GetNamedPart.CLASSTYPE_FOR)) {
                return new QuoteExp(typeval);
            }
            if (typeval instanceof ObjectType) {
                if (mname.equals("new")) {
                    return nexp.setProcedureKind('N');
                }
                if (mname.equals(GetNamedPart.INSTANCEOF_METHOD_NAME)) {
                    return nexp.setProcedureKind(Access.INNERCLASS_CONTEXT);
                }
                if (mname.equals(GetNamedPart.CAST_METHOD_NAME)) {
                    return nexp.setProcedureKind(Access.CLASS_CONTEXT);
                }
            }
        }
        if (!(typeval instanceof ObjectType)) {
            if (type2.isSubtype(Compilation.typeClassType)) {
                Declaration declaration = decl;
            } else if (type2.isSubtype(Type.javalangClassType)) {
                GetNamedExp getNamedExp = nexp;
                Declaration declaration2 = decl;
            } else {
                if (type2 instanceof ObjectType) {
                    ObjectType otype = (ObjectType) type2;
                    PrimProcedure[] methods = ClassMethods.getMethods(otype, Compilation.mangleName(mname), 'V', caller, language);
                    if (methods != null && methods.length > 0) {
                        nexp.methods = methods;
                        return nexp.setProcedureKind(Access.METHOD_CONTEXT);
                    } else if (type2.isSubtype(typeHasNamedParts)) {
                        if (decl != null) {
                            Object constantValue = Declaration.followAliases(decl).getConstantValue();
                            Object val = constantValue;
                            if (constantValue != null) {
                                HasNamedParts value = (HasNamedParts) val;
                                if (value.isConstant(mname)) {
                                    return QuoteExp.getInstance(value.get(mname));
                                }
                            }
                        }
                        PrimProcedure[] primProcedureArr = methods;
                        GetNamedExp getNamedExp2 = nexp;
                        Declaration declaration3 = decl;
                        return new ApplyExp(typeHasNamedParts.getDeclaredMethod("get", 1), args[0], QuoteExp.getInstance(mname)).setLine((Expression) applyExp);
                    } else {
                        GetNamedExp getNamedExp3 = nexp;
                        Declaration declaration4 = decl;
                        if (SlotGet.lookupMember(otype, mname, caller) != null || (mname.equals(PropertyTypeConstants.PROPERTY_TYPE_LENGTH) && (type2 instanceof ArrayType))) {
                            ApplyExp aexp = new ApplyExp((Procedure) SlotGet.field, args);
                            aexp.setLine((Expression) applyExp);
                            return inlineCalls.visitApplyOnly(aexp, type);
                        }
                    }
                } else {
                    Declaration declaration5 = decl;
                }
                if (comp.warnUnknownMember()) {
                    comp.error('w', "no known slot '" + mname + "' in " + type2.getName());
                }
                return applyExp;
            }
            return applyExp;
        } else if (mname.length() <= 1 || mname.charAt(0) != '.') {
            if (CompileReflect.checkKnownClass(typeval, comp) < 0) {
                return applyExp;
            }
            PrimProcedure[] methods2 = ClassMethods.getMethods((ObjectType) typeval, Compilation.mangleName(mname), 0, caller, language);
            if (methods2 == null || methods2.length <= 0) {
                ApplyExp aexp2 = new ApplyExp((Procedure) SlotGet.staticField, args);
                aexp2.setLine((Expression) applyExp);
                return inlineCalls.visitApplyOnly(aexp2, type);
            }
            nexp.methods = methods2;
            return nexp.setProcedureKind('S');
        } else {
            Expression expression = context;
            return new QuoteExp(new NamedPart(typeval, mname, 'D'));
        }
    }

    public static Expression validateSetNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length != 3 || !(args[1] instanceof QuoteExp)) {
            return exp;
        }
        Expression context = args[0];
        String mname = ((QuoteExp) args[1]).getValue().toString();
        Type type = context.getType();
        Compilation comp = visitor.getCompilation();
        Type typeval = comp.getLanguage().getTypeFor(context);
        ClassType caller = comp == null ? null : comp.curClass != null ? comp.curClass : comp.mainClass;
        ApplyExp original = exp;
        if (typeval instanceof ClassType) {
            exp = new ApplyExp((Procedure) SlotSet.set$Mnstatic$Mnfield$Ex, args);
        } else if ((type instanceof ClassType) && SlotSet.lookupMember((ClassType) type, mname, caller) != null) {
            exp = new ApplyExp((Procedure) SlotSet.set$Mnfield$Ex, args);
        }
        if (exp != original) {
            exp.setLine((Expression) original);
        }
        exp.setType(Type.voidType);
        return exp;
    }

    public static Expression makeExp(Expression clas, Expression member) {
        String combinedName = combineName(clas, member);
        Environment env = Environment.getCurrent();
        if (combinedName != null) {
            Symbol symbol = Namespace.EmptyNamespace.getSymbol(combinedName);
            Declaration decl = ((Translator) Compilation.getCurrent()).lexical.lookup((Object) symbol, false);
            if (!Declaration.isUnknown(decl)) {
                return new ReferenceExp(decl);
            }
            if (symbol != null && env.isBound(symbol, (Object) null)) {
                return new ReferenceExp((Object) combinedName);
            }
        }
        if (clas instanceof ReferenceExp) {
            ReferenceExp referenceExp = (ReferenceExp) clas;
            ReferenceExp rexp = referenceExp;
            if (referenceExp.isUnknown()) {
                Object rsym = rexp.getSymbol();
                if (env.get((EnvironmentKey) rsym instanceof Symbol ? (Symbol) rsym : env.getSymbol(rsym.toString()), (Object) null) == null) {
                    try {
                        clas = QuoteExp.getInstance(Type.make(ClassType.getContextClass(rexp.getName())));
                    } catch (Throwable th) {
                    }
                }
            }
        }
        GetNamedExp exp = new GetNamedExp(new Expression[]{clas, member});
        exp.combinedName = combinedName;
        return exp;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0015, code lost:
        if (r0 == null) goto L_0x0017;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String combineName(gnu.expr.Expression r4, gnu.expr.Expression r5) {
        /*
            java.lang.Object r0 = r5.valueIfConstant()
            r1 = r0
            boolean r0 = r0 instanceof gnu.mapping.SimpleSymbol
            if (r0 == 0) goto L_0x003f
            boolean r0 = r4 instanceof gnu.expr.ReferenceExp
            if (r0 == 0) goto L_0x0017
            r0 = r4
            gnu.expr.ReferenceExp r0 = (gnu.expr.ReferenceExp) r0
            java.lang.String r0 = r0.getSimpleName()
            r2 = r0
            if (r0 != 0) goto L_0x0023
        L_0x0017:
            boolean r0 = r4 instanceof gnu.kawa.functions.GetNamedExp
            if (r0 == 0) goto L_0x003f
            r0 = r4
            gnu.kawa.functions.GetNamedExp r0 = (gnu.kawa.functions.GetNamedExp) r0
            java.lang.String r0 = r0.combinedName
            r2 = r0
            if (r0 == 0) goto L_0x003f
        L_0x0023:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r2)
            r3 = 58
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = r0.intern()
            return r0
        L_0x003f:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileNamedPart.combineName(gnu.expr.Expression, gnu.expr.Expression):java.lang.String");
    }

    public static Expression makeExp(Expression clas, String member) {
        return makeExp(clas, (Expression) new QuoteExp(member));
    }

    public static Expression makeExp(Type type, String member) {
        return makeExp((Expression) new QuoteExp(type), (Expression) new QuoteExp(member));
    }

    public static Expression validateNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        SlotGet slotProc;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        NamedPart namedPart = (NamedPart) proc;
        switch (namedPart.kind) {
            case 'D':
                Expression[] xargs = new Expression[2];
                xargs[1] = QuoteExp.getInstance(namedPart.member.toString().substring(1));
                if (args.length > 0) {
                    xargs[0] = Compilation.makeCoercion(args[0], (Expression) new QuoteExp(namedPart.container));
                    slotProc = SlotGet.field;
                } else {
                    xargs[0] = QuoteExp.getInstance(namedPart.container);
                    slotProc = SlotGet.staticField;
                }
                ApplyExp aexp = new ApplyExp((Procedure) slotProc, xargs);
                aexp.setLine((Expression) exp);
                return visitor.visitApplyOnly(aexp, required);
            default:
                return exp;
        }
    }

    public static Expression validateNamedPartSetter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        SlotSet slotProc;
        exp.visitArgs(visitor);
        NamedPart get = (NamedPart) ((NamedPartSetter) proc).getGetter();
        if (get.kind != 'D') {
            return exp;
        }
        Expression[] xargs = new Expression[3];
        xargs[1] = QuoteExp.getInstance(get.member.toString().substring(1));
        xargs[2] = exp.getArgs()[0];
        if (exp.getArgCount() == 1) {
            xargs[0] = QuoteExp.getInstance(get.container);
            slotProc = SlotSet.set$Mnstatic$Mnfield$Ex;
        } else if (exp.getArgCount() != 2) {
            return exp;
        } else {
            xargs[0] = Compilation.makeCoercion(exp.getArgs()[0], (Expression) new QuoteExp(get.container));
            slotProc = SlotSet.set$Mnfield$Ex;
        }
        ApplyExp aexp = new ApplyExp((Procedure) slotProc, xargs);
        aexp.setLine((Expression) exp);
        return visitor.visitApplyOnly(aexp, required);
    }

    public static Expression makeGetNamedInstancePartExp(Expression member) {
        if (member instanceof QuoteExp) {
            Object val = ((QuoteExp) member).getValue();
            if (val instanceof SimpleSymbol) {
                return QuoteExp.getInstance(new GetNamedInstancePart(val.toString()));
            }
        }
        return new ApplyExp((Procedure) Invoke.make, new QuoteExp(ClassType.make("gnu.kawa.functions.GetNamedInstancePart")), member);
    }

    public static Expression validateGetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Procedure property;
        Expression[] xargs;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        GetNamedInstancePart gproc = (GetNamedInstancePart) proc;
        if (gproc.isField) {
            xargs = new Expression[]{args[0], new QuoteExp(gproc.pname)};
            property = SlotGet.field;
        } else {
            int nargs = args.length;
            Expression[] xargs2 = new Expression[(nargs + 1)];
            xargs2[0] = args[0];
            xargs2[1] = new QuoteExp(gproc.pname);
            System.arraycopy(args, 1, xargs2, 2, nargs - 1);
            property = Invoke.invoke;
            xargs = xargs2;
        }
        return visitor.visitApplyOnly(new ApplyExp(property, xargs), required);
    }

    public static Expression validateSetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        return visitor.visitApplyOnly(new ApplyExp((Procedure) SlotSet.set$Mnfield$Ex, args[0], new QuoteExp(((SetNamedInstancePart) proc).pname), args[1]), required);
    }
}
