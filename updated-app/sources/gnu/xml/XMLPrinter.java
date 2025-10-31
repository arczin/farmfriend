package gnu.xml;

import gnu.expr.Keyword;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumable;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.math.DFloNum;
import gnu.math.RealNum;
import gnu.text.Char;
import gnu.text.Path;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

public class XMLPrinter extends OutPort implements PositionConsumer, XConsumer {
    private static final int COMMENT = -5;
    private static final int ELEMENT_END = -4;
    private static final int ELEMENT_START = -3;
    static final String HtmlEmptyTags = "/area/base/basefont/br/col/frame/hr/img/input/isindex/link/meta/para/";
    private static final int KEYWORD = -6;
    private static final int PROC_INST = -7;
    private static final int WORD = -2;
    public static final ThreadLocation doctypePublic = new ThreadLocation("doctype-public");
    public static final ThreadLocation doctypeSystem = new ThreadLocation("doctype-system");
    public static final ThreadLocation indentLoc = new ThreadLocation("xml-indent");
    boolean canonicalize = true;
    public boolean canonicalizeCDATA;
    Object[] elementNameStack = new Object[20];
    int elementNesting;
    public boolean escapeNonAscii = true;
    public boolean escapeText = true;
    boolean inAttribute = false;
    int inComment;
    boolean inDocument;
    boolean inStartTag = false;
    public boolean indentAttributes;
    boolean isHtml = false;
    boolean isHtmlOrXhtml = false;
    NamespaceBinding namespaceBindings = NamespaceBinding.predefinedXML;
    NamespaceBinding[] namespaceSaveStack = new NamespaceBinding[20];
    boolean needXMLdecl = false;
    int prev = 32;
    public int printIndent = -1;
    boolean printXMLdecl = false;
    char savedHighSurrogate;
    public boolean strict;
    Object style;
    boolean undeclareNamespaces = false;
    public int useEmptyElementTag = 2;

    public void setPrintXMLdecl(boolean value) {
        this.printXMLdecl = value;
    }

    public XMLPrinter(OutPort out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public XMLPrinter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public XMLPrinter(OutputStream out, boolean autoFlush) {
        super((Writer) new OutputStreamWriter(out), true, autoFlush);
    }

    public XMLPrinter(Writer out) {
        super(out);
    }

    public XMLPrinter(OutputStream out) {
        super((Writer) new OutputStreamWriter(out), false, false);
    }

    public XMLPrinter(OutputStream out, Path path) {
        super(new OutputStreamWriter(out), true, false, path);
    }

    public static XMLPrinter make(OutPort out, Object style2) {
        XMLPrinter xout = new XMLPrinter(out, true);
        xout.setStyle(style2);
        return xout;
    }

    public static String toString(Object value) {
        StringWriter stringWriter = new StringWriter();
        new XMLPrinter((Writer) stringWriter).writeObject(value);
        return stringWriter.toString();
    }

    public void setStyle(Object style2) {
        this.style = style2;
        this.useEmptyElementTag = this.canonicalize ^ true ? 1 : 0;
        if ("html".equals(style2)) {
            this.isHtml = true;
            this.isHtmlOrXhtml = true;
            this.useEmptyElementTag = 2;
            if (this.namespaceBindings == NamespaceBinding.predefinedXML) {
                this.namespaceBindings = XmlNamespace.HTML_BINDINGS;
            }
        } else if (this.namespaceBindings == XmlNamespace.HTML_BINDINGS) {
            this.namespaceBindings = NamespaceBinding.predefinedXML;
        }
        if ("xhtml".equals(style2)) {
            this.isHtmlOrXhtml = true;
            this.useEmptyElementTag = 2;
        }
        if ("plain".equals(style2)) {
            this.escapeText = false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean mustHexEscape(int v) {
        return (v >= 127 && (v <= 159 || this.escapeNonAscii)) || v == 8232 || (v < 32 && (this.inAttribute || !(v == 9 || v == 10)));
    }

    public void write(int v) {
        closeTag();
        if (this.printIndent >= 0 && (v == 13 || v == 10)) {
            if (!(v == 10 && this.prev == 13)) {
                writeBreak(82);
            }
            if (this.inComment > 0) {
                this.inComment = 1;
            }
        } else if (!this.escapeText) {
            this.bout.write(v);
            this.prev = v;
        } else if (this.inComment > 0) {
            if (v != 45) {
                this.inComment = 1;
            } else if (this.inComment == 1) {
                this.inComment = 2;
            } else {
                this.bout.write(32);
            }
            super.write(v);
        } else {
            this.prev = 59;
            if (v == 60 && (!this.isHtml || !this.inAttribute)) {
                this.bout.write("&lt;");
            } else if (v == 62) {
                this.bout.write("&gt;");
            } else if (v == 38) {
                this.bout.write("&amp;");
            } else if (v == 34 && this.inAttribute) {
                this.bout.write("&quot;");
            } else if (mustHexEscape(v)) {
                int i = v;
                if (v >= 55296) {
                    if (v < 56320) {
                        this.savedHighSurrogate = (char) v;
                        return;
                    } else if (v < 57344) {
                        i = ((this.savedHighSurrogate - 55296) * 1024) + (i - 56320) + 65536;
                        this.savedHighSurrogate = 0;
                    }
                }
                this.bout.write("&#x" + Integer.toHexString(i).toUpperCase() + ";");
            } else {
                this.bout.write(v);
                this.prev = v;
            }
        }
    }

    private void startWord() {
        closeTag();
        writeWordStart();
    }

    public void writeBoolean(boolean v) {
        startWord();
        super.print(v);
        writeWordEnd();
    }

    /* access modifiers changed from: protected */
    public void startNumber() {
        startWord();
    }

    /* access modifiers changed from: protected */
    public void endNumber() {
        writeWordEnd();
    }

    public void closeTag() {
        if (this.inStartTag && !this.inAttribute) {
            if (this.printIndent >= 0 && this.indentAttributes) {
                endLogicalBlock("");
            }
            this.bout.write(62);
            this.inStartTag = false;
            this.prev = -3;
        } else if (this.needXMLdecl) {
            this.bout.write("<?xml version=\"1.0\"?>\n");
            if (this.printIndent >= 0) {
                startLogicalBlock("", "", 2);
            }
            this.needXMLdecl = false;
            this.prev = 62;
        }
    }

    /* access modifiers changed from: package-private */
    public void setIndentMode() {
        String indent = null;
        Object xmlIndent = indentLoc.get((Object) null);
        if (xmlIndent != null) {
            indent = xmlIndent.toString();
        }
        if (indent == null) {
            this.printIndent = -1;
        } else if (indent.equals("pretty")) {
            this.printIndent = 0;
        } else if (indent.equals("always") || indent.equals("yes")) {
            this.printIndent = 1;
        } else {
            this.printIndent = -1;
        }
    }

    public void startDocument() {
        if (this.printXMLdecl) {
            this.needXMLdecl = true;
        }
        setIndentMode();
        this.inDocument = true;
        if (this.printIndent >= 0 && !this.needXMLdecl) {
            startLogicalBlock("", "", 2);
        }
    }

    public void endDocument() {
        this.inDocument = false;
        if (this.printIndent >= 0) {
            endLogicalBlock("");
        }
        freshLine();
    }

    public void beginEntity(Object base) {
    }

    public void endEntity() {
    }

    /* access modifiers changed from: protected */
    public void writeQName(Object name) {
        if (name instanceof Symbol) {
            Symbol sname = (Symbol) name;
            String prefix = sname.getPrefix();
            if (prefix != null && prefix.length() > 0) {
                this.bout.write(prefix);
                this.bout.write(58);
            }
            this.bout.write(sname.getLocalPart());
            return;
        }
        this.bout.write(name == null ? "{null name}" : (String) name);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0118, code lost:
        if (r10 == false) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x011a, code lost:
        r12 = r12 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x011d, code lost:
        r12 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x011e, code lost:
        r8[r12] = r11;
        r9 = r9 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startElement(java.lang.Object r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r18.closeTag()
            int r2 = r0.elementNesting
            if (r2 != 0) goto L_0x007e
            boolean r2 = r0.inDocument
            if (r2 != 0) goto L_0x0012
            r18.setIndentMode()
        L_0x0012:
            int r2 = r0.prev
            r3 = -7
            if (r2 != r3) goto L_0x001c
            r2 = 10
            r0.write(r2)
        L_0x001c:
            gnu.mapping.ThreadLocation r2 = doctypeSystem
            r3 = 0
            java.lang.Object r2 = r2.get(r3)
            if (r2 == 0) goto L_0x007e
            java.lang.String r4 = r2.toString()
            int r5 = r4.length()
            if (r5 <= 0) goto L_0x007e
            gnu.mapping.ThreadLocation r5 = doctypePublic
            java.lang.Object r5 = r5.get(r3)
            gnu.text.PrettyWriter r6 = r0.bout
            java.lang.String r7 = "<!DOCTYPE "
            r6.write((java.lang.String) r7)
            gnu.text.PrettyWriter r6 = r0.bout
            java.lang.String r7 = r19.toString()
            r6.write((java.lang.String) r7)
            if (r5 != 0) goto L_0x0048
            goto L_0x004c
        L_0x0048:
            java.lang.String r3 = r5.toString()
        L_0x004c:
            if (r3 == 0) goto L_0x0068
            int r6 = r3.length()
            if (r6 <= 0) goto L_0x0068
            gnu.text.PrettyWriter r6 = r0.bout
            java.lang.String r7 = " PUBLIC \""
            r6.write((java.lang.String) r7)
            gnu.text.PrettyWriter r6 = r0.bout
            r6.write((java.lang.String) r3)
            gnu.text.PrettyWriter r6 = r0.bout
            java.lang.String r7 = "\" \""
            r6.write((java.lang.String) r7)
            goto L_0x006f
        L_0x0068:
            gnu.text.PrettyWriter r6 = r0.bout
            java.lang.String r7 = " SYSTEM \""
            r6.write((java.lang.String) r7)
        L_0x006f:
            gnu.text.PrettyWriter r6 = r0.bout
            r6.write((java.lang.String) r4)
            gnu.text.PrettyWriter r6 = r0.bout
            java.lang.String r7 = "\">"
            r6.write((java.lang.String) r7)
            r18.println()
        L_0x007e:
            int r2 = r0.printIndent
            r3 = 2
            java.lang.String r4 = ""
            if (r2 < 0) goto L_0x00a3
            int r2 = r0.prev
            r5 = -3
            if (r2 == r5) goto L_0x0094
            int r2 = r0.prev
            r5 = -4
            if (r2 == r5) goto L_0x0094
            int r2 = r0.prev
            r5 = -5
            if (r2 != r5) goto L_0x00a0
        L_0x0094:
            int r2 = r0.printIndent
            if (r2 <= 0) goto L_0x009b
            r2 = 82
            goto L_0x009d
        L_0x009b:
            r2 = 78
        L_0x009d:
            r0.writeBreak(r2)
        L_0x00a0:
            r0.startLogicalBlock((java.lang.String) r4, (java.lang.String) r4, (int) r3)
        L_0x00a3:
            gnu.text.PrettyWriter r2 = r0.bout
            r5 = 60
            r2.write((int) r5)
            r18.writeQName(r19)
            int r2 = r0.printIndent
            if (r2 < 0) goto L_0x00b8
            boolean r2 = r0.indentAttributes
            if (r2 == 0) goto L_0x00b8
            r0.startLogicalBlock((java.lang.String) r4, (java.lang.String) r4, (int) r3)
        L_0x00b8:
            java.lang.Object[] r2 = r0.elementNameStack
            int r4 = r0.elementNesting
            r2[r4] = r1
            r2 = 0
            gnu.xml.NamespaceBinding[] r4 = r0.namespaceSaveStack
            int r5 = r0.elementNesting
            int r6 = r5 + 1
            r0.elementNesting = r6
            gnu.xml.NamespaceBinding r6 = r0.namespaceBindings
            r4[r5] = r6
            boolean r4 = r1 instanceof gnu.xml.XName
            r5 = 1
            if (r4 == 0) goto L_0x01b1
            r4 = r1
            gnu.xml.XName r4 = (gnu.xml.XName) r4
            gnu.xml.NamespaceBinding r2 = r4.namespaceNodes
            gnu.xml.NamespaceBinding r4 = r0.namespaceBindings
            gnu.xml.NamespaceBinding r4 = gnu.xml.NamespaceBinding.commonAncestor(r2, r4)
            if (r2 != 0) goto L_0x00df
            r7 = 0
            goto L_0x00e3
        L_0x00df:
            int r7 = r2.count(r4)
        L_0x00e3:
            gnu.xml.NamespaceBinding[] r8 = new gnu.xml.NamespaceBinding[r7]
            r9 = 0
            boolean r10 = r0.canonicalize
            r11 = r2
        L_0x00e9:
            if (r11 == r4) goto L_0x0126
            r12 = r9
            r13 = 0
            java.lang.String r14 = r11.getUri()
            java.lang.String r15 = r11.getPrefix()
        L_0x00f5:
            int r12 = r12 + -1
            if (r12 < 0) goto L_0x0118
            r16 = r8[r12]
            java.lang.String r3 = r16.getPrefix()
            if (r15 != r3) goto L_0x0102
            goto L_0x0122
        L_0x0102:
            if (r10 != 0) goto L_0x0105
            goto L_0x0116
        L_0x0105:
            if (r15 != 0) goto L_0x0108
            goto L_0x0118
        L_0x0108:
            if (r3 == 0) goto L_0x0111
            int r17 = r15.compareTo(r3)
            if (r17 > 0) goto L_0x0111
            goto L_0x0118
        L_0x0111:
            int r17 = r12 + 1
            r8[r17] = r16
        L_0x0116:
            r3 = 2
            goto L_0x00f5
        L_0x0118:
            if (r10 == 0) goto L_0x011d
            int r12 = r12 + 1
            goto L_0x011e
        L_0x011d:
            r12 = r9
        L_0x011e:
            r8[r12] = r11
            int r9 = r9 + 1
        L_0x0122:
            gnu.xml.NamespaceBinding r11 = r11.next
            r3 = 2
            goto L_0x00e9
        L_0x0126:
            r3 = r9
            r7 = r3
        L_0x0128:
            int r7 = r7 + -1
            java.lang.String r9 = "xmlns"
            java.lang.String r11 = "xmlns:"
            r12 = 32
            if (r7 < 0) goto L_0x017a
            r13 = r8[r7]
            java.lang.String r14 = r13.prefix
            java.lang.String r15 = r13.uri
            gnu.xml.NamespaceBinding r6 = r0.namespaceBindings
            java.lang.String r6 = r6.resolve(r14)
            if (r15 != r6) goto L_0x0141
            goto L_0x0128
        L_0x0141:
            if (r15 != 0) goto L_0x014a
            if (r14 == 0) goto L_0x014a
            boolean r6 = r0.undeclareNamespaces
            if (r6 != 0) goto L_0x014a
            goto L_0x0128
        L_0x014a:
            gnu.text.PrettyWriter r6 = r0.bout
            r6.write((int) r12)
            if (r14 != 0) goto L_0x0157
            gnu.text.PrettyWriter r6 = r0.bout
            r6.write((java.lang.String) r9)
            goto L_0x0161
        L_0x0157:
            gnu.text.PrettyWriter r6 = r0.bout
            r6.write((java.lang.String) r11)
            gnu.text.PrettyWriter r6 = r0.bout
            r6.write((java.lang.String) r14)
        L_0x0161:
            gnu.text.PrettyWriter r6 = r0.bout
            java.lang.String r9 = "=\""
            r6.write((java.lang.String) r9)
            r0.inAttribute = r5
            if (r15 == 0) goto L_0x016f
            r0.write((java.lang.String) r15)
        L_0x016f:
            r6 = 0
            r0.inAttribute = r6
            gnu.text.PrettyWriter r6 = r0.bout
            r9 = 34
            r6.write((int) r9)
            goto L_0x0128
        L_0x017a:
            boolean r6 = r0.undeclareNamespaces
            if (r6 == 0) goto L_0x01af
            gnu.xml.NamespaceBinding r6 = r0.namespaceBindings
        L_0x0180:
            if (r6 == r4) goto L_0x01af
            java.lang.String r13 = r6.prefix
            java.lang.String r14 = r6.uri
            if (r14 == 0) goto L_0x01ac
            java.lang.String r14 = r2.resolve(r13)
            if (r14 != 0) goto L_0x01ac
            gnu.text.PrettyWriter r14 = r0.bout
            r14.write((int) r12)
            if (r13 != 0) goto L_0x019b
            gnu.text.PrettyWriter r14 = r0.bout
            r14.write((java.lang.String) r9)
            goto L_0x01a5
        L_0x019b:
            gnu.text.PrettyWriter r14 = r0.bout
            r14.write((java.lang.String) r11)
            gnu.text.PrettyWriter r14 = r0.bout
            r14.write((java.lang.String) r13)
        L_0x01a5:
            gnu.text.PrettyWriter r14 = r0.bout
            java.lang.String r15 = "=\"\""
            r14.write((java.lang.String) r15)
        L_0x01ac:
            gnu.xml.NamespaceBinding r6 = r6.next
            goto L_0x0180
        L_0x01af:
            r0.namespaceBindings = r2
        L_0x01b1:
            int r3 = r0.elementNesting
            gnu.xml.NamespaceBinding[] r4 = r0.namespaceSaveStack
            int r4 = r4.length
            if (r3 < r4) goto L_0x01d8
            int r3 = r0.elementNesting
            r4 = 2
            int r3 = r3 * 2
            gnu.xml.NamespaceBinding[] r3 = new gnu.xml.NamespaceBinding[r3]
            gnu.xml.NamespaceBinding[] r6 = r0.namespaceSaveStack
            int r7 = r0.elementNesting
            r8 = 0
            java.lang.System.arraycopy(r6, r8, r3, r8, r7)
            r0.namespaceSaveStack = r3
            int r6 = r0.elementNesting
            int r6 = r6 * 2
            java.lang.Object[] r4 = new java.lang.Object[r6]
            java.lang.Object[] r6 = r0.elementNameStack
            int r7 = r0.elementNesting
            java.lang.System.arraycopy(r6, r8, r4, r8, r7)
            r0.elementNameStack = r4
        L_0x01d8:
            r0.inStartTag = r5
            java.lang.String r3 = r18.getHtmlTag(r19)
            java.lang.String r4 = "script"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L_0x01ee
            java.lang.String r4 = "style"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x01f1
        L_0x01ee:
            r4 = 0
            r0.escapeText = r4
        L_0x01f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLPrinter.startElement(java.lang.Object):void");
    }

    public static boolean isHtmlEmptyElementTag(String name) {
        int index = HtmlEmptyTags.indexOf(name);
        return index > 0 && HtmlEmptyTags.charAt(index + -1) == '/' && HtmlEmptyTags.charAt(name.length() + index) == '/';
    }

    /* access modifiers changed from: protected */
    public String getHtmlTag(Object type) {
        if (type instanceof Symbol) {
            Symbol sym = (Symbol) type;
            String uri = sym.getNamespaceURI();
            if (uri == "http://www.w3.org/1999/xhtml" || (this.isHtmlOrXhtml && uri == "")) {
                return sym.getLocalPart();
            }
            return null;
        } else if (this.isHtmlOrXhtml) {
            return type.toString();
        } else {
            return null;
        }
    }

    public void endElement() {
        if (this.useEmptyElementTag == 0) {
            closeTag();
        }
        Object type = this.elementNameStack[this.elementNesting - 1];
        String typeName = getHtmlTag(type);
        String str = ">";
        if (this.inStartTag) {
            if (this.printIndent >= 0 && this.indentAttributes) {
                endLogicalBlock("");
            }
            String end = null;
            boolean isEmpty = typeName != null && isHtmlEmptyElementTag(typeName);
            if ((this.useEmptyElementTag == 0 || (typeName != null && !isEmpty)) && (type instanceof Symbol)) {
                Symbol sym = (Symbol) type;
                String prefix = sym.getPrefix();
                String uri = sym.getNamespaceURI();
                String local = sym.getLocalName();
                if (prefix != "") {
                    end = "></" + prefix + ":" + local + str;
                } else if (uri == "" || uri == null) {
                    end = "></" + local + str;
                }
            }
            if (end == null) {
                if (!isEmpty || !this.isHtml) {
                    str = this.useEmptyElementTag == 2 ? " />" : "/>";
                }
                end = str;
            }
            this.bout.write(end);
            this.inStartTag = false;
        } else {
            if (this.printIndent >= 0) {
                setIndentation(0, false);
                if (this.prev == -4) {
                    writeBreak(this.printIndent > 0 ? 82 : 78);
                }
            }
            this.bout.write("</");
            writeQName(type);
            this.bout.write(str);
        }
        if (this.printIndent >= 0) {
            endLogicalBlock("");
        }
        this.prev = -4;
        if (typeName != null && !this.escapeText && ("script".equals(typeName) || "style".equals(typeName))) {
            this.escapeText = true;
        }
        NamespaceBinding[] namespaceBindingArr = this.namespaceSaveStack;
        int i = this.elementNesting - 1;
        this.elementNesting = i;
        this.namespaceBindings = namespaceBindingArr[i];
        this.namespaceSaveStack[this.elementNesting] = null;
        this.elementNameStack[this.elementNesting] = null;
    }

    public void startAttribute(Object attrType) {
        if (!this.inStartTag && this.strict) {
            error("attribute not in element", "SENR0001");
        }
        if (this.inAttribute) {
            this.bout.write(34);
        }
        this.inAttribute = true;
        this.bout.write(32);
        if (this.printIndent >= 0) {
            writeBreakFill();
        }
        this.bout.write(attrType.toString());
        this.bout.write("=\"");
        this.prev = 32;
    }

    public void endAttribute() {
        if (this.inAttribute) {
            if (this.prev != -6) {
                this.bout.write(34);
                this.inAttribute = false;
            }
            this.prev = 32;
        }
    }

    public void writeDouble(double d) {
        startWord();
        this.bout.write(formatDouble(d));
    }

    public void writeFloat(float f) {
        startWord();
        this.bout.write(formatFloat(f));
    }

    public static String formatDouble(double d) {
        if (Double.isNaN(d)) {
            return "NaN";
        }
        boolean neg = d < 0.0d;
        if (Double.isInfinite(d)) {
            return neg ? "-INF" : "INF";
        }
        double dabs = neg ? -d : d;
        String dstr = Double.toString(d);
        if ((dabs >= 1000000.0d || dabs < 1.0E-6d) && dabs != 0.0d) {
            return RealNum.toStringScientific(dstr);
        }
        return formatDecimal(RealNum.toStringDecimal(dstr));
    }

    public static String formatFloat(float f) {
        if (Float.isNaN(f)) {
            return "NaN";
        }
        boolean neg = f < 0.0f;
        if (Float.isInfinite(f)) {
            return neg ? "-INF" : "INF";
        }
        float fabs = neg ? -f : f;
        String fstr = Float.toString(f);
        if ((fabs >= 1000000.0f || ((double) fabs) < 1.0E-6d) && ((double) fabs) != 0.0d) {
            return RealNum.toStringScientific(fstr);
        }
        return formatDecimal(RealNum.toStringDecimal(fstr));
    }

    public static String formatDecimal(BigDecimal dec) {
        return formatDecimal(dec.toPlainString());
    }

    static String formatDecimal(String str) {
        char ch;
        if (str.indexOf(46) < 0) {
            return str;
        }
        int len = str.length();
        int pos = len;
        do {
            pos--;
            ch = str.charAt(pos);
        } while (ch == '0');
        if (ch != '.') {
            pos++;
        }
        return pos == len ? str : str.substring(0, pos);
    }

    public void print(Object v) {
        if (v instanceof BigDecimal) {
            v = formatDecimal((BigDecimal) v);
        } else if ((v instanceof Double) || (v instanceof DFloNum)) {
            v = formatDouble(((Number) v).doubleValue());
        } else if (v instanceof Float) {
            v = formatFloat(((Float) v).floatValue());
        }
        write(v == null ? "(null)" : v.toString());
    }

    public void writeObject(Object v) {
        if (v instanceof SeqPosition) {
            this.bout.clearWordEnd();
            SeqPosition pos = (SeqPosition) v;
            pos.sequence.consumeNext(pos.ipos, this);
            if (pos.sequence instanceof NodeTree) {
                this.prev = 45;
            }
        } else if ((v instanceof Consumable) && !(v instanceof UnescapedData)) {
            ((Consumable) v).consume(this);
        } else if (v instanceof Keyword) {
            startAttribute(((Keyword) v).getName());
            this.prev = -6;
        } else {
            closeTag();
            if (v instanceof UnescapedData) {
                this.bout.clearWordEnd();
                this.bout.write(((UnescapedData) v).getData());
                this.prev = 45;
            } else if (v instanceof Char) {
                Char.print(((Char) v).intValue(), this);
            } else {
                startWord();
                this.prev = 32;
                print(v);
                writeWordEnd();
                this.prev = -2;
            }
        }
    }

    public boolean ignoring() {
        return false;
    }

    public void write(String str, int start, int length) {
        if (length > 0) {
            closeTag();
            int limit = start + length;
            int count = 0;
            while (start < limit) {
                int start2 = start + 1;
                int start3 = str.charAt(start);
                if (mustHexEscape(start3) || (this.inComment <= 0 ? start3 == 60 || start3 == 62 || start3 == 38 || (this.inAttribute && (start3 == 34 || start3 < 32)) : start3 == 45 || this.inComment == 2)) {
                    if (count > 0) {
                        this.bout.write(str, (start2 - 1) - count, count);
                    }
                    write(start3);
                    count = 0;
                } else {
                    count++;
                }
                start = start2;
            }
            if (count > 0) {
                this.bout.write(str, limit - count, count);
            }
        }
        this.prev = 45;
    }

    public void write(char[] buf, int off, int len) {
        if (len > 0) {
            closeTag();
            int limit = off + len;
            int count = 0;
            while (off < limit) {
                int off2 = off + 1;
                char off3 = buf[off];
                if (mustHexEscape(off3) || (this.inComment <= 0 ? off3 == '<' || off3 == '>' || off3 == '&' || (this.inAttribute && (off3 == '\"' || off3 < ' ')) : off3 == '-' || this.inComment == 2)) {
                    if (count > 0) {
                        this.bout.write(buf, (off2 - 1) - count, count);
                    }
                    write(off3);
                    count = 0;
                } else {
                    count++;
                }
                off = off2;
            }
            if (count > 0) {
                this.bout.write(buf, limit - count, count);
            }
        }
        this.prev = 45;
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        seq.consumeNext(ipos, this);
    }

    public void writeBaseUri(Object uri) {
    }

    public void beginComment() {
        closeTag();
        if (this.printIndent >= 0 && (this.prev == -3 || this.prev == -4 || this.prev == -5)) {
            writeBreak(this.printIndent > 0 ? 82 : 78);
        }
        this.bout.write("<!--");
        this.inComment = 1;
    }

    public void endComment() {
        this.bout.write("-->");
        this.prev = -5;
        this.inComment = 0;
    }

    public void writeComment(String chars) {
        beginComment();
        write(chars);
        endComment();
    }

    public void writeComment(char[] chars, int offset, int length) {
        beginComment();
        write(chars, offset, length);
        endComment();
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        if (this.canonicalizeCDATA) {
            write(chars, offset, length);
            return;
        }
        closeTag();
        this.bout.write("<![CDATA[");
        int limit = offset + length;
        int i = offset;
        while (i < limit - 2) {
            if (chars[i] == ']' && chars[i + 1] == ']' && chars[i + 2] == '>') {
                if (i > offset) {
                    this.bout.write(chars, offset, i - offset);
                }
                print("]]]><![CDATA[]>");
                offset = i + 3;
                length = limit - offset;
                i += 2;
            }
            i++;
        }
        this.bout.write(chars, offset, length);
        this.bout.write("]]>");
        this.prev = 62;
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        if ("xml".equals(target)) {
            this.needXMLdecl = false;
        }
        closeTag();
        this.bout.write("<?");
        print(target);
        print(' ');
        this.bout.write(content, offset, length);
        this.bout.write("?>");
        this.prev = -7;
    }

    public void consume(SeqPosition position) {
        position.sequence.consumeNext(position.ipos, this);
    }

    public void error(String msg, String code) {
        throw new RuntimeException("serialization error: " + msg + " [" + code + ']');
    }
}
