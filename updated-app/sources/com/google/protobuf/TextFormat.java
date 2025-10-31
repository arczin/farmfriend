package com.google.protobuf;

import androidx.core.location.LocationRequestCompat;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.util.Ev3Constants;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.google.protobuf.MessageReflection;
import com.google.protobuf.TextFormatParseInfoTree;
import com.google.protobuf.UnknownFieldSet;
import gnu.expr.Declaration;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextFormat {
    private static final Printer DEFAULT_PRINTER = new Printer();
    private static final Parser PARSER = Parser.newBuilder().build();
    private static final Printer SINGLE_LINE_PRINTER = new Printer().setSingleLineMode(true);
    private static final Printer UNICODE_PRINTER = new Printer().setEscapeNonAscii(false);
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(TextFormat.class.getName());

    private TextFormat() {
    }

    public static void print(MessageOrBuilder message, Appendable output) throws IOException {
        DEFAULT_PRINTER.print(message, new TextGenerator(output));
    }

    public static void print(UnknownFieldSet fields, Appendable output) throws IOException {
        DEFAULT_PRINTER.printUnknownFields(fields, new TextGenerator(output));
    }

    public static void printUnicode(MessageOrBuilder message, Appendable output) throws IOException {
        UNICODE_PRINTER.print(message, new TextGenerator(output));
    }

    public static void printUnicode(UnknownFieldSet fields, Appendable output) throws IOException {
        UNICODE_PRINTER.printUnknownFields(fields, new TextGenerator(output));
    }

    public static String shortDebugString(MessageOrBuilder message) {
        try {
            StringBuilder sb = new StringBuilder();
            SINGLE_LINE_PRINTER.print(message, new TextGenerator(sb));
            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String shortDebugString(Descriptors.FieldDescriptor field, Object value) {
        try {
            StringBuilder sb = new StringBuilder();
            SINGLE_LINE_PRINTER.printField(field, value, new TextGenerator(sb));
            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String shortDebugString(UnknownFieldSet fields) {
        try {
            StringBuilder sb = new StringBuilder();
            SINGLE_LINE_PRINTER.printUnknownFields(fields, new TextGenerator(sb));
            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(MessageOrBuilder message) {
        try {
            StringBuilder text = new StringBuilder();
            print(message, (Appendable) text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(UnknownFieldSet fields) {
        try {
            StringBuilder text = new StringBuilder();
            print(fields, (Appendable) text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(MessageOrBuilder message) {
        try {
            StringBuilder text = new StringBuilder();
            UNICODE_PRINTER.print(message, new TextGenerator(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(UnknownFieldSet fields) {
        try {
            StringBuilder text = new StringBuilder();
            UNICODE_PRINTER.printUnknownFields(fields, new TextGenerator(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printField(Descriptors.FieldDescriptor field, Object value, Appendable output) throws IOException {
        DEFAULT_PRINTER.printField(field, value, new TextGenerator(output));
    }

    public static String printFieldToString(Descriptors.FieldDescriptor field, Object value) {
        try {
            StringBuilder text = new StringBuilder();
            printField(field, value, text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printFieldValue(Descriptors.FieldDescriptor field, Object value, Appendable output) throws IOException {
        DEFAULT_PRINTER.printFieldValue(field, value, new TextGenerator(output));
    }

    public static void printUnknownFieldValue(int tag, Object value, Appendable output) throws IOException {
        printUnknownFieldValue(tag, value, new TextGenerator(output));
    }

    /* access modifiers changed from: private */
    public static void printUnknownFieldValue(int tag, Object value, TextGenerator generator) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                generator.print(unsignedToString(((Long) value).longValue()));
                return;
            case 1:
                Locale locale = null;
                generator.print(String.format((Locale) null, "0x%016x", new Object[]{(Long) value}));
                return;
            case 2:
                generator.print("\"");
                generator.print(escapeBytes((ByteString) value));
                generator.print("\"");
                return;
            case 3:
                DEFAULT_PRINTER.printUnknownFields((UnknownFieldSet) value, generator);
                return;
            case 5:
                Locale locale2 = null;
                generator.print(String.format((Locale) null, "0x%08x", new Object[]{(Integer) value}));
                return;
            default:
                throw new IllegalArgumentException("Bad tag: " + tag);
        }
    }

    private static final class Printer {
        boolean escapeNonAscii;
        boolean singleLineMode;

        private Printer() {
            this.singleLineMode = false;
            this.escapeNonAscii = true;
        }

        /* access modifiers changed from: private */
        public Printer setSingleLineMode(boolean singleLineMode2) {
            this.singleLineMode = singleLineMode2;
            return this;
        }

        /* access modifiers changed from: private */
        public Printer setEscapeNonAscii(boolean escapeNonAscii2) {
            this.escapeNonAscii = escapeNonAscii2;
            return this;
        }

        /* access modifiers changed from: private */
        public void print(MessageOrBuilder message, TextGenerator generator) throws IOException {
            for (Map.Entry<Descriptors.FieldDescriptor, Object> field : message.getAllFields().entrySet()) {
                printField(field.getKey(), field.getValue(), generator);
            }
            printUnknownFields(message.getUnknownFields(), generator);
        }

        /* access modifiers changed from: private */
        public void printField(Descriptors.FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            if (field.isRepeated()) {
                for (Object element : (List) value) {
                    printSingleField(field, element, generator);
                }
                return;
            }
            printSingleField(field, value, generator);
        }

        private void printSingleField(Descriptors.FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            if (field.isExtension()) {
                generator.print("[");
                if (!field.getContainingType().getOptions().getMessageSetWireFormat() || field.getType() != Descriptors.FieldDescriptor.Type.MESSAGE || !field.isOptional() || field.getExtensionScope() != field.getMessageType()) {
                    generator.print(field.getFullName());
                } else {
                    generator.print(field.getMessageType().getFullName());
                }
                generator.print("]");
            } else if (field.getType() == Descriptors.FieldDescriptor.Type.GROUP) {
                generator.print(field.getMessageType().getName());
            } else {
                generator.print(field.getName());
            }
            if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                generator.print(": ");
            } else if (this.singleLineMode) {
                generator.print(" { ");
            } else {
                generator.print(" {\n");
                generator.indent();
            }
            printFieldValue(field, value, generator);
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (this.singleLineMode) {
                    generator.print("} ");
                    return;
                }
                generator.outdent();
                generator.print("}\n");
            } else if (this.singleLineMode) {
                generator.print(" ");
            } else {
                generator.print("\n");
            }
        }

        /* access modifiers changed from: private */
        public void printFieldValue(Descriptors.FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            String str;
            switch (field.getType()) {
                case INT32:
                case SINT32:
                case SFIXED32:
                    generator.print(((Integer) value).toString());
                    return;
                case INT64:
                case SINT64:
                case SFIXED64:
                    generator.print(((Long) value).toString());
                    return;
                case BOOL:
                    generator.print(((Boolean) value).toString());
                    return;
                case FLOAT:
                    generator.print(((Float) value).toString());
                    return;
                case DOUBLE:
                    generator.print(((Double) value).toString());
                    return;
                case UINT32:
                case FIXED32:
                    generator.print(TextFormat.unsignedToString(((Integer) value).intValue()));
                    return;
                case UINT64:
                case FIXED64:
                    generator.print(TextFormat.unsignedToString(((Long) value).longValue()));
                    return;
                case STRING:
                    generator.print("\"");
                    if (this.escapeNonAscii) {
                        str = TextFormatEscaper.escapeText((String) value);
                    } else {
                        str = TextFormat.escapeDoubleQuotesAndBackslashes((String) value).replace("\n", "\\n");
                    }
                    generator.print(str);
                    generator.print("\"");
                    return;
                case BYTES:
                    generator.print("\"");
                    if (value instanceof ByteString) {
                        generator.print(TextFormat.escapeBytes((ByteString) value));
                    } else {
                        generator.print(TextFormat.escapeBytes((byte[]) value));
                    }
                    generator.print("\"");
                    return;
                case ENUM:
                    generator.print(((Descriptors.EnumValueDescriptor) value).getName());
                    return;
                case MESSAGE:
                case GROUP:
                    print((Message) value, generator);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: private */
        public void printUnknownFields(UnknownFieldSet unknownFields, TextGenerator generator) throws IOException {
            for (Map.Entry<Integer, UnknownFieldSet.Field> entry : unknownFields.asMap().entrySet()) {
                int number = entry.getKey().intValue();
                UnknownFieldSet.Field field = entry.getValue();
                printUnknownField(number, 0, field.getVarintList(), generator);
                printUnknownField(number, 5, field.getFixed32List(), generator);
                printUnknownField(number, 1, field.getFixed64List(), generator);
                printUnknownField(number, 2, field.getLengthDelimitedList(), generator);
                for (UnknownFieldSet value : field.getGroupList()) {
                    generator.print(entry.getKey().toString());
                    if (this.singleLineMode) {
                        generator.print(" { ");
                    } else {
                        generator.print(" {\n");
                        generator.indent();
                    }
                    printUnknownFields(value, generator);
                    if (this.singleLineMode) {
                        generator.print("} ");
                    } else {
                        generator.outdent();
                        generator.print("}\n");
                    }
                }
            }
        }

        private void printUnknownField(int number, int wireType, List<?> values, TextGenerator generator) throws IOException {
            for (Object value : values) {
                generator.print(String.valueOf(number));
                generator.print(": ");
                TextFormat.printUnknownFieldValue(wireType, value, generator);
                generator.print(this.singleLineMode ? " " : "\n");
            }
        }
    }

    public static String unsignedToString(int value) {
        if (value >= 0) {
            return Integer.toString(value);
        }
        return Long.toString(((long) value) & 4294967295L);
    }

    public static String unsignedToString(long value) {
        if (value >= 0) {
            return Long.toString(value);
        }
        return BigInteger.valueOf(LocationRequestCompat.PASSIVE_INTERVAL & value).setBit(63).toString();
    }

    private static final class TextGenerator {
        private boolean atStartOfLine;
        private final StringBuilder indent;
        private final Appendable output;

        private TextGenerator(Appendable output2) {
            this.indent = new StringBuilder();
            this.atStartOfLine = true;
            this.output = output2;
        }

        public void indent() {
            this.indent.append("  ");
        }

        public void outdent() {
            int length = this.indent.length();
            if (length != 0) {
                this.indent.delete(length - 2, length);
                return;
            }
            throw new IllegalArgumentException(" Outdent() without matching Indent().");
        }

        public void print(CharSequence text) throws IOException {
            int size = text.length();
            int pos = 0;
            for (int i = 0; i < size; i++) {
                if (text.charAt(i) == 10) {
                    write(text.subSequence(pos, i + 1));
                    pos = i + 1;
                    this.atStartOfLine = true;
                }
            }
            write(text.subSequence(pos, size));
        }

        private void write(CharSequence data) throws IOException {
            if (data.length() != 0) {
                if (this.atStartOfLine) {
                    this.atStartOfLine = false;
                    this.output.append(this.indent);
                }
                this.output.append(data);
            }
        }
    }

    private static final class Tokenizer {
        private static final Pattern DOUBLE_INFINITY = Pattern.compile("-?inf(inity)?", 2);
        private static final Pattern FLOAT_INFINITY = Pattern.compile("-?inf(inity)?f?", 2);
        private static final Pattern FLOAT_NAN = Pattern.compile("nanf?", 2);
        private static final Pattern TOKEN = Pattern.compile("[a-zA-Z_][0-9a-zA-Z_+-]*+|[.]?[0-9+-][0-9a-zA-Z_.+-]*+|\"([^\"\n\\\\]|\\\\.)*+(\"|\\\\?$)|'([^'\n\\\\]|\\\\.)*+('|\\\\?$)", 8);
        private static final Pattern WHITESPACE = Pattern.compile("(\\s|(#.*$))++", 8);
        private int column;
        /* access modifiers changed from: private */
        public String currentToken;
        private int line;
        private final Matcher matcher;
        private int pos;
        private int previousColumn;
        private int previousLine;
        private final CharSequence text;

        private Tokenizer(CharSequence text2) {
            this.pos = 0;
            this.line = 0;
            this.column = 0;
            this.previousLine = 0;
            this.previousColumn = 0;
            this.text = text2;
            this.matcher = WHITESPACE.matcher(text2);
            skipWhitespace();
            nextToken();
        }

        /* access modifiers changed from: package-private */
        public int getPreviousLine() {
            return this.previousLine;
        }

        /* access modifiers changed from: package-private */
        public int getPreviousColumn() {
            return this.previousColumn;
        }

        /* access modifiers changed from: package-private */
        public int getLine() {
            return this.line;
        }

        /* access modifiers changed from: package-private */
        public int getColumn() {
            return this.column;
        }

        public boolean atEnd() {
            return this.currentToken.length() == 0;
        }

        public void nextToken() {
            this.previousLine = this.line;
            this.previousColumn = this.column;
            while (this.pos < this.matcher.regionStart()) {
                if (this.text.charAt(this.pos) == 10) {
                    this.line++;
                    this.column = 0;
                } else {
                    this.column++;
                }
                this.pos++;
            }
            if (this.matcher.regionStart() == this.matcher.regionEnd()) {
                this.currentToken = "";
                return;
            }
            this.matcher.usePattern(TOKEN);
            if (this.matcher.lookingAt()) {
                this.currentToken = this.matcher.group();
                this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
            } else {
                this.currentToken = String.valueOf(this.text.charAt(this.pos));
                this.matcher.region(this.pos + 1, this.matcher.regionEnd());
            }
            skipWhitespace();
        }

        private void skipWhitespace() {
            this.matcher.usePattern(WHITESPACE);
            if (this.matcher.lookingAt()) {
                this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
            }
        }

        public boolean tryConsume(String token) {
            if (!this.currentToken.equals(token)) {
                return false;
            }
            nextToken();
            return true;
        }

        public void consume(String token) throws ParseException {
            if (!tryConsume(token)) {
                throw parseException("Expected \"" + token + "\".");
            }
        }

        public boolean lookingAtInteger() {
            if (this.currentToken.length() == 0) {
                return false;
            }
            char c = this.currentToken.charAt(0);
            if (('0' <= c && c <= '9') || c == '-' || c == '+') {
                return true;
            }
            return false;
        }

        public boolean lookingAt(String text2) {
            return this.currentToken.equals(text2);
        }

        public String consumeIdentifier() throws ParseException {
            for (int i = 0; i < this.currentToken.length(); i++) {
                char c = this.currentToken.charAt(i);
                if (('a' > c || c > 'z') && (('A' > c || c > 'Z') && !(('0' <= c && c <= '9') || c == '_' || c == '.'))) {
                    throw parseException("Expected identifier. Found '" + this.currentToken + "'");
                }
            }
            String result = this.currentToken;
            nextToken();
            return result;
        }

        public boolean tryConsumeIdentifier() {
            try {
                consumeIdentifier();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public int consumeInt32() throws ParseException {
            try {
                int result = TextFormat.parseInt32(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public int consumeUInt32() throws ParseException {
            try {
                int result = TextFormat.parseUInt32(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public long consumeInt64() throws ParseException {
            try {
                long result = TextFormat.parseInt64(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeInt64() {
            try {
                consumeInt64();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public long consumeUInt64() throws ParseException {
            try {
                long result = TextFormat.parseUInt64(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeUInt64() {
            try {
                consumeUInt64();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public double consumeDouble() throws ParseException {
            if (DOUBLE_INFINITY.matcher(this.currentToken).matches()) {
                boolean negative = this.currentToken.startsWith("-");
                nextToken();
                return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else if (this.currentToken.equalsIgnoreCase("nan")) {
                nextToken();
                return Double.NaN;
            } else {
                try {
                    double result = Double.parseDouble(this.currentToken);
                    nextToken();
                    return result;
                } catch (NumberFormatException e) {
                    throw floatParseException(e);
                }
            }
        }

        public boolean tryConsumeDouble() {
            try {
                consumeDouble();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public float consumeFloat() throws ParseException {
            if (FLOAT_INFINITY.matcher(this.currentToken).matches()) {
                boolean negative = this.currentToken.startsWith("-");
                nextToken();
                return negative ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            } else if (FLOAT_NAN.matcher(this.currentToken).matches()) {
                nextToken();
                return Float.NaN;
            } else {
                try {
                    float result = Float.parseFloat(this.currentToken);
                    nextToken();
                    return result;
                } catch (NumberFormatException e) {
                    throw floatParseException(e);
                }
            }
        }

        public boolean tryConsumeFloat() {
            try {
                consumeFloat();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public boolean consumeBoolean() throws ParseException {
            if (this.currentToken.equals("true") || this.currentToken.equals("True") || this.currentToken.equals("t") || this.currentToken.equals(Component.TYPEFACE_SANSSERIF)) {
                nextToken();
                return true;
            } else if (this.currentToken.equals("false") || this.currentToken.equals("False") || this.currentToken.equals("f") || this.currentToken.equals(Component.TYPEFACE_DEFAULT)) {
                nextToken();
                return false;
            } else {
                throw parseException("Expected \"true\" or \"false\".");
            }
        }

        public String consumeString() throws ParseException {
            return consumeByteString().toStringUtf8();
        }

        public boolean tryConsumeString() {
            try {
                consumeString();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public ByteString consumeByteString() throws ParseException {
            List<ByteString> list = new ArrayList<>();
            consumeByteString(list);
            while (true) {
                if (!this.currentToken.startsWith("'") && !this.currentToken.startsWith("\"")) {
                    return ByteString.copyFrom((Iterable<ByteString>) list);
                }
                consumeByteString(list);
            }
        }

        private void consumeByteString(List<ByteString> list) throws ParseException {
            char c = 0;
            if (this.currentToken.length() > 0) {
                c = this.currentToken.charAt(0);
            }
            char quote = c;
            if (quote != '\"' && quote != '\'') {
                throw parseException("Expected string.");
            } else if (this.currentToken.length() < 2 || this.currentToken.charAt(this.currentToken.length() - 1) != quote) {
                throw parseException("String missing ending quote.");
            } else {
                try {
                    ByteString result = TextFormat.unescapeBytes(this.currentToken.substring(1, this.currentToken.length() - 1));
                    nextToken();
                    list.add(result);
                } catch (InvalidEscapeSequenceException e) {
                    throw parseException(e.getMessage());
                }
            }
        }

        public ParseException parseException(String description) {
            return new ParseException(this.line + 1, this.column + 1, description);
        }

        public ParseException parseExceptionPreviousToken(String description) {
            return new ParseException(this.previousLine + 1, this.previousColumn + 1, description);
        }

        private ParseException integerParseException(NumberFormatException e) {
            return parseException("Couldn't parse integer: " + e.getMessage());
        }

        private ParseException floatParseException(NumberFormatException e) {
            return parseException("Couldn't parse number: " + e.getMessage());
        }

        public UnknownFieldParseException unknownFieldParseExceptionPreviousToken(String unknownField, String description) {
            return new UnknownFieldParseException(this.previousLine + 1, this.previousColumn + 1, unknownField, description);
        }
    }

    public static class ParseException extends IOException {
        private static final long serialVersionUID = 3196188060225107702L;
        private final int column;
        private final int line;

        public ParseException(String message) {
            this(-1, -1, message);
        }

        public ParseException(int line2, int column2, String message) {
            super(Integer.toString(line2) + ":" + column2 + ": " + message);
            this.line = line2;
            this.column = column2;
        }

        public int getLine() {
            return this.line;
        }

        public int getColumn() {
            return this.column;
        }
    }

    public static class UnknownFieldParseException extends ParseException {
        private final String unknownField;

        public UnknownFieldParseException(String message) {
            this(-1, -1, "", message);
        }

        public UnknownFieldParseException(int line, int column, String unknownField2, String message) {
            super(line, column, message);
            this.unknownField = unknownField2;
        }

        public String getUnknownField() {
            return this.unknownField;
        }
    }

    public static Parser getParser() {
        return PARSER;
    }

    public static void merge(Readable input, Message.Builder builder) throws IOException {
        PARSER.merge(input, builder);
    }

    public static void merge(CharSequence input, Message.Builder builder) throws ParseException {
        PARSER.merge(input, builder);
    }

    public static void merge(Readable input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
        PARSER.merge(input, extensionRegistry, builder);
    }

    public static void merge(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws ParseException {
        PARSER.merge(input, extensionRegistry, builder);
    }

    public static class Parser {
        private static final int BUFFER_SIZE = 4096;
        private final boolean allowUnknownFields;
        private TextFormatParseInfoTree.Builder parseInfoTreeBuilder;
        private final SingularOverwritePolicy singularOverwritePolicy;

        public enum SingularOverwritePolicy {
            ALLOW_SINGULAR_OVERWRITES,
            FORBID_SINGULAR_OVERWRITES
        }

        private Parser(boolean allowUnknownFields2, SingularOverwritePolicy singularOverwritePolicy2, TextFormatParseInfoTree.Builder parseInfoTreeBuilder2) {
            this.allowUnknownFields = allowUnknownFields2;
            this.singularOverwritePolicy = singularOverwritePolicy2;
            this.parseInfoTreeBuilder = parseInfoTreeBuilder2;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {
            private boolean allowUnknownFields = false;
            private TextFormatParseInfoTree.Builder parseInfoTreeBuilder = null;
            private SingularOverwritePolicy singularOverwritePolicy = SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES;

            public Builder setSingularOverwritePolicy(SingularOverwritePolicy p) {
                this.singularOverwritePolicy = p;
                return this;
            }

            public Builder setParseInfoTreeBuilder(TextFormatParseInfoTree.Builder parseInfoTreeBuilder2) {
                this.parseInfoTreeBuilder = parseInfoTreeBuilder2;
                return this;
            }

            public Parser build() {
                return new Parser(this.allowUnknownFields, this.singularOverwritePolicy, this.parseInfoTreeBuilder);
            }
        }

        public void merge(Readable input, Message.Builder builder) throws IOException {
            merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(CharSequence input, Message.Builder builder) throws ParseException {
            merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(Readable input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
            merge((CharSequence) toStringBuilder(input), extensionRegistry, builder);
        }

        private static StringBuilder toStringBuilder(Readable input) throws IOException {
            StringBuilder text = new StringBuilder();
            CharBuffer buffer = CharBuffer.allocate(4096);
            while (true) {
                int n = input.read(buffer);
                if (n == -1) {
                    return text;
                }
                buffer.flip();
                text.append(buffer, 0, n);
            }
        }

        private void checkUnknownFields(List<String> unknownFields) throws ParseException {
            if (!unknownFields.isEmpty()) {
                StringBuilder msg = new StringBuilder("Input contains unknown fields and/or extensions:");
                for (String field : unknownFields) {
                    msg.append(10).append(field);
                }
                if (this.allowUnknownFields) {
                    TextFormat.logger.warning(msg.toString());
                } else {
                    String[] lineColumn = unknownFields.get(0).split(":");
                    throw new ParseException(Integer.valueOf(lineColumn[0]).intValue(), Integer.valueOf(lineColumn[1]).intValue(), msg.toString());
                }
            }
        }

        public void merge(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws ParseException {
            Tokenizer tokenizer = new Tokenizer(input);
            MessageReflection.BuilderAdapter target = new MessageReflection.BuilderAdapter(builder);
            List<String> unknownFields = new ArrayList<>();
            while (!tokenizer.atEnd()) {
                mergeField(tokenizer, extensionRegistry, target, unknownFields);
            }
            checkUnknownFields(unknownFields);
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, List<String> unknownFields) throws ParseException {
            mergeField(tokenizer, extensionRegistry, target, this.parseInfoTreeBuilder, unknownFields);
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, TextFormatParseInfoTree.Builder parseTreeBuilder, List<String> unknownFields) throws ParseException {
            ExtensionRegistry.ExtensionInfo extension;
            Descriptors.FieldDescriptor field;
            Descriptors.FieldDescriptor field2;
            Descriptors.FieldDescriptor field3;
            Tokenizer tokenizer2 = tokenizer;
            TextFormatParseInfoTree.Builder builder = parseTreeBuilder;
            List<String> list = unknownFields;
            Descriptors.FieldDescriptor field4 = null;
            int startLine = tokenizer.getLine();
            int startColumn = tokenizer.getColumn();
            Descriptors.Descriptor type = target.getDescriptorForType();
            if (tokenizer2.tryConsume("[")) {
                StringBuilder name = new StringBuilder(tokenizer.consumeIdentifier());
                while (tokenizer2.tryConsume(".")) {
                    name.append('.');
                    name.append(tokenizer.consumeIdentifier());
                }
                ExtensionRegistry.ExtensionInfo extension2 = target.findExtensionByName(extensionRegistry, name.toString());
                if (extension2 == null) {
                    list.add((tokenizer.getPreviousLine() + 1) + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + ".[" + name + "]");
                } else if (extension2.descriptor.getContainingType() == type) {
                    field4 = extension2.descriptor;
                } else {
                    throw tokenizer2.parseExceptionPreviousToken("Extension \"" + name + "\" does not extend message type \"" + type.getFullName() + "\".");
                }
                tokenizer2.consume("]");
                field = field4;
                extension = extension2;
            } else {
                ExtensionRegistry extensionRegistry2 = extensionRegistry;
                MessageReflection.MergeTarget mergeTarget = target;
                String name2 = tokenizer.consumeIdentifier();
                Descriptors.FieldDescriptor field5 = type.findFieldByName(name2);
                if (field5 == null) {
                    Descriptors.FieldDescriptor field6 = type.findFieldByName(name2.toLowerCase(Locale.US));
                    if (field6 != null) {
                        field3 = field6;
                        if (field6.getType() != Descriptors.FieldDescriptor.Type.GROUP) {
                            field5 = null;
                        }
                    } else {
                        field3 = field6;
                    }
                    field5 = field3;
                }
                if (field5 != null && field5.getType() == Descriptors.FieldDescriptor.Type.GROUP && !field5.getMessageType().getName().equals(name2)) {
                    field5 = null;
                }
                if (field5 == null) {
                    list.add((tokenizer.getPreviousLine() + 1) + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + "." + name2);
                }
                field = field5;
                extension = null;
            }
            if (field != null) {
                if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    tokenizer2.tryConsume(":");
                    if (builder != null) {
                        field2 = field;
                        consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, builder.getBuilderForSubMessageField(field), unknownFields);
                    } else {
                        field2 = field;
                        consumeFieldValues(tokenizer, extensionRegistry, target, field2, extension, parseTreeBuilder, unknownFields);
                    }
                } else {
                    field2 = field;
                    tokenizer2.consume(":");
                    consumeFieldValues(tokenizer, extensionRegistry, target, field2, extension, parseTreeBuilder, unknownFields);
                }
                if (builder != null) {
                    builder.setLocation(field2, TextFormatParseLocation.create(startLine, startColumn));
                }
                if (!tokenizer2.tryConsume(";")) {
                    tokenizer2.tryConsume(",");
                }
            } else if (!tokenizer2.tryConsume(":") || tokenizer2.lookingAt("{") || tokenizer2.lookingAt("<")) {
                skipFieldMessage(tokenizer);
            } else {
                skipFieldValue(tokenizer);
            }
        }

        private void consumeFieldValues(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, Descriptors.FieldDescriptor field, ExtensionRegistry.ExtensionInfo extension, TextFormatParseInfoTree.Builder parseTreeBuilder, List<String> unknownFields) throws ParseException {
            if (!field.isRepeated() || !tokenizer.tryConsume("[")) {
                consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
                return;
            }
            while (true) {
                consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
                if (!tokenizer.tryConsume("]")) {
                    tokenizer.consume(",");
                } else {
                    return;
                }
            }
        }

        private void consumeFieldValue(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, Descriptors.FieldDescriptor field, ExtensionRegistry.ExtensionInfo extension, TextFormatParseInfoTree.Builder parseTreeBuilder, List<String> unknownFields) throws ParseException {
            String endToken;
            Tokenizer tokenizer2 = tokenizer;
            MessageReflection.MergeTarget mergeTarget = target;
            Descriptors.FieldDescriptor fieldDescriptor = field;
            ExtensionRegistry.ExtensionInfo extensionInfo = extension;
            Object value = null;
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (tokenizer2.tryConsume("<")) {
                    endToken = ">";
                } else {
                    tokenizer2.consume("{");
                    endToken = "}";
                }
                MessageReflection.MergeTarget subField = mergeTarget.newMergeTargetForField(fieldDescriptor, extensionInfo == null ? null : extensionInfo.defaultInstance);
                while (!tokenizer2.tryConsume(endToken)) {
                    if (!tokenizer.atEnd()) {
                        mergeField(tokenizer, extensionRegistry, subField, parseTreeBuilder, unknownFields);
                    } else {
                        throw tokenizer2.parseException("Expected \"" + endToken + "\".");
                    }
                }
                value = subField.finish();
            } else {
                switch (field.getType()) {
                    case INT32:
                    case SINT32:
                    case SFIXED32:
                        value = Integer.valueOf(tokenizer.consumeInt32());
                        break;
                    case INT64:
                    case SINT64:
                    case SFIXED64:
                        value = Long.valueOf(tokenizer.consumeInt64());
                        break;
                    case BOOL:
                        value = Boolean.valueOf(tokenizer.consumeBoolean());
                        break;
                    case FLOAT:
                        value = Float.valueOf(tokenizer.consumeFloat());
                        break;
                    case DOUBLE:
                        value = Double.valueOf(tokenizer.consumeDouble());
                        break;
                    case UINT32:
                    case FIXED32:
                        value = Integer.valueOf(tokenizer.consumeUInt32());
                        break;
                    case UINT64:
                    case FIXED64:
                        value = Long.valueOf(tokenizer.consumeUInt64());
                        break;
                    case STRING:
                        value = tokenizer.consumeString();
                        break;
                    case BYTES:
                        value = tokenizer.consumeByteString();
                        break;
                    case ENUM:
                        Descriptors.EnumDescriptor enumType = field.getEnumType();
                        if (tokenizer.lookingAtInteger()) {
                            int number = tokenizer.consumeInt32();
                            value = enumType.findValueByNumber(number);
                            if (value == null) {
                                throw tokenizer2.parseExceptionPreviousToken("Enum type \"" + enumType.getFullName() + "\" has no value with number " + number + '.');
                            }
                        } else {
                            String id = tokenizer.consumeIdentifier();
                            value = enumType.findValueByName(id);
                            if (value == null) {
                                throw tokenizer2.parseExceptionPreviousToken("Enum type \"" + enumType.getFullName() + "\" has no value named \"" + id + "\".");
                            }
                        }
                        break;
                    case MESSAGE:
                    case GROUP:
                        throw new RuntimeException("Can't get here.");
                }
            }
            if (field.isRepeated()) {
                mergeTarget.addRepeatedField(fieldDescriptor, value);
            } else if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && target.hasField(field)) {
                throw tokenizer2.parseExceptionPreviousToken("Non-repeated field \"" + field.getFullName() + "\" cannot be overwritten.");
            } else if (this.singularOverwritePolicy != SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES || field.getContainingOneof() == null || !mergeTarget.hasOneof(field.getContainingOneof())) {
                mergeTarget.setField(fieldDescriptor, value);
            } else {
                Descriptors.OneofDescriptor oneof = field.getContainingOneof();
                throw tokenizer2.parseExceptionPreviousToken("Field \"" + field.getFullName() + "\" is specified along with field \"" + mergeTarget.getOneofFieldDescriptor(oneof).getFullName() + "\", another member of oneof \"" + oneof.getName() + "\".");
            }
        }

        private void skipField(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsume("[")) {
                do {
                    tokenizer.consumeIdentifier();
                } while (tokenizer.tryConsume("."));
                tokenizer.consume("]");
            } else {
                tokenizer.consumeIdentifier();
            }
            if (!tokenizer.tryConsume(":") || tokenizer.lookingAt("<") || tokenizer.lookingAt("{")) {
                skipFieldMessage(tokenizer);
            } else {
                skipFieldValue(tokenizer);
            }
            if (!tokenizer.tryConsume(";")) {
                tokenizer.tryConsume(",");
            }
        }

        private void skipFieldMessage(Tokenizer tokenizer) throws ParseException {
            String delimiter;
            if (tokenizer.tryConsume("<")) {
                delimiter = ">";
            } else {
                tokenizer.consume("{");
                delimiter = "}";
            }
            while (!tokenizer.lookingAt(">") && !tokenizer.lookingAt("}")) {
                skipField(tokenizer);
            }
            tokenizer.consume(delimiter);
        }

        private void skipFieldValue(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsumeString()) {
                do {
                } while (tokenizer.tryConsumeString());
            } else if (!tokenizer.tryConsumeIdentifier() && !tokenizer.tryConsumeInt64() && !tokenizer.tryConsumeUInt64() && !tokenizer.tryConsumeDouble() && !tokenizer.tryConsumeFloat()) {
                throw tokenizer.parseException("Invalid field value: " + tokenizer.currentToken);
            }
        }
    }

    public static String escapeBytes(ByteString input) {
        return TextFormatEscaper.escapeBytes(input);
    }

    public static String escapeBytes(byte[] input) {
        return TextFormatEscaper.escapeBytes(input);
    }

    public static ByteString unescapeBytes(CharSequence charString) throws InvalidEscapeSequenceException {
        int i;
        int pos;
        ByteString input = ByteString.copyFromUtf8(charString.toString());
        byte[] result = new byte[input.size()];
        int pos2 = 0;
        int i2 = 0;
        while (i < input.size()) {
            byte c = input.byteAt(i);
            if (c != 92) {
                result[pos2] = c;
                pos2++;
            } else if (i + 1 < input.size()) {
                i++;
                byte c2 = input.byteAt(i);
                if (isOctal(c2)) {
                    int code = digitValue(c2);
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        i++;
                        code = (code * 8) + digitValue(input.byteAt(i));
                    }
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        i++;
                        code = (code * 8) + digitValue(input.byteAt(i));
                    }
                    result[pos2] = (byte) code;
                    pos2++;
                } else {
                    switch (c2) {
                        case 34:
                            pos = pos2 + 1;
                            result[pos2] = Ev3Constants.Opcode.OR32;
                            break;
                        case 39:
                            result[pos2] = 39;
                            pos = pos2 + 1;
                            break;
                        case 92:
                            pos = pos2 + 1;
                            result[pos2] = Ev3Constants.Opcode.SELECT8;
                            break;
                        case 97:
                            pos = pos2 + 1;
                            result[pos2] = 7;
                            break;
                        case 98:
                            pos = pos2 + 1;
                            result[pos2] = 8;
                            break;
                        case 102:
                            pos = pos2 + 1;
                            result[pos2] = 12;
                            break;
                        case 110:
                            pos = pos2 + 1;
                            result[pos2] = 10;
                            break;
                        case 114:
                            pos = pos2 + 1;
                            result[pos2] = 13;
                            break;
                        case 116:
                            pos = pos2 + 1;
                            result[pos2] = 9;
                            break;
                        case 118:
                            pos = pos2 + 1;
                            result[pos2] = 11;
                            break;
                        case 120:
                            if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                i++;
                                int code2 = digitValue(input.byteAt(i));
                                if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                    i++;
                                    code2 = (code2 * 16) + digitValue(input.byteAt(i));
                                }
                                pos = pos2 + 1;
                                result[pos2] = (byte) code2;
                                break;
                            } else {
                                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\x' with no digits");
                            }
                        default:
                            throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\" + ((char) c2) + '\'');
                    }
                    pos2 = pos;
                }
            } else {
                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\' at end of string.");
            }
            i2 = i + 1;
        }
        if (result.length == pos2) {
            return ByteString.wrap(result);
        }
        return ByteString.copyFrom(result, 0, pos2);
    }

    public static class InvalidEscapeSequenceException extends IOException {
        private static final long serialVersionUID = -8164033650142593304L;

        InvalidEscapeSequenceException(String description) {
            super(description);
        }
    }

    static String escapeText(String input) {
        return escapeBytes(ByteString.copyFromUtf8(input));
    }

    public static String escapeDoubleQuotesAndBackslashes(String input) {
        return TextFormatEscaper.escapeDoubleQuotesAndBackslashes(input);
    }

    static String unescapeText(String input) throws InvalidEscapeSequenceException {
        return unescapeBytes(input).toStringUtf8();
    }

    private static boolean isOctal(byte c) {
        return 48 <= c && c <= 55;
    }

    private static boolean isHex(byte c) {
        return (48 <= c && c <= 57) || (97 <= c && c <= 102) || (65 <= c && c <= 70);
    }

    private static int digitValue(byte c) {
        if (48 <= c && c <= 57) {
            return c + Ev3Constants.Opcode.COM_READY;
        }
        if (97 > c || c > 122) {
            return (c - 65) + 10;
        }
        return (c - 97) + 10;
    }

    static int parseInt32(String text) throws NumberFormatException {
        return (int) parseInteger(text, true, false);
    }

    static int parseUInt32(String text) throws NumberFormatException {
        return (int) parseInteger(text, false, false);
    }

    static long parseInt64(String text) throws NumberFormatException {
        return parseInteger(text, true, true);
    }

    static long parseUInt64(String text) throws NumberFormatException {
        return parseInteger(text, false, true);
    }

    private static long parseInteger(String text, boolean isSigned, boolean isLong) throws NumberFormatException {
        int pos = 0;
        boolean negative = false;
        if (text.startsWith("-", 0)) {
            if (isSigned) {
                pos = 0 + 1;
                negative = true;
            } else {
                throw new NumberFormatException("Number must be positive: " + text);
            }
        }
        int radix = 10;
        if (text.startsWith("0x", pos)) {
            pos += 2;
            radix = 16;
        } else if (text.startsWith(Component.TYPEFACE_DEFAULT, pos)) {
            radix = 8;
        }
        String numberText = text.substring(pos);
        if (numberText.length() < 16) {
            long result = Long.parseLong(numberText, radix);
            if (negative) {
                result = -result;
            }
            if (isLong) {
                return result;
            }
            if (isSigned) {
                if (result <= 2147483647L && result >= -2147483648L) {
                    return result;
                }
                throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
            } else if (result < Declaration.TRANSIENT_ACCESS && result >= 0) {
                return result;
            } else {
                throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
            }
        } else {
            BigInteger bigValue = new BigInteger(numberText, radix);
            if (negative) {
                bigValue = bigValue.negate();
            }
            if (!isLong) {
                if (isSigned) {
                    if (bigValue.bitLength() > 31) {
                        throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
                    }
                } else if (bigValue.bitLength() > 32) {
                    throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
                }
            } else if (isSigned) {
                if (bigValue.bitLength() > 63) {
                    throw new NumberFormatException("Number out of range for 64-bit signed integer: " + text);
                }
            } else if (bigValue.bitLength() > 64) {
                throw new NumberFormatException("Number out of range for 64-bit unsigned integer: " + text);
            }
            return bigValue.longValue();
        }
    }
}
