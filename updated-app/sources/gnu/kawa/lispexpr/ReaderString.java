package gnu.kawa.lispexpr;

import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderString extends ReadTableEntry {
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        return readString(in, ch, count);
    }

    /* JADX WARNING: type inference failed for: r2v16, types: [char[]] */
    /* JADX WARNING: type inference failed for: r2v17, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r2v17, types: [char] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readString(gnu.text.Lexer r8, int r9, int r10) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            int r10 = r8.tokenBufferLength
            gnu.text.LineBufferedReader r0 = r8.getPort()
            boolean r1 = r0 instanceof gnu.mapping.InPort
            if (r1 == 0) goto L_0x0016
            r2 = r0
            gnu.mapping.InPort r2 = (gnu.mapping.InPort) r2
            char r3 = r2.readState
            char r4 = (char) r9
            r2.readState = r4
            r2 = r9
            goto L_0x0018
        L_0x0016:
            r3 = 0
            r2 = r9
        L_0x0018:
            r4 = 13
            r5 = 10
            if (r2 != r4) goto L_0x0026
            int r2 = r0.read()     // Catch:{ all -> 0x008d }
            if (r2 != r5) goto L_0x003d
            goto L_0x0018
        L_0x0026:
            int r6 = r0.pos     // Catch:{ all -> 0x008d }
            int r7 = r0.limit     // Catch:{ all -> 0x008d }
            if (r6 >= r7) goto L_0x0039
            if (r2 == r5) goto L_0x0039
            char[] r2 = r0.buffer     // Catch:{ all -> 0x008d }
            int r6 = r0.pos     // Catch:{ all -> 0x008d }
            int r7 = r6 + 1
            r0.pos = r7     // Catch:{ all -> 0x008d }
            char r2 = r2[r6]     // Catch:{ all -> 0x008d }
            goto L_0x003d
        L_0x0039:
            int r2 = r0.read()     // Catch:{ all -> 0x008d }
        L_0x003d:
            if (r2 != r9) goto L_0x0057
            java.lang.String r9 = new java.lang.String     // Catch:{ all -> 0x008d }
            char[] r2 = r8.tokenBuffer     // Catch:{ all -> 0x008d }
            int r4 = r8.tokenBufferLength     // Catch:{ all -> 0x008d }
            int r4 = r4 - r10
            r9.<init>(r2, r10, r4)     // Catch:{ all -> 0x008d }
            java.lang.String r9 = r9.intern()     // Catch:{ all -> 0x008d }
            r8.tokenBufferLength = r10
            if (r1 == 0) goto L_0x0056
            gnu.mapping.InPort r0 = (gnu.mapping.InPort) r0
            r0.readState = r3
        L_0x0056:
            return r9
        L_0x0057:
            switch(r2) {
                case 13: goto L_0x0072;
                case 92: goto L_0x005b;
                default: goto L_0x005a;
            }
        L_0x005a:
            goto L_0x0082
        L_0x005b:
            boolean r2 = r8 instanceof gnu.kawa.lispexpr.LispReader     // Catch:{ all -> 0x008d }
            if (r2 == 0) goto L_0x0067
            r2 = r8
            gnu.kawa.lispexpr.LispReader r2 = (gnu.kawa.lispexpr.LispReader) r2     // Catch:{ all -> 0x008d }
            int r2 = r2.readEscape()     // Catch:{ all -> 0x008d }
            goto L_0x006b
        L_0x0067:
            int r2 = r0.read()     // Catch:{ all -> 0x008d }
        L_0x006b:
            r4 = -2
            if (r2 != r4) goto L_0x0082
            r2 = 10
            goto L_0x0018
        L_0x0072:
            boolean r6 = r0.getConvertCR()     // Catch:{ all -> 0x008d }
            if (r6 == 0) goto L_0x007b
            r4 = 10
            goto L_0x007e
        L_0x007b:
            r2 = 32
        L_0x007e:
            r8.tokenBufferAppend(r4)     // Catch:{ all -> 0x008d }
            goto L_0x0018
        L_0x0082:
            if (r2 >= 0) goto L_0x0089
            java.lang.String r4 = "unexpected EOF in string literal"
            r8.eofError(r4)     // Catch:{ all -> 0x008d }
        L_0x0089:
            r8.tokenBufferAppend(r2)     // Catch:{ all -> 0x008d }
            goto L_0x0018
        L_0x008d:
            r9 = move-exception
            r8.tokenBufferLength = r10
            if (r1 == 0) goto L_0x0096
            gnu.mapping.InPort r0 = (gnu.mapping.InPort) r0
            r0.readState = r3
        L_0x0096:
            goto L_0x0098
        L_0x0097:
            throw r9
        L_0x0098:
            goto L_0x0097
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderString.readString(gnu.text.Lexer, int, int):java.lang.String");
    }
}
