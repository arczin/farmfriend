package gnu.xml;

import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class XName extends Symbol implements Externalizable {
    NamespaceBinding namespaceNodes;

    public XName() {
    }

    public XName(Symbol symbol, NamespaceBinding namespaceNodes2) {
        super(symbol.getNamespace(), symbol.getName());
        this.namespaceNodes = namespaceNodes2;
    }

    public final NamespaceBinding getNamespaceNodes() {
        return this.namespaceNodes;
    }

    public final void setNamespaceNodes(NamespaceBinding nodes) {
        this.namespaceNodes = nodes;
    }

    /* access modifiers changed from: package-private */
    public String lookupNamespaceURI(String prefix) {
        for (NamespaceBinding ns = this.namespaceNodes; ns != null; ns = ns.next) {
            if (prefix == ns.prefix) {
                return ns.uri;
            }
        }
        return null;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(this.namespaceNodes);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.namespaceNodes = (NamespaceBinding) in.readObject();
    }

    public static boolean isNameStart(int ch) {
        return Character.isUnicodeIdentifierStart(ch) || ch == 95;
    }

    public static boolean isNamePart(int ch) {
        return Character.isUnicodeIdentifierPart(ch) || ch == 45 || ch == 46;
    }

    public static boolean isNmToken(String value) {
        return checkName(value) >= 0;
    }

    public static boolean isName(String value) {
        return checkName(value) > 0;
    }

    public static boolean isNCName(String value) {
        return checkName(value) > 1;
    }

    public static int checkName(String value) {
        int len = value.length();
        if (len == 0) {
            return -1;
        }
        int result = 2;
        int ch = 0;
        while (ch < len) {
            boolean first = ch == 0;
            int i = ch + 1;
            int ch2 = value.charAt(ch);
            if (ch2 >= 55296 && ch2 < 56320 && i < len) {
                ch2 = ((ch2 - 55296) * 1024) + (value.charAt(i) - 56320) + 65536;
                i++;
            }
            if (ch2 == 58) {
                if (result == 2) {
                    result = 1;
                }
            } else if (!isNamePart(ch2)) {
                return -1;
            } else {
                if (first && !isNameStart(ch2)) {
                    result = 0;
                }
            }
            ch = i;
        }
        return result;
    }
}
