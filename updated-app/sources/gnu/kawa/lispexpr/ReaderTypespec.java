package gnu.kawa.lispexpr;

import gnu.mapping.InPort;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderTypespec extends ReadTableEntry {
    public int getKind() {
        return 6;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        boolean z;
        int startPos = in.tokenBufferLength;
        LineBufferedReader port = in.getPort();
        ReadTable rtable = ReadTable.getCurrent();
        char saveReadState = 0;
        in.tokenBufferAppend(ch);
        int c = ch;
        if (port instanceof InPort) {
            saveReadState = ((InPort) port).readState;
            ((InPort) port).readState = (char) ch;
        }
        boolean got_open_square = false;
        while (true) {
            int prev = c;
            try {
                if (port.pos >= port.limit || prev == 10) {
                    c = port.read();
                } else {
                    char[] cArr = port.buffer;
                    int i = port.pos;
                    port.pos = i + 1;
                    c = cArr[i];
                }
                if (c != 92) {
                    if (!got_open_square && c == 91) {
                        z = true;
                    } else if (!got_open_square || c != 93) {
                        if (rtable.lookup(c).getKind() != 2) {
                            break;
                        }
                        in.tokenBufferAppend(c);
                    } else {
                        z = false;
                    }
                    got_open_square = z;
                    in.tokenBufferAppend(c);
                } else if (in instanceof LispReader) {
                    c = ((LispReader) in).readEscape();
                } else {
                    c = port.read();
                }
            } finally {
                in.tokenBufferLength = startPos;
                if (port instanceof InPort) {
                    ((InPort) port).readState = saveReadState;
                }
            }
        }
        in.unread(c);
        return new String(in.tokenBuffer, startPos, in.tokenBufferLength - startPos).intern();
    }
}
