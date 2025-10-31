package gnu.xquery.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.CollationKey;
import java.text.Collator;

public class NamedCollator extends Collator implements Externalizable {
    public static final String UNICODE_CODEPOINT_COLLATION = "http://www.w3.org/2005/xpath-functions/collation/codepoint";
    public static final NamedCollator codepointCollation = new NamedCollator();
    Collator collator;
    String name;

    public static NamedCollator make(String name2) {
        NamedCollator coll = new NamedCollator();
        coll.name = name2;
        coll.resolve();
        return coll;
    }

    public String getName() {
        return this.name;
    }

    public static NamedCollator find(String name2) {
        return make(name2);
    }

    static {
        codepointCollation.name = UNICODE_CODEPOINT_COLLATION;
    }

    public void resolve() {
        if (this.name != null && !this.name.equals(UNICODE_CODEPOINT_COLLATION)) {
            throw new RuntimeException("unknown collation: " + this.name);
        }
    }

    public static int codepointCompare(String str1, String str2) {
        int i1;
        int c1 = 0;
        int c2 = 0;
        int len1 = str1.length();
        int len2 = str2.length();
        while (c1 != len1) {
            if (c2 == len2) {
                return 1;
            }
            int i12 = c1 + 1;
            int i13 = str1.charAt(c1);
            if (i13 < 55296 || i13 >= 56320 || i12 >= len1) {
                int i = i12;
                i1 = i13;
                c1 = i;
            } else {
                int i14 = i12 + 1;
                i1 = ((i13 - 55296) * 1024) + (str1.charAt(i12) - 56320) + 65536;
                c1 = i14;
            }
            int i2 = c2 + 1;
            int c22 = str2.charAt(c2);
            if (c22 >= 55296 && c22 < 56320 && i2 < len2) {
                c22 = ((c22 - 55296) * 1024) + (str2.charAt(i2) - 56320) + 65536;
                i2++;
            }
            if (i1 == c22) {
                c2 = i2;
            } else if (i1 < c22) {
                return -1;
            } else {
                return 1;
            }
        }
        if (c2 == len2) {
            return 0;
        }
        return -1;
    }

    public int compare(String str1, String str2) {
        if (this.collator != null) {
            return this.collator.compare(str1, str2);
        }
        return codepointCompare(str1, str2);
    }

    public CollationKey getCollationKey(String source) {
        return this.collator.getCollationKey(source);
    }

    public int hashCode() {
        if (this.collator != null) {
            return this.collator.hashCode();
        }
        return 0;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        resolve();
    }
}
