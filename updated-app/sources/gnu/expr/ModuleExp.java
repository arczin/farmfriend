package gnu.expr;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.mapping.WrappedException;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.Externalizable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ModuleExp extends LambdaExp implements Externalizable {
    public static final int EXPORT_SPECIFIED = 16384;
    public static final int IMMEDIATE = 1048576;
    public static final int LAZY_DECLARATIONS = 524288;
    public static final int NONSTATIC_SPECIFIED = 65536;
    public static final int STATIC_RUN_SPECIFIED = 262144;
    public static final int STATIC_SPECIFIED = 32768;
    public static final int SUPERTYPE_SPECIFIED = 131072;
    public static boolean alwaysCompile = compilerAvailable;
    public static boolean compilerAvailable = true;
    public static String dumpZipPrefix;
    public static int interactiveCounter;
    static int lastZipCounter;
    public static boolean neverCompile = false;
    ModuleInfo info;
    ClassType[] interfaces;
    ClassType superType;

    public static Class evalToClass(Compilation comp, URL url) throws SyntaxException {
        URL url2;
        Compilation compilation = comp;
        ModuleExp module = comp.getModule();
        SourceMessages messages = comp.getMessages();
        try {
            compilation.minfo.loadByStages(12);
            if (messages.seenErrors()) {
                return null;
            }
            ArrayClassLoader loader = compilation.loader;
            if (url == null) {
                url2 = Path.currentPath().toURL();
            } else {
                url2 = url;
            }
            try {
                loader.setResourceContext(url2);
                ZipOutputStream zout = null;
                if (dumpZipPrefix != null) {
                    try {
                        StringBuffer zipname = new StringBuffer(dumpZipPrefix);
                        lastZipCounter++;
                        if (interactiveCounter > lastZipCounter) {
                            lastZipCounter = interactiveCounter;
                        }
                        zipname.append(lastZipCounter);
                        zipname.append(".zip");
                        zout = new ZipOutputStream(new FileOutputStream(zipname.toString()));
                    } catch (IOException e) {
                        ex = e;
                        throw new WrappedException("I/O error in lambda eval", ex);
                    } catch (ClassNotFoundException e2) {
                        ex = e2;
                        throw new WrappedException("class not found in lambda eval", ex);
                    } catch (Throwable th) {
                        ex = th;
                        comp.getMessages().error('f', "internal compile error - caught " + ex, ex);
                        throw new SyntaxException(messages);
                    }
                }
                int iClass = 0;
                while (iClass < compilation.numClasses) {
                    ClassType clas = compilation.classes[iClass];
                    String className = clas.getName();
                    byte[] classBytes = clas.writeToArray();
                    loader.addClass(className, classBytes);
                    if (zout != null) {
                        ZipEntry zent = new ZipEntry(className.replace('.', '/') + ".class");
                        zent.setSize((long) classBytes.length);
                        CRC32 crc = new CRC32();
                        crc.update(classBytes);
                        url = url2;
                        zent.setCrc(crc.getValue());
                        zent.setMethod(0);
                        zout.putNextEntry(zent);
                        zout.write(classBytes);
                    } else {
                        url = url2;
                    }
                    iClass++;
                    url2 = url;
                }
                if (zout != null) {
                    zout.close();
                }
                Class clas2 = null;
                ArrayClassLoader context = loader;
                while (context.getParent() instanceof ArrayClassLoader) {
                    context = (ArrayClassLoader) context.getParent();
                }
                for (int iClass2 = 0; iClass2 < compilation.numClasses; iClass2++) {
                    ClassType ctype = compilation.classes[iClass2];
                    Class cclass = loader.loadClass(ctype.getName());
                    ctype.setReflectClass(cclass);
                    ctype.setExisting(true);
                    if (iClass2 == 0) {
                        clas2 = cclass;
                    } else if (context != loader) {
                        context.addClass(cclass);
                    }
                }
                ModuleInfo minfo = compilation.minfo;
                minfo.setModuleClass(clas2);
                comp.cleanupAfterCompilation();
                int ndeps = minfo.numDependencies;
                for (int idep = 0; idep < ndeps; idep++) {
                    ModuleInfo dep = minfo.dependencies[idep];
                    Class dclass = dep.getModuleClassRaw();
                    if (dclass == null) {
                        dclass = evalToClass(dep.comp, (URL) null);
                    }
                    compilation.loader.addClass(dclass);
                }
                return clas2;
            } catch (IOException e3) {
                ex = e3;
                URL url3 = url2;
                throw new WrappedException("I/O error in lambda eval", ex);
            } catch (ClassNotFoundException e4) {
                ex = e4;
                URL url4 = url2;
                throw new WrappedException("class not found in lambda eval", ex);
            } catch (Throwable th2) {
                ex = th2;
                URL url5 = url2;
                comp.getMessages().error('f', "internal compile error - caught " + ex, ex);
                throw new SyntaxException(messages);
            }
        } catch (IOException e5) {
            ex = e5;
            URL url6 = url;
            throw new WrappedException("I/O error in lambda eval", ex);
        } catch (ClassNotFoundException e6) {
            ex = e6;
            URL url7 = url;
            throw new WrappedException("class not found in lambda eval", ex);
        } catch (Throwable th3) {
            ex = th3;
            URL url8 = url;
            comp.getMessages().error('f', "internal compile error - caught " + ex, ex);
            throw new SyntaxException(messages);
        }
    }

    public static void mustNeverCompile() {
        alwaysCompile = false;
        neverCompile = true;
        compilerAvailable = false;
    }

    public static void mustAlwaysCompile() {
        alwaysCompile = true;
        neverCompile = false;
    }

    public static final boolean evalModule(Environment env, CallContext ctx, Compilation comp, URL url, OutPort msg) throws Throwable {
        ModuleExp mexp = comp.getModule();
        Language language = comp.getLanguage();
        Object inst = evalModule1(env, comp, url, msg);
        if (inst == null) {
            return false;
        }
        evalModule2(env, ctx, language, mexp, inst);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0049, code lost:
        if (r3.seenErrors() != false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d3, code lost:
        if (r3.seenErrors() != false) goto L_0x00d5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object evalModule1(gnu.mapping.Environment r11, gnu.expr.Compilation r12, java.net.URL r13, gnu.mapping.OutPort r14) throws gnu.text.SyntaxException {
        /*
            gnu.expr.ModuleExp r0 = r12.getModule()
            gnu.expr.ModuleInfo r1 = r12.minfo
            r0.info = r1
            gnu.mapping.Environment r1 = gnu.mapping.Environment.setSaveCurrent(r11)
            gnu.expr.Compilation r2 = gnu.expr.Compilation.setSaveCurrent(r12)
            gnu.text.SourceMessages r3 = r12.getMessages()
            r4 = 0
            r5 = 0
            boolean r6 = alwaysCompile
            if (r6 == 0) goto L_0x0027
            boolean r6 = neverCompile
            if (r6 != 0) goto L_0x001f
            goto L_0x0027
        L_0x001f:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.String r7 = "alwaysCompile and neverCompile are both true!"
            r6.<init>(r7)
            throw r6
        L_0x0027:
            boolean r6 = neverCompile
            r7 = 0
            if (r6 == 0) goto L_0x002e
            r12.mustCompile = r7
        L_0x002e:
            r6 = 6
            r12.process(r6)     // Catch:{ all -> 0x00f2 }
            gnu.expr.ModuleInfo r6 = r12.minfo     // Catch:{ all -> 0x00f2 }
            r8 = 8
            r6.loadByStages(r8)     // Catch:{ all -> 0x00f2 }
            r6 = 20
            r8 = 0
            if (r14 == 0) goto L_0x0045
            boolean r9 = r3.checkErrors((java.io.PrintWriter) r14, (int) r6)     // Catch:{ all -> 0x00f2 }
            if (r9 == 0) goto L_0x0058
            goto L_0x004b
        L_0x0045:
            boolean r9 = r3.seenErrors()     // Catch:{ all -> 0x00f2 }
            if (r9 == 0) goto L_0x0058
        L_0x004b:
            gnu.mapping.Environment.restoreCurrent(r1)
            gnu.expr.Compilation.restoreCurrent(r2)
            if (r5 == 0) goto L_0x0057
            r5.setContextClassLoader(r4)
        L_0x0057:
            return r8
        L_0x0058:
            boolean r9 = r12.mustCompile     // Catch:{ all -> 0x00f2 }
            if (r9 != 0) goto L_0x009b
            boolean r6 = gnu.expr.Compilation.debugPrintFinalExpr     // Catch:{ all -> 0x00f2 }
            if (r6 == 0) goto L_0x008d
            if (r14 == 0) goto L_0x008d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f2 }
            r6.<init>()     // Catch:{ all -> 0x00f2 }
            java.lang.String r7 = "[Evaluating final module \""
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x00f2 }
            java.lang.String r7 = r0.getName()     // Catch:{ all -> 0x00f2 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x00f2 }
            java.lang.String r7 = "\":"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x00f2 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00f2 }
            r14.println(r6)     // Catch:{ all -> 0x00f2 }
            r0.print(r14)     // Catch:{ all -> 0x00f2 }
            r6 = 93
            r14.println(r6)     // Catch:{ all -> 0x00f2 }
            r14.flush()     // Catch:{ all -> 0x00f2 }
        L_0x008d:
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x00f2 }
            gnu.mapping.Environment.restoreCurrent(r1)
            gnu.expr.Compilation.restoreCurrent(r2)
            if (r5 == 0) goto L_0x009a
            r5.setContextClassLoader(r4)
        L_0x009a:
            return r6
        L_0x009b:
            java.lang.Class r9 = evalToClass(r12, r13)     // Catch:{ all -> 0x00f2 }
            if (r9 != 0) goto L_0x00ae
            gnu.mapping.Environment.restoreCurrent(r1)
            gnu.expr.Compilation.restoreCurrent(r2)
            if (r5 == 0) goto L_0x00ad
            r5.setContextClassLoader(r4)
        L_0x00ad:
            return r8
        L_0x00ae:
            java.lang.Thread r10 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00c0 }
            r5 = r10
            java.lang.ClassLoader r10 = r5.getContextClassLoader()     // Catch:{ all -> 0x00c0 }
            r4 = r10
            java.lang.ClassLoader r10 = r9.getClassLoader()     // Catch:{ all -> 0x00c0 }
            r5.setContextClassLoader(r10)     // Catch:{ all -> 0x00c0 }
            goto L_0x00c2
        L_0x00c0:
            r10 = move-exception
            r5 = 0
        L_0x00c2:
            r0.body = r8     // Catch:{ all -> 0x00f2 }
            r0.thisVariable = r8     // Catch:{ all -> 0x00f2 }
            if (r14 == 0) goto L_0x00cf
            boolean r6 = r3.checkErrors((java.io.PrintWriter) r14, (int) r6)     // Catch:{ all -> 0x00f2 }
            if (r6 == 0) goto L_0x00e5
            goto L_0x00d5
        L_0x00cf:
            boolean r6 = r3.seenErrors()     // Catch:{ all -> 0x00f2 }
            if (r6 == 0) goto L_0x00e5
        L_0x00d5:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r7)     // Catch:{ all -> 0x00f2 }
            gnu.mapping.Environment.restoreCurrent(r1)
            gnu.expr.Compilation.restoreCurrent(r2)
            if (r5 == 0) goto L_0x00e4
            r5.setContextClassLoader(r4)
        L_0x00e4:
            return r6
        L_0x00e5:
            gnu.mapping.Environment.restoreCurrent(r1)
            gnu.expr.Compilation.restoreCurrent(r2)
            if (r5 == 0) goto L_0x00f1
            r5.setContextClassLoader(r4)
        L_0x00f1:
            return r9
        L_0x00f2:
            r6 = move-exception
            gnu.mapping.Environment.restoreCurrent(r1)
            gnu.expr.Compilation.restoreCurrent(r2)
            if (r5 == 0) goto L_0x00fe
            r5.setContextClassLoader(r4)
        L_0x00fe:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ModuleExp.evalModule1(gnu.mapping.Environment, gnu.expr.Compilation, java.net.URL, gnu.mapping.OutPort):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x010a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void evalModule2(gnu.mapping.Environment r17, gnu.mapping.CallContext r18, gnu.expr.Language r19, gnu.expr.ModuleExp r20, java.lang.Object r21) throws java.lang.Throwable {
        /*
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            gnu.mapping.Environment r6 = gnu.mapping.Environment.setSaveCurrent(r17)
            r7 = 0
            r8 = 0
            java.lang.Boolean r0 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0104 }
            if (r5 != r0) goto L_0x001b
            gnu.expr.Expression r0 = r4.body     // Catch:{ all -> 0x0104 }
            r0.apply(r2)     // Catch:{ all -> 0x0104 }
            goto L_0x00f6
        L_0x001b:
            boolean r0 = r5 instanceof java.lang.Class     // Catch:{ all -> 0x0104 }
            if (r0 == 0) goto L_0x002b
            gnu.expr.ModuleContext r0 = gnu.expr.ModuleContext.getContext()     // Catch:{ all -> 0x0104 }
            r9 = r5
            java.lang.Class r9 = (java.lang.Class) r9     // Catch:{ all -> 0x0104 }
            java.lang.Object r0 = r0.findInstance((java.lang.Class) r9)     // Catch:{ all -> 0x0104 }
            r5 = r0
        L_0x002b:
            boolean r0 = r5 instanceof java.lang.Runnable     // Catch:{ all -> 0x0102 }
            if (r0 == 0) goto L_0x0048
            boolean r0 = r5 instanceof gnu.expr.ModuleBody     // Catch:{ all -> 0x0102 }
            if (r0 == 0) goto L_0x0042
            r0 = r5
            gnu.expr.ModuleBody r0 = (gnu.expr.ModuleBody) r0     // Catch:{ all -> 0x0102 }
            boolean r9 = r0.runDone     // Catch:{ all -> 0x0102 }
            if (r9 != 0) goto L_0x0041
            r9 = 1
            r0.runDone = r9     // Catch:{ all -> 0x0102 }
            r0.run((gnu.mapping.CallContext) r2)     // Catch:{ all -> 0x0102 }
        L_0x0041:
            goto L_0x0048
        L_0x0042:
            r0 = r5
            java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x0102 }
            r0.run()     // Catch:{ all -> 0x0102 }
        L_0x0048:
            if (r4 != 0) goto L_0x004f
            gnu.kawa.reflect.ClassMemberLocation.defineAll(r5, r3, r1)     // Catch:{ all -> 0x0102 }
            goto L_0x00f6
        L_0x004f:
            gnu.expr.Declaration r0 = r20.firstDecl()     // Catch:{ all -> 0x0102 }
        L_0x0053:
            if (r0 == 0) goto L_0x00f6
            java.lang.Object r9 = r0.getSymbol()     // Catch:{ all -> 0x0102 }
            boolean r10 = r0.isPrivate()     // Catch:{ all -> 0x0102 }
            if (r10 != 0) goto L_0x00ed
            if (r9 != 0) goto L_0x0063
            goto L_0x00ed
        L_0x0063:
            gnu.bytecode.Field r10 = r0.field     // Catch:{ all -> 0x0102 }
            boolean r11 = r9 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x0102 }
            if (r11 == 0) goto L_0x006d
            r11 = r9
            gnu.mapping.Symbol r11 = (gnu.mapping.Symbol) r11     // Catch:{ all -> 0x0102 }
            goto L_0x007b
        L_0x006d:
            java.lang.String r11 = ""
            java.lang.String r12 = r9.toString()     // Catch:{ all -> 0x0102 }
            java.lang.String r12 = r12.intern()     // Catch:{ all -> 0x0102 }
            gnu.mapping.Symbol r11 = gnu.mapping.Symbol.make(r11, r12)     // Catch:{ all -> 0x0102 }
        L_0x007b:
            java.lang.Object r12 = r3.getEnvPropertyFor(r0)     // Catch:{ all -> 0x0102 }
            gnu.expr.Expression r13 = r0.getValue()     // Catch:{ all -> 0x0102 }
            gnu.bytecode.Field r14 = r0.field     // Catch:{ all -> 0x0102 }
            int r14 = r14.getModifiers()     // Catch:{ all -> 0x0102 }
            r14 = r14 & 16
            r15 = 0
            if (r14 == 0) goto L_0x00d5
            boolean r14 = r13 instanceof gnu.expr.QuoteExp     // Catch:{ all -> 0x0102 }
            if (r14 == 0) goto L_0x009e
            gnu.expr.QuoteExp r14 = gnu.expr.QuoteExp.undefined_exp     // Catch:{ all -> 0x0102 }
            if (r13 == r14) goto L_0x009e
            r14 = r13
            gnu.expr.QuoteExp r14 = (gnu.expr.QuoteExp) r14     // Catch:{ all -> 0x0102 }
            java.lang.Object r14 = r14.getValue()     // Catch:{ all -> 0x0102 }
            goto L_0x00c4
        L_0x009e:
            gnu.bytecode.Field r14 = r0.field     // Catch:{ all -> 0x0102 }
            java.lang.reflect.Field r14 = r14.getReflectField()     // Catch:{ all -> 0x0102 }
            java.lang.Object r14 = r14.get(r15)     // Catch:{ all -> 0x0102 }
            boolean r16 = r0.isIndirectBinding()     // Catch:{ all -> 0x0102 }
            if (r16 != 0) goto L_0x00b6
            gnu.expr.QuoteExp r15 = gnu.expr.QuoteExp.getInstance(r14)     // Catch:{ all -> 0x0102 }
            r0.setValue(r15)     // Catch:{ all -> 0x0102 }
            goto L_0x00c4
        L_0x00b6:
            boolean r16 = r0.isAlias()     // Catch:{ all -> 0x0102 }
            if (r16 == 0) goto L_0x00c0
            boolean r15 = r13 instanceof gnu.expr.ReferenceExp     // Catch:{ all -> 0x0102 }
            if (r15 != 0) goto L_0x00c4
        L_0x00c0:
            r15 = 0
            r0.setValue(r15)     // Catch:{ all -> 0x0102 }
        L_0x00c4:
            boolean r15 = r0.isIndirectBinding()     // Catch:{ all -> 0x0102 }
            if (r15 == 0) goto L_0x00d1
            r15 = r14
            gnu.mapping.Location r15 = (gnu.mapping.Location) r15     // Catch:{ all -> 0x0102 }
            r1.addLocation(r11, r12, r15)     // Catch:{ all -> 0x0102 }
            goto L_0x00d4
        L_0x00d1:
            r1.define(r11, r12, r14)     // Catch:{ all -> 0x0102 }
        L_0x00d4:
            goto L_0x00ed
        L_0x00d5:
            gnu.kawa.reflect.StaticFieldLocation r14 = new gnu.kawa.reflect.StaticFieldLocation     // Catch:{ all -> 0x0102 }
            gnu.bytecode.ClassType r15 = r10.getDeclaringClass()     // Catch:{ all -> 0x0102 }
            java.lang.String r2 = r10.getName()     // Catch:{ all -> 0x0102 }
            r14.<init>((gnu.bytecode.ClassType) r15, (java.lang.String) r2)     // Catch:{ all -> 0x0102 }
            r2 = r14
            r2.setDeclaration(r0)     // Catch:{ all -> 0x0102 }
            r1.addLocation(r11, r12, r2)     // Catch:{ all -> 0x0102 }
            r14 = 0
            r0.setValue(r14)     // Catch:{ all -> 0x0102 }
        L_0x00ed:
            gnu.expr.Declaration r2 = r0.nextDecl()     // Catch:{ all -> 0x0102 }
            r0 = r2
            r2 = r18
            goto L_0x0053
        L_0x00f6:
            r18.runUntilDone()     // Catch:{ all -> 0x0102 }
            gnu.mapping.Environment.restoreCurrent(r6)
            if (r8 == 0) goto L_0x0101
            r8.setContextClassLoader(r7)
        L_0x0101:
            return
        L_0x0102:
            r0 = move-exception
            goto L_0x0105
        L_0x0104:
            r0 = move-exception
        L_0x0105:
            gnu.mapping.Environment.restoreCurrent(r6)
            if (r8 == 0) goto L_0x010d
            r8.setContextClassLoader(r7)
        L_0x010d:
            goto L_0x010f
        L_0x010e:
            throw r0
        L_0x010f:
            goto L_0x010e
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ModuleExp.evalModule2(gnu.mapping.Environment, gnu.mapping.CallContext, gnu.expr.Language, gnu.expr.ModuleExp, java.lang.Object):void");
    }

    public String getNamespaceUri() {
        return this.info.uri;
    }

    public final ClassType getSuperType() {
        return this.superType;
    }

    public final void setSuperType(ClassType s) {
        this.superType = s;
    }

    public final ClassType[] getInterfaces() {
        return this.interfaces;
    }

    public final void setInterfaces(ClassType[] s) {
        this.interfaces = s;
    }

    public final boolean isStatic() {
        return getFlag(32768) || ((Compilation.moduleStatic >= 0 || getFlag(1048576)) && !getFlag(131072) && !getFlag(65536));
    }

    public boolean staticInitRun() {
        return isStatic() && (getFlag(262144) || Compilation.moduleStatic == 2);
    }

    public void allocChildClasses(Compilation comp) {
        declareClosureEnv();
        if (comp.usingCPStyle()) {
            allocFrame(comp);
        }
    }

    /* access modifiers changed from: package-private */
    public void allocFields(Compilation comp) {
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if ((!decl.isSimple() || decl.isPublic()) && decl.field == null && decl.getFlag(65536) && decl.getFlag(6)) {
                decl.makeField(comp, (Expression) null);
            }
        }
        for (Declaration decl2 = firstDecl(); decl2 != null; decl2 = decl2.nextDecl()) {
            if (decl2.field == null) {
                Expression value = decl2.getValue();
                if ((!decl2.isSimple() || decl2.isPublic() || decl2.isNamespaceDecl() || (decl2.getFlag(16384) && decl2.getFlag(6))) && !decl2.getFlag(65536)) {
                    if (!(value instanceof LambdaExp) || (value instanceof ModuleExp) || (value instanceof ClassExp)) {
                        decl2.makeField(comp, (decl2.shouldEarlyInit() || decl2.isAlias()) ? value : null);
                    } else {
                        ((LambdaExp) value).allocFieldFor(comp);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitModuleExp(this, d);
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Module/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.writeSpaceFill();
        out.startLogicalBlock("(", false, ")");
        Declaration decl = firstDecl();
        if (decl != null) {
            out.print("Declarations:");
            while (decl != null) {
                out.writeSpaceFill();
                decl.printInfo(out);
                decl = decl.nextDecl();
            }
        }
        out.endLogicalBlock(")");
        out.writeSpaceLinear();
        if (this.body == null) {
            out.print("<null body>");
        } else {
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    public Declaration firstDecl() {
        synchronized (this) {
            if (getFlag(524288)) {
                this.info.setupModuleExp();
            }
        }
        return this.decls;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ed  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.bytecode.ClassType classFor(gnu.expr.Compilation r10) {
        /*
            r9 = this;
            gnu.bytecode.ClassType r0 = r9.type
            if (r0 == 0) goto L_0x000d
            gnu.bytecode.ClassType r0 = r9.type
            gnu.bytecode.ClassType r1 = gnu.expr.Compilation.typeProcedure
            if (r0 == r1) goto L_0x000d
            gnu.bytecode.ClassType r0 = r9.type
            return r0
        L_0x000d:
            java.lang.String r0 = r9.getFileName()
            java.lang.String r1 = r9.getName()
            r2 = 0
            r3 = 0
            if (r1 == 0) goto L_0x001b
            r0 = r1
            goto L_0x0059
        L_0x001b:
            if (r0 != 0) goto L_0x0026
            java.lang.String r0 = r9.getName()
            if (r0 != 0) goto L_0x0059
            java.lang.String r0 = "$unnamed_input_file$"
            goto L_0x0059
        L_0x0026:
            java.lang.String r4 = r9.filename
            java.lang.String r5 = "-"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0051
            java.lang.String r4 = r9.filename
            java.lang.String r5 = "/dev/stdin"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x003b
            goto L_0x0051
        L_0x003b:
            gnu.text.Path r3 = gnu.text.Path.valueOf(r0)
            java.lang.String r0 = r3.getLast()
            r4 = 46
            int r4 = r0.lastIndexOf(r4)
            if (r4 <= 0) goto L_0x0059
            r5 = 0
            java.lang.String r0 = r0.substring(r5, r4)
            goto L_0x0059
        L_0x0051:
            java.lang.String r0 = r9.getName()
            if (r0 != 0) goto L_0x0059
            java.lang.String r0 = "$stdin$"
        L_0x0059:
            java.lang.String r4 = r9.getName()
            if (r4 != 0) goto L_0x0062
            r9.setName(r0)
        L_0x0062:
            java.lang.String r0 = gnu.expr.Compilation.mangleNameIfNeeded(r0)
            java.lang.String r4 = r10.classPrefix
            int r4 = r4.length()
            if (r4 != 0) goto L_0x00ce
            if (r3 == 0) goto L_0x00ce
            boolean r4 = r3.isAbsolute()
            if (r4 != 0) goto L_0x00ce
            gnu.text.Path r4 = r3.getParent()
            r5 = r4
            if (r4 == 0) goto L_0x00ce
            java.lang.String r4 = r5.toString()
            r6 = r4
            int r4 = r4.length()
            if (r4 <= 0) goto L_0x00ce
            java.lang.String r4 = ".."
            int r4 = r6.indexOf(r4)
            if (r4 >= 0) goto L_0x00ce
            java.lang.String r4 = "file.separator"
            java.lang.String r4 = java.lang.System.getProperty(r4)
            java.lang.String r7 = "/"
            java.lang.String r4 = r6.replaceAll(r4, r7)
            java.lang.String r6 = "./"
            boolean r6 = r4.startsWith(r6)
            if (r6 == 0) goto L_0x00a9
            r6 = 2
            java.lang.String r4 = r4.substring(r6)
        L_0x00a9:
            java.lang.String r6 = "."
            boolean r7 = r4.equals(r6)
            if (r7 == 0) goto L_0x00b3
            r6 = r0
            goto L_0x00cc
        L_0x00b3:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = gnu.expr.Compilation.mangleURI(r4)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
        L_0x00cc:
            r2 = r6
            goto L_0x00e1
        L_0x00ce:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r10.classPrefix
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r2 = r4.toString()
        L_0x00e1:
            gnu.bytecode.ClassType r4 = new gnu.bytecode.ClassType
            r4.<init>(r2)
            r9.setType(r4)
            gnu.expr.ModuleExp r5 = r10.mainLambda
            if (r5 != r9) goto L_0x0128
            gnu.bytecode.ClassType r5 = r10.mainClass
            if (r5 != 0) goto L_0x00f4
            r10.mainClass = r4
            goto L_0x0128
        L_0x00f4:
            gnu.bytecode.ClassType r5 = r10.mainClass
            java.lang.String r5 = r5.getName()
            boolean r5 = r2.equals(r5)
            if (r5 != 0) goto L_0x0128
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "inconsistent main class name: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r6 = " - old name: "
            java.lang.StringBuilder r5 = r5.append(r6)
            gnu.bytecode.ClassType r6 = r10.mainClass
            java.lang.String r6 = r6.getName()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = 101(0x65, float:1.42E-43)
            r10.error(r6, r5)
        L_0x0128:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ModuleExp.classFor(gnu.expr.Compilation):gnu.bytecode.ClassType");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        String name = null;
        if (this.type == null || this.type == Compilation.typeProcedure || this.type.isExisting()) {
            if (0 == 0) {
                name = getName();
            }
            if (name == null) {
                name = getFileName();
            }
            out.writeObject(name);
            return;
        }
        out.writeObject(this.type);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Object name = in.readObject();
        if (name instanceof ClassType) {
            this.type = (ClassType) name;
            setName(this.type.getName());
        } else {
            setName((String) name);
        }
        this.flags |= 524288;
    }
}
