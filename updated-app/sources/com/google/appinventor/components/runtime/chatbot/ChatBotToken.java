package com.google.appinventor.components.runtime.chatbot;

import com.google.appinventor.components.common.ComponentConstants;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public final class ChatBotToken {
    /* access modifiers changed from: private */
    public static Descriptors.FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptors.Descriptor internal_static_request_descriptor = getDescriptor().getMessageTypes().get(2);
    /* access modifiers changed from: private */
    public static final GeneratedMessageV3.FieldAccessorTable internal_static_request_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_request_descriptor, new String[]{"Version", "Token", "Uuid", "Question", "System", "Apikey", "Provider", "Model", "Inputimage", "Doimage"});
    /* access modifiers changed from: private */
    public static final Descriptors.Descriptor internal_static_response_descriptor = getDescriptor().getMessageTypes().get(3);
    /* access modifiers changed from: private */
    public static final GeneratedMessageV3.FieldAccessorTable internal_static_response_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_response_descriptor, new String[]{"Version", "Status", "Uuid", "Answer", "Outputimage"});
    /* access modifiers changed from: private */
    public static final Descriptors.Descriptor internal_static_token_descriptor = getDescriptor().getMessageTypes().get(1);
    /* access modifiers changed from: private */
    public static final GeneratedMessageV3.FieldAccessorTable internal_static_token_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_token_descriptor, new String[]{"Version", "Keyid", "Generation", "Unsigned", "Signature"});
    /* access modifiers changed from: private */
    public static final Descriptors.Descriptor internal_static_unsigned_descriptor = getDescriptor().getMessageTypes().get(0);
    /* access modifiers changed from: private */
    public static final GeneratedMessageV3.FieldAccessorTable internal_static_unsigned_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_unsigned_descriptor, new String[]{"Huuid", "Version", "Generation"});

    public interface requestOrBuilder extends MessageOrBuilder {
        String getApikey();

        ByteString getApikeyBytes();

        boolean getDoimage();

        ByteString getInputimage();

        String getModel();

        ByteString getModelBytes();

        String getProvider();

        ByteString getProviderBytes();

        String getQuestion();

        ByteString getQuestionBytes();

        String getSystem();

        ByteString getSystemBytes();

        token getToken();

        tokenOrBuilder getTokenOrBuilder();

        String getUuid();

        ByteString getUuidBytes();

        long getVersion();

        boolean hasApikey();

        boolean hasDoimage();

        boolean hasInputimage();

        boolean hasModel();

        boolean hasProvider();

        boolean hasQuestion();

        boolean hasSystem();

        boolean hasToken();

        boolean hasUuid();

        boolean hasVersion();
    }

    public interface responseOrBuilder extends MessageOrBuilder {
        String getAnswer();

        ByteString getAnswerBytes();

        ByteString getOutputimage();

        long getStatus();

        String getUuid();

        ByteString getUuidBytes();

        long getVersion();

        boolean hasAnswer();

        boolean hasOutputimage();

        boolean hasStatus();

        boolean hasUuid();

        boolean hasVersion();
    }

    public interface tokenOrBuilder extends MessageOrBuilder {
        long getGeneration();

        long getKeyid();

        ByteString getSignature();

        ByteString getUnsigned();

        long getVersion();

        boolean hasGeneration();

        boolean hasKeyid();

        boolean hasSignature();

        boolean hasUnsigned();

        boolean hasVersion();
    }

    public interface unsignedOrBuilder extends MessageOrBuilder {
        long getGeneration();

        String getHuuid();

        ByteString getHuuidBytes();

        long getVersion();

        boolean hasGeneration();

        boolean hasHuuid();

        boolean hasVersion();
    }

    private ChatBotToken() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite) registry);
    }

    public static final class unsigned extends GeneratedMessageV3 implements unsignedOrBuilder {
        private static final unsigned DEFAULT_INSTANCE = new unsigned();
        public static final int GENERATION_FIELD_NUMBER = 3;
        public static final int HUUID_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<unsigned> PARSER = new AbstractParser<unsigned>() {
            public unsigned parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new unsigned(input, extensionRegistry);
            }
        };
        public static final int VERSION_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public long generation_;
        /* access modifiers changed from: private */
        public volatile Object huuid_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public long version_;

        private unsigned(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private unsigned() {
            this.memoizedIsInitialized = -1;
            this.huuid_ = "";
            this.version_ = 0;
            this.generation_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private unsigned(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.huuid_ = bs;
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.version_ = input.readUInt64();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.generation_ = input.readUInt64();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_unsigned_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_unsigned_fieldAccessorTable.ensureFieldAccessorsInitialized(unsigned.class, Builder.class);
        }

        public boolean hasHuuid() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getHuuid() {
            Object ref = this.huuid_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.huuid_ = s;
            }
            return s;
        }

        public ByteString getHuuidBytes() {
            Object ref = this.huuid_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.huuid_ = b;
            return b;
        }

        public boolean hasVersion() {
            return (this.bitField0_ & 2) == 2;
        }

        public long getVersion() {
            return this.version_;
        }

        public boolean hasGeneration() {
            return (this.bitField0_ & 4) == 4;
        }

        public long getGeneration() {
            return this.generation_;
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
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 1, this.huuid_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(2, this.version_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt64(3, this.generation_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.huuid_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeUInt64Size(2, this.version_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeUInt64Size(3, this.generation_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof unsigned)) {
                return super.equals(obj);
            }
            unsigned other = (unsigned) obj;
            boolean result = 1 != 0 && hasHuuid() == other.hasHuuid();
            if (hasHuuid()) {
                result = result && getHuuid().equals(other.getHuuid());
            }
            boolean result2 = result && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result2 = result2 && getVersion() == other.getVersion();
            }
            boolean result3 = result2 && hasGeneration() == other.hasGeneration();
            if (hasGeneration()) {
                result3 = result3 && getGeneration() == other.getGeneration();
            }
            if (!result3 || !this.unknownFields.equals(other.unknownFields)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = (41 * 19) + getDescriptorForType().hashCode();
            if (hasHuuid() != 0) {
                hash = (((hash * 37) + 1) * 53) + getHuuid().hashCode();
            }
            if (hasVersion() != 0) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashLong(getVersion());
            }
            if (hasGeneration() != 0) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashLong(getGeneration());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static unsigned parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static unsigned parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static unsigned parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static unsigned parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static unsigned parseFrom(InputStream input) throws IOException {
            return (unsigned) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static unsigned parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (unsigned) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static unsigned parseDelimitedFrom(InputStream input) throws IOException {
            return (unsigned) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static unsigned parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (unsigned) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static unsigned parseFrom(CodedInputStream input) throws IOException {
            return (unsigned) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static unsigned parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (unsigned) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(unsigned prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            return new Builder(parent);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements unsignedOrBuilder {
            private int bitField0_;
            private long generation_;
            private Object huuid_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_unsigned_descriptor;
            }

            /* access modifiers changed from: protected */
            public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_unsigned_fieldAccessorTable.ensureFieldAccessorsInitialized(unsigned.class, Builder.class);
            }

            private Builder() {
                this.huuid_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.huuid_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = unsigned.alwaysUseFieldBuilders;
            }

            public Builder clear() {
                super.clear();
                this.huuid_ = "";
                this.bitField0_ &= -2;
                this.version_ = 0;
                this.bitField0_ &= -3;
                this.generation_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_unsigned_descriptor;
            }

            public unsigned getDefaultInstanceForType() {
                return unsigned.getDefaultInstance();
            }

            public unsigned build() {
                unsigned result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public unsigned buildPartial() {
                unsigned result = new unsigned((GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.huuid_ = this.huuid_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.generation_ = this.generation_;
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof unsigned) {
                    return mergeFrom((unsigned) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(unsigned other) {
                if (other == unsigned.getDefaultInstance()) {
                    return this;
                }
                if (other.hasHuuid()) {
                    this.bitField0_ |= 1;
                    this.huuid_ = other.huuid_;
                    onChanged();
                }
                if (other.hasVersion()) {
                    setVersion(other.getVersion());
                }
                if (other.hasGeneration()) {
                    setGeneration(other.getGeneration());
                }
                mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    unsigned parsedMessage = unsigned.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    unsigned parsedMessage2 = (unsigned) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((unsigned) null);
                    }
                    throw th;
                }
            }

            public boolean hasHuuid() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getHuuid() {
                Object ref = this.huuid_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.huuid_ = s;
                }
                return s;
            }

            public ByteString getHuuidBytes() {
                Object ref = this.huuid_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.huuid_ = b;
                return b;
            }

            public Builder setHuuid(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.huuid_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearHuuid() {
                this.bitField0_ &= -2;
                this.huuid_ = unsigned.getDefaultInstance().getHuuid();
                onChanged();
                return this;
            }

            public Builder setHuuidBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.huuid_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasVersion() {
                return (this.bitField0_ & 2) == 2;
            }

            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 2;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -3;
                this.version_ = 0;
                onChanged();
                return this;
            }

            public boolean hasGeneration() {
                return (this.bitField0_ & 4) == 4;
            }

            public long getGeneration() {
                return this.generation_;
            }

            public Builder setGeneration(long value) {
                this.bitField0_ |= 4;
                this.generation_ = value;
                onChanged();
                return this;
            }

            public Builder clearGeneration() {
                this.bitField0_ &= -5;
                this.generation_ = 0;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static unsigned getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<unsigned> parser() {
            return PARSER;
        }

        public Parser<unsigned> getParserForType() {
            return PARSER;
        }

        public unsigned getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class token extends GeneratedMessageV3 implements tokenOrBuilder {
        private static final token DEFAULT_INSTANCE = new token();
        public static final int GENERATION_FIELD_NUMBER = 3;
        public static final int KEYID_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<token> PARSER = new AbstractParser<token>() {
            public token parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new token(input, extensionRegistry);
            }
        };
        public static final int SIGNATURE_FIELD_NUMBER = 5;
        public static final int UNSIGNED_FIELD_NUMBER = 4;
        public static final int VERSION_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public long generation_;
        /* access modifiers changed from: private */
        public long keyid_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public ByteString signature_;
        /* access modifiers changed from: private */
        public ByteString unsigned_;
        /* access modifiers changed from: private */
        public long version_;

        private token(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private token() {
            this.memoizedIsInitialized = -1;
            this.version_ = 1;
            this.keyid_ = 1;
            this.generation_ = 0;
            this.unsigned_ = ByteString.EMPTY;
            this.signature_ = ByteString.EMPTY;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private token(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            this.bitField0_ |= 1;
                            this.version_ = input.readUInt64();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.keyid_ = input.readUInt64();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.generation_ = input.readUInt64();
                            break;
                        case 34:
                            this.bitField0_ |= 8;
                            this.unsigned_ = input.readBytes();
                            break;
                        case 42:
                            this.bitField0_ |= 16;
                            this.signature_ = input.readBytes();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_token_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_token_fieldAccessorTable.ensureFieldAccessorsInitialized(token.class, Builder.class);
        }

        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        public long getVersion() {
            return this.version_;
        }

        public boolean hasKeyid() {
            return (this.bitField0_ & 2) == 2;
        }

        public long getKeyid() {
            return this.keyid_;
        }

        public boolean hasGeneration() {
            return (this.bitField0_ & 4) == 4;
        }

        public long getGeneration() {
            return this.generation_;
        }

        public boolean hasUnsigned() {
            return (this.bitField0_ & 8) == 8;
        }

        public ByteString getUnsigned() {
            return this.unsigned_;
        }

        public boolean hasSignature() {
            return (this.bitField0_ & 16) == 16;
        }

        public ByteString getSignature() {
            return this.signature_;
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
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(2, this.keyid_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt64(3, this.generation_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBytes(4, this.unsigned_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(5, this.signature_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeUInt64Size(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeUInt64Size(2, this.keyid_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeUInt64Size(3, this.generation_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBytesSize(4, this.unsigned_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBytesSize(5, this.signature_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof token)) {
                return super.equals(obj);
            }
            token other = (token) obj;
            boolean result = 1 != 0 && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result = result && getVersion() == other.getVersion();
            }
            boolean result2 = result && hasKeyid() == other.hasKeyid();
            if (hasKeyid()) {
                result2 = result2 && getKeyid() == other.getKeyid();
            }
            boolean result3 = result2 && hasGeneration() == other.hasGeneration();
            if (hasGeneration()) {
                result3 = result3 && getGeneration() == other.getGeneration();
            }
            boolean result4 = result3 && hasUnsigned() == other.hasUnsigned();
            if (hasUnsigned()) {
                result4 = result4 && getUnsigned().equals(other.getUnsigned());
            }
            boolean result5 = result4 && hasSignature() == other.hasSignature();
            if (hasSignature()) {
                result5 = result5 && getSignature().equals(other.getSignature());
            }
            if (!result5 || !this.unknownFields.equals(other.unknownFields)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = (41 * 19) + getDescriptorForType().hashCode();
            if (hasVersion() != 0) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashLong(getVersion());
            }
            if (hasKeyid() != 0) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashLong(getKeyid());
            }
            if (hasGeneration() != 0) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashLong(getGeneration());
            }
            if (hasUnsigned() != 0) {
                hash = (((hash * 37) + 4) * 53) + getUnsigned().hashCode();
            }
            if (hasSignature() != 0) {
                hash = (((hash * 37) + 5) * 53) + getSignature().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static token parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static token parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static token parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static token parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static token parseFrom(InputStream input) throws IOException {
            return (token) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static token parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (token) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static token parseDelimitedFrom(InputStream input) throws IOException {
            return (token) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static token parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (token) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static token parseFrom(CodedInputStream input) throws IOException {
            return (token) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static token parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (token) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(token prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            return new Builder(parent);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements tokenOrBuilder {
            private int bitField0_;
            private long generation_;
            private long keyid_;
            private ByteString signature_;
            private ByteString unsigned_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_token_descriptor;
            }

            /* access modifiers changed from: protected */
            public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_token_fieldAccessorTable.ensureFieldAccessorsInitialized(token.class, Builder.class);
            }

            private Builder() {
                this.version_ = 1;
                this.keyid_ = 1;
                this.unsigned_ = ByteString.EMPTY;
                this.signature_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.version_ = 1;
                this.keyid_ = 1;
                this.unsigned_ = ByteString.EMPTY;
                this.signature_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = token.alwaysUseFieldBuilders;
            }

            public Builder clear() {
                super.clear();
                this.version_ = 1;
                this.bitField0_ &= -2;
                this.keyid_ = 1;
                this.bitField0_ &= -3;
                this.generation_ = 0;
                this.bitField0_ &= -5;
                this.unsigned_ = ByteString.EMPTY;
                this.bitField0_ &= -9;
                this.signature_ = ByteString.EMPTY;
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_token_descriptor;
            }

            public token getDefaultInstanceForType() {
                return token.getDefaultInstance();
            }

            public token build() {
                token result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public token buildPartial() {
                token result = new token((GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.keyid_ = this.keyid_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.generation_ = this.generation_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.unsigned_ = this.unsigned_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.signature_ = this.signature_;
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof token) {
                    return mergeFrom((token) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(token other) {
                if (other == token.getDefaultInstance()) {
                    return this;
                }
                if (other.hasVersion()) {
                    setVersion(other.getVersion());
                }
                if (other.hasKeyid()) {
                    setKeyid(other.getKeyid());
                }
                if (other.hasGeneration()) {
                    setGeneration(other.getGeneration());
                }
                if (other.hasUnsigned()) {
                    setUnsigned(other.getUnsigned());
                }
                if (other.hasSignature()) {
                    setSignature(other.getSignature());
                }
                mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    token parsedMessage = token.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    token parsedMessage2 = (token) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((token) null);
                    }
                    throw th;
                }
            }

            public boolean hasVersion() {
                return (this.bitField0_ & 1) == 1;
            }

            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 1;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -2;
                this.version_ = 1;
                onChanged();
                return this;
            }

            public boolean hasKeyid() {
                return (this.bitField0_ & 2) == 2;
            }

            public long getKeyid() {
                return this.keyid_;
            }

            public Builder setKeyid(long value) {
                this.bitField0_ |= 2;
                this.keyid_ = value;
                onChanged();
                return this;
            }

            public Builder clearKeyid() {
                this.bitField0_ &= -3;
                this.keyid_ = 1;
                onChanged();
                return this;
            }

            public boolean hasGeneration() {
                return (this.bitField0_ & 4) == 4;
            }

            public long getGeneration() {
                return this.generation_;
            }

            public Builder setGeneration(long value) {
                this.bitField0_ |= 4;
                this.generation_ = value;
                onChanged();
                return this;
            }

            public Builder clearGeneration() {
                this.bitField0_ &= -5;
                this.generation_ = 0;
                onChanged();
                return this;
            }

            public boolean hasUnsigned() {
                return (this.bitField0_ & 8) == 8;
            }

            public ByteString getUnsigned() {
                return this.unsigned_;
            }

            public Builder setUnsigned(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 8;
                    this.unsigned_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearUnsigned() {
                this.bitField0_ &= -9;
                this.unsigned_ = token.getDefaultInstance().getUnsigned();
                onChanged();
                return this;
            }

            public boolean hasSignature() {
                return (this.bitField0_ & 16) == 16;
            }

            public ByteString getSignature() {
                return this.signature_;
            }

            public Builder setSignature(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 16;
                    this.signature_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearSignature() {
                this.bitField0_ &= -17;
                this.signature_ = token.getDefaultInstance().getSignature();
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static token getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<token> parser() {
            return PARSER;
        }

        public Parser<token> getParserForType() {
            return PARSER;
        }

        public token getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class request extends GeneratedMessageV3 implements requestOrBuilder {
        public static final int APIKEY_FIELD_NUMBER = 6;
        private static final request DEFAULT_INSTANCE = new request();
        public static final int DOIMAGE_FIELD_NUMBER = 20;
        public static final int INPUTIMAGE_FIELD_NUMBER = 9;
        public static final int MODEL_FIELD_NUMBER = 8;
        @Deprecated
        public static final Parser<request> PARSER = new AbstractParser<request>() {
            public request parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new request(input, extensionRegistry);
            }
        };
        public static final int PROVIDER_FIELD_NUMBER = 7;
        public static final int QUESTION_FIELD_NUMBER = 4;
        public static final int SYSTEM_FIELD_NUMBER = 5;
        public static final int TOKEN_FIELD_NUMBER = 2;
        public static final int UUID_FIELD_NUMBER = 3;
        public static final int VERSION_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public volatile Object apikey_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public boolean doimage_;
        /* access modifiers changed from: private */
        public ByteString inputimage_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public volatile Object model_;
        /* access modifiers changed from: private */
        public volatile Object provider_;
        /* access modifiers changed from: private */
        public volatile Object question_;
        /* access modifiers changed from: private */
        public volatile Object system_;
        /* access modifiers changed from: private */
        public token token_;
        /* access modifiers changed from: private */
        public volatile Object uuid_;
        /* access modifiers changed from: private */
        public long version_;

        private request(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private request() {
            this.memoizedIsInitialized = -1;
            this.version_ = 1;
            this.uuid_ = "";
            this.question_ = "";
            this.system_ = "";
            this.apikey_ = "";
            this.provider_ = "chatgpt";
            this.model_ = "";
            this.inputimage_ = ByteString.EMPTY;
            this.doimage_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private request(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            this.bitField0_ |= 1;
                            this.version_ = input.readUInt64();
                            break;
                        case 18:
                            token.Builder subBuilder = (this.bitField0_ & 2) == 2 ? this.token_.toBuilder() : null;
                            this.token_ = (token) input.readMessage(token.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.token_);
                                this.token_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 26:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 4;
                            this.uuid_ = bs;
                            break;
                        case 34:
                            ByteString bs2 = input.readBytes();
                            this.bitField0_ |= 8;
                            this.question_ = bs2;
                            break;
                        case 42:
                            ByteString bs3 = input.readBytes();
                            this.bitField0_ |= 16;
                            this.system_ = bs3;
                            break;
                        case 50:
                            ByteString bs4 = input.readBytes();
                            this.bitField0_ |= 32;
                            this.apikey_ = bs4;
                            break;
                        case 58:
                            ByteString bs5 = input.readBytes();
                            this.bitField0_ |= 64;
                            this.provider_ = bs5;
                            break;
                        case 66:
                            ByteString bs6 = input.readBytes();
                            this.bitField0_ |= 128;
                            this.model_ = bs6;
                            break;
                        case 74:
                            this.bitField0_ |= 256;
                            this.inputimage_ = input.readBytes();
                            break;
                        case ComponentConstants.TEXTBOX_PREFERRED_WIDTH:
                            this.bitField0_ |= 512;
                            this.doimage_ = input.readBool();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_request_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_request_fieldAccessorTable.ensureFieldAccessorsInitialized(request.class, Builder.class);
        }

        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        public long getVersion() {
            return this.version_;
        }

        public boolean hasToken() {
            return (this.bitField0_ & 2) == 2;
        }

        public token getToken() {
            return this.token_ == null ? token.getDefaultInstance() : this.token_;
        }

        public tokenOrBuilder getTokenOrBuilder() {
            return this.token_ == null ? token.getDefaultInstance() : this.token_;
        }

        public boolean hasUuid() {
            return (this.bitField0_ & 4) == 4;
        }

        public String getUuid() {
            Object ref = this.uuid_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.uuid_ = s;
            }
            return s;
        }

        public ByteString getUuidBytes() {
            Object ref = this.uuid_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.uuid_ = b;
            return b;
        }

        public boolean hasQuestion() {
            return (this.bitField0_ & 8) == 8;
        }

        public String getQuestion() {
            Object ref = this.question_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.question_ = s;
            }
            return s;
        }

        public ByteString getQuestionBytes() {
            Object ref = this.question_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.question_ = b;
            return b;
        }

        public boolean hasSystem() {
            return (this.bitField0_ & 16) == 16;
        }

        public String getSystem() {
            Object ref = this.system_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.system_ = s;
            }
            return s;
        }

        public ByteString getSystemBytes() {
            Object ref = this.system_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.system_ = b;
            return b;
        }

        public boolean hasApikey() {
            return (this.bitField0_ & 32) == 32;
        }

        public String getApikey() {
            Object ref = this.apikey_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.apikey_ = s;
            }
            return s;
        }

        public ByteString getApikeyBytes() {
            Object ref = this.apikey_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.apikey_ = b;
            return b;
        }

        public boolean hasProvider() {
            return (this.bitField0_ & 64) == 64;
        }

        public String getProvider() {
            Object ref = this.provider_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.provider_ = s;
            }
            return s;
        }

        public ByteString getProviderBytes() {
            Object ref = this.provider_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.provider_ = b;
            return b;
        }

        public boolean hasModel() {
            return (this.bitField0_ & 128) == 128;
        }

        public String getModel() {
            Object ref = this.model_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.model_ = s;
            }
            return s;
        }

        public ByteString getModelBytes() {
            Object ref = this.model_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.model_ = b;
            return b;
        }

        public boolean hasInputimage() {
            return (this.bitField0_ & 256) == 256;
        }

        public ByteString getInputimage() {
            return this.inputimage_;
        }

        public boolean hasDoimage() {
            return (this.bitField0_ & 512) == 512;
        }

        public boolean getDoimage() {
            return this.doimage_;
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
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getToken());
            }
            if ((this.bitField0_ & 4) == 4) {
                GeneratedMessageV3.writeString(output, 3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                GeneratedMessageV3.writeString(output, 4, this.question_);
            }
            if ((this.bitField0_ & 16) == 16) {
                GeneratedMessageV3.writeString(output, 5, this.system_);
            }
            if ((this.bitField0_ & 32) == 32) {
                GeneratedMessageV3.writeString(output, 6, this.apikey_);
            }
            if ((this.bitField0_ & 64) == 64) {
                GeneratedMessageV3.writeString(output, 7, this.provider_);
            }
            if ((this.bitField0_ & 128) == 128) {
                GeneratedMessageV3.writeString(output, 8, this.model_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeBytes(9, this.inputimage_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeBool(20, this.doimage_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeUInt64Size(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, getToken());
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += GeneratedMessageV3.computeStringSize(3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += GeneratedMessageV3.computeStringSize(4, this.question_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += GeneratedMessageV3.computeStringSize(5, this.system_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += GeneratedMessageV3.computeStringSize(6, this.apikey_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += GeneratedMessageV3.computeStringSize(7, this.provider_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += GeneratedMessageV3.computeStringSize(8, this.model_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size2 += CodedOutputStream.computeBytesSize(9, this.inputimage_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size2 += CodedOutputStream.computeBoolSize(20, this.doimage_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof request)) {
                return super.equals(obj);
            }
            request other = (request) obj;
            boolean result = 1 != 0 && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result = result && getVersion() == other.getVersion();
            }
            boolean result2 = result && hasToken() == other.hasToken();
            if (hasToken()) {
                result2 = result2 && getToken().equals(other.getToken());
            }
            boolean result3 = result2 && hasUuid() == other.hasUuid();
            if (hasUuid()) {
                result3 = result3 && getUuid().equals(other.getUuid());
            }
            boolean result4 = result3 && hasQuestion() == other.hasQuestion();
            if (hasQuestion()) {
                result4 = result4 && getQuestion().equals(other.getQuestion());
            }
            boolean result5 = result4 && hasSystem() == other.hasSystem();
            if (hasSystem()) {
                result5 = result5 && getSystem().equals(other.getSystem());
            }
            boolean result6 = result5 && hasApikey() == other.hasApikey();
            if (hasApikey()) {
                result6 = result6 && getApikey().equals(other.getApikey());
            }
            boolean result7 = result6 && hasProvider() == other.hasProvider();
            if (hasProvider()) {
                result7 = result7 && getProvider().equals(other.getProvider());
            }
            boolean result8 = result7 && hasModel() == other.hasModel();
            if (hasModel()) {
                result8 = result8 && getModel().equals(other.getModel());
            }
            boolean result9 = result8 && hasInputimage() == other.hasInputimage();
            if (hasInputimage()) {
                result9 = result9 && getInputimage().equals(other.getInputimage());
            }
            boolean result10 = result9 && hasDoimage() == other.hasDoimage();
            if (hasDoimage()) {
                result10 = result10 && getDoimage() == other.getDoimage();
            }
            if (!result10 || !this.unknownFields.equals(other.unknownFields)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = (41 * 19) + getDescriptorForType().hashCode();
            if (hasVersion() != 0) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashLong(getVersion());
            }
            if (hasToken() != 0) {
                hash = (((hash * 37) + 2) * 53) + getToken().hashCode();
            }
            if (hasUuid() != 0) {
                hash = (((hash * 37) + 3) * 53) + getUuid().hashCode();
            }
            if (hasQuestion() != 0) {
                hash = (((hash * 37) + 4) * 53) + getQuestion().hashCode();
            }
            if (hasSystem() != 0) {
                hash = (((hash * 37) + 5) * 53) + getSystem().hashCode();
            }
            if (hasApikey() != 0) {
                hash = (((hash * 37) + 6) * 53) + getApikey().hashCode();
            }
            if (hasProvider() != 0) {
                hash = (((hash * 37) + 7) * 53) + getProvider().hashCode();
            }
            if (hasModel() != 0) {
                hash = (((hash * 37) + 8) * 53) + getModel().hashCode();
            }
            if (hasInputimage() != 0) {
                hash = (((hash * 37) + 9) * 53) + getInputimage().hashCode();
            }
            if (hasDoimage() != 0) {
                hash = (((hash * 37) + 20) * 53) + Internal.hashBoolean(getDoimage());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static request parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static request parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static request parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static request parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static request parseFrom(InputStream input) throws IOException {
            return (request) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static request parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (request) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static request parseDelimitedFrom(InputStream input) throws IOException {
            return (request) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static request parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (request) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static request parseFrom(CodedInputStream input) throws IOException {
            return (request) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static request parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (request) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(request prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            return new Builder(parent);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements requestOrBuilder {
            private Object apikey_;
            private int bitField0_;
            private boolean doimage_;
            private ByteString inputimage_;
            private Object model_;
            private Object provider_;
            private Object question_;
            private Object system_;
            private SingleFieldBuilderV3<token, token.Builder, tokenOrBuilder> tokenBuilder_;
            private token token_;
            private Object uuid_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_request_descriptor;
            }

            /* access modifiers changed from: protected */
            public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_request_fieldAccessorTable.ensureFieldAccessorsInitialized(request.class, Builder.class);
            }

            private Builder() {
                this.version_ = 1;
                this.token_ = null;
                this.uuid_ = "";
                this.question_ = "";
                this.system_ = "";
                this.apikey_ = "";
                this.provider_ = "chatgpt";
                this.model_ = "";
                this.inputimage_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.version_ = 1;
                this.token_ = null;
                this.uuid_ = "";
                this.question_ = "";
                this.system_ = "";
                this.apikey_ = "";
                this.provider_ = "chatgpt";
                this.model_ = "";
                this.inputimage_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (request.alwaysUseFieldBuilders) {
                    getTokenFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.version_ = 1;
                this.bitField0_ &= -2;
                if (this.tokenBuilder_ == null) {
                    this.token_ = null;
                } else {
                    this.tokenBuilder_.clear();
                }
                this.bitField0_ &= -3;
                this.uuid_ = "";
                this.bitField0_ &= -5;
                this.question_ = "";
                this.bitField0_ &= -9;
                this.system_ = "";
                this.bitField0_ &= -17;
                this.apikey_ = "";
                this.bitField0_ &= -33;
                this.provider_ = "chatgpt";
                this.bitField0_ &= -65;
                this.model_ = "";
                this.bitField0_ &= -129;
                this.inputimage_ = ByteString.EMPTY;
                this.bitField0_ &= -257;
                this.doimage_ = false;
                this.bitField0_ &= -513;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_request_descriptor;
            }

            public request getDefaultInstanceForType() {
                return request.getDefaultInstance();
            }

            public request build() {
                request result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public request buildPartial() {
                request result = new request((GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.tokenBuilder_ == null) {
                    result.token_ = this.token_;
                } else {
                    result.token_ = this.tokenBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.uuid_ = this.uuid_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.question_ = this.question_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.system_ = this.system_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.apikey_ = this.apikey_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.provider_ = this.provider_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.model_ = this.model_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                result.inputimage_ = this.inputimage_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                result.doimage_ = this.doimage_;
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof request) {
                    return mergeFrom((request) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(request other) {
                if (other == request.getDefaultInstance()) {
                    return this;
                }
                if (other.hasVersion()) {
                    setVersion(other.getVersion());
                }
                if (other.hasToken()) {
                    mergeToken(other.getToken());
                }
                if (other.hasUuid()) {
                    this.bitField0_ |= 4;
                    this.uuid_ = other.uuid_;
                    onChanged();
                }
                if (other.hasQuestion()) {
                    this.bitField0_ |= 8;
                    this.question_ = other.question_;
                    onChanged();
                }
                if (other.hasSystem()) {
                    this.bitField0_ |= 16;
                    this.system_ = other.system_;
                    onChanged();
                }
                if (other.hasApikey()) {
                    this.bitField0_ |= 32;
                    this.apikey_ = other.apikey_;
                    onChanged();
                }
                if (other.hasProvider()) {
                    this.bitField0_ |= 64;
                    this.provider_ = other.provider_;
                    onChanged();
                }
                if (other.hasModel()) {
                    this.bitField0_ |= 128;
                    this.model_ = other.model_;
                    onChanged();
                }
                if (other.hasInputimage()) {
                    setInputimage(other.getInputimage());
                }
                if (other.hasDoimage()) {
                    setDoimage(other.getDoimage());
                }
                mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    request parsedMessage = request.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    request parsedMessage2 = (request) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((request) null);
                    }
                    throw th;
                }
            }

            public boolean hasVersion() {
                return (this.bitField0_ & 1) == 1;
            }

            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 1;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -2;
                this.version_ = 1;
                onChanged();
                return this;
            }

            public boolean hasToken() {
                return (this.bitField0_ & 2) == 2;
            }

            public token getToken() {
                if (this.tokenBuilder_ == null) {
                    return this.token_ == null ? token.getDefaultInstance() : this.token_;
                }
                return this.tokenBuilder_.getMessage();
            }

            public Builder setToken(token value) {
                if (this.tokenBuilder_ != null) {
                    this.tokenBuilder_.setMessage(value);
                } else if (value != null) {
                    this.token_ = value;
                    onChanged();
                } else {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setToken(token.Builder builderForValue) {
                if (this.tokenBuilder_ == null) {
                    this.token_ = builderForValue.build();
                    onChanged();
                } else {
                    this.tokenBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeToken(token value) {
                if (this.tokenBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.token_ == null || this.token_ == token.getDefaultInstance()) {
                        this.token_ = value;
                    } else {
                        this.token_ = token.newBuilder(this.token_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.tokenBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearToken() {
                if (this.tokenBuilder_ == null) {
                    this.token_ = null;
                    onChanged();
                } else {
                    this.tokenBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public token.Builder getTokenBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return getTokenFieldBuilder().getBuilder();
            }

            public tokenOrBuilder getTokenOrBuilder() {
                if (this.tokenBuilder_ != null) {
                    return this.tokenBuilder_.getMessageOrBuilder();
                }
                return this.token_ == null ? token.getDefaultInstance() : this.token_;
            }

            private SingleFieldBuilderV3<token, token.Builder, tokenOrBuilder> getTokenFieldBuilder() {
                if (this.tokenBuilder_ == null) {
                    this.tokenBuilder_ = new SingleFieldBuilderV3<>(getToken(), getParentForChildren(), isClean());
                    this.token_ = null;
                }
                return this.tokenBuilder_;
            }

            public boolean hasUuid() {
                return (this.bitField0_ & 4) == 4;
            }

            public String getUuid() {
                Object ref = this.uuid_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.uuid_ = s;
                }
                return s;
            }

            public ByteString getUuidBytes() {
                Object ref = this.uuid_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.uuid_ = b;
                return b;
            }

            public Builder setUuid(String value) {
                if (value != null) {
                    this.bitField0_ |= 4;
                    this.uuid_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearUuid() {
                this.bitField0_ &= -5;
                this.uuid_ = request.getDefaultInstance().getUuid();
                onChanged();
                return this;
            }

            public Builder setUuidBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 4;
                    this.uuid_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasQuestion() {
                return (this.bitField0_ & 8) == 8;
            }

            public String getQuestion() {
                Object ref = this.question_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.question_ = s;
                }
                return s;
            }

            public ByteString getQuestionBytes() {
                Object ref = this.question_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.question_ = b;
                return b;
            }

            public Builder setQuestion(String value) {
                if (value != null) {
                    this.bitField0_ |= 8;
                    this.question_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearQuestion() {
                this.bitField0_ &= -9;
                this.question_ = request.getDefaultInstance().getQuestion();
                onChanged();
                return this;
            }

            public Builder setQuestionBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 8;
                    this.question_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasSystem() {
                return (this.bitField0_ & 16) == 16;
            }

            public String getSystem() {
                Object ref = this.system_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.system_ = s;
                }
                return s;
            }

            public ByteString getSystemBytes() {
                Object ref = this.system_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.system_ = b;
                return b;
            }

            public Builder setSystem(String value) {
                if (value != null) {
                    this.bitField0_ |= 16;
                    this.system_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearSystem() {
                this.bitField0_ &= -17;
                this.system_ = request.getDefaultInstance().getSystem();
                onChanged();
                return this;
            }

            public Builder setSystemBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 16;
                    this.system_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasApikey() {
                return (this.bitField0_ & 32) == 32;
            }

            public String getApikey() {
                Object ref = this.apikey_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.apikey_ = s;
                }
                return s;
            }

            public ByteString getApikeyBytes() {
                Object ref = this.apikey_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.apikey_ = b;
                return b;
            }

            public Builder setApikey(String value) {
                if (value != null) {
                    this.bitField0_ |= 32;
                    this.apikey_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearApikey() {
                this.bitField0_ &= -33;
                this.apikey_ = request.getDefaultInstance().getApikey();
                onChanged();
                return this;
            }

            public Builder setApikeyBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 32;
                    this.apikey_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasProvider() {
                return (this.bitField0_ & 64) == 64;
            }

            public String getProvider() {
                Object ref = this.provider_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.provider_ = s;
                }
                return s;
            }

            public ByteString getProviderBytes() {
                Object ref = this.provider_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.provider_ = b;
                return b;
            }

            public Builder setProvider(String value) {
                if (value != null) {
                    this.bitField0_ |= 64;
                    this.provider_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearProvider() {
                this.bitField0_ &= -65;
                this.provider_ = request.getDefaultInstance().getProvider();
                onChanged();
                return this;
            }

            public Builder setProviderBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 64;
                    this.provider_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasModel() {
                return (this.bitField0_ & 128) == 128;
            }

            public String getModel() {
                Object ref = this.model_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.model_ = s;
                }
                return s;
            }

            public ByteString getModelBytes() {
                Object ref = this.model_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.model_ = b;
                return b;
            }

            public Builder setModel(String value) {
                if (value != null) {
                    this.bitField0_ |= 128;
                    this.model_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearModel() {
                this.bitField0_ &= -129;
                this.model_ = request.getDefaultInstance().getModel();
                onChanged();
                return this;
            }

            public Builder setModelBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 128;
                    this.model_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasInputimage() {
                return (this.bitField0_ & 256) == 256;
            }

            public ByteString getInputimage() {
                return this.inputimage_;
            }

            public Builder setInputimage(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 256;
                    this.inputimage_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearInputimage() {
                this.bitField0_ &= -257;
                this.inputimage_ = request.getDefaultInstance().getInputimage();
                onChanged();
                return this;
            }

            public boolean hasDoimage() {
                return (this.bitField0_ & 512) == 512;
            }

            public boolean getDoimage() {
                return this.doimage_;
            }

            public Builder setDoimage(boolean value) {
                this.bitField0_ |= 512;
                this.doimage_ = value;
                onChanged();
                return this;
            }

            public Builder clearDoimage() {
                this.bitField0_ &= -513;
                this.doimage_ = false;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static request getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<request> parser() {
            return PARSER;
        }

        public Parser<request> getParserForType() {
            return PARSER;
        }

        public request getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class response extends GeneratedMessageV3 implements responseOrBuilder {
        public static final int ANSWER_FIELD_NUMBER = 4;
        private static final response DEFAULT_INSTANCE = new response();
        public static final int OUTPUTIMAGE_FIELD_NUMBER = 5;
        @Deprecated
        public static final Parser<response> PARSER = new AbstractParser<response>() {
            public response parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new response(input, extensionRegistry);
            }
        };
        public static final int STATUS_FIELD_NUMBER = 2;
        public static final int UUID_FIELD_NUMBER = 3;
        public static final int VERSION_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public volatile Object answer_;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public ByteString outputimage_;
        /* access modifiers changed from: private */
        public long status_;
        /* access modifiers changed from: private */
        public volatile Object uuid_;
        /* access modifiers changed from: private */
        public long version_;

        private response(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private response() {
            this.memoizedIsInitialized = -1;
            this.version_ = 1;
            this.status_ = 0;
            this.uuid_ = "";
            this.answer_ = "";
            this.outputimage_ = ByteString.EMPTY;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private response(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            this.bitField0_ |= 1;
                            this.version_ = input.readUInt64();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.status_ = input.readUInt64();
                            break;
                        case 26:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 4;
                            this.uuid_ = bs;
                            break;
                        case 34:
                            ByteString bs2 = input.readBytes();
                            this.bitField0_ |= 8;
                            this.answer_ = bs2;
                            break;
                        case 42:
                            this.bitField0_ |= 16;
                            this.outputimage_ = input.readBytes();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_response_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_response_fieldAccessorTable.ensureFieldAccessorsInitialized(response.class, Builder.class);
        }

        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        public long getVersion() {
            return this.version_;
        }

        public boolean hasStatus() {
            return (this.bitField0_ & 2) == 2;
        }

        public long getStatus() {
            return this.status_;
        }

        public boolean hasUuid() {
            return (this.bitField0_ & 4) == 4;
        }

        public String getUuid() {
            Object ref = this.uuid_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.uuid_ = s;
            }
            return s;
        }

        public ByteString getUuidBytes() {
            Object ref = this.uuid_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.uuid_ = b;
            return b;
        }

        public boolean hasAnswer() {
            return (this.bitField0_ & 8) == 8;
        }

        public String getAnswer() {
            Object ref = this.answer_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.answer_ = s;
            }
            return s;
        }

        public ByteString getAnswerBytes() {
            Object ref = this.answer_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.answer_ = b;
            return b;
        }

        public boolean hasOutputimage() {
            return (this.bitField0_ & 16) == 16;
        }

        public ByteString getOutputimage() {
            return this.outputimage_;
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
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                GeneratedMessageV3.writeString(output, 3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                GeneratedMessageV3.writeString(output, 4, this.answer_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(5, this.outputimage_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeUInt64Size(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeUInt64Size(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += GeneratedMessageV3.computeStringSize(3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += GeneratedMessageV3.computeStringSize(4, this.answer_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBytesSize(5, this.outputimage_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof response)) {
                return super.equals(obj);
            }
            response other = (response) obj;
            boolean result = 1 != 0 && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result = result && getVersion() == other.getVersion();
            }
            boolean result2 = result && hasStatus() == other.hasStatus();
            if (hasStatus()) {
                result2 = result2 && getStatus() == other.getStatus();
            }
            boolean result3 = result2 && hasUuid() == other.hasUuid();
            if (hasUuid()) {
                result3 = result3 && getUuid().equals(other.getUuid());
            }
            boolean result4 = result3 && hasAnswer() == other.hasAnswer();
            if (hasAnswer()) {
                result4 = result4 && getAnswer().equals(other.getAnswer());
            }
            boolean result5 = result4 && hasOutputimage() == other.hasOutputimage();
            if (hasOutputimage()) {
                result5 = result5 && getOutputimage().equals(other.getOutputimage());
            }
            if (!result5 || !this.unknownFields.equals(other.unknownFields)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = (41 * 19) + getDescriptorForType().hashCode();
            if (hasVersion() != 0) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashLong(getVersion());
            }
            if (hasStatus() != 0) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashLong(getStatus());
            }
            if (hasUuid() != 0) {
                hash = (((hash * 37) + 3) * 53) + getUuid().hashCode();
            }
            if (hasAnswer() != 0) {
                hash = (((hash * 37) + 4) * 53) + getAnswer().hashCode();
            }
            if (hasOutputimage() != 0) {
                hash = (((hash * 37) + 5) * 53) + getOutputimage().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static response parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static response parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static response parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static response parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static response parseFrom(InputStream input) throws IOException {
            return (response) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static response parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (response) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static response parseDelimitedFrom(InputStream input) throws IOException {
            return (response) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static response parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (response) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static response parseFrom(CodedInputStream input) throws IOException {
            return (response) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static response parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (response) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(response prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* access modifiers changed from: protected */
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            return new Builder(parent);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements responseOrBuilder {
            private Object answer_;
            private int bitField0_;
            private ByteString outputimage_;
            private long status_;
            private Object uuid_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_response_descriptor;
            }

            /* access modifiers changed from: protected */
            public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_response_fieldAccessorTable.ensureFieldAccessorsInitialized(response.class, Builder.class);
            }

            private Builder() {
                this.version_ = 1;
                this.uuid_ = "";
                this.answer_ = "";
                this.outputimage_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.version_ = 1;
                this.uuid_ = "";
                this.answer_ = "";
                this.outputimage_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = response.alwaysUseFieldBuilders;
            }

            public Builder clear() {
                super.clear();
                this.version_ = 1;
                this.bitField0_ &= -2;
                this.status_ = 0;
                this.bitField0_ &= -3;
                this.uuid_ = "";
                this.bitField0_ &= -5;
                this.answer_ = "";
                this.bitField0_ &= -9;
                this.outputimage_ = ByteString.EMPTY;
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_response_descriptor;
            }

            public response getDefaultInstanceForType() {
                return response.getDefaultInstance();
            }

            public response build() {
                response result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public response buildPartial() {
                response result = new response((GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.status_ = this.status_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.uuid_ = this.uuid_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.answer_ = this.answer_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.outputimage_ = this.outputimage_;
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof response) {
                    return mergeFrom((response) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(response other) {
                if (other == response.getDefaultInstance()) {
                    return this;
                }
                if (other.hasVersion()) {
                    setVersion(other.getVersion());
                }
                if (other.hasStatus()) {
                    setStatus(other.getStatus());
                }
                if (other.hasUuid()) {
                    this.bitField0_ |= 4;
                    this.uuid_ = other.uuid_;
                    onChanged();
                }
                if (other.hasAnswer()) {
                    this.bitField0_ |= 8;
                    this.answer_ = other.answer_;
                    onChanged();
                }
                if (other.hasOutputimage()) {
                    setOutputimage(other.getOutputimage());
                }
                mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    response parsedMessage = response.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    response parsedMessage2 = (response) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((response) null);
                    }
                    throw th;
                }
            }

            public boolean hasVersion() {
                return (this.bitField0_ & 1) == 1;
            }

            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 1;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -2;
                this.version_ = 1;
                onChanged();
                return this;
            }

            public boolean hasStatus() {
                return (this.bitField0_ & 2) == 2;
            }

            public long getStatus() {
                return this.status_;
            }

            public Builder setStatus(long value) {
                this.bitField0_ |= 2;
                this.status_ = value;
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= -3;
                this.status_ = 0;
                onChanged();
                return this;
            }

            public boolean hasUuid() {
                return (this.bitField0_ & 4) == 4;
            }

            public String getUuid() {
                Object ref = this.uuid_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.uuid_ = s;
                }
                return s;
            }

            public ByteString getUuidBytes() {
                Object ref = this.uuid_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.uuid_ = b;
                return b;
            }

            public Builder setUuid(String value) {
                if (value != null) {
                    this.bitField0_ |= 4;
                    this.uuid_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearUuid() {
                this.bitField0_ &= -5;
                this.uuid_ = response.getDefaultInstance().getUuid();
                onChanged();
                return this;
            }

            public Builder setUuidBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 4;
                    this.uuid_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasAnswer() {
                return (this.bitField0_ & 8) == 8;
            }

            public String getAnswer() {
                Object ref = this.answer_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.answer_ = s;
                }
                return s;
            }

            public ByteString getAnswerBytes() {
                Object ref = this.answer_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.answer_ = b;
                return b;
            }

            public Builder setAnswer(String value) {
                if (value != null) {
                    this.bitField0_ |= 8;
                    this.answer_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearAnswer() {
                this.bitField0_ &= -9;
                this.answer_ = response.getDefaultInstance().getAnswer();
                onChanged();
                return this;
            }

            public Builder setAnswerBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 8;
                    this.answer_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasOutputimage() {
                return (this.bitField0_ & 16) == 16;
            }

            public ByteString getOutputimage() {
                return this.outputimage_;
            }

            public Builder setOutputimage(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 16;
                    this.outputimage_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearOutputimage() {
                this.bitField0_ &= -17;
                this.outputimage_ = response.getDefaultInstance().getOutputimage();
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static response getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<response> parser() {
            return PARSER;
        }

        public Parser<response> getParserForType() {
            return PARSER;
        }

        public response getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\nchat.proto\"D\n\bunsigned\u0012\r\n\u0005huuid\u0018\u0001 \u0001(\t\u0012\u0012\n\u0007version\u0018\u0002 \u0001(\u0004:\u00010\u0012\u0015\n\ngeneration\u0018\u0003 \u0001(\u0004:\u00010\"i\n\u0005token\u0012\u0012\n\u0007version\u0018\u0001 \u0001(\u0004:\u00011\u0012\u0010\n\u0005keyid\u0018\u0002 \u0001(\u0004:\u00011\u0012\u0015\n\ngeneration\u0018\u0003 \u0001(\u0004:\u00010\u0012\u0010\n\bunsigned\u0018\u0004 \u0001(\f\u0012\u0011\n\tsignature\u0018\u0005 \u0001(\f\"Ã\u0001\n\u0007request\u0012\u0012\n\u0007version\u0018\u0001 \u0001(\u0004:\u00011\u0012\u0015\n\u0005token\u0018\u0002 \u0001(\u000b2\u0006.token\u0012\f\n\u0004uuid\u0018\u0003 \u0001(\t\u0012\u0010\n\bquestion\u0018\u0004 \u0001(\t\u0012\u000e\n\u0006system\u0018\u0005 \u0001(\t\u0012\u000e\n\u0006apikey\u0018\u0006 \u0001(\t\u0012\u0019\n\bprovider\u0018\u0007 \u0001(\t:\u0007chatgpt\u0012\r\n\u0005model\u0018\b \u0001(\t\u0012\u0012\n\ninputimage\u0018\t \u0001(\f\u0012\u000f\n\u0007doimage\u0018\u0014 \u0001(\b\"d\n\bresponse\u0012", "\u0012\n\u0007version\u0018\u0001 \u0001(\u0004:\u00011\u0012\u0011\n\u0006status\u0018\u0002 \u0001(\u0004:\u00010\u0012\f\n\u0004uuid\u0018\u0003 \u0001(\t\u0012\u000e\n\u0006answer\u0018\u0004 \u0001(\t\u0012\u0013\n\u000boutputimage\u0018\u0005 \u0001(\fBA\n1com.google.appinventor.components.runtime.chatbotB\fChatBotToken"}, new Descriptors.FileDescriptor[0], new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                ChatBotToken.descriptor = root;
                return null;
            }
        });
    }
}
