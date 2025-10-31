package gnu.xml;

import androidx.appcompat.widget.ActivityChooserView;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.text.URIPath;
import java.io.Writer;

public class NodeTree extends TreeList {
    static int counter;
    int id;
    int idCount;
    String[] idNames;
    int[] idOffsets;

    public int nextPos(int position) {
        if ((position & 1) != 0) {
        }
        int index = posToDataIndex(position);
        int next = nextNodeIndex(index, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (next != index) {
            return next << 1;
        }
        if (index == this.data.length) {
            return 0;
        }
        return (index << 1) + 3;
    }

    public static NodeTree make() {
        return new NodeTree();
    }

    public int getId() {
        if (this.id == 0) {
            int i = counter + 1;
            counter = i;
            this.id = i;
        }
        return this.id;
    }

    public int stableCompare(AbstractSequence other) {
        int comp = 0;
        if (this == other) {
            return 0;
        }
        int comp2 = super.stableCompare(other);
        if (comp2 != 0 || !(other instanceof NodeTree)) {
            return comp2;
        }
        int id1 = getId();
        int id2 = ((NodeTree) other).getId();
        if (id1 < id2) {
            comp = -1;
        } else if (id1 > id2) {
            comp = 1;
        }
        return comp;
    }

    public SeqPosition getIteratorAtPos(int ipos) {
        return KNode.make(this, ipos);
    }

    public String posNamespaceURI(int ipos) {
        Object type = getNextTypeObject(ipos);
        if (type instanceof XName) {
            return ((XName) type).getNamespaceURI();
        }
        if (type instanceof Symbol) {
            return ((Symbol) type).getNamespaceURI();
        }
        return null;
    }

    public String posPrefix(int ipos) {
        int colon;
        String name = getNextTypeName(ipos);
        if (name != null && (colon = name.indexOf(58)) >= 0) {
            return name.substring(0, colon);
        }
        return null;
    }

    public String posLocalName(int ipos) {
        Object type = getNextTypeObject(ipos);
        if (type instanceof XName) {
            return ((XName) type).getLocalPart();
        }
        if (type instanceof Symbol) {
            return ((Symbol) type).getLocalName();
        }
        return getNextTypeName(ipos);
    }

    public boolean posIsDefaultNamespace(int ipos, String namespaceURI) {
        throw new Error("posIsDefaultNamespace not implemented");
    }

    public String posLookupNamespaceURI(int ipos, String prefix) {
        if (getNextKind(ipos) == 33) {
            Object type = getNextTypeObject(ipos);
            if (type instanceof XName) {
                return ((XName) type).lookupNamespaceURI(prefix);
            }
            return null;
        }
        throw new IllegalArgumentException("argument must be an element");
    }

    public String posLookupPrefix(int ipos, String namespaceURI) {
        throw new Error("posLookupPrefix not implemented");
    }

    public int posFirstChild(int ipos) {
        char datum;
        int index = gotoChildrenStart(posToDataIndex(ipos));
        if (index < 0 || (datum = this.data[index]) == 61707 || datum == 61708 || datum == 61713) {
            return -1;
        }
        return index << 1;
    }

    public boolean posHasAttributes(int ipos) {
        int index = gotoAttributesStart(posToDataIndex(ipos));
        if (index >= 0 && index >= 0 && this.data[index] == 61705) {
            return true;
        }
        return false;
    }

    public int getAttribute(int parent, String namespaceURI, String localName) {
        String str = null;
        String intern = namespaceURI == null ? null : namespaceURI.intern();
        if (localName != null) {
            str = localName.intern();
        }
        return getAttributeI(parent, intern, str);
    }

    public int getAttributeI(int parent, String namespaceURI, String localName) {
        int attr = firstAttributePos(parent);
        while (attr != 0 && getNextKind(attr) == 35) {
            if ((localName == null || posLocalName(attr) == localName) && (namespaceURI == null || posNamespaceURI(attr) == namespaceURI)) {
                return attr;
            }
            attr = nextPos(attr);
        }
        return 0;
    }

    public Object typedValue(int ipos) {
        StringBuffer sbuf = new StringBuffer();
        stringValue(posToDataIndex(ipos), sbuf);
        String str = sbuf.toString();
        int kind = getNextKind(ipos);
        if (kind == 37 || kind == 36) {
            return str;
        }
        return new UntypedAtomic(str);
    }

    public String posTarget(int ipos) {
        int index = posToDataIndex(ipos);
        if (this.data[index] == 61716) {
            return (String) this.objects[getIntN(index + 1)];
        }
        throw new ClassCastException("expected process-instruction");
    }

    public int ancestorAttribute(int ipos, String namespace, String name) {
        while (ipos != -1) {
            int attr = getAttributeI(ipos, namespace, name);
            if (attr != 0) {
                return attr;
            }
            ipos = parentPos(ipos);
        }
        return 0;
    }

    public Path baseUriOfPos(int pos, boolean resolveRelative) {
        int attr;
        Path uri = null;
        int index = posToDataIndex(pos);
        while (index != this.data.length) {
            char datum = this.data[index];
            Path base = null;
            if (datum == 61714) {
                int oindex = getIntN(index + 1);
                if (oindex >= 0) {
                    base = URIPath.makeURI(this.objects[oindex]);
                }
            } else if (((datum >= 40960 && datum <= 45055) || datum == 61704) && (attr = getAttributeI(pos, NamespaceBinding.XML_NAMESPACE, "base")) != 0) {
                base = URIPath.valueOf(KNode.getNodeValue(this, attr));
            }
            if (base != null) {
                uri = (uri == null || !resolveRelative) ? base : base.resolve(uri);
                if (uri.isAbsolute() || !resolveRelative) {
                    return uri;
                }
            }
            index = parentOrEntityI(index);
            if (index == -1) {
                return uri;
            }
            pos = index << 1;
        }
        return null;
    }

    public String toString() {
        CharArrayOutPort wr = new CharArrayOutPort();
        consume((Consumer) new XMLPrinter((Writer) wr));
        wr.close();
        return wr.toString();
    }

    public void makeIDtableIfNeeded() {
        if (this.idNames == null) {
            this.idNames = new String[64];
            this.idOffsets = new int[64];
            int limit = endPos();
            int ipos = 0;
            while (true) {
                ipos = nextMatching(ipos, ElementType.anyElement, limit, true);
                if (ipos != 0) {
                    int attr = getAttributeI(ipos, NamespaceBinding.XML_NAMESPACE, "id");
                    if (attr != 0) {
                        enterID(KNode.getNodeValue(this, attr), ipos);
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void enterID(String name, int offset) {
        int size;
        String[] tmpNames = this.idNames;
        int[] tmpOffsets = this.idOffsets;
        if (tmpNames == null) {
            size = 64;
            this.idNames = new String[64];
            this.idOffsets = new int[64];
        } else {
            int i = this.idCount * 4;
            int length = this.idNames.length;
            int size2 = length;
            if (i >= length * 3) {
                this.idNames = new String[(size2 * 2)];
                this.idOffsets = new int[(size2 * 2)];
                this.idCount = 0;
                int i2 = size2;
                while (true) {
                    i2--;
                    if (i2 < 0) {
                        break;
                    }
                    String oldName = tmpNames[i2];
                    if (oldName != null) {
                        enterID(oldName, tmpOffsets[i2]);
                    }
                }
                tmpNames = this.idNames;
                tmpOffsets = this.idOffsets;
                size = size2 * 2;
            } else {
                size = size2;
            }
        }
        int hash = name.hashCode();
        int mask = size - 1;
        int index = hash & mask;
        int step = ((hash ^ -1) << 1) | 1;
        while (true) {
            String oldName2 = tmpNames[index];
            if (oldName2 == null) {
                tmpNames[index] = name;
                tmpOffsets[index] = offset;
                this.idCount++;
                return;
            } else if (!oldName2.equals(name)) {
                index = (index + step) & mask;
            } else {
                return;
            }
        }
    }

    public int lookupID(String name) {
        String[] tmpNames = this.idNames;
        int[] tmpOffsets = this.idOffsets;
        int size = this.idNames.length;
        int hash = name.hashCode();
        int mask = size - 1;
        int index = hash & mask;
        int step = ((hash ^ -1) << 1) | 1;
        while (true) {
            String oldName = tmpNames[index];
            if (oldName == null) {
                return -1;
            }
            if (oldName.equals(name)) {
                return tmpOffsets[index];
            }
            index = (index + step) & mask;
        }
    }
}
