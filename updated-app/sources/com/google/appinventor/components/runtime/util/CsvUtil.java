package com.google.appinventor.components.runtime.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class CsvUtil {
    private CsvUtil() {
    }

    public static YailList fromCsvTable(String csvString) throws Exception {
        CsvParser csvParser = new CsvParser(new StringReader(csvString));
        ArrayList<YailList> csvList = new ArrayList<>();
        while (csvParser.hasNext()) {
            csvList.add(YailList.makeList((List) csvParser.next()));
        }
        csvParser.throwAnyProblem();
        return YailList.makeList((List) csvList);
    }

    public static YailList fromCsvRow(String csvString) throws Exception {
        CsvParser csvParser = new CsvParser(new StringReader(csvString));
        if (csvParser.hasNext()) {
            YailList row = YailList.makeList((List) csvParser.next());
            if (!csvParser.hasNext()) {
                csvParser.throwAnyProblem();
                return row;
            }
            throw new IllegalArgumentException("CSV text has multiple rows. Expected just one row.");
        }
        throw new IllegalArgumentException("CSV text cannot be parsed as a row.");
    }

    public static String toCsvRow(YailList csvRow) {
        StringBuilder csvStringBuilder = new StringBuilder();
        makeCsvRow(csvRow, csvStringBuilder);
        return csvStringBuilder.toString();
    }

    public static String toCsvTable(YailList csvList) {
        StringBuilder csvStringBuilder = new StringBuilder();
        for (Object rowObj : csvList.toArray()) {
            makeCsvRow((YailList) rowObj, csvStringBuilder);
            csvStringBuilder.append("\r\n");
        }
        return csvStringBuilder.toString();
    }

    private static void makeCsvRow(YailList row, StringBuilder csvStringBuilder) {
        String fieldDelim = "";
        for (Object fieldObj : row.toArray()) {
            csvStringBuilder.append(fieldDelim).append("\"").append(fieldObj.toString().replaceAll("\"", "\"\"")).append("\"");
            fieldDelim = ",";
        }
    }

    private static class CsvParser implements Iterator<List<String>> {
        private final Pattern ESCAPED_QUOTE_PATTERN = Pattern.compile("\"\"");
        private final char[] buf = new char[10240];
        private int cellLength = -1;
        private int delimitedCellLength = -1;
        private final Reader in;
        private Exception lastException;
        private int limit;
        private boolean opened = true;
        private int pos;
        private long previouslyRead;

        public CsvParser(Reader in2) {
            this.in = in2;
        }

        public void skip(long charPosition) throws IOException {
            int n;
            while (charPosition > 0 && (n = this.in.read(this.buf, 0, Math.min((int) charPosition, this.buf.length))) >= 0) {
                this.previouslyRead += (long) n;
                charPosition -= (long) n;
            }
        }

        public boolean hasNext() {
            if (this.limit == 0) {
                fill();
            }
            return (this.pos < this.limit || indexAfterCompactionAndFilling(this.pos) < this.limit) && lookingAtCell();
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0058  */
        /* JADX WARNING: Removed duplicated region for block: B:3:0x000f  */
        /* JADX WARNING: Removed duplicated region for block: B:4:0x0022  */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x0056  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.ArrayList<java.lang.String> next() {
            /*
                r8 = this;
                java.util.ArrayList r0 = com.google.appinventor.components.runtime.collect.Lists.newArrayList()
            L_0x0004:
                char[] r1 = r8.buf
                int r2 = r8.pos
                char r1 = r1[r2]
                r2 = 34
                r3 = 1
                if (r1 == r2) goto L_0x0022
                java.lang.String r1 = new java.lang.String
                char[] r2 = r8.buf
                int r4 = r8.pos
                int r5 = r8.cellLength
                r1.<init>(r2, r4, r5)
                java.lang.String r1 = r1.trim()
                r0.add(r1)
                goto L_0x0043
            L_0x0022:
                java.lang.String r1 = new java.lang.String
                char[] r2 = r8.buf
                int r4 = r8.pos
                int r4 = r4 + r3
                int r5 = r8.cellLength
                int r5 = r5 + -2
                r1.<init>(r2, r4, r5)
                java.util.regex.Pattern r2 = r8.ESCAPED_QUOTE_PATTERN
                java.util.regex.Matcher r2 = r2.matcher(r1)
                java.lang.String r4 = "\""
                java.lang.String r2 = r2.replaceAll(r4)
                java.lang.String r2 = r2.trim()
                r0.add(r2)
            L_0x0043:
                int r1 = r8.delimitedCellLength
                r2 = 44
                r4 = 0
                if (r1 <= 0) goto L_0x0058
                char[] r1 = r8.buf
                int r5 = r8.pos
                int r6 = r8.delimitedCellLength
                int r5 = r5 + r6
                int r5 = r5 - r3
                char r1 = r1[r5]
                if (r1 != r2) goto L_0x0058
                r1 = 1
                goto L_0x0059
            L_0x0058:
                r1 = 0
            L_0x0059:
                int r5 = r8.pos
                int r6 = r8.delimitedCellLength
                int r5 = r5 + r6
                r8.pos = r5
                r5 = -1
                r8.cellLength = r5
                r8.delimitedCellLength = r5
                int r5 = r8.limit
                int r6 = r8.pos
                int r7 = r8.limit
                if (r6 < r7) goto L_0x0079
                int r6 = r8.pos
                int r6 = r8.indexAfterCompactionAndFilling(r6)
                int r7 = r8.limit
                if (r6 >= r7) goto L_0x0078
                goto L_0x0079
            L_0x0078:
                r3 = 0
            L_0x0079:
                if (r3 != 0) goto L_0x0088
                char[] r4 = r8.buf
                int r6 = r5 + -1
                char r4 = r4[r6]
                if (r4 != r2) goto L_0x0088
                java.lang.String r2 = ""
                r0.add(r2)
            L_0x0088:
                if (r1 == 0) goto L_0x0092
                if (r3 == 0) goto L_0x0092
                boolean r2 = r8.lookingAtCell()
                if (r2 != 0) goto L_0x0004
            L_0x0092:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.CsvUtil.CsvParser.next():java.util.ArrayList");
        }

        public long getCharPosition() {
            return this.previouslyRead + ((long) this.pos);
        }

        private int indexAfterCompactionAndFilling(int i) {
            if (this.pos > 0) {
                i = compact(i);
            }
            fill();
            return i;
        }

        private int compact(int i) {
            int oldPos = this.pos;
            this.pos = 0;
            int toMove = this.limit - oldPos;
            if (toMove > 0) {
                System.arraycopy(this.buf, oldPos, this.buf, 0, toMove);
            }
            this.limit -= oldPos;
            this.previouslyRead += (long) oldPos;
            return i - oldPos;
        }

        private void fill() {
            int toFill = this.buf.length - this.limit;
            while (this.opened && toFill > 0) {
                try {
                    int n = this.in.read(this.buf, this.limit, toFill);
                    if (n == -1) {
                        this.opened = false;
                    } else {
                        this.limit += n;
                        toFill -= n;
                    }
                } catch (IOException e) {
                    this.lastException = e;
                    this.opened = false;
                }
            }
        }

        private boolean lookingAtCell() {
            return this.buf[this.pos] == '\"' ? findUnescapedEndQuote(this.pos + 1) : findUnquotedCellEnd(this.pos);
        }

        private boolean findUnescapedEndQuote(int i) {
            while (true) {
                if (i >= this.limit) {
                    int indexAfterCompactionAndFilling = indexAfterCompactionAndFilling(i);
                    i = indexAfterCompactionAndFilling;
                    if (indexAfterCompactionAndFilling >= this.limit) {
                        this.lastException = new IllegalArgumentException("Syntax Error. unclosed quoted cell");
                        return false;
                    }
                }
                if (this.buf[i] != '\"' || ((i = checkedIndex(i + 1)) != this.limit && this.buf[i] == '\"')) {
                    i++;
                }
            }
            this.cellLength = i - this.pos;
            return findDelimOrEnd(i);
        }

        private boolean findDelimOrEnd(int i) {
            while (true) {
                if (i >= this.limit) {
                    int indexAfterCompactionAndFilling = indexAfterCompactionAndFilling(i);
                    i = indexAfterCompactionAndFilling;
                    if (indexAfterCompactionAndFilling >= this.limit) {
                        this.delimitedCellLength = this.limit - this.pos;
                        return true;
                    }
                }
                switch (this.buf[i]) {
                    case 9:
                    case ' ':
                        i++;
                    case 10:
                    case ',':
                        this.delimitedCellLength = checkedIndex(i + 1) - this.pos;
                        return true;
                    case 13:
                        int j = checkedIndex(i + 1);
                        this.delimitedCellLength = (this.buf[j] == 10 ? checkedIndex(j + 1) : j) - this.pos;
                        return true;
                    default:
                        this.lastException = new IOException("Syntax Error: non-whitespace between closing quote and delimiter or end");
                        return false;
                }
            }
        }

        private int checkedIndex(int i) {
            return i < this.limit ? i : indexAfterCompactionAndFilling(i);
        }

        private boolean findUnquotedCellEnd(int i) {
            while (true) {
                if (i >= this.limit) {
                    int indexAfterCompactionAndFilling = indexAfterCompactionAndFilling(i);
                    i = indexAfterCompactionAndFilling;
                    if (indexAfterCompactionAndFilling >= this.limit) {
                        int i2 = this.limit - this.pos;
                        this.cellLength = i2;
                        this.delimitedCellLength = i2;
                        return true;
                    }
                }
                switch (this.buf[i]) {
                    case 10:
                    case ',':
                        this.cellLength = i - this.pos;
                        this.delimitedCellLength = this.cellLength + 1;
                        return true;
                    case 13:
                        this.cellLength = i - this.pos;
                        int j = checkedIndex(i + 1);
                        this.delimitedCellLength = (this.buf[j] == 10 ? checkedIndex(j + 1) : j) - this.pos;
                        return true;
                    case '\"':
                        this.lastException = new IllegalArgumentException("Syntax Error: quote in unquoted cell");
                        return false;
                    default:
                        i++;
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void throwAnyProblem() throws Exception {
            if (this.lastException != null) {
                throw this.lastException;
            }
        }
    }
}
