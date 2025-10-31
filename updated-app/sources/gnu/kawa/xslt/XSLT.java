package gnu.kawa.xslt;

import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.Focus;
import gnu.kawa.xml.KDocument;
import gnu.lists.TreeList;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xquery.lang.XQParser;
import gnu.xquery.lang.XQResolveNames;
import gnu.xquery.lang.XQuery;
import java.io.IOException;

public class XSLT extends XQuery {
    public static XSLT instance;
    public static Symbol nullMode = Symbol.make((Object) null, "");

    public String getName() {
        return "XSLT";
    }

    public XSLT() {
        instance = this;
        ModuleBody.setMainPrintValues(true);
    }

    public static XSLT getXsltInstance() {
        if (instance == null) {
            new XSLT();
        }
        return instance;
    }

    public Lexer getLexer(InPort inp, SourceMessages messages) {
        return new XslTranslator(inp, messages, this);
    }

    public boolean parse(Compilation comp, int options) throws IOException, SyntaxException {
        Compilation.defaultCallConvention = 2;
        ((XslTranslator) comp.lexer).parse(comp);
        comp.setState(4);
        XQParser xqparser = new XQParser((InPort) null, comp.getMessages(), this);
        XQResolveNames resolver = new XQResolveNames(comp);
        resolver.functionNamespacePath = xqparser.functionNamespacePath;
        resolver.parser = xqparser;
        resolver.resolveModule(comp.mainLambda);
        return true;
    }

    public static void registerEnvironment() {
        Language.setDefaults(new XSLT());
    }

    public static void defineCallTemplate(Symbol name, double priority, Procedure template) {
    }

    public static void defineApplyTemplate(String pattern, double priority, Symbol mode, Procedure template) {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable.getTemplateTable(mode).enter(pattern, priority, template);
    }

    public static void defineTemplate(Symbol name, String pattern, double priority, Symbol mode, Procedure template) {
        if (name != null) {
            defineCallTemplate(name, priority, template);
        }
        if (pattern != null) {
            defineApplyTemplate(pattern, priority, mode, template);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void process(gnu.lists.TreeList r6, gnu.kawa.xml.Focus r7, gnu.mapping.CallContext r8) throws java.lang.Throwable {
        /*
            gnu.lists.Consumer r0 = r8.consumer
        L_0x0002:
            int r1 = r7.ipos
            int r2 = r6.getNextKind(r1)
            switch(r2) {
                case 29: goto L_0x0080;
                case 30: goto L_0x000b;
                case 31: goto L_0x000b;
                case 32: goto L_0x000b;
                case 33: goto L_0x0044;
                case 34: goto L_0x003f;
                case 35: goto L_0x0016;
                case 36: goto L_0x000c;
                case 37: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            return
        L_0x000c:
            int r1 = r1 >>> 1
            int r1 = r6.nextDataIndex(r1)
            int r1 = r1 << 1
            goto L_0x0095
        L_0x0016:
            r7.getNextTypeObject()
            java.lang.String r2 = r7.getNextTypeName()
            gnu.kawa.xslt.TemplateTable r3 = gnu.kawa.xslt.TemplateTable.nullModeTable
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "@"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
            gnu.mapping.Procedure r2 = r3.find(r2)
            if (r2 == 0) goto L_0x0080
            r2.check0(r8)
            r8.runUntilDone()
            goto L_0x0095
        L_0x003f:
            int r1 = r6.firstChildPos(r1)
            goto L_0x0095
        L_0x0044:
            java.lang.Object r2 = r7.getNextTypeObject()
            java.lang.String r3 = r7.getNextTypeName()
            gnu.kawa.xslt.TemplateTable r4 = gnu.kawa.xslt.TemplateTable.nullModeTable
            gnu.mapping.Procedure r3 = r4.find(r3)
            if (r3 == 0) goto L_0x005b
            r3.check0(r8)
            r8.runUntilDone()
            goto L_0x0074
        L_0x005b:
            r0.startElement(r2)
            int r2 = r6.firstAttributePos(r1)
            if (r2 != 0) goto L_0x0068
            int r2 = r6.firstChildPos(r1)
        L_0x0068:
            r7.push(r6, r2)
            process(r6, r7, r8)
            r7.pop()
            r0.endElement()
        L_0x0074:
            int r1 = r1 >>> 1
            int r1 = r6.nextDataIndex(r1)
            int r1 = r1 << 1
            r7.gotoNext()
            goto L_0x0095
        L_0x0080:
            int r1 = r1 >>> 1
            r2 = 2147483647(0x7fffffff, float:NaN)
            int r2 = r6.nextNodeIndex(r1, r2)
            if (r1 != r2) goto L_0x008f
            int r2 = r6.nextDataIndex(r1)
        L_0x008f:
            r6.consumeIRange(r1, r2, r0)
            int r1 = r2 << 1
        L_0x0095:
            r7.ipos = r1
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.xslt.XSLT.process(gnu.lists.TreeList, gnu.kawa.xml.Focus, gnu.mapping.CallContext):void");
    }

    public static void runStylesheet() throws Throwable {
        CallContext ctx = CallContext.getInstance();
        ApplicationMainSupport.processSetProperties();
        String[] args = ApplicationMainSupport.commandLineArgArray;
        for (String arg : args) {
            KDocument doc = Document.parse(arg);
            Focus pos = Focus.getCurrent();
            pos.push(doc.sequence, doc.ipos);
            process((TreeList) doc.sequence, pos, ctx);
        }
    }

    public static void applyTemplates(String select, Symbol mode) throws Throwable {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable templateTable = TemplateTable.getTemplateTable(mode);
        CallContext ctx = CallContext.getInstance();
        Focus pos = Focus.getCurrent();
        TreeList doc = (TreeList) pos.sequence;
        pos.push(doc, doc.firstChildPos(pos.ipos));
        process(doc, pos, ctx);
        pos.pop();
    }
}
