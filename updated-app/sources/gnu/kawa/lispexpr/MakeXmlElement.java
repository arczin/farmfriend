package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.MakeText;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.xml.NamespaceBinding;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class MakeXmlElement extends Syntax {
    public static final MakeXmlElement makeXml = new MakeXmlElement();
    static final ClassType typeNamespace = ClassType.make("gnu.mapping.Namespace");

    static {
        makeXml.setName("$make-xml$");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Namespace namespace;
        Object value;
        Object namespaceList;
        Pair pair1;
        Translator translator = tr;
        Pair pair12 = (Pair) form.getCdr();
        Object namespaceList2 = pair12.getCar();
        Object obj = pair12.getCdr();
        NamespaceBinding saveBindings = translator.xmlElementNamespaces;
        NamespaceBinding nsBindings = saveBindings;
        boolean nsSeen = false;
        Object namespaceList3 = namespaceList2;
        while (namespaceList3 instanceof Pair) {
            if (!nsSeen) {
                tr.letStart();
                nsSeen = true;
            }
            Pair namespacePair = (Pair) namespaceList3;
            Pair namespaceNode = (Pair) namespacePair.getCar();
            String nsPrefix = (String) namespaceNode.getCar();
            String nsPrefix2 = nsPrefix.length() == 0 ? null : nsPrefix.intern();
            Object valueList = namespaceNode.getCdr();
            StringBuilder sbuf = new StringBuilder();
            while (valueList instanceof Pair) {
                Pair valuePair = (Pair) valueList;
                Object valueForm = valuePair.getCar();
                if (LList.listLength(valueForm, false) == 2 && (valueForm instanceof Pair) && ((Pair) valueForm).getCar() == MakeText.makeText) {
                    value = ((Pair) ((Pair) valueForm).getCdr()).getCar();
                } else {
                    value = translator.rewrite_car(valuePair, false).valueIfConstant();
                }
                if (value == null) {
                    Object savePos = translator.pushPositionOf(valuePair);
                    pair1 = pair12;
                    namespaceList = namespaceList3;
                    translator.error('e', "namespace URI must be literal");
                    translator.popPositionOf(savePos);
                } else {
                    pair1 = pair12;
                    namespaceList = namespaceList3;
                    sbuf.append(value);
                }
                valueList = valuePair.getCdr();
                pair12 = pair1;
                namespaceList3 = namespaceList;
            }
            Pair pair13 = pair12;
            Object obj2 = namespaceList3;
            String nsUri = sbuf.toString().intern();
            nsBindings = new NamespaceBinding(nsPrefix2, nsUri == "" ? null : nsUri, nsBindings);
            if (nsPrefix2 == null) {
                namespace = Namespace.valueOf(nsUri);
                nsPrefix2 = "[default-element-namespace]";
            } else {
                namespace = XmlNamespace.getInstance(nsPrefix2, nsUri);
            }
            translator.letVariable(Namespace.EmptyNamespace.getSymbol(nsPrefix2), typeNamespace, new QuoteExp(namespace)).setFlag(2121728);
            namespaceList3 = namespacePair.getCdr();
            pair12 = pair13;
        }
        Object obj3 = namespaceList3;
        MakeElement mkElement = new MakeElement();
        mkElement.setNamespaceNodes(nsBindings);
        translator.xmlElementNamespaces = nsBindings;
        if (nsSeen) {
            try {
                tr.letEnter();
            } catch (Throwable th) {
                th = th;
                Pair pair = form;
            }
        }
        try {
            Expression result = translator.rewrite(Translator.makePair(form, mkElement, obj));
            Expression letDone = nsSeen ? translator.letDone(result) : result;
            translator.xmlElementNamespaces = saveBindings;
            return letDone;
        } catch (Throwable th2) {
            th = th2;
            translator.xmlElementNamespaces = saveBindings;
            throw th;
        }
    }
}
