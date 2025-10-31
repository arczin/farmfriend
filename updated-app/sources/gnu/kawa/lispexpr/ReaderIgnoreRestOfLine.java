package gnu.kawa.lispexpr;

public class ReaderIgnoreRestOfLine extends ReadTableEntry {
    static ReaderIgnoreRestOfLine instance = new ReaderIgnoreRestOfLine();

    public static ReaderIgnoreRestOfLine getInstance() {
        return instance;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0007 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x000a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object read(gnu.text.Lexer r2, int r3, int r4) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r1 = this;
        L_0x0001:
            int r3 = r2.read()
            if (r3 >= 0) goto L_0x000a
            java.lang.Object r0 = gnu.lists.Sequence.eofValue
            return r0
        L_0x000a:
            r0 = 10
            if (r3 == r0) goto L_0x0012
            r0 = 13
            if (r3 != r0) goto L_0x0001
        L_0x0012:
            gnu.mapping.Values r0 = gnu.mapping.Values.empty
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderIgnoreRestOfLine.read(gnu.text.Lexer, int, int):java.lang.Object");
    }
}
