package gnu.lists;

import androidx.appcompat.widget.ActivityChooserView;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.text.Char;
import java.io.PrintWriter;

public class TreeList extends AbstractSequence implements Appendable, XConsumer, PositionConsumer, Consumable {
    protected static final int BEGIN_ATTRIBUTE_LONG = 61705;
    public static final int BEGIN_ATTRIBUTE_LONG_SIZE = 5;
    protected static final int BEGIN_DOCUMENT = 61712;
    protected static final int BEGIN_ELEMENT_LONG = 61704;
    protected static final int BEGIN_ELEMENT_SHORT = 40960;
    protected static final int BEGIN_ELEMENT_SHORT_INDEX_MAX = 4095;
    public static final int BEGIN_ENTITY = 61714;
    public static final int BEGIN_ENTITY_SIZE = 5;
    static final char BOOL_FALSE = '';
    static final char BOOL_TRUE = '';
    static final int BYTE_PREFIX = 61440;
    static final int CDATA_SECTION = 61717;
    static final int CHAR_FOLLOWS = 61702;
    static final int COMMENT = 61719;
    protected static final int DOCUMENT_URI = 61720;
    static final int DOUBLE_FOLLOWS = 61701;
    static final int END_ATTRIBUTE = 61706;
    public static final int END_ATTRIBUTE_SIZE = 1;
    protected static final int END_DOCUMENT = 61713;
    protected static final int END_ELEMENT_LONG = 61708;
    protected static final int END_ELEMENT_SHORT = 61707;
    protected static final int END_ENTITY = 61715;
    static final int FLOAT_FOLLOWS = 61700;
    public static final int INT_FOLLOWS = 61698;
    static final int INT_SHORT_ZERO = 49152;
    static final int JOINER = 61718;
    static final int LONG_FOLLOWS = 61699;
    public static final int MAX_CHAR_SHORT = 40959;
    static final int MAX_INT_SHORT = 8191;
    static final int MIN_INT_SHORT = -4096;
    static final char OBJECT_REF_FOLLOWS = '';
    static final int OBJECT_REF_SHORT = 57344;
    static final int OBJECT_REF_SHORT_INDEX_MAX = 4095;
    protected static final char POSITION_PAIR_FOLLOWS = '';
    static final char POSITION_REF_FOLLOWS = '';
    protected static final int PROCESSING_INSTRUCTION = 61716;
    public int attrStart;
    int currentParent;
    public char[] data;
    public int docStart;
    public int gapEnd;
    public int gapStart;
    public Object[] objects;
    public int oindex;

    public TreeList() {
        this.currentParent = -1;
        resizeObjects();
        this.gapEnd = HttpRequestContext.HTTP_OK;
        this.data = new char[this.gapEnd];
    }

    public TreeList(TreeList list, int startPosition, int endPosition) {
        this();
        list.consumeIRange(startPosition, endPosition, this);
    }

    public TreeList(TreeList list) {
        this(list, 0, list.data.length);
    }

    public void clear() {
        this.gapStart = 0;
        this.gapEnd = this.data.length;
        this.attrStart = 0;
        if (this.gapEnd > 1500) {
            this.gapEnd = HttpRequestContext.HTTP_OK;
            this.data = new char[this.gapEnd];
        }
        this.objects = null;
        this.oindex = 0;
        resizeObjects();
    }

    public void ensureSpace(int needed) {
        int avail = this.gapEnd - this.gapStart;
        if (needed > avail) {
            int oldSize = this.data.length;
            int neededSize = (oldSize - avail) + needed;
            int newSize = oldSize * 2;
            if (newSize < neededSize) {
                newSize = neededSize;
            }
            char[] tmp = new char[newSize];
            if (this.gapStart > 0) {
                System.arraycopy(this.data, 0, tmp, 0, this.gapStart);
            }
            int afterGap = oldSize - this.gapEnd;
            if (afterGap > 0) {
                System.arraycopy(this.data, this.gapEnd, tmp, newSize - afterGap, afterGap);
            }
            this.gapEnd = newSize - afterGap;
            this.data = tmp;
        }
    }

    public final void resizeObjects() {
        Object[] tmp;
        if (this.objects == null) {
            tmp = new Object[100];
        } else {
            int oldLength = this.objects.length;
            tmp = new Object[(oldLength * 2)];
            System.arraycopy(this.objects, 0, tmp, 0, oldLength);
        }
        this.objects = tmp;
    }

    public int find(Object arg1) {
        if (this.oindex == this.objects.length) {
            resizeObjects();
        }
        this.objects[this.oindex] = arg1;
        int i = this.oindex;
        this.oindex = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public final int getIntN(int index) {
        return (this.data[index] << 16) | (this.data[index + 1] & LispReader.TOKEN_ESCAPE_CHAR);
    }

    /* access modifiers changed from: protected */
    public final long getLongN(int index) {
        char[] data2 = this.data;
        return ((((long) data2[index]) & 65535) << 48) | ((((long) data2[index + 1]) & 65535) << 32) | ((((long) data2[index + 2]) & 65535) << 16) | (65535 & ((long) data2[index + 3]));
    }

    public final void setIntN(int index, int i) {
        this.data[index] = (char) (i >> 16);
        this.data[index + 1] = (char) i;
    }

    public void consume(SeqPosition position) {
        ensureSpace(3);
        int index = find(position.copy());
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = POSITION_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        ensureSpace(5);
        this.data[this.gapStart] = POSITION_PAIR_FOLLOWS;
        setIntN(this.gapStart + 1, find(seq));
        setIntN(this.gapStart + 3, ipos);
        this.gapStart += 5;
    }

    public void writeObject(Object v) {
        ensureSpace(3);
        int index = find(v);
        if (index < 4096) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) (OBJECT_REF_SHORT | index);
            return;
        }
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = OBJECT_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeDocumentUri(Object uri) {
        ensureSpace(3);
        int index = find(uri);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61720;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeComment(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61719;
        setIntN(i2, length);
        int i3 = i2 + 2;
        System.arraycopy(chars, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public void writeComment(String comment, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61719;
        setIntN(i2, length);
        int i3 = i2 + 2;
        comment.getChars(offset, offset + length, this.data, i3);
        this.gapStart = i3 + length;
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61716;
        setIntN(i2, find(target));
        setIntN(i2 + 2, length);
        int i3 = i2 + 4;
        System.arraycopy(content, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public void writeProcessingInstruction(String target, String content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61716;
        setIntN(i2, find(target));
        setIntN(i2 + 2, length);
        int i3 = i2 + 4;
        content.getChars(offset, offset + length, this.data, i3);
        this.gapStart = i3 + length;
    }

    public void startElement(Object type) {
        startElement(find(type));
    }

    public void startDocument() {
        ensureSpace(6);
        this.gapEnd--;
        int p = this.gapStart;
        this.data[p] = 61712;
        if (this.docStart == 0) {
            this.docStart = p + 1;
            setIntN(p + 1, this.gapEnd - this.data.length);
            int i = p + 3;
            int i2 = -1;
            if (this.currentParent != -1) {
                i2 = this.currentParent - p;
            }
            setIntN(i, i2);
            this.currentParent = p;
            this.gapStart = p + 5;
            this.currentParent = p;
            this.data[this.gapEnd] = 61713;
            return;
        }
        throw new Error("nested document");
    }

    public void endDocument() {
        if (this.data[this.gapEnd] == END_DOCUMENT && this.docStart > 0 && this.data[this.currentParent] == BEGIN_DOCUMENT) {
            this.gapEnd++;
            setIntN(this.docStart, (this.gapStart - this.docStart) + 1);
            this.docStart = 0;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61713;
            int parent = getIntN(this.currentParent + 3);
            this.currentParent = parent >= -1 ? parent : this.currentParent + parent;
            return;
        }
        throw new Error("unexpected endDocument");
    }

    public void beginEntity(Object base) {
        if (this.gapStart == 0) {
            ensureSpace(6);
            this.gapEnd--;
            int p = this.gapStart;
            this.data[p] = 61714;
            setIntN(p + 1, find(base));
            int i = p + 3;
            int i2 = -1;
            if (this.currentParent != -1) {
                i2 = this.currentParent - p;
            }
            setIntN(i, i2);
            this.gapStart = p + 5;
            this.currentParent = p;
            this.data[this.gapEnd] = 61715;
        }
    }

    public void endEntity() {
        if (this.gapEnd + 1 != this.data.length || this.data[this.gapEnd] != END_ENTITY) {
            return;
        }
        if (this.data[this.currentParent] == 61714) {
            this.gapEnd++;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61715;
            int parent = getIntN(this.currentParent + 3);
            this.currentParent = parent >= -1 ? parent : this.currentParent + parent;
            return;
        }
        throw new Error("unexpected endEntity");
    }

    public void startElement(int index) {
        ensureSpace(10);
        this.gapEnd -= 7;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61704;
        setIntN(this.gapStart, this.gapEnd - this.data.length);
        this.gapStart += 2;
        this.data[this.gapEnd] = 61708;
        setIntN(this.gapEnd + 1, index);
        setIntN(this.gapEnd + 3, this.gapStart - 3);
        setIntN(this.gapEnd + 5, this.currentParent);
        this.currentParent = this.gapStart - 3;
    }

    public void setElementName(int elementIndex, int nameIndex) {
        if (this.data[elementIndex] == BEGIN_ELEMENT_LONG) {
            int j = getIntN(elementIndex + 1);
            elementIndex = j + (j < 0 ? this.data.length : elementIndex);
        }
        if (elementIndex >= this.gapEnd) {
            setIntN(elementIndex + 1, nameIndex);
            return;
        }
        throw new Error("setElementName before gapEnd");
    }

    public void endElement() {
        if (this.data[this.gapEnd] == END_ELEMENT_LONG) {
            int index = getIntN(this.gapEnd + 1);
            int begin = getIntN(this.gapEnd + 3);
            int parent = getIntN(this.gapEnd + 5);
            this.currentParent = parent;
            this.gapEnd += 7;
            int offset = this.gapStart - begin;
            int parentOffset = begin - parent;
            if (index >= 4095 || offset >= 65536 || parentOffset >= 65536) {
                this.data[begin] = 61704;
                setIntN(begin + 1, offset);
                this.data[this.gapStart] = 61708;
                setIntN(this.gapStart + 1, index);
                setIntN(this.gapStart + 3, -offset);
                if (parent >= this.gapStart || begin <= this.gapStart) {
                    parent -= this.gapStart;
                }
                setIntN(this.gapStart + 5, parent);
                this.gapStart += 7;
                return;
            }
            this.data[begin] = (char) (BEGIN_ELEMENT_SHORT | index);
            this.data[begin + 1] = (char) offset;
            this.data[begin + 2] = (char) parentOffset;
            this.data[this.gapStart] = 61707;
            this.data[this.gapStart + 1] = (char) offset;
            this.gapStart += 2;
            return;
        }
        throw new Error("unexpected endElement");
    }

    public void startAttribute(Object attrType) {
        startAttribute(find(attrType));
    }

    public void startAttribute(int index) {
        ensureSpace(6);
        this.gapEnd--;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61705;
        if (this.attrStart == 0) {
            this.attrStart = this.gapStart;
            setIntN(this.gapStart, index);
            setIntN(this.gapStart + 2, this.gapEnd - this.data.length);
            this.gapStart += 4;
            this.data[this.gapEnd] = 61706;
            return;
        }
        throw new Error("nested attribute");
    }

    public void setAttributeName(int attrIndex, int nameIndex) {
        setIntN(attrIndex + 1, nameIndex);
    }

    public void endAttribute() {
        if (this.attrStart > 0) {
            if (this.data[this.gapEnd] == END_ATTRIBUTE) {
                this.gapEnd++;
                setIntN(this.attrStart + 2, (this.gapStart - this.attrStart) + 1);
                this.attrStart = 0;
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = 61706;
                return;
            }
            throw new Error("unexpected endAttribute");
        }
    }

    public Consumer append(char c) {
        write((int) c);
        return this;
    }

    public void write(int c) {
        ensureSpace(3);
        if (c <= 40959) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) c;
        } else if (c < 65536) {
            char[] cArr2 = this.data;
            int i2 = this.gapStart;
            this.gapStart = i2 + 1;
            cArr2[i2] = 61702;
            char[] cArr3 = this.data;
            int i3 = this.gapStart;
            this.gapStart = i3 + 1;
            cArr3[i3] = (char) c;
        } else {
            Char.print(c, this);
        }
    }

    public void writeBoolean(boolean v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = v ? BOOL_TRUE : BOOL_FALSE;
    }

    public void writeByte(int v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((v & 255) + BYTE_PREFIX);
    }

    public void writeInt(int v) {
        ensureSpace(3);
        if (v < MIN_INT_SHORT || v > MAX_INT_SHORT) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61698;
            setIntN(this.gapStart, v);
            this.gapStart += 2;
            return;
        }
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) (INT_SHORT_ZERO + v);
    }

    public void writeLong(long v) {
        ensureSpace(5);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61699;
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) ((int) (v >>> 48));
        char[] cArr3 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr3[i3] = (char) ((int) (v >>> 32));
        char[] cArr4 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr4[i4] = (char) ((int) (v >>> 16));
        char[] cArr5 = this.data;
        int i5 = this.gapStart;
        this.gapStart = i5 + 1;
        cArr5[i5] = (char) ((int) v);
    }

    public void writeFloat(float v) {
        ensureSpace(3);
        int i = Float.floatToIntBits(v);
        char[] cArr = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr[i2] = 61700;
        char[] cArr2 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr2[i3] = (char) (i >>> 16);
        char[] cArr3 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr3[i4] = (char) i;
    }

    public void writeDouble(double v) {
        ensureSpace(5);
        long l = Double.doubleToLongBits(v);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61701;
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) ((int) (l >>> 48));
        char[] cArr3 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr3[i3] = (char) ((int) (l >>> 32));
        char[] cArr4 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr4[i4] = (char) ((int) (l >>> 16));
        char[] cArr5 = this.data;
        int i5 = this.gapStart;
        this.gapStart = i5 + 1;
        cArr5[i5] = (char) ((int) l);
    }

    public boolean ignoring() {
        return false;
    }

    public void writeJoiner() {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61718;
    }

    public void write(char[] buf, int off, int len) {
        if (len == 0) {
            writeJoiner();
        }
        ensureSpace(len);
        while (len > 0) {
            int off2 = off + 1;
            char ch = buf[off];
            len--;
            if (ch <= 40959) {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = ch;
            } else {
                write((int) ch);
                ensureSpace(len);
            }
            off = off2;
        }
    }

    public void write(String str) {
        write((CharSequence) str, 0, str.length());
    }

    public void write(CharSequence str, int start, int length) {
        if (length == 0) {
            writeJoiner();
        }
        ensureSpace(length);
        while (length > 0) {
            int start2 = start + 1;
            char start3 = str.charAt(start);
            length--;
            if (start3 <= 40959) {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = start3;
            } else {
                write((int) start3);
                ensureSpace(length);
            }
            start = start2;
        }
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61717;
        setIntN(i2, length);
        int i3 = i2 + 2;
        System.arraycopy(chars, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public Consumer append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    public Consumer append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        for (int i = start; i < end; i++) {
            append(csq.charAt(i));
        }
        return this;
    }

    public boolean isEmpty() {
        if ((this.gapStart == 0 ? this.gapEnd : 0) == this.data.length) {
            return true;
        }
        return false;
    }

    public int size() {
        int size = 0;
        int i = 0;
        while (true) {
            i = nextPos(i);
            if (i == 0) {
                return size;
            }
            size++;
        }
    }

    public int createPos(int index, boolean isAfter) {
        return createRelativePos(0, index, isAfter);
    }

    public final int posToDataIndex(int ipos) {
        if (ipos == -1) {
            return this.data.length;
        }
        int index = ipos >>> 1;
        if ((ipos & 1) != 0) {
            index--;
        }
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if ((ipos & 1) == 0) {
            return index;
        }
        int index2 = nextDataIndex(index);
        if (index2 < 0) {
            return this.data.length;
        }
        if (index2 == this.gapStart) {
            return index2 + (this.gapEnd - this.gapStart);
        }
        return index2;
    }

    public int firstChildPos(int ipos) {
        int index = gotoChildrenStart(posToDataIndex(ipos));
        if (index < 0) {
            return 0;
        }
        return index << 1;
    }

    public final int gotoChildrenStart(int index) {
        int index2;
        int index3;
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) || datum == BEGIN_ELEMENT_LONG) {
            index2 = index + 3;
        } else if (datum != BEGIN_DOCUMENT && datum != 61714) {
            return -1;
        } else {
            index2 = index + 5;
        }
        while (true) {
            if (index2 >= this.gapStart) {
                index2 += this.gapEnd - this.gapStart;
            }
            char datum2 = this.data[index2];
            if (datum2 == BEGIN_ATTRIBUTE_LONG) {
                int end = getIntN(index2 + 3);
                index3 = end + (end < 0 ? this.data.length : index2);
            } else if (datum2 == END_ATTRIBUTE || datum2 == JOINER) {
                index3 = index2 + 1;
            } else if (datum2 != DOCUMENT_URI) {
                return index2;
            } else {
                index3 = index2 + 3;
            }
        }
    }

    public int parentPos(int ipos) {
        int index = posToDataIndex(ipos);
        do {
            index = parentOrEntityI(index);
            if (index == -1) {
                return -1;
            }
        } while (this.data[index] == 61714);
        return index << 1;
    }

    public int parentOrEntityPos(int ipos) {
        int index = parentOrEntityI(posToDataIndex(ipos));
        if (index < 0) {
            return -1;
        }
        return index << 1;
    }

    public int parentOrEntityI(int i) {
        if (i == this.data.length) {
            return -1;
        }
        char c = this.data[i];
        if (c == BEGIN_DOCUMENT || c == 61714) {
            int intN = getIntN(i + 3);
            if (intN >= -1) {
                return intN;
            }
            return i + intN;
        } else if (c < BEGIN_ELEMENT_SHORT || c > 45055) {
            if (c != BEGIN_ELEMENT_LONG) {
                while (true) {
                    if (i == this.gapStart) {
                        i = this.gapEnd;
                    }
                    if (i != this.data.length) {
                        switch (this.data[i]) {
                            case END_ATTRIBUTE /*61706*/:
                                i++;
                                break;
                            case END_ELEMENT_SHORT /*61707*/:
                                return i - this.data[i + 1];
                            case END_ELEMENT_LONG /*61708*/:
                                int intN2 = getIntN(i + 3);
                                if (intN2 < 0) {
                                    return intN2 + i;
                                }
                                return intN2;
                            case END_DOCUMENT /*61713*/:
                                return -1;
                            default:
                                i = nextDataIndex(i);
                                if (i >= 0) {
                                    break;
                                } else {
                                    return -1;
                                }
                        }
                    } else {
                        return -1;
                    }
                }
            } else {
                int intN3 = getIntN(i + 1);
                if (intN3 < 0) {
                    i = this.data.length;
                }
                int i2 = intN3 + i;
                int intN4 = getIntN(i2 + 5);
                if (intN4 == 0) {
                    return -1;
                }
                if (intN4 < 0) {
                    return intN4 + i2;
                }
                return intN4;
            }
        } else {
            char c2 = this.data[i + 2];
            if (c2 == 0) {
                return -1;
            }
            return i - c2;
        }
    }

    public int getAttributeCount(int parent) {
        int n = 0;
        int attr = firstAttributePos(parent);
        while (attr != 0 && getNextKind(attr) == 35) {
            n++;
            attr = nextPos(attr);
        }
        return n;
    }

    public boolean gotoAttributesStart(TreePosition pos) {
        int index = gotoAttributesStart(pos.ipos >> 1);
        if (index < 0) {
            return false;
        }
        pos.push(this, index << 1);
        return true;
    }

    public int firstAttributePos(int ipos) {
        int index = gotoAttributesStart(posToDataIndex(ipos));
        if (index < 0) {
            return 0;
        }
        return index << 1;
    }

    public int gotoAttributesStart(int index) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum < BEGIN_ELEMENT_SHORT || datum > 45055) && datum != BEGIN_ELEMENT_LONG) {
            return -1;
        }
        return index + 3;
    }

    public Object get(int index) {
        int i = 0;
        do {
            index--;
            if (index < 0) {
                return getPosNext(i);
            }
            i = nextPos(i);
        } while (i != 0);
        throw new IndexOutOfBoundsException();
    }

    public boolean consumeNext(int ipos, Consumer out) {
        if (!hasNext(ipos)) {
            return false;
        }
        int start = posToDataIndex(ipos);
        int end = nextNodeIndex(start, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (end == start) {
            end = nextDataIndex(start);
        }
        if (end < 0) {
            return true;
        }
        consumeIRange(start, end, out);
        return true;
    }

    public void consumePosRange(int startPos, int endPos, Consumer out) {
        consumeIRange(posToDataIndex(startPos), posToDataIndex(endPos), out);
    }

    public int consumeIRange(int i, int i2, Consumer consumer) {
        int i3 = (i > this.gapStart || i2 <= this.gapStart) ? i2 : this.gapStart;
        while (true) {
            if (i >= i3) {
                if (i != this.gapStart || i2 <= this.gapEnd) {
                    return i;
                }
                i = this.gapEnd;
                i3 = i2;
            }
            int i4 = i + 1;
            char c = this.data[i];
            if (c <= 40959) {
                int i5 = i4 - 1;
                while (true) {
                    if (i4 >= i3) {
                        i = i4;
                    } else {
                        int i6 = i4 + 1;
                        if (this.data[i4] > 40959) {
                            i = i6 - 1;
                        } else {
                            i4 = i6;
                        }
                    }
                }
                consumer.write(this.data, i5, i - i5);
            } else {
                if (c >= OBJECT_REF_SHORT && c <= 61439) {
                    consumer.writeObject(this.objects[c - OBJECT_REF_SHORT]);
                } else if (c >= BEGIN_ELEMENT_SHORT && c <= 45055) {
                    consumer.startElement(this.objects[c - BEGIN_ELEMENT_SHORT]);
                    i = i4 + 2;
                } else if (c < 45056 || c > 57343) {
                    boolean z = true;
                    switch (c) {
                        case 61696:
                        case 61697:
                            if (c == 61696) {
                                z = false;
                            }
                            consumer.writeBoolean(z);
                            break;
                        case INT_FOLLOWS /*61698*/:
                            consumer.writeInt(getIntN(i4));
                            i = i4 + 2;
                            continue;
                        case LONG_FOLLOWS /*61699*/:
                            consumer.writeLong(getLongN(i4));
                            i = i4 + 4;
                            continue;
                        case FLOAT_FOLLOWS /*61700*/:
                            consumer.writeFloat(Float.intBitsToFloat(getIntN(i4)));
                            i = i4 + 2;
                            continue;
                        case DOUBLE_FOLLOWS /*61701*/:
                            consumer.writeDouble(Double.longBitsToDouble(getLongN(i4)));
                            i = i4 + 4;
                            continue;
                        case CHAR_FOLLOWS /*61702*/:
                            consumer.write(this.data, i4, (c + 1) - CHAR_FOLLOWS);
                            i = i4 + 1;
                            continue;
                        case BEGIN_ELEMENT_LONG /*61704*/:
                            int intN = getIntN(i4);
                            consumer.startElement(this.objects[getIntN(intN + (intN >= 0 ? i4 - 1 : this.data.length) + 1)]);
                            i = i4 + 2;
                            continue;
                        case BEGIN_ATTRIBUTE_LONG /*61705*/:
                            consumer.startAttribute(this.objects[getIntN(i4)]);
                            i = i4 + 4;
                            continue;
                        case END_ATTRIBUTE /*61706*/:
                            consumer.endAttribute();
                            break;
                        case END_ELEMENT_SHORT /*61707*/:
                            i = i4 + 1;
                            consumer.endElement();
                            continue;
                        case END_ELEMENT_LONG /*61708*/:
                            getIntN(i4);
                            consumer.endElement();
                            i = i4 + 6;
                            continue;
                        case 61710:
                            if (consumer instanceof PositionConsumer) {
                                ((PositionConsumer) consumer).consume((SeqPosition) this.objects[getIntN(i4)]);
                                i = i4 + 2;
                                continue;
                            }
                        case 61709:
                            consumer.writeObject(this.objects[getIntN(i4)]);
                            i = i4 + 2;
                            continue;
                        case 61711:
                            AbstractSequence abstractSequence = (AbstractSequence) this.objects[getIntN(i4)];
                            int intN2 = getIntN(i4 + 2);
                            if (consumer instanceof PositionConsumer) {
                                ((PositionConsumer) consumer).writePosition(abstractSequence, intN2);
                            } else {
                                consumer.writeObject(abstractSequence.getIteratorAtPos(intN2));
                            }
                            i = i4 + 4;
                            continue;
                        case BEGIN_DOCUMENT /*61712*/:
                            consumer.startDocument();
                            i = i4 + 4;
                            continue;
                        case END_DOCUMENT /*61713*/:
                            consumer.endDocument();
                            break;
                        case BEGIN_ENTITY /*61714*/:
                            if (consumer instanceof TreeList) {
                                ((TreeList) consumer).beginEntity(this.objects[getIntN(i4)]);
                            }
                            i = i4 + 4;
                            continue;
                        case END_ENTITY /*61715*/:
                            if (consumer instanceof TreeList) {
                                ((TreeList) consumer).endEntity();
                                break;
                            }
                            break;
                        case PROCESSING_INSTRUCTION /*61716*/:
                            String str = (String) this.objects[getIntN(i4)];
                            int intN3 = getIntN(i4 + 2);
                            int i7 = i4 + 4;
                            if (consumer instanceof XConsumer) {
                                ((XConsumer) consumer).writeProcessingInstruction(str, this.data, i7, intN3);
                            }
                            i = i7 + intN3;
                            continue;
                        case CDATA_SECTION /*61717*/:
                            int intN4 = getIntN(i4);
                            int i8 = i4 + 2;
                            if (consumer instanceof XConsumer) {
                                ((XConsumer) consumer).writeCDATA(this.data, i8, intN4);
                            } else {
                                consumer.write(this.data, i8, intN4);
                            }
                            i = intN4 + i8;
                            continue;
                        case JOINER /*61718*/:
                            consumer.write("");
                            break;
                        case COMMENT /*61719*/:
                            int intN5 = getIntN(i4);
                            int i9 = i4 + 2;
                            if (consumer instanceof XConsumer) {
                                ((XConsumer) consumer).writeComment(this.data, i9, intN5);
                            }
                            i = intN5 + i9;
                            continue;
                        case DOCUMENT_URI /*61720*/:
                            if (consumer instanceof TreeList) {
                                ((TreeList) consumer).writeDocumentUri(this.objects[getIntN(i4)]);
                            }
                            i = i4 + 2;
                            continue;
                        default:
                            throw new Error("unknown code:" + c);
                    }
                } else {
                    consumer.writeInt(c - INT_SHORT_ZERO);
                }
                i = i4;
            }
        }
        return i;
    }

    public void toString(String str, StringBuffer stringBuffer) {
        int i;
        int i2;
        int i3 = this.gapStart;
        int i4 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            if (i4 < i3 || (i4 == this.gapStart && (i4 = this.gapEnd) != (i3 = this.data.length))) {
                int i5 = i4 + 1;
                char c = this.data[i4];
                if (c <= 40959) {
                    int i6 = i5 - 1;
                    while (true) {
                        if (i5 >= i3) {
                            i4 = i5;
                        } else {
                            int i7 = i5 + 1;
                            if (this.data[i5] > 40959) {
                                i4 = i7 - 1;
                            } else {
                                i5 = i7;
                            }
                        }
                    }
                    if (z) {
                        stringBuffer.append('>');
                        z = false;
                    }
                    stringBuffer.append(this.data, i6, i4 - i6);
                    z2 = false;
                } else {
                    boolean z3 = true;
                    if (c >= OBJECT_REF_SHORT && c <= 61439) {
                        if (z) {
                            stringBuffer.append('>');
                            z = false;
                        }
                        if (z2) {
                            stringBuffer.append(str);
                        } else {
                            z2 = true;
                        }
                        stringBuffer.append(this.objects[c - OBJECT_REF_SHORT]);
                        i4 = i5;
                    } else if (c >= BEGIN_ELEMENT_SHORT && c <= 45055) {
                        if (z) {
                            stringBuffer.append('>');
                        }
                        int i8 = c - BEGIN_ELEMENT_SHORT;
                        if (z2) {
                            stringBuffer.append(str);
                        }
                        stringBuffer.append('<');
                        stringBuffer.append(this.objects[i8].toString());
                        i4 = i5 + 2;
                        z = true;
                        z2 = false;
                    } else if (c < 45056 || c > 57343) {
                        switch (c) {
                            case 61696:
                            case 61697:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                if (z2) {
                                    stringBuffer.append(str);
                                } else {
                                    z2 = true;
                                }
                                if (c == 61696) {
                                    z3 = false;
                                }
                                stringBuffer.append(z3);
                                i4 = i5;
                                break;
                            case INT_FOLLOWS /*61698*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                if (z2) {
                                    stringBuffer.append(str);
                                } else {
                                    z2 = true;
                                }
                                stringBuffer.append(getIntN(i5));
                                i4 = i5 + 2;
                                break;
                            case LONG_FOLLOWS /*61699*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                if (z2) {
                                    stringBuffer.append(str);
                                } else {
                                    z2 = true;
                                }
                                stringBuffer.append(getLongN(i5));
                                i4 = i5 + 4;
                                break;
                            case FLOAT_FOLLOWS /*61700*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                if (z2) {
                                    stringBuffer.append(str);
                                } else {
                                    z2 = true;
                                }
                                stringBuffer.append(Float.intBitsToFloat(getIntN(i5)));
                                i4 = i5 + 2;
                                break;
                            case DOUBLE_FOLLOWS /*61701*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                if (z2) {
                                    stringBuffer.append(str);
                                } else {
                                    z2 = true;
                                }
                                stringBuffer.append(Double.longBitsToDouble(getLongN(i5)));
                                i4 = i5 + 4;
                                break;
                            case CHAR_FOLLOWS /*61702*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                stringBuffer.append(this.data, i5, (c + 1) - CHAR_FOLLOWS);
                                i4 = i5 + 1;
                                z2 = false;
                                break;
                            case BEGIN_ELEMENT_LONG /*61704*/:
                                int intN = getIntN(i5);
                                int length = intN >= 0 ? i5 - 1 : this.data.length;
                                int i9 = i5 + 2;
                                int intN2 = getIntN(intN + length + 1);
                                if (z) {
                                    stringBuffer.append('>');
                                } else if (z2) {
                                    stringBuffer.append(str);
                                }
                                stringBuffer.append('<');
                                stringBuffer.append(this.objects[intN2]);
                                i4 = i9;
                                z = true;
                                z2 = false;
                                break;
                            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                                int intN3 = getIntN(i5);
                                stringBuffer.append(' ');
                                stringBuffer.append(this.objects[intN3]);
                                stringBuffer.append("=\"");
                                i4 = i5 + 4;
                                z = false;
                                break;
                            case END_ATTRIBUTE /*61706*/:
                                stringBuffer.append('\"');
                                i4 = i5;
                                z = true;
                                z2 = false;
                                break;
                            case END_ELEMENT_SHORT /*61707*/:
                            case END_ELEMENT_LONG /*61708*/:
                                if (c == END_ELEMENT_SHORT) {
                                    i = i5 + 1;
                                    i2 = this.data[(i - 2) - this.data[i5]] - BEGIN_ELEMENT_SHORT;
                                } else {
                                    i2 = getIntN(i5);
                                    i = i5 + 6;
                                }
                                if (z) {
                                    stringBuffer.append("/>");
                                } else {
                                    stringBuffer.append("</");
                                    stringBuffer.append(this.objects[i2]);
                                    stringBuffer.append('>');
                                }
                                i4 = i;
                                z = false;
                                z2 = true;
                                break;
                            case 61709:
                            case 61710:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                if (z2) {
                                    stringBuffer.append(str);
                                } else {
                                    z2 = true;
                                }
                                stringBuffer.append(this.objects[getIntN(i5)]);
                                i4 = i5 + 2;
                                break;
                            case 61711:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                if (z2) {
                                    stringBuffer.append(str);
                                } else {
                                    z2 = true;
                                }
                                stringBuffer.append(((AbstractSequence) this.objects[getIntN(i5)]).getIteratorAtPos(getIntN(i5 + 2)));
                                i4 = i5 + 4;
                                break;
                            case BEGIN_DOCUMENT /*61712*/:
                            case BEGIN_ENTITY /*61714*/:
                                i4 = i5 + 4;
                                break;
                            case END_DOCUMENT /*61713*/:
                            case END_ENTITY /*61715*/:
                            case JOINER /*61718*/:
                                i4 = i5;
                                break;
                            case PROCESSING_INSTRUCTION /*61716*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                stringBuffer.append("<?");
                                int intN4 = getIntN(i5);
                                int i10 = i5 + 2;
                                stringBuffer.append(this.objects[intN4]);
                                int intN5 = getIntN(i10);
                                int i11 = i10 + 2;
                                if (intN5 > 0) {
                                    stringBuffer.append(' ');
                                    stringBuffer.append(this.data, i11, intN5);
                                    i11 += intN5;
                                }
                                i4 = i11;
                                stringBuffer.append("?>");
                                break;
                            case CDATA_SECTION /*61717*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                int intN6 = getIntN(i5);
                                int i12 = i5 + 2;
                                stringBuffer.append("<![CDATA[");
                                stringBuffer.append(this.data, i12, intN6);
                                stringBuffer.append("]]>");
                                i4 = intN6 + i12;
                                break;
                            case COMMENT /*61719*/:
                                if (z) {
                                    stringBuffer.append('>');
                                    z = false;
                                }
                                int intN7 = getIntN(i5);
                                int i13 = i5 + 2;
                                stringBuffer.append("<!--");
                                stringBuffer.append(this.data, i13, intN7);
                                stringBuffer.append("-->");
                                i4 = intN7 + i13;
                                break;
                            case DOCUMENT_URI /*61720*/:
                                i4 = i5 + 2;
                                break;
                            default:
                                throw new Error("unknown code:" + c);
                        }
                    } else {
                        if (z) {
                            stringBuffer.append('>');
                            z = false;
                        }
                        if (z2) {
                            stringBuffer.append(str);
                        } else {
                            z2 = true;
                        }
                        stringBuffer.append(c - INT_SHORT_ZERO);
                        i4 = i5;
                    }
                }
            } else {
                return;
            }
        }
    }

    public boolean hasNext(int ipos) {
        char ch;
        int index = posToDataIndex(ipos);
        if (index == this.data.length || (ch = this.data[index]) == END_ATTRIBUTE || ch == END_ELEMENT_SHORT || ch == END_ELEMENT_LONG || ch == END_DOCUMENT) {
            return false;
        }
        return true;
    }

    public int getNextKind(int ipos) {
        return getNextKindI(posToDataIndex(ipos));
    }

    public int getNextKindI(int index) {
        if (index == this.data.length) {
            return 0;
        }
        char datum = this.data[index];
        if (datum <= 40959) {
            return 29;
        }
        if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
            return 32;
        }
        if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            return 33;
        }
        if ((65280 & datum) == BYTE_PREFIX) {
            return 28;
        }
        if (datum >= 45056 && datum <= 57343) {
            return 22;
        }
        switch (datum) {
            case 61696:
            case 61697:
                return 27;
            case INT_FOLLOWS /*61698*/:
                return 22;
            case LONG_FOLLOWS /*61699*/:
                return 24;
            case FLOAT_FOLLOWS /*61700*/:
                return 25;
            case DOUBLE_FOLLOWS /*61701*/:
                return 26;
            case CHAR_FOLLOWS /*61702*/:
            case BEGIN_DOCUMENT /*61712*/:
                return 34;
            case BEGIN_ELEMENT_LONG /*61704*/:
                return 33;
            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                return 35;
            case END_ATTRIBUTE /*61706*/:
            case END_ELEMENT_SHORT /*61707*/:
            case END_ELEMENT_LONG /*61708*/:
            case END_DOCUMENT /*61713*/:
            case END_ENTITY /*61715*/:
                return 0;
            case BEGIN_ENTITY /*61714*/:
                return getNextKind((index + 5) << 1);
            case PROCESSING_INSTRUCTION /*61716*/:
                return 37;
            case CDATA_SECTION /*61717*/:
                return 31;
            case COMMENT /*61719*/:
                return 36;
            default:
                return 32;
        }
    }

    public Object getNextTypeObject(int ipos) {
        int index;
        for (int index2 = posToDataIndex(ipos); index2 != this.data.length; index2 += 5) {
            char datum = this.data[index2];
            if (datum != 61714) {
                if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                    index = datum - BEGIN_ELEMENT_SHORT;
                } else if (datum == BEGIN_ELEMENT_LONG) {
                    int j = getIntN(index2 + 1);
                    index = getIntN(j + (j < 0 ? this.data.length : index2) + 1);
                } else if (datum == BEGIN_ATTRIBUTE_LONG) {
                    index = getIntN(index2 + 1);
                } else if (datum != PROCESSING_INSTRUCTION) {
                    return null;
                } else {
                    index = getIntN(index2 + 1);
                }
                if (index < 0) {
                    return null;
                }
                return this.objects[index];
            }
        }
        return null;
    }

    public String getNextTypeName(int ipos) {
        Object type = getNextTypeObject(ipos);
        if (type == null) {
            return null;
        }
        return type.toString();
    }

    public Object getPosPrevious(int ipos) {
        if ((ipos & 1) == 0 || ipos == -1) {
            return super.getPosPrevious(ipos);
        }
        return getPosNext(ipos - 3);
    }

    private Object copyToList(int startPosition, int endPosition) {
        return new TreeList(this, startPosition, endPosition);
    }

    public int getPosNextInt(int ipos) {
        int index = posToDataIndex(ipos);
        if (index < this.data.length) {
            char datum = this.data[index];
            if (datum >= 45056 && datum <= 57343) {
                return datum - INT_SHORT_ZERO;
            }
            if (datum == 61698) {
                return getIntN(index + 1);
            }
        }
        return ((Number) getPosNext(ipos)).intValue();
    }

    public Object getPosNext(int i) {
        int posToDataIndex = posToDataIndex(i);
        if (posToDataIndex == this.data.length) {
            return Sequence.eofValue;
        }
        char c = this.data[posToDataIndex];
        if (c <= 40959) {
            return Convert.toObject(c);
        }
        if (c >= OBJECT_REF_SHORT && c <= 61439) {
            return this.objects[c - OBJECT_REF_SHORT];
        }
        if (c >= BEGIN_ELEMENT_SHORT && c <= 45055) {
            return copyToList(posToDataIndex, this.data[posToDataIndex + 1] + posToDataIndex + 2);
        }
        if (c >= 45056 && c <= 57343) {
            return Convert.toObject(c - INT_SHORT_ZERO);
        }
        boolean z = true;
        switch (c) {
            case 61696:
            case 61697:
                if (c == 61696) {
                    z = false;
                }
                return Convert.toObject(z);
            case INT_FOLLOWS /*61698*/:
                return Convert.toObject(getIntN(posToDataIndex + 1));
            case LONG_FOLLOWS /*61699*/:
                return Convert.toObject(getLongN(posToDataIndex + 1));
            case FLOAT_FOLLOWS /*61700*/:
                return Convert.toObject(Float.intBitsToFloat(getIntN(posToDataIndex + 1)));
            case DOUBLE_FOLLOWS /*61701*/:
                return Convert.toObject(Double.longBitsToDouble(getLongN(posToDataIndex + 1)));
            case CHAR_FOLLOWS /*61702*/:
                return Convert.toObject(this.data[posToDataIndex + 1]);
            case BEGIN_ELEMENT_LONG /*61704*/:
                int intN = getIntN(posToDataIndex + 1);
                return copyToList(posToDataIndex, intN + (intN < 0 ? this.data.length : posToDataIndex) + 7);
            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                int intN2 = getIntN(posToDataIndex + 3);
                return copyToList(posToDataIndex, intN2 + (intN2 < 0 ? this.data.length : posToDataIndex) + 1);
            case END_ATTRIBUTE /*61706*/:
            case END_ELEMENT_SHORT /*61707*/:
            case END_ELEMENT_LONG /*61708*/:
            case END_DOCUMENT /*61713*/:
                return Sequence.eofValue;
            case 61709:
            case 61710:
                return this.objects[getIntN(posToDataIndex + 1)];
            case 61711:
                return ((AbstractSequence) this.objects[getIntN(posToDataIndex + 1)]).getIteratorAtPos(getIntN(posToDataIndex + 3));
            case BEGIN_DOCUMENT /*61712*/:
                int intN3 = getIntN(posToDataIndex + 1);
                return copyToList(posToDataIndex, intN3 + (intN3 < 0 ? this.data.length : posToDataIndex) + 1);
            case JOINER /*61718*/:
                return "";
            default:
                throw unsupported("getPosNext, code=" + Integer.toHexString(c));
        }
    }

    public void stringValue(int startIndex, int endIndex, StringBuffer sbuf) {
        int index = startIndex;
        while (index < endIndex && index >= 0) {
            index = stringValue(false, index, sbuf);
        }
    }

    public int stringValue(int index, StringBuffer sbuf) {
        int next = nextNodeIndex(index, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (next <= index) {
            return stringValue(false, index, sbuf);
        }
        stringValue(index, next, sbuf);
        return index;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a0, code lost:
        r1 = getIntN(r9);
        r9 = r9 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a6, code lost:
        if (r8 == false) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ab, code lost:
        if (r0 != CDATA_SECTION) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ad, code lost:
        r10.append(r7.data, r9, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b3, code lost:
        return r9 + r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int stringValue(boolean r8, int r9, java.lang.StringBuffer r10) {
        /*
            r7 = this;
            int r0 = r7.gapStart
            if (r9 < r0) goto L_0x000c
            int r0 = r7.gapEnd
            int r1 = r7.gapStart
            int r0 = r0 - r1
            int r9 = r9 + r0
        L_0x000c:
            char[] r0 = r7.data
            int r0 = r0.length
            r1 = -1
            if (r9 != r0) goto L_0x0013
            return r1
        L_0x0013:
            char[] r0 = r7.data
            char r0 = r0[r9]
            r2 = 1
            int r9 = r9 + r2
            r3 = 40959(0x9fff, float:5.7396E-41)
            if (r0 > r3) goto L_0x0023
            r10.append(r0)
            return r9
        L_0x0023:
            r3 = 57344(0xe000, float:8.0356E-41)
            r4 = 0
            if (r0 < r3) goto L_0x0036
            r5 = 61439(0xefff, float:8.6094E-41)
            if (r0 > r5) goto L_0x0036
            java.lang.Object[] r8 = r7.objects
            int r0 = r0 - r3
            r8 = r8[r0]
            goto L_0x00ff
        L_0x0036:
            r3 = 40960(0xa000, float:5.7397E-41)
            r5 = 0
            if (r0 < r3) goto L_0x004d
            r3 = 45055(0xafff, float:6.3136E-41)
            if (r0 > r3) goto L_0x004d
            int r4 = r9 + 2
            char[] r8 = r7.data
            char r8 = r8[r9]
            int r8 = r8 + r9
            int r9 = r8 + 1
            r8 = r5
            goto L_0x00ff
        L_0x004d:
            r3 = 65280(0xff00, float:9.1477E-41)
            r3 = r3 & r0
            r6 = 61440(0xf000, float:8.6096E-41)
            if (r3 != r6) goto L_0x005c
            r8 = r0 & 255(0xff, float:3.57E-43)
            r10.append(r8)
            return r9
        L_0x005c:
            r3 = 45056(0xb000, float:6.3137E-41)
            if (r0 < r3) goto L_0x006e
            r3 = 57343(0xdfff, float:8.0355E-41)
            if (r0 > r3) goto L_0x006e
            r8 = 49152(0xc000, float:6.8877E-41)
            int r0 = r0 - r8
            r10.append(r0)
            return r9
        L_0x006e:
            switch(r0) {
                case 61696: goto L_0x014f;
                case 61697: goto L_0x014f;
                case 61698: goto L_0x0143;
                case 61699: goto L_0x0137;
                case 61700: goto L_0x0127;
                case 61701: goto L_0x0117;
                case 61702: goto L_0x010d;
                case 61703: goto L_0x0071;
                case 61704: goto L_0x00ec;
                case 61705: goto L_0x00d9;
                case 61706: goto L_0x00d8;
                case 61707: goto L_0x00d8;
                case 61708: goto L_0x00d8;
                case 61709: goto L_0x0071;
                case 61710: goto L_0x0071;
                case 61711: goto L_0x00be;
                case 61712: goto L_0x00b4;
                case 61713: goto L_0x00d8;
                case 61714: goto L_0x00b4;
                case 61715: goto L_0x00d8;
                case 61716: goto L_0x009e;
                case 61717: goto L_0x00a0;
                case 61718: goto L_0x009b;
                case 61719: goto L_0x00a0;
                case 61720: goto L_0x0098;
                default: goto L_0x0071;
            }
        L_0x0071:
            java.lang.Error r8 = new java.lang.Error
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r1 = "unimplemented: "
            java.lang.StringBuilder r10 = r10.append(r1)
            java.lang.String r0 = java.lang.Integer.toHexString(r0)
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.String r0 = " at:"
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.StringBuilder r9 = r10.append(r9)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0098:
            int r9 = r9 + 2
            return r9
        L_0x009b:
            r8 = r5
            goto L_0x00ff
        L_0x009e:
            int r9 = r9 + 2
        L_0x00a0:
            int r1 = r7.getIntN(r9)
            int r9 = r9 + 2
            if (r8 == 0) goto L_0x00ad
            r8 = 61717(0xf115, float:8.6484E-41)
            if (r0 != r8) goto L_0x00b2
        L_0x00ad:
            char[] r8 = r7.data
            r10.append(r8, r9, r1)
        L_0x00b2:
            int r9 = r9 + r1
            return r9
        L_0x00b4:
            int r4 = r9 + 4
            int r9 = r9 + -1
            int r9 = r7.nextDataIndex(r9)
            r8 = r5
            goto L_0x00ff
        L_0x00be:
            java.lang.Object[] r0 = r7.objects
            int r1 = r7.getIntN(r9)
            r0 = r0[r1]
            gnu.lists.AbstractSequence r0 = (gnu.lists.AbstractSequence) r0
            int r1 = r9 + 2
            int r1 = r7.getIntN(r1)
            gnu.lists.TreeList r0 = (gnu.lists.TreeList) r0
            int r1 = r1 >> r2
            r0.stringValue((boolean) r8, (int) r1, (java.lang.StringBuffer) r10)
            int r9 = r9 + 4
            r8 = r5
            goto L_0x00ff
        L_0x00d8:
            return r1
        L_0x00d9:
            if (r8 != 0) goto L_0x00dd
            int r4 = r9 + 4
        L_0x00dd:
            int r8 = r9 + 2
            int r8 = r7.getIntN(r8)
            if (r8 >= 0) goto L_0x00e9
            char[] r9 = r7.data
            int r9 = r9.length
            int r9 = r9 + r2
        L_0x00e9:
            int r9 = r9 + r8
            r8 = r5
            goto L_0x00ff
        L_0x00ec:
            int r4 = r9 + 2
            int r8 = r7.getIntN(r9)
            if (r8 >= 0) goto L_0x00f9
            char[] r9 = r7.data
            int r9 = r9.length
            goto L_0x00fb
        L_0x00f9:
            int r9 = r9 + -1
        L_0x00fb:
            int r8 = r8 + r9
            int r9 = r8 + 7
            r8 = r5
        L_0x00ff:
            if (r8 == 0) goto L_0x0104
            r10.append(r8)
        L_0x0104:
            if (r4 <= 0) goto L_0x010c
        L_0x0106:
            int r4 = r7.stringValue((boolean) r2, (int) r4, (java.lang.StringBuffer) r10)
            if (r4 >= 0) goto L_0x0106
        L_0x010c:
            return r9
        L_0x010d:
            char[] r8 = r7.data
            char r8 = r8[r9]
            r10.append(r8)
            int r9 = r9 + r2
            return r9
        L_0x0117:
            long r0 = r7.getLongN(r9)
            double r0 = java.lang.Double.longBitsToDouble(r0)
            r10.append(r0)
            int r9 = r9 + 4
            return r9
        L_0x0127:
            int r8 = r7.getIntN(r9)
            float r8 = java.lang.Float.intBitsToFloat(r8)
            r10.append(r8)
            int r9 = r9 + 2
            return r9
        L_0x0137:
            long r0 = r7.getLongN(r9)
            r10.append(r0)
            int r9 = r9 + 4
            return r9
        L_0x0143:
            int r8 = r7.getIntN(r9)
            r10.append(r8)
            int r9 = r9 + 2
            return r9
        L_0x014f:
            r8 = 61696(0xf100, float:8.6455E-41)
            if (r0 == r8) goto L_0x0156
            goto L_0x0157
        L_0x0156:
            r2 = 0
        L_0x0157:
            r10.append(r2)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.TreeList.stringValue(boolean, int, java.lang.StringBuffer):int");
    }

    public int createRelativePos(int istart, int offset, boolean isAfter) {
        if (isAfter) {
            if (offset == 0) {
                if ((istart & 1) != 0) {
                    return istart;
                }
                if (istart == 0) {
                    return 1;
                }
            }
            offset--;
        }
        if (offset >= 0) {
            int pos = posToDataIndex(istart);
            do {
                offset--;
                if (offset >= 0) {
                    pos = nextDataIndex(pos);
                } else {
                    if (pos >= this.gapEnd) {
                        pos -= this.gapEnd - this.gapStart;
                    }
                    return isAfter ? 1 | ((pos + 1) << 1) : pos << 1;
                }
            } while (pos >= 0);
            throw new IndexOutOfBoundsException();
        }
        throw unsupported("backwards createRelativePos");
    }

    public final int nextNodeIndex(int pos, int limit) {
        if ((Integer.MIN_VALUE | limit) == -1) {
            limit = this.data.length;
        }
        while (true) {
            if (pos == this.gapStart) {
                pos = this.gapEnd;
            }
            if (pos >= limit) {
                return pos;
            }
            char datum = this.data[pos];
            if (datum <= 40959 || ((datum >= OBJECT_REF_SHORT && datum <= 61439) || ((datum >= 45056 && datum <= 57343) || (65280 & datum) == BYTE_PREFIX))) {
                pos++;
            } else if (datum < BEGIN_ELEMENT_SHORT || datum > 45055) {
                switch (datum) {
                    case BEGIN_ELEMENT_LONG /*61704*/:
                    case BEGIN_ATTRIBUTE_LONG /*61705*/:
                    case BEGIN_DOCUMENT /*61712*/:
                    case PROCESSING_INSTRUCTION /*61716*/:
                    case COMMENT /*61719*/:
                        return pos;
                    case END_ATTRIBUTE /*61706*/:
                    case END_ELEMENT_SHORT /*61707*/:
                    case END_ELEMENT_LONG /*61708*/:
                    case END_DOCUMENT /*61713*/:
                    case END_ENTITY /*61715*/:
                        return pos;
                    case BEGIN_ENTITY /*61714*/:
                        pos += 5;
                        break;
                    case JOINER /*61718*/:
                        pos++;
                        break;
                    case DOCUMENT_URI /*61720*/:
                        pos += 3;
                        break;
                    default:
                        pos = nextDataIndex(pos);
                        break;
                }
            } else {
                return pos;
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ba, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x011a A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0127  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int nextMatching(int r8, gnu.lists.ItemPredicate r9, int r10, boolean r11) {
        /*
            r7 = this;
            int r8 = r7.posToDataIndex(r8)
            int r10 = r7.posToDataIndex(r10)
            boolean r0 = r9 instanceof gnu.lists.NodePredicate
            if (r0 == 0) goto L_0x0012
            int r0 = r7.nextNodeIndex(r8, r10)
            goto L_0x0013
        L_0x0012:
            r0 = r8
        L_0x0013:
            boolean r1 = r9 instanceof gnu.lists.ElementPredicate
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x001f
            r1 = 0
            r4 = 1
            goto L_0x002c
        L_0x001f:
            boolean r1 = r9 instanceof gnu.lists.AttributePredicate
            if (r1 == 0) goto L_0x0028
            r1 = 0
            r4 = 0
            goto L_0x002c
        L_0x0028:
            r1 = 1
            r4 = 1
        L_0x002c:
            int r5 = r7.gapStart
            if (r0 != r5) goto L_0x0032
            int r0 = r7.gapEnd
        L_0x0032:
            if (r0 < r10) goto L_0x0038
            r5 = -1
            if (r10 == r5) goto L_0x0038
            return r2
        L_0x0038:
            char[] r5 = r7.data
            char r5 = r5[r0]
            r6 = 40959(0x9fff, float:5.7396E-41)
            if (r5 <= r6) goto L_0x0144
            r6 = 57344(0xe000, float:8.0356E-41)
            if (r5 < r6) goto L_0x004b
            r6 = 61439(0xefff, float:8.6094E-41)
            if (r5 <= r6) goto L_0x0144
        L_0x004b:
            r6 = 45056(0xb000, float:6.3137E-41)
            if (r5 < r6) goto L_0x0057
            r6 = 57343(0xdfff, float:8.0355E-41)
            if (r5 > r6) goto L_0x0057
            goto L_0x0144
        L_0x0057:
            switch(r5) {
                case 61696: goto L_0x00fe;
                case 61697: goto L_0x00fe;
                case 61698: goto L_0x00f7;
                case 61699: goto L_0x00ef;
                case 61700: goto L_0x00f7;
                case 61701: goto L_0x00ef;
                case 61702: goto L_0x00eb;
                case 61703: goto L_0x005a;
                case 61704: goto L_0x00d0;
                case 61705: goto L_0x00be;
                case 61706: goto L_0x00b7;
                case 61707: goto L_0x00b0;
                case 61708: goto L_0x00a9;
                case 61709: goto L_0x00f7;
                case 61710: goto L_0x00f7;
                case 61711: goto L_0x00a0;
                case 61712: goto L_0x009c;
                case 61713: goto L_0x00b7;
                case 61714: goto L_0x0098;
                case 61715: goto L_0x00ba;
                case 61716: goto L_0x008d;
                case 61717: goto L_0x007d;
                case 61718: goto L_0x0079;
                case 61719: goto L_0x006e;
                case 61720: goto L_0x006a;
                default: goto L_0x005a;
            }
        L_0x005a:
            r6 = 40960(0xa000, float:5.7397E-41)
            if (r5 < r6) goto L_0x012b
            r6 = 45055(0xafff, float:6.3136E-41)
            if (r5 > r6) goto L_0x012b
            if (r11 == 0) goto L_0x0105
            int r5 = r0 + 3
            goto L_0x010e
        L_0x006a:
            int r0 = r0 + 3
            goto L_0x015e
        L_0x006e:
            int r5 = r0 + 3
            int r6 = r0 + 1
            int r6 = r7.getIntN(r6)
            int r5 = r5 + r6
            goto L_0x0110
        L_0x0079:
            int r0 = r0 + 1
            goto L_0x015e
        L_0x007d:
            int r5 = r0 + 3
            int r6 = r0 + 1
            int r6 = r7.getIntN(r6)
            int r5 = r5 + r6
            if (r1 == 0) goto L_0x008a
            goto L_0x0110
        L_0x008a:
            r0 = r5
            goto L_0x015e
        L_0x008d:
            int r5 = r0 + 5
            int r6 = r0 + 3
            int r6 = r7.getIntN(r6)
            int r5 = r5 + r6
            goto L_0x0110
        L_0x0098:
            int r0 = r0 + 5
            goto L_0x015e
        L_0x009c:
            int r5 = r0 + 5
            goto L_0x0110
        L_0x00a0:
            int r5 = r0 + 5
            if (r1 == 0) goto L_0x00a6
            goto L_0x0110
        L_0x00a6:
            r0 = r5
            goto L_0x015e
        L_0x00a9:
            if (r11 != 0) goto L_0x00ac
            return r2
        L_0x00ac:
            int r0 = r0 + 7
            goto L_0x015e
        L_0x00b0:
            if (r11 != 0) goto L_0x00b3
            return r2
        L_0x00b3:
            int r0 = r0 + 2
            goto L_0x015e
        L_0x00b7:
            if (r11 != 0) goto L_0x00ba
            return r2
        L_0x00ba:
            int r0 = r0 + 1
            goto L_0x015e
        L_0x00be:
            int r5 = r0 + 3
            int r5 = r7.getIntN(r5)
            int r6 = r5 + 1
            if (r5 >= 0) goto L_0x00cc
            char[] r0 = r7.data
            int r0 = r0.length
        L_0x00cc:
            int r6 = r6 + r0
            r0 = r6
            goto L_0x015e
        L_0x00d0:
            if (r11 == 0) goto L_0x00d5
            int r5 = r0 + 3
            goto L_0x00e5
        L_0x00d5:
            int r5 = r0 + 1
            int r5 = r7.getIntN(r5)
            if (r5 >= 0) goto L_0x00e1
            char[] r6 = r7.data
            int r6 = r6.length
            goto L_0x00e2
        L_0x00e1:
            r6 = r0
        L_0x00e2:
            int r5 = r5 + r6
            int r5 = r5 + 7
        L_0x00e5:
            if (r4 == 0) goto L_0x00e8
            goto L_0x0110
        L_0x00e8:
            r0 = r5
            goto L_0x015e
        L_0x00eb:
            int r0 = r0 + 2
            goto L_0x015e
        L_0x00ef:
            int r5 = r0 + 5
            if (r1 == 0) goto L_0x00f4
            goto L_0x0110
        L_0x00f4:
            r0 = r5
            goto L_0x015e
        L_0x00f7:
            int r5 = r0 + 3
            if (r1 == 0) goto L_0x00fc
            goto L_0x0110
        L_0x00fc:
            r0 = r5
            goto L_0x015e
        L_0x00fe:
            int r5 = r0 + 1
            if (r1 == 0) goto L_0x0103
            goto L_0x0110
        L_0x0103:
            r0 = r5
            goto L_0x015e
        L_0x0105:
            char[] r5 = r7.data
            int r6 = r0 + 1
            char r5 = r5[r6]
            int r5 = r5 + r0
            int r5 = r5 + 2
        L_0x010e:
            if (r4 == 0) goto L_0x0129
        L_0x0110:
            if (r0 <= r8) goto L_0x0127
            int r6 = r0 << 1
            boolean r6 = r9.isInstancePos(r7, r6)
            if (r6 == 0) goto L_0x0127
            int r8 = r7.gapEnd
            if (r0 < r8) goto L_0x0124
            int r8 = r7.gapEnd
            int r9 = r7.gapStart
            int r8 = r8 - r9
            int r0 = r0 - r8
        L_0x0124:
            int r8 = r0 << 1
            return r8
        L_0x0127:
            r0 = r5
            goto L_0x015e
        L_0x0129:
            r0 = r5
            goto L_0x015e
        L_0x012b:
            java.lang.Error r8 = new java.lang.Error
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "unknown code:"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r5)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0144:
            if (r1 == 0) goto L_0x015b
            int r5 = r0 << 1
            boolean r5 = r9.isInstancePos(r7, r5)
            if (r5 == 0) goto L_0x015b
            int r8 = r7.gapEnd
            if (r0 < r8) goto L_0x0158
            int r8 = r7.gapEnd
            int r9 = r7.gapStart
            int r8 = r8 - r9
            int r0 = r0 - r8
        L_0x0158:
            int r8 = r0 << 1
            return r8
        L_0x015b:
            int r0 = r0 + 1
        L_0x015e:
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.TreeList.nextMatching(int, gnu.lists.ItemPredicate, int, boolean):int");
    }

    public int nextPos(int position) {
        int index = posToDataIndex(position);
        if (index == this.data.length) {
            return 0;
        }
        if (index >= this.gapEnd) {
            index -= this.gapEnd - this.gapStart;
        }
        return (index << 1) + 3;
    }

    public final int nextDataIndex(int pos) {
        if (pos == this.gapStart) {
            pos = this.gapEnd;
        }
        if (pos == this.data.length) {
            return -1;
        }
        int pos2 = pos + 1;
        char datum = this.data[pos];
        if (datum <= 40959 || ((datum >= OBJECT_REF_SHORT && datum <= 61439) || (datum >= 45056 && datum <= 57343))) {
            return pos2;
        }
        if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            return this.data[pos2] + pos2 + 1;
        }
        switch (datum) {
            case 61696:
            case 61697:
            case JOINER /*61718*/:
                return pos2;
            case INT_FOLLOWS /*61698*/:
            case FLOAT_FOLLOWS /*61700*/:
            case 61709:
            case 61710:
                return pos2 + 2;
            case LONG_FOLLOWS /*61699*/:
            case DOUBLE_FOLLOWS /*61701*/:
                return pos2 + 4;
            case CHAR_FOLLOWS /*61702*/:
                return pos2 + 1;
            case BEGIN_ELEMENT_LONG /*61704*/:
                int j = getIntN(pos2);
                return j + (j < 0 ? this.data.length : pos2 - 1) + 7;
            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                int j2 = getIntN(pos2 + 2);
                return j2 + (j2 < 0 ? this.data.length : pos2 - 1) + 1;
            case END_ATTRIBUTE /*61706*/:
            case END_ELEMENT_SHORT /*61707*/:
            case END_ELEMENT_LONG /*61708*/:
            case END_DOCUMENT /*61713*/:
            case END_ENTITY /*61715*/:
                return -1;
            case 61711:
                return pos2 + 4;
            case BEGIN_DOCUMENT /*61712*/:
                int j3 = getIntN(pos2);
                return j3 + (j3 < 0 ? this.data.length : pos2 - 1) + 1;
            case BEGIN_ENTITY /*61714*/:
                int j4 = pos2 + 4;
                while (true) {
                    if (j4 == this.gapStart) {
                        j4 = this.gapEnd;
                    }
                    if (j4 == this.data.length) {
                        return -1;
                    }
                    if (this.data[j4] == END_ENTITY) {
                        return j4 + 1;
                    }
                    j4 = nextDataIndex(j4);
                }
            case PROCESSING_INSTRUCTION /*61716*/:
                pos2 += 2;
                break;
            case CDATA_SECTION /*61717*/:
            case COMMENT /*61719*/:
                break;
            default:
                throw new Error("unknown code:" + Integer.toHexString(datum));
        }
        return pos2 + 2 + getIntN(pos2);
    }

    public Object documentUriOfPos(int pos) {
        int index = posToDataIndex(pos);
        if (index != this.data.length && this.data[index] == BEGIN_DOCUMENT) {
            int next = index + 5;
            if (next == this.gapStart) {
                next = this.gapEnd;
            }
            if (next < this.data.length && this.data[next] == DOCUMENT_URI) {
                return this.objects[getIntN(next + 1)];
            }
        }
        return null;
    }

    public int compare(int ipos1, int ipos2) {
        int i1 = posToDataIndex(ipos1);
        int i2 = posToDataIndex(ipos2);
        if (i1 < i2) {
            return -1;
        }
        return i1 > i2 ? 1 : 0;
    }

    /* access modifiers changed from: protected */
    public int getIndexDifference(int ipos1, int ipos0) {
        int i0 = posToDataIndex(ipos0);
        int i1 = posToDataIndex(ipos1);
        boolean negate = false;
        if (i0 > i1) {
            negate = true;
            int i = i1;
            i1 = i0;
            i0 = i;
        }
        int i2 = 0;
        while (i0 < i1) {
            i0 = nextDataIndex(i0);
            i2++;
        }
        return negate ? -i2 : i2;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public void consume(Consumer out) {
        consumeIRange(0, this.data.length, out);
    }

    public void statistics() {
        PrintWriter out = new PrintWriter(System.out);
        statistics(out);
        out.flush();
    }

    public void statistics(PrintWriter out) {
        out.print("data array length: ");
        out.println(this.data.length);
        out.print("data array gap: ");
        out.println(this.gapEnd - this.gapStart);
        out.print("object array length: ");
        out.println(this.objects.length);
    }

    public void dump() {
        PrintWriter out = new PrintWriter(System.out);
        dump(out);
        out.flush();
    }

    public void dump(PrintWriter out) {
        out.println(getClass().getName() + " @" + Integer.toHexString(System.identityHashCode(this)) + " gapStart:" + this.gapStart + " gapEnd:" + this.gapEnd + " length:" + this.data.length);
        dump(out, 0, this.data.length);
    }

    public void dump(PrintWriter printWriter, int i, int i2) {
        PrintWriter printWriter2 = printWriter;
        int i3 = i;
        int i4 = 0;
        while (i3 < i2) {
            if (i3 < this.gapStart || i3 >= this.gapEnd) {
                char c = this.data[i3];
                printWriter2.print("" + i3 + ": 0x" + Integer.toHexString(c) + '=' + ((short) c));
                i4--;
                if (i4 < 0) {
                    if (c <= 40959) {
                        if (c >= ' ' && c < 127) {
                            printWriter2.print("='" + ((char) c) + "'");
                        } else if (c == 10) {
                            printWriter2.print("='\\n'");
                        } else {
                            printWriter2.print("='\\u" + Integer.toHexString(c) + "'");
                        }
                    } else if (c >= OBJECT_REF_SHORT && c <= 61439) {
                        int i5 = c - OBJECT_REF_SHORT;
                        Object obj = this.objects[i5];
                        printWriter2.print("=Object#" + i5 + '=' + obj + ':' + obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj)));
                    } else if (c >= BEGIN_ELEMENT_SHORT && c <= 45055) {
                        int i6 = c - BEGIN_ELEMENT_SHORT;
                        printWriter2.print("=BEGIN_ELEMENT_SHORT end:" + (this.data[i3 + 1] + i3) + " index#" + i6 + "=<" + this.objects[i6] + '>');
                        i4 = 2;
                    } else if (c < 45056 || c > 57343) {
                        switch (c) {
                            case 61696:
                                printWriter2.print("= false");
                                break;
                            case 61697:
                                printWriter2.print("= true");
                                break;
                            case INT_FOLLOWS /*61698*/:
                                printWriter2.print("=INT_FOLLOWS value:" + getIntN(i3 + 1));
                                i4 = 2;
                                break;
                            case LONG_FOLLOWS /*61699*/:
                                printWriter2.print("=LONG_FOLLOWS value:" + getLongN(i3 + 1));
                                i4 = 4;
                                break;
                            case FLOAT_FOLLOWS /*61700*/:
                                printWriter2.write("=FLOAT_FOLLOWS value:" + Float.intBitsToFloat(getIntN(i3 + 1)));
                                i4 = 2;
                                break;
                            case DOUBLE_FOLLOWS /*61701*/:
                                printWriter2.print("=DOUBLE_FOLLOWS value:" + Double.longBitsToDouble(getLongN(i3 + 1)));
                                i4 = 4;
                                break;
                            case CHAR_FOLLOWS /*61702*/:
                                printWriter2.print("=CHAR_FOLLOWS");
                                i4 = 1;
                                break;
                            case BEGIN_ELEMENT_LONG /*61704*/:
                                int intN = getIntN(i3 + 1);
                                int length = intN + (intN < 0 ? this.data.length : i3);
                                printWriter2.print("=BEGIN_ELEMENT_LONG end:");
                                printWriter2.print(length);
                                int intN2 = getIntN(length + 1);
                                printWriter2.print(" -> #");
                                printWriter2.print(intN2);
                                if (intN2 < 0 || intN2 + 1 >= this.objects.length) {
                                    printWriter2.print("=<out-of-bounds>");
                                } else {
                                    printWriter2.print("=<" + this.objects[intN2] + '>');
                                }
                                i4 = 2;
                                break;
                            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                                int intN3 = getIntN(i3 + 1);
                                printWriter2.print("=BEGIN_ATTRIBUTE name:" + intN3 + "=" + this.objects[intN3]);
                                int intN4 = getIntN(i3 + 3);
                                printWriter2.print(" end:" + (intN4 + (intN4 < 0 ? this.data.length : i3)));
                                i4 = 4;
                                break;
                            case END_ATTRIBUTE /*61706*/:
                                printWriter2.print("=END_ATTRIBUTE");
                                break;
                            case END_ELEMENT_SHORT /*61707*/:
                                printWriter2.print("=END_ELEMENT_SHORT begin:");
                                int i7 = i3 - this.data[i3 + 1];
                                printWriter2.print(i7);
                                int i8 = this.data[i7] - BEGIN_ELEMENT_SHORT;
                                printWriter2.print(" -> #");
                                printWriter2.print(i8);
                                printWriter2.print("=<");
                                printWriter2.print(this.objects[i8]);
                                printWriter2.print('>');
                                i4 = 1;
                                break;
                            case END_ELEMENT_LONG /*61708*/:
                                int intN5 = getIntN(i3 + 1);
                                printWriter2.print("=END_ELEMENT_LONG name:" + intN5 + "=<" + this.objects[intN5] + '>');
                                int intN6 = getIntN(i3 + 3);
                                if (intN6 < 0) {
                                    intN6 += i3;
                                }
                                printWriter2.print(" begin:" + intN6);
                                int intN7 = getIntN(i3 + 5);
                                if (intN7 < 0) {
                                    intN7 += i3;
                                }
                                printWriter2.print(" parent:" + intN7);
                                i4 = 6;
                                break;
                            case 61709:
                            case 61710:
                                i4 = 2;
                                break;
                            case 61711:
                                printWriter2.print("=POSITION_PAIR_FOLLOWS seq:");
                                int intN8 = getIntN(i3 + 1);
                                printWriter2.print(intN8);
                                printWriter2.print('=');
                                Object obj2 = this.objects[intN8];
                                printWriter2.print(obj2 == null ? null : obj2.getClass().getName());
                                printWriter2.print('@');
                                if (obj2 == null) {
                                    printWriter2.print("null");
                                } else {
                                    printWriter2.print(Integer.toHexString(System.identityHashCode(obj2)));
                                }
                                printWriter2.print(" ipos:");
                                printWriter2.print(getIntN(i3 + 3));
                                i4 = 4;
                                break;
                            case BEGIN_DOCUMENT /*61712*/:
                                int intN9 = getIntN(i3 + 1);
                                int length2 = intN9 < 0 ? this.data.length : i3;
                                printWriter2.print("=BEGIN_DOCUMENT end:");
                                printWriter2.print(intN9 + length2);
                                printWriter2.print(" parent:");
                                printWriter2.print(getIntN(i3 + 3));
                                i4 = 4;
                                break;
                            case END_DOCUMENT /*61713*/:
                                printWriter2.print("=END_DOCUMENT");
                                break;
                            case BEGIN_ENTITY /*61714*/:
                                int intN10 = getIntN(i3 + 1);
                                printWriter2.print("=BEGIN_ENTITY base:");
                                printWriter2.print(intN10);
                                printWriter2.print(" parent:");
                                printWriter2.print(getIntN(i3 + 3));
                                i4 = 4;
                                break;
                            case END_ENTITY /*61715*/:
                                printWriter2.print("=END_ENTITY");
                                break;
                            case PROCESSING_INSTRUCTION /*61716*/:
                                printWriter2.print("=PROCESSING_INSTRUCTION: ");
                                printWriter2.print(this.objects[getIntN(i3 + 1)]);
                                printWriter2.print(" '");
                                int intN11 = getIntN(i3 + 3);
                                printWriter2.write(this.data, i3 + 5, intN11);
                                printWriter2.print('\'');
                                i4 = intN11 + 4;
                                break;
                            case CDATA_SECTION /*61717*/:
                                printWriter2.print("=CDATA: '");
                                int intN12 = getIntN(i3 + 1);
                                printWriter2.write(this.data, i3 + 3, intN12);
                                printWriter2.print('\'');
                                i4 = intN12 + 2;
                                break;
                            case JOINER /*61718*/:
                                printWriter2.print("= joiner");
                                break;
                            case COMMENT /*61719*/:
                                printWriter2.print("=COMMENT: '");
                                int intN13 = getIntN(i3 + 1);
                                printWriter2.write(this.data, i3 + 3, intN13);
                                printWriter2.print('\'');
                                i4 = intN13 + 2;
                                break;
                            case DOCUMENT_URI /*61720*/:
                                printWriter2.print("=DOCUMENT_URI: ");
                                printWriter2.print(this.objects[getIntN(i3 + 1)]);
                                i4 = 2;
                                break;
                        }
                    } else {
                        printWriter2.print("= INT_SHORT:" + (c - INT_SHORT_ZERO));
                    }
                }
                printWriter.println();
                if (i4 > 0) {
                    i3 += i4;
                    i4 = 0;
                }
            }
            i3++;
        }
    }
}
