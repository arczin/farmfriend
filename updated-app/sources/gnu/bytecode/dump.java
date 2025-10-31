package gnu.bytecode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class dump extends ClassFileInput {
    ClassTypeWriter writer;

    dump(InputStream str, ClassTypeWriter writer2) throws IOException, ClassFormatError {
        super(str);
        this.ctype = new ClassType();
        readFormatVersion();
        readConstants();
        readClassInfo();
        readFields();
        readMethods();
        readAttributes(this.ctype);
        writer2.print(this.ctype);
        writer2.flush();
    }

    public ConstantPool readConstants() throws IOException {
        this.ctype.constants = super.readConstants();
        return this.ctype.constants;
    }

    public Attribute readAttribute(String name, int length, AttrContainer container) throws IOException {
        return super.readAttribute(name, length, container);
    }

    static int readMagic(InputStream in) throws IOException {
        int b;
        int magic = 0;
        for (int j = 0; j < 4 && (b = in.read()) >= 0; j++) {
            magic = (magic << 8) | (b & 255);
        }
        return magic;
    }

    public static void process(InputStream in, String filename, OutputStream out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter((ClassType) null, out, flags));
    }

    public static void process(InputStream in, String filename, Writer out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter((ClassType) null, out, flags));
    }

    public static void process(InputStream in, String filename, ClassTypeWriter out) throws IOException {
        InputStream inp = new BufferedInputStream(in);
        inp.mark(5);
        int magic = readMagic(inp);
        if (magic == -889275714) {
            out.print("Reading .class from ");
            out.print(filename);
            out.println('.');
            new dump(inp, out);
        } else if (magic == 1347093252) {
            inp.reset();
            out.print("Reading classes from archive ");
            out.print(filename);
            out.println('.');
            ZipInputStream zin = new ZipInputStream(inp);
            while (true) {
                ZipEntry nextEntry = zin.getNextEntry();
                ZipEntry zent = nextEntry;
                if (nextEntry != null) {
                    String name = zent.getName();
                    if (zent.isDirectory()) {
                        out.print("Archive directory: ");
                        out.print(name);
                        out.println('.');
                    } else {
                        out.println();
                        if (readMagic(zin) == -889275714) {
                            out.print("Reading class member: ");
                            out.print(name);
                            out.println('.');
                            new dump(zin, out);
                        } else {
                            out.print("Skipping non-class member: ");
                            out.print(name);
                            out.println('.');
                        }
                    }
                } else {
                    System.exit(-1);
                    return;
                }
            }
        } else {
            System.err.println("File " + filename + " is not a valid .class file");
            System.exit(-1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d4, code lost:
        r10 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d5, code lost:
        if (r5 != false) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r0 = r9.getFile();
        r5 = r0.lastIndexOf(33);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00df, code lost:
        if (r5 > 0) goto L_0x00e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00e5, code lost:
        r5 = r0.substring(0, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e7, code lost:
        r5 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        new java.net.URL(r5).openConnection().getInputStream();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x010b, code lost:
        throw r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x010c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        java.lang.System.err.print("Error opening zip archive ");
        java.lang.System.err.print(r8);
        java.lang.System.err.println(" not found.");
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0125, code lost:
        if (r0.getCause() != null) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0127, code lost:
        r0.getCause().printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x012e, code lost:
        java.lang.System.exit(-1);
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0135, code lost:
        java.lang.System.err.print("File for URL ");
        java.lang.System.err.print(r8);
        java.lang.System.err.println(" not found.");
        java.lang.System.exit(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x014a, code lost:
        r0 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010c A[ExcHandler: ZipException (r0v31 'e' java.util.zip.ZipException A[CUSTOM_DECLARE]), Splitter:B:30:0x00c3] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0134 A[Catch:{ IOException -> 0x0154 }, ExcHandler: FileNotFoundException (e java.io.FileNotFoundException), Splitter:B:30:0x00c3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void main(java.lang.String[] r17) {
        /*
            r1 = r17
            java.lang.String r2 = "jar:"
            int r3 = r1.length
            gnu.bytecode.ClassTypeWriter r4 = new gnu.bytecode.ClassTypeWriter
            java.io.PrintStream r0 = java.lang.System.out
            r5 = 0
            r6 = 0
            r4.<init>((gnu.bytecode.ClassType) r5, (java.io.OutputStream) r0, (int) r6)
            if (r3 != 0) goto L_0x0015
            java.io.PrintStream r0 = java.lang.System.err
            usage(r0)
        L_0x0015:
            r7 = 0
        L_0x0016:
            if (r7 >= r3) goto L_0x01e7
            r8 = r1[r7]
            java.lang.String r0 = "-verbose"
            boolean r0 = r8.equals(r0)
            if (r0 != 0) goto L_0x01dc
            java.lang.String r0 = "--verbose"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x002c
            goto L_0x01dc
        L_0x002c:
            boolean r0 = uriSchemeSpecified(r8)
            java.lang.String r9 = ".class"
            r10 = 46
            java.lang.String r11 = " not found."
            r12 = 47
            if (r0 == 0) goto L_0x014e
            boolean r0 = r8.startsWith(r2)     // Catch:{ IOException -> 0x0154 }
            r14 = 33
            if (r0 == 0) goto L_0x00c2
            r15 = 4
            java.lang.String r15 = r8.substring(r15)     // Catch:{ IOException -> 0x0154 }
            boolean r16 = uriSchemeSpecified(r15)     // Catch:{ IOException -> 0x0154 }
            if (r16 != 0) goto L_0x0081
            int r5 = r15.indexOf(r14)     // Catch:{ IOException -> 0x0154 }
            if (r5 < 0) goto L_0x0081
            java.lang.String r8 = r15.substring(r6, r5)     // Catch:{ IOException -> 0x0154 }
            java.io.File r13 = new java.io.File     // Catch:{ IOException -> 0x0154 }
            r13.<init>(r8)     // Catch:{ IOException -> 0x0154 }
            java.net.URI r8 = r13.toURI()     // Catch:{ IOException -> 0x0154 }
            java.net.URL r8 = r8.toURL()     // Catch:{ IOException -> 0x0154 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0154 }
            r13.<init>()     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r13 = r13.append(r2)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r8 = r13.append(r8)     // Catch:{ IOException -> 0x0154 }
            java.lang.String r5 = r15.substring(r5)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r5 = r8.append(r5)     // Catch:{ IOException -> 0x0154 }
            java.lang.String r8 = r5.toString()     // Catch:{ IOException -> 0x0154 }
        L_0x0081:
            java.lang.String r5 = "!/"
            int r5 = r15.indexOf(r5)     // Catch:{ IOException -> 0x0154 }
            if (r5 >= 0) goto L_0x00c0
            int r5 = r8.lastIndexOf(r14)     // Catch:{ IOException -> 0x0154 }
            if (r5 > 0) goto L_0x0091
            r5 = 0
            goto L_0x00c3
        L_0x0091:
            int r13 = r8.indexOf(r12, r5)     // Catch:{ IOException -> 0x0154 }
            if (r13 >= 0) goto L_0x00c0
            int r5 = r5 + 1
            java.lang.String r13 = r8.substring(r5)     // Catch:{ IOException -> 0x0154 }
            java.lang.String r10 = r13.replace(r10, r12)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0154 }
            r13.<init>()     // Catch:{ IOException -> 0x0154 }
            java.lang.String r5 = r8.substring(r6, r5)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r5 = r13.append(r5)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r5 = r5.append(r12)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ IOException -> 0x0154 }
            java.lang.String r8 = r5.toString()     // Catch:{ IOException -> 0x0154 }
            r5 = r0
            goto L_0x00c3
        L_0x00c0:
            r5 = r0
            goto L_0x00c3
        L_0x00c2:
            r5 = r0
        L_0x00c3:
            java.net.URL r9 = new java.net.URL     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            r9.<init>(r8)     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            java.net.URLConnection r0 = r9.openConnection()     // Catch:{ ZipException -> 0x00d3, FileNotFoundException -> 0x0134 }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ ZipException -> 0x00d3, FileNotFoundException -> 0x0134 }
            goto L_0x014c
        L_0x00d3:
            r0 = move-exception
            r10 = r0
            if (r5 == 0) goto L_0x010b
            java.lang.String r0 = r9.getFile()     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            int r5 = r0.lastIndexOf(r14)     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            if (r5 <= 0) goto L_0x00e7
            java.lang.String r0 = r0.substring(r6, r5)     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            r5 = r0
            goto L_0x00e8
        L_0x00e7:
            r5 = r0
        L_0x00e8:
            java.net.URL r0 = new java.net.URL     // Catch:{ FileNotFoundException -> 0x00f5, ZipException -> 0x010c }
            r0.<init>(r5)     // Catch:{ FileNotFoundException -> 0x00f5, ZipException -> 0x010c }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ FileNotFoundException -> 0x00f5, ZipException -> 0x010c }
            r0.getInputStream()     // Catch:{ FileNotFoundException -> 0x00f5, ZipException -> 0x010c }
            goto L_0x010b
        L_0x00f5:
            r0 = move-exception
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            java.lang.String r9 = "Jar File for URL "
            r0.print(r9)     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            r0.print(r5)     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            r0.println(r11)     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
            r5 = -1
            java.lang.System.exit(r5)     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
        L_0x010b:
            throw r10     // Catch:{ FileNotFoundException -> 0x0134, ZipException -> 0x010c }
        L_0x010c:
            r0 = move-exception
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            java.lang.String r9 = "Error opening zip archive "
            r5.print(r9)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r5.print(r8)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r5.println(r11)     // Catch:{ IOException -> 0x0154 }
            r0.printStackTrace()     // Catch:{ IOException -> 0x0154 }
            java.lang.Throwable r5 = r0.getCause()     // Catch:{ IOException -> 0x0154 }
            if (r5 == 0) goto L_0x012e
            java.lang.Throwable r0 = r0.getCause()     // Catch:{ IOException -> 0x0154 }
            r0.printStackTrace()     // Catch:{ IOException -> 0x0154 }
        L_0x012e:
            r5 = -1
            java.lang.System.exit(r5)     // Catch:{ IOException -> 0x0154 }
            r0 = 0
            goto L_0x014c
        L_0x0134:
            r0 = move-exception
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            java.lang.String r5 = "File for URL "
            r0.print(r5)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r0.print(r8)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r0.println(r11)     // Catch:{ IOException -> 0x0154 }
            r5 = -1
            java.lang.System.exit(r5)     // Catch:{ IOException -> 0x0154 }
            r0 = 0
        L_0x014c:
            goto L_0x01c4
        L_0x014e:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0156 }
            r0.<init>(r8)     // Catch:{ FileNotFoundException -> 0x0156 }
            goto L_0x01c4
        L_0x0154:
            r0 = move-exception
            goto L_0x01c8
        L_0x0156:
            r0 = move-exception
            java.lang.Class r0 = gnu.bytecode.ObjectType.getContextClass(r8)     // Catch:{ NoClassDefFoundError -> 0x0179, all -> 0x0160 }
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ NoClassDefFoundError -> 0x0179, all -> 0x0160 }
            goto L_0x017e
        L_0x0160:
            r0 = move-exception
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            java.lang.String r5 = "File "
            r0.print(r5)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r0.print(r8)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r0.println(r11)     // Catch:{ IOException -> 0x0154 }
            r5 = -1
            java.lang.System.exit(r5)     // Catch:{ IOException -> 0x0154 }
            r0 = 0
            goto L_0x017f
        L_0x0179:
            r0 = move-exception
            java.lang.ClassLoader r0 = gnu.bytecode.ObjectType.getContextClassLoader()     // Catch:{ IOException -> 0x0154 }
        L_0x017e:
        L_0x017f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0154 }
            r5.<init>()     // Catch:{ IOException -> 0x0154 }
            java.lang.String r10 = r8.replace(r10, r12)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch:{ IOException -> 0x0154 }
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ IOException -> 0x0154 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0154 }
            java.net.URL r0 = r0.getResource(r5)     // Catch:{ all -> 0x01a6 }
            java.net.URLConnection r5 = r0.openConnection()     // Catch:{ all -> 0x01a6 }
            java.io.InputStream r5 = r5.getInputStream()     // Catch:{ all -> 0x01a6 }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x01a6 }
            r0 = r5
            goto L_0x01c4
        L_0x01a6:
            r0 = move-exception
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            java.lang.String r9 = "Can't find .class file for class "
            r5.print(r9)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r5.print(r8)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            java.lang.String r9 = " - "
            r5.print(r9)     // Catch:{ IOException -> 0x0154 }
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ IOException -> 0x0154 }
            r5.println(r0)     // Catch:{ IOException -> 0x0154 }
            r5 = -1
            java.lang.System.exit(r5)     // Catch:{ IOException -> 0x0154 }
            r0 = 0
        L_0x01c4:
            process(r0, r8, r4)     // Catch:{ IOException -> 0x0154 }
            goto L_0x01e2
        L_0x01c8:
            r0.printStackTrace()
            java.io.PrintStream r5 = java.lang.System.err
            java.lang.String r8 = "caught "
            r5.println(r8)
            java.io.PrintStream r5 = java.lang.System.err
            r5.print(r0)
            r5 = -1
            java.lang.System.exit(r5)
            goto L_0x01e2
        L_0x01dc:
            r0 = 15
            r4.setFlags(r0)
        L_0x01e2:
            int r7 = r7 + 1
            r5 = 0
            goto L_0x0016
        L_0x01e7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.dump.main(java.lang.String[]):void");
    }

    static int uriSchemeLength(String uri) {
        int len = uri.length();
        int i = 0;
        while (i < len) {
            char ch = uri.charAt(i);
            if (ch == ':') {
                return i;
            }
            if (i != 0) {
                if (!(Character.isLetterOrDigit(ch) || ch == '+' || ch == '-' || ch == '.')) {
                }
                i++;
            } else if (Character.isLetter(ch)) {
                i++;
            }
            return -1;
        }
        return -1;
    }

    static boolean uriSchemeSpecified(String name) {
        int ulen = uriSchemeLength(name);
        if (ulen == 1 && File.separatorChar == '\\') {
            char drive = name.charAt(0);
            if (drive >= 'a' && drive <= 'z') {
                return false;
            }
            if (drive < 'A' || drive > 'Z') {
                return true;
            }
            return false;
        } else if (ulen > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void usage(PrintStream err) {
        err.println("Prints and dis-assembles the contents of JVM .class files.");
        err.println("Usage: [--verbose] class-or-jar ...");
        err.println("where a class-or-jar can be one of:");
        err.println("- a fully-qualified class name; or");
        err.println("- the name of a .class file, or a URL reference to one; or");
        err.println("- the name of a .jar or .zip archive file, or a URL reference to one.");
        err.println("If a .jar/.zip archive is named, all its.class file members are printed.");
        err.println();
        err.println("You can name a single .class member of an archive with a jar: URL,");
        err.println("which looks like: jar:jar-spec!/p1/p2/cl.class");
        err.println("The jar-spec can be a URL or the name of the .jar file.");
        err.println("You can also use the shorthand syntax: jar:jar-spec!p1.p2.cl");
        System.exit(-1);
    }
}
