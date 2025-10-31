package gnu.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LineBufferedReader extends Reader {
    public static final int BUFFER_SIZE = 8192;
    private static final int CONVERT_CR = 1;
    private static final int DONT_KEEP_FULL_LINES = 8;
    private static final int PREV_WAS_CR = 4;
    private static final int USER_BUFFER = 2;
    public char[] buffer;
    private int flags;
    int highestPos;
    protected Reader in;
    public int limit;
    protected int lineNumber;
    private int lineStartPos;
    protected int markPos;
    Path path;
    public int pos;
    protected int readAheadLimit = 0;
    public char readState = 10;

    public void close() throws IOException {
        this.in.close();
    }

    public char getReadState() {
        return this.readState;
    }

    public void setKeepFullLines(boolean keep) {
        if (keep) {
            this.flags &= -9;
        } else {
            this.flags |= 8;
        }
    }

    public final boolean getConvertCR() {
        return (this.flags & 1) != 0;
    }

    public final void setConvertCR(boolean convertCR) {
        if (convertCR) {
            this.flags |= 1;
        } else {
            this.flags &= -2;
        }
    }

    public LineBufferedReader(InputStream in2) {
        this.in = new InputStreamReader(in2);
    }

    public LineBufferedReader(Reader in2) {
        this.in = in2;
    }

    public void lineStart(boolean revisited) throws IOException {
    }

    public int fill(int len) throws IOException {
        return this.in.read(this.buffer, this.pos, len);
    }

    private void clearMark() {
        int i = 0;
        this.readAheadLimit = 0;
        if (this.lineStartPos >= 0) {
            i = this.lineStartPos;
        }
        while (true) {
            i++;
            if (i < this.pos) {
                char ch = this.buffer[i - 1];
                if (ch == 10 || (ch == 13 && (!getConvertCR() || this.buffer[i] != 10))) {
                    this.lineNumber++;
                    this.lineStartPos = i;
                }
            } else {
                return;
            }
        }
    }

    public void setBuffer(char[] buffer2) throws IOException {
        if (buffer2 == null) {
            if (this.buffer != null) {
                char[] buffer3 = new char[this.buffer.length];
                System.arraycopy(this.buffer, 0, buffer3, 0, this.buffer.length);
                this.buffer = buffer3;
            }
            this.flags &= -3;
        } else if (this.limit - this.pos <= buffer2.length) {
            this.flags |= 2;
            reserve(buffer2, 0);
        } else {
            throw new IOException("setBuffer - too short");
        }
    }

    private void reserve(char[] buffer2, int reserve) throws IOException {
        int saveStart;
        int reserve2 = reserve + this.limit;
        if (reserve2 <= buffer2.length) {
            saveStart = 0;
        } else {
            saveStart = this.pos;
            if (this.readAheadLimit > 0 && this.markPos < this.pos) {
                if (this.pos - this.markPos > this.readAheadLimit || ((this.flags & 2) != 0 && reserve2 - this.markPos > buffer2.length)) {
                    clearMark();
                } else {
                    saveStart = this.markPos;
                }
            }
            int reserve3 = reserve2 - buffer2.length;
            if (reserve3 > saveStart || (saveStart > this.lineStartPos && (this.flags & 8) == 0)) {
                if (reserve3 <= this.lineStartPos && saveStart > this.lineStartPos) {
                    saveStart = this.lineStartPos;
                } else if ((this.flags & 2) != 0) {
                    saveStart -= (saveStart - reserve3) >> 2;
                } else {
                    if (this.lineStartPos >= 0) {
                        saveStart = this.lineStartPos;
                    }
                    buffer2 = new char[(buffer2.length * 2)];
                }
            }
            this.lineStartPos -= saveStart;
            this.limit -= saveStart;
            this.markPos -= saveStart;
            this.pos -= saveStart;
            this.highestPos -= saveStart;
        }
        if (this.limit > 0) {
            System.arraycopy(this.buffer, saveStart, buffer2, 0, this.limit);
        }
        this.buffer = buffer2;
    }

    public int read() throws IOException {
        char prev;
        if (this.pos > 0) {
            prev = this.buffer[this.pos - 1];
        } else if ((this.flags & 4) != 0) {
            prev = 13;
        } else if (this.lineStartPos >= 0) {
            prev = 10;
        } else {
            prev = 0;
        }
        if (prev == 13 || prev == 10) {
            if (this.lineStartPos < this.pos && (this.readAheadLimit == 0 || this.pos <= this.markPos)) {
                this.lineStartPos = this.pos;
                this.lineNumber++;
            }
            boolean revisited = this.pos < this.highestPos;
            if (prev != 10 || (this.pos > 1 ? this.buffer[this.pos - 2] != 13 : (this.flags & 4) == 0)) {
                lineStart(revisited);
            }
            if (!revisited) {
                this.highestPos = this.pos + 1;
            }
        }
        if (this.pos >= this.limit) {
            if (this.buffer == null) {
                this.buffer = new char[8192];
            } else if (this.limit == this.buffer.length) {
                reserve(this.buffer, 1);
            }
            if (this.pos == 0) {
                if (prev == 13) {
                    this.flags |= 4;
                } else {
                    this.flags &= -5;
                }
            }
            int readCount = fill(this.buffer.length - this.pos);
            if (readCount <= 0) {
                return -1;
            }
            this.limit += readCount;
        }
        char[] cArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        char ch = cArr[i];
        if (ch == 10) {
            if (prev == 13) {
                if (this.lineStartPos == this.pos - 1) {
                    this.lineNumber--;
                    this.lineStartPos--;
                }
                if (getConvertCR()) {
                    return read();
                }
            }
        } else if (ch != 13 || !getConvertCR()) {
            return ch;
        } else {
            return 10;
        }
        return ch;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: char} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(char[] r9, int r10, int r11) throws java.io.IOException {
        /*
            r8 = this;
            int r0 = r8.pos
            int r1 = r8.limit
            if (r0 < r1) goto L_0x0008
            r0 = 0
            goto L_0x0024
        L_0x0008:
            int r0 = r8.pos
            if (r0 <= 0) goto L_0x0015
            char[] r0 = r8.buffer
            int r1 = r8.pos
            int r1 = r1 + -1
            char r0 = r0[r1]
            goto L_0x0024
        L_0x0015:
            int r0 = r8.flags
            r0 = r0 & 4
            if (r0 != 0) goto L_0x0022
            int r0 = r8.lineStartPos
            if (r0 < 0) goto L_0x0020
            goto L_0x0022
        L_0x0020:
            r0 = 0
            goto L_0x0024
        L_0x0022:
            r0 = 10
        L_0x0024:
            r1 = r11
        L_0x0025:
            if (r1 <= 0) goto L_0x007d
            int r2 = r8.pos
            int r3 = r8.limit
            if (r2 >= r3) goto L_0x005c
            r2 = 10
            if (r0 == r2) goto L_0x005c
            r3 = 13
            if (r0 != r3) goto L_0x0036
            goto L_0x005c
        L_0x0036:
            int r4 = r8.pos
            int r5 = r8.limit
            int r6 = r5 - r4
            if (r1 >= r6) goto L_0x0040
            int r5 = r4 + r1
        L_0x0040:
            if (r4 >= r5) goto L_0x0054
            char[] r6 = r8.buffer
            char r0 = r6[r4]
            if (r0 == r2) goto L_0x0054
            if (r0 != r3) goto L_0x004b
            goto L_0x0054
        L_0x004b:
            int r6 = r10 + 1
            char r7 = (char) r0
            r9[r10] = r7
            int r4 = r4 + 1
            r10 = r6
            goto L_0x0040
        L_0x0054:
            int r2 = r8.pos
            int r2 = r4 - r2
            int r1 = r1 - r2
            r8.pos = r4
            goto L_0x0025
        L_0x005c:
            int r2 = r8.pos
            int r3 = r8.limit
            if (r2 < r3) goto L_0x0067
            if (r1 >= r11) goto L_0x0067
            int r2 = r11 - r1
            return r2
        L_0x0067:
            int r0 = r8.read()
            if (r0 >= 0) goto L_0x0074
            int r11 = r11 - r1
            if (r11 > 0) goto L_0x0072
            r2 = -1
            goto L_0x0073
        L_0x0072:
            r2 = r11
        L_0x0073:
            return r2
        L_0x0074:
            int r2 = r10 + 1
            char r3 = (char) r0
            r9[r10] = r3
            int r1 = r1 + -1
            r10 = r2
            goto L_0x0025
        L_0x007d:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.read(char[], int, int):int");
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path2) {
        this.path = path2;
    }

    public String getName() {
        if (this.path == null) {
            return null;
        }
        return this.path.toString();
    }

    public void setName(Object name) {
        setPath(Path.valueOf(name));
    }

    public int getLineNumber() {
        int lineno = this.lineNumber;
        if (this.readAheadLimit != 0) {
            return lineno + countLines(this.buffer, this.lineStartPos < 0 ? 0 : this.lineStartPos, this.pos);
        } else if (this.pos <= 0 || this.pos <= this.lineStartPos) {
            return lineno;
        } else {
            char prev = this.buffer[this.pos - 1];
            if (prev == 10 || prev == 13) {
                return lineno + 1;
            }
            return lineno;
        }
    }

    public void setLineNumber(int lineNumber2) {
        this.lineNumber += lineNumber2 - getLineNumber();
    }

    public void incrLineNumber(int lineDelta, int lineStartPos2) {
        this.lineNumber += lineDelta;
        this.lineStartPos = lineStartPos2;
    }

    public int getColumnNumber() {
        char prev;
        int i = 0;
        if (this.pos > 0 && ((prev = this.buffer[this.pos - 1]) == 10 || prev == 13)) {
            return 0;
        }
        if (this.readAheadLimit <= 0) {
            return this.pos - this.lineStartPos;
        }
        if (this.lineStartPos >= 0) {
            i = this.lineStartPos;
        }
        int start = i;
        while (i < this.pos) {
            int i2 = i + 1;
            char ch = this.buffer[i];
            if (ch == 10 || ch == 13) {
                start = i2;
            }
            i = i2;
        }
        int col = this.pos - start;
        if (this.lineStartPos < 0) {
            return col - this.lineStartPos;
        }
        return col;
    }

    public boolean markSupported() {
        return true;
    }

    public synchronized void mark(int readAheadLimit2) {
        if (this.readAheadLimit > 0) {
            clearMark();
        }
        this.readAheadLimit = readAheadLimit2;
        this.markPos = this.pos;
    }

    public void reset() throws IOException {
        if (this.readAheadLimit > 0) {
            if (this.pos > this.highestPos) {
                this.highestPos = this.pos;
            }
            this.pos = this.markPos;
            this.readAheadLimit = 0;
            return;
        }
        throw new IOException("mark invalid");
    }

    public void readLine(StringBuffer sbuf, char mode) throws IOException {
        while (read() >= 0) {
            int start = this.pos - 1;
            this.pos = start;
            while (this.pos < this.limit) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                char ch = cArr[i];
                if (ch != 13) {
                    if (ch == 10) {
                    }
                }
                sbuf.append(this.buffer, start, (this.pos - 1) - start);
                if (mode == 'P') {
                    this.pos--;
                    return;
                } else if (!getConvertCR() && ch != 10) {
                    if (mode != 'I') {
                        sbuf.append(13);
                    }
                    int ch2 = read();
                    if (ch2 == 10) {
                        if (mode != 'I') {
                            sbuf.append(10);
                            return;
                        }
                        return;
                    } else if (ch2 >= 0) {
                        unread_quick();
                        return;
                    } else {
                        return;
                    }
                } else if (mode != 'I') {
                    sbuf.append(10);
                    return;
                } else {
                    return;
                }
            }
            sbuf.append(this.buffer, start, this.pos - start);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        r1 = r7.pos - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002d, code lost:
        if (r0 == 10) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0033, code lost:
        if (getConvertCR() != false) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
        if (r7.pos < r7.limit) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003b, code lost:
        r7.pos--;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0048, code lost:
        if (r7.buffer[r7.pos] != 10) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
        r7.pos++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0059, code lost:
        return new java.lang.String(r7.buffer, r3, r1 - r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readLine() throws java.io.IOException {
        /*
            r7 = this;
            int r0 = r7.read()
            if (r0 >= 0) goto L_0x0008
            r1 = 0
            return r1
        L_0x0008:
            r1 = 13
            if (r0 == r1) goto L_0x0073
            r2 = 10
            if (r0 != r2) goto L_0x0011
            goto L_0x0073
        L_0x0011:
            int r3 = r7.pos
            int r3 = r3 + -1
        L_0x0015:
            int r4 = r7.pos
            int r5 = r7.limit
            if (r4 >= r5) goto L_0x005a
            char[] r4 = r7.buffer
            int r5 = r7.pos
            int r6 = r5 + 1
            r7.pos = r6
            char r0 = r4[r5]
            if (r0 == r1) goto L_0x0029
            if (r0 != r2) goto L_0x0015
        L_0x0029:
            int r1 = r7.pos
            int r1 = r1 + -1
            if (r0 == r2) goto L_0x0050
            boolean r4 = r7.getConvertCR()
            if (r4 != 0) goto L_0x0050
            int r4 = r7.pos
            int r5 = r7.limit
            if (r4 < r5) goto L_0x0042
            int r2 = r7.pos
            int r2 = r2 + -1
            r7.pos = r2
            goto L_0x005a
        L_0x0042:
            char[] r4 = r7.buffer
            int r5 = r7.pos
            char r4 = r4[r5]
            if (r4 != r2) goto L_0x0050
            int r2 = r7.pos
            int r2 = r2 + 1
            r7.pos = r2
        L_0x0050:
            java.lang.String r2 = new java.lang.String
            char[] r4 = r7.buffer
            int r5 = r1 - r3
            r2.<init>(r4, r3, r5)
            return r2
        L_0x005a:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r2 = 100
            r1.<init>(r2)
            char[] r2 = r7.buffer
            int r4 = r7.pos
            int r4 = r4 - r3
            r1.append(r2, r3, r4)
            r2 = 73
            r7.readLine(r1, r2)
            java.lang.String r2 = r1.toString()
            return r2
        L_0x0073:
            java.lang.String r1 = ""
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.readLine():java.lang.String");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: char} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int skip(int r8) throws java.io.IOException {
        /*
            r7 = this;
            if (r8 >= 0) goto L_0x0012
            int r0 = -r8
        L_0x0003:
            if (r0 <= 0) goto L_0x000f
            int r1 = r7.pos
            if (r1 <= 0) goto L_0x000f
            r7.unread()
            int r0 = r0 + -1
            goto L_0x0003
        L_0x000f:
            int r1 = r8 + r0
            return r1
        L_0x0012:
            r0 = r8
            int r1 = r7.pos
            int r2 = r7.limit
            if (r1 < r2) goto L_0x001b
            r1 = 0
            goto L_0x0037
        L_0x001b:
            int r1 = r7.pos
            if (r1 <= 0) goto L_0x0028
            char[] r1 = r7.buffer
            int r2 = r7.pos
            int r2 = r2 + -1
            char r1 = r1[r2]
            goto L_0x0037
        L_0x0028:
            int r1 = r7.flags
            r1 = r1 & 4
            if (r1 != 0) goto L_0x0035
            int r1 = r7.lineStartPos
            if (r1 < 0) goto L_0x0033
            goto L_0x0035
        L_0x0033:
            r1 = 0
            goto L_0x0037
        L_0x0035:
            r1 = 10
        L_0x0037:
            if (r0 <= 0) goto L_0x0074
            r2 = 10
            if (r1 == r2) goto L_0x0068
            r3 = 13
            if (r1 == r3) goto L_0x0068
            int r4 = r7.pos
            int r5 = r7.limit
            if (r4 < r5) goto L_0x0048
            goto L_0x0068
        L_0x0048:
            int r4 = r7.pos
            int r5 = r7.limit
            int r6 = r5 - r4
            if (r0 >= r6) goto L_0x0052
            int r5 = r4 + r0
        L_0x0052:
            if (r4 >= r5) goto L_0x0060
            char[] r6 = r7.buffer
            char r1 = r6[r4]
            if (r1 == r2) goto L_0x0060
            if (r1 != r3) goto L_0x005d
            goto L_0x0060
        L_0x005d:
            int r4 = r4 + 1
            goto L_0x0052
        L_0x0060:
            int r2 = r7.pos
            int r2 = r4 - r2
            int r0 = r0 - r2
            r7.pos = r4
            goto L_0x0037
        L_0x0068:
            int r1 = r7.read()
            if (r1 >= 0) goto L_0x0071
            int r2 = r8 - r0
            return r2
        L_0x0071:
            int r0 = r0 + -1
            goto L_0x0037
        L_0x0074:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.skip(int):int");
    }

    public boolean ready() throws IOException {
        return this.pos < this.limit || this.in.ready();
    }

    public final void skip_quick() throws IOException {
        this.pos++;
    }

    public void skip() throws IOException {
        read();
    }

    static int countLines(char[] buffer2, int start, int limit2) {
        int count = 0;
        char prev = 0;
        for (int i = start; i < limit2; i++) {
            char ch = buffer2[i];
            if ((ch == 10 && prev != 13) || ch == 13) {
                count++;
            }
            prev = ch;
        }
        return count;
    }

    public void skipRestOfLine() throws IOException {
        int c;
        do {
            c = read();
            if (c >= 0) {
                if (c == 13) {
                    int c2 = read();
                    if (c2 >= 0 && c2 != 10) {
                        unread();
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        } while (c != 10);
    }

    public void unread() throws IOException {
        if (this.pos != 0) {
            this.pos--;
            char ch = this.buffer[this.pos];
            if (ch == 10 || ch == 13) {
                if (this.pos > 0 && ch == 10 && getConvertCR() && this.buffer[this.pos - 1] == 13) {
                    this.pos--;
                }
                if (this.pos < this.lineStartPos) {
                    this.lineNumber--;
                    int i = this.pos;
                    while (true) {
                        if (i > 0) {
                            i--;
                            char ch2 = this.buffer[i];
                            if (ch2 != 13) {
                                if (ch2 == 10) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    i++;
                    this.lineStartPos = i;
                    return;
                }
                return;
            }
            return;
        }
        throw new IOException("unread too much");
    }

    public void unread_quick() {
        this.pos--;
    }

    public int peek() throws IOException {
        char ch;
        if (this.pos >= this.limit || this.pos <= 0 || (ch = this.buffer[this.pos - 1]) == 10 || ch == 13) {
            int c = read();
            if (c >= 0) {
                unread_quick();
            }
            return c;
        }
        char ch2 = this.buffer[this.pos];
        if (ch2 != 13 || !getConvertCR()) {
            return ch2;
        }
        return 10;
    }
}
