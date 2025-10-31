package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import java.io.IOException;
import java.io.InputStream;

public final class StringValue extends GeneratedMessageV3 implements StringValueOrBuilder {
    private static final StringValue DEFAULT_INSTANCE = new StringValue();
    /* access modifiers changed from: private */
    public static final Parser<StringValue> PARSER = new AbstractParser<StringValue>() {
        public StringValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new StringValue(input, extensionRegistry);
        }
    };
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object value_;

    private StringValue(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private StringValue() {
        this.memoizedIsInitialized = -1;
        this.value_ = "";
    }

    public final UnknownFieldSet getUnknownFields() {
        return UnknownFieldSet.getDefaultInstance();
    }

    private StringValue(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        boolean done = false;
        while (!done) {
            try {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10:
                        this.value_ = input.readStringRequireUtf8();
                        break;
                    default:
                        if (input.skipField(tag)) {
                            break;
                        } else {
                            done = true;
                            break;
                        }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
            } catch (Throwable th) {
                makeExtensionsImmutable();
                throw th;
            }
        }
        makeExtensionsImmutable();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
    }

    /* access modifiers changed from: protected */
    public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable.ensureFieldAccessorsInitialized(StringValue.class, Builder.class);
    }

    public String getValue() {
        Object ref = this.value_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.value_ = s;
        return s;
    }

    public ByteString getValueBytes() {
        Object ref = this.value_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.value_ = b;
        return b;
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        if (!getValueBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.value_);
        }
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (!getValueBytes().isEmpty()) {
            size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.value_);
        }
        this.memoizedSize = size2;
        return size2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StringValue)) {
            return super.equals(obj);
        }
        StringValue other = (StringValue) obj;
        if (1 == 0 || !getValue().equals(other.getValue())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = (((((((41 * 19) + getDescriptorForType().hashCode()) * 37) + 1) * 53) + getValue().hashCode()) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static StringValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static StringValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static StringValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static StringValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static StringValue parseFrom(InputStream input) throws IOException {
        return (StringValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static StringValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (StringValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static StringValue parseDelimitedFrom(InputStream input) throws IOException {
        return (StringValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static StringValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (StringValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static StringValue parseFrom(CodedInputStream input) throws IOException {
        return (StringValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static StringValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (StringValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(StringValue prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
        if (this == DEFAULT_INSTANCE) {
            return new Builder();
        }
        return new Builder().mergeFrom(this);
    }

    /* access modifiers changed from: protected */
    public Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
        return new Builder(parent);
    }

    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements StringValueOrBuilder {
        private Object value_;

        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable.ensureFieldAccessorsInitialized(StringValue.class, Builder.class);
        }

        private Builder() {
            this.value_ = "";
            maybeForceBuilderInitialization();
        }

        private Builder(GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.value_ = "";
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            boolean z = GeneratedMessageV3.alwaysUseFieldBuilders;
        }

        public Builder clear() {
            super.clear();
            this.value_ = "";
            return this;
        }

        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
        }

        public StringValue getDefaultInstanceForType() {
            return StringValue.getDefaultInstance();
        }

        public StringValue build() {
            StringValue result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public StringValue buildPartial() {
            StringValue result = new StringValue((GeneratedMessageV3.Builder) this);
            Object unused = result.value_ = this.value_;
            onBuilt();
            return result;
        }

        public Builder clone() {
            return (Builder) super.clone();
        }

        public Builder setField(Descriptors.FieldDescriptor field, Object value) {
            return (Builder) super.setField(field, value);
        }

        public Builder clearField(Descriptors.FieldDescriptor field) {
            return (Builder) super.clearField(field);
        }

        public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
            return (Builder) super.clearOneof(oneof);
        }

        public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
            return (Builder) super.setRepeatedField(field, index, value);
        }

        public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
            return (Builder) super.addRepeatedField(field, value);
        }

        public Builder mergeFrom(Message other) {
            if (other instanceof StringValue) {
                return mergeFrom((StringValue) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(StringValue other) {
            if (other == StringValue.getDefaultInstance()) {
                return this;
            }
            if (!other.getValue().isEmpty()) {
                this.value_ = other.value_;
                onChanged();
            }
            onChanged();
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            try {
                StringValue parsedMessage = (StringValue) StringValue.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                StringValue parsedMessage2 = (StringValue) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (0 != 0) {
                    mergeFrom((StringValue) null);
                }
                throw th;
            }
        }

        public String getValue() {
            Object ref = this.value_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.value_ = s;
            return s;
        }

        public ByteString getValueBytes() {
            Object ref = this.value_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.value_ = b;
            return b;
        }

        public Builder setValue(String value) {
            if (value != null) {
                this.value_ = value;
                onChanged();
                return this;
            }
            throw new NullPointerException();
        }

        public Builder clearValue() {
            this.value_ = StringValue.getDefaultInstance().getValue();
            onChanged();
            return this;
        }

        public Builder setValueBytes(ByteString value) {
            if (value != null) {
                AbstractMessageLite.checkByteStringIsUtf8(value);
                this.value_ = value;
                onChanged();
                return this;
            }
            throw new NullPointerException();
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return this;
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return this;
        }
    }

    public static StringValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<StringValue> parser() {
        return PARSER;
    }

    public Parser<StringValue> getParserForType() {
        return PARSER;
    }

    public StringValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
