package kawa;

import gnu.bytecode.ClassType;
import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.lists.FString;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0or1;
import gnu.mapping.Values;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.text.WriterManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class repl extends Procedure0or1 {
    public static String compilationTopname = null;
    static int defaultParseOptions = 72;
    public static String homeDirectory;
    public static boolean noConsole;
    static Language previousLanguage;
    static boolean shutdownRegistered = WriterManager.instance.registerShutdownHook();
    Language language;

    public repl(Language language2) {
        this.language = language2;
    }

    public Object apply0() {
        Shell.run(this.language, Environment.getCurrent());
        return Values.empty;
    }

    public Object apply1(Object env) {
        Shell.run(this.language, (Environment) env);
        return Values.empty;
    }

    static void bad_option(String str) {
        System.err.println("kawa: bad option '" + str + "'");
        printOptions(System.err);
        System.exit(-1);
    }

    public static void printOption(PrintStream out, String option, String doc) {
        out.print(" ");
        out.print(option);
        int len = option.length() + 1;
        for (int i = 0; i < 30 - len; i++) {
            out.print(" ");
        }
        out.print(" ");
        out.println(doc);
    }

    public static void printOptions(PrintStream out) {
        out.println("Usage: [java kawa.repl | kawa] [options ...]");
        out.println();
        out.println(" Generic options:");
        printOption(out, "--help", "Show help about options");
        printOption(out, "--author", "Show author information");
        printOption(out, "--version", "Show version information");
        out.println();
        out.println(" Options");
        printOption(out, "-e <expr>", "Evaluate expression <expr>");
        printOption(out, "-c <expr>", "Same as -e, but make sure ~/.kawarc.scm is run first");
        printOption(out, "-f <filename>", "File to interpret");
        printOption(out, "-s| --", "Start reading commands interactively from console");
        printOption(out, "-w", "Launch the interpreter in a GUI window");
        printOption(out, "--server <port>", "Start a server accepting telnet connections on <port>");
        printOption(out, "--debug-dump-zip", "Compiled interactive expressions to a zip archive");
        printOption(out, "--debug-print-expr", "Print generated internal expressions");
        printOption(out, "--debug-print-final-expr", "Print expression after any optimizations");
        printOption(out, "--debug-error-prints-stack-trace", "Print stack trace with errors");
        printOption(out, "--debug-warning-prints-stack-trace", "Print stack trace with warnings");
        printOption(out, "--[no-]full-tailcalls", "(Don't) use full tail-calls");
        printOption(out, "-C <filename> ...", "Compile named files to Java class files");
        printOption(out, "--output-format <format>", "Use <format> when printing top-level output");
        printOption(out, "--<language>", "Select source language, one of:");
        String[][] languages = Language.getLanguages();
        for (int i = 0; i < languages.length; i++) {
            out.print("   ");
            String[] lang = languages[i];
            int nwords = lang.length - 1;
            for (int j = 0; j < nwords; j++) {
                out.print(lang[j] + " ");
            }
            if (i == 0) {
                out.print("[default]");
            }
            out.println();
        }
        out.println(" Compilation options, must be specified before -C");
        printOption(out, "-d <dirname>", "Directory to place .class files in");
        printOption(out, "-P <prefix>", "Prefix to prepand to class names");
        printOption(out, "-T <topname>", "name to give to top-level class");
        printOption(out, "--main", "Generate an application, with a main method");
        printOption(out, "--applet", "Generate an applet");
        printOption(out, "--servlet", "Generate a servlet");
        printOption(out, "--module-static", "Top-level definitions are by default static");
        ArrayList<String> keys = Compilation.options.keys();
        for (int i2 = 0; i2 < keys.size(); i2++) {
            String name = keys.get(i2);
            printOption(out, "--" + name, Compilation.options.getDoc(name));
        }
        out.println();
        out.println("For more information go to:  http://www.gnu.org/software/kawa/");
    }

    static void checkInitFile() {
        Object scmHomeDirectory;
        if (homeDirectory == null) {
            File initFile = null;
            homeDirectory = System.getProperty("user.home");
            if (homeDirectory != null) {
                scmHomeDirectory = new FString(homeDirectory);
                initFile = new File(homeDirectory, "/".equals(System.getProperty("file.separator")) ? ".kawarc.scm" : "kawarc.scm");
            } else {
                scmHomeDirectory = Boolean.FALSE;
            }
            Environment.getCurrent().put("home-directory", scmHomeDirectory);
            if (initFile != null && initFile.exists() && !Shell.runFileOrClass(initFile.getPath(), true, 0)) {
                System.exit(-1);
            }
        }
    }

    public static void setArgs(String[] args, int arg_start) {
        ApplicationMainSupport.setArgs(args, arg_start);
    }

    public static void getLanguageFromFilenameExtension(String name) {
        if (previousLanguage == null) {
            previousLanguage = Language.getInstanceFromFilenameExtension(name);
            if (previousLanguage != null) {
                Language.setDefaults(previousLanguage);
                return;
            }
        }
        getLanguage();
    }

    public static void getLanguage() {
        if (previousLanguage == null) {
            previousLanguage = Language.getInstance((String) null);
            Language.setDefaults(previousLanguage);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:345:0x06c5, code lost:
        if (r4 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:346:0x06c7, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:406:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int processArgs(java.lang.String[] r16, int r17, int r18) {
        /*
            r1 = r16
            r2 = r18
            r3 = 0
            r0 = r17
            r4 = 0
        L_0x0008:
            r5 = -1
            if (r0 >= r2) goto L_0x06c5
            r6 = r1[r0]
            java.lang.String r7 = "-c"
            boolean r8 = r6.equals(r7)
            r9 = 1
            if (r8 != 0) goto L_0x067d
            java.lang.String r8 = "-e"
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L_0x0020
            goto L_0x067d
        L_0x0020:
            java.lang.String r7 = "-f"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x0049
            int r0 = r0 + 1
            if (r0 != r2) goto L_0x002f
            bad_option(r6)
        L_0x002f:
            r4 = r1[r0]
            getLanguageFromFilenameExtension(r4)
            int r6 = r0 + 1
            setArgs(r1, r6)
            checkInitFile()
            boolean r4 = kawa.Shell.runFileOrClass(r4, r9, r3)
            if (r4 != 0) goto L_0x0045
            java.lang.System.exit(r5)
        L_0x0045:
            r4 = 1
            goto L_0x06c1
        L_0x0049:
            java.lang.String r7 = "--script"
            boolean r7 = r6.startsWith(r7)
            if (r7 == 0) goto L_0x0081
            r4 = 8
            java.lang.String r4 = r6.substring(r4)
            int r0 = r0 + r9
            int r7 = r4.length()
            if (r7 <= 0) goto L_0x0066
            int r3 = java.lang.Integer.parseInt(r4)     // Catch:{ all -> 0x0064 }
            goto L_0x0066
        L_0x0064:
            r0 = move-exception
            r0 = r2
        L_0x0066:
            if (r0 != r2) goto L_0x006b
            bad_option(r6)
        L_0x006b:
            r2 = r1[r0]
            getLanguageFromFilenameExtension(r2)
            int r0 = r0 + r9
            setArgs(r1, r0)
            checkInitFile()
            boolean r0 = kawa.Shell.runFileOrClass(r2, r9, r3)
            if (r0 != 0) goto L_0x0080
            java.lang.System.exit(r5)
        L_0x0080:
            return r5
        L_0x0081:
            java.lang.String r7 = "\\"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x01c1
            int r0 = r0 + r9
            if (r0 != r2) goto L_0x008f
            bad_option(r6)
        L_0x008f:
            r2 = r1[r0]
            gnu.text.SourceMessages r4 = new gnu.text.SourceMessages
            r4.<init>()
            java.io.BufferedInputStream r6 = new java.io.BufferedInputStream     // Catch:{ all -> 0x01b4 }
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ all -> 0x01b4 }
            r7.<init>(r2)     // Catch:{ all -> 0x01b4 }
            r6.<init>(r7)     // Catch:{ all -> 0x01b4 }
            int r7 = r6.read()     // Catch:{ all -> 0x01b4 }
            r10 = 35
            if (r7 != r10) goto L_0x0175
            java.lang.StringBuffer r10 = new java.lang.StringBuffer     // Catch:{ all -> 0x01b4 }
            r11 = 100
            r10.<init>(r11)     // Catch:{ all -> 0x01b4 }
            java.util.Vector r11 = new java.util.Vector     // Catch:{ all -> 0x01b4 }
            r12 = 10
            r11.<init>(r12)     // Catch:{ all -> 0x01b4 }
        L_0x00b7:
            r13 = 13
            if (r7 == r12) goto L_0x00c4
            if (r7 == r13) goto L_0x00c4
            if (r7 < 0) goto L_0x00c4
            int r7 = r6.read()     // Catch:{ all -> 0x01b4 }
            goto L_0x00b7
        L_0x00c4:
            r7 = 0
        L_0x00c5:
            int r14 = r6.read()     // Catch:{ all -> 0x01b4 }
            r15 = 39
            if (r14 >= 0) goto L_0x00ec
            java.io.PrintStream r9 = java.lang.System.err     // Catch:{ all -> 0x01b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b4 }
            r3.<init>()     // Catch:{ all -> 0x01b4 }
            java.lang.String r8 = "unexpected end-of-file processing argument line for: '"
            java.lang.StringBuilder r3 = r3.append(r8)     // Catch:{ all -> 0x01b4 }
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch:{ all -> 0x01b4 }
            java.lang.StringBuilder r3 = r3.append(r15)     // Catch:{ all -> 0x01b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01b4 }
            r9.println(r3)     // Catch:{ all -> 0x01b4 }
            java.lang.System.exit(r5)     // Catch:{ all -> 0x01b4 }
        L_0x00ec:
            r3 = 92
            if (r7 != 0) goto L_0x0161
            if (r14 == r3) goto L_0x015b
            if (r14 == r15) goto L_0x015b
            r3 = 34
            if (r14 != r3) goto L_0x00f9
            goto L_0x015b
        L_0x00f9:
            if (r14 == r12) goto L_0x011a
            if (r14 != r13) goto L_0x00fe
            goto L_0x011a
        L_0x00fe:
            r3 = 32
            if (r14 == r3) goto L_0x0106
            r3 = 9
            if (r14 != r3) goto L_0x016d
        L_0x0106:
            int r3 = r10.length()     // Catch:{ all -> 0x01b4 }
            if (r3 <= 0) goto L_0x0117
            java.lang.String r3 = r10.toString()     // Catch:{ all -> 0x01b4 }
            r11.addElement(r3)     // Catch:{ all -> 0x01b4 }
            r3 = 0
            r10.setLength(r3)     // Catch:{ all -> 0x01b4 }
        L_0x0117:
            r3 = 0
            r9 = 1
            goto L_0x00c5
        L_0x011a:
            int r3 = r10.length()     // Catch:{ all -> 0x01b4 }
            if (r3 <= 0) goto L_0x0127
            java.lang.String r3 = r10.toString()     // Catch:{ all -> 0x01b4 }
            r11.addElement(r3)     // Catch:{ all -> 0x01b4 }
        L_0x0127:
            int r3 = r11.size()     // Catch:{ all -> 0x01b4 }
            if (r3 <= 0) goto L_0x0175
            java.lang.String[] r7 = new java.lang.String[r3]     // Catch:{ all -> 0x01b4 }
            r11.copyInto(r7)     // Catch:{ all -> 0x01b4 }
            r8 = 0
            int r7 = processArgs(r7, r8, r3)     // Catch:{ all -> 0x01b4 }
            if (r7 < 0) goto L_0x0175
            if (r7 >= r3) goto L_0x0175
            java.io.PrintStream r8 = java.lang.System.err     // Catch:{ all -> 0x01b4 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b4 }
            r9.<init>()     // Catch:{ all -> 0x01b4 }
            java.lang.String r10 = ""
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x01b4 }
            int r3 = r3 - r7
            java.lang.StringBuilder r3 = r9.append(r3)     // Catch:{ all -> 0x01b4 }
            java.lang.String r7 = " unused meta args"
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ all -> 0x01b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01b4 }
            r8.println(r3)     // Catch:{ all -> 0x01b4 }
            goto L_0x0175
        L_0x015b:
            r7 = r14
            r3 = 0
            r9 = 1
            goto L_0x00c5
        L_0x0161:
            if (r7 != r3) goto L_0x0165
            r7 = 0
            goto L_0x016d
        L_0x0165:
            if (r14 != r7) goto L_0x016d
            r3 = 0
            r7 = 0
            r9 = 1
            goto L_0x00c5
        L_0x016d:
            char r3 = (char) r14     // Catch:{ all -> 0x01b4 }
            r10.append(r3)     // Catch:{ all -> 0x01b4 }
            r3 = 0
            r9 = 1
            goto L_0x00c5
        L_0x0175:
            getLanguageFromFilenameExtension(r2)     // Catch:{ all -> 0x01b4 }
            gnu.mapping.InPort r12 = gnu.mapping.InPort.openFile(r6, r2)     // Catch:{ all -> 0x01b4 }
            r2 = 1
            int r0 = r0 + r2
            setArgs(r1, r0)     // Catch:{ all -> 0x01b4 }
            checkInitFile()     // Catch:{ all -> 0x01b4 }
            gnu.mapping.OutPort r0 = gnu.mapping.OutPort.errDefault()     // Catch:{ all -> 0x01b4 }
            gnu.expr.Language r10 = gnu.expr.Language.getDefaultLanguage()     // Catch:{ all -> 0x01b4 }
            gnu.mapping.Environment r11 = gnu.mapping.Environment.getCurrent()     // Catch:{ all -> 0x01b4 }
            gnu.mapping.OutPort r13 = gnu.mapping.OutPort.outDefault()     // Catch:{ all -> 0x01b4 }
            r14 = 0
            r15 = r4
            java.lang.Throwable r1 = kawa.Shell.run((gnu.expr.Language) r10, (gnu.mapping.Environment) r11, (gnu.mapping.InPort) r12, (gnu.mapping.OutPort) r13, (gnu.mapping.OutPort) r14, (gnu.text.SourceMessages) r15)     // Catch:{ all -> 0x01b4 }
            r2 = 20
            r4.printAll((java.io.PrintWriter) r0, (int) r2)     // Catch:{ all -> 0x01b4 }
            if (r1 == 0) goto L_0x01b3
            boolean r0 = r1 instanceof gnu.text.SyntaxException     // Catch:{ all -> 0x01b4 }
            if (r0 == 0) goto L_0x01b2
            r0 = r1
            gnu.text.SyntaxException r0 = (gnu.text.SyntaxException) r0     // Catch:{ all -> 0x01b4 }
            gnu.text.SourceMessages r0 = r0.getMessages()     // Catch:{ all -> 0x01b4 }
            if (r0 != r4) goto L_0x01b2
            r2 = 1
            java.lang.System.exit(r2)     // Catch:{ all -> 0x01b4 }
        L_0x01b2:
            throw r1     // Catch:{ all -> 0x01b4 }
        L_0x01b3:
            goto L_0x01c0
        L_0x01b4:
            r0 = move-exception
            gnu.mapping.OutPort r1 = gnu.mapping.OutPort.errDefault()
            kawa.Shell.printError(r0, r4, r1)
            r1 = 1
            java.lang.System.exit(r1)
        L_0x01c0:
            return r5
        L_0x01c1:
            java.lang.String r3 = "-s"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L_0x0666
            java.lang.String r3 = "--"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x01d3
            goto L_0x0666
        L_0x01d3:
            java.lang.String r3 = "-w"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x01ed
            int r0 = r0 + 1
            getLanguage()
            setArgs(r1, r0)
            checkInitFile()
            startGuiConsole()
            r3 = 0
            r4 = 1
            goto L_0x06c1
        L_0x01ed:
            java.lang.String r3 = "-d"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x0208
            int r0 = r0 + 1
            if (r0 != r2) goto L_0x01fc
            bad_option(r6)
        L_0x01fc:
            gnu.expr.ModuleManager r3 = gnu.expr.ModuleManager.getInstance()
            r5 = r1[r0]
            r3.setCompilationDirectory(r5)
            r3 = 0
            goto L_0x06c1
        L_0x0208:
            java.lang.String r3 = "--target"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L_0x05e8
            java.lang.String r3 = "target"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x021b
            r3 = 0
            goto L_0x05e9
        L_0x021b:
            java.lang.String r3 = "-P"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x0231
            int r0 = r0 + 1
            if (r0 != r2) goto L_0x022a
            bad_option(r6)
        L_0x022a:
            r3 = r1[r0]
            gnu.expr.Compilation.classPrefixDefault = r3
            r3 = 0
            goto L_0x06c1
        L_0x0231:
            java.lang.String r3 = "-T"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x0247
            int r0 = r0 + 1
            if (r0 != r2) goto L_0x0240
            bad_option(r6)
        L_0x0240:
            r3 = r1[r0]
            compilationTopname = r3
            r3 = 0
            goto L_0x06c1
        L_0x0247:
            java.lang.String r3 = "-C"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x025a
            r3 = 1
            int r0 = r0 + r3
            if (r0 != r2) goto L_0x0256
            bad_option(r6)
        L_0x0256:
            compileFiles(r1, r0, r2)
            return r5
        L_0x025a:
            java.lang.String r3 = "--output-format"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L_0x05d9
            java.lang.String r3 = "--format"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x026d
            r3 = 0
            goto L_0x05da
        L_0x026d:
            java.lang.String r3 = "--connect"
            boolean r3 = r6.equals(r3)
            r7 = 0
            java.lang.String r8 = "-"
            if (r3 == 0) goto L_0x02d2
            int r3 = r0 + 1
            if (r3 != r2) goto L_0x027f
            bad_option(r6)
        L_0x027f:
            r0 = r1[r3]
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0289
            r5 = 0
            goto L_0x0297
        L_0x0289:
            r0 = r1[r3]     // Catch:{ NumberFormatException -> 0x0290 }
            int r5 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0290 }
            goto L_0x0297
        L_0x0290:
            r0 = move-exception
            java.lang.String r0 = "--connect port#"
            bad_option(r0)
        L_0x0297:
            java.net.Socket r0 = new java.net.Socket     // Catch:{ IOException -> 0x02c2 }
            java.net.InetAddress r6 = java.net.InetAddress.getByName(r7)     // Catch:{ IOException -> 0x02c2 }
            r0.<init>(r6, r5)     // Catch:{ IOException -> 0x02c2 }
            kawa.Telnet r5 = new kawa.Telnet     // Catch:{ IOException -> 0x02c2 }
            r6 = 1
            r5.<init>(r0, r6)     // Catch:{ IOException -> 0x02c2 }
            kawa.TelnetInputStream r0 = r5.getInputStream()     // Catch:{ IOException -> 0x02c2 }
            kawa.TelnetOutputStream r5 = r5.getOutputStream()     // Catch:{ IOException -> 0x02c2 }
            java.io.PrintStream r6 = new java.io.PrintStream     // Catch:{ IOException -> 0x02c2 }
            r7 = 1
            r6.<init>(r5, r7)     // Catch:{ IOException -> 0x02c2 }
            java.lang.System.setIn(r0)     // Catch:{ IOException -> 0x02c2 }
            java.lang.System.setOut(r6)     // Catch:{ IOException -> 0x02c2 }
            java.lang.System.setErr(r6)     // Catch:{ IOException -> 0x02c2 }
            r0 = r3
            r3 = 0
            goto L_0x06c1
        L_0x02c2:
            r0 = move-exception
            java.io.PrintStream r1 = java.lang.System.err
            r0.printStackTrace(r1)
            java.lang.Error r1 = new java.lang.Error
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x02d2:
            java.lang.String r3 = "--server"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x036a
            getLanguage()
            r3 = 1
            int r0 = r0 + r3
            if (r0 != r2) goto L_0x02e4
            bad_option(r6)
        L_0x02e4:
            r2 = r1[r0]
            boolean r2 = r2.equals(r8)
            if (r2 == 0) goto L_0x02ee
            r3 = 0
            goto L_0x02fc
        L_0x02ee:
            r0 = r1[r0]     // Catch:{ NumberFormatException -> 0x02f5 }
            int r3 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x02f5 }
            goto L_0x02fc
        L_0x02f5:
            r0 = move-exception
            java.lang.String r0 = "--server port#"
            bad_option(r0)
            r3 = -1
        L_0x02fc:
            java.net.ServerSocket r0 = new java.net.ServerSocket     // Catch:{ IOException -> 0x035f }
            r0.<init>(r3)     // Catch:{ IOException -> 0x035f }
            int r1 = r0.getLocalPort()     // Catch:{ IOException -> 0x035f }
            java.io.PrintStream r2 = java.lang.System.err     // Catch:{ IOException -> 0x035f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x035f }
            r3.<init>()     // Catch:{ IOException -> 0x035f }
            java.lang.String r4 = "Listening on port "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x035f }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ IOException -> 0x035f }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x035f }
            r2.println(r1)     // Catch:{ IOException -> 0x035f }
        L_0x031d:
            java.io.PrintStream r1 = java.lang.System.err     // Catch:{ IOException -> 0x035f }
            java.lang.String r2 = "waiting ... "
            r1.print(r2)     // Catch:{ IOException -> 0x035f }
            java.io.PrintStream r1 = java.lang.System.err     // Catch:{ IOException -> 0x035f }
            r1.flush()     // Catch:{ IOException -> 0x035f }
            java.net.Socket r1 = r0.accept()     // Catch:{ IOException -> 0x035f }
            java.io.PrintStream r2 = java.lang.System.err     // Catch:{ IOException -> 0x035f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x035f }
            r3.<init>()     // Catch:{ IOException -> 0x035f }
            java.lang.String r4 = "got connection from "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x035f }
            java.net.InetAddress r4 = r1.getInetAddress()     // Catch:{ IOException -> 0x035f }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x035f }
            java.lang.String r4 = " port:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x035f }
            int r4 = r1.getPort()     // Catch:{ IOException -> 0x035f }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x035f }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x035f }
            r2.println(r3)     // Catch:{ IOException -> 0x035f }
            gnu.expr.Language r2 = gnu.expr.Language.getDefaultLanguage()     // Catch:{ IOException -> 0x035f }
            kawa.TelnetRepl.serve(r2, r1)     // Catch:{ IOException -> 0x035f }
            goto L_0x031d
        L_0x035f:
            r0 = move-exception
            java.lang.Error r1 = new java.lang.Error
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x036a:
            java.lang.String r3 = "--http-auto-handler"
            boolean r3 = r6.equals(r3)
            java.lang.String r8 = "kawa: HttpServer classes not found"
            if (r3 == 0) goto L_0x0386
            int r0 = r0 + 2
            if (r0 < r2) goto L_0x037b
            bad_option(r6)
        L_0x037b:
            java.io.PrintStream r3 = java.lang.System.err
            r3.println(r8)
            java.lang.System.exit(r5)
            r3 = 0
            goto L_0x06c1
        L_0x0386:
            java.lang.String r3 = "--http-start"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x03a2
            int r0 = r0 + 1
            if (r0 < r2) goto L_0x0397
            java.lang.String r3 = "missing httpd port argument"
            bad_option(r3)
        L_0x0397:
            java.io.PrintStream r3 = java.lang.System.err
            r3.println(r8)
            java.lang.System.exit(r5)
            r3 = 0
            goto L_0x06c1
        L_0x03a2:
            java.lang.String r3 = "--main"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x03b0
            r3 = 1
            gnu.expr.Compilation.generateMainDefault = r3
            r3 = 0
            goto L_0x06c1
        L_0x03b0:
            java.lang.String r3 = "--applet"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x03c1
            int r3 = defaultParseOptions
            r3 = r3 | 16
            defaultParseOptions = r3
            r3 = 0
            goto L_0x06c1
        L_0x03c1:
            java.lang.String r3 = "--servlet"
            boolean r3 = r6.equals(r3)
            r8 = 2
            if (r3 == 0) goto L_0x03d6
            int r3 = defaultParseOptions
            r5 = 32
            r3 = r3 | r5
            defaultParseOptions = r3
            gnu.kawa.servlet.HttpRequestContext.importServletDefinitions = r8
            r3 = 0
            goto L_0x06c1
        L_0x03d6:
            java.lang.String r3 = "--debug-dump-zip"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x03e5
            java.lang.String r3 = "kawa-zip-dump-"
            gnu.expr.ModuleExp.dumpZipPrefix = r3
            r3 = 0
            goto L_0x06c1
        L_0x03e5:
            java.lang.String r3 = "--debug-print-expr"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x03f3
            r3 = 1
            gnu.expr.Compilation.debugPrintExpr = r3
            r3 = 0
            goto L_0x06c1
        L_0x03f3:
            r3 = 1
            java.lang.String r9 = "--debug-print-final-expr"
            boolean r9 = r6.equals(r9)
            if (r9 == 0) goto L_0x0401
            gnu.expr.Compilation.debugPrintFinalExpr = r3
            r3 = 0
            goto L_0x06c1
        L_0x0401:
            java.lang.String r9 = "--debug-error-prints-stack-trace"
            boolean r9 = r6.equals(r9)
            if (r9 == 0) goto L_0x040e
            gnu.text.SourceMessages.debugStackTraceOnError = r3
            r3 = 0
            goto L_0x06c1
        L_0x040e:
            java.lang.String r9 = "--debug-warning-prints-stack-trace"
            boolean r9 = r6.equals(r9)
            if (r9 == 0) goto L_0x041b
            gnu.text.SourceMessages.debugStackTraceOnWarning = r3
            r3 = 0
            goto L_0x06c1
        L_0x041b:
            java.lang.String r3 = "--module-nonstatic"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L_0x05d4
            java.lang.String r3 = "--no-module-static"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x042e
            r3 = 0
            goto L_0x05d5
        L_0x042e:
            java.lang.String r3 = "--module-static"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x043c
            r3 = 1
            gnu.expr.Compilation.moduleStatic = r3
            r3 = 0
            goto L_0x06c1
        L_0x043c:
            java.lang.String r3 = "--module-static-run"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x0449
            gnu.expr.Compilation.moduleStatic = r8
            r3 = 0
            goto L_0x06c1
        L_0x0449:
            java.lang.String r3 = "--no-inline"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L_0x05cf
            java.lang.String r3 = "--inline=none"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x045b
            goto L_0x05cf
        L_0x045b:
            java.lang.String r3 = "--no-console"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x0469
            r3 = 1
            noConsole = r3
            r3 = 0
            goto L_0x06c1
        L_0x0469:
            r3 = 1
            java.lang.String r9 = "--inline"
            boolean r9 = r6.equals(r9)
            if (r9 == 0) goto L_0x0477
            gnu.expr.Compilation.inlineOk = r3
            r3 = 0
            goto L_0x06c1
        L_0x0477:
            java.lang.String r3 = "--cps"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x0485
            r3 = 4
            gnu.expr.Compilation.defaultCallConvention = r3
            r3 = 0
            goto L_0x06c1
        L_0x0485:
            java.lang.String r3 = "--full-tailcalls"
            boolean r3 = r6.equals(r3)
            r9 = 3
            if (r3 == 0) goto L_0x0493
            gnu.expr.Compilation.defaultCallConvention = r9
            r3 = 0
            goto L_0x06c1
        L_0x0493:
            java.lang.String r3 = "--no-full-tailcalls"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x04a1
            r3 = 1
            gnu.expr.Compilation.defaultCallConvention = r3
            r3 = 0
            goto L_0x06c1
        L_0x04a1:
            r3 = 1
            java.lang.String r10 = "--pedantic"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x04af
            gnu.expr.Language.requirePedantic = r3
            r3 = 0
            goto L_0x06c1
        L_0x04af:
            java.lang.String r3 = "--help"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x04c2
            java.io.PrintStream r3 = java.lang.System.out
            printOptions(r3)
            r3 = 0
            java.lang.System.exit(r3)
            goto L_0x06c1
        L_0x04c2:
            r3 = 0
            java.lang.String r10 = "--author"
            boolean r10 = r6.equals(r10)
            if (r10 == 0) goto L_0x04d7
            java.io.PrintStream r5 = java.lang.System.out
            java.lang.String r6 = "Per Bothner <per@bothner.com>"
            r5.println(r6)
            java.lang.System.exit(r3)
            goto L_0x06c1
        L_0x04d7:
            java.lang.String r3 = "--version"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x04ff
            java.io.PrintStream r3 = java.lang.System.out
            java.lang.String r4 = "Kawa "
            r3.print(r4)
            java.io.PrintStream r3 = java.lang.System.out
            java.lang.String r4 = kawa.Version.getVersion()
            r3.print(r4)
            java.io.PrintStream r3 = java.lang.System.out
            r3.println()
            java.io.PrintStream r3 = java.lang.System.out
            java.lang.String r4 = "Copyright (C) 2009 Per Bothner"
            r3.println(r4)
            r3 = 0
            r4 = 1
            goto L_0x06c1
        L_0x04ff:
            int r3 = r6.length()
            if (r3 <= 0) goto L_0x05c4
            r3 = 0
            char r10 = r6.charAt(r3)
            r11 = 45
            if (r10 != r11) goto L_0x05c4
            int r10 = r6.length()
            if (r10 <= r8) goto L_0x0529
            char r10 = r6.charAt(r3)
            if (r10 != r11) goto L_0x0529
            r3 = 1
            char r10 = r6.charAt(r3)
            if (r10 != r11) goto L_0x0523
            goto L_0x0524
        L_0x0523:
            r8 = 1
        L_0x0524:
            java.lang.String r3 = r6.substring(r8)
            goto L_0x052a
        L_0x0529:
            r3 = r6
        L_0x052a:
            gnu.expr.Language r8 = gnu.expr.Language.getInstance(r3)
            if (r8 == 0) goto L_0x053f
            gnu.expr.Language r3 = previousLanguage
            if (r3 != 0) goto L_0x0538
            gnu.expr.Language.setDefaults(r8)
            goto L_0x053b
        L_0x0538:
            gnu.expr.Language.setCurrentLanguage(r8)
        L_0x053b:
            previousLanguage = r8
            goto L_0x05c1
        L_0x053f:
            java.lang.String r8 = "="
            int r8 = r3.indexOf(r8)
            if (r8 >= 0) goto L_0x0548
            goto L_0x0553
        L_0x0548:
            int r7 = r8 + 1
            java.lang.String r7 = r3.substring(r7)
            r10 = 0
            java.lang.String r3 = r3.substring(r10, r8)
        L_0x0553:
            java.lang.String r8 = "no-"
            boolean r8 = r3.startsWith(r8)
            if (r8 == 0) goto L_0x0563
            int r8 = r3.length()
            if (r8 <= r9) goto L_0x0563
            r8 = 1
            goto L_0x0564
        L_0x0563:
            r8 = 0
        L_0x0564:
            if (r7 != 0) goto L_0x056f
            if (r8 == 0) goto L_0x056f
            java.lang.String r3 = r3.substring(r9)
            java.lang.String r7 = "no"
        L_0x056f:
            gnu.text.Options r9 = gnu.expr.Compilation.options
            java.lang.String r3 = r9.set((java.lang.String) r3, (java.lang.String) r7)
            if (r3 == 0) goto L_0x05c1
            java.lang.String r9 = "unknown option name"
            if (r8 == 0) goto L_0x0596
            if (r3 != r9) goto L_0x0596
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r8 = "both '--no-' prefix and '="
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r7 = "' specified"
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r3 = r3.toString()
        L_0x0596:
            if (r3 != r9) goto L_0x059c
            bad_option(r6)
            goto L_0x05c1
        L_0x059c:
            java.io.PrintStream r7 = java.lang.System.err
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "kawa: bad option '"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r6 = r8.append(r6)
            java.lang.String r8 = "': "
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.StringBuilder r3 = r6.append(r3)
            java.lang.String r3 = r3.toString()
            r7.println(r3)
            java.lang.System.exit(r5)
        L_0x05c1:
            r3 = 0
            goto L_0x06c1
        L_0x05c4:
            boolean r3 = gnu.expr.ApplicationMainSupport.processSetProperty(r6)
            if (r3 != 0) goto L_0x05cc
            goto L_0x06c5
        L_0x05cc:
            r3 = 0
            goto L_0x06c1
        L_0x05cf:
            r3 = 0
            gnu.expr.Compilation.inlineOk = r3
            goto L_0x06c1
        L_0x05d4:
            r3 = 0
        L_0x05d5:
            gnu.expr.Compilation.moduleStatic = r5
            goto L_0x06c1
        L_0x05d9:
            r3 = 0
        L_0x05da:
            int r0 = r0 + 1
            if (r0 != r2) goto L_0x05e1
            bad_option(r6)
        L_0x05e1:
            r5 = r1[r0]
            kawa.Shell.setDefaultFormat(r5)
            goto L_0x06c1
        L_0x05e8:
            r3 = 0
        L_0x05e9:
            int r0 = r0 + 1
            if (r0 != r2) goto L_0x05f0
            bad_option(r6)
        L_0x05f0:
            r5 = r1[r0]
            java.lang.String r6 = "7"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x05fe
            r6 = 3342336(0x330000, float:4.68361E-39)
            gnu.expr.Compilation.defaultClassFileVersion = r6
        L_0x05fe:
            java.lang.String r6 = "6"
            boolean r6 = r5.equals(r6)
            if (r6 != 0) goto L_0x0661
            java.lang.String r6 = "1.6"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x060f
            goto L_0x0661
        L_0x060f:
            java.lang.String r6 = "5"
            boolean r6 = r5.equals(r6)
            if (r6 != 0) goto L_0x065c
            java.lang.String r6 = "1.5"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x0620
            goto L_0x065c
        L_0x0620:
            java.lang.String r6 = "1.4"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x062e
            r5 = 3145728(0x300000, float:4.408104E-39)
            gnu.expr.Compilation.defaultClassFileVersion = r5
            goto L_0x06c1
        L_0x062e:
            java.lang.String r6 = "1.3"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x063c
            r5 = 3080192(0x2f0000, float:4.316268E-39)
            gnu.expr.Compilation.defaultClassFileVersion = r5
            goto L_0x06c1
        L_0x063c:
            java.lang.String r6 = "1.2"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x064a
            r5 = 3014656(0x2e0000, float:4.224433E-39)
            gnu.expr.Compilation.defaultClassFileVersion = r5
            goto L_0x06c1
        L_0x064a:
            java.lang.String r6 = "1.1"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x0658
            r5 = 2949123(0x2d0003, float:4.132602E-39)
            gnu.expr.Compilation.defaultClassFileVersion = r5
            goto L_0x06c1
        L_0x0658:
            bad_option(r5)
            goto L_0x06c1
        L_0x065c:
            r5 = 3211264(0x310000, float:4.49994E-39)
            gnu.expr.Compilation.defaultClassFileVersion = r5
            goto L_0x06c1
        L_0x0661:
            r5 = 3276800(0x320000, float:4.591775E-39)
            gnu.expr.Compilation.defaultClassFileVersion = r5
            goto L_0x06c1
        L_0x0666:
            r2 = 1
            int r0 = r0 + r2
            getLanguage()
            setArgs(r1, r0)
            checkInitFile()
            gnu.expr.Language r0 = gnu.expr.Language.getDefaultLanguage()
            gnu.mapping.Environment r1 = gnu.mapping.Environment.getCurrent()
            kawa.Shell.run(r0, r1)
            return r5
        L_0x067d:
            int r0 = r0 + 1
            if (r0 != r2) goto L_0x0684
            bad_option(r6)
        L_0x0684:
            getLanguage()
            int r4 = r0 + 1
            setArgs(r1, r4)
            boolean r4 = r6.equals(r7)
            if (r4 == 0) goto L_0x0695
            checkInitFile()
        L_0x0695:
            gnu.expr.Language r6 = gnu.expr.Language.getDefaultLanguage()
            gnu.text.SourceMessages r4 = new gnu.text.SourceMessages
            r4.<init>()
            gnu.mapping.Environment r7 = gnu.mapping.Environment.getCurrent()
            gnu.mapping.CharArrayInPort r8 = new gnu.mapping.CharArrayInPort
            r9 = r1[r0]
            r8.<init>((java.lang.String) r9)
            gnu.mapping.OutPort r9 = gnu.mapping.OutPort.outDefault()
            r10 = 0
            r11 = r4
            java.lang.Throwable r6 = kawa.Shell.run((gnu.expr.Language) r6, (gnu.mapping.Environment) r7, (gnu.mapping.InPort) r8, (gnu.mapping.OutPort) r9, (gnu.mapping.OutPort) r10, (gnu.text.SourceMessages) r11)
            if (r6 == 0) goto L_0x06bf
            gnu.mapping.OutPort r7 = gnu.mapping.OutPort.errDefault()
            kawa.Shell.printError(r6, r4, r7)
            java.lang.System.exit(r5)
        L_0x06bf:
            r4 = 1
        L_0x06c1:
            r5 = 1
            int r0 = r0 + r5
            goto L_0x0008
        L_0x06c5:
            if (r4 == 0) goto L_0x06c8
            r0 = -1
        L_0x06c8:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.repl.processArgs(java.lang.String[], int, int):int");
    }

    public static void compileFiles(String[] strArr, int i, int i2) {
        ModuleManager instance = ModuleManager.getInstance();
        int i3 = i2 - i;
        Compilation[] compilationArr = new Compilation[i3];
        ModuleInfo[] moduleInfoArr = new ModuleInfo[i3];
        SourceMessages sourceMessages = new SourceMessages();
        for (int i4 = i; i4 < i2; i4++) {
            String str = strArr[i4];
            getLanguageFromFilenameExtension(str);
            try {
                Compilation parse = Language.getDefaultLanguage().parse(InPort.openFile(str), sourceMessages, defaultParseOptions);
                if (compilationTopname != null) {
                    ClassType classType = new ClassType(Compilation.mangleNameIfNeeded(compilationTopname));
                    ModuleExp module = parse.getModule();
                    module.setType(classType);
                    module.setName(compilationTopname);
                    parse.mainClass = classType;
                }
                int i5 = i4 - i;
                moduleInfoArr[i5] = instance.find(parse);
                compilationArr[i5] = parse;
            } catch (FileNotFoundException e) {
                System.err.println(e);
                System.exit(-1);
            } catch (Throwable th) {
                if (!(th instanceof SyntaxException) || th.getMessages() != sourceMessages) {
                    internalError(th, (Compilation) null, str);
                }
            }
            if (sourceMessages.seenErrorsOrWarnings()) {
                System.err.println("(compiling " + str + ')');
                if (sourceMessages.checkErrors(System.err, 20)) {
                    System.exit(1);
                }
            }
        }
        for (int i6 = i; i6 < i2; i6++) {
            String str2 = strArr[i6];
            int i7 = i6 - i;
            Compilation compilation = compilationArr[i7];
            try {
                System.err.println("(compiling " + str2 + " to " + compilation.mainClass.getName() + ')');
                moduleInfoArr[i7].loadByStages(14);
                boolean seenErrors = sourceMessages.seenErrors();
                sourceMessages.checkErrors(System.err, 50);
                if (seenErrors) {
                    System.exit(-1);
                }
                compilationArr[i7] = compilation;
                boolean seenErrors2 = sourceMessages.seenErrors();
                sourceMessages.checkErrors(System.err, 50);
                if (seenErrors2) {
                    System.exit(-1);
                }
            } catch (Throwable th2) {
                internalError(th2, compilation, str2);
            }
        }
    }

    static void internalError(Throwable ex, Compilation comp, Object arg) {
        StringBuffer sbuf = new StringBuffer();
        if (comp != null) {
            String file = comp.getFileName();
            int line = comp.getLineNumber();
            if (file != null && line > 0) {
                sbuf.append(file);
                sbuf.append(':');
                sbuf.append(line);
                sbuf.append(": ");
            }
        }
        sbuf.append("internal error while compiling ");
        sbuf.append(arg);
        System.err.println(sbuf.toString());
        ex.printStackTrace(System.err);
        System.exit(-1);
    }

    public static void main(String[] args) {
        try {
            int iArg = processArgs(args, 0, args.length);
            if (iArg >= 0) {
                if (iArg < args.length) {
                    String filename = args[iArg];
                    getLanguageFromFilenameExtension(filename);
                    setArgs(args, iArg + 1);
                    checkInitFile();
                    boolean runFileOrClass = Shell.runFileOrClass(filename, false, 0);
                } else {
                    getLanguage();
                    setArgs(args, iArg);
                    checkInitFile();
                    if (shouldUseGuiConsole()) {
                        startGuiConsole();
                    } else if (!Shell.run(Language.getDefaultLanguage(), Environment.getCurrent())) {
                        System.exit(-1);
                    }
                }
                if (shutdownRegistered == 0) {
                    OutPort.runCleanups();
                }
                ModuleBody.exitDecrement();
            }
        } finally {
            if (!shutdownRegistered) {
                OutPort.runCleanups();
            }
            ModuleBody.exitDecrement();
        }
    }

    public static boolean shouldUseGuiConsole() {
        if (noConsole) {
            return true;
        }
        try {
            if (Class.forName("java.lang.System").getMethod("console", new Class[0]).invoke(new Object[0], new Object[0]) == null) {
                return true;
            }
            return false;
        } catch (Throwable th) {
        }
    }

    private static void startGuiConsole() {
        try {
            Class.forName("kawa.GuiConsole").newInstance();
        } catch (Exception ex) {
            System.err.println("failed to create Kawa window: " + ex);
            System.exit(-1);
        }
    }
}
